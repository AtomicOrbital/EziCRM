package com.example.crm.service.impl;

import com.example.crm.entity.AttributeGroupEntity;
import com.example.crm.entity.UserAttributesEntity;
import com.example.crm.entity.UserEntity;
import com.example.crm.payload.request.AddAttributeForMultipleUsersRequest;
import com.example.crm.payload.request.MultipleUserAttributesRequest;
import com.example.crm.payload.request.UpdateGroupAttributeRequest;
import com.example.crm.payload.request.UserAttributesRequest;
import com.example.crm.payload.response.UserAttributesResponse;
import com.example.crm.repository.AttributeGroupRepository;
import com.example.crm.repository.UserAttributesRepository;
import com.example.crm.repository.UserRepository;
import com.example.crm.service.UserAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
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
                .map(attribute -> {
                    UserAttributesResponse response = new UserAttributesResponse();
                    response.setAttributeId(attribute.getAttributeId());
                    response.setAttributeName(attribute.getName());
                    response.setAttributeValue(attribute.getValue());
                    response.setAttributeGroupId(attribute.getAttributeGroupEntity().getAttributeGroupId());
                    response.setGroupName(attribute.getAttributeGroupEntity().getName());
                    if (attribute.getUserEntity() != null) {
                        response.setUserId(attribute.getUserEntity().getUserId());
                        response.setUserName(attribute.getUserEntity().getUsername());
                    }
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<UserAttributesResponse> getAttributesForUserInGroup(Long userId, Long attributeGroupId) {
        List<UserAttributesEntity> attributes = userAttributesRepository.findByUserEntity_UserIdAndAttributeGroupEntity_AttributeGroupId(userId, attributeGroupId);
        return attributes.stream()
                .map(attribute -> {
                    UserAttributesResponse response = new UserAttributesResponse();
                    response.setAttributeId(attribute.getAttributeId());
                    response.setUserId(userId);
                    response.setAttributeGroupId(attributeGroupId);
                    response.setAttributeName(attribute.getName());
                    response.setAttributeValue(attribute.getValue());


                    if (attribute.getUserEntity() != null) {
                        response.setUserName(attribute.getUserEntity().getUsername());
                    }
                    if (attribute.getAttributeGroupEntity() != null) {
                        response.setGroupName(attribute.getAttributeGroupEntity().getName());
                    }

                    return response;
                })
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
                null,
                attributeGroup.getAttributeGroupId(),
                attributes.getName(),
                attributes.getValue(),
                attributeGroup.getName());
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
                user.getUsername(),
                attributeGroup.getAttributeGroupId(),
                attributes.getName(),
                attributes.getValue(),
                attributeGroup.getName());
    }

    @Override
    public List<UserAttributesResponse> createMultipleGroupAttributes(Long attributeGroupId, MultipleUserAttributesRequest request) {
        AttributeGroupEntity attributeGroup = attributeGroupRepository.findById(attributeGroupId)
                .orElseThrow(() -> new EntityNotFoundException("AttributeGroup not found with id: " + attributeGroupId));

        List<UserAttributesEntity> attributesList = request.getAttributes().stream()
                .map(attr -> {
                    UserAttributesEntity newAttr = new UserAttributesEntity();
                    newAttr.setName(attr.getName());
                    newAttr.setValue(attr.getValue());
                    newAttr.setAttributeGroupEntity(attributeGroup);

                    return userAttributesRepository.save(newAttr);
                }).collect(Collectors.toList());

        return attributesList.stream().map(attr -> new UserAttributesResponse(
                attr.getAttributeId(),
                (attr.getUserEntity() != null) ? attr.getUserEntity().getUserId() : null,
                (attr.getUserEntity() != null) ? attr.getUserEntity().getUsername() : null,
                attributeGroup.getAttributeGroupId(),
                attr.getName(),
                attr.getValue(),
                attributeGroup.getName()
        )).collect(Collectors.toList());
    }


    @Override
    public UserAttributesResponse updateGroupAttribute(Long id, UpdateGroupAttributeRequest updateGroupAttributeRequest) {
        UserAttributesEntity attributes = userAttributesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Attribute not found with id: " + id));

        attributes.setName(updateGroupAttributeRequest.getName());
        attributes.setValue(updateGroupAttributeRequest.getValue());

        attributes = userAttributesRepository.save(attributes);
        return new UserAttributesResponse(attributes.getAttributeId(),
                (attributes.getUserEntity() != null) ? attributes.getUserEntity().getUserId() : null,
                (attributes.getUserEntity() != null) ? attributes.getUserEntity().getUsername() : null,
                attributes.getAttributeGroupEntity().getAttributeGroupId(),
                attributes.getName(),
                attributes.getValue(),
                attributes.getAttributeGroupEntity().getName()
        );
    }


    @Override
    public UserAttributesResponse updateGroupAttributeForuser(Long attributeGroupId,Long userId,Long attributeId, UserAttributesRequest request) {
        UserAttributesEntity attribute = userAttributesRepository.findById(attributeId)
                .orElseThrow(() -> new EntityNotFoundException("Attribute not found with id: " + attributeId));

        AttributeGroupEntity attributeGroup = attributeGroupRepository.findById(attributeGroupId)
                .orElseThrow(() -> new EntityNotFoundException("AttributeGroup not found with id: " + attributeGroupId));

        if(!attribute.getAttributeGroupEntity().getAttributeGroupId().equals(attributeGroupId)){
            throw new EntityNotFoundException("Attribute dose not belong to the specified group.");
        }

        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + request.getUserId()));

        attribute.setName(request.getName());
        attribute.setValue(request.getValue());
        attribute.setUserEntity(user);
        attribute = userAttributesRepository.save(attribute);

        UserAttributesEntity updatedAttribute = userAttributesRepository.save(attribute);

        return new UserAttributesResponse(
                updatedAttribute.getAttributeId(),
                user.getUserId(),
                user.getUsername(),
                attributeGroup.getAttributeGroupId(),
                updatedAttribute.getName(),
                updatedAttribute.getValue(),
                attributeGroup.getName()
        );
    }

    @Override
    public List<UserAttributesResponse> addAttributeForMultipleUsers(AddAttributeForMultipleUsersRequest request) {
        List<UserAttributesResponse> responses = new ArrayList<>();
        AttributeGroupEntity attributeGroup = attributeGroupRepository.findById(request.getAttributeGroupId())
                .orElseThrow(() -> new EntityNotFoundException("Attribute not found with id: " + request.getAttributeGroupId()));

        for(Long userId : request.getUserId()){
            UserEntity user = userRepository.findById(userId)
                    .orElseThrow(()-> new EntityNotFoundException("User not found with id: " + userId));

            UserAttributesEntity newAttributes = new UserAttributesEntity();
            newAttributes.setUserEntity(user);
            newAttributes.setAttributeGroupEntity(attributeGroup);
            newAttributes.setName(request.getAttributeName());
            newAttributes.setValue(request.getAttributeValue());

            userAttributesRepository.save(newAttributes);

            responses.add(new UserAttributesResponse(
                    newAttributes.getAttributeId(),
                    user.getUserId(),
                    user.getUsername(),
                    attributeGroup.getAttributeGroupId(),
                    newAttributes.getName(),
                    newAttributes.getValue(),
                    attributeGroup.getName()
            ));
        }
        return responses;
    }

    @Override
    public void deleteUserAttrribute(Long attributeId) {
        if(!userAttributesRepository.existsById(attributeId)) {
            throw new EntityNotFoundException("Attribute not found with id: " + attributeId);
        }
        userAttributesRepository.deleteById(attributeId);
    }
}
