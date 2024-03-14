package com.example.crm.service;

import com.example.crm.payload.request.UpdateGroupAttributeRequest;
import com.example.crm.payload.request.UserAttributesRequest;
import com.example.crm.payload.response.UserAttributesResponse;

import java.util.List;

public interface UserAttributeService {
    List<UserAttributesResponse> getAttributesByGroupId(Long attributeGroupId);

    List<UserAttributesResponse> getAttributesForUserInGroup(Long userId,Long attributeId);
    UserAttributesResponse createGroupAttribute(Long attributeGroupId, UserAttributesRequest userAttributesRequest);
    UserAttributesResponse createGroupAttributeForUser(Long atributeGroupId, UserAttributesRequest userAttributesRequest);
    UserAttributesResponse updateGroupAtribute(Long id, UpdateGroupAttributeRequest updateGroupAttributeRequest);
    UserAttributesResponse updateGroupAttributeForuser(Long attributeId, UserAttributesRequest userAttributesRequest);
    void deleteUserAttrribute(Long attributeId);
}
