package com.example.crm.payload.response;

public class UserAttributesResponse {
    private Long attributeId;
    private Long userId;
    private String userName;
    private Long attributeGroupId;
    private String attributeName;
    private String attributeValue;
    private String groupName;


    public UserAttributesResponse() {
    }

    public UserAttributesResponse(Long attributeId, Long userId, String userName, Long attributeGroupId, String attributeName, String attributeValue, String groupName) {
        this.attributeId = attributeId;
        this.userId = userId;
        this.userName = userName;
        this.attributeGroupId = attributeGroupId;
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
        this.groupName = groupName;
    }

    public Long getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Long attributeId) {
        this.attributeId = attributeId;
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

    public Long getAttributeGroupId() {
        return attributeGroupId;
    }

    public void setAttributeGroupId(Long attributeGroupId) {
        this.attributeGroupId = attributeGroupId;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
