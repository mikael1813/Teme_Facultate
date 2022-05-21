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
    partial class Inscriere : Form
    {
        ClientCtrl ctrl;
        List<Proba> list = new List<Proba>();
        int lastSelected = -1;
        public Inscriere(ClientCtrl ctrl)
        {
            this.ctrl = ctrl;
            InitializeComponent();
            dataGridViewProbe.DataSource = ctrl.getProbe();
        }

        private void Inscriere_Load(object sender, EventArgs e)
        {

        }

        private void adaugaProba(object sender, EventArgs e)
        {
            List<Proba> list2 = new List<Proba>();
            foreach (Proba p in list)
            {
                list2.Add(p);
            }
            int i = -1;
            foreach (Proba p in ctrl.getProbe())
            {
                i++;
                if (i == lastSelected)
                {
                    bool ok = false;
                    foreach (Proba pp in list)
                    {
                        if (pp.id == p.id)
                            ok = true;
                    }
                    if (!ok)
                    {
                        list2.Add(p);
                        list.Add(p);
                    }

                    else
                    {
                        MessageBox.Show("Nu poti introduce aceeasi proba de 2 ori!");
                    }
                    break;
                }
            }
            dataGridViewProbeParticipant.DataSource = list2;

        }

        private void select(object sender, DataGridViewCellEventArgs e)
        {
            lastSelected = e.RowIndex;
        }

        private void inscrie(object sender, EventArgs e)
        {
            Participant p = new Participant(int.Parse(textBoxVarsta.Text), textBoxNume.Text);
            ctrl.Inscrie(p, list);
            //Update update = new Update(ctrl);
            //update.ShowDialog();
        }
    }
}
