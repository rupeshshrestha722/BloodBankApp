package com.islington.nepalbloodbankdonor.Pojo;

import java.io.Serializable;



public class DonorDetail implements Serializable{

    String id,name,phone,email,address,bloodGroup;

    public DonorDetail(String id,String name,String phone, String email, String address,String bloodGroup) {

        this.id = id;
        this.name= name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.bloodGroup = bloodGroup;
    }

    public DonorDetail(String name, String bloodGroup, String phone) {
        this.name = name;
        this.bloodGroup = bloodGroup;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
