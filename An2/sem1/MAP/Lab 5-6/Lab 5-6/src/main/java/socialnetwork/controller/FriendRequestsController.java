package socialnetwork.controller;

//import com.sun.webkit.network.Util;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import socialnetwork.domain.Enums.Stare;
import socialnetwork.domain.FriendRequest;
import socialnetwork.domain.Utilizator;
import socialnetwork.service.SuperService;

import java.util.ArrayList;
import java.util.List;

public class FriendRequestsController {
    SuperService service;
    ObservableList<FriendRequest> model = FXCollections.observableArrayList();
    Utilizator u;

    @FXML
    TableView<FriendRequest> tableView;
    @FXML
    TableColumn<FriendRequest, String> tableColumnFName;
    @FXML
    TableColumn<FriendRequest, String> tableColumnLName;
    @FXML
    TableColumn<FriendRequest, String> tableColumnState;
    @FXML
    TableColumn<FriendRequest, String> tableColumnDate;
    @FXML
    TableColumn<FriendRequest, Long> tableColumnId;
    @FXML
    TextField textName;

    public void setService(SuperService service, Utilizator u) {
        this.service = service;
        this.u = u;
        initModel();
        textName.setText(u.getFirstName() + " " + u.getLastName());
    }

    @FXML
    public void initialize() {
        tableColumnId.setCellValueFactory(param -> {
            Long id1 = param.getValue().getId().getLeft().getLeft();
            Long id2 = param.getValue().getId().getLeft().getRight();

            if (id1 == u.getId()) {
                return new SimpleObjectProperty<>(id2);
            } else
                return new SimpleObjectProperty<>(id1);
        });

        tableColumnFName.setCellValueFactory(param -> {
            Utilizator u1 = service.getUtilizator(param.getValue().getId().getLeft().getLeft());
            Utilizator u2 = service.getUtilizator(param.getValue().getId().getLeft().getRight());

            if (u1.getId() == u.getId()) {
                return new SimpleObjectProperty<>(u2.getFirstName());
            } else {
                return new SimpleObjectProperty<>(u1.getFirstName());
            }
        });

        tableColumnLName.setCellValueFactory(param -> {
            Utilizator u1 = service.getUtilizator(param.getValue().getId().getLeft().getLeft());
            Utilizator u2 = service.getUtilizator(param.getValue().getId().getLeft().getRight());

            if (u1.getId() == u.getId()) {
                return new SimpleObjectProperty<>(u2.getLastName());
            } else {
                return new SimpleObjectProperty<>(u1.getLastName());
            }
        });

        tableColumnState.setCellValueFactory(new PropertyValueFactory<FriendRequest, String>("state"));

        tableColumnDate.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getId().getRight().toString()));
        tableView.setItems(model);
    }

    private void initModel() {
        List<FriendRequest> list = new ArrayList<>();

        service.getFriendRequests().forEach(x -> {
            if (x.getId().getLeft().getLeft() == u.getId())
                list.add(x);
            if (x.getId().getLeft().getRight() == u.getId())
                list.add(x);
        });

        model.setAll(list);
    }

    public void handleUndo(MouseEvent mouseEvent) {
        if(tableView.getSelectionModel().getSelectedItem() == null){
            MessageAlert.showErrorMessage(null,"Trebuie selectata o cerere de prietenie");
        }
        else{
            FriendRequest friendRequest = tableView.getSelectionModel().getSelectedItem();

            if(friendRequest.getState() != Stare.PENDING){
                MessageAlert.showErrorMessage(null,"Nu poti retrage o cerere deja revizuita");
            }
            else{
                service.deleteFriendRequest(friendRequest.getId().getLeft().getLeft(),friendRequest.getId().getLeft().getRight(),friendRequest.getId().getRight());
                initModel();
                initialize();
            }
        }
    }

    public void handleAccept(MouseEvent mouseEvent) {
        if(tableView.getSelectionModel().getSelectedItem() == null){
            MessageAlert.showErrorMessage(null,"Trebuie selectata o cerere de prietenie");
        }
        else{
            FriendRequest friendRequest = tableView.getSelectionModel().getSelectedItem();

            if(friendRequest.getState() != Stare.PENDING){
                MessageAlert.showErrorMessage(null,"Nu poti schimba o cerere deja revizuita");
            }
            else{
                service.acceptFriendRequest(friendRequest.getId().getLeft().getLeft(),friendRequest.getId().getLeft().getRight());
                initModel();
                initialize();
            }
        }
    }

    public void handleDecline(MouseEvent mouseEvent) {
        if(tableView.getSelectionModel().getSelectedItem() == null){
            MessageAlert.showErrorMessage(null,"Trebuie selectata o cerere de prietenie");
        }
        else{
            FriendRequest friendRequest = tableView.getSelectionModel().getSelectedItem();

            if(friendRequest.getState() != Stare.PENDING){
                MessageAlert.showErrorMessage(null,"Nu poti schimba o cerere deja revizuita");
            }
            else{
                service.declineFriendRequest(friendRequest.getId().getLeft().getLeft(),friendRequest.getId().getLeft().getRight());
                initModel();
                initialize();
            }
        }
    }
}
