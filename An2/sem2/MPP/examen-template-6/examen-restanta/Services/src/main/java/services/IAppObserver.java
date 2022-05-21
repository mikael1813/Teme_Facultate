package services;


import domain.Game;
import domain.Round;
import domain.Utilizator;

import java.io.IOException;
import java.rmi.Remote;

public interface IAppObserver extends Remote {

    void start(Game game, Round round, Utilizator utilizator)throws AppException, IOException;
}
