package models;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "type")
    private int type;

    public User() {
    }

    public String getLogin() {
        return login;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {

        return id;
    }

    public String getPassword() {
        return password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
