package com.example.crm.service.impl;

import com.example.crm.entity.UserGroupsEntity;
import com.example.crm.payload.request.UserGroupRequest;
import com.example.crm.payload.response.UserGroupResponse;
import com.example.crm.repository.UserGroupRepository;
import com.example.crm.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserGroupServiceImpl implements UserGroupService {
    @Autowired
    private UserGroupRepository userGroupRepository;
    @Override
    public UserGroupResponse createUserGroup(UserGroupRequest request) {
        UserGroupsEntity userGroups = new UserGroupsEntity();
        userGroups.setName(request.getGroupName());

        UserGroupsEntity savedGroup = userGroupRepository.save(userGroups);
        return new UserGroupResponse(savedGroup.getGroupId(), savedGroup.getName());
    }

    @Override
    public List<UserGroupResponse> getAllUserGroups() {
        List<UserGroupsEntity> userGroups = userGroupRepository.findAll();
        return userGroups.stream().map(group -> new UserGroupResponse(
                group.getGroupId(),
                        group.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public UserGroupResponse getUserGroupById(Long id) {
        UserGroupsEntity userGroup = userGroupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("UserGroup not found with id: " + id));
        return new UserGroupResponse(userGroup.getGroupId(), userGroup.getName());
    }

    @Override
    public UserGroupResponse updateUserGroup(Long id, UserGroupRequest request) {
        UserGroupsEntity userGroup = userGroupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("UserGroup not found with id: " + id));
        userGroup.setName(request.getGroupName());

        userGroupRepository.save(userGroup);
        return new UserGroupResponse(userGroup.getGroupId(), userGroup.getName());
    }

    @Override
    public void deleteUserGroup(Long groupId) {
       UserGroupsEntity group = userGroupRepository.findById(groupId)
               .orElseThrow(() -> new EntityNotFoundException(("UserGroup not found with id: " + groupId)));

       userGroupRepository.delete(group);
    }
}
