package client.gui;

import domain.Game;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class EndController {
    @FXML
    TextField txt1;
    @FXML
    TextField txt2;
    @FXML
    TextField txt3;

    public void setService(Game game) {
        if (game.getTotalPoints().get(0) > game.getTotalPoints().get(1) && game.getTotalPoints().get(0) > game.getTotalPoints().get(2)) {
            txt1.setText(game.getUsers().get(0).getUser() + " points:" + game.getTotalPoints().get(0));
            if (game.getTotalPoints().get(1) > game.getTotalPoints().get(2)) {
                txt2.setText(game.getUsers().get(1).getUser() + " points:" + game.getTotalPoints().get(1));
                txt3.setText(game.getUsers().get(2).getUser() + " points:" + game.getTotalPoints().get(2));
            } else {
                txt3.setText(game.getUsers().get(1).getUser() + " points:" + game.getTotalPoints().get(1));
                txt2.setText(game.getUsers().get(2).getUser() + " points:" + game.getTotalPoints().get(2));
            }
        }
        if (game.getTotalPoints().get(1) > game.getTotalPoints().get(2)) {
            txt1.setText(game.getUsers().get(1) + " points:" + game.getTotalPoints().get(1));
            if (game.getTotalPoints().get(0) > game.getTotalPoints().get(2)) {
                txt2.setText(game.getUsers().get(0).getUser() + " points:" + game.getTotalPoints().get(0));
                txt3.setText(game.getUsers().get(2).getUser() + " points:" + game.getTotalPoints().get(2));
            }
            else{
                txt3.setText(game.getUsers().get(0).getUser() + " points:" + game.getTotalPoints().get(0));
                txt2.setText(game.getUsers().get(2).getUser() + " points:" + game.getTotalPoints().get(2));
            }
        }
        else{
            txt1.setText(game.getUsers().get(2).getUser() + " points:" + game.getTotalPoints().get(2));
            if (game.getTotalPoints().get(1) > game.getTotalPoints().get(0)) {
                txt2.setText(game.getUsers().get(1).getUser() + " points:" + game.getTotalPoints().get(1));
                txt3.setText(game.getUsers().get(0).getUser() + " points:" + game.getTotalPoints().get(0));
            }
            else{
                txt3.setText(game.getUsers().get(1).getUser() + " points:" + game.getTotalPoints().get(1));
                txt2.setText(game.getUsers().get(0).getUser() + " points:" + game.getTotalPoints().get(0));
            }
        }
    }
}
