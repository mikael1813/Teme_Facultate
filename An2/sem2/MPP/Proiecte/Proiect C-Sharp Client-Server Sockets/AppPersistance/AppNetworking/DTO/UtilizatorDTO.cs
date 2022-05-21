using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AppNetworking.DTO
{
    [Serializable]
    public class UtilizatorDTO
    {
        public string user { get; set; }
        public string pass { get; set; }
        public int id { get; set; }
        public UtilizatorDTO(string u,string p)
        {
            user = u;
            pass = p;
        }
    }
}
