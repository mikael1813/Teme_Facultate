package server;


import domain.Game;
import domain.Round;
import domain.Utilizator;

import repos.GameRepository;
import repos.RoundRepository;
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

public class Service implements IService {

    private UtilizatorRepository utilizatorRepository;
    private GameRepository gameRepository;
    private RoundRepository roundRepository;
    private Map<Integer, IAppObserver> loggedClients = new HashMap<>();
    private Game game = new Game();
    private List<Round> rounds = new ArrayList<>();

    public Service(UtilizatorRepository utilizatorRepository, GameRepository gameRepository, RoundRepository roundRepository) {

        this.utilizatorRepository = utilizatorRepository;
        this.gameRepository = gameRepository;
        this.roundRepository = roundRepository;
    }

    @Override
    public Utilizator login(Utilizator u, IAppObserver client) throws AppException, RemoteException {

        try {
            Utilizator utilizator = utilizatorRepository.findByUser(u.getUser());

            System.out.println(utilizator);
            System.out.println(u.getParola());
            if (!utilizator.getParola().equals(u.getParola())) {
                throw new AppException("Wrong Password");
            }
            if (loggedClients.get(utilizator.getId()) != null)
                throw new AppException("User already logged in.");
            loggedClients.put(utilizator.getId(), client);
            return utilizator;
        } catch (NoResultException e) {
            throw new AppException("Wrong username");
        }
    }

    @Override
    public void logout(Utilizator u, IAppObserver client) throws AppException, RemoteException {
        IAppObserver localClient = loggedClients.remove(u.getId());
        if (localClient == null)
            throw new AppException("User " + u.getId() + " is not logged in.");
    }

    @Override
    public void start(Utilizator utilizator, String str, IAppObserver client) throws AppException, RemoteException {
        boolean free = false;
        if (game.free()) {
            free = true;
            game.add(utilizator.getId(), str);
            if (game.full()) {
                try {
                    List<Game> g = (List<Game>) gameRepository.findAll();
                    game.setId(g.get(g.size() - 1).getId() + 1);
                    gameRepository.save(game);
                    Round round = new Round();
                    round.setModel1(game.getCuv1());
                    round.setModel2(game.getCuv2());
                    round.setModel3(game.getCuv3());
                    round.setGame(game.getId());
                    rounds.add(round);
                    startGame(game, round, utilizator);
                } catch (AppException e) {
                    e.printStackTrace();
                }
            }
        }
        if (!free) {
            game.setId(1);
            game.add(utilizator.getId(), str);
        }
    }

    @Override
    public Iterable<Utilizator> getUsers() throws AppException, RemoteException {
        return utilizatorRepository.findAll();
    }

    @Override
    public void receive(Game game, Round round, String str, String str2, Utilizator u) throws AppException, RemoteException{
        int points=0;
        if (str.length() != str2.length()) {
            points=-2;
        }
        if(str.equals(str2)){
            points=str.length();
        }


        if (u.getId() == game.getU1()) {
            rounds.get(rounds.size() - 1).setPropunere1(str2);
            rounds.get(rounds.size() - 1).setPuncte1(points);
        }

        if (u.getId() == game.getU2()) {
            rounds.get(rounds.size() - 1).setPropunere2(str2);
            rounds.get(rounds.size() - 1).setPuncte2(points);
        }
        if (u.getId() == game.getU3()) {
            rounds.get(rounds.size() - 1).setPropunere3(str2);
            rounds.get(rounds.size() - 1).setPuncte3(points);
        }
        if(rounds.get(rounds.size()-1).full()) {
            if (rounds.size() == 3) {
                System.out.println("end");
            } else {
                roundRepository.save(rounds.get(rounds.size()-1));
                Round round1 = new Round();
                round.setModel1(game.getCuv1());
                round.setModel2(game.getCuv2());
                round.setModel3(game.getCuv3());
                round.setGame(game.getId());
                rounds.add(round1);
                startGame(game, round1, u);
            }
        }

    }

    private void startGame(Game game, Round round, Utilizator utilizator) throws AppException, RemoteException {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Integer> users = new ArrayList<>();
        users.add(game.getU1());
        users.add(game.getU2());
        users.add(game.getU3());
        for (Integer us : users) {
            IAppObserver client = loggedClients.get(us);
            if (client != null)
                executor.execute(() -> {
                    try {
                        //System.out.println("Notifying [" + us.getId()+ "] friend ["+user.getId()+"] logged in.");
                        client.start(game, round, utilizator);
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

