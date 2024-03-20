package com.example.crm.payload.request;

import com.example.crm.payload.response.RoleResponse;

import java.time.LocalDate;

public class UserRequest {
    private String username;
    private String address;
//    private String password;
    private LocalDate dateOfBirth;
    private String email;
    private String phone;

//    private Integer idRole;


    public UserRequest() {
    }

    public UserRequest(String username, String address, LocalDate dateOfBirth, String email, String phone) {
        this.username = username;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
