package com.example.crm.payload.request;

public class UserAttributesRequest {
    private Long userId;
    private String name;
    private String value;

    public UserAttributesRequest() {
    }

    public UserAttributesRequest(Long userId, String name, String value) {
        this.userId = userId;
        this.name = name;
        this.value = value;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
