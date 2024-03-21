package com.example.crm.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

import java.util.*;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid") // Đảm bảo tên cột phản ánh chính xác cột trong DB
    private Long userId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "address")
    private String address;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "created_at",nullable = false, updatable = false)
//    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at",nullable = false)
//    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "id_role")
    private RoleEntity role;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserGroupRelationsEntity> userGroupsGroupRelationsEntities;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAttributesEntity> userAttributes = new ArrayList<>();

    public UserEntity() {

    }

    public UserEntity(Long userId, String username, String password, String address, LocalDate dateOfBirth, String email, String phone, Date createdAt, Date updatedAt, RoleEntity role, List<UserGroupRelationsEntity> userGroupsGroupRelationsEntities, List<UserAttributesEntity> userAttributes) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phone = phone;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.role = role;
        this.userGroupsGroupRelationsEntities = userGroupsGroupRelationsEntities;
        this.userAttributes = userAttributes;
    }

    public List<UserAttributesEntity> getUserAttributes() {
        return userAttributes;
    }

    public void setUserAttributes(List<UserAttributesEntity> userAttributes) {
        this.userAttributes = userAttributes;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<UserGroupRelationsEntity> getUserGroupsGroupRelationsEntities() {
        return userGroupsGroupRelationsEntities;
    }

    public void setUserGroupsGroupRelationsEntities(List<UserGroupRelationsEntity> userGroupsGroupRelationsEntities) {
        this.userGroupsGroupRelationsEntities = userGroupsGroupRelationsEntities;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }
    @PrePersist
    protected void onCreate() {
        createdAt = new Date(); // Thiết lập thời gian hiện tại khi entity được tạo
//        updatedAt = createdAt;  // updatedAt sẽ giống như createdAt
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date(); // Cập nhật thời gian hiện tại khi entity được cập nhật
    }

}

