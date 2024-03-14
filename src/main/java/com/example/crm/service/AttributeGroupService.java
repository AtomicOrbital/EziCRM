package com.example.crm.service;

import com.example.crm.entity.AttributeGroupEntity;
import com.example.crm.payload.request.AttributeGroupRequest;
import com.example.crm.payload.response.AttributeGroupResponse;

import java.util.List;

public interface AttributeGroupService {
    List<AttributeGroupResponse> getAllAttributeGroup();
    AttributeGroupResponse getAttributeGroupById(Long id);
    AttributeGroupResponse createAttributeGroup(AttributeGroupRequest attributeGroupRequest);
    AttributeGroupResponse updateAttributeGroup(Long id, AttributeGroupRequest attributeGroupRequest);
    void deleteAttributeGroup(Long id);
}
