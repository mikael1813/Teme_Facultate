package socialnetwork.controller;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import socialnetwork.domain.Message;
import socialnetwork.domain.Utilizator;
import socialnetwork.service.SuperService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MessagesController {
    SuperService service;
    ObservableList<Message> model = FXCollections.observableArrayList();

    @FXML
    TableView<Message> tableView;
    @FXML
    TableColumn<Message, Long> tableColumnId;
    @FXML
    TableColumn<Message, String> tableColumnFrom;
    @FXML
    TableColumn<Message, String> tableColumnTo;
    @FXML
    TableColumn<Message, String> tableColumnMessage;
    @FXML
    TableColumn<Message, String> tableColumnDate;


    public void setService(SuperService service) {
        this.service = service;
        //service.addObserver(this);
        initModel();
    }

    @FXML
    public void initialize() {
        //TODO
        tableColumnId.setCellValueFactory(new PropertyValueFactory<Message, Long>("Id"));
        tableColumnFrom.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getFrom().getId().toString()));
        tableColumnTo.setCellValueFactory(param -> {
            List<Utilizator> u = param.getValue().getTo();
            String s = "";
            for (Utilizator x : u) {
                s = s + x.getId().toString() + " ";
            }
            return new SimpleObjectProperty<>(s);
        });
        tableColumnMessage.setCellValueFactory(new PropertyValueFactory<Message, String>("message"));
        tableColumnDate.setCellValueFactory(new PropertyValueFactory<Message, String>("data"));
        tableView.setItems(model);

    }

    private void initModel() {
        Iterable<Message> messages = service.getMessages();
        List<Message> messageList = StreamSupport.stream(messages.spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(messageList);
    }
}
