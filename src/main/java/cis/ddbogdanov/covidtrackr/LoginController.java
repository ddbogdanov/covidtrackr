package cis.ddbogdanov.covidtrackr;

import cis.ddbogdanov.covidtrackr.model.UserRepo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class LoginController implements Initializable {

    @Autowired
    UserRepo userRepo;

    @FXML
    private JFXTextField usernameField;
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private JFXButton loginButton, addNewUserButton, dbButton, minimizeButton, exitButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loginButton.setOnAction(e -> {

        });

        addNewUserButton.setOnAction(e -> {

        });

        dbButton.setOnAction(e -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/databaseInfo.fxml"));
            Scene databaseScene;

            try {
                databaseScene = new Scene(loader.load());
            }
            catch(Exception ex) {
                ex.printStackTrace();
                return;
            }
            databaseScene.setFill(Color.TRANSPARENT);

            Stage databaseStage = new Stage();
            databaseStage.initOwner(dbButton.getScene().getWindow());
            databaseStage.initModality(Modality.WINDOW_MODAL);
            databaseStage.initStyle(StageStyle.TRANSPARENT);
            databaseStage.setScene(databaseScene);
            databaseStage.showAndWait();
        });

        minimizeButton.setOnAction(e -> {
            Stage stage = (Stage)minimizeButton.getScene().getWindow();
            stage.setIconified(true);
        });
        exitButton.setOnAction(e -> {
            Platform.exit();
        });
    }

    public void login() {

    }
}
