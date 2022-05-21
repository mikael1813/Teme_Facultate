package server;

import domain.Card;
import domain.Game;
import domain.Utilizator;
import repos.CardRepositoy;
import repos.UtilizatorRepository;
import services.AppException;
import services.IAppObserver;
import services.IService;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class Service implements IService {
    private CardRepositoy cardRepositoy;
    private UtilizatorRepository utilizatorRepository;
    private Map<String, IAppObserver> loggedClients = new HashMap<>();
    private List<Game> games = new ArrayList<>();

    public Service(CardRepositoy cardRepositoy, UtilizatorRepository utilizatorRepository) {
        this.cardRepositoy = cardRepositoy;
        this.utilizatorRepository = utilizatorRepository;
    }

    @Override
    public Utilizator login(Utilizator u, IAppObserver client) throws AppException, RemoteException {

        try {
            Utilizator utilizator = utilizatorRepository.findByUser(u.getUser());
            System.out.println(cardRepositoy.findAll());
            System.out.println(utilizator);
            System.out.println(u.getParola());
            if (!utilizator.getParola().equals(u.getParola())) {
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

    public List<Card> getCards() {
        return (List<Card>) cardRepositoy.findAll();
    }

    @Override
    public void setObserver(Utilizator utilizator, IAppObserver obs) throws AppException, RemoteException {
        loggedClients.replace(utilizator.getUser(), obs);
    }

    @Override
    public void receiveCard(Card card, Utilizator utilizator, Game game) throws RemoteException {

        for (Game g : games) {
            //if (g.getU1().equals(game.getU1()) && g.getU2().equals(game.getU2()) && g.getU3().equals(game.getU3())) {
            if (g.getId().equals(game.getId())) {
                Map<String, Card> map = g.getCurrentRound();
                map.put(utilizator.getUser(), card);
                if (map.size() == 4) {
                    System.out.println("DDDDDDDDDDDDDDDDDDDDDD");
                    game.setRunde(game.getRunde() + 1);
                    int red = 0;
                    List<Utilizator> users = new ArrayList<>();
                    users.add(game.getU1());
                    users.add(game.getU2());
                    users.add(game.getU3());
                    List<Card> cards = new ArrayList<>();
                    for (Utilizator us : users) {
                        cards.add(map.get(us.getUser()));
                        if (map.get(us.getUser()).getCuloare().equals("red")) {
                            red++;
                        }
                    }
                    if (red == 1) {
                        if (map.get(game.getU1()).getCuloare().equals("red"))
                            game.setCards1(game.app(game.getCards1(), cards));
                        if (map.get(game.getU2()).getCuloare().equals("red"))
                            game.setCards1(game.app(game.getCards2(), cards));
                        if (map.get(game.getU3()).getCuloare().equals("red"))
                            game.setCards1(game.app(game.getCards3(), cards));
                    }

                    if (red == 2) {
                        if (map.get(game.getU1()).getCuloare().equals("red") && map.get(game.getU2()).getCuloare().equals("red")) {
                            if (map.get(game.getU1()).getNr() > map.get(game.getU2()).getNr())
                                game.setCards1(game.app(game.getCards1(), cards));
                            else
                                game.setCards2(game.app(game.getCards2(), cards));
                        }
                        if (map.get(game.getU1()).getCuloare().equals("red") && map.get(game.getU3()).getCuloare().equals("red")) {
                            if (map.get(game.getU1()).getNr() > map.get(game.getU3()).getNr())
                                game.setCards1(game.app(game.getCards1(), cards));
                            else
                                game.setCards3(game.app(game.getCards3(), cards));
                        }
                        if (map.get(game.getU3()).getCuloare().equals("red") && map.get(game.getU2()).getCuloare().equals("red")) {
                            if (map.get(game.getU3()).getNr() > map.get(game.getU2()).getNr())
                                game.setCards3(game.app(game.getCards3(), cards));
                            else
                                game.setCards2(game.app(game.getCards2(), cards));
                        }
                    }

                    if (red == 0) {
                        if (map.get(game.getU1()).getNr() > map.get(game.getU2()).getNr() && map.get(game.getU1()).getNr() > map.get(game.getU3()).getNr())
                            game.setCards1(game.app(game.getCards1(), cards));
                        else if (map.get(game.getU3()).getNr() > map.get(game.getU2()).getNr())
                            game.setCards3(game.app(game.getCards3(), cards));
                        else
                            game.setCards2(game.app(game.getCards2(), cards));
                    }

                    if (game.getRunde() == 3) {

                    } else {
                        ExecutorService executor = Executors.newFixedThreadPool(3);
                        List<Utilizator> users2 = new ArrayList<>();
                        users2.add(game.getU1());
                        users2.add(game.getU2());
                        users2.add(game.getU3());
                        for (Utilizator us : users2) {
                            IAppObserver client = loggedClients.get(us.getUser());
                            if (client != null)
                                executor.execute(() -> {
                                    try {
                                        //System.out.println("Notifying [" + us.getId()+ "] friend ["+user.getId()+"] logged in.");
                                        client.start(game, us);
                                    } catch (AppException | RemoteException e) {
                                        System.out.println("Error notifying friend " + e);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                });
                        }

                        executor.shutdown();
                    }
                }

            }
        }
    }

    private void startGame(Game game, Utilizator utilizator) throws AppException {


        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Utilizator> users = new ArrayList<>();
        users.add(game.getU1());
        users.add(game.getU2());
        users.add(game.getU3());
        for (Utilizator us : users) {
            IAppObserver client = loggedClients.get(us.getUser());
            if (client != null)
                executor.execute(() -> {
                    try {
                        //System.out.println("Notifying [" + us.getId()+ "] friend ["+user.getId()+"] logged in.");
                        client.start(game, utilizator);
                    } catch (AppException | RemoteException e) {
                        System.out.println("Error notifying friend " + e);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        }

        executor.shutdown();
    }


}

