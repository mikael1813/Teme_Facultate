using AppNetworking;
using GTKClient.forms;
using Services;
using System;
using System.Windows.Forms;

namespace GTKClient
{
    static class StartChatClient
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);


            //IChatServer server=new ChatServerMock();          
            IAppServices server = new AppServerProxy("127.0.0.1", 55555);
            ClientCtrl ctrl = new ClientCtrl(server);
            Login win = new Login(ctrl);
            Application.Run(win);
        }
    }
}
