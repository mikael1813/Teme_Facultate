using AppModel;
using AppNetworking;
using AppPersistance;
using AppPersistance.database;
using proto;
using Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace Server
{
    class StartServer
    {
        static void Main(string[] args)
        {

            // IUserRepository userRepo = new UserRepositoryMock();
            //  IUserRepository userRepo=new UserRepositoryDb();
            // IMessageRepository messageRepository=new MessageRepositoryDb();
            ParticipantRepository repo2 = new ParticipantDBRepository();
            ProbaRepository repo3 = new ProbaDBRepository();
            InscriereRepository repo = new InscriereDBRepository(repo2, repo3);
            UtilizatorRepository repo1 = new UtilizatorDBRepository();
            IAppServices appServices = new AppServicesImpl(repo1, repo3, repo2, repo);
            foreach (Utilizator u in repo1.FindAll())
            {
                Console.WriteLine(u);
            }
            // IChatServer serviceImpl = new ChatServerImpl();
            ProtoChatServer server = new ProtoChatServer("127.0.0.1", 55556, appServices);
            server.Start();
            Console.WriteLine("Server started ...");
            //Console.WriteLine("Press <enter> to exit...");
            Console.ReadLine();

        }
    }

    public class SerialChatServer : ConcurrentServer
    {
        private IAppServices server;
        private ChatClientWorker worker;
        public SerialChatServer(string host, int port, IAppServices server) : base(host, port)
        {
            this.server = server;
            Console.WriteLine("SerialChatServer...");
        }
        protected override Thread createWorker(TcpClient client)
        {
            worker = new ChatClientWorker(server, client);
            return new Thread(new ThreadStart(worker.run));
        }
    }

    public class ProtoChatServer : ConcurrentServer
    {
        private IAppServices server;
        private ProtoV3ChatWorker worker;
        public ProtoChatServer(string host, int port, IAppServices server)
            : base(host, port)
        {
            this.server = server;
            Console.WriteLine("ProtoChatServer...");
        }
        protected override Thread createWorker(TcpClient client)
        {
            worker = new ProtoV3ChatWorker(server, client);
            return new Thread(new ThreadStart(worker.run));
        }
    }

}
