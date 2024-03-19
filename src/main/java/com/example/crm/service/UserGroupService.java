package com.example.crm.service;

import com.example.crm.payload.request.UserGroupRequest;
import com.example.crm.payload.response.UserGroupResponse;

import java.util.List;

public interface UserGroupService {
    UserGroupResponse createUserGroup(UserGroupRequest userGroupRequest);
    List<UserGroupResponse> getAllUserGroups();
    UserGroupResponse getUserGroupById(Long id);
    UserGroupResponse updateUserGroup(Long id, UserGroupRequest userGroupRequest);
    void deleteUserGroup(Long id);
}
