package cis.ddbogdanov.covidtrackr;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Quick and dirty spring dependency injection - not the best way to do it but it works
 * Lots of help from here: https://medium.com/@alenibric/springboot-and-javafx-cookbook-e8f3dd80deb9
 */

@Component
public class Navigation {
    private static final String ADD_USER_VIEW = "/addNewUser.fxml";
    private static final String LOGIN_VIEW = "/login.fxml";
    private static final String DB_INFO_VIEW = "/databaseInfo.fxml";
    private Stage stage;
    private Parent fxmlRoot;

    @Autowired
    private AddUserController addUserController;
    @Autowired
    private LoginController loginController;
    @Autowired
    private DatabaseViewController dbController;

    public void showAddUserView(int width, int height) {
        show(ADD_USER_VIEW, width, height);
    }
    public void showLoginView(int width, int height) {
        show(LOGIN_VIEW, width, height);
    }
    public void showDbInfoView(int width, int height) {
        show(DB_INFO_VIEW, width, height);
    }

    public void show(String view, int width, int height) {
        Scene scene;
        scene = new Scene(loadFxml(view), width, height);

        if(view.equals(ADD_USER_VIEW) || view.equals(DB_INFO_VIEW)) {
            scene.setFill(Color.TRANSPARENT);
        }

        stage.setScene(scene);
        stage.show();
    }
    private Parent loadFxml(String view) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(view));
        loader.setControllerFactory(param -> getViewController(view));
        try {
            loader.load();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        Parent root = loader.getRoot();
        fxmlRoot = loader.getRoot();
        return root;
    }
    private Object getViewController(String view) {
        if(ADD_USER_VIEW.equals(view)) {
            return addUserController;
        }
        else if(DB_INFO_VIEW.equals(view)) {
            return dbController;
        }
        return loginController;
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Parent getRoot() {
        return fxmlRoot;
    }
}