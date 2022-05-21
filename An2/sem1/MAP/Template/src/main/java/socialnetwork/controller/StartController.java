package socialnetwork.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import socialnetwork.domain.Mesaj;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.enums.Activitate;
import socialnetwork.domain.enums.Pozitie;
import socialnetwork.domain.enums.Privatitate;
import socialnetwork.service.SuperService;
import socialnetwork.utils.events.UtilizatorChangeEvent;
import socialnetwork.utils.observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class StartController implements Observer<UtilizatorChangeEvent> {

    SuperService service;
    ObservableList<Utilizator> modelUsers = FXCollections.observableArrayList();
    ObservableList<Mesaj> modelMessages = FXCollections.observableArrayList();

    @FXML
    TableView<Utilizator> tableViewUsers;
    @FXML
    TableColumn<Utilizator, Long> tableColumnUserId;
    @FXML
    TableColumn<Utilizator, String> tableColumnUserFName;
    @FXML
    TableColumn<Utilizator, String> tableColumnUserLName;
    @FXML
    TableColumn<Utilizator, Pozitie> tableColumnUserPosition;
    @FXML
    TableView<Mesaj> tableViewMessage;
    @FXML
    TableColumn<Mesaj, Long> tableViewMessageSender;
    @FXML
    TableColumn<Mesaj, Privatitate> tableViewMessagePrivacy;
    @FXML
    TableColumn<Mesaj, Long> tableViewMessageReceiver;
    @FXML
    TableColumn<Mesaj, String> tableViewMessageMesaj;
    @FXML
    TextField textFieldUser;
    @FXML
    Button buttonInactiv;
    @FXML
    Button buttonActiv;
    @FXML
    TextField textFieldMessage;
    @FXML
    Button buttonSendMesaj;

    Utilizator user;

    public void setUser(Utilizator user) {
        this.user = user;
        textFieldUser.setText(user.getFirstName() + " " + user.getLastName());
    }

    public void setService(SuperService service) {
        this.service = service;
        service.addObserver(this);
        initModelUsers();
        //initModelMessages();
    }

    @FXML
    public void initialize() {
        tableColumnUserId.setCellValueFactory(new PropertyValueFactory<Utilizator, Long>("Id"));
        tableColumnUserFName.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("firstName"));
        tableColumnUserLName.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("lastName"));
        tableColumnUserPosition.setCellValueFactory(new PropertyValueFactory<Utilizator, Pozitie>("pozitie"));
        tableViewUsers.setItems(modelUsers);

        tableViewMessageSender.setCellValueFactory(new PropertyValueFactory<Mesaj, Long>("u1"));
        tableViewMessagePrivacy.setCellValueFactory(new PropertyValueFactory<Mesaj, Privatitate>("privacy"));
        tableViewMessageReceiver.setCellValueFactory(new PropertyValueFactory<Mesaj, Long>("u2"));
        tableViewMessageMesaj.setCellValueFactory(new PropertyValueFactory<Mesaj, String>("mesaj"));
        tableViewMessage.setItems(modelMessages);

    }

    private void initModelMessages() {
        Iterable<Mesaj> mesajs = service.getMessages();
        List<Mesaj> list = StreamSupport.stream(mesajs.spliterator(), false)
                .filter(x -> {
                    return x.getPrivacy() == Privatitate.PUBLIC || x.getU1() == user.getId() || x.getU2() == user.getId();
                })
                .collect(Collectors.toList());

        modelMessages.setAll(list);
    }

    private void initModelUsers() {
        Iterable<Utilizator> users = service.getUsers();
        List<Utilizator> utilizators = StreamSupport.stream(users.spliterator(), false)
                .filter(x -> {
                    if (x.getActivitate() == Activitate.ACTIV)
                        return true;
                    return false;
                })
                .collect(Collectors.toList());
        modelUsers.setAll(utilizators);
    }

    public void handleButtonActivClicked(MouseEvent mouseEvent) {
        service.setUserActiv(user);
    }

    public void handleButtonInactivClicked(MouseEvent mouseEvent) {
        if (user.getPozitie() == Pozitie.SEF)
            if (service.canSefLeave())
                service.setUserInactiv(user);
            else
                MessageAlert.showErrorMessage(null, "Seful nu poate pleca daca nu ai iesit toti ceilalti");

        else
            service.setUserInactiv(user);

    }

    @Override
    public void update(UtilizatorChangeEvent utilizatorChangeEvent) {
        initModelUsers();

        if (user.getActivitate() == Activitate.ACTIV) {
            //initModelMessages();
        }
    }


    public void handleSend(MouseEvent mouseEvent) {
        Mesaj m = service.addMesaj(user.getId(), Privatitate.PUBLIC, Long.parseLong("0"), textFieldMessage.getText());
        modelMessages.add(new Mesaj(user.getId(), Privatitate.PUBLIC, Long.parseLong("0"), textFieldMessage.getText()));
        //tableViewMessage.getItems().add(m);
//        if(textFieldMessage.getText()!=""){
//            if(user.getPozitie()==Pozitie.SEF){
//                if(tableViewUsers.getSelectionModel().getSelectedItem()!=null){
//                    service.addMesaj(user.getId(),Privatitate.PRIVATE,tableViewUsers.getSelectionModel().getSelectedItem().getId(),textFieldMessage.getText());
//
//                }
//                else{
//                    service.addMesaj(user.getId(),Privatitate.PUBLIC,Long.parseLong("0"),textFieldMessage.getText());
//                }
//            }
//            else{
//                service.addMesaj(user.getId(),Privatitate.PUBLIC,Long.parseLong("0"),textFieldMessage.getText());
//            }
//        }
//        else{
//            MessageAlert.showErrorMessage(null,"Mesajul nu poate fi gol");
//        }

    }
}
