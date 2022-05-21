using AppModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AppNetworking.DTO
{
    [Serializable]
    public class ProbaDTO
    {
        public ProbaDTO(float d, Stil s)
        {
            distanta = d;
            stil = s;
        }
        public float distanta { get; set; }

        public Stil stil { get; set; }
        public int id { get; set; }
    }
}
