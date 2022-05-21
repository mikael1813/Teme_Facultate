using AppModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Services
{
    public interface IAppServices
    {
        void login(Utilizator user, IAppObserver client);
        void logout(Utilizator user, IAppObserver client);

        IEnumerable<Proba> getProbe();
        IEnumerable<Participant> getParticipantiDupaProba(Proba p);

        IEnumerable<Proba> getProbeDupaParticipanti(Participant p);

        void Inscrie(Participant participant, List<Proba> probe);
        List<int> getNrParticipanti();
    }
}
