package client.gui;


import domain.Game;
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
import java.util.List;

public class ClientCtrl extends UnicastRemoteObject implements Serializable, IAppObserver {
    IService server;
    Utilizator utilizator;
    StartController startController;
    GameController gameController;
    boolean ok =false;

    public ClientCtrl(IService server) throws RemoteException {

        this.server = server;

    }

    public Utilizator login(Utilizator utilizator) throws AppException {
        this.utilizator = utilizator;
        try {
            return server.login(utilizator, this);

        } catch (RemoteException | AppException e) {
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

    public void search(Utilizator utilizator) throws RemoteException, AppException {
        server.start(utilizator, this);
    }

    public void logout() throws RemoteException, AppException {
        server.logout(utilizator);
        startController.close();
    }

    @Override
    public void start(Game game) throws RemoteException, AppException {
        Platform.runLater(() -> {
            if(ok){
                gameController.close();
            }
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
            gameController = loader.getController();

            gameController.setService(this, utilizator, game);

            stage.show();
            ok=true;
        });
    }

    @Override
    public void end(Game game) throws RemoteException, AppException {
        Platform.runLater(() -> {
            System.out.println("end");
            gameController.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/end.fxml"));

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
            EndController endController = loader.getController();

            endController.setService(game);

            stage.show();
        });
    }

    public void send(Game game, String text, String text1) throws AppException, RemoteException {
        server.send(game,text,text1,utilizator);
    }
}
