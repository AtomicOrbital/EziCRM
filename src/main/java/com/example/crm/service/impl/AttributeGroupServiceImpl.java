package com.example.crm.service.impl;

import com.example.crm.entity.AttributeGroupEntity;
import com.example.crm.payload.request.AttributeGroupRequest;
import com.example.crm.payload.response.AttributeGroupResponse;
import com.example.crm.repository.AttributeGroupRepository;
import com.example.crm.service.AttributeGroupService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttributeGroupServiceImpl implements AttributeGroupService {
    @Autowired
    private AttributeGroupRepository attributeGroupRepository;

    private AttributeGroupResponse convertToResponse(AttributeGroupEntity attributeGroupEntity){
        return new AttributeGroupResponse(attributeGroupEntity.getAttributeGroupId(), attributeGroupEntity.getName());
    }
    @Override
    public List<AttributeGroupResponse> getAllAttributeGroup() {
        List<AttributeGroupEntity> attributeGroupEntities = attributeGroupRepository.findAll();
        return attributeGroupEntities.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AttributeGroupResponse getAttributeGroupById(Long id) {
        AttributeGroupEntity attributeGroupEntity = attributeGroupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AttributeGroup not found with id: " + id));
        return convertToResponse(attributeGroupEntity);
    }

    @Override
    public AttributeGroupResponse createAttributeGroup(AttributeGroupRequest attributeGroupRequest) {
        AttributeGroupEntity attributeGroup = new AttributeGroupEntity();
        attributeGroup.setName(attributeGroupRequest.getName());
        AttributeGroupEntity savedAttributeGroup = attributeGroupRepository.save(attributeGroup);
        return convertToResponse(savedAttributeGroup);
    }

    @Override
    public AttributeGroupResponse updateAttributeGroup(Long id, AttributeGroupRequest attributeGroupRequest) {
        AttributeGroupEntity attributeGroup = attributeGroupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AttributeGroup not found with id: " + id));
        attributeGroup.setName(attributeGroupRequest.getName());
        AttributeGroupEntity updatedAttributeGroup = attributeGroupRepository.save(attributeGroup);
        return convertToResponse(updatedAttributeGroup);
    }

    @Override
    public void deleteAttributeGroup(Long id) {
        AttributeGroupEntity attributeGroup = attributeGroupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AttributeGroup not found with id: )" + id));
        attributeGroupRepository.delete(attributeGroup);
    }
}
