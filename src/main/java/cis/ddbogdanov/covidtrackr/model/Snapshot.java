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
    @Column(name="id")
    private UUID userid;
    @Column(name="countryname")
    private String countryName;
    @Column(name="date")
    private String date;
    @Column(name="totalcases")
    private int totalCases;
    @Column(name="totaldeaths")
    private int totalDeaths;
    @Column(name="newcases")
    private int newCases;

    public Snapshot() {

    }
    public Snapshot(UUID id, UUID userid, String countryName, String date, int totalCases, int totalDeaths, int newCases) {
        this.id = id;
        this.userid = id;
        this.countryName = countryName;
        this.date = date;
        this.totalCases = totalCases;
        this.totalDeaths = totalDeaths;
        this.newCases = newCases;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    public void setUserid(UUID userid) {
        this.userid = userid;
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
    public void setNewCases(int newCases) {
        this.newCases = newCases;
    }

    public UUID getId() {
        return id;
    }
    public UUID getUserid() {
        return userid;
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
    public int getNewCases() {
        return newCases;
    }

    public Snapshot getObject() {
        return new Snapshot(this.id, this.userid, this.countryName, this.date, this.totalCases, this.totalDeaths, this.newCases);
    }
    @Override
    public String toString() {
        return id.toString() + " " + userid.toString() + " " + countryName + " " + date + " " + totalCases + " " + totalDeaths + " " + newCases;
    }
}
