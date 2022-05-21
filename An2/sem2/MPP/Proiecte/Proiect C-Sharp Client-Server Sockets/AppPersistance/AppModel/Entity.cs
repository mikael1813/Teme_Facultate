using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AppModel
{
    public class Entity<TID>
    {
        public TID id { get; set; }

        public override string ToString()
        {
            return id + " ";
        }
    }
}
