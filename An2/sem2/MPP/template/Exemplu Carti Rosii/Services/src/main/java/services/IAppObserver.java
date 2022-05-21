package services;

import domain.Game;
import domain.Utilizator;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IAppObserver extends Remote {
    public void start(Game game,Utilizator utilizator) throws AppException, IOException;
}
