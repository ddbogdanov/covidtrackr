package cis.ddbogdanov.covidtrackr.controller;

import cis.ddbogdanov.covidtrackr.model.Snapshot;
import cis.ddbogdanov.covidtrackr.model.SnapshotRepo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("/SnapshotSceneController.fxml")
public class SnapshotSceneController implements Initializable {

    private final FxWeaver fxWeaver;
    private Stage stage;

    @FXML private AnchorPane pane;
    @FXML private VBox formVbox;
    @FXML private JFXTextField countryNameTextField, dateTextField, totalCasesTextField, totalDeathsTextField, recoveredTextField;
    @FXML private JFXButton submitButton, deleteButton, goButton, refreshButton;
    @FXML private JFXListView<Snapshot> listView;

    @Autowired
    private SnapshotRepo snapshotRepo;

    public SnapshotSceneController(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.stage = new Stage();
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        populateList();

        if(!LoginController.getUser().getIsAdmin()) {
            formVbox.setDisable(true);
        }

        listView.getSelectionModel().selectedItemProperty().addListener((observableValue, snapshot, t1) -> {
            try {
                Snapshot selectedSnapshot = listView.getSelectionModel().getSelectedItem();
                countryNameTextField.setText(selectedSnapshot.getCountryName());
                dateTextField.setText(selectedSnapshot.getDate());
                totalCasesTextField.setText(Integer.toString(selectedSnapshot.getTotalCases()));
                totalDeathsTextField.setText(Integer.toString(selectedSnapshot.getTotalDeaths()));
                recoveredTextField.setText(Integer.toString(selectedSnapshot.getRecovered()));
            }
            catch(NullPointerException ex) {
                clearTextFields();
            }
        });

        goButton.setOnAction(e -> {
            loadSnapshot();
        });
        submitButton.setOnAction(e -> {
            updateSnapshot();
        });
        deleteButton.setOnAction(e -> {
            deleteSnapshot();
        });
        refreshButton.setOnAction(e -> {
            populateList();
        });
    }

    public void show() {
        stage.show();
    }

    private void clearTextFields() {
        countryNameTextField.clear();
        dateTextField.clear();
        totalCasesTextField.clear();
        totalDeathsTextField.clear();
        recoveredTextField.clear();
    }
    private void updateSnapshot() {
        Snapshot updatedSnapshot = new Snapshot(
                listView.getSelectionModel().getSelectedItem().getId(),
                listView.getSelectionModel().getSelectedItem().getUserId(),
                countryNameTextField.getText(),
                dateTextField.getText(),
                Integer.parseInt(totalCasesTextField.getText()),
                Integer.parseInt(totalDeathsTextField.getText()),
                Integer.parseInt(recoveredTextField.getText())
        );
        snapshotRepo.save(updatedSnapshot);
        populateList();
    }
    private void deleteSnapshot() {
        Snapshot selected = listView.getSelectionModel().getSelectedItem();
        snapshotRepo.deleteById(selected.getId());
        populateList();
    }
    private void populateList() {
        ObservableList<Snapshot> snapshotList = FXCollections.observableArrayList();

        if(snapshotRepo.count() != 0) {
            snapshotList.addAll(snapshotRepo.findAllByUserId(LoginController.getUser().getId()));
        }
        listView.setItems(snapshotList);
    }
    private void loadSnapshot() {
        Snapshot updatedSnapshot = new Snapshot(
                listView.getSelectionModel().getSelectedItem().getId(),
                listView.getSelectionModel().getSelectedItem().getUserId(),
                countryNameTextField.getText(),
                dateTextField.getText(),
                Integer.parseInt(totalCasesTextField.getText()),
                Integer.parseInt(totalDeathsTextField.getText()),
                Integer.parseInt(recoveredTextField.getText())
        );
        HomeController.homeSceneController.loadOutlook(updatedSnapshot); //reference to currently loaded HomeSceneController
        LoginController.homeController.showHomeView(); //reference to currently loaded HomeController
    }
}
