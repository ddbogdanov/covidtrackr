package cis.ddbogdanov.covidtrackr;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CovidTrackrApplication extends Application {

    private ConfigurableApplicationContext springContext;
    private Parent root;
    private double xOffset, yOffset;

    @Override
    public void init() throws Exception {
        springContext = SpringApplication.run(CovidTrackrApplication.class);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        loader.setControllerFactory(springContext::getBean);
        root = loader.load();
    }
    @Override
    public void start(Stage primaryStage) {

        root.setOnMousePressed(pressEvent -> {
            xOffset = pressEvent.getSceneX();
            yOffset = pressEvent.getSceneY();
        });
        root.setOnMouseDragged(dragEvent -> {
            primaryStage.setX(dragEvent.getScreenX() - xOffset);
            primaryStage.setY(dragEvent.getScreenY() - yOffset);
        });

        primaryStage.setTitle("COVID Trackr");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }
    @Override
    public void stop() {
        springContext.close();
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(CovidTrackrApplication.class, args);
    }

}
