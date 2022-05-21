package client.gui;

import domain.Card;
import domain.Game;
import domain.Utilizator;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
import java.util.List;

public class ClientCtrl extends UnicastRemoteObject implements Serializable, IAppObserver {
    IService server;
    Utilizator utilizator;
    StartController startController = new StartController();
    int rounds;

    public ClientCtrl(IService server) throws RemoteException {

        this.server = server;
        rounds=0;
    }

    public List<Card> getCards() throws AppException, RemoteException {
        return server.getCards();
    }

    public Utilizator login(Utilizator utilizator) throws AppException {
        this.utilizator = utilizator;
        try {
            return server.login(utilizator, this);

        } catch (RemoteException e) {
            throw new AppException("Error logging " + e);
        }
    }

    public void show() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/start.fxml"));

        AnchorPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setTitle("Start");
        stage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(root);
        stage.setScene(scene);

        startController = loader.getController();

        startController.setService(this, utilizator);

        stage.show();
    }


    @Override
    public void start(Game game, Utilizator u) throws AppException, IOException {
        System.out.println(" e in start");
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
                gameController.setService(this, u, game);
            } catch (AppException e) {
                e.printStackTrace();
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            stage.show();
        });


    }

    public void search(Utilizator utilizator) {
        try {
            server.start(utilizator, this);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AppException e) {
            e.printStackTrace();
        }
    }


    public void sendCard(Card card, Utilizator utilizator, Game game) throws RemoteException {
        server.receiveCard(card,utilizator,game);
    }
}
