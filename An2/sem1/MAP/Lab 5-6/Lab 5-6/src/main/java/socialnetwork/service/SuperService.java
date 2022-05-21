package socialnetwork.service;

import jdk.vm.ci.meta.Constant;
import socialnetwork.domain.*;
import socialnetwork.domain.validators.IdValidation;
import socialnetwork.domain.validators.RepositoryException;
import socialnetwork.domain.validators.ValidationException;
import socialnetwork.utils.events.UtilizatorChangeEvent;
import socialnetwork.utils.observer.Observable;
import socialnetwork.utils.observer.Observer;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Lucreaza cu service-urile de prietenie si utilizatori
 */
public class SuperService implements Observable<UtilizatorChangeEvent> {
    private final UtilizatorService utilizatorService;
    private final PrietenieService prietenieService;
    private final MessageService messageService;
    private final FriendRequestService friendRequestService;

    private List<Observer<UtilizatorChangeEvent>> observers = new ArrayList<>();

    /**
     * Metoda creaza un super server care se foloseste de serverul de utilizatori si de cel de prieteni
     *
     * @param utilizatorService de tip UtilizatorService
     * @param prietenieService  de tip PrietenieService
     */
    public SuperService(UtilizatorService utilizatorService, PrietenieService prietenieService, MessageService messageService, FriendRequestService friendRequestService) {
        this.utilizatorService = utilizatorService;
        this.prietenieService = prietenieService;
        this.messageService = messageService;
        this.friendRequestService = friendRequestService;

        for (Prietenie p : prietenieService.getAll()) {
            System.out.println(p.toString());
            utilizatorService.addPrietenUtilizator(p.getId().getLeft(), p.getId().getRight());
        }
    }

    /**
     * Metoda adauga un nou utilizator avand firstName nume si lastName prenume
     *
     * @param from  de tip Long
     * @param to    de tip List<Long>
     * @param mesaj de tip String
     * @throws ValidationException daca mesajul nu este valid
     * @throws RepositoryException daca exista deja un element cu acelasi id
     */
    public void addMessage(Long from, List<Long> to, String mesaj) throws ValidationException, RepositoryException {
        if (getUtilizator(from) == null)
            throw new ValidationException("\t!!!Nu exista un utilizator cu unul din id-urile date");
        for (Long i : to) {
            if (getUtilizator(i) == null)
                throw new ValidationException("\t!!!Nu exista un utilizator cu unul din id-urile date");
        }

        List<Utilizator> list = new ArrayList<Utilizator>();

        for (Long j : to) {
            list.add(getUtilizator(j));
        }

        Message m = new Message(getUtilizator(from), list, mesaj);
        if (messageService.add(m) != null)
            throw new RepositoryException("\t!!!Exista deja un mesaj cu acest id");
    }

    /**
     * Metoda adauga un nou utilizator avand firstName nume si lastName prenume
     *
     * @param nume    de tip String
     * @param prenume de tip String
     * @throws ValidationException daca utilizatorul nu este valid
     * @throws RepositoryException daca exista deja un element cu acelasi id
     */
    public void addUtilizator(String nume, String prenume) throws ValidationException, RepositoryException {
        Utilizator u = new Utilizator(nume, prenume);
        if (utilizatorService.addUtilizator(u) != null)
            throw new RepositoryException("\t!!!Exista deja un utilizator cu acest id");
    }

    /**
     * Metoda sterge utilizatorul cu id-ul ID
     *
     * @param id de tip Long
     * @throws ValidationException ID-ul nu reprezinta un long
     * @throws RepositoryException nu exista un utilizator cu acest ID
     */
    public void deleteUtilizator(Long id) throws ValidationException, RepositoryException {

        List<Long> list = prietenieService.getPrieteniUtilizator(id);

        try {
            if (list != null)
                list.stream().forEach(x -> {
                    utilizatorService.deletePrietenUtilizator(id, x);
                    prietenieService.deletePrietenie(id, x);
                });
            if (utilizatorService.deleteUtilizator(id) == null)
                throw new RepositoryException("\tNu exista un utilizator cu acest id");
        } catch (RepositoryException e) {
            throw new RepositoryException("\tNu exista un utilizator cu acest id");
        }
    }

    /**
     * Metoda adauga o noua prietenie intre utilizatorii cu ID1 si ID2
     *
     * @param id1 de tip Long
     * @param id2 de tip Long
     * @throws ValidationException cele doua id-uri se pot converti in longuri
     * @throws RepositoryException daca id-urile nu reprezinta id-urile unor utilizatori existenti,
     *                             daca prietenia deja exista in repository
     */
    public void addPrietenie(Long id1, Long id2) throws ValidationException, RepositoryException {
        utilizatorService.addPrietenUtilizator(id1, id2);
        Prietenie p = new Prietenie(id1, id2);
        if (prietenieService.addPrietenie(p) != null)
            throw new RepositoryException("\tSunt deja prieteni\n");
    }

    /**
     * Metoda adauga o noua cerere de prietenie intre utilizatorii cu ID1 si ID2
     *
     * @param id1 de tip Long
     * @param id2 de tip Long
     * @throws ValidationException cele doua id-uri se pot converti in longuri
     * @throws RepositoryException daca id-urile nu reprezinta id-urile unor utilizatori existenti,
     *                             daca prietenia deja exista in repository
     */
    public void addFriendRequest(Long id1, Long id2) throws ValidationException, RepositoryException {
        FriendRequest f = new FriendRequest(id1, id2);

        for (Long x : prietenieService.getPrieteniUtilizator(id1)) {
            if (x == id2)
                throw new ValidationException("Acesti utilizatori sunt deja prieteni");
        }

        if (friendRequestService.addFriendRequest(f) != null)
            throw new RepositoryException("\tCererea exista deja\n");
    }

    /**
     * @param id1 de tip Long
     * @param id2 de tip Long
     * @throws ValidationException cele doua id-uri nu se pot converti in longuri
     * @throws RepositoryException daca id-urile nu reprezint id-urile unor utilizatori existenit,
     *                             daca acestia nu erau prieteni
     */
    public void deletePrietenie(Long id1, Long id2) throws ValidationException, RepositoryException {

        utilizatorService.deletePrietenUtilizator(id1, id2);
        //se sterge prietenia din repository
        if (prietenieService.deletePrietenie(id1, id2) == null)
            throw new RepositoryException("\tNu erau prieteni\n");
    }

    /**
     * @param id1 de tip Long
     * @param id2 de tip Long
     * @throws ValidationException cele doua id-uri nu se pot converti in longuri
     * @throws RepositoryException daca id-urile nu reprezint id-urile unor utilizatori existenit,
     *                             daca acestia nu erau prieteni
     */
    public void deleteFriendRequest(Long id1, Long id2, LocalDateTime date) throws ValidationException, RepositoryException {

        if (friendRequestService.deleteFriendRequest(id1, id2, date) == null)
            throw new RepositoryException("\tNu exista cererea de prietenie\n");
    }

    /**
     * @param id1 de tip Long
     * @param id2 de tip Long
     * @throws ValidationException cele doua id-uri nu se pot converti in longuri
     * @throws RepositoryException daca id-urile nu reprezint id-urile unor utilizatori existenit,
     *                             daca acestia nu aveau o cerere de prieteni
     */
    public void acceptFriendRequest(Long id1, Long id2) throws ValidationException, RepositoryException {

        friendRequestService.acceptFriendRequest(id1, id2);
        addPrietenie(id1, id2);
    }

    /**
     * @param id1 de tip Long
     * @param id2 de tip Long
     * @throws ValidationException cele doua id-uri nu se pot converti in longuri
     * @throws RepositoryException daca id-urile nu reprezint id-urile unor utilizatori existenit,
     *                             daca acestia nu aveau o cerere de prieteni
     */
    public void declineFriendRequest(Long id1, Long id2) throws ValidationException, RepositoryException {
        friendRequestService.declineFriendRequest(id1, id2);
    }

    /**
     * Metoda verifica daca cele doua id-uri se pot transforma in long-uri
     *
     * @param ID1 de tip String
     * @param ID2 de tip String
     * @throws ValidationException daca cel putin un id nu se poate transforma in long
     */
    public void iduriIntregi(String ID1, String ID2) throws ValidationException {
        String eroare = "";
        if (!IdValidation.isLong(ID1))
            eroare = eroare + "\tId-ul utilizatorului trebuie sa fie un numar intreg\n";
        if (!IdValidation.isLong(ID2))
            eroare = eroare + "\tId-ul prietenului trebuie sa fie un numar intreg\n";
        if (!eroare.isEmpty())
            throw new ValidationException(eroare);
    }

    /**
     * @param id de tip Long
     * @return List<Long> ce reprezinta toate id-ruile prieteniilor utilizatorului cu id
     */
    public List<String> getPrietenii(Long id) throws ValidationException, RepositoryException {
        if (utilizatorService.getUtilizator(id) == null)
            throw new RepositoryException("Id-ul introdus nu corespunde niciunui utilizator");

        List<Tuple<Long, LocalDateTime>> list = prietenieService.getPrieteniUtilizatorandTime(id);

        List<String> returnList = new ArrayList<>();

        list.forEach(x -> {
            String string = new String();
            Utilizator u = utilizatorService.getUtilizator(x.getLeft());
            string = string + u.getFirstName() + " " + u.getLastName() + " " + x.getRight().format(Constants.DATE_TIME_FORMATTER);
            returnList.add(string);
        });

        return returnList;
    }

    /**
     * @param u de tip Utilizator
     * @return List<Tuple < Utilizator, LocalDateTime>> ce reprezinta toti utilizatori ce sunt prieteni cu utilizatorul cu id si data de cand sunt prieteni
     */
    public List<Tuple<Utilizator, LocalDateTime>> getPrietenii(Utilizator u) throws ValidationException, RepositoryException {
        if (utilizatorService.getUtilizator(u.getId()) == null)
            throw new RepositoryException("Id-ul introdus nu corespunde niciunui utilizator");

        List<Tuple<Long, LocalDateTime>> list = prietenieService.getPrieteniUtilizatorandTime(u.getId());
        List<Tuple<Utilizator, LocalDateTime>> returnList = new ArrayList<>();

        list.forEach(x -> {
            returnList.add(new Tuple<>(utilizatorService.getUtilizator(x.getLeft()), x.getRight()));
        });

        return returnList;
    }

    /**
     * @param id de tip Long
     * @return List<Long> ce reprezinta toate id-ruile prieteniilor utilizatorului cu id
     */
    public List<String> getPrietenii(Long id, Month month) throws ValidationException, RepositoryException {
        if (utilizatorService.getUtilizator(id) == null)
            throw new RepositoryException("Id-ul introdus nu corespunde niciunui utilizator");

        List<Tuple<Long, LocalDateTime>> list = prietenieService.getPrieteniUtilizatorandTime(id);

        List<String> returnList = new ArrayList<>();

        list.forEach(x -> {
            String string = new String();
            if (x.getRight().getMonth() == month) {
                Utilizator u = utilizatorService.getUtilizator(x.getLeft());
                string = string + u.getFirstName() + " " + u.getLastName() + " " + x.getRight().format(Constants.DATE_TIME_FORMATTER);
                returnList.add(string);
            }
        });

        return returnList;
    }

    /**
     * @return int ce reprezinta componentele conexe din reteaua de prieteni
     */
    public int getComponenteConexe() {
        Graph g = new Graph((int) utilizatorService.size());

        for (Prietenie p : prietenieService.getAll()) {
            int j = Math.toIntExact(p.getId().getRight()) - 1;
            int i = Math.toIntExact(p.getId().getLeft()) - 1;
            g.addEdge(i, j);
        }

        return g.connectedComponents();
    }

    /**
     * @return int[] ce reprezinta utlizatori care formeaza cel mai lung lant de prieteni
     */
    public int[] getLongestPath() {
        Graph2 g = new Graph2((int) utilizatorService.size());

        for (Prietenie p : prietenieService.getAll()) {
            int j = Math.toIntExact(p.getId().getRight()) - 1;
            int i = Math.toIntExact(p.getId().getLeft()) - 1;
            g.addEdge(i, j);
        }

        int max = 0, nr1 = 0;

        for (Utilizator u1 : utilizatorService.getAll()) {
            for (Utilizator u2 : utilizatorService.getAll()) {
                if (u1.getId() != u2.getId()) {
                    int i = Math.toIntExact(u1.getId()) - 1;
                    int j = Math.toIntExact(u2.getId()) - 1;
                    int distanta = g.getLongestPath(i, j);
                    if (max < distanta) {
                        max = distanta;
                        nr1 = i;
                    }
                }
            }
        }

        Graph g2 = new Graph((int) utilizatorService.size());

        for (Prietenie p : prietenieService.getAll()) {
            int j = Math.toIntExact(p.getId().getRight()) - 1;
            int i = Math.toIntExact(p.getId().getLeft()) - 1;
            g2.addEdge(i, j);
        }

        int[] list = new int[(int) utilizatorService.size()];
        for (int y = 0; y < utilizatorService.size(); y++) list[y] = -1;


        boolean[] devisitat = new boolean[(int) utilizatorService.size()];
        for (boolean i : devisitat) i = false;

        g2.DFSUtil2(nr1, devisitat, list, 0);
        return list;
    }


    /**
     * @param id1 de tip String
     * @param id2 de tip String
     * @throws ValidationException cele doua id-uri nu se pot converti in longuri
     * @throws RepositoryException daca id-urile nu reprezint id-urile unor utilizatori existenit,
     *                             daca acestia nu erau prieteni
     */
    public Iterable<Message> getConversatii(Long id1, Long id2) throws ValidationException, RepositoryException {
        if (id1 == id2) throw new ValidationException("Utilizatorii trebuie sa fie diferiti");

        List<Message> m = messageConvertor(messageService.getConversatii(id1, id2));

        List<Message> sortedList = m.stream()
                .sorted(Comparator.comparing(Message::getData))
                .collect(Collectors.toList());

        return sortedList;
    }

    /**
     * @return Iterable<Utilizator> repr toti utilizatori
     */
    public Iterable<Utilizator> getUsers() {
        return utilizatorService.getAll();
    }

    /**
     * @return Iterable<Prietenie> repr toate prieteniile
     */
    public Iterable<Prietenie> getFriendships() {
        return prietenieService.getAll();
    }

    /**
     * @return Iterable<Message> repr toate mesajele
     */
    public Iterable<Message> getMessages() {
        return messageConvertor(messageService.getAll());
    }

    /**
     * @return Iterable<Message> repr toate cererile de prietenie
     */
    public Iterable<FriendRequest> getFriendRequests() {
        return friendRequestService.getAll();
    }

    /**
     * @param id de tip Long
     * @return Utilizator ce reprezinta utilizatorul cu id-ul id
     */
    public Utilizator getUtilizator(Long id) {
        return utilizatorService.getUtilizator(id);
    }

    public List<Message> messageConvertor(Iterable<MessageFromFile> msg) {
        List<Message> list = new ArrayList<Message>();

        msg.forEach(x -> {
            List<Utilizator> l = new ArrayList<Utilizator>();

            for (Long j : x.getTo()) {
                l.add(getUtilizator(j));
            }

            Message m = new Message(getUtilizator(x.getFrom()), l, x.getMessage(), x.getData());
            m.setId(x.getId());
            list.add(m);
        });

        return list;
    }

    @Override
    public void addObserver(Observer<UtilizatorChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<UtilizatorChangeEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(UtilizatorChangeEvent t) {
        observers.stream().forEach(x -> x.update(t));
    }
}
