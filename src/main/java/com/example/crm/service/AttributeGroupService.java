package com.example.crm.service;

import com.example.crm.entity.AttributeGroupEntity;
import com.example.crm.payload.request.AttributeGroupRequest;
import com.example.crm.payload.response.AttributeGroupDetailResponse;
import com.example.crm.payload.response.AttributeGroupResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AttributeGroupService {
    List<AttributeGroupResponse> getAllAttributeGroup();
    List<AttributeGroupDetailResponse> getAllAttributeGroupsWithDetails();
    Page<AttributeGroupDetailResponse> getAllGroupsAndAttributesWithPaging(Pageable pageable);
    AttributeGroupResponse getAttributeGroupById(Long id);
    AttributeGroupResponse createAttributeGroup(AttributeGroupRequest attributeGroupRequest);
    AttributeGroupResponse updateAttributeGroup(Long id, AttributeGroupRequest attributeGroupRequest);
    void deleteAttributeGroup(Long id);
}
