using AppModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AppNetworking.DTO
{
    [Serializable]
    public class InscriereDTO
    {
        public InscriereDTO(Participant a, Proba b)
        {
            Participant = a;
            Proba = b;
        }
        public Participant Participant { get; set; }

        public Proba Proba { get; set; }
        public int id { get; set; }
    }
}
