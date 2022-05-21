using AppModel;
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
    partial class Update : Form
    {
        public Update(List<ProbacuParticipant> list)
        {
            
            InitializeComponent();
            
            dataGridView1.DataSource = list;
            
        }

        private void Update_Load(object sender, EventArgs e)
        {

        }
    }
}
