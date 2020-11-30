package cis.ddbogdanov.covidtrackr;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DatabaseViewController implements Initializable {

    @FXML
    JFXButton closeButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        closeButton.setOnAction(e -> {
            Stage stage = (Stage)closeButton.getScene().getWindow();
            stage.close();
        });
    }
}
