package com.example.crm.payload.response;

import java.util.Date;

public class UserResponse {
    private Long userId;
    private String userName;
    private String fullName;
    private String email;
    private String phone;
    private Date createdAt;

    public UserResponse() {
    }

    public UserResponse(Long userId, String userName, String fullName, String email, String phone, Date createdAt) {
        this.userId = userId;
        this.userName = userName;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.createdAt = createdAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
