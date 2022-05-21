using AppModel;
using Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GTKClient
{
    public class ClientCtrl : IAppObserver
    {
        public event EventHandler<AppUserEventArgs> updateEvent; //ctrl calls it when it has received an update
        private readonly IAppServices server;
        private Utilizator currentUser;
        public ClientCtrl(IAppServices server)
        {
            this.server = server;
            currentUser = null;
        }

        public void login(String userId, String pass)
        {
            Utilizator user = new Utilizator(userId, pass);
            server.login(user, this);
            Console.WriteLine("Login succeeded ....");
            currentUser = user;
            Console.WriteLine("Current user {0}", user);
        }

        internal List<int> getNrParticipanti()
        {
            return server.getNrParticipanti();
        }

        public IEnumerable<Proba> getProbe()
        {
            return server.getProbe();
        }

        internal object getParticipantiDupaProba(Proba p)
        {
            return server.getParticipantiDupaProba(p);
        }

        internal object getProbeDupaParticipanti(Participant p)
        {
            return server.getProbeDupaParticipanti(p);
        }

        //public void messageReceived(Message message)
        //{
        //    String mess = "[" + message.Sender.Id + "]: " + message.Text;
        //    ChatUserEventArgs userArgs = new ChatUserEventArgs(ChatUserEvent.NewMessage, mess);
        //    Console.WriteLine("Message received");
        //    OnUserEvent(userArgs);
        //}

        //public void friendLoggedIn(User friend)
        //{
        //    Console.WriteLine("Friend logged in " + friend);
        //    ChatUserEventArgs userArgs = new ChatUserEventArgs(ChatUserEvent.FriendLoggedIn, friend.Id);
        //    OnUserEvent(userArgs);
        //}

        //public void friendLoggedOut(User friend)
        //{
        //    Console.WriteLine("Friend logged out" + friend);
        //    ChatUserEventArgs userArgs = new ChatUserEventArgs(ChatUserEvent.FriendLoggedOut, friend.Id);
        //    OnUserEvent(userArgs);
        //}

        public void logout()
        {
            Console.WriteLine("Ctrl logout");
            server.logout(currentUser, this);
            currentUser = null;
        }

        internal void Inscrie(Participant p, List<Proba> list)
        {
            server.Inscrie(p, list);
        }

        protected virtual void OnUserEvent(AppUserEventArgs e)
        {
            if (updateEvent == null) return;
            updateEvent(this, e);
            Console.WriteLine("Update Event called");
        }
        //public IList<String> getLoggedFriends()
        //{
        //    IList<String> loggedFriends = new List<string>();
        //    User[] friends = server.getLoggedFriends(currentUser);
        //    foreach (var user in friends)
        //    {
        //        loggedFriends.Add(user.Id);
        //    }
        //    return loggedFriends;
        //}

        //public void sendMessage(string id, string txt)
        //{
        //    //display the sent message on the user window
        //    String mess = "[" + currentUser.Id + "-->" + id + "]: " + txt;
        //    ChatUserEventArgs userArgs = new ChatUserEventArgs(ChatUserEvent.NewMessage, mess);
        //    OnUserEvent(userArgs);
        //    //sends the message to the server
        //    User receiver = new User(id);
        //    Message message = new Message(currentUser, receiver, txt);
        //    server.sendMessage(message);
        //}

        public void newInscriere(List<ProbacuParticipant> list)
        {
            Console.WriteLine("New Inscriere");
            AppUserEventArgs userArgs = new AppUserEventArgs(AppUserEvent.NewInscriere,list);
            OnUserEvent(userArgs);
        }
    }
}
