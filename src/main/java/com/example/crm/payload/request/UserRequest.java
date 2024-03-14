package com.example.crm.payload.request;

import java.util.Date;

public class UserRequest {
    private String userName;
    private String password;
    private String fullName;
    private Date dateOfBirth;
    private String email;
    private String phone;


    public UserRequest() {
    }

    public UserRequest(String userName, String password, String fullName, Date dateOfBirth, String email, String phone) {
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
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
