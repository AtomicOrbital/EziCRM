package com.example.crm.entity.Id;

import javax.persistence.*;
import java.util.List;

import java.io.Serializable;

@Embeddable
public class UserGroupRelationsId implements Serializable {
    @Column(name = "userid")
    private Long userId;

    @Column(name = "groupid")
    private Long groupId;

    public UserGroupRelationsId() {
    }

    public UserGroupRelationsId(Long userId, Long groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
