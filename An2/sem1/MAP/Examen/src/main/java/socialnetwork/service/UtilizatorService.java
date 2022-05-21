package socialnetwork.service;

import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.ValidationException;
import socialnetwork.repository.Repository;

import java.util.Iterator;

public class UtilizatorService {
    private final Repository<Long, Utilizator> repo;


    public UtilizatorService(Repository<Long, Utilizator> repo) {
        this.repo = repo;
    }

    public Iterable<Utilizator> getAll() {
        return repo.findAll();
    }

    public Utilizator addUtilizator(Utilizator user) throws ValidationException, IllegalArgumentException {
        user.setId(generateId());
        Utilizator task = repo.save(user);
        return task;
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

    public Utilizator deleteUtilizator(Long Id) {
        Utilizator task = repo.delete(Id);
        return task;
    }


    public Utilizator getUtilizator(Long id) {
        return repo.findOne(id);
    }
}
