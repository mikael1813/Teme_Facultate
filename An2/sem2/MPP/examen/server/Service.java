package server;


import domain.Cuvant;
import domain.Game;
import domain.Tuple;
import domain.Utilizator;
import javassist.compiler.ast.Pair;
import repos.RepositoryCuvant;
import repos.RepositoryUtilizator;
import repos.database.CuvantDBRepository;
import services.AppException;
import services.IAppObserver;
import services.IService;

import javax.persistence.NoResultException;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class Service implements IService {

    private RepositoryCuvant cuvantRepository;
    private RepositoryUtilizator utilizatorRepository;
    private Map<String, IAppObserver> loggedClients = new HashMap<>();
    private List<Game> games = new ArrayList<>();
    private Map<String, Tuple> cuvinte = new HashMap<>();

    public Service(RepositoryCuvant cuvantRepository, RepositoryUtilizator utilizatorRepository) {
        this.cuvantRepository = cuvantRepository;
        this.utilizatorRepository = utilizatorRepository;
    }

    @Override
    public Utilizator login(Utilizator u, IAppObserver client) throws AppException, RemoteException {

        try {
            Utilizator utilizator = utilizatorRepository.findBy(u.getUser(), u.getParola());
            System.out.println(cuvantRepository.findAll());
            System.out.println(utilizator);
            System.out.println(u.getParola());
            if (utilizator.getParola() == null || utilizator.getUser() == null) {
                throw new AppException("Wrong Password");
            }
            if (loggedClients.get(utilizator.getUser()) != null)
                throw new AppException("User already logged in.");
            loggedClients.put(utilizator.getUser(), client);
            return utilizator;
        } catch (NoResultException e) {
            throw new AppException("Wrong username");
        }
    }

    @Override
    public void start(Utilizator u, IAppObserver client) throws AppException, RemoteException {
        System.out.println(games);
        AtomicBoolean free = new AtomicBoolean(false);
        games.forEach(x -> {
            if (x.free()) {
                free.set(true);
                x.add(u);
                if (x.full()) {
                    try {
                        startGame(x, u);
                    } catch (AppException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        if (!free.get()) {
            Game game = new Game();
            game.setId(1);
            game.add(u);
            games.add(game);
        }
    }

    private void endGame(Game game){
        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Utilizator> users = game.getUsers();
        for (Utilizator us : users) {
            IAppObserver client = loggedClients.get(us.getUser());
            if (client != null)
                executor.execute(() -> {
                    try {
                        //System.out.println("Notifying [" + us.getId()+ "] friend ["+user.getId()+"] logged in.");
                        client.end(game);
                    } catch (AppException | RemoteException e) {
                        System.out.println("Error notifying friend " + e);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        }

    }

    @Override
    public void send(Game game, String text, String text1, Utilizator utilizator) throws AppException,RemoteException {
        Tuple tuple = new Tuple(text1, text);
        String carac1 = "", carac2 = "";
        if (cuvinte.get(utilizator.getUser()) == null) {
            cuvinte.put(utilizator.getUser(), tuple);
            if (cuvinte.size() == 3) {
                game.setRound(game.getRound() + 1);
                for (Cuvant cuv : cuvantRepository.findAll()) {
                    if (cuv.getCuvant().equals(game.getCuvant())) {
                        carac1 = cuv.getCarac1();
                        carac2 = cuv.getCarac2();
                    }
                }
                for (Utilizator us : utilizatorRepository.findAll()) {
                    int points = 0;
                    tuple = cuvinte.get(us.getUser());
                    String carac = tuple.getCuv1();
                    int apare = 0;
                    if (carac==carac1 || carac==carac2) {
                        for (Utilizator us2 : utilizatorRepository.findAll()) {
                            if (!us2.getUser().equals(us.getUser()) && (cuvinte.get(us2.getUser()).getCuv1().equals(carac) || cuvinte.get(us2.getUser()).getCuv2().equals(carac)))
                                apare++;
                        }
                    }
                    if (apare == 0) points = points + 5;
                    if (apare == 1) points = points + 2;
                    if (apare == 2) points += 1;
                    carac = tuple.getCuv2();
                    apare = 0;
                    if (carac==carac1 || carac==carac2) {
                        for (Utilizator us2 : utilizatorRepository.findAll()) {
                            if (!us2.getUser().equals(us.getUser()) && (cuvinte.get(us2.getUser()).getCuv1().equals(carac) || cuvinte.get(us2.getUser()).getCuv2().equals(carac)))
                                apare++;
                        }
                    }
                    if (apare == 0) points = points + 5;
                    if (apare == 1) points = points + 2;
                    if (apare == 2) points += 1;
                    for (int i = 0; i < 3; i++) {
                        if (game.getUsers().get(i).getUser().equals(us.getUser())) {
                            game.getLastPoints().set(i, points);
                            game.getTotalPoints().set(i, game.getTotalPoints().get(i));
                        }
                    }

                }
                cuvinte.clear();
                if(game.getRound()==3){
                    endGame(game);
                }
                else{
                    startGame(game,utilizator);
                }
            }
        }


    }

    private void startGame(Game game, Utilizator utilizator) throws AppException {
        List<Cuvant> cuvants = (List<Cuvant>) cuvantRepository.findAll();
        Random random = new Random();
        int randomInt = random.nextInt(cuvants.size());
        game.setCuvant(cuvants.get(randomInt).getCuvant());
        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Utilizator> users = game.getUsers();
        for (Utilizator us : users) {
            IAppObserver client = loggedClients.get(us.getUser());
            if (client != null)
                executor.execute(() -> {
                    try {
                        //System.out.println("Notifying [" + us.getId()+ "] friend ["+user.getId()+"] logged in.");
                        client.start(game);
                    } catch (AppException | RemoteException e) {
                        System.out.println("Error notifying friend " + e);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        }

        executor.shutdown();
    }

    @Override
    public void logout(Utilizator utilizator) throws AppException, RemoteException {
        IAppObserver localClient = loggedClients.remove(utilizator.getUser());
        if (localClient == null)
            throw new AppException("User " + utilizator.getUser() + " is not logged in.");
    }
}

