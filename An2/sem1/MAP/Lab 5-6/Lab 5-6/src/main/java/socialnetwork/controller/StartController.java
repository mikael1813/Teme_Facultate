package socialnetwork.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import socialnetwork.domain.FriendRequest;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.service.SuperService;
import socialnetwork.utils.events.UtilizatorChangeEvent;
import socialnetwork.utils.observer.Observer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class StartController implements Observer<UtilizatorChangeEvent> {
    SuperService service;
    ObservableList<Utilizator> modelUsers = FXCollections.observableArrayList();

    ObservableList<Tuple<Utilizator, LocalDateTime>> modelFriends = FXCollections.observableArrayList();


    @FXML
    TableView<Utilizator> tableViewUsers;
    @FXML
    TableColumn<Utilizator, Long> tableColumnUserId;
    @FXML
    TableColumn<Utilizator, String> tableColumnUserFName;
    @FXML
    TableColumn<Utilizator, String> tableColumnUserLName;
    @FXML
    TableView<Tuple<Utilizator, LocalDateTime>> tableViewFriends;
    @FXML
    TableColumn<Tuple<Utilizator, LocalDateTime>, Long> tableColumnFriendId;
    @FXML
    TableColumn<Tuple<Utilizator, LocalDateTime>, String> tableColumnFriendFName;
    @FXML
    TableColumn<Tuple<Utilizator, LocalDateTime>, String> tableColumnFriendLName;
    @FXML
    TableColumn<Tuple<Utilizator, LocalDateTime>, String> tableColumnFriendDate;
    @FXML
    TextField userFilterId;
    @FXML
    TextField userFilterFName;
    @FXML
    TextField userFilterLName;
    @FXML
    Button deleteFriend;
    @FXML
    Button buttonAddFriend;
    @FXML
    Button buttonSendMessage;

    Utilizator lastSelected;
    List<FriendRequest> friendRequestList;


    public void setService(SuperService service) {
        this.service = service;
        service.addObserver(this);
        initModelUsers();
    }


    @FXML
    public void initialize() {
        friendRequestList = new ArrayList<>();
        // TODO
        tableColumnUserId.setCellValueFactory(new PropertyValueFactory<Utilizator, Long>("Id"));
        tableColumnUserFName.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("firstName"));
        tableColumnUserLName.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("lastName"));
        tableViewUsers.setItems(modelUsers);

        userFilterId.textProperty().addListener(x -> handleUserFilter());
        userFilterFName.textProperty().addListener(x -> handleUserFilter());
        userFilterLName.textProperty().addListener(x -> handleUserFilter());

    }

    @FXML
    public void handleUserClicked() {
        Utilizator u1 = tableViewUsers.getSelectionModel().getSelectedItem();
        lastSelected = u1;

        modelFriends.setAll(service.getPrietenii(u1));

        tableColumnFriendId.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getLeft().getId().toString()));
        tableColumnFriendFName.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getLeft().getFirstName()));
        tableColumnFriendLName.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getLeft().getLastName()));
        tableColumnFriendDate.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getRight().toString()));
        tableViewFriends.setItems(modelFriends);
    }


    @FXML
    public void handleUserFilter() {
        String id = userFilterId.getText();
        String fname = userFilterFName.getText();
        String lname = userFilterLName.getText();

        Predicate<Utilizator> idPredicate = u -> u.getId().toString().contains(id);
        Predicate<Utilizator> fnamePredicate = u -> u.getFirstName().contains(fname);
        Predicate<Utilizator> lnamePredicate = u -> u.getLastName().contains(lname);

        modelUsers.setAll(StreamSupport.stream(service.getUsers().spliterator(), false)
                .filter(idPredicate.and(fnamePredicate).and(lnamePredicate))
                .collect(Collectors.toList()));
    }

    @FXML
    private void handleDeleteFriend() {
        Tuple<Utilizator, LocalDateTime> t = tableViewFriends.getSelectionModel().getSelectedItem();
        if (t == null) {
            MessageAlert.showErrorMessage(null, "Nu ati selectat niciun prieten");
        } else {
            Utilizator u = t.getLeft();

            service.deletePrietenie(lastSelected.getId(), u.getId());
            handleUserClicked();
        }
    }

    @FXML
    private void handleAddFriend() {
        if (lastSelected == null) {
            MessageAlert.showErrorMessage(null, "Nu ati selectat niciun utilizator");
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/Utilizatori.fxml"));

            AnchorPane root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Stage stage = new Stage();
            stage.setTitle("Users");
            stage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            stage.setScene(scene);

            UsersController usersController = loader.getController();
            usersController.setService(service, lastSelected);

            stage.show();
        }
    }


    private void initModelUsers() {
        // TODO
        Iterable<Utilizator> users = service.getUsers();
        List<Utilizator> utilizators = StreamSupport.stream(users.spliterator(), false)
                .collect(Collectors.toList());
        modelUsers.setAll(utilizators);
    }

    @Override
    public void update(UtilizatorChangeEvent utilizatorChangeEvent) {
        modelUsers.setAll(StreamSupport.stream(service.getUsers().spliterator(), false)
                .collect(Collectors.toList()));
    }

    public void handleUserFilterId(KeyEvent keyEvent) {
        handleUserFilter();
    }

    public void handleUserFilterFName(KeyEvent keyEvent) {
        handleUserFilter();
    }

    public void handleUserFilterLName(KeyEvent keyEvent) {
        handleUserFilter();
    }

    public void handleFriendRequests(MouseEvent mouseEvent) {
        if (lastSelected == null) {
            MessageAlert.showErrorMessage(null, "Nu ati selectat niciun utilizator");
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/FriendRequests.fxml"));

            AnchorPane root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Stage stage = new Stage();
            stage.setTitle("FriendRequests");
            stage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            stage.setScene(scene);

            FriendRequestsController friendRequestsController = loader.getController();
            friendRequestsController.setService(service, lastSelected);

            stage.show();
        }
    }

    public void handleSendMessage(MouseEvent mouseEvent) {
        if (lastSelected == null) {
            MessageAlert.showErrorMessage(null, "Nu ati selectat niciun utilizator");
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/SendMessage.fxml"));

            AnchorPane root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Stage stage = new Stage();
            stage.setTitle("Message");
            stage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            stage.setScene(scene);

            SendMessageController friendRequestsController = loader.getController();
            friendRequestsController.setService(service, lastSelected);

            stage.show();
        }
    }

    public void handleShowMessages(MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/Messages.fxml"));

        AnchorPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setTitle("Message");
        stage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(root);
        stage.setScene(scene);

        MessagesController messagesController = loader.getController();
        messagesController.setService(service);

        stage.show();
    }


//    @Override
//    public void update(Event event) {
//        initModel();
//    }
}
