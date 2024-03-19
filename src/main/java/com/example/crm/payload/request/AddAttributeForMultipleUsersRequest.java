package com.example.crm.payload.request;

import java.util.List;

public class AddAttributeForMultipleUsersRequest {
    private List<Long> userId;
    private String attributeName;
    private String attributeValue;
    private Long attributeGroupId;

    public AddAttributeForMultipleUsersRequest() {
    }

    public AddAttributeForMultipleUsersRequest(List<Long> userId, String attributeName, String attributeValue, Long attributeGroupId) {
        this.userId = userId;
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
        this.attributeGroupId = attributeGroupId;
    }

    public List<Long> getUserId() {
        return userId;
    }

    public void setUserId(List<Long> userId) {
        this.userId = userId;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public Long getAttributeGroupId() {
        return attributeGroupId;
    }

    public void setAttributeGroupId(Long attributeGroupId) {
        this.attributeGroupId = attributeGroupId;
    }
}
