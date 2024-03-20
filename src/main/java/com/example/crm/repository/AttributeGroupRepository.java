package com.example.crm.repository;

import com.example.crm.entity.AttributeGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttributeGroupRepository extends JpaRepository<AttributeGroupEntity, Long> {
    @Query("SELECT g FROM AttributeGroupEntity g LEFT JOIN g.userAttributesEntitySet")
    List<AttributeGroupEntity> findAllWithAttributes();
}
