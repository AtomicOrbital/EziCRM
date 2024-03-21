package com.example.crm.repository;

import com.example.crm.entity.AttributeGroupEntity;
import com.example.crm.payload.response.AttributeGroupDetailResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttributeGroupRepository extends JpaRepository<AttributeGroupEntity, Long> {
    @Query("SELECT g FROM AttributeGroupEntity g JOIN FETCH g.userAttributesEntitySet")
    List<AttributeGroupEntity> findAllWithAttributes();

    @Query(value = "SELECT g FROM AttributeGroupEntity g JOIN FETCH g.userAttributesEntitySet ",
            countQuery = "SELECT count(g) FROM AttributeGroupEntity g"
    )
    Page<AttributeGroupEntity> findAllWithAttributesWithPaging(Pageable pageable);
}
