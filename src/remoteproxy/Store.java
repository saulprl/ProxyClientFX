package remoteproxy;

import java.io.Serializable;

public class Store implements Serializable {

    private int id;
    private String name;
    private String address;
    private int manager;
    private String phone;

    public Store() {
    }

    public Store(int id, String name, String address, int manager, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.manager = manager;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getManager() {
        return manager;
    }

    public void setManager(int manager) {
        this.manager = manager;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
