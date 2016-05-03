package org.meiskalt7.entity;

import javax.persistence.*;

@Entity
@javax.persistence.Table(name = "users")
public class User {

    @Id
    @Column
    @GeneratedValue
    private int id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String usertype_id;

    @ManyToOne
    @JoinColumn(name = "usertype_id", nullable = false, insertable = false, updatable = false)
    private UserType userType = new UserType();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsertype_id() {
        return usertype_id;
    }

    public void setUsertype_id(String usertype_id) {
        this.usertype_id = usertype_id;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
