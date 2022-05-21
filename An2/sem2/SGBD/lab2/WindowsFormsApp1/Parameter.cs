using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace WindowsFormsApp1
{
    class Parameter
    {
        public int intreg { get; set; }
        public string cuvant { get; set; }
        public string tip { get; set; }
        public Parameter(int intreg)
        {
            tip = "int";
            this.intreg = intreg;
        }
        public Parameter(string intreg)
        {
            tip = "string";
            this.cuvant = intreg;
        }
    }
}
