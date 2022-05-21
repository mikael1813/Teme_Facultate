package client.gui;

import domain.Utilizator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.AppException;

import java.rmi.RemoteException;
import java.util.regex.Pattern;

public class StartController {
    private ClientCtrl ctrl;
    Utilizator utilizator;
    @FXML
    Button btn;
    @FXML
    TextField txt1;
    @FXML
    TextField txt2;


    public void start(MouseEvent mouseEvent) throws RemoteException, AppException {
        String str = txt2.getText();
        boolean ok =true;
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char ch = charArray[i];
            if (!(ch == '*' || ch == '-' || ch == '~' || ch == '^')) {
                ok=false;
            }
        }
        if(ok){
            ctrl.search(utilizator,str);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Wrong input");
            alert.setContentText("Trebuie sa folosesti doar caracterele precizate");
            alert.showAndWait();
        }

    }

    public void setService(ClientCtrl ctrl, Utilizator u) {
        utilizator = u;
        this.ctrl = ctrl;
        txt1.setText("*,-,~,^");
    }


    public void close() {
        Stage stage1 = (Stage) btn.getScene().getWindow();
        stage1.close();
    }

    public void logout(MouseEvent mouseEvent) throws AppException {
        ctrl.logout(utilizator);
        Stage stage1 = (Stage) btn.getScene().getWindow();
        stage1.close();
    }
}
