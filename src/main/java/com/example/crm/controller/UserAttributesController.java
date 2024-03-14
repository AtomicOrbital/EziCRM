package com.example.crm.controller;

import com.example.crm.payload.BaseResponse;
import com.example.crm.payload.request.UpdateGroupAttributeRequest;
import com.example.crm.payload.request.UserAttributesRequest;
import com.example.crm.payload.response.UserAttributesResponse;
import com.example.crm.service.UserAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserAttributesController {
    @Autowired
    private UserAttributeService userAttributeService;

    @GetMapping("/attributes/{attributeGroupId}")
    public ResponseEntity<BaseResponse> getAttributesByGroupId(@PathVariable Long attributeGroupId){
        List<UserAttributesResponse> attributesResponses = userAttributeService.getAttributesByGroupId(attributeGroupId);
        return ResponseEntity.ok(new BaseResponse(200, "Successfully fetched attributes for the group", attributesResponses));
    }

    @GetMapping("/{attributeGroupId}/users/{userId}/attributes")
    public ResponseEntity<BaseResponse> getGroupAttributeForUser(@PathVariable Long attributeGroupId, @PathVariable Long userId) {
        List<UserAttributesResponse> attributesResponses = userAttributeService.getAttributesForUserInGroup(attributeGroupId, userId);
        return ResponseEntity.ok(new BaseResponse(200, "Successfully fetched attributes for the user in the group.", attributesResponses));
    }

    @PostMapping("/attribute-groups/{attributeGroupId}/attributes")
    public ResponseEntity<BaseResponse> createAttribueInGroup(
            @PathVariable Long attributeGroupId,
            @RequestBody UserAttributesRequest request
            ){
        BaseResponse baseResponse = new BaseResponse();
        try {
            UserAttributesResponse response = userAttributeService.createGroupAttribute(attributeGroupId, request);
            baseResponse.setStatus(201);
            baseResponse.setMessage("Attribute successfully added to the group");
            baseResponse.setData(response);
            return ResponseEntity.ok(baseResponse);
        } catch (Exception e){
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @PostMapping("/attribute-groups/{attributeGroupId}/users/{userId}/attributes")
    public ResponseEntity<BaseResponse> createGroupAttributeForUser(
            @PathVariable Long attributeGroupId,
            @PathVariable Long userId,
            @RequestBody UserAttributesRequest request) {

        request.setUserId(userId);

        UserAttributesResponse response = userAttributeService.createGroupAttributeForUser(attributeGroupId, request);
        BaseResponse baseResponse = new BaseResponse(200, "Attribute successfully added to the user within the group", response);
        return ResponseEntity.ok(baseResponse);
    }

    // Cập nhật thuộc tính trong nhóm
    @PutMapping("/attributeGroupId/attributes/{attributeId}")
    public ResponseEntity<BaseResponse> updateGroupAttribute(
            @PathVariable Long attributeId,
            @RequestBody UpdateGroupAttributeRequest updateGroupAttributeRequest) {

        UserAttributesResponse updatedAttribute = userAttributeService.updateGroupAtribute(attributeId, updateGroupAttributeRequest);
        return ResponseEntity.ok(new BaseResponse(200, "Attribute successfully updated.", updatedAttribute));
    }

    // Cập nhật thuộc tính cho người dùng trong nhóm
    @PutMapping("/attributeGroupId/users/userId/attributes/{attributeId}")
    public ResponseEntity<BaseResponse> updateGroupAttributeForUser(
            @PathVariable Long attributeId,
            @RequestBody UserAttributesRequest request) {

        UserAttributesResponse updatedAttribute = userAttributeService.updateGroupAttributeForuser(attributeId, request);
        return ResponseEntity.ok(new BaseResponse(200, "Attribute for user successfully updated.", updatedAttribute));
    }

    @DeleteMapping("/attributes/{attributeId}")
    public ResponseEntity<BaseResponse> deleteUserAttribute(@PathVariable Long attributeId){
        userAttributeService.deleteUserAttrribute(attributeId);
        return ResponseEntity.ok(new BaseResponse(200, "Attribute successfully deleted ", null));
    }
}
