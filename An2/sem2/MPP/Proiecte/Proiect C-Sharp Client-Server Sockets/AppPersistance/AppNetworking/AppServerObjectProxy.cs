using AppModel;
using AppNetworking.DTO;
using Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace AppNetworking
{
    public class AppServerProxy : IAppServices
    {
        private string host;
        private int port;

        private IAppObserver client;

        private NetworkStream stream;

        private IFormatter formatter;
        private TcpClient connection;

        private Queue<Response> responses;
        private volatile bool finished;
        private EventWaitHandle _waitHandle;
        public AppServerProxy(string host, int port)
        {
            this.host = host;
            this.port = port;
            responses = new Queue<Response>();
        }

        public virtual void login(Utilizator user, IAppObserver client)
        {
            initializeConnection();
            UtilizatorDTO udto = DTOUtils.getDTO(user);
            sendRequest(new LoginRequest(udto));
            Response response = readResponse();
            if (response is OkResponse)
            {
                this.client = client;
                return;
            }
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                closeConnection();
                throw new AppException(err.Message);
            }
        }

        //public virtual void sendMessage(Message message)
        //{
        //	MessageDTO mdto = DTOUtils.getDTO(message);
        //	sendRequest(new SendMessageRequest(mdto));
        //	Response response = readResponse();
        //	if (response is ErrorResponse)
        //	{
        //		ErrorResponse err = (ErrorResponse)response;
        //		throw new ChatException(err.Message);
        //	}
        //}

        public virtual void logout(Utilizator user, IAppObserver client)
        {
            UtilizatorDTO udto = DTOUtils.getDTO(user);
            sendRequest(new LogoutRequest(udto));
            Response response = readResponse();
            closeConnection();
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new AppException(err.Message);
            }
        }



        //public virtual User[] getLoggedFriends(User user)
        //{
        //	UserDTO udto = DTOUtils.getDTO(user);
        //	sendRequest(new GetLoggedFriendsRequest(udto));
        //	Response response = readResponse();
        //	if (response is ErrorResponse)
        //	{
        //		ErrorResponse err = (ErrorResponse)response;
        //		throw new ChatException(err.Message);
        //	}
        //	GetLoggedFriendsResponse resp = (GetLoggedFriendsResponse)response;
        //	UserDTO[] frDTO = resp.Friends;
        //	User[] friends = DTOUtils.getFromDTO(frDTO);
        //	return friends;
        //}

        private void closeConnection()
        {
            finished = true;
            try
            {
                stream.Close();
                //output.close();
                connection.Close();
                _waitHandle.Close();
                client = null;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }

        }

        private void sendRequest(Request request)
        {
            try
            {
                formatter.Serialize(stream, request);
                stream.Flush();
            }
            catch (Exception e)
            {
                throw new AppException("Error sending object " + e);
            }

        }

        private Response readResponse()
        {
            Response response = null;
            try
            {
                _waitHandle.WaitOne();
                lock (responses)
                {
                    //Monitor.Wait(responses); 
                    response = responses.Dequeue();

                }


            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
            return response;
        }
        private void initializeConnection()
        {
            try
            {
                connection = new TcpClient(host, port);
                stream = connection.GetStream();
                formatter = new BinaryFormatter();
                finished = false;
                _waitHandle = new AutoResetEvent(false);
                startReader();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }
        private void startReader()
        {
            Thread tw = new Thread(run);
            tw.Start();
        }


        private void handleUpdate(UpdateResponse update)
        {
            if(update is NewInscriereResponse)
            {
                NewInscriereResponse nwUpd = (NewInscriereResponse)update;
                List<ProbacuParticipant> list = nwUpd.Friends;
                try
                {
                    client.newInscriere();
                }
                catch(AppException e)
                {
                    Console.WriteLine(e.StackTrace);
                }
            }
            //if (update is FriendLoggedInResponse)
            //{

            //	FriendLoggedInResponse frUpd = (FriendLoggedInResponse)update;
            //	User friend = DTOUtils.getFromDTO(frUpd.Friend);
            //	Console.WriteLine("Friend logged in " + friend);
            //	try
            //	{
            //		client.friendLoggedIn(friend);
            //	}
            //	catch (ChatException e)
            //	{
            //		Console.WriteLine(e.StackTrace);
            //	}
            //}
            //if (update is FriendLoggedOutResponse)
            //{
            //	FriendLoggedOutResponse frOutRes = (FriendLoggedOutResponse)update;
            //	User friend = DTOUtils.getFromDTO(frOutRes.Friend);
            //	Console.WriteLine("Friend logged out " + friend);
            //	try
            //	{
            //		client.friendLoggedOut(friend);
            //	}
            //	catch (ChatException e)
            //	{
            //		Console.WriteLine(e.StackTrace);
            //	}
            //}

            //if (update is NewMessageResponse)
            //{
            //	NewMessageResponse msgRes = (NewMessageResponse)update;
            //	Message message = DTOUtils.getFromDTO(msgRes.Message);
            //	try
            //	{
            //		client.messageReceived(message);
            //	}
            //	catch (ChatException e)
            //	{
            //		Console.WriteLine(e.StackTrace);
            //	}
            //}
        }
        public virtual void run()
        {
            while (!finished)
            {
                try
                {
                    object response = formatter.Deserialize(stream);
                    Console.WriteLine("response received " + response);
                    if (response is UpdateResponse)
                    {
                        handleUpdate((UpdateResponse)response);
                    }
                    else
                    {

                        lock (responses)
                        {


                            responses.Enqueue((Response)response);

                        }
                        _waitHandle.Set();
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine("Reading error " + e);
                }

            }
        }

        public IEnumerable<Proba> getProbe()
        {
            sendRequest(new getProbeRequest());
            Response response = readResponse();
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new AppException(err.Message);
            }
            GetProbeResponse resp = (GetProbeResponse)response;
            List<ProbaDTO> list = (List<ProbaDTO>)resp.Friends;
            List<Proba> ls = DTOUtils.getFromDTO(list);
            return ls;
        }

        public IEnumerable<Participant> getParticipantiDupaProba(Proba p)
        {
            ProbaDTO pp = DTOUtils.getDTO(p);
            sendRequest(new getParticipantiDupaProbaRequest(pp));
            Response response = readResponse();
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new AppException(err.Message);
            }
            GetParticipantiDupaProbaResponse resp = (GetParticipantiDupaProbaResponse)response;
            List<ParticipantDTO> list = (List<ParticipantDTO>)resp.Friends;
            List<Participant> ls = DTOUtils.getFromDTO(list);
            return ls;
        }

        public IEnumerable<Proba> getProbeDupaParticipanti(Participant p)
        {
            ParticipantDTO pp = DTOUtils.getDTO(p);
            sendRequest(new getProbeDupaParticipantRequest(pp));
            Response response = readResponse();
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new AppException(err.Message);
            }
            GetProbeDupaParticipantResponse resp = (GetProbeDupaParticipantResponse)response;
            List<ProbaDTO> list = (List<ProbaDTO>)resp.Friends;
            List<Proba> ls = DTOUtils.getFromDTO(list);
            return ls;
        }

        public void Inscrie(Participant participant, List<Proba> probe)
        {
            ParticipantDTO pp = DTOUtils.getDTO(participant);
            List<ProbaDTO> list = DTOUtils.getDTO(probe);
            sendRequest(new InscrieRequest(pp,list));
            Response response = readResponse();
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new AppException(err.Message);
            }
        }

        public List<int> getNrParticipanti()
        {
            sendRequest(new getNrParticipantiRequest());
            Response response = readResponse();
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new AppException(err.Message);
            }
            GetNrParticipantiResponse resp = (GetNrParticipantiResponse)response;
            List<int> list = (List<int>)resp.Friends;
            //List<Proba> ls = DTOUtils.getFromDTO(list);
            return list;
        }
        //}
    }
}
