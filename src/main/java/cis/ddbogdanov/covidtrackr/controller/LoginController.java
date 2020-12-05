package cis.ddbogdanov.covidtrackr.controller;

import cis.ddbogdanov.covidtrackr.model.User;
import cis.ddbogdanov.covidtrackr.model.UserRepo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

@Component
@FxmlView("/LoginController.fxml")
public class LoginController implements Initializable {

    private final FxWeaver fxWeaver;
    private Stage stage;
    private static User user;
    public static HomeController homeController;

    @Autowired
    private UserRepo userRepo;

    @FXML private JFXTextField usernameField;
    @FXML private JFXPasswordField passwordField;
    @FXML private JFXButton loginButton, addNewUserButton, dbButton, minimizeButton, exitButton;
    @FXML private Label loginStatus;

    public LoginController(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbButton.setDisable(true);
        loginStatus.setVisible(false);

        loginButton.setOnAction(e -> {
            login();
        });
        addNewUserButton.setOnAction(e -> {
            addUser();
        });

        dbButton.setOnAction(e -> {
            showDbInfo();
        });
        minimizeButton.setOnAction(e -> {
            Stage stage = (Stage)minimizeButton.getScene().getWindow();
            stage.setIconified(true);
        });
        exitButton.setOnAction(e -> {
            Platform.exit();
        });
    }


    private void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        UUID userId;
        boolean isAdmin;
        try {
            if (userRepo.findByUsername(username).get(0).getPassword().equals(password)) {

                userId = userRepo.findByUsername(username).get(0).getId();
                isAdmin = userRepo.findByUsername(username).get(0).getIsAdmin();
                user = new User(userId, username, password, isAdmin);

                loginStatus.setTextFill(Color.web("#FFFFFF"));
                loginStatus.setText("Login Successful!");
                loginStatus.setVisible(true);
                homeController = fxWeaver.loadController(HomeController.class);
                homeController.show();
                loginButton.getScene().getWindow().hide();

            } else {
                loginStatus.setTextFill(Color.web("#F73331"));
                loginStatus.setVisible(true);
                loginStatus.setText("Password is incorrect");
            }
        }
        catch(IndexOutOfBoundsException ex) {
            loginStatus.setTextFill(Color.web("#F73331"));
            loginStatus.setVisible(true);
            loginStatus.setText("This username may not exist");
        }
    }

    private void showDbInfo() {
        fxWeaver.loadController(DatabaseViewController.class).show();
        /*Stage dbInfoStage = new Stage();
        dbInfoStage.initOwner(dbButton.getScene().getWindow());
        dbInfoStage.initModality(Modality.WINDOW_MODAL);
        dbInfoStage.initStyle(StageStyle.TRANSPARENT);
        navigation.setStage(dbInfoStage);
        navigation.showDbInfoView(300, 400);
        Parent root = navigation.getRoot();
        root.setOnMousePressed(pressEvent -> {
            xOffset = pressEvent.getSceneX();
            yOffset = pressEvent.getSceneY();
        });
        root.setOnMouseDragged(dragEvent -> {
            dbInfoStage.setX(dragEvent.getScreenX() - xOffset);
            dbInfoStage.setY(dragEvent.getScreenY() - yOffset);
        });
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/DatabaseViewController.fxml"));
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
        databaseStage.showAndWait();*/
    }
    private void addUser() {
        fxWeaver.loadController(AddUserController.class).show();
        /*Stage newUserStage = new Stage();
        newUserStage.initOwner(addNewUserButton.getScene().getWindow());
        newUserStage.initModality(Modality.WINDOW_MODAL);
        newUserStage.initStyle(StageStyle.TRANSPARENT);
        navigation.setStage(newUserStage);
        //navigation.showAddUserView(300, 400);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddUserController.fxml"));
        Scene newUserScene;

        try {
            newUserScene = new Scene(loader.load());
        }
        catch(Exception ex) {
            ex.printStackTrace();
            return;
        }
        newUserScene.setFill(Color.TRANSPARENT);

        Stage newUserStage = new Stage();
        newUserStage.initOwner(addNewUserButton.getScene().getWindow());
        newUserStage.initModality(Modality.WINDOW_MODAL);
        newUserStage.initStyle(StageStyle.TRANSPARENT);
        newUserStage.setScene(newUserScene);
        newUserStage.showAndWait();*/
    }
    public void show() {
        stage.show();
    }
    public static User getUser() {
        return user;
    }
}
