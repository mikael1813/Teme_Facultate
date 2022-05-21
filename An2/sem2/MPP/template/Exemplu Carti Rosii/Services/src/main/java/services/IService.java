package services;

import domain.Card;
import domain.Game;
import domain.Utilizator;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IService extends Remote {
    public Utilizator login(Utilizator u, IAppObserver client) throws AppException, RemoteException;

    public void start(Utilizator u, IAppObserver client) throws AppException, RemoteException;

    public List<Card> getCards() throws AppException, RemoteException;

    void setObserver(Utilizator utilizator, IAppObserver obs) throws AppException, RemoteException;

    void receiveCard(Card card, Utilizator utilizator, Game game) throws RemoteException;
}
