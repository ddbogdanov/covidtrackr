package cis.ddbogdanov.covidtrackr.controller;

import cis.ddbogdanov.covidtrackr.model.Snapshot;
import cis.ddbogdanov.covidtrackr.model.SnapshotRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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

import static org.springframework.util.StringUtils.capitalize;

//https://disease.sh/docs/#/COVID-19%3A%20Worldometers/get_v3_covid_19_countries__country_

@Component
@FxmlView("/HomeSceneController.fxml")
public class HomeSceneController implements Initializable {

    private final FxWeaver fxWeaver;
    private static Snapshot snapshot;
    private static final String API_BASE_URL = "https://disease.sh/v3/covid-19/";
    private Stage stage;

    @Autowired
    private SnapshotRepo snapshotRepo;

    @FXML private AnchorPane pane;
    @FXML public PieChart pieChart;
    @FXML public Label dateLabel, totalCasesLabel, totalDeathsLabel, recoveredLabel, saveStatus, outlookLabel;
    @FXML private JFXButton saveSnapshotButton, searchButton;
    @FXML private JFXTextField searchTextField;

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

        snapshot = fetchOutlook("all");
        populatePieChart(snapshot);

        searchButton.setOnAction(e -> {
            snapshot = fetchOutlook(capitalize(searchTextField.getText()));
            setOutlookLabel(capitalize(searchTextField.getText()));
            populatePieChart(snapshot);
        });
        saveSnapshotButton.setOnAction(e -> {
            saveSnapshot(snapshot);
        });
    }
    public AnchorPane getScene() {
        return pane;
    }

    private Snapshot fetchOutlook(String countryName) {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String date = localDate.format(formatter);
        int totalCases = 0;
        int totalDeaths = 0;
        int recovered = 0;

        if(countryName.equals("global") || countryName.equals("Global")) {
            countryName = "all";
        }

        try {
            final HttpUriRequest request = RequestBuilder
                    .get(buildURL(countryName))
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

        if(countryName.equals("all") || countryName.equals("All")) {
            countryName = "Global";
        }

        return new Snapshot(UUID.randomUUID(), LoginController.getUser().getId(), countryName, date, totalCases, totalDeaths, recovered);
    }
    private String buildURL(String countryName) {
        if(countryName.equals("all") || countryName.equals("All") || countryName.equals("global") || countryName.equals("Global")) {
            return API_BASE_URL + "all";
        }
        else {
            return API_BASE_URL + "countries/" + countryName;
        }
    }
    private void setOutlookLabel(String countryName) {
        if(countryName.equals("all") || countryName.equals("All") || countryName.equals("global") || countryName.equals("Global")) {
            outlookLabel.setText("Global Outlook");
        }
        else {
            outlookLabel.setText("Outlook for " + countryName);
        }
    }
    private void populatePieChart(Snapshot snapshot) {
        int totalCases = snapshot.getTotalCases();
        int totalDeaths = snapshot.getTotalDeaths();
        int recovered = snapshot.getRecovered();

        pieChart.getData().clear();
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

    public void loadOutlook(Snapshot snapshot) {
        dateLabel.setText(snapshot.getDate());
        totalCasesLabel.setText(NumberFormat.getNumberInstance(Locale.US).format(snapshot.getTotalCases()));
        totalDeathsLabel.setText(NumberFormat.getNumberInstance(Locale.US).format(snapshot.getTotalDeaths()));
        recoveredLabel.setText(NumberFormat.getNumberInstance(Locale.US).format(snapshot.getRecovered()));
        setOutlookLabel(snapshot.getCountryName());
        populatePieChart(snapshot);
        System.out.println(recoveredLabel.getText());
    }
}
