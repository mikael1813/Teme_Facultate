package services;


import domain.Game;
import domain.Round;
import domain.Utilizator;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IService extends Remote {
    public Utilizator login(Utilizator u, IAppObserver client) throws AppException, RemoteException;
    public void logout(Utilizator u, IAppObserver client) throws AppException, RemoteException;


    void start(Utilizator utilizator, String str,IAppObserver client)throws AppException, RemoteException;

    public Iterable<Utilizator> getUsers()throws AppException, RemoteException;

    void receive(Game game, Round round, String str, String str2, Utilizator u)throws AppException, RemoteException;
}
