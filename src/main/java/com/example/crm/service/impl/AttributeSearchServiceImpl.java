package com.example.crm.service.impl;

import com.example.crm.entity.UserAttributesEntity;
import com.example.crm.payload.response.UserAttributesResponse;
import com.example.crm.repository.UserAttributesRepository;
import com.example.crm.service.AttributeSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttributeSearchServiceImpl implements AttributeSearchService {
    @Autowired
    private UserAttributesRepository userAttributesRepository;

    private UserAttributesResponse convertToResponse(UserAttributesEntity entity){
        UserAttributesResponse response = new UserAttributesResponse();
        response.setAttributeId(entity.getAttributeId());
        response.setUserId(entity.getUserEntity() != null ? entity.getUserEntity().getUserId() : null);
        response.setAttributeGroupId(entity.getAttributeGroupEntity().getAttributeGroupId());
        response.setAttributeName(entity.getName());
        response.setAttributeValue(entity.getValue());
        return response;
    }

    @Override
    public List<UserAttributesResponse> searchAttributes(Long groupId, String name) {
        List<UserAttributesEntity> attributes = userAttributesRepository.findByGroupIdAndAttributeNameLike(groupId, name);

        return attributes.stream().map(this::convertToResponse).collect(Collectors.toList());
    }
}
