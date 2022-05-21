package client.gui;

import domain.Game;
import domain.Utilizator;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.AppException;
import services.IAppObserver;


import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class StartController {
    private ClientCtrl ctrl;
    Utilizator utilizator;
    @FXML
    Button btn;


    public void start(MouseEvent mouseEvent) throws RemoteException, AppException {
        ctrl.search(utilizator);
    }

    public void setService(ClientCtrl ctrl, Utilizator u) {
        utilizator = u;
        this.ctrl = ctrl;
    }


    public void close() {
        Stage stage1 = (Stage) btn.getScene().getWindow();
        stage1.close();
    }
}
