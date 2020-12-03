package cis.ddbogdanov.covidtrackr;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;


//TODO: Add auto read into db form
@Component
@FxmlView("/DatabaseViewController.fxml")
//@PropertySource("classpath:application.properties")
public class DatabaseViewController implements Initializable {

    private Stage stage;
    private final FxWeaver fxWeaver;

    //@Autowired
    //private Environment env;
    //@Autowired
    //Navigation navigation;

    @FXML
    JFXTextField endpointTextField, portTextField, dbNameTextField, dbUsernameTextField;
    @FXML
    JFXPasswordField dbPasswordField;
    @FXML
    JFXButton closeButton;
    @FXML
    AnchorPane pane;

    public DatabaseViewController(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.stage = new Stage();
        stage.setScene(new Scene(pane));

        closeButton.setOnAction(e -> {
            Stage stage = (Stage)closeButton.getScene().getWindow();
            stage.close();
        });
        /*String endpoint = env.getProperty("spring.datasource.url");

        endpointTextField.setText(endpoint);
        endpointTextField.setDisable(true);
        System.out.println(endpoint);*/
    }

    public void show() {
        stage.show();
    }
}
