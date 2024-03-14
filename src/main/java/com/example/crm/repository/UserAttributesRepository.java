package com.example.crm.repository;

import com.example.crm.entity.UserAttributesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAttributesRepository extends JpaRepository<UserAttributesEntity, Long> {
    List<UserAttributesEntity> findByAttributeGroupEntity_AttributeGroupId(Long attributeGroupId);
    List<UserAttributesEntity> findByUserEntity_UserIdAndAttributeGroupEntity_AttributeGroupId(Long userId, Long attributeGroupid);

}
