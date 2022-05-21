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
    public class ProbaDBRepository : ProbaRepository
    {

        private static readonly ILog log = LogManager.GetLogger("ProbaDBRepository");
        public ProbaDBRepository()
        {
            log.Info("Creating SortingTaskDbRepository");
        }
        public void Delete(int id)
        {
            IDbConnection con = DBUtils.getConnection();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "delete from Proba where ID=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);
                var dataR = comm.ExecuteNonQuery();
                if (dataR == 0) ;
                //throw new RepositoryException("No task deleted!");
            }
        }

        public IEnumerable<Proba> filterByStil(Stil stil)
        {
            log.InfoFormat("Entering findOne with value {0}", stil);
            IDbConnection con = DBUtils.getConnection();
            IList<Proba> list = new List<Proba>();

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Proba where ID=@stil";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@stil";
                paramId.Value = stil;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int id1 = dataR.GetInt32(0);
                        float dist = dataR.GetFloat(1);
                        Enum.TryParse(dataR.GetString(2), out Stil stil1);
                        Proba proba = new Proba(dist, stil1);
                        proba.id = id1;
                        list.Add(proba);
                    }
                }
            }
            log.InfoFormat("Exiting findOne with value {0}", null);
            return list;
        }

        public IEnumerable<Proba> FindAll()
        {
            IDbConnection con = DBUtils.getConnection();
            IList<Proba> list = new List<Proba>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Proba";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {

                        int id = dataR.GetInt32(0);
                        float dist = dataR.GetFloat(1);
                        Enum.TryParse(dataR.GetString(2), out Stil stil);
                        Proba proba = new Proba(dist, stil);
                        proba.id = id;
                        list.Add(proba);
                    }
                }
            }

            return list;
        }

        public Proba FindOne(int id)
        {
            log.InfoFormat("Entering findOne with value {0}", id);
            IDbConnection con = DBUtils.getConnection();

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Proba where ID=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int id1 = dataR.GetInt32(0);
                        float dist = dataR.GetFloat(1);
                        Enum.TryParse(dataR.GetString(2), out Stil stil);
                        Proba proba = new Proba(dist, stil);
                        proba.id = id1;
                        return proba;
                    }
                }
            }
            log.InfoFormat("Exiting findOne with value {0}", null);
            return null;
        }


        public Proba Save(Proba entity)
        {
            var con = DBUtils.getConnection();

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into Proba (distanta, stil) values (@dist, @stil)";

                var paramIDParticipant = comm.CreateParameter();
                paramIDParticipant.ParameterName = "@dist";
                paramIDParticipant.Value = entity.distanta;
                comm.Parameters.Add(paramIDParticipant);

                var paramIDProba = comm.CreateParameter();
                paramIDProba.ParameterName = "@stil";
                paramIDProba.Value = entity.stil.ToString();
                comm.Parameters.Add(paramIDProba);

                var result = comm.ExecuteNonQuery();
                if (result == 0) ;
                //throw new RepositoryException("No task added !");
            }
            return entity;
        }

    }
}
