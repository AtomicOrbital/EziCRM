package com.example.crm.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "userattributes")
public class UserAttributesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attributeid")
    private Long attributeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attributegroupid")
    private AttributeGroupEntity attributeGroupEntity;

    @Column(name = "name",nullable = false, unique = true)
    private String name;

    @Lob
    @Column(name = "value")
    private String value;

    public UserAttributesEntity() {
    }

    public UserAttributesEntity(Long attributeId, UserEntity userEntity, AttributeGroupEntity attributeGroupEntity, String name, String value) {
        this.attributeId = attributeId;
        this.userEntity = userEntity;
        this.attributeGroupEntity = attributeGroupEntity;
        this.name = name;
        this.value = value;
    }

    public Long getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Long attributeId) {
        this.attributeId = attributeId;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public AttributeGroupEntity getAttributeGroupEntity() {
        return attributeGroupEntity;
    }

    public void setAttributeGroupEntity(AttributeGroupEntity attributeGroupEntity) {
        this.attributeGroupEntity = attributeGroupEntity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
