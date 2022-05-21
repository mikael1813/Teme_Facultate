using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AppNetworking.DTO
{
    [Serializable]
    public class ParticipantDTO
    {
        public ParticipantDTO(string n,int v)
        {
            varsta = v;
            nume = n;
        }
        public int varsta { get; set; }

        public string nume { get; set; }
        public int id { get; set; }
    }
}
