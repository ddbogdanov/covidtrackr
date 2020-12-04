package cis.ddbogdanov.covidtrackr.model;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name="snapshottable")
public class Snapshot {

    @Id
    @Type(type="org.hibernate.type.UUIDCharType")
    @Column(name="id")
    private UUID id;
    @Type(type="org.hibernate.type.UUIDCharType")
    @Column(name="userid")
    private UUID userId;
    @Column(name="countryname")
    private String countryName;
    @Column(name="date")
    private String date;
    @Column(name="totalcases")
    private int totalCases;
    @Column(name="totaldeaths")
    private int totalDeaths;
    @Column(name="recovered")
    private int recovered;

    public Snapshot() {

    }
    public Snapshot(UUID id, UUID userId, String countryName, String date, int totalCases, int totalDeaths, int recovered) {
        this.id = id;
        this.userId = userId;
        this.countryName = countryName;
        this.date = date;
        this.totalCases = totalCases;
        this.totalDeaths = totalDeaths;
        this.recovered = recovered;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    public void setUserId(UUID userId) {
        this.userId = userId;
    }
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setTotalCases(int totalCases) {
        this.totalCases = totalCases;
    }
    public void setTotalDeaths(int totalDeaths) {
        this.totalDeaths = totalDeaths;
    }
    public void setRecovered(int newCases) {
        this.recovered = newCases;
    }

    public UUID getId() {
        return id;
    }
    public UUID getUserId() {
        return userId;
    }
    public String getCountryName() {
        return countryName;
    }
    public String getDate() {
        return date;
    }
    public int getTotalCases() {
        return totalCases;
    }
    public int getTotalDeaths() {
        return totalDeaths;
    }
    public int getRecovered() {
        return recovered;
    }

    public Snapshot getObject() {
        return new Snapshot(this.id, this.userId, this.countryName, this.date, this.totalCases, this.totalDeaths, this.recovered);
    }
    @Override
    public String toString() {
        return id.toString() + " " + userId.toString() + " " + countryName + " " + date + " " + totalCases + " " + totalDeaths + " " + recovered;
    }
}
