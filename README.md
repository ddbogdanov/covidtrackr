# covidtrackr
A simple app that shows the user global covid statistics, allows them to search for covid statistics by country, and visualize this data in a pie chart. It also allows users to take snapshots at the current point in time to save for later.

## Setup
You will need to create an application.properties file under `src/main/resources/` and define:
- `spring.datasource.url`
- `spring.datasource.username`
- `spring.datasource.password`
Additionally, you may choose to define `spring.main.web-application-type` as `none` since a web server is not needed for this app.

Allow maven to build dependencies from the pom.xml, then compile and run the project.

## Technologies used
- Spring Data
- JavaFX
- MySQL
