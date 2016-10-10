package models;

import javax.persistence.*;

@Entity
@Table(name = "driver")
public class Driver {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "status")
    private int status;
    @ManyToOne
    private Area area;

    public void setOrder(Order order) {
        this.order = order;
    }

    public Order getOrder() {

        return order;
    }

    private Order order;

    public Driver() {
    }

    public String getLogin() {
        return login;
    }

    public Driver(String login, String password) {
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
