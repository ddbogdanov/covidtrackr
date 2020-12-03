package cis.ddbogdanov.covidtrackr.controller;

import cis.ddbogdanov.covidtrackr.model.SnapshotRepo;
import cis.ddbogdanov.covidtrackr.model.UserRepo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("/HomeSceneController.fxml")
public class HomeSceneController implements Initializable {

    private final FxWeaver fxWeaver;
    private Stage stage;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private SnapshotRepo snapshotRepo;

    @FXML AnchorPane pane;
    @FXML Button button;

    public HomeSceneController(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.stage = new Stage();
        Scene scene = new Scene(pane);
        stage.setScene(scene);
    }

    public void show() {
        stage.show();
    }
}
