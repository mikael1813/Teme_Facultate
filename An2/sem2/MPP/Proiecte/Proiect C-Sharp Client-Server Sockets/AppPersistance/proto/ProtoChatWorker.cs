using AppModel;
using Google.Protobuf;
using Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace proto
{
    public class ProtoV3ChatWorker : IAppObserver
    {
        private IAppServices server;
        private TcpClient connection;

        private NetworkStream stream;
        private volatile bool connected;
        public ProtoV3ChatWorker(IAppServices server, TcpClient connection)
        {
            this.server = server;
            this.connection = connection;
            try
            {

                stream = connection.GetStream();
                connected = true;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        public void newInscriere()
        {
            List<int> list = new List<int>();
            try
            {
                lock (server)
                {
                    //list = server.getNrParticipanti();
                }
                App.Protocol.Response response = new App.Protocol.Response
                {
                    Type = App.Protocol.Response.Types.Type.NewInscriere
                };
                sendResponse(response);
            }
            catch (AppException e)
            {
                connected = false;
                sendResponse(ProtoUtils.createErrorResponse(e.Message));
            }
        }

        public virtual void run()
        {
            while (connected)
            {
                try
                {

                    App.Protocol.Request request = App.Protocol.Request.Parser.ParseDelimitedFrom(stream);
                    App.Protocol.Response response = handleRequest(request);
                    if (response != null)
                    {
                        sendResponse(response);
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.StackTrace);
                }

                try
                {
                    Thread.Sleep(1000);
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.StackTrace);
                }
            }
            try
            {
                stream.Close();
                connection.Close();
            }
            catch (Exception e)
            {
                Console.WriteLine("Error " + e);
            }
        }
        /*public virtual void messageReceived(chat.model.Message message)
		{
			Console.WriteLine("Message received  " + message);
			try
			{
				sendResponse(ProtoUtils.createNewMessageResponse(message));
			}
			catch (Exception e)
			{
				throw new ChatException("Sending error: " + e);
			}
		}

		public virtual void friendLoggedIn(chat.model.User friend)
		{
			Console.WriteLine("Friend logged in " + friend);
			try
			{
				sendResponse(ProtoUtils.createFriendLoggedInResponse(friend));
			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
			}
		}
		public virtual void friendLoggedOut(chat.model.User friend)
		{
			Console.WriteLine("Friend logged out " + friend);
			try
			{
				sendResponse(ProtoUtils.createFriendLoggedOutResponse(friend));
			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
			}
		}*/

        private App.Protocol.Response handleRequest(App.Protocol.Request request)
        {
            App.Protocol.Response response = null;
            App.Protocol.Request.Types.Type reqType = request.Type;
            switch (reqType)
            {
                case App.Protocol.Request.Types.Type.Login:
                    {
                        Console.WriteLine("Login request ...");
                        AppModel.Utilizator user = ProtoUtils.getUser(request);
                        try
                        {
                            lock (server)
                            {
                                server.login(user, this);
                            }
                            return ProtoUtils.createOkResponse();
                        }
                        catch (AppException e)
                        {
                            connected = false;
                            return ProtoUtils.createErrorResponse(e.Message);
                        }
                    }
                case App.Protocol.Request.Types.Type.Logout:
                    {
                        Console.WriteLine("Logout request");
                        AppModel.Utilizator user = ProtoUtils.getUser(request);
                        try
                        {
                            lock (server)
                            {

                                server.logout(user, this);
                            }
                            connected = false;
                            return ProtoUtils.createOkResponse();

                        }
                        catch (AppException e)
                        {
                            return ProtoUtils.createErrorResponse(e.Message);
                        }
                    }
                case App.Protocol.Request.Types.Type.GetProbe:
                    {
                        Console.WriteLine("GetProbe request ...");
                        //AppModel.Utilizator user = ProtoUtils.getUser(request);
                        try
                        {
                            List<Proba> list;
                            lock (server)
                            {

                                list = (List<Proba>)server.getProbe();
                            }
                            return ProtoUtils.createGetProbeResponse(list);
                        }
                        catch (AppException e)
                        {
                            connected = false;
                            return ProtoUtils.createErrorResponse(e.Message);
                        }
                    }
                case App.Protocol.Request.Types.Type.GetParticipantiDupaProbe:
                    {
                        Console.WriteLine("GetParticipantiDupaProbe request ...");
                        AppModel.Proba proba = ProtoUtils.getProba(request);
                        try
                        {
                            List<Participant> list;
                            lock (server)
                            {

                                list = (List<Participant>)server.getParticipantiDupaProba(proba);
                            }
                            return ProtoUtils.createGetParticipantiDupaProbaResponse(list);
                        }
                        catch (AppException e)
                        {
                            connected = false;
                            return ProtoUtils.createErrorResponse(e.Message);
                        }
                    }
                case App.Protocol.Request.Types.Type.GetProbeDupaParticipant:
                    {
                        Console.WriteLine("GetProbeDupaParticipant request ...");
                        AppModel.Participant participant = ProtoUtils.getParticipant(request);
                        try
                        {
                            List<Proba> list;
                            lock (server)
                            {

                                list = (List<Proba>)server.getProbeDupaParticipanti(participant);
                            }
                            return ProtoUtils.createGetProbeDupaParticipantResponse(list);
                        }
                        catch (AppException e)
                        {
                            connected = false;
                            return ProtoUtils.createErrorResponse(e.Message);
                        }
                    }
                case App.Protocol.Request.Types.Type.Inscrie:
                    {
                        Console.WriteLine("Inscrie request ...");
                        AppModel.Participant participant = ProtoUtils.getParticipantInscriere(request);
                        List<AppModel.Proba> list = ProtoUtils.getProbeInscriere(request);
                        try
                        {
                            lock (server)
                            {
                                server.Inscrie(participant, list);
                            }
                            return ProtoUtils.createOkResponse();
                        }
                        catch (AppException e)
                        {
                            connected = false;
                            return ProtoUtils.createErrorResponse(e.Message);
                        }
                    }
                case App.Protocol.Request.Types.Type.GetNrParticipanti:
                    {
                        Console.WriteLine("GetNrParticipanti request ...");
                        //AppModel.Participant participant = ProtoUtils.getParticipantInscriere(request);
                        //List<AppModel.Proba> list = ProtoUtils.getProbeInscriere(request);
                        List<int> list = new List<int>();
                        try
                        {
                            lock (server)
                            {
                                list = server.getNrParticipanti();
                            }
                            return ProtoUtils.createGetNrParticipanti(list);
                        }
                        catch (AppException e)
                        {
                            connected = false;
                            return ProtoUtils.createErrorResponse(e.Message);
                        }
                    }
                    /*case App.Protocol.Request.Types.Type.SendMessage:
						{
							Console.WriteLine("SendMessageRequest ...");
							chat.model.Message message = ProtoUtils.getMessage(request);
							try
							{
								lock (server)
								{
									server.sendMessage(message);
								}
								return ProtoUtils.createOkResponse();
							}
							catch (ChatException e)
							{
								return ProtoUtils.createErrorResponse(e.Message);
							}
						}

					case ChatRequest.Types.Type.GetLoggedFriends:
						{
							Console.WriteLine("GetLoggedFriends Request ...");
							chat.model.User user = ProtoUtils.getUser(request);  //DTOUtils.getFromDTO(udto);
							try
							{
								chat.model.User[] friends;
								lock (server)
								{

									friends = server.getLoggedFriends(user);
								}
								return ProtoUtils.createLoggedFriendsResponse(friends);
							}
							catch (ChatException e)
							{
								return ProtoUtils.createErrorResponse(e.Message);
							}
						}*/
            }
            return response;
        }

        private void sendResponse(App.Protocol.Response response)
        {
            Console.WriteLine("sending response " + response);
            lock (stream)
            {
                response.WriteDelimitedTo(stream);
                stream.Flush();
            }

        }
    }
}
