package client.gui;

import domain.Game;
import domain.Utilizator;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.AppException;

import java.rmi.RemoteException;


public class GameController {
    ClientCtrl ctrl;
    Utilizator utilizator;
    Game game;
    @FXML
    TextField txt1;
    @FXML
    TextField txt2;
    @FXML
    TextField txt3;
    @FXML
    TextField txt4;
    @FXML
    TextField txt5;
    public void setService(ClientCtrl ctrl,Utilizator u,Game g) {
        this.ctrl = ctrl;
        utilizator=u;
        game=g;
        if(game.getUsers().get(0).getUser().equals(utilizator.getUser())) {
            txt1.setText(game.getUsers().get(1).getUser()+" points:"+game.getLastPoints().get(1));
            txt2.setText(game.getUsers().get(2).getUser()+" points:"+game.getLastPoints().get(2));
        }
        else if (game.getUsers().get(1).getUser().equals(utilizator.getUser())){
            txt1.setText(game.getUsers().get(0).getUser()+" points:"+game.getLastPoints().get(0));
            txt2.setText(game.getUsers().get(2).getUser()+" points:"+game.getLastPoints().get(2));
        }
        else{
            txt1.setText(game.getUsers().get(1).getUser()+" points:"+game.getLastPoints().get(1));
            txt2.setText(game.getUsers().get(2).getUser()+" points:"+game.getLastPoints().get(2));
        }
        txt3.setText(game.getCuvant());

    }
    public void send(MouseEvent mouseEvent) throws AppException, RemoteException {
        ctrl.send(game,txt4.getText(),txt5.getText());
    }
    public void close(){
        Stage stage1 = (Stage) txt1.getScene().getWindow();
        stage1.close();
    }
}
