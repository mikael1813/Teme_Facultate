using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AppModel
{
    public class Participant : Entity<int>
    {
        public Participant(int v, string n)
        {
            varsta = v;
            nume = n;
        }
        public int varsta { get; set; }

        public string nume { get; set; }

        public override string ToString()
        {
            return nume + " " + varsta;
        }
    }
}
