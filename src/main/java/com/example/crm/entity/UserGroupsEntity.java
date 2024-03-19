package com.example.crm.entity;

import com.example.crm.entity.Id.UserGroupRelationsId;
import javax.persistence.*;
import java.util.List;

import java.util.*;

@Entity(name = "usergroups")
public class UserGroupsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "groupid")
    private Long groupId;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "userGroupsEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserGroupRelationsEntity> userGroupRelationsEntities;

    public UserGroupsEntity() {
    }

    public UserGroupsEntity(Long groupId, String name, List<UserGroupRelationsEntity> userGroupRelationsEntities) {
        this.groupId = groupId;
        this.name = name;
        this.userGroupRelationsEntities = userGroupRelationsEntities;
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

    public List<UserGroupRelationsEntity> getUserGroupRelationsEntities() {
        return userGroupRelationsEntities;
    }

    public void setUserGroupRelationsEntities(List<UserGroupRelationsEntity> userGroupRelationsEntities) {
        this.userGroupRelationsEntities = userGroupRelationsEntities;
    }
}
