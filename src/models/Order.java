package models;

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
    @Column(name = "address")
    private String address;

    public Order() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {

        return id;
    }

    public int getStatus() {
        return status;
    }

    public String getAddress() {
        return address;
    }

}
