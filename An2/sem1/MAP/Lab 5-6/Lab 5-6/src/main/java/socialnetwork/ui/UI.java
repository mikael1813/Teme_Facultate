package socialnetwork.ui;

import socialnetwork.domain.MessageFromFile;
import socialnetwork.domain.validators.IdValidation;
import socialnetwork.domain.validators.RepositoryException;
import socialnetwork.domain.validators.ValidationException;
import socialnetwork.service.SuperService;

import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

/**
 * Gestioneaza folosirea programului de catre utilizator
 */
public class UI {
    private final SuperService service;

    private final Scanner console = new Scanner(System.in);

    /**
     * Metoda afisaza meniul
     */
    private void menu() {
        System.out.println("*  Meniu  *");
        System.out.println("1 - adauga un utilizator");
        System.out.println("2 - sterge un utilizator");
        System.out.println("3 - adauga o prietenie");
        System.out.println("4 - sterge o prietenie");
        System.out.println("5 - afiseaza  numarul de comunitati");
        System.out.println("6 - afiseaza cea mai sociabila comunitate");
        System.out.println("7 - afiseaza toti prietenii unui utilizator");
        System.out.println("8 - afiseaza toti prietenii unui utilizator creati intro anumita luna");
        System.out.println("9 - adauga un mesaj");
        System.out.println("10 - afiseaza toti utilizatori");
        System.out.println("11 - afiseaza toate prieteniile");
        System.out.println("12 - afiseaza toate mesajele");
        System.out.println("13 - afiseaza toate conversatiile a doi utilizatori cronologic");
        System.out.println("14 - afiseaza toate cererile de prietenie");
        System.out.println("15 - adauga o cerere de prietenie");
        System.out.println("16 - accepta cererea de prietenie dintre 2 utilizaatori");
        System.out.println("17 - refuza cererea de prietenie dintre 2 utilizaatori");
        System.out.println("menu - reafiseaza meniul");
        System.out.println("stop - opreste programul");
        System.out.println();
    }

    /**
     * Constructor UI
     *
     * @param service de tip SuperService
     */
    public UI(SuperService service) {
        this.service = service;
    }


    /**
     * Metoda realizeaza gestiunea ui-ului
     *
     * @return 0 cand se incheie
     */
    public int ui() {
        menu();

        while (true) {
            System.out.println();
            System.out.print("Scrie urmatoarea comanda: ");
            String n = console.next();
            switch (n) {
                case "menu":
                    menu();
                    break;
                case "1":
                    try {
                        addUtilizator();
                    } catch (ValidationException v) {
                        System.out.println(v.getMessage());
                    } catch (RepositoryException r) {
                        System.out.println(r.getMessage());
                    }
                    break;
                case "2":
                    try {
                        deleteUtilizator();
                    } catch (ValidationException v) {
                        System.out.println(v.getMessage());
                    } catch (RepositoryException r) {
                        System.out.println(r.getMessage());
                    }
                    break;
                case "3":
                    try {
                        addPrietenie();
                    } catch (ValidationException v) {
                        System.out.println(v.getMessage());
                    } catch (RepositoryException r) {
                        System.out.println(r.getMessage());
                    }
                    break;
                case "4":
                    try {
                        deletePrietenie();
                    } catch (ValidationException v) {
                        System.out.println(v.getMessage());
                    } catch (RepositoryException r) {
                        System.out.println(r.getMessage());
                    }
                    break;
                case "5":
                    getComponeneteConexe();
                    break;
                case "6":
                    getLongestPath();
                    break;
                case "7":
                    try {
                        afisarePrieteniiUtilizatorului();
                    } catch (ValidationException v) {
                        System.out.println(v.getMessage());
                    } catch (RepositoryException r) {
                        System.out.println(r.getMessage());
                    }
                    break;
                case "8":
                    try {
                        afisarePrieteniiUtilizatoruluidupaLuna();
                    } catch (ValidationException v) {
                        System.out.println(v.getMessage());
                    } catch (RepositoryException r) {
                        System.out.println(r.getMessage());
                    }
                    break;
                case "9":
                    try {
                        addMesagge();
                    } catch (ValidationException v) {
                        System.out.println(v.getMessage());
                    } catch (RepositoryException r) {
                        System.out.println(r.getMessage());
                    }
                case "10":
                    afisareUtilizatori();
                    break;
                case "11":
                    afisarePrietenii();
                    break;
                case "12":
                    afisareMesaje();
                    break;
                case "13":
                    try {
                        afiseazaConversatii();
                    } catch (ValidationException v) {
                        System.out.println(v.getMessage());
                    } catch (RepositoryException r) {
                        System.out.println(r.getMessage());
                    }
                    break;
                case "14":
                    afisareFriendRequest();
                    break;
                case "15":
                    try {
                        addFriendRequest();
                    } catch (ValidationException v) {
                        System.out.println(v.getMessage());
                    } catch (RepositoryException r) {
                        System.out.println(r.getMessage());
                    }
                    break;
                case "16":
                    acceptFriendRequest();
                    break;
                case "17":
                    declineFriendRequest();
                    break;
                case "stop":
                    return 0;
            }
        }

    }

    /**
     * Metoda afisaza componentele conexe din grupul de prieteni ai utilizaotirlor
     */
    private void getComponeneteConexe() {
        int c = service.getComponenteConexe();
        System.out.print("Exista ");
        if (c == 1)
            System.out.println("un singur component conex");
        else
            System.out.println(c + " componenete conexe");
    }

    /**
     * Metoda adiseaza utilizatori cu cel mai lung lant de prieteni
     */
    private void getLongestPath() {
        int[] c = service.getLongestPath();
        for (int i = 0; i < c.length; i++) c[i]++;
        for (int i = 0; i < c.length; i++) {
            if (c[i] != 0)
                System.out.print(c[i] + " ");
        }
        System.out.println();
    }

    /**
     * Metoda realizeaza interactiunea cu utilizatorul pentru adaugarea unui utilizator
     *
     * @throws ValidationException daca utilizatorul nu este valid
     * @throws RepositoryException daca exista deja un element cu acelasi id
     */
    private void addUtilizator() throws ValidationException, RepositoryException {
        System.out.println("Introdu numele utilizatorului");
        String nume = console.next();
        System.out.println("Introdu prenumele utilizatorului");
        String prenume = console.next();

        service.addUtilizator(nume, prenume);

    }

    /**
     * Metoda realizeaza interactiunea cu utilizatorul pentru stergerea unui utilizator
     *
     * @throws ValidationException ID-ul nu reprezinta un long
     * @throws RepositoryException nu exista un utilizator cu acest ID
     */
    private void deleteUtilizator() throws ValidationException, RepositoryException {
        System.out.println("Introdu id-ul utilizatorului care sa fie sters");
        String id = console.next();

        if (!IdValidation.isLong(id))
            throw new ValidationException("\tId-ul trebuie sa fie un numar intreg\n");
        Long idd = Long.parseLong(id);

        service.deleteUtilizator(idd);
    }

    /**
     * Metoda realizeaza interactiunea cu utilizatorul pentru adaugarea unei prietenii
     *
     * @throws ValidationException cele doua id-uri se pot converti in longuri,
     * @throws RepositoryException daca id-urile nu reprezinta id-urile unor utilizatori existenti,
     *                             daca prietenia deja exista in repository
     */
    private void addPrietenie() throws ValidationException, RepositoryException {
        System.out.println("Introdu id-ul primului prieten");
        String id1 = console.next();
        System.out.println("Introdu id-ul celui deal doilea prieten");
        String id2 = console.next();

        service.iduriIntregi(id1, id2);
        Long idd1 = Long.parseLong(id1);
        Long idd2 = Long.parseLong(id2);

        service.addPrietenie(idd1, idd2);

    }

    /**
     * Metoda realizeaza interactiunea cu utilizatorul pentru adaugarea unei cereri de prietenie
     *
     * @throws ValidationException cele doua id-uri se pot converti in longuri,
     * @throws RepositoryException daca id-urile nu reprezinta id-urile unor utilizatori existenti,
     *                             daca prietenia deja exista in repository
     */
    private void addFriendRequest() throws ValidationException, RepositoryException {
        System.out.println("Introdu id-ul celui ce trimite cererea de prietenie");
        String id1 = console.next();
        System.out.println("Introdu id-ul celui ce primeste cererea de prietenie");
        String id2 = console.next();

        service.iduriIntregi(id1, id2);
        Long idd1 = Long.parseLong(id1);
        Long idd2 = Long.parseLong(id2);

        service.addFriendRequest(idd1, idd2);

    }

    /**
     * Metoda realizeaza interactiunea cu utilizatorul pentru acceptarea unei cereri de prietenie
     *
     * @throws ValidationException cele doua id-uri se pot converti in longuri,
     * @throws RepositoryException daca id-urile nu reprezinta id-urile unor utilizatori existenti,
     *                             daca cererea de prietenie nu exista in repository
     */
    private void acceptFriendRequest() throws ValidationException, RepositoryException {
        System.out.println("Introdu id-ul primului utilizator");
        String id1 = console.next();
        System.out.println("Introdu id-ul celuilalt utilizator");
        String id2 = console.next();

        service.iduriIntregi(id1, id2);
        Long idd1 = Long.parseLong(id1);
        Long idd2 = Long.parseLong(id2);

        //service.acceptFriendRequest(idd1, idd2);
    }

    /**
     * Metoda realizeaza interactiunea cu utilizatorul pentru refuzarea unei cereri de prietenie
     *
     * @throws ValidationException cele doua id-uri se pot converti in longuri,
     * @throws RepositoryException daca id-urile nu reprezinta id-urile unor utilizatori existenti,
     *                             daca cererea de prietenie nu exista in repository
     */
    private void declineFriendRequest() throws ValidationException, RepositoryException {
        System.out.println("Introdu id-ul primului utilizator");
        String id1 = console.next();
        System.out.println("Introdu id-ul celuilalt utilizator");
        String id2 = console.next();

        service.iduriIntregi(id1, id2);
        Long idd1 = Long.parseLong(id1);
        Long idd2 = Long.parseLong(id2);

        service.declineFriendRequest(idd1, idd2);
    }

    /**
     * Metoda realizeaza interactiunea cu utilizatorul pentru stergerea unei prietenii
     *
     * @throws ValidationException cele doua id-uri nu se pot converti in longuri
     * @throws RepositoryException daca id-urile nu reprezint id-urile unor utilizatori existenit,
     *                             daca acestia nu erau prieteni
     */
    private void deletePrietenie() throws ValidationException, RepositoryException {
        System.out.println("Introdu id-ul primului prieten");
        String id1 = console.next();
        System.out.println("Introdu id-ul celui deal doilea prieten");
        String id2 = console.next();

        service.iduriIntregi(id1, id2);
        Long idd1 = Long.parseLong(id1);
        Long idd2 = Long.parseLong(id2);
        service.deletePrietenie(idd1, idd2);
    }

    /**
     * Metoda adiseaza numele, prenumele si data fiecarui prieten ale unui utlizator
     *
     * @throws ValidationException id-ul nu se pot converti in long
     * @throws RepositoryException daca id-ul nu reprezinta id-ul unui utilizator existent
     */
    private void afisarePrieteniiUtilizatorului() throws ValidationException, RepositoryException {
        System.out.print("Introdu id-ul utilizatorului: ");
        String id = console.next();
        System.out.println();

        if (!IdValidation.isLong(id))
            throw new ValidationException("\tId-ul trebuie sa fie un numar intreg\n");
        Long idd = Long.parseLong(id);

        List<String> list = service.getPrietenii(idd);

        list.forEach(System.out::println);
    }

    /**
     * Metoda adiseaza numele, prenumele si data fiecarui prieten ale unui utlizator
     *
     * @throws ValidationException id-ul nu se pot converti in long
     * @throws RepositoryException daca id-ul nu reprezinta id-ul unui utilizator existent
     */
    private void afisarePrieteniiUtilizatoruluidupaLuna() throws ValidationException, RepositoryException {
        System.out.print("Introdu id-ul utilizatorului: ");
        String id = console.next();
        System.out.print("Introdu luna de cand trebuie sa fie prieteni: ");
        String month = console.next();

        String mesaj = "";

        if (!IdValidation.isLong(id))
            mesaj = mesaj + "\tId-ul trebuie sa fie un numar intreg\n";
        Long idd = Long.parseLong(id);

        int luna = 0;
        try {
            luna = Integer.parseInt(month);
        } catch (NumberFormatException e) {
            mesaj = mesaj + "\tLuna trebuie sa fie un numar intreg\n";
        }

        if (mesaj != "") throw new ValidationException(mesaj);

        if (luna > 12 || luna < 1)
            throw new ValidationException("\tLuna trebuie sa fie un numar intre 1 si 12\n");

        Month m = Month.of(luna);
        List<String> list = service.getPrietenii(idd, m);

        list.forEach(System.out::println);
    }

    private void addMesagge() throws ValidationException, RepositoryException {
        System.out.println("introdu id-ul utilizatorului care trimite mesajul");
        String from = console.next();

        if (!IdValidation.isLong(from))
            throw new ValidationException("\tId-ul trebuie sa fie un numar intreg\n");
        Long idd = Long.parseLong(from);

        System.out.println("Introdu la cate persoane va trimite mesajul");
        String n = console.next();
        if (!IdValidation.isInt(from))
            throw new ValidationException("\tNumarul trebuie sa fie intreg\n");
        int nn = Integer.parseInt(n);

        List<Long> list = new ArrayList<Long>();

        for (int i = 0; i < nn; i++) {
            System.out.println("Introdu urmatoarea persoana care primeste mesajul");
            String to = console.next();
            if (!IdValidation.isLong(to))
                throw new ValidationException("\tId-ul trebuie sa fie un numar intreg\n");
            Long too = Long.parseLong(to);

            list.add(too);

        }

        System.out.println("Introdu mesajul");
        String mesaj = console.next();

        service.addMessage(idd, list, mesaj);
    }

    /**
     * Metoda realizeaza interactiunea cu utilizatorul pentru afisarea conversatiilor a 2 utilizatori
     *
     * @throws ValidationException cele doua id-uri nu se pot converti in longuri
     * @throws RepositoryException daca id-urile nu reprezint id-urile unor utilizatori existenit,
     *                             daca acestia nu erau prieteni
     */
    private void afiseazaConversatii() throws ValidationException, RepositoryException {
        System.out.println("Introdu id-ul primului utilizator");
        String id1 = console.next();
        System.out.println("Introdu id-ul celui deal doilea utilizator");
        String id2 = console.next();

        service.iduriIntregi(id1, id2);
        Long idd1 = Long.parseLong(id1);
        Long idd2 = Long.parseLong(id2);
        service.getConversatii(idd1, idd2).forEach(x -> System.out.println(x.getFrom().getId() + ": " + x.getMessage()));
    }

    /**
     * Metoda afiseaza utilizatori
     */
    private void afisareUtilizatori() {
        service.getUsers().forEach(System.out::println);
    }

    /**
     * Metoda afiseaza Prieteniile
     */
    private void afisarePrietenii() {
        service.getFriendships().forEach(System.out::println);
    }

    /**
     * Metoda afiseaza Mesajele
     */
    private void afisareMesaje() {
        service.getMessages().forEach(System.out::println);
    }

    /**
     * Metoda afiseaza cererile de prietenie
     */
    private void afisareFriendRequest() {
        service.getFriendRequests().forEach(System.out::println);
    }
}
