package com.example.crm.payload.response;

import java.util.Date;
import java.util.List;

public class UserResponse {
    private Long userId;
    private String userName;
    private String address;
    private int age;
    private String email;
    private String phone;
    private List<AttributeResponse> dynamicAttributes;
    private Date createdAt;

    private String roleName;



    public static class AttributeResponse {
        private String name;
        private String value;

        public AttributeResponse() {
        }

        public AttributeResponse(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public UserResponse() {
    }

    public UserResponse(Long userId, String userName, String address, int age, String email, String phone, List<AttributeResponse> dynamicAttributes, Date createdAt, String roleName) {
        this.userId = userId;
        this.userName = userName;
        this.address = address;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.dynamicAttributes = dynamicAttributes;
        this.createdAt = createdAt;
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<AttributeResponse> getDynamicAttributes() {
        return dynamicAttributes;
    }

    public void setDynamicAttributes(List<AttributeResponse> dynamicAttributes) {
        this.dynamicAttributes = dynamicAttributes;
    }
}
