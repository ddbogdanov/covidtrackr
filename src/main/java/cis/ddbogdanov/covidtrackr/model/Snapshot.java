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

    @Override
    public String toString() {
        return "Date: " + this.date + "; Country: " + countryName;
    }
}