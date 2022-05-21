using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data.SqlClient;



namespace Lab1224_2
{
    public partial class Form1 : Form
    {
        string connectionString = "Server=LAPTOP-PVMK6IIK\\SQLEXPRESS;Database=Ski Resort;Integrated Security=true;";
        DataSet ds = new DataSet();
        SqlDataAdapter adapter = new SqlDataAdapter();
        bool ok = true;
        int lastSelectedChildRow = -1;
        public Form1()
        {
            InitializeComponent();
        }



        private void Form1_Load(object sender, EventArgs e)
        {
            this.Text = "Antrenori";
            button4.Enabled = false;
            button3.Enabled = false;
            button1.Enabled = false;
            //buttonReload.Text = "Reload data";
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    connection.Open();
                    adapter.SelectCommand = new SqlCommand("SELECT * FROM Antrenori;", connection);
                    adapter.Fill(ds, "Antrenori");
                    dataGridView1.DataSource = ds.Tables["Antrenori"];
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }



        private void buttonReload_Click(object sender, EventArgs e)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    adapter.SelectCommand = new SqlCommand("SELECT * FROM Servicii;", connection);
                    ds.Tables["Servicii"].Clear();
                    /*daca nu deschidem conexiunea explicit, metoda Fill o va deschide ca sa execute comanda
                    * si apoi o va inchide*/
                    adapter.Fill(ds, "Servicii");
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void selectParentCell(object sender, DataGridViewCellEventArgs e)
        {
            button4.Enabled = true;
            DataGridViewRow row = dataGridView1.Rows[e.RowIndex];
            int id = int.Parse(row.Cells[0].Value.ToString());
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    connection.Open();
                    if (ok == true)
                    {
                        SqlParameter sqlParameter = new SqlParameter("@id", SqlDbType.Int);
                        sqlParameter.Value = id;
                        SqlCommand sqlCommand = new SqlCommand("SELECT * FROM Servicii where IdAntrenor=@id;", connection);
                        sqlCommand.Parameters.Add(sqlParameter);
                        adapter.SelectCommand = sqlCommand;
                        adapter.Fill(ds, "Servicii");
                        ok = false;
                    }
                    dataGridView2.DataSource = ds.Tables["Servicii"];
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void selectedChildCell(object sender, DataGridViewCellEventArgs e)
        {
            button3.Enabled = true;
            button1.Enabled = true;
            lastSelectedChildRow = e.RowIndex;
        }

        private void deleteFromChild(object sender, EventArgs e)
        {
            if (lastSelectedChildRow == -1)
            {
                MessageBox.Show("Nu s-a selectat nicio linie din fiu");
            }
            else
            {
                DataGridViewRow row = dataGridView2.Rows[lastSelectedChildRow];
                int id = int.Parse(row.Cells[0].Value.ToString());

                try
                {
                    using (SqlConnection connection = new SqlConnection(connectionString))
                    {
                        connection.Open();

                        SqlParameter sqlParameter = new SqlParameter("@Id", SqlDbType.Int);
                        sqlParameter.Value = id;
                        SqlCommand sqlCommand = new SqlCommand("DELETE FROM Servicii WHERE IdServiciu=@Id;", connection);
                        sqlCommand.Parameters.Add(sqlParameter);
                        adapter.SelectCommand = sqlCommand;
                        adapter.Fill(ds, "Servicii");


                        dataGridView2.DataSource = ds.Tables["Servicii"];
                    }
                }
                catch (Exception ex)
                {
                    MessageBox.Show(ex.Message);
                }
            }
        }

        private void ModificaFromChild(object sender, EventArgs e)
        {
            foreach (DataGridViewRow row in dataGridView2.Rows)
            {
                if (row.Cells[0].Value != null)
                {
                    int id = int.Parse(row.Cells[0].Value.ToString());
                    int idAntrenor = int.Parse(row.Cells[1].Value.ToString());
                    string descriere = row.Cells[2].Value.ToString();
                    int pret = int.Parse(row.Cells[3].Value.ToString());
                    int idPachet = int.Parse(row.Cells[4].Value.ToString());

                    try
                    {
                        using (SqlConnection connection = new SqlConnection(connectionString))
                        {
                            connection.Open();

                            SqlParameter sqlParameter = new SqlParameter("@Id", SqlDbType.Int);
                            SqlParameter sqlParameter1 = new SqlParameter("@idAntrenor", SqlDbType.Int);
                            SqlParameter sqlParameter2 = new SqlParameter("@descriere", SqlDbType.VarChar);
                            SqlParameter sqlParameter3 = new SqlParameter("@pret", SqlDbType.Int);
                            SqlParameter sqlParameter4 = new SqlParameter("@idPachet", SqlDbType.Int);
                            sqlParameter.Value = id;
                            sqlParameter1.Value = idAntrenor;
                            sqlParameter2.Value = descriere;
                            sqlParameter3.Value = pret;
                            sqlParameter4.Value = idPachet;
                            SqlCommand sqlCommand = new SqlCommand("UPDATE Servicii SET IdAntrenor=@idAntrenor,Descriere=@descriere,Pret=@pret,IdPachet=@idPachet WHERE IdServiciu=@Id;", connection);
                            sqlCommand.Parameters.Add(sqlParameter);
                            sqlCommand.Parameters.Add(sqlParameter1);
                            sqlCommand.Parameters.Add(sqlParameter2);
                            sqlCommand.Parameters.Add(sqlParameter3);
                            sqlCommand.Parameters.Add(sqlParameter4);
                            adapter.SelectCommand = sqlCommand;
                            adapter.Fill(ds, "Servicii");


                            dataGridView2.DataSource = ds.Tables["Servicii"];
                        }
                    }
                    catch (Exception ex)
                    {
                        MessageBox.Show(ex.Message);
                    }
                }
            }
        }

        private void addService(object sender, EventArgs e)
        {
            int id = int.Parse(textBox1.Text);
            int idAntrenor = int.Parse(textBox2.Text);
            string descriere = textBox3.Text;
            int pret = int.Parse(textBox4.Text);
            int idPachet = int.Parse(textBox5.Text);

            try
            {
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    connection.Open();

                    SqlParameter sqlParameter = new SqlParameter("@Id", SqlDbType.Int);
                    SqlParameter sqlParameter1 = new SqlParameter("@idAntrenor", SqlDbType.Int);
                    SqlParameter sqlParameter2 = new SqlParameter("@descriere", SqlDbType.VarChar);
                    SqlParameter sqlParameter3 = new SqlParameter("@pret", SqlDbType.Int);
                    SqlParameter sqlParameter4 = new SqlParameter("@idPachet", SqlDbType.Int);
                    sqlParameter.Value = id;
                    sqlParameter1.Value = idAntrenor;
                    sqlParameter2.Value = descriere;
                    sqlParameter3.Value = pret;
                    sqlParameter4.Value = idPachet;
                    SqlCommand sqlCommand = new SqlCommand("Insert into Servicii values(@Id,@idAntrenor,@descriere,@pret,@idPachet)", connection);
                    sqlCommand.Parameters.Add(sqlParameter);
                    sqlCommand.Parameters.Add(sqlParameter1);
                    sqlCommand.Parameters.Add(sqlParameter2);
                    sqlCommand.Parameters.Add(sqlParameter3);
                    sqlCommand.Parameters.Add(sqlParameter4);
                    adapter.SelectCommand = sqlCommand;
                    adapter.Fill(ds, "Servicii");


                    dataGridView2.DataSource = ds.Tables["Servicii"];
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
    }
}
