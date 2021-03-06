package cis.ddbogdanov.covidtrackr.model;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Default users:
 *  username: admin
 *  password: password
 *
 *  username: notadmin
 *  password: password
 */

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
    @Column(name="isadmin")
    private boolean isadmin;

    public User() {
        id = UUID.randomUUID();
        this.username = null;
        this.password = null;
        this.isadmin = false;
    }
    public User(UUID id, String username, String password, boolean isadmin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isadmin = isadmin;
    }

    public UUID getId() {
        return id;
    }
    public String getPassword() {
        return password;
    }
    public boolean getIsAdmin() { return isadmin; }

    public String toString() {
        return this.id.toString() + " " + this.username + " " + this.password + " " + this.isadmin;
    }
}
