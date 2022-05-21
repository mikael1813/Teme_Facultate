using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SqlClient;



namespace MaleaLukoil224
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.Title = "Oil=Stonks";
            Console.BackgroundColor = ConsoleColor.Magenta;
            Console.Clear();
            string connectionString = "Server=DESKTOP-KQN5LR3;Database=Malea Lukoil;Integrated Security=true;";
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    Console.WriteLine("Starea conexiunii: {​​​​0}​​​​", connection.State);
                    connection.Open();
                    Console.WriteLine("Starea conexiunii: {​​​​0}​​​​", connection.State);
                    SqlCommand insertCommand = new SqlCommand("INSERT INTO Angajati (nume, prenume," +
                    "data_nasterii, salariu, cetatenia) VALUES (@nume1, @prenume1, @data_nasterii1, " +
                    "@salariu1, @cetatenia1), (@nume2, @prenume2, @data_nasterii2, @salariu2," +
                    " @cetatenia2);", connection);
                    insertCommand.Parameters.AddWithValue("@nume1", "Al-Janabi");
                    insertCommand.Parameters.AddWithValue("@prenume1", "Hakem");
                    insertCommand.Parameters.AddWithValue("@data_nasterii1", "2000-09-13");
                    insertCommand.Parameters.AddWithValue("@salariu1", 30000);
                    insertCommand.Parameters.AddWithValue("@cetatenia1", "irakiana");
                    insertCommand.Parameters.AddWithValue("@nume2", "ElGammal");
                    insertCommand.Parameters.AddWithValue("@prenume2", "Mai");
                    insertCommand.Parameters.AddWithValue("@data_nasterii2", "1999-09-14");
                    insertCommand.Parameters.AddWithValue("@salariu2", 40000);
                    insertCommand.Parameters.AddWithValue("@cetatenia2", "UAEiana :))");
                    int insertCount = insertCommand.ExecuteNonQuery();
                    Console.WriteLine("Numar de inregistrari inserate: {​​​​0}​​​​", insertCount);
                    SqlCommand selectCommand = new SqlCommand("SELECT * FROM Angajati;", connection);
                    SqlDataReader reader = selectCommand.ExecuteReader();
                    if (reader.HasRows)
                    {
                        Console.WriteLine("Angajatii din tabel sunt: ");
                        while (reader.Read())
                        {
                            Console.WriteLine("{​​​​0}​​​​ {​​​​1}​​​​ {​​​​2}​​​​ {​​​​3}​​​​ {​​​​4}​​​​ {​​​​5}​​​​", reader.GetInt32(0), reader.GetString(1),
                            reader.GetString(2), reader.GetDateTime(3).ToString("dd-MM-yyyy"),
                            reader.GetInt64(4), reader.GetString(5));
                        }
                    }
                    reader.Close();
                    SqlCommand updateCommand = new SqlCommand("UPDATE Angajati SET salariu=@nou " +
                    "WHERE prenume=@prenume;", connection);
                    updateCommand.Parameters.AddWithValue("@nou", 80000);
                    updateCommand.Parameters.AddWithValue("@prenume", "Mai");
                    updateCommand.ExecuteNonQuery();
                    SqlCommand deleteCommand = new SqlCommand("DELETE FROM Angajati WHERE nume=@nume;", connection);
                    deleteCommand.Parameters.AddWithValue("@nume", "Al-Janabi");
                    deleteCommand.ExecuteNonQuery();
                    reader = selectCommand.ExecuteReader();
                    if (reader.HasRows)
                    {
                        Console.WriteLine("Dupa update si delete, angajatii din tabel sunt: ");
                        while (reader.Read())
                        {
                            Console.WriteLine("{​​​​0}​​​​ {​​​​1}​​​​ {​​​​2}​​​​ {​​​​3}​​​​ {​​​​4}​​​​ {​​​​5}​​​​", reader.GetInt32(0), reader.GetString(1),
                            reader.GetString(2), reader.GetDateTime(3).ToString("dd-MM-yyyy"),
                            reader.GetInt64(4), reader.GetString(5));
                        }
                    }
                    reader.Close();
                }
            }
            catch (Exception ex)
            {
                Console.ForegroundColor = ConsoleColor.Red;
                Console.WriteLine("Mesajul erorii este {​​​​0}​​​​", ex.Message);
            }
            Console.ReadKey();
        }
    }
}
