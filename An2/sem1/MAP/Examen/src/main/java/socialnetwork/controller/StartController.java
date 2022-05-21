package socialnetwork.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import socialnetwork.domain.Hotel;
import socialnetwork.domain.Location;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.enums.Type;
import socialnetwork.service.SuperService;
import socialnetwork.utils.events.UtilizatorChangeEvent;
import socialnetwork.utils.observer.Observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class StartController implements Observer<UtilizatorChangeEvent> {

    SuperService service;
    ObservableList<Hotel> modelHotels = FXCollections.observableArrayList();
    ObservableList<Double> modelLocations = FXCollections.observableArrayList();

    @FXML
    TableView<Hotel> tableView;
    @FXML
    TableColumn<Hotel, String> tableColumnName;
    @FXML
    TableColumn<Hotel, Integer> tableColumnNoRooms;
    @FXML
    TableColumn<Hotel, Type> tableColumnType;
    @FXML
    TableColumn<Hotel,Double> tableColumnPrice;
    @FXML
    ComboBox<Double> comboBox;

    public StartController() { }

    public void setService(SuperService service) {
        this.service = service;
        //service.addObserver(this);
        initModelLocations();
    }

    @FXML
    public void initialize() {
        tableColumnName.setCellValueFactory(new PropertyValueFactory<Hotel, String>("hotelName"));
        tableColumnNoRooms.setCellValueFactory(new PropertyValueFactory<Hotel, Integer>("noRooms"));
        tableColumnType.setCellValueFactory(new PropertyValueFactory<Hotel, Type>("type"));
        tableColumnPrice.setCellValueFactory(new PropertyValueFactory<Hotel, Double>("pricePerNight"));
        tableView.setItems(modelHotels);

        comboBox.cellFactoryProperty();
        comboBox.setItems(modelLocations);

    }

    private void initModelLocations(){
        Iterable<Location> locations = service.getLocations();
        List<Location> locations1 = StreamSupport.stream(locations.spliterator(),false)
                .collect(Collectors.toList());
        List<Double> list1= new ArrayList<>();
        locations1.forEach(x->{
            list1.add(x.getId());
        });
        modelLocations.setAll(list1);
    }

    private void initModelUsers() {
        //Iterable<Utilizator> users = service.getUsers();
        //List<Utilizator> utilizators = StreamSupport.stream(users.spliterator(), false)
        //        .collect(Collectors.toList());
        //modelUsers.setAll(utilizators);
    }

    private void initModelHotels(List<Hotel> list){
        modelHotels.setAll(list);
    }


    @Override
    public void update(UtilizatorChangeEvent utilizatorChangeEvent) {

    }


    public void handleCombo(MouseEvent mouseEvent) {
        Double id = comboBox.getValue();
        if(id!=null) {
            System.out.println(id);
            modelHotels.setAll(service.getHotelsByLocation(id));
            service.getHotelsByLocation(id).forEach(x -> {
                System.out.println(x.getHotelName());
            });
        }
    }

    public void handleSelect(MouseEvent mouseEvent) {

        Hotel h = tableView.getSelectionModel().getSelectedItem();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/data.fxml"));

        AnchorPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setTitle("Date");
        stage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(root);
        stage.setScene(scene);

        DateController messagesController = loader.getController();
        messagesController.setService(service,h);

        stage.show();
    }
}
