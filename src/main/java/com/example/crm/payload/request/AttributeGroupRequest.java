package com.example.crm.payload.request;

import javax.validation.constraints.NotBlank;

public class AttributeGroupRequest {
    @NotBlank(message = "group name cannot be empty")
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
