package client.gui;

import domain.Utilizator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.AppException;

import java.rmi.RemoteException;

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

    public void logout(MouseEvent mouseEvent) throws RemoteException, AppException {
        ctrl.logout();
    }
}
