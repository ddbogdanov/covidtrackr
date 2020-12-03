package cis.ddbogdanov.covidtrackr.controller;

import cis.ddbogdanov.covidtrackr.model.User;
import cis.ddbogdanov.covidtrackr.model.UserRepo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
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
@FxmlView("/AddUserController.fxml")
public class AddUserController implements Initializable {

    private FxWeaver fxWeaver;
    private Stage stage;

    @Autowired
    private UserRepo userRepo;

    @FXML
    AnchorPane pane;
    @FXML
    JFXButton submitButton, closeButton;
    @FXML
    JFXTextField usernameTextField;
    @FXML
    JFXPasswordField userPasswordField, userConfirmPasswordField;
    @FXML
    JFXCheckBox isAdminCheck;
    @FXML
    Label passMatchField;

    public AddUserController(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.stage = new Stage();
        stage.setScene(new Scene(pane));

        passMatchField.setVisible(false);

        submitButton.setOnAction(e -> {
            submit();
        });
        closeButton.setOnAction(e -> {
            Stage stage = (Stage)closeButton.getScene().getWindow();
            stage.close();
        });
    }
    private void submit() {
        String username = usernameTextField.getText();
        String password = userPasswordField.getText();
        String confirmPassword = userConfirmPasswordField.getText();
        boolean isAdmin = isAdminCheck.isSelected();

        try {
            //User user = userRepo.findByUsername("notadmin").get(0).getObject();
            //System.out.println(user);

            System.out.println(userRepo.findByUsername("notadmin"));
        }
        catch(Exception ex) {
            ex.printStackTrace();
            System.out.println("username does not exist in database");
        }

    }
    public void show() {
        stage.show();
    }
}

/*
try {
            if (userRepo.findByUsername(username).get(0).getUsername().equals(username)) {
                passMatchField.setTextFill(Color.web("#F73331"));
                passMatchField.setText("This username already exists");
                passMatchField.setVisible(true);
            }
        } catch (Exception ex) {
            if (password.equals(confirmPassword)) {
                User newUser = new User(UUID.randomUUID(), username, password, isAdmin);
                System.out.println(newUser.toString());
                //userRepo.save(newUser);
            }
            else {
                passMatchField.setTextFill(Color.web("#F73331"));
                passMatchField.setText("Passwords do not match");
                passMatchField.setVisible(true);
            }
        }
 */