package services;


import domain.Game;
import domain.Utilizator;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAppObserver extends Remote {

    void start(Game game)throws RemoteException,AppException;
    void end(Game game)throws RemoteException,AppException;
}
