package com.islington.nepalbloodbankdonor.Pojo;

import java.io.Serializable;

/**
 * Created by rupesh on 11/23/16.
 */

public class UserDetail implements Serializable {
    String id,name,username,password,email,address,phone;

    public UserDetail() {
    }

    public UserDetail(String id, String name,String username, String password, String email, String address, String phone) {
        this.id = id;
        this.name = name;
        this.username=username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
