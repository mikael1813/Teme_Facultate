package services;



import domain.Game;
import domain.Utilizator;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IService extends Remote {
    public Utilizator login(Utilizator u, IAppObserver client) throws AppException, RemoteException;

    void logout(Utilizator utilizator) throws AppException, RemoteException;
    public void start(Utilizator u, IAppObserver client) throws AppException, RemoteException;

    void send(Game game, String text, String text1, Utilizator utilizator) throws AppException, RemoteException;
}
