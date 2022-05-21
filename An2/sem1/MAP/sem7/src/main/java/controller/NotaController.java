package controller;

import domain.NotaDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import service.ServiceManager;

import java.util.List;
import java.util.stream.Collectors;

public class NotaController {

    ObservableList<NotaDto> modelGrade = FXCollections.observableArrayList();
    List<String> modelTema;
    private ServiceManager service;
    Integer counter=0;

    @FXML
    TableColumn<NotaDto, String> tableColumnName;
    @FXML
    TableColumn<NotaDto, String> tableColumnTema;
    @FXML
    TableColumn<NotaDto, Double> tableColumnNota;

    @FXML
    TableColumn<NotaDto, String> tableColumnProfesor;
    @FXML
    TableView<NotaDto> tableViewNote;
    @FXML
    TextField textFieldCounter;
    @FXML
    TextField textFieldNume;
    @FXML
    TextField textFieldNota;
    @FXML
    TextField textFieldTema;


    @FXML
    public void initialize() {
        // TODO

        tableViewNote.setItems(modelGrade);
        tableColumnName.setCellValueFactory(new PropertyValueFactory<NotaDto, String>("studentName"));
        tableColumnTema.setCellValueFactory(new PropertyValueFactory<NotaDto, String>("temaId"));
        tableColumnNota.setCellValueFactory(new PropertyValueFactory<NotaDto, Double>("nota"));
        tableColumnProfesor.setCellValueFactory(new PropertyValueFactory<NotaDto, String>("profesor"));
        textFieldNume.textProperty().addListener(e->handleFilter());
        textFieldNota.textProperty().addListener(e->handleFilter());
        textFieldTema.textProperty().addListener(e->handleFilter());
    }

    private List<NotaDto> getNotaDTOList() {
        // TODO

        return service.findAllGrades()
                .stream()
                .map(nota->new NotaDto(nota.getStudent().getName(),nota.getTema().getId(),nota.getValue(),nota.getProfesor()))
                .collect(Collectors.toList());

    }

    @FXML
    private void handleFilter() {
        // TODO sem 8
        String nume = textFieldNume.getText();

        modelGrade.setAll(getNotaDTOList().stream()
                .filter(x->x.getStudentName().startsWith(nume))
                .filter(x->{
                    try{
                        return x.getNota() > Double.parseDouble(textFieldNota.getText());
                    }
                    catch (NumberFormatException e){
                        return true;
                    }
                })
                .filter(x->x.getTemaId().startsWith(textFieldTema.getText()))
                .collect(Collectors.toList()));

    }


    public void setService(ServiceManager service) {
        // TODO
        this.service=service;
        modelGrade.setAll(getNotaDTOList());
    }

    public void handleButtonCounterClick(ActionEvent actionEvent) {

        counter++;

        textFieldCounter.setText(counter.toString());

    }
}
