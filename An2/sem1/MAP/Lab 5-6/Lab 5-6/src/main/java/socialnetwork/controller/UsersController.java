package socialnetwork.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.ValidationException;
import socialnetwork.service.SuperService;
import socialnetwork.utils.events.UtilizatorChangeEvent;
import socialnetwork.utils.observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UsersController {
    SuperService service;
    ObservableList<Utilizator> modelUsers = FXCollections.observableArrayList();

    Utilizator u;

    @FXML
    TableView<Utilizator> tableViewUsers;
    @FXML
    TextField textFieldName;
    @FXML
    Button buttonAddFriend;
    @FXML
    TableColumn<Utilizator, Long> tableColumnId;
    @FXML
    TableColumn<Utilizator, String> tabceColumnFName;
    @FXML
    TableColumn<Utilizator, String> tabceColumnLName;

    public void setService(SuperService service, Utilizator u) {
        this.u = u;
        this.service = service;
        //service.addObserver(this);
        initModel();
    }

    @FXML
    public void initialize() {
        //TODO
        tableColumnId.setCellValueFactory(new PropertyValueFactory<Utilizator, Long>("Id"));
        tabceColumnFName.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("firstName"));
        tabceColumnLName.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("lastName"));
        tableViewUsers.setItems(modelUsers);

        textFieldName.textProperty().addListener(x -> handleFilter());

    }

    private void initModel() {
        List<Utilizator> list = new ArrayList<>();
        service.getPrietenii(u).forEach(x -> {
            list.add(x.getLeft());
        });

        List<Utilizator> listNeprieteni = new ArrayList<>();

        service.getUsers().forEach(x -> {
            boolean ok = true;
            for (Utilizator y : list) {
                if (y.equals(x))
                    ok = false;
            }
            if (ok && x != u)
                listNeprieteni.add(x);
        });


        modelUsers.setAll(listNeprieteni);
    }

//    @Override
//    public void update(UtilizatorChangeEvent utilizatorChangeEvent) {
//        initModel();
//    }

    @FXML
    public void handleFilterName(KeyEvent keyEvent) {
        handleFilter();
    }

    public void handleFilter() {
        String fname = textFieldName.getText();

        Predicate<Utilizator> fnamePredicate = u -> u.getFirstName().contains(fname);
        modelUsers.setAll(StreamSupport.stream(service.getUsers().spliterator(), false)
                .filter(fnamePredicate)
                .collect(Collectors.toList()));
    }

    public void handleFriendRequest(MouseEvent mouseEvent) {
        if (tableViewUsers.getSelectionModel().getSelectedItem() == null) {
            MessageAlert.showErrorMessage(null, "Trebuie sa selectezi un utilizator la care sa-i trimiti ceererea de prietenie!");
        } else {
            Utilizator u2 = tableViewUsers.getSelectionModel().getSelectedItem();
            try{
            service.addFriendRequest(u.getId(), u2.getId());}
            catch (ValidationException v){
                MessageAlert.showErrorMessage(null,v.getMessage());
            }
        }
    }
}
