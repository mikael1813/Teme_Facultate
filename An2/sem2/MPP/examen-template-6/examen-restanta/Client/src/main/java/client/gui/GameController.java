package client.gui;

import domain.Game;
import domain.Round;
import domain.Utilizator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import services.AppException;

import java.rmi.RemoteException;
import java.util.List;


public class GameController {
    @FXML
    Label label1;
    @FXML
    Label label2;
    @FXML
    Label label3;
    @FXML
    TextField txt;
    @FXML
    TextField txt1;
    @FXML
    TextField txt2;
    @FXML
    TextField txt3;
    ClientCtrl ctrl;
    Game game;
    Utilizator u;
    Round round;

    public void setService(ClientCtrl ctrl, Game game, Utilizator u, Round round) throws RemoteException, AppException {
        this.ctrl = ctrl;
        this.game = game;
        this.u = u;
        this.round = round;
        String user1 = "";
        String user2 = "";
        String user3 = "";
        List<Utilizator> list = (List<Utilizator>) ctrl.getUsers();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == game.getU1()) {
                user1 = list.get(i).getUser();
            }
            if (list.get(i).getId() == game.getU2()) {
                user2 = list.get(i).getUser();
            }
            if (list.get(i).getId() == game.getU3()) {
                user3 = list.get(i).getUser();
            }
        }
        label1.setText(user1);
        label2.setText(user2);
        label3.setText(user3);
        char[] charArray = game.getCuv1().toCharArray();
        String s = "";
        for (int i = 0; i < charArray.length; i++) {
            s = s + "_ ";
        }
        txt1.setText(s);
        charArray = game.getCuv2().toCharArray();
        s = "";
        for (int i = 0; i < charArray.length; i++) {
            s = s + "_ ";
        }
        txt2.setText(s);
        charArray = game.getCuv3().toCharArray();
        s = "";
        for (int i = 0; i < charArray.length; i++) {
            s = s + "_ ";
        }
        txt3.setText(s);
    }

    public void send(MouseEvent mouseEvent) throws RemoteException, AppException {
        String str="", str2="";
        int id=0;
        if (game.getU1() == u.getId()) {
            str = game.getCuv1();
            id = 1;
        }
        if (game.getU2() == u.getId()) {
            str = game.getCuv2();
            id = 2;
        }
        if (game.getU3() == u.getId()) {
            str = game.getCuv3();
            id = 3;
        }
        if (id == Integer.parseInt((txt.getText()))) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("MPP chat");
            alert.setHeaderText("Error");
            alert.setContentText("Nu iti poti ghici propriul cuvant");
            alert.showAndWait();
        } else {
            if (Integer.parseInt(txt.getText()) == 1) {
                str2 = txt1.getText();
            }
            if (Integer.parseInt(txt.getText()) == 2) {
                str2 = txt2.getText();
            }
            if (Integer.parseInt(txt.getText()) == 3) {
                str2 = txt3.getText();
            }
            ctrl.send(game,round,str,str2,u);
        }

    }
}
