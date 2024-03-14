package com.example.crm.service.impl;

import com.example.crm.entity.AttributeGroupEntity;
import com.example.crm.entity.UserAttributesEntity;
import com.example.crm.entity.UserEntity;
import com.example.crm.payload.request.UpdateGroupAttributeRequest;
import com.example.crm.payload.request.UserAttributesRequest;
import com.example.crm.payload.response.UserAttributesResponse;
import com.example.crm.repository.AttributeGroupRepository;
import com.example.crm.repository.UserAttributesRepository;
import com.example.crm.repository.UserRepository;
import com.example.crm.service.UserAttributeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserAttributeServiceImpl implements UserAttributeService {
    @Autowired
    private AttributeGroupRepository attributeGroupRepository;

    @Autowired
    private UserAttributesRepository userAttributesRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<UserAttributesResponse> getAttributesByGroupId(Long attributeGroupId) {
        List<UserAttributesEntity> attributes = userAttributesRepository.findByAttributeGroupEntity_AttributeGroupId(attributeGroupId);
        return attributes.stream()
                .map(attribute -> new UserAttributesResponse(
                        attribute.getAttributeId(),
                        attribute.getUserEntity() != null ? attribute.getUserEntity().getUserId() : null,
                        attribute.getAttributeGroupEntity().getAttributeGroupId(),
                        attribute.getName(),
                        attribute.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserAttributesResponse> getAttributesForUserInGroup(Long userId, Long attributeGroupId) {
        List<UserAttributesEntity> attributes = userAttributesRepository.findByUserEntity_UserIdAndAttributeGroupEntity_AttributeGroupId(userId, attributeGroupId);
        return attributes.stream()
                .map(attribute -> new UserAttributesResponse(
                        attribute.getAttributeId(),
                        userId,
                        attributeGroupId,
                        attribute.getName(),
                        attribute.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public UserAttributesResponse createGroupAttribute(Long attributeGroupId, UserAttributesRequest userAttributesRequest) {
        AttributeGroupEntity attributeGroup = attributeGroupRepository.findById(attributeGroupId)
                .orElseThrow(() -> new EntityNotFoundException("AttributeGroup not found with id: " + attributeGroupId));

        UserAttributesEntity attributes = new UserAttributesEntity();
        attributes.setName(userAttributesRequest.getName());
        attributes.setValue(userAttributesRequest.getValue());
        attributes.setAttributeGroupEntity(attributeGroup);
        attributes = userAttributesRepository.save(attributes);

        return new UserAttributesResponse(attributes.getAttributeId(),
                null,
                attributes.getAttributeGroupEntity().getAttributeGroupId(),
                attributes.getName(),
                attributes.getValue());
    }

    @Override
    public UserAttributesResponse createGroupAttributeForUser(Long atributeGroupId, UserAttributesRequest userAttributesRequest) {
        AttributeGroupEntity attributeGroup = attributeGroupRepository.findById(atributeGroupId)
                .orElseThrow(() -> new EntityNotFoundException("AttibuteGroup not found with id: " + atributeGroupId));

        UserEntity user = userRepository.findById(userAttributesRequest.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userAttributesRequest.getUserId()));

        // Tạo và lưu thông tin mới với thông tin từ request
        UserAttributesEntity attributes = new UserAttributesEntity();
        attributes.setName(userAttributesRequest.getName());
        attributes.setValue(userAttributesRequest.getValue());
        attributes.setAttributeGroupEntity(attributeGroup);

        attributes.setUserEntity(user);
        attributes = userAttributesRepository.save(attributes);
        return new UserAttributesResponse(attributes.getAttributeId(),
                user.getUserId(),
                attributeGroup.getAttributeGroupId(),
                attributes.getName(),
                attributes.getValue());
    }

    @Override
    public UserAttributesResponse updateGroupAtribute(Long id, UpdateGroupAttributeRequest updateGroupAttributeRequest) {
        UserAttributesEntity attributes = userAttributesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Attribute not found with id: " + id));

        attributes.setName(updateGroupAttributeRequest.getName());
        attributes.setValue(updateGroupAttributeRequest.getValue());

        attributes = userAttributesRepository.save(attributes);
        return new UserAttributesResponse(attributes.getAttributeId(),
                attributes.getUserEntity() != null ? attributes.getUserEntity().getUserId() : null,
                attributes.getAttributeGroupEntity().getAttributeGroupId(),
                attributes.getName(),attributes.getValue());
    }

    @Override
    public UserAttributesResponse updateGroupAttributeForuser(Long attributeId, UserAttributesRequest request) {
        UserAttributesEntity attribute = userAttributesRepository.findById(attributeId)
                .orElseThrow(() -> new EntityNotFoundException("Attribute not found with id: " + attributeId));

        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + request.getUserId()));

        attribute.setName(request.getName());
        attribute.setValue(request.getValue());
        attribute.setUserEntity(user);
        attribute = userAttributesRepository.save(attribute);

        return new UserAttributesResponse(attribute.getAttributeId(), user.getUserId(), attribute.getAttributeGroupEntity().getAttributeGroupId(), attribute.getName(), attribute.getValue());

    }

    @Override
    public void deleteUserAttrribute(Long attributeId) {
        if(!userAttributesRepository.existsById(attributeId)) {
            throw new EntityNotFoundException("Attribute not found with id: " + attributeId);
        }
        userAttributesRepository.deleteById(attributeId);
    }
}
