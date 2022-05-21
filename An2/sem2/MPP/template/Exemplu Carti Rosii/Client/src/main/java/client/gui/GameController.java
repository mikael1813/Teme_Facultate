package client.gui;

import domain.Card;
import domain.Game;
import domain.Utilizator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import services.AppException;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    private ClientCtrl ctrl;
    Utilizator utilizator;
    Game game;
    ObservableList<Card> model = FXCollections.observableArrayList();
    ObservableList<Card> model1 = FXCollections.observableArrayList();
    ObservableList<Card> model2 = FXCollections.observableArrayList();
    ObservableList<Card> model3 = FXCollections.observableArrayList();

    @FXML
    TextField txt1;
    @FXML
    TextField txt2;
    @FXML
    TextField txt3;
    @FXML
    TableColumn<Card, String> culoare1;
    @FXML
    TableColumn<Card, String> culoare2;
    @FXML
    TableColumn<Card, String> culoare3;
    @FXML
    TableColumn<Card, String> culoare;
    @FXML
    TableColumn<Card, Integer> numar1;
    @FXML
    TableColumn<Card, Integer> numar2;
    @FXML
    TableColumn<Card, Integer> numar3;
    @FXML
    TableColumn<Card, Integer> numar;
    @FXML
    TableView<Card> table;
    @FXML
    TableView<Card> table1;
    @FXML
    TableView<Card> table2;
    @FXML
    TableView<Card> table3;

    public void setService(ClientCtrl ctrl, Utilizator u, Game game) throws AppException, RemoteException {
        utilizator = u;
        this.game = game;
        this.ctrl = ctrl;
        initModels();
    }

    @FXML
    public void initialize() {
        culoare.setCellValueFactory(new PropertyValueFactory<Card, String>("culoare"));
        culoare1.setCellValueFactory(new PropertyValueFactory<Card, String>("culoare"));
        culoare2.setCellValueFactory(new PropertyValueFactory<Card, String>("culoare"));
        culoare3.setCellValueFactory(new PropertyValueFactory<Card, String>("culoare"));
        numar.setCellValueFactory(new PropertyValueFactory<Card, Integer>("nr"));
        numar1.setCellValueFactory(new PropertyValueFactory<Card, Integer>("nr"));
        numar2.setCellValueFactory(new PropertyValueFactory<Card, Integer>("nr"));
        numar3.setCellValueFactory(new PropertyValueFactory<Card, Integer>("nr"));

        table.setItems(model);
        table1.setItems(model1);
        table2.setItems(model2);
        table3.setItems(model3);
    }

    public void initModels() throws AppException, RemoteException {
        //initialize();
        java.util.List<Card> list1 = new ArrayList<>();
        game.getCards1().forEach(x->{
            list1.add(x);
        });
        model1.setAll(list1);
        txt1.setText(game.getU1().getUser());


        java.util.List<Card> list2 = new ArrayList<>();
        game.getCards2().forEach(x->{
            list2.add(x);
        });
        model2.setAll(list2);
        txt2.setText(game.getU2().getUser());

        java.util.List<Card> list3 = new ArrayList<>();
        game.getCards3().forEach(x->{
            list3.add(x);
        });
        model1.setAll(list3);
        txt3.setText(game.getU3().getUser());

        java.util.List<Card> list = new ArrayList<>();
        ctrl.getCards().forEach(x->{
            list.add(x);
        });
        model.setAll(list);
    }

    public void alege(MouseEvent mouseEvent) throws RemoteException {
        Card card = table.getSelectionModel().getSelectedItem();

        ctrl.sendCard(card,utilizator,game);
    }
}
