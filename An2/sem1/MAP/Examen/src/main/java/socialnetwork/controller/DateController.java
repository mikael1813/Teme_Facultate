package socialnetwork.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import socialnetwork.domain.Hotel;
import socialnetwork.domain.SpecialOffer;
import socialnetwork.domain.enums.Type;
import socialnetwork.service.SuperService;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static socialnetwork.utils.Constants.DATE_TIME_FORMATTER;

public class DateController {

    SuperService service;
    ObservableList<SpecialOffer> modelOffers = FXCollections.observableArrayList();

    @FXML
    TableView<SpecialOffer> tableView;
    @FXML
    TableColumn<SpecialOffer, String> tableColumnStart;
    @FXML
    TableColumn<SpecialOffer, String> tableColumnEnd;
    @FXML
    TableColumn<SpecialOffer, Integer> tableColumnPercents;
    @FXML
    DatePicker datePicker;
    @FXML
    Button button;

    Hotel hotel;

    public void setService(SuperService service,Hotel h) {
        this.service = service;
        //service.addObserver(this);
        this.hotel = h;

    }

    @FXML
    public void initialize() {
        tableColumnStart.setCellValueFactory(param->{
            String s = param.getValue().getStartDate().format(DATE_TIME_FORMATTER);
            return new SimpleObjectProperty<>(s);
        });
        tableColumnEnd.setCellValueFactory(param->{
            String s = param.getValue().getEndDate().format(DATE_TIME_FORMATTER);
            return new SimpleObjectProperty<>(s);
        });
        tableColumnPercents.setCellValueFactory(new PropertyValueFactory<SpecialOffer, Integer>("percents"));
        tableView.setItems(modelOffers);

    }

    public void handledate(MouseEvent mouseEvent) {
        LocalDate date = datePicker.getValue();
        modelOffers.setAll(service.getOffersByDate(date,hotel));
    }
}
