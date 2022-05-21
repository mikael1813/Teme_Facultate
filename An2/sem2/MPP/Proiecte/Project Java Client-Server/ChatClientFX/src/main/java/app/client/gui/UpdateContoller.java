package app.client.gui;

import domain.Proba;
import domain.ProbaCuParticipanti;
import domain.enums.Stil;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import services.AppException;
import services.IService;

import java.util.ArrayList;
import java.util.List;

public class UpdateContoller {
    IService service;
    ObservableList<ProbaCuParticipanti> modelProbe = FXCollections.observableArrayList();

    @FXML
    TableView<ProbaCuParticipanti> tableViewProbe;
    @FXML
    TableColumn<ProbaCuParticipanti, Integer> tableColumnId;
    @FXML
    TableColumn<ProbaCuParticipanti, Float> tableColumnDistanta;
    @FXML
    TableColumn<ProbaCuParticipanti, Stil> tableColumnStil;
    @FXML
    TableColumn<ProbaCuParticipanti, Integer> tableColumnNr;

    public void setService(IService service) throws AppException {
        this.service = service;
        initModelProbe();
    }

    @FXML
    public void initialize() throws AppException {

        tableColumnId.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getId()));
        tableColumnDistanta.setCellValueFactory(new PropertyValueFactory<ProbaCuParticipanti, Float>("distanta"));
        tableColumnStil.setCellValueFactory(new PropertyValueFactory<ProbaCuParticipanti, Stil>("stil"));
        tableColumnNr.setCellValueFactory(new PropertyValueFactory<ProbaCuParticipanti, Integer>("nr"));
        tableViewProbe.setItems(modelProbe);


    }

    public void initModelProbe() throws AppException {
        List<Integer> list = service.getNrParticipanti();
        Proba[] probas = service.getProbe();
        List<ProbaCuParticipanti> list1 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ProbaCuParticipanti probaCuParticipanti = new ProbaCuParticipanti(probas[i].getDistanta(),probas[i].getStil(),list.get(i));
            probaCuParticipanti.setId(probas[i].getId());
            list1.add(probaCuParticipanti);
        }

        modelProbe.setAll(list1);
    }
}
