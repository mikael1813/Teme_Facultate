using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace GTKClient.forms
{
    partial class Login : Form
    {
        ClientCtrl ctrl;
        public Login(ClientCtrl ctrl)
        {
            InitializeComponent();
            this.ctrl = ctrl;
            Console.WriteLine("Service adaugat cu succes");
            txtPassword.PasswordChar = '*';
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }


        private void Login_Load(object sender, EventArgs e)
        {

        }

        private void ClickEvent(object sender, EventArgs e)
        {
            String user = txtUsername.Text;
            String pass = txtPassword.Text;
            try
            {
                ctrl.login(user, pass);
                //MessageBox.Show("Login succeded");
                ListeProbe Win = new ListeProbe(ctrl);
                Win.Text = "Window for " + user;
                Win.Show();
                this.Hide();
            }
            catch (Exception ex)
            {
                MessageBox.Show(this, "Login Error " + ex.Message + ex.StackTrace, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }
        }
    }
}
