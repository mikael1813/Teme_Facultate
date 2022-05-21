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
using System.Collections.Specialized;
using WindowsFormsApp1;

namespace Lab1224_2
{
    public partial class Form1 : Form
    {
        string connectionString = "Server=LAPTOP-PVMK6IIK\\SQLEXPRESS;Database=Ski Resort;Integrated Security=true;";
        DataSet ds = new DataSet();
        SqlDataAdapter adapter = new SqlDataAdapter();
        bool ok = true;
        int lastSelectedChildRow = -1;
        DataGridViewRow lastSelectedParentRow;
        NameValueCollection sAll;
        public Form1(NameValueCollection sAll)
        {
            InitializeComponent();
            this.sAll = sAll;
        }



        private void Form1_Load(object sender, EventArgs e)
        {
            this.Text = sAll.Get("ParentName");
            button4.Enabled = false;
            button3.Enabled = false;
            button1.Enabled = false;
            //buttonReload.Text = "Reload data";
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    connection.Open();
                    adapter.SelectCommand = new SqlCommand("SELECT * FROM " + sAll.Get("ParentName") + ";", connection);
                    adapter.Fill(ds, sAll.Get("ParentName"));
                    dataGridView1.DataSource = ds.Tables[sAll.Get("ParentName")];
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
                    int id = int.Parse(lastSelectedParentRow.Cells[0].Value.ToString());
                    SqlParameter sqlParameter = new SqlParameter("@id", SqlDbType.Int);
                    sqlParameter.Value = id;
                    SqlCommand sqlCommand = new SqlCommand("SELECT * FROM " + sAll.Get("ChildName") + " where " + sAll.Get("arg2") + "=@id;", connection);
                    sqlCommand.Parameters.Add(sqlParameter);
                    adapter.SelectCommand = sqlCommand;
                    ds.Tables[sAll.Get("ChildName")].Clear();
                    /*daca nu deschidem conexiunea explicit, metoda Fill o va deschide ca sa execute comanda
                    * si apoi o va inchide*/
                    adapter.Fill(ds, sAll.Get("ChildName"));
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
            lastSelectedParentRow = row;
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
                        SqlCommand sqlCommand = new SqlCommand("SELECT * FROM " + sAll.Get("ChildName") + " where " + sAll.Get("arg2") + "=@id;", connection);
                        sqlCommand.Parameters.Add(sqlParameter);
                        adapter.SelectCommand = sqlCommand;
                        adapter.Fill(ds, sAll.Get("ChildName"));
                        ok = false;
                    }
                    dataGridView2.DataSource = ds.Tables[sAll.Get("ChildName")];
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
                        SqlCommand sqlCommand = new SqlCommand("DELETE FROM " + sAll.Get("ChildName") + " WHERE " + sAll.Get("arg1") + "=@Id;", connection);
                        sqlCommand.Parameters.Add(sqlParameter);
                        adapter.SelectCommand = sqlCommand;
                        adapter.Fill(ds, sAll.Get("ChildName"));


                        dataGridView2.DataSource = ds.Tables[sAll.Get("ChildName")];
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
                    ///List<Parameter> list = new List<Parameter>();
                    List<String> list5 = new List<String>();
                    string query = "UPDATE " + sAll.Get("ChildName") + " SET ";
                    query = query + sAll.Get("arg2") + "=@" + sAll.Get("arg2");
                    for (int i = 2; i < int.Parse(sAll.Get("number")); i++)
                    {
                        query = query + "," + sAll.Get("arg" + (i + 1).ToString()) + "=@" + sAll.Get("arg" + (i + 1).ToString());
                    }
                    query = query + " WHERE "+sAll.Get("arg1")+"=@"+ sAll.Get("arg1") + ";";
                    for (int i = 0; i < int.Parse(sAll.Get("number")); i++)
                    {
                        list5.Add(row.Cells[i].Value.ToString());
                        //if (sAll.Get("type" + (i + 1).ToString()) == "0")
                        //{
                        //    Parameter p = new Parameter(int.Parse(row.Cells[i].Value.ToString()));
                        //    list.Add(p);
                        //}
                        //else
                        //{
                        //    Parameter p = new Parameter(row.Cells[i].Value.ToString());
                        //    list.Add(p);
                        //}
                    }

                    try
                    {
                        using (SqlConnection connection = new SqlConnection(connectionString))
                        {
                            connection.Open();


                            SqlCommand sqlCommand = new SqlCommand(query, connection);

                            for (int i = 0; i < int.Parse(sAll.Get("number")); i++)
                            {
                                String str = "@" + sAll.Get("arg" + (i + 1).ToString());
                                sqlCommand.Parameters.AddWithValue(str, list5[i]);
                                //if (list[i].tip == "int")
                                //{
                                //    SqlParameter sqlParameter = new SqlParameter("@" + sAll.Get("arg" + (i+1).ToString()), SqlDbType.Int);
                                //    sqlParameter.Value = list[i].intreg;
                                //    sqlCommand.Parameters.Add(sqlParameter);
                                //}
                                //else
                                //{
                                //    SqlParameter sqlParameter = new SqlParameter("@" + sAll.Get("arg" + (i + 1).ToString()), SqlDbType.VarChar);
                                //    sqlParameter.Value = list[i].cuvant;
                                //    sqlCommand.Parameters.Add(sqlParameter);
                                //}
                            }
                            adapter.SelectCommand = sqlCommand;
                            adapter.Fill(ds, sAll.Get("ChildName"));


                            dataGridView2.DataSource = ds.Tables[sAll.Get("ChildName")];
                        }
                    }
                    catch (Exception ex)
                    {
                        MessageBox.Show(ex.Message);
                    }
                }
            }
        }


        private void addChild(object sender, EventArgs e)
        {
            try
            {
                
                using (SqlConnection connection = new SqlConnection(connectionString))
                { 

                    connection.Open();

                    string query = "Begin IF NOT EXISTS(SELECT * FROM " + sAll.Get("ChildName") + " where " + sAll.Get("arg1") + "=@"+sAll.Get("arg1")+") BEGIN ";

                    query += "Insert into " + sAll.Get("ChildName") + " values(";
                    query = query + "@" + sAll.Get("arg" + (0 + 1).ToString());
                    for (int i = 1; i < int.Parse(sAll.Get("number")); i++)
                    {
                        query = query + ",@" + sAll.Get("arg" + (i + 1).ToString());
                    }
                    query = query + ") END END";

                    foreach (DataGridViewRow row in dataGridView2.Rows)
                    {
                        
                        if (row.Cells[0].Value != null)
                        {
                            //List<Parameter> list = new List<Parameter>();
                            List<String> list5 = new List<String>();
                            for (int i = 0; i < int.Parse(sAll.Get("number")); i++)
                            {
                                list5.Add(row.Cells[i].Value.ToString());
                                //if (sAll.Get("type" + (i + 1).ToString()) == "0")
                                //{
                                //    Parameter p = new Parameter(int.Parse(row.Cells[i].Value.ToString()));
                                //    list.Add(p);
                                //}
                                //else
                                //{
                                //    Parameter p = new Parameter(row.Cells[i].Value.ToString());
                                //    list.Add(p);
                                //}
                            }

                            SqlCommand sqlCommand2 = new SqlCommand(query, connection);

                            for (int i = 0; i < int.Parse(sAll.Get("number")); i++)
                            {
                                String str = "@" + sAll.Get("arg" + (i + 1).ToString());
                                sqlCommand2.Parameters.AddWithValue(str, list5[i]);
                                //if (list[i].tip == "int")
                                //{
                                //    SqlParameter sqlParameter = new SqlParameter("@" + sAll.Get("arg" + (i + 1).ToString()), SqlDbType.Int);
                                    
                                //    //sqlParameter.Value = list[i].intreg;
                                //    //sqlCommand2.Parameters.Add(sqlParameter);
                                    
                                //}
                                //else
                                //{
                                //    SqlParameter sqlParameter = new SqlParameter("@" + sAll.Get("arg" + (i + 1).ToString()), SqlDbType.VarChar);
                                //    sqlParameter.Value = list[i].cuvant;
                                //    sqlCommand2.Parameters.Add(sqlParameter);
                                //}
                            }
                            adapter.SelectCommand = sqlCommand2;
                            adapter.Fill(ds, sAll.Get("ChildName"));


                            dataGridView2.DataSource = ds.Tables[sAll.Get("ChildName")];
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
    }
}
