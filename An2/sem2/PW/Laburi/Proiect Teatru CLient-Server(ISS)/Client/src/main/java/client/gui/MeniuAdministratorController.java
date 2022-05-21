package client.gui;

import domain.Client;
import domain.Loc;
import domain.Spectacol;
import domain.enums.Stare;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import services.AppException;


import java.util.ArrayList;
import java.util.List;

public class MeniuAdministratorController {
    private ClientCtrl ctrl;

    ObservableList<Spectacol> modelSpectacole = FXCollections.observableArrayList();
    ObservableList<Loc> modelLocuri = FXCollections.observableArrayList();

    @FXML
    TableView<Spectacol> tableViewSpectacole;
    @FXML
    TableView<Loc> tableViewLocuri;
    @FXML
    TableColumn<Loc, Integer> tableColumnID2;
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
    TableColumn<Spectacol, Integer> tableColumnID;
    @FXML
    TableColumn<Spectacol, String> tableColumnNume;
    @FXML
    TableColumn<Spectacol, String> tableColumnData;
    @FXML
    TextField txtNume;
    @FXML
    TextField txtData;
    @FXML
    TextField txtNumar;
    @FXML
    TextField txtRand;
    @FXML
    TextField txtLoja;
    @FXML
    TextField txtPret;
    @FXML
    TextField txtL;
    @FXML
    TextField txtR;
    @FXML
    TextField txtP;
    @FXML
    TextField txtLo;
    Spectacol lastSelectedSpectacol;
    Loc lastSelectedLoc;


    public void setService(ClientCtrl ctrl) throws AppException {
        this.ctrl = ctrl;
        initModels();
    }


    @FXML
    public void initialize() {
        tableColumnID2.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getId()));
        tableColumnNumar.setCellValueFactory(new PropertyValueFactory<Loc, Integer>("numar"));
        tableColumnLoja.setCellValueFactory(new PropertyValueFactory<Loc, Integer>("loja"));
        tableColumnRand.setCellValueFactory(new PropertyValueFactory<Loc, Integer>("rand"));
        tableColumnPret.setCellValueFactory(new PropertyValueFactory<Loc, Float>("pret"));
        tableColumnStare.setCellValueFactory(new PropertyValueFactory<Loc, Stare>("stare"));
        tableViewLocuri.setItems(modelLocuri);

        tableColumnID.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getId()));
        tableColumnNume.setCellValueFactory(new PropertyValueFactory<Spectacol, String>("nume"));
//        tableColumnData.setCellValueFactory(param -> {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            return new SimpleObjectProperty(param.getValue().getDate().format(formatter));
//        });
        tableColumnData.setCellValueFactory(new PropertyValueFactory<Spectacol, String>("date"));
        tableViewSpectacole.setItems(modelSpectacole);

    }

    public void initModels() throws AppException {
        //initialize();
        List<Loc> list = new ArrayList<>();
        ctrl.getLocuri().forEach(x -> {
            list.add(x);
        });
        modelLocuri.setAll(list);

        List<Spectacol> list2 = new ArrayList<>();
        ctrl.getSpectacole().forEach(x -> {
            list2.add(x);
        });
        modelSpectacole.setAll(list2);
    }

    public void eventSelectSpectacol(MouseEvent mouseEvent) {
        Spectacol spectacol = tableViewSpectacole.getSelectionModel().getSelectedItem();
        lastSelectedSpectacol = spectacol;
        txtNume.setText(spectacol.getNume());
        txtData.setText(spectacol.getDate().toString());
    }

    public void eventSelectLoc(MouseEvent mouseEvent) {
        Loc loc = tableViewLocuri.getSelectionModel().getSelectedItem();
        lastSelectedLoc = loc;
        txtNumar.setText(String.valueOf(loc.getNumar()));
        txtLoja.setText(String.valueOf(loc.getLoja()));
        txtRand.setText(String.valueOf(loc.getRand()));
        txtPret.setText(String.valueOf(loc.getPret()));
    }

    public void eventLogout(MouseEvent mouseEvent) {
        ((Node) (mouseEvent.getSource())).getScene().getWindow().hide();
    }

    public void addShow(MouseEvent mouseEvent) throws AppException {
        Spectacol spectacol = new Spectacol(txtNume.getText(), txtData.getText());
        ctrl.addShow(spectacol);
        initModels();
    }

    public void updateShow(MouseEvent mouseEvent) throws AppException {
        Spectacol spectacol = new Spectacol(txtNume.getText(), txtData.getText());
        spectacol.setId(lastSelectedSpectacol.getId());
        ctrl.updateShow(spectacol);
        //initModels();
    }

    public void deleteShow(MouseEvent mouseEvent) throws AppException {
        ctrl.deleteShow(lastSelectedSpectacol.getId());
        initModels();
    }

    public void addLoc(MouseEvent mouseEvent) throws AppException {
        Loc loc = new Loc(Integer.parseInt(txtLoja.getText()), Integer.parseInt(txtNumar.getText()), Integer.parseInt(txtRand.getText()), Float.parseFloat(txtPret.getText()), Stare.Liber);
        ctrl.addLoc(loc);
        initModels();
    }

    public void updateLoc(MouseEvent mouseEvent) throws AppException {
        Loc loc = new Loc(Integer.parseInt(txtLoja.getText()), Integer.parseInt(txtNumar.getText()), Integer.parseInt(txtRand.getText()), Float.parseFloat(txtPret.getText()), Stare.Liber);
        loc.setId(lastSelectedLoc.getId());
        ctrl.updateLoc(loc);
        initModels();
    }

    public void deleteLoc(MouseEvent mouseEvent) throws AppException {
        ctrl.deleteLoc(lastSelectedLoc.getId());
        initModels();
    }

    public void addLocuri(MouseEvent mouseEvent) throws AppException {
        for (int i = 1; i <= Integer.parseInt(txtL.getText()); i++) {
            Loc loc = new Loc(Integer.parseInt(txtLo.getText()),i,Integer.parseInt(txtR.getText()),Float.parseFloat(txtP.getText()),Stare.Liber);
            ctrl.addLoc(loc);
        }
        initModels();
    }
}
