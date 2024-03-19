package com.example.crm.payload.request;

public class UserGroupRequest {
    private String groupName;

    public UserGroupRequest() {
    }

    public UserGroupRequest(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
