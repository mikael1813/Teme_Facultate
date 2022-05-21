package controllers;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class PlataCuCardController {

    public void eventSubmit(MouseEvent mouseEvent) {
        ((Node) (mouseEvent.getSource())).getScene().getWindow().hide();
    }
}
