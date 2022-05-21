using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AppModel
{[Serializable]
    public class ProbacuParticipant
    {
        public ProbacuParticipant(Proba p, int part)
        {
            this.distanta = p.distanta;
            this.stil = p.stil;
            participanti = part;
        }
        public float distanta { get; set; }
        public Stil stil { get; set; }
        public int participanti { get; set; }

        public override string ToString()
        {
            return distanta + " " + stil + " " + participanti;
        }

    }
}
