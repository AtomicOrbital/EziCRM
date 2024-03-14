package com.example.crm.entity;

import com.example.crm.entity.Id.UserGroupRelationsId;
import jakarta.persistence.*;

import java.util.*;

@Entity(name = "usergroups")
public class UserGroupsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "groupID")
    private Long groupId;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "userGroupsEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserGroupRelationsEntity> userGroupRelations;

    public UserGroupsEntity() {
    }

    public UserGroupsEntity(Long groupId, String name, List<UserGroupRelationsEntity> userGroupRelations) {
        this.groupId = groupId;
        this.name = name;
        this.userGroupRelations = userGroupRelations;
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

    public List<UserGroupRelationsEntity> getUserGroupRelations() {
        return userGroupRelations;
    }

    public void setUserGroupRelations(List<UserGroupRelationsEntity> userGroupRelations) {
        this.userGroupRelations = userGroupRelations;
    }
}
