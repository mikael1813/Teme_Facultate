using AppNetworking;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace proto
{
    static class ProtoUtils
    {
        public static App.Protocol.Request createLoginRequest(AppModel.Utilizator user)
        {
            //new proto.User {Id = user.Id, Passwd = user.Password};
            App.Protocol.Utilizator userDTO = new App.Protocol.Utilizator { Id = user.id, Parola = user.parola, User = user.user };
            App.Protocol.Request request = new App.Protocol.Request { Type = App.Protocol.Request.Types.Type.Login, User = userDTO };

            return request;
        }

        public static App.Protocol.Request createLogoutRequest(AppModel.Utilizator user)
        {
            App.Protocol.Utilizator userDTO = new App.Protocol.Utilizator { Id = user.id };
            App.Protocol.Request request = new App.Protocol.Request { Type = App.Protocol.Request.Types.Type.Logout, User = userDTO };
            return request;
        }

        internal static App.Protocol.Response createGetNrParticipanti(List<int> list)
        {
            App.Protocol.Response response = new App.Protocol.Response
            {
                Type = App.Protocol.Response.Types.Type.GetNrParticipanti
            };
            foreach (int nr in list)
            {
                response.NrParticipnati.Add(nr);
            }

            return response;
        }

        /*public static Request createSendMesssageRequest(chat.model.Message message)
{
   proto.Message messageDTO = new proto.Message
   {
       SenderId = message.Sender.Id,
       ReceiverId = message.Receiver.Id,
       Text = message.Text
   };

   ChatRequest request = new ChatRequest { Type = ChatRequest.Types.Type.SendMessage, Message = messageDTO };
   return request;
}


public static ChatRequest createLoggedFriendsRequest(chat.model.User user)
{
   proto.User userDTO = new proto.User { Id = user.Id };
   ChatRequest request = new ChatRequest
   {
       Type = ChatRequest.Types.Type.GetLoggedFriends,
       User = userDTO
   };
   return request;
}*/


        public static App.Protocol.Response createOkResponse()
        {
            App.Protocol.Response response = new App.Protocol.Response { Type = App.Protocol.Response.Types.Type.Ok };
            return response;
        }


        public static App.Protocol.Response createErrorResponse(String text)
        {
            App.Protocol.Response response = new App.Protocol.Response
            {
                Type = App.Protocol.Response.Types.Type.Error,
                Error = text
            };
            return response;
        }

        internal static App.Protocol.Response createGetProbeResponse(List<AppModel.Proba> list)
        {
            App.Protocol.Response response = new App.Protocol.Response
            {
                Type = App.Protocol.Response.Types.Type.GetProbe
            };
            foreach (AppModel.Proba proba in list)
            {
                App.Protocol.Proba userDTO = new App.Protocol.Proba { Distanta = proba.distanta, Stil = proba.stil.ToString(), Id = proba.id };

                response.Probe.Add(userDTO);
            }

            return response;
        }

        internal static App.Protocol.Response createGetParticipantiDupaProbaResponse(List<AppModel.Participant> list)
        {
            App.Protocol.Response response = new App.Protocol.Response
            {
                Type = App.Protocol.Response.Types.Type.GetProbe
            };
            foreach (AppModel.Participant participant in list)
            {
                App.Protocol.Participant userDTO = new App.Protocol.Participant { Nume = participant.nume, Varsta = participant.varsta, Id = participant.id };

                response.Participanti.Add(userDTO);
            }

            return response;
        }

        /*public static Response createFriendLoggedInResponse(chat.model.User user)
        {
            proto.User userDTO = new proto.User { Id = user.Id };
            ChatResponse response = new ChatResponse { Type = ChatResponse.Types.Type.FriendLoggedIn, User = userDTO };
            return response;
        }

        public static ChatResponse createFriendLoggedOutResponse(chat.model.User user)
        {
            proto.User userDTO = new proto.User { Id = user.Id };
            ChatResponse response = new ChatResponse { Type = ChatResponse.Types.Type.FriendLoggedOut, User = userDTO };
            return response;
        }

        public static ChatResponse createNewMessageResponse(chat.model.Message message)
        {
            proto.Message messageDTO = new proto.Message
            {
                SenderId = message.Sender.Id,
                ReceiverId = message.Receiver.Id,
                Text = message.Text
            };

            ChatResponse response = new ChatResponse { Type = ChatResponse.Types.Type.NewMessage, Message = messageDTO };
            return response;
        }


        public static ChatResponse createLoggedFriendsResponse(chat.model.User[] users)
        {
            ChatResponse response = new ChatResponse
            {
                Type = ChatResponse.Types.Type.GetLoggedFriends
            };
            foreach (chat.model.User user in users)
            {
                proto.User userDTO = new proto.User { Id = user.Id };

                response.Friends.Add(userDTO);
            }

            return response;
        }*/

        public static String getError(App.Protocol.Response response)
        {
            String errorMessage = response.Error;
            return errorMessage;
        }

        internal static AppModel.Participant getParticipantInscriere(App.Protocol.Request request)
        {
            AppModel.Participant participant = new AppModel.Participant(request.ParticipnatNou.Varsta, request.ParticipnatNou.Nume);
            participant.id = request.ParticipnatNou.Id;
            return participant;
        }

        internal static List<AppModel.Proba> getProbeInscriere(App.Protocol.Request request)
        {
            List<AppModel.Proba> list = new List<AppModel.Proba>();
            foreach(App.Protocol.Proba p in request.ListProbe)
            {
                AppModel.Proba proba = new AppModel.Proba(p.Distanta, (AppModel.Stil)Enum.Parse(typeof(AppModel.Stil), p.Stil));
                list.Add(proba);
            }
            return list;
            
        }

        internal static App.Protocol.Response createGetProbeDupaParticipantResponse(List<AppModel.Proba> list)
        {
            App.Protocol.Response response = new App.Protocol.Response
            {
                Type = App.Protocol.Response.Types.Type.GetProbe
            };
            foreach (AppModel.Proba proba in list)
            {
                App.Protocol.Proba userDTO = new App.Protocol.Proba { Distanta = proba.distanta, Stil = proba.stil.ToString(), Id = proba.id };

                response.ProbeParticipant.Add(userDTO);
            }

            return response;
        }

        /* public static App.Protocol.Utilizator getUser(App.Protocol.Response response)
         {
             AppModel.Utilizator user = new AppModel.Utilizator(response);
             return user;
         }*/

        public static AppModel.Utilizator getUser(App.Protocol.Request request)
        {
            AppModel.Utilizator user = new AppModel.Utilizator(request.User.User, request.User.Parola);
            user.id = request.User.Id;
            return user;
        }

        public static AppModel.Proba getProba(App.Protocol.Request request)
        {
            AppModel.Proba proba = new AppModel.Proba(request.Proba.Distanta, (AppModel.Stil)Enum.Parse(typeof(AppModel.Stil), request.Proba.Stil));
            proba.id = request.Proba.Id;
            return proba;
        }

        public static AppModel.Participant getParticipant(App.Protocol.Request request)
        {
            AppModel.Participant participant = new AppModel.Participant(request.Participant.Varsta, request.Participant.Nume);
            participant.id = request.Participant.Id;
            return participant;
        }


        /*public static chat.model.Message getMessage(ChatResponse response)
        {
            chat.model.User sender = new chat.model.User(response.Message.SenderId);
            chat.model.User receiver = new chat.model.User(response.Message.ReceiverId);
            chat.model.Message message = new chat.model.Message(sender, receiver, response.Message.Text);
            return message;
        }

        public static chat.model.User[] getFriends(ChatResponse response)
        {
            chat.model.User[] friends = new chat.model.User[response.Friends.Count];
            for (int i = 0; i < response.Friends.Count; i++)
            {
                chat.model.User user = new chat.model.User(response.Friends[i].Id);
                friends[i] = user;
            }
            return friends;
        }

        public static chat.model.User getUser(ChatRequest request)
        {
            chat.model.User user = new chat.model.User(request.User.Id);
            user.Password = request.User.Passwd;
            return user;
        }
        public static chat.model.Message getMessage(ChatRequest request)
        {
            chat.model.User sender = new chat.model.User(request.Message.SenderId);
            chat.model.User receiver = new chat.model.User(request.Message.ReceiverId);
            chat.model.Message message = new chat.model.Message(sender, receiver, request.Message.Text);
            return message;
        }*/

    }
}
