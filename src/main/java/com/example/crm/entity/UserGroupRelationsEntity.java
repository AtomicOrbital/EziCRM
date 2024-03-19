package com.example.crm.entity;


import com.example.crm.entity.Id.UserGroupRelationsId;
import javax.persistence.*;
import java.util.List;

import java.io.Serializable;

@Entity(name = "usergrouprelations")
public class UserGroupRelationsEntity implements Serializable {
    @EmbeddedId
    private UserGroupRelationsId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    private UserEntity userEntity;

    @MapsId("groupId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupid")
    private UserGroupsEntity userGroupsEntity;

    public UserGroupRelationsEntity() {
    }

    public UserGroupRelationsEntity(UserGroupRelationsId id, UserEntity userEntity, UserGroupsEntity userGroupsEntity) {
        this.id = id;
        this.userEntity = userEntity;
        this.userGroupsEntity = userGroupsEntity;
    }

    public UserGroupRelationsId getId() {
        return id;
    }

    public void setId(UserGroupRelationsId id) {
        this.id = id;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public UserGroupsEntity getUserGroupsEntity() {
        return userGroupsEntity;
    }

    public void setUserGroupsEntity(UserGroupsEntity userGroupsEntity) {
        this.userGroupsEntity = userGroupsEntity;
    }
}
