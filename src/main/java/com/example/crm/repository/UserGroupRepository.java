package com.example.crm.repository;

import com.example.crm.entity.UserGroupsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroupsEntity, Long> {
}
