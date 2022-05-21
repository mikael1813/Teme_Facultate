package client.gui;

import domain.Client;
import domain.Utilizator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.AppException;


import java.io.IOException;

public class LoginController {
    private ClientCtrl ctrl;

    @FXML
    TextField txtUsername;
    @FXML
    TextField txtParola;

    public void setService(ClientCtrl ctrl) {
        this.ctrl = ctrl;
    }

    public void event(MouseEvent mouseEvent) throws AppException {
        Utilizator u = ctrl.login(new Utilizator(txtUsername.getText(), txtParola.getText()));
        System.out.println(u.getParola());
        if (!u.getParola().equals(txtParola.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Teatru");
            alert.setHeaderText("Authentification failure");
            alert.setContentText("Wrong password");
            alert.showAndWait();
        } else if (u == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Teatru");
            alert.setHeaderText("Authentification failure");
            alert.setContentText("Wrong username");
            alert.showAndWait();
        } else {
            if (u.getClass() == Client.class) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/views/MeniuClient.fxml"));

                AnchorPane root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Stage stage = new Stage();
                stage.setTitle("Teatru");
                stage.initModality(Modality.WINDOW_MODAL);
                Scene scene = new Scene(root);
                stage.setScene(scene);

                MeniuClientController meniuClientController = loader.getController();
                try {
                    meniuClientController.setService(ctrl, (Client) u);

                    Stage stage1 = (Stage) txtParola.getScene().getWindow();
                    stage1.close();


                    stage.show();
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Teatru");
                    alert.setHeaderText("No show today");
                    alert.setContentText("There is no available show today");
                    alert.showAndWait();
                }


            } else {
                Stage stage1 = (Stage) txtParola.getScene().getWindow();
                stage1.close();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/views/MeniuAdministrator.fxml"));

                AnchorPane root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Stage stage = new Stage();
                stage.setTitle("Teatru");
                stage.initModality(Modality.WINDOW_MODAL);
                Scene scene = new Scene(root);
                stage.setScene(scene);

                MeniuAdministratorController meniuAdministratorController = loader.getController();
                meniuAdministratorController.setService(ctrl);

                stage.show();
            }
        }
    }
}
