package app.sevices;

import domain.Client;
import domain.Loc;
import domain.Spectacol;
import domain.Utilizator;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IService extends Remote {
    public Utilizator login(Utilizator u, IAppObserver client) throws RemoteException, AppException;

    public void updateLoc(Loc loc) throws RemoteException;

    public Iterable<Loc> getLocuri() throws RemoteException;

    public Iterable<Spectacol> getSpectacole() throws RemoteException;

    public Spectacol getSpectacolDeAzi() throws RemoteException;

    public void rezerva(Client client, Loc loc) throws RemoteException;

    public void addShow(Spectacol spectacol) throws RemoteException;

    public void updateShow(Spectacol spectacol) throws RemoteException;

    public void deleteShow(Integer id) throws RemoteException;

    public void addLoc(Loc loc) throws RemoteException;

    public void deleteLoc(Integer id) throws RemoteException;
}
