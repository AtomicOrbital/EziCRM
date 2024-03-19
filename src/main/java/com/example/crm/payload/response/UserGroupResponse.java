package com.example.crm.payload.response;

public class UserGroupResponse {
    private Long groupId;
    private String name;

    public UserGroupResponse() {
    }

    public UserGroupResponse(Long groupId, String name) {
        this.groupId = groupId;
        this.name = name;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
