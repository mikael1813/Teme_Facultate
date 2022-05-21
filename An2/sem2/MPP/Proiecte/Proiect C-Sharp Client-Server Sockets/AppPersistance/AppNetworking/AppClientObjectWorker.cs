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
	public class ChatClientWorker : IAppObserver //, Runnable
	{
		private IAppServices server;
		private TcpClient connection;

		private NetworkStream stream;
		private IFormatter formatter;
		private volatile bool connected;
		public ChatClientWorker(IAppServices server, TcpClient connection)
		{
			this.server = server;
			this.connection = connection;
			try
			{

				stream = connection.GetStream();
				formatter = new BinaryFormatter();
				connected = true;
			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
			}
		}

		public virtual void run()
		{
			while (connected)
			{
				try
				{
					object request = formatter.Deserialize(stream);
					object response = handleRequest((Request)request);
					if (response != null)
					{
						sendResponse((Response)response);
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
		//public virtual void messageReceived(Message message)
		//{
		//	MessageDTO mdto = DTOUtils.getDTO(message);
		//	Console.WriteLine("Message received  " + message);
		//	try
		//	{
		//		sendResponse(new NewMessageResponse(mdto));
		//	}
		//	catch (Exception e)
		//	{
		//		throw new ChatException("Sending error: " + e);
		//	}
		//}

		//public virtual void friendLoggedIn(User friend)
		//{
		//	UserDTO udto = DTOUtils.getDTO(friend);
		//	Console.WriteLine("Friend logged in " + friend);
		//	try
		//	{
		//		sendResponse(new FriendLoggedInResponse(udto));
		//	}
		//	catch (Exception e)
		//	{
		//		Console.WriteLine(e.StackTrace);
		//	}
		//}
		//public virtual void friendLoggedOut(User friend)
		//{
		//	UserDTO udto = DTOUtils.getDTO(friend);
		//	Console.WriteLine("Friend logged out " + friend);
		//	try
		//	{
		//		sendResponse(new FriendLoggedOutResponse(udto));
		//	}
		//	catch (Exception e)
		//	{
		//		Console.WriteLine(e.StackTrace);
		//	}
		//}

		private Response handleRequest(Request request)
		{
			Response response = null;
			if (request is LoginRequest)
			{
				Console.WriteLine("Login request ...");
				LoginRequest logReq = (LoginRequest)request;
				UtilizatorDTO udto = logReq.User;
				Utilizator user = DTOUtils.getFromDTO(udto);
				try
				{
					lock (server)
					{
						server.login(user, this);
					}
					return new OkResponse();
				}
				catch (AppException e)
				{
					connected = false;
					return new ErrorResponse(e.Message);
				}
			}
			if (request is LogoutRequest)
			{
				Console.WriteLine("Logout request");
				LogoutRequest logReq = (LogoutRequest)request;
				UtilizatorDTO udto = logReq.User;
				Utilizator user = DTOUtils.getFromDTO(udto);
				try
				{
					lock (server)
					{

						server.logout(user, this);
					}
					connected = false;
					return new OkResponse();

				}
				catch (AppException e)
				{
					return new ErrorResponse(e.Message);
				}
			}
			if (request is getProbeRequest)
			{
				Console.WriteLine("getProbe request ...");
				getProbeRequest logReq = (getProbeRequest)request;
				try
				{
					List<Proba> list;
					lock (server)
					{
						list = (List<Proba>)server.getProbe();
					}
					List<ProbaDTO> listDTO = DTOUtils.getDTO(list);
					return new GetProbeResponse(listDTO);
				}
				catch (AppException e)
				{
					connected = false;
					return new ErrorResponse(e.Message);
				}
			}
			if (request is getParticipantiDupaProbaRequest)
			{
				Console.WriteLine("getParticipantiDupaProbaRequest request ...");
				getParticipantiDupaProbaRequest logReq = (getParticipantiDupaProbaRequest)request;
				ProbaDTO udto = logReq.Proba;
				Proba user = DTOUtils.getFromDTO(udto);
				try
				{
					List<Participant> list;
					lock (server)
					{
						list = (List<Participant>)server.getParticipantiDupaProba(user);
					}
					List<ParticipantDTO> lista = DTOUtils.getDTO(list);
					return new GetParticipantiDupaProbaResponse(lista);
				}
				catch (AppException e)
				{
					connected = false;
					return new ErrorResponse(e.Message);
				}
			}
			if (request is getProbeDupaParticipantRequest)
			{
				Console.WriteLine("Login request ...");
				getProbeDupaParticipantRequest logReq = (getProbeDupaParticipantRequest)request;
				ParticipantDTO udto = logReq.Proba;
				Participant user = DTOUtils.getFromDTO(udto);
				try
				{
					List<Proba> list;
					lock (server)
					{
						list = (List<Proba>)server.getProbeDupaParticipanti(user);
					}
					List<ProbaDTO> listDTO = DTOUtils.getDTO(list);
					return new GetProbeDupaParticipantResponse(listDTO);
				}
				catch (AppException e)
				{
					connected = false;
					return new ErrorResponse(e.Message);
				}
			}
			if (request is getNrParticipantiRequest)
			{
				Console.WriteLine("Login request ...");
				getNrParticipantiRequest logReq = (getNrParticipantiRequest)request;
				
				try
				{
					List<int> list;
					lock (server)
					{
						list = server.getNrParticipanti();
					}
					return new GetNrParticipantiResponse(list);
				}
				catch (AppException e)
				{
					connected = false;
					return new ErrorResponse(e.Message);
				}
			}
			if (request is InscrieRequest)
			{
				Console.WriteLine("InscrieRequest request ...");
				InscrieRequest logReq = (InscrieRequest)request;
				ParticipantDTO udto = logReq.User;
				Participant user = DTOUtils.getFromDTO(udto);
				List<ProbaDTO> list = logReq.List;
				List<Proba> llist = DTOUtils.getFromDTO(list);
				try
				{
					lock (server)
					{
						server.Inscrie(user, llist);
						return new OkResponse();
					}
					
				}
				catch (AppException e)
				{
					connected = false;
					return new ErrorResponse(e.Message);
				}
			}
			//if (request is SendMessageRequest)
			//{
			//	Console.WriteLine("SendMessageRequest ...");
			//	SendMessageRequest senReq = (SendMessageRequest)request;
			//	MessageDTO mdto = senReq.Message;
			//	Message message = DTOUtils.getFromDTO(mdto);
			//	try
			//	{
			//		lock (server)
			//		{
			//			server.sendMessage(message);
			//		}
			//		return new OkResponse();
			//	}
			//	catch (ChatException e)
			//	{
			//		return new ErrorResponse(e.Message);
			//	}
			//}

			//if (request is GetLoggedFriendsRequest)
			//{
			//	Console.WriteLine("GetLoggedFriends Request ...");
			//	GetLoggedFriendsRequest getReq = (GetLoggedFriendsRequest)request;
			//	UserDTO udto = getReq.User;
			//	User user = DTOUtils.getFromDTO(udto);
			//	try
			//	{
			//		User[] friends;
			//		lock (server)
			//		{

			//			friends = server.getLoggedFriends(user);
			//		}
			//		UserDTO[] frDTO = DTOUtils.getDTO(friends);
			//		return new GetLoggedFriendsResponse(frDTO);
			//	}
			//	catch (ChatException e)
			//	{
			//		return new ErrorResponse(e.Message);
			//	}
			//}
			return response;
		}

		private void sendResponse(Response response)
		{
			Console.WriteLine("sending response " + response);
			formatter.Serialize(stream, response);
			stream.Flush();

		}

        public void newInscriere()
        {
            try
            {
                //sendResponse(new NewInscriereResponse(list));
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        
    }
}
