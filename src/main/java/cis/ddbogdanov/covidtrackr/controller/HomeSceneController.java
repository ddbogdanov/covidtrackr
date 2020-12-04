package cis.ddbogdanov.covidtrackr.controller;

import cis.ddbogdanov.covidtrackr.model.Snapshot;
import cis.ddbogdanov.covidtrackr.model.SnapshotRepo;
import cis.ddbogdanov.covidtrackr.model.UserRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;

//https://disease.sh/docs/#/COVID-19%3A%20Worldometers/get_v3_covid_19_countries__country_

//TODO: Add menu to change between global and individual country snapshots
//TODO: Add Vbox as wrapper for pie chart, save button, and save status label
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
    @FXML PieChart pieChart;
    @FXML Label dateLabel, totalCasesLabel, totalDeathsLabel, recoveredLabel, saveStatus;
    @FXML JFXButton saveSnapshotButton;

    public HomeSceneController(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.stage = new Stage();
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        pieChart.setLegendVisible(false);
        saveStatus.setVisible(false);

        Snapshot snapshot = fetchWorldOutlook();
        populatePieChart(snapshot);

        saveSnapshotButton.setOnAction(e -> {
            saveSnapshot(snapshot);
        });
    }
    public void show() {
        stage.show();
    }

    private Snapshot fetchWorldOutlook() {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String countryName = "Global";
        String date = localDate.format(formatter);
        int totalCases = 0;
        int totalDeaths = 0;
        int recovered = 0;

        try {
            final HttpUriRequest request = RequestBuilder
                    .get("https://disease.sh/v3/covid-19/all")
                    .build();
            final HttpResponse response = HttpClientBuilder.create().build().execute(request);
            final String jsonString = EntityUtils.toString(response.getEntity());
            final JsonNode json = new ObjectMapper().readTree(jsonString);

            totalCases = json.get("cases").asInt();
            totalDeaths = json.get("deaths").asInt();
            recovered = json.get("recovered").asInt();

            dateLabel.setText(date);
            totalCasesLabel.setText(NumberFormat.getNumberInstance(Locale.US).format(totalCases));
            totalDeathsLabel.setText(NumberFormat.getNumberInstance(Locale.US).format(totalDeaths));
            recoveredLabel.setText(NumberFormat.getNumberInstance(Locale.US).format(recovered));
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        return new Snapshot(UUID.randomUUID(), LoginController.getUser().getId(), countryName, date, totalCases, totalDeaths, recovered);
    }
    private void populatePieChart(Snapshot snapshot) {
        int totalCases = snapshot.getTotalCases();
        int totalDeaths = snapshot.getTotalDeaths();
        int recovered = snapshot.getRecovered();

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Total Cases", totalCases),
                new PieChart.Data("Total Deaths", totalDeaths),
                new PieChart.Data("Recovered", recovered)
        );
        pieChart.setData(pieChartData);
    }

    private void saveSnapshot(Snapshot snapshot) {
        try {
            snapshotRepo.save(snapshot);
            saveStatus.setTextFill(Color.web("#FFFFFF"));
            saveStatus.setText("Snapshot saved!");
            saveStatus.setVisible(true);
        }
        catch(Exception ex) {
            saveStatus.setTextFill(Color.web("#F73331"));
            saveStatus.setText("Something went wrong");
            saveStatus.setVisible(true);
        }
    }
}