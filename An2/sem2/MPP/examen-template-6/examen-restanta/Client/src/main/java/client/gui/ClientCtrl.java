package client.gui;


import domain.Game;
import domain.Round;
import domain.Utilizator;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.AppException;
import services.IAppObserver;
import services.IService;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientCtrl extends UnicastRemoteObject implements Serializable, IAppObserver {
    IService server;
    Utilizator utilizator;
    int rounds;

    public ClientCtrl(IService server) throws RemoteException {

        this.server = server;
        rounds = 0;
    }


    public Utilizator login(Utilizator utilizator) throws AppException {
        this.utilizator = utilizator;
        try {
            return server.login(utilizator, this);

        } catch (RemoteException e) {
            throw new AppException("Error logging " + e);
        }
    }

    public void logout(Utilizator utilizator) throws AppException {
        this.utilizator = utilizator;
        try {
            server.logout(utilizator, this);

        } catch (RemoteException e) {
            throw new AppException("Error " + e);
        }
    }

    public void search(Utilizator utilizator, String str) {
        try {
            server.start(utilizator, str, this);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AppException e) {
            e.printStackTrace();
        }
    }

    public Iterable<Utilizator> getUsers() throws RemoteException, AppException {
        return server.getUsers();
    }

    @Override
    public void start(Game game, Round round, Utilizator utilizator) throws AppException, IOException {
        Platform.runLater(()->{
            System.out.println("S-a umplut in game controller");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/game.fxml"));

            AnchorPane root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Stage stage = new Stage();
            stage.setTitle("Game");
            stage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            GameController gameController = loader.getController();

            try {
                gameController.setService(this, game, utilizator,round);
            } catch (AppException e) {
                e.printStackTrace();
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            stage.show();
        });
    }

    public void send(Game game, Round round, String str, String str2, Utilizator u) throws RemoteException, AppException {
        server.receive(game,round,str,str2,u);
    }
}
