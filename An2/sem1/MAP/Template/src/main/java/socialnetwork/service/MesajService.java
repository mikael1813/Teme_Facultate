package socialnetwork.service;

import socialnetwork.domain.Mesaj;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.enums.Privatitate;
import socialnetwork.repository.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MesajService {
    private final Repository<Long, Mesaj> repo;


    public MesajService(Repository<Long, Mesaj> repo) {
        this.repo = repo;
    }

    public Iterable<Mesaj> getAll() {
        return repo.findAll();
    }


    public Iterable<Mesaj> getAllPublic() {
        List<Mesaj> list = new ArrayList<>();
        repo.findAll().forEach(x->{
            if(x.getPrivacy()== Privatitate.PUBLIC){
                list.add(x);
            }
        });
        return list;
    }

    private long generateId() {
        long s = 0;
        Iterator i = repo.findIDs().iterator();
        while (i.hasNext()) {
            long a = i.next().hashCode();
            if (a - s > 1) return s + 1;
            s = a;
        }
        return s + 1;
    }

    public Mesaj add(Mesaj m){
        m.setId(generateId());
        Mesaj mesaj = repo.save(m);
        return mesaj;
    }
}
