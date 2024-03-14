package com.example.crm.payload.request;

public class AttributeGroupRequest {
    private String name;

    public AttributeGroupRequest() {
    }

    public AttributeGroupRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
