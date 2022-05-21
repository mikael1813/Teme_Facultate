using AppModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AppPersistance
{
    public interface ProbaRepository : Repository<int, Proba>
    {
        IEnumerable<Proba> filterByStil(Stil stil);
    }
}
