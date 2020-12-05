package cis.ddbogdanov.covidtrackr.controller;

import cis.ddbogdanov.covidtrackr.application.ResizeHelper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Access to this class from LoginController is currently disabled
 */
@Component
@FxmlView("/DatabaseViewController.fxml")
public class DatabaseViewController implements Initializable {

    private Stage stage;
    private final FxWeaver fxWeaver;

    @FXML JFXTextField endpointTextField, portTextField, dbNameTextField, dbUsernameTextField;
    @FXML JFXPasswordField dbPasswordField;
    @FXML JFXButton closeButton;
    @FXML AnchorPane pane;

    public DatabaseViewController(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        Scene scene = new Scene(pane);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        ResizeHelper.addResizeListener(stage);

        closeButton.setOnAction(e -> {
            Stage stage = (Stage)closeButton.getScene().getWindow();
            stage.close();
        });
    }

    public void show() {
        stage.show();
    }
}
