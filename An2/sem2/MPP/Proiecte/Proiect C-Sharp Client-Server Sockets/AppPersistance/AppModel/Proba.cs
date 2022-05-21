using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AppModel
{
    public enum Stil
    {
        LIBER,
        SPATE,
        FLUTURE,
        MIXT
    }
    [Serializable]
    public class Proba : Entity<int>
    {
        public Proba(float d, Stil s)
        {
            distanta = d;
            stil = s;
        }
        public float distanta { get; set; }

        public Stil stil { get; set; }

        public override string ToString()
        {
            return distanta + " " + stil;
        }
    }
}
