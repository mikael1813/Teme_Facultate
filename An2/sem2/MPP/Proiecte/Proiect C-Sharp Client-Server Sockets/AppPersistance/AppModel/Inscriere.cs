using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AppModel
{
    public class Inscriere : Entity<int>
    {
        public Inscriere(Participant a, Proba b)
        {
            Participant = a;
            Proba = b;
        }
        public Participant Participant { get; set; }

        public Proba Proba { get; set; }

        public override string ToString()
        {
            return Participant + " " + Proba;
        }
    }
}
