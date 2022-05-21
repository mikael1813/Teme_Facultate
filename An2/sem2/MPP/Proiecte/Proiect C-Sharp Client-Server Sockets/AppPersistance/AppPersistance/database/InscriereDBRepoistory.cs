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
    public class InscriereDBRepository : InscriereRepository
    {
        private static readonly ILog log = LogManager.GetLogger("InscriereDBRepository");
        private ParticipantRepository participantRepository;
        private ProbaRepository probaRepository;
        public InscriereDBRepository(ParticipantRepository participantRepository, ProbaRepository probaRepository)
        {
            this.probaRepository = probaRepository;
            this.participantRepository = participantRepository;
            log.Info("Creating SortingTaskDbRepository");
        }

        public void Delete(int id)
        {
            IDbConnection con = DBUtils.getConnection();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "delete from Inscriere where ID=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);
                var dataR = comm.ExecuteNonQuery();
                if (dataR == 0) ;
                //throw new RepositoryException("No task deleted!");
            }
        }

        public IEnumerable<Inscriere> FindAll()
        {
            IDbConnection con = DBUtils.getConnection();
            IList<Inscriere> list = new List<Inscriere>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Inscriere";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {

                        int id = dataR.GetInt32(0);
                        int idParticipant = dataR.GetInt32(1);
                        int idProba = dataR.GetInt32(2);
                        Participant pa = participantRepository.FindOne(idParticipant);
                        pa.id = idParticipant;
                        Proba p = probaRepository.FindOne(idProba);
                        p.id = idProba;
                        Inscriere inscriere = new Inscriere(pa, p);
                        inscriere.id = id;
                        list.Add(inscriere);
                    }
                }
            }

            return list;
        }

        public Inscriere FindOne(int id)
        {
            log.InfoFormat("Entering findOne with value {0}", id);
            IDbConnection con = DBUtils.getConnection();

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Inscriere where ID=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int id1 = dataR.GetInt32(0);
                        int idParticipant = dataR.GetInt32(1);
                        int idProba = dataR.GetInt32(2);
                        Participant pa = participantRepository.FindOne(idParticipant);
                        Proba p = probaRepository.FindOne(idProba);
                        Inscriere inscriere = new Inscriere(pa, p);
                        inscriere.id = id1;
                        log.InfoFormat("Exiting findOne with value {0}", inscriere);
                        return inscriere;
                    }
                }
            }
            log.InfoFormat("Exiting findOne with value {0}", null);
            return null;
        }


        public Inscriere Save(Inscriere entity)
        {
            var con = DBUtils.getConnection();

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into Inscriere (IDParticipant, IDProba) values (@IDParticipant, @IDProba)";

                var paramIDParticipant = comm.CreateParameter();
                paramIDParticipant.ParameterName = "@IDParticipant";
                paramIDParticipant.Value = entity.Participant.id;
                comm.Parameters.Add(paramIDParticipant);

                var paramIDProba = comm.CreateParameter();
                paramIDProba.ParameterName = "@IDProba";
                paramIDProba.Value = entity.Proba.id;
                comm.Parameters.Add(paramIDProba);

                var result = comm.ExecuteNonQuery();
                if (result == 0) ;
                //throw new RepositoryException("No task added !");
            }
            return entity;
        }

    }
}
