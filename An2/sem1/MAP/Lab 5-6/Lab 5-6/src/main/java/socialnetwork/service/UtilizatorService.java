package socialnetwork.service;

import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.RepositoryException;
import socialnetwork.domain.validators.ValidationException;
import socialnetwork.repository.Repository;

import javax.swing.text.html.parser.Entity;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * Se opereaza doar asupra Utilizatorilor
 */
public class UtilizatorService {
    private final Repository<Long, Utilizator> repo;

    /**
     * Metoda creaza o noua entitate serice pentru utilizatori
     *
     * @param repo de tip Repository<Long,Utilizator>
     */
    public UtilizatorService(Repository<Long, Utilizator> repo) {
        this.repo = repo;
    }

    /**
     * Metoda incearca sa adauge utilizatorul in repository
     *
     * @param messageTask de tip Utilizator
     * @return null- if the given entity is saved otherwise returns the entity (id already exists)
     * @throws ValidationException      – if the entity is not valid
     * @throws IllegalArgumentException – if the given entity is null.
     */
    public Utilizator addUtilizator(Utilizator messageTask) throws ValidationException, IllegalArgumentException {
        messageTask.setId(generateId());
        Utilizator task = repo.save(messageTask);
        return task;
    }

    /**
     * @return un id care nu mai exista in repository
     */
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

    /**
     * Metoda incearca sa stearga utilizatorul cu id-ul id din repository
     *
     * @param Id de tip Long
     * @return the removed entity or null if there is no entity with the given id
     * @throws IllegalArgumentException – if the given id is null
     */
    public Utilizator deleteUtilizator(Long Id) {
        Utilizator task = repo.delete(Id);
        return task;
    }

    /**
     * Metoda adauga in lista de prieteni ai utilizatorului cu id-ul idU utilizatorul cu id-ul idP,
     * iar in lista de prieteni ai utilizatorului cu id-ul idP utilizatorul cu id-ul idU
     *
     * @param idU de tip long
     * @param idP de tip long
     * @throws RepositoryException daca id-urile nu reprezinta id-urile unor utilizatori existenti
     */
    public void addPrietenUtilizator(Long idU, Long idP) throws RepositoryException {
        Utilizator u = repo.findOne(idU), p = repo.findOne(idP);
        validarePrietenUtilizator(u, p);
        u.addFriend(p);
        p.addFriend(u);
    }

    /**
     * Metoda sterge din lista de prieteni ai utilizatorului cu id-ul idU utilizatorul cu id-ul idP,
     * iar in lista de prieteni ai utilizatorului cu id-ul idP utilizatorul cu id-ul idU
     *
     * @param idU de tip long
     * @param idP de tip long
     * @throws RepositoryException daca id-urile nu reprezinta id-urile unor utilizatori existenti
     */
    public void deletePrietenUtilizator(Long idU, Long idP) throws RepositoryException {
        Utilizator u = repo.findOne(idU), p = repo.findOne(idP);
        validarePrietenUtilizator(u, p);
        u.deletePrieten(p);
        p.deletePrieten(u);
    }

    /**
     * Metoda verifica daca exista cei doi utilizatori
     *
     * @param u de tip Utilizator din repository/null
     * @param p de tip Utilizator din repository/null
     * @throws RepositoryException daca cel putin unul din cei doi utilizatori nu exista
     */
    private void validarePrietenUtilizator(Utilizator u, Utilizator p) throws RepositoryException {
        String error = "";
        if (u == null)
            error = error + "\tId-ul utilizatorului nu exista\n";
        if (p == null)
            error = error + "\tId-ul prietenului nu exista\n";
        if (!error.isEmpty())
            throw new RepositoryException(error);
    }


    /**
     * @return toate entitatile utilizator
     */
    public Iterable<Utilizator> getAll() {
        return repo.findAll();
    }

    /**
     * @param id de tip Long
     *
     * @return utilizatorul cu id-ul id
     */
    public Utilizator getUtilizator(Long id) {
        return repo.findOne(id);
    }

    /**
     * @return numarul de utilizatori din repository
     */
    public long size() {
        return repo.size();
    }

    ///TO DO: add other methods
}
