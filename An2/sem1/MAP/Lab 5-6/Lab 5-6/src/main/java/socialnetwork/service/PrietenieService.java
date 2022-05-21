package socialnetwork.service;

import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.validators.ValidationException;
import socialnetwork.repository.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Se opereaza doar asupra Prieteniilor
 */
public class PrietenieService {
    private final Repository<Tuple<Long,Long>, Prietenie> repo;

    /**
     * Metoda creaza un nou service pentru prietenii care foloseste un Repository de prietenii
     *
     * @param repo de tip Repository
     */
    public PrietenieService(Repository<Tuple<Long, Long>, Prietenie> repo) {
        this.repo = repo;
    }

    /**
     * Metoda adauga noua prietenie in repo doar daca aceasta nu exista deja
     *
     * @param prietenie de tip Prietenie, not null
     * @return entitatea care trebuia sa fie adaugata daca deja exista in repo, altfel null
     * @throws ValidationException if the entity is not valid
     */
    public Prietenie addPrietenie(Prietenie prietenie) throws ValidationException {
        Prietenie pri = prietenie;
        //daca se doreste stergerea prieteniei 1-2, se verifica daca exista prietenia 1-2 sau prietenia 2-1
        if (repo.findOne(new Tuple<>(prietenie.getId().getRight(), prietenie.getId().getLeft())) == null &&
                repo.findOne(new Tuple<>(prietenie.getId().getLeft(), prietenie.getId().getRight())) == null)
            pri = repo.save(prietenie);
        return pri;
    }

    /**
     * Metoda elimina prietenia dintre utilizatorii cu id1 si id2
     *
     * @param id1 de tip Long, trebuie sa fie a unui utilizator existent
     * @param id2 de tip Long, trebuie sa fie a unui utilizator existent
     * @return prietenie eliminata sau null daca aceasta nu exista
     */
    public Prietenie deletePrietenie(Long id1, Long id2) {
        //se incearca stergerea prieteniei id1-id2 si id2-id1
        Prietenie prietenie = repo.delete(new Tuple<>(id1, id2));
        if (prietenie == null)
            prietenie = repo.delete(new Tuple<>(id2, id1));
        return prietenie;
    }

    /**
     * Metoda returneaza id-urile tuturor utilizatorilor care sunt prieteni cu utilizatorul care are id-ul id
     *
     * @param id de tip long
     * @return o lista de long-uri
     */
    public List<Long> getPrieteniUtilizator(long id) {
        List<Long> list = new ArrayList<>();
        for (Prietenie p : getAll())
            if (p.getId().getLeft() == id)
                list.add(p.getId().getRight());
            else if (p.getId().getRight() == id)
                list.add(p.getId().getLeft());
        return list;
    }

    public List<Tuple<Long,LocalDateTime>> getPrieteniUtilizatorandTime(Long id){
        List<Tuple<Long,LocalDateTime>> list = new ArrayList<>();
        for (Prietenie p : getAll())
            if (p.getId().getLeft() == id) {
                Tuple tuple = new Tuple(p.getId().getRight(),p.getDate());
                list.add(tuple);
            }
            else if (p.getId().getRight() == id) {
                Tuple tuple = new Tuple(p.getId().getLeft(),p.getDate());
                list.add(tuple);
            }
        return list;
    }

    /**
     * @return toate entitatile prietenie
     */
    public Iterable<Prietenie> getAll() {
        return repo.findAll();
    }

}
