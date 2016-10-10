package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "taxi_order")
public class Order {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "status")
    private int status;
    @OneToOne
    private Driver driver;
    @Column(name = "payment")
    private int payment;
    @Column(name = "address")
    private String address;
    @ManyToOne
    private Area area;

    public Order() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public int getId() {

        return id;
    }

    public int getStatus() {
        return status;
    }

    public Driver getDriver() {
        return driver;
    }

    public int getPayment() {
        return payment;
    }

    public String getAddress() {
        return address;
    }

    public Area getArea() {
        return area;
    }
}
