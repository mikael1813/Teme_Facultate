using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AppModel
{
    public class Utilizator : Entity<int>
    {
        public Utilizator(string u, string p)
        {
            user = u;
            parola = p;
        }

        public string user { get; set; }

        public string parola { get; set; }

        public override string ToString()
        {
            return user + " " + parola;
        }
    }
}
