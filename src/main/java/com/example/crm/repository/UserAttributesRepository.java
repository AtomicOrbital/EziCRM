package com.example.crm.repository;

import com.example.crm.entity.UserAttributesEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAttributesRepository extends JpaRepository<UserAttributesEntity, Long> {
    List<UserAttributesEntity> findByAttributeGroupEntity_AttributeGroupId(Long attributeGroupId);
    List<UserAttributesEntity> findByUserEntity_UserIdAndAttributeGroupEntity_AttributeGroupId(Long userId, Long attributeGroupid);
    @Query("SELECT u FROM UserAttributesEntity u WHERE u.attributeGroupEntity.attributeGroupId = :groupId AND (:name IS NULL OR u.name LIKE %:name%)")
    List<UserAttributesEntity> findByGroupIdAndAttributeNameLike(@Param("groupId") Long groupId, @Param("name") String name);
    List<UserAttributesEntity> findByUserEntityUserId(Long userId);

    @Query("SELECT u FROM UserAttributesEntity u WHERE u.attributeGroupEntity.attributeGroupId IN (:groupIds)")
    List<UserAttributesEntity> findAllByAttributeGroupIdsIn(@Param("groupIds") List<Long> groupIds);
}
