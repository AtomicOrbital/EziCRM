package com.example.crm.payload.request;

import java.util.List;

public class MultipleUserAttributesRequest {
    private List<UserAttributesRequest> attributes;

    public MultipleUserAttributesRequest() {
    }

    public MultipleUserAttributesRequest(List<UserAttributesRequest> attributes) {
        this.attributes = attributes;
    }

    public List<UserAttributesRequest> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<UserAttributesRequest> attributes) {
        this.attributes = attributes;
    }
}
