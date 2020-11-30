package cis.ddbogdanov.covidtrackr.model;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name="usertable")
public class User {

    @Id
    @Type(type="org.hibernate.type.UUIDCharType")
    @Column(name="id")
    private UUID id;
    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;

    public User() {
        id = UUID.randomUUID();
        this.username = null;
        this.password = null;
    }
    public User(UUID id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public User getObject() {
        return new User(this.id, this.username, this.password);
    }
}
