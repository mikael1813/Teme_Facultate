using AppModel;
using log4net;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AppPersistance.database
{
    public class UtilizatorDBRepository : UtilizatorRepository
    {
        private static readonly ILog log = LogManager.GetLogger("UtilizatorDBRepository");
        public UtilizatorDBRepository()
        {
            log.Info("Creating SortingTaskDbRepository");
        }
        public void Delete(int id)
        {
            IDbConnection con = DBUtils.getConnection();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "delete from Utilizator where ID=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);
                var dataR = comm.ExecuteNonQuery();
                if (dataR == 0) ;
                //throw new RepositoryException("No task deleted!");
            }
        }

        public IEnumerable<Utilizator> FindAll()
        {
            IDbConnection con = DBUtils.getConnection();
            IList<Utilizator> list = new List<Utilizator>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Utilizator";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {

                        int id = dataR.GetInt32(0);
                        string u = dataR.GetString(1);
                        string p = dataR.GetString(2);
                        Utilizator utilizator = new Utilizator(u, p);
                        utilizator.id = id;
                        list.Add(utilizator);
                    }
                }
            }

            return list;
        }

        public Utilizator FindOne(int id)
        {
            log.InfoFormat("Entering findOne with value {0}", id);
            IDbConnection con = DBUtils.getConnection();

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Utilizator where ID=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int id1 = dataR.GetInt32(0);
                        string u = dataR.GetString(1);
                        string p = dataR.GetString(2);
                        Utilizator utilizator = new Utilizator(u, p);
                        utilizator.id = id1;
                        return utilizator;
                    }
                }
            }
            log.InfoFormat("Exiting findOne with value {0}", null);
            return null;
        }

        public Utilizator Save(Utilizator entity)
        {
            var con = DBUtils.getConnection();

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into Utilizator (user, parola) values (@user, @parola)";

                var paramIDParticipant = comm.CreateParameter();
                paramIDParticipant.ParameterName = "@user";
                paramIDParticipant.Value = entity.user;
                comm.Parameters.Add(paramIDParticipant);

                var paramIDProba = comm.CreateParameter();
                paramIDProba.ParameterName = "@parola";
                paramIDProba.Value = entity.parola;
                comm.Parameters.Add(paramIDProba);

                var result = comm.ExecuteNonQuery();
                if (result == 0) ;
                //throw new RepositoryException("No task added !");
            }
            return entity;
        }
    }
}
