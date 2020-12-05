package cis.ddbogdanov.covidtrackr.controller;

import cis.ddbogdanov.covidtrackr.application.ResizeHelper;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("/HomeController.fxml")
public class HomeController implements Initializable {

    private static final String ACTIVE_BUTTON ="-fx-background-color: #11AB97";
    private static final String INACTIVE_BUTTON ="-fx-background-color: #007B68";
    private final FxWeaver fxWeaver;
    private Stage stage;

    public static Node homeScene, snapshotScene;
    public static HomeSceneController homeSceneController;

    @FXML private AnchorPane pane;
    @FXML private JFXButton minimizeButton, maximizeButton, exitButton, homeTab, snapshotTab, logoutButton;
    @FXML private BorderPane mainView;

    public HomeController(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.stage = new Stage();
        stage.setTitle("COVIDTrackr - Home");
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        ResizeHelper.addResizeListener(stage);

        //homeScene = fxWeaver.loadView(HomeSceneController.class);
        homeSceneController = fxWeaver.loadController(HomeSceneController.class);
        homeScene = homeSceneController.getScene();
        snapshotScene = fxWeaver.loadView(SnapshotSceneController.class);

        mainView.setMinSize(1104, 553);
        mainView.setMaxSize(1920, 1080);
        homeTab.setStyle(ACTIVE_BUTTON);
        mainView.setCenter(homeScene);

        homeTab.setOnAction(e -> {
            showHomeView();
        });
        snapshotTab.setOnAction(e -> {
            showSnapshotView();
        });
        logoutButton.setOnAction(e -> {
            logoutButton.getScene().getWindow().hide();
        });

        minimizeButton.setOnAction(e -> {
            Stage stage = (Stage)minimizeButton.getScene().getWindow();
            stage.setIconified(true);
        });
        maximizeButton.setOnAction(e -> {
            Stage stage = (Stage)maximizeButton.getScene().getWindow();
            stage.setMaximized(true);
            //TODO: Add restore down functionality
        });
        exitButton.setOnAction(e -> {
            Platform.exit();
        });
    }

    public void show() {
        stage.show();
    }
    public void showHomeView() {
        homeTab.setStyle(ACTIVE_BUTTON);
        snapshotTab.setStyle(INACTIVE_BUTTON);
        logoutButton.setStyle(INACTIVE_BUTTON);
        mainView.setCenter(homeScene);
    }
    private void showSnapshotView() {
        homeTab.setStyle(INACTIVE_BUTTON);
        snapshotTab.setStyle(ACTIVE_BUTTON);
        logoutButton.setStyle(INACTIVE_BUTTON);
        mainView.setCenter(snapshotScene);
    }
}