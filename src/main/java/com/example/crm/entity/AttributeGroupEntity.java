package com.example.crm.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "attributegroups")
public class AttributeGroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attributegroupid")
    private Long attributeGroupId;

    @Column(name = "name",nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "attributeGroupEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAttributesEntity> userAttributesEntitySet;

    public AttributeGroupEntity() {
    }

    public AttributeGroupEntity(Long attributeGroupId, String name, List<UserAttributesEntity> userAttributesEntitySet) {
        this.attributeGroupId = attributeGroupId;
        this.name = name;
        this.userAttributesEntitySet = userAttributesEntitySet;
    }

    public Long getAttributeGroupId() {
        return attributeGroupId;
    }

    public void setAttributeGroupId(Long attributeGroupId) {
        this.attributeGroupId = attributeGroupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserAttributesEntity> getUserAttributesEntitySet() {
        return userAttributesEntitySet;
    }

    public void setUserAttributesEntitySet(List<UserAttributesEntity> userAttributesEntitySet) {
        this.userAttributesEntitySet = userAttributesEntitySet;
    }
}
