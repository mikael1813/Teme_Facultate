package client.gui;

import domain.Utilizator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.AppException;

public class LoginController {
    @FXML
    TextField txtUsername;
    @FXML
    TextField txtParola;

    private ClientCtrl ctrl;

    public void setService(ClientCtrl ctrl) {
        this.ctrl = ctrl;
    }

    public void event(MouseEvent mouseEvent) throws AppException {

        try {
            Utilizator u = ctrl.login(new Utilizator(txtUsername.getText(), txtParola.getText()));
            if (u != null) {
                ctrl.show();

                Stage stage1 = (Stage) txtParola.getScene().getWindow();
                stage1.close();



            }
        } catch (AppException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("MPP chat");
            alert.setHeaderText("Authentication failure");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

}