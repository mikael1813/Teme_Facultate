package socialnetwork.service;

import socialnetwork.domain.Utilizator;
import socialnetwork.domain.enums.Activitate;
import socialnetwork.repository.Repository;

public class UtilizatorService {
    private final Repository<Long, Utilizator> repo;


    public UtilizatorService(Repository<Long, Utilizator> repo) {
        this.repo = repo;
    }

    public Iterable<Utilizator> getAll() {
        return repo.findAll();
    }

    public void setInactiv(Utilizator u){
        u.setActivitate(Activitate.INACTIV);
        repo.delete(u.getId());
        repo.save(u);
    }

    public void setActiv(Utilizator u){
        u.setActivitate(Activitate.ACTIV);
        repo.delete(u.getId());
        repo.save(u);
    }


    public Utilizator getUtilizator(Long id) {
        return repo.findOne(id);
    }
}
