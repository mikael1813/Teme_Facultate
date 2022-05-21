package controllers;

import domain.Client;
import domain.Loc;
import domain.enums.Stare;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import service.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MeniuClientController {
    private Service service;
    private Client client;

    ObservableList<Loc> modelProbe = FXCollections.observableArrayList();
    ObservableList<Loc> modelProbe2 = FXCollections.observableArrayList();

    @FXML
    TableView<Loc> tableViewLocuri;
    @FXML
    TableView<Loc> tableViewLocuriSelectate;
    @FXML
    TableColumn<Loc, Integer> tableColumnNumar;
    @FXML
    TableColumn<Loc, Integer> tableColumnLoja;
    @FXML
    TableColumn<Loc, Integer> tableColumnRand;
    @FXML
    TableColumn<Loc, Float> tableColumnPret;
    @FXML
    TableColumn<Loc, Stare> tableColumnStare;
    @FXML
    TableColumn<Loc, Integer> tableColumn2Numar;
    @FXML
    TableColumn<Loc, Integer> tableColumn2Rand;
    @FXML
    TableColumn<Loc, Integer> tableColumn2Loja;
    @FXML
    Label label;


    public void setService(Service service, Client client) {
        this.service = service;
        this.client = client;
        initModelLocuri();
        start((Stage) tableViewLocuri.getScene().getWindow());
        System.out.println(service.getSpectacolDeAzi());
        label.setText("Specatcolul "+service.getSpectacolDeAzi().getNume());
    }

    @FXML
    public void initialize() {
        tableColumnNumar.setCellValueFactory(new PropertyValueFactory<Loc, Integer>("numar"));
        tableColumnLoja.setCellValueFactory(new PropertyValueFactory<Loc, Integer>("loja"));
        tableColumnRand.setCellValueFactory(new PropertyValueFactory<Loc, Integer>("rand"));
        tableColumnPret.setCellValueFactory(new PropertyValueFactory<Loc, Float>("pret"));
        tableColumnStare.setCellValueFactory(new PropertyValueFactory<Loc, Stare>("stare"));
        tableViewLocuri.setItems(modelProbe);

        tableColumn2Numar.setCellValueFactory(new PropertyValueFactory<Loc, Integer>("numar"));
        tableColumn2Loja.setCellValueFactory(new PropertyValueFactory<Loc, Integer>("loja"));
        tableColumn2Rand.setCellValueFactory(new PropertyValueFactory<Loc, Integer>("rand"));
        tableViewLocuriSelectate.setItems(modelProbe2);

    }

    public void start(Stage primaryStage) {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                modelProbe2.forEach(x -> {
                    x.setStare(Stare.Liber);
                    service.updateLoc(x);
                });
                Platform.exit();
                System.exit(0);
            }
        });
    }

    public void initModelLocuri() {
        initialize();
        List<Loc> list = new ArrayList<>();
        service.getLocuri().forEach(x -> {
            list.add(x);
        });
        modelProbe.setAll(list);
    }

    public void eventRezerva(MouseEvent mouseEvent) {
        modelProbe2.forEach(x -> {
            service.rezerva(client, x);
        });

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/PlataCuCard.fxml"));

        AnchorPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setTitle("Plata");
        stage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.show();

        modelProbe2.clear();
    }

    public void eventLogout(MouseEvent mouseEvent) {
        ((Node) (mouseEvent.getSource())).getScene().getWindow().hide();
        modelProbe2.forEach(x -> {
            x.setStare(Stare.Liber);
            service.updateLoc(x);
        });
    }

    public void eventAddLoc(MouseEvent mouseEvent) {
        Loc loc = tableViewLocuri.getSelectionModel().getSelectedItem();
        if (loc == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Teatru");
            alert.setHeaderText("Selection failure");
            alert.setContentText("No seat selected");
            alert.showAndWait();
        } else {
            if (loc.getStare() == Stare.Rezervat) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Teatru");
                alert.setHeaderText("Selection failure");
                alert.setContentText("This seat is already taken");
                alert.showAndWait();
            } else {
                modelProbe2.add(loc);
                loc.setStare(Stare.Rezervat);
                service.updateLoc(loc);
                initModelLocuri();
            }
        }
    }

    public void eventDeleteSelectedLoc(MouseEvent mouseEvent) {
        Loc loc = tableViewLocuriSelectate.getSelectionModel().getSelectedItem();
        if (loc == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Teatru");
            alert.setHeaderText("Selection failure");
            alert.setContentText("No seat selected");
            alert.showAndWait();
        } else {
            modelProbe2.remove(loc);
            loc.setStare(Stare.Liber);
            service.updateLoc(loc);
            initModelLocuri();
        }
    }


}
