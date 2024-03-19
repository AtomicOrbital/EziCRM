package com.example.crm.controller;

import com.example.crm.payload.BaseResponse;
import com.example.crm.payload.request.UserGroupRequest;
import com.example.crm.payload.response.UserGroupResponse;
import com.example.crm.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-groups")
public class UserGroupController {

    @Autowired
    private UserGroupService userGroupService;

    @PostMapping
    public ResponseEntity<BaseResponse> createUserGroup(@RequestBody UserGroupRequest request) {
        UserGroupResponse response = userGroupService.createUserGroup(request);
        return ResponseEntity.ok(new BaseResponse(200, "User Group successfully created.", response));
    }

    @GetMapping
    public ResponseEntity<BaseResponse> getAllUserGroups() {
        List<UserGroupResponse> responses = userGroupService.getAllUserGroups();
        return ResponseEntity.ok(new BaseResponse(200, "User Groups fetched successfully.", responses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getUserGroupById(@PathVariable Long id) {
        UserGroupResponse response = userGroupService.getUserGroupById(id);
        return ResponseEntity.ok(new BaseResponse(200, "User Group fetched successfully.", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> updateUserGroup(@PathVariable Long id, @RequestBody UserGroupRequest request) {
        UserGroupResponse response = userGroupService.updateUserGroup(id, request);
        return ResponseEntity.ok(new BaseResponse(200, "User Group successfully updated.", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteUserGroup(@PathVariable Long id) {
        userGroupService.deleteUserGroup(id);
        return ResponseEntity.ok(new BaseResponse(200, "User Group successfully deleted.", null));
    }
}
