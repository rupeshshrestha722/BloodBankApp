package com.islington.nepalbloodbankdonor.Pojo;

import java.io.Serializable;

/**
 * Created by rupesh on 12/1/16.
 */

public class Blood implements Serializable {
    String id,name,blood_group,quantity,contact,location;

    public Blood() {
    }

    public Blood(String id, String name, String blood_group, String quantity, String contact, String location) {
        this.id = id;
        this.name = name;
        this.blood_group = blood_group;
        this.quantity = quantity;
        this.contact = contact;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
