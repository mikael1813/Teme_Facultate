package socialnetwork.service;

import socialnetwork.domain.Message;
import socialnetwork.domain.MessageFromFile;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.RepositoryException;
import socialnetwork.domain.validators.ValidationException;
import socialnetwork.repository.Repository;

import java.util.*;

public class MessageService {
    private final Repository<Long, MessageFromFile> repo;

    /**
     * Metoda creaza un nou service pentru message-uri care foloseste un Repository de Message
     *
     * @param repo de tip Repository
     */
    public MessageService(Repository<Long, MessageFromFile> repo) {
        this.repo = repo;
    }


    /**
     * Metoda incearca sa adauge message-ul in repository
     *
     * @param m de tip Message
     * @return null- if the given entity is saved otherwise returns the entity (id already exists)
     * @throws ValidationException      – if the entity is not valid
     * @throws IllegalArgumentException – if the given entity is null.
     */
    public MessageFromFile add(Message m) {
        List<Long> l = new ArrayList<Long>();
        for (Utilizator i : m.getTo()) {
            l.add(i.getId());
        }
        MessageFromFile mf = new MessageFromFile(m.getFrom().getId(), l, m.getMessage(), m.getData());
        mf.setId(generateId());

        MessageFromFile task = repo.save(mf);
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
     * @param id1 de tip String
     * @param id2 de tip String
     *
     * @return toate conversatiile celor 2 utilizatori
     */
    public Iterable<MessageFromFile> getConversatii(Long id1, Long id2) {
        List<MessageFromFile> list = new ArrayList<MessageFromFile>();

        repo.findAll().forEach(x->{
                x.getTo().forEach(y->{
                    if(y == id2 && x.getFrom() == id1){
                        list.add(x);
                    }
                    if(y == id1 && x.getFrom() == id2){
                        list.add(x);
                    }
                });
        });

        return list;
    }

    /**
     * @return toate entitatile message
     */
    public Iterable<MessageFromFile> getAll() {
        return repo.findAll();
    }

}
