package client.gui;

import domain.Client;
import domain.Loc;
import domain.Spectacol;
import domain.Utilizator;
import services.AppException;
import services.IAppObserver;
import services.IService;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientCtrl extends UnicastRemoteObject implements Serializable, IAppObserver {
    IService server;

    public ClientCtrl(IService server) throws RemoteException{

        this.server = server;
    }


    public Utilizator login(Utilizator utilizator) throws AppException {
        try {
            return server.login(utilizator,this);

        } catch (RemoteException e) {
            throw new AppException("Error logging "+e);
        }
    }

    public Iterable<Loc> getLocuri() throws AppException {
        try {
            Iterable<Loc> locs = server.getLocuri();
            return server.getLocuri();

        } catch (RemoteException e) {
            throw new AppException("Error getLocuri "+e);
        }
    }

    public Iterable<Spectacol> getSpectacole()throws AppException {
        try {
            return server.getSpectacole();

        } catch (RemoteException e) {
            throw new AppException("Error getSpectacole "+e);
        }
    }

    public void addShow(Spectacol spectacol) throws AppException{
        try {
            server.addShow(spectacol);

        } catch (RemoteException e) {
            throw new AppException("Error addShow "+e);
        }
    }

    public void updateShow(Spectacol spectacol) throws AppException{
        try {
            server.updateShow(spectacol);

        } catch (RemoteException e) {
            throw new AppException("Error updateShow "+e);
        }
    }

    public void deleteShow(Integer id) throws AppException{
        try {
            server.deleteShow(id);

        } catch (RemoteException e) {
            throw new AppException("Error deleteShow "+e);
        }
    }

    public void addLoc(Loc loc) throws AppException{
        try {
            server.addLoc(loc);

        } catch (RemoteException e) {
            throw new AppException("Error addLoc "+e);
        }
    }

    public void updateLoc(Loc loc) throws AppException{
        try {
            server.updateLoc(loc);

        } catch (RemoteException e) {
            throw new AppException("Error updateLoc "+e);
        }
    }

    public void deleteLoc(Integer id) throws AppException{
        try {
            server.deleteLoc(id);

        } catch (RemoteException e) {
            throw new AppException("Error deleteLoc "+e);
        }
    }

    public void rezerva(Client client, Loc x) throws AppException{
        try {
            server.rezerva(client,x);

        } catch (RemoteException e) {
            throw new AppException("Error rezerva "+e);
        }
    }

    public Spectacol getSpectacolDeAzi() throws AppException{
        try {
            return server.getSpectacolDeAzi();

        } catch (RemoteException e) {
            throw new AppException("Error getSpectacolDeAzi "+e);
        }
    }
}
