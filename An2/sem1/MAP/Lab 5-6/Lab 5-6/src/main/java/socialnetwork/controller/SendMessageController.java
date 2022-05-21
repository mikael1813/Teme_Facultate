package socialnetwork.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import socialnetwork.domain.Utilizator;
import socialnetwork.service.SuperService;

import java.util.ArrayList;
import java.util.List;

public class SendMessageController {

    SuperService service;
    ObservableList<Utilizator> model = FXCollections.observableArrayList();
    Utilizator u;
    List<Utilizator> list;

    @FXML
    TableView<Utilizator> tableView;
    @FXML
    TableColumn<Utilizator, Long> tableColumnId;
    @FXML
    TableColumn<Utilizator, String> tableColumnFName;
    @FXML
    TableColumn<Utilizator, String> tableColumnLName;
    @FXML
    Label label;
    @FXML
    TextField textField;

    public void setService(SuperService service, Utilizator u) {
        this.u = u;
        this.service = service;
        //service.addObserver(this);
        initModel();
        list = new ArrayList<>();
    }

    @FXML
    public void initialize() {
        //TODO
        tableColumnId.setCellValueFactory(new PropertyValueFactory<Utilizator, Long>("Id"));
        tableColumnFName.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("firstName"));
        tableColumnLName.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("lastName"));
        tableView.setItems(model);


    }

    private void initModel() {
        List<Utilizator> lis = new ArrayList<>();

        service.getUsers().forEach(x -> {
            if (!x.equals(u))
                lis.add(x);
        });

        model.setAll(lis);
    }

    public void handleUserClicked(MouseEvent mouseEvent) {
        Utilizator u2 = tableView.getSelectionModel().getSelectedItem();

        if (list.contains(u2))
            list.remove(u2);
        else
            list.add(u2);

        buildLabel();
    }

    private void buildLabel(){
        String users = "";
        for (Utilizator x : list) {
            users = users + x.getId().toString() + " ";
        }

        label.setText(users);
    }

    public void handleSelectAll(MouseEvent mouseEvent) {
        List<Utilizator> lis = new ArrayList<>();

        service.getUsers().forEach(x -> {
            if (!x.equals(u))
                lis.add(x);
        });

        list = new ArrayList<>();

        lis.forEach(x->{
            list.add(x);
        });

        buildLabel();
    }

    public void handleDeleteAll(MouseEvent mouseEvent) {
        list = new ArrayList<>();

        buildLabel();
    }

    public void handleSend(MouseEvent mouseEvent) {
        if(list.size() == 0){
            MessageAlert.showErrorMessage(null,"Nu ai selectat niciun destinatar");
        }
        else{
            if(textField.getText() == ""){
                MessageAlert.showErrorMessage(null,"Nu ai scris niciun mesaj");
            }
            else{
                List<Long> l = new ArrayList<>();

                list.forEach(x->{
                    l.add(x.getId());
                });

                service.addMessage(u.getId(),l,textField.getText());

                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Mesaj trimis cu succes!","");
            }
        }
    }
}
