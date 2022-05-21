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
    public class ParticipantDBRepository : ParticipantRepository
    {

        private static readonly ILog log = LogManager.GetLogger("ParticipantDBRepository");
        public ParticipantDBRepository()
        {
            log.Info("Creating SortingTaskDbRepository");
        }
        public void Delete(int id)
        {
            IDbConnection con = DBUtils.getConnection();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "delete from Participant where ID=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);
                var dataR = comm.ExecuteNonQuery();
                if (dataR == 0) ;
                //throw new RepositoryException("No task deleted!");
            }
        }

        public IEnumerable<Participant> filterByName(string name)
        {
            log.InfoFormat("Entering findOne with value {0}", name);
            IDbConnection con = DBUtils.getConnection();
            IList<Participant> list = new List<Participant>();

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Participant where nume=@nume";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@nume";
                paramId.Value = name;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int id1 = dataR.GetInt32(0);
                        string nume = dataR.GetString(1);
                        int varsta = dataR.GetInt32(2);
                        Participant participant = new Participant(varsta, nume);
                        participant.id = id1;
                        list.Add(participant);
                    }
                }
            }
            log.InfoFormat("Exiting findOne with value {0}", null);
            return list;
        }

        public IEnumerable<Participant> filterByVarsta(int varsta)
        {
            log.InfoFormat("Entering findOne with value {0}", varsta);
            IDbConnection con = DBUtils.getConnection();
            IList<Participant> list = new List<Participant>();

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Participant where nume=@varsta";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@varsta";
                paramId.Value = varsta;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int id1 = dataR.GetInt32(0);
                        string nume = dataR.GetString(1);
                        int varsta2 = dataR.GetInt32(2);
                        Participant participant = new Participant(varsta2, nume);
                        participant.id = id1;
                        list.Add(participant);
                    }
                }
            }
            log.InfoFormat("Exiting findOne with value {0}", null);
            return list;
        }

        public IEnumerable<Participant> FindAll()
        {
            IDbConnection con = DBUtils.getConnection();
            IList<Participant> list = new List<Participant>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Participant";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {

                        int id = dataR.GetInt32(0);
                        string nume = dataR.GetString(1);
                        int varsta = dataR.GetInt32(2);
                        Participant participant = new Participant(varsta, nume);
                        participant.id = id;
                        list.Add(participant);
                    }
                }
            }

            return list;
        }

        public Participant FindOne(int id)
        {
            log.InfoFormat("Entering findOne with value {0}", id);
            IDbConnection con = DBUtils.getConnection();

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Participant where ID=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int id1 = dataR.GetInt32(0);
                        string nume = dataR.GetString(1);
                        int varsta = dataR.GetInt32(2);
                        Participant participant = new Participant(varsta, nume);
                        participant.id = id1;
                        return participant;
                    }
                }
            }
            log.InfoFormat("Exiting findOne with value {0}", null);
            return null;
        }

        public Participant Save(Participant entity)
        {
            var con = DBUtils.getConnection();

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into Participant (nume,varsta) values (@nume, @varsta)";

                var paramIDParticipant = comm.CreateParameter();
                paramIDParticipant.ParameterName = "@nume";
                paramIDParticipant.Value = entity.nume;
                comm.Parameters.Add(paramIDParticipant);

                var paramIDProba = comm.CreateParameter();
                paramIDProba.ParameterName = "@varsta";
                paramIDProba.Value = entity.varsta;
                comm.Parameters.Add(paramIDProba);

                var result = comm.ExecuteNonQuery();
                if (result == 0) ;
                //throw new RepositoryException("No task added !");
            }
            return entity;
        }

    }
}
