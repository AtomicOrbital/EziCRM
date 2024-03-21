package com.example.crm.controller;

import com.example.crm.entity.UserAttributesEntity;
import com.example.crm.payload.BaseResponse;
import com.example.crm.payload.request.AddAttributeForMultipleUsersRequest;
import com.example.crm.payload.request.MultipleUserAttributesRequest;
import com.example.crm.payload.request.UpdateGroupAttributeRequest;
import com.example.crm.payload.request.UserAttributesRequest;
import com.example.crm.payload.response.UserAttributesResponse;
import com.example.crm.service.UserAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UserAttributesController {
    @Autowired
    private UserAttributeService userAttributeService;

    @GetMapping("/attributes/{attributeGroupId}")
    public ResponseEntity<BaseResponse> getAttributesByGroupId(@PathVariable Long attributeGroupId){
        BaseResponse baseResponse = new BaseResponse();
        try {
            List<UserAttributesResponse> attributesResponses = userAttributeService.getAttributesByGroupId(attributeGroupId);
            if (attributesResponses.isEmpty()) {
                baseResponse.setStatus(HttpStatus.NOT_FOUND.value());
                baseResponse.setMessage("No attributes found for the given group ID");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(baseResponse);
            }
            baseResponse.setStatus(HttpStatus.OK.value());
            baseResponse.setMessage("SUCCESS");
            baseResponse.setData(attributesResponses);
            return ResponseEntity.ok(baseResponse);
        } catch (Exception e) {
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }


    @GetMapping("/{attributeGroupId}/users/{userId}/attributes")
    public ResponseEntity<BaseResponse> getGroupAttributeForUser(@PathVariable Long attributeGroupId, @PathVariable Long userId) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            List<UserAttributesResponse> attributesResponses = userAttributeService.getAttributesForUserInGroup(attributeGroupId, userId);
            if (attributesResponses.isEmpty()) {
                baseResponse.setStatus(HttpStatus.NOT_FOUND.value());
                baseResponse.setMessage("No attributes found for the given group ID");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(baseResponse);
            }
            baseResponse.setStatus(HttpStatus.OK.value());
            baseResponse.setMessage("Successfully fetched attributes for the user in the group.");
            baseResponse.setData(attributesResponses);
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @GetMapping("/search/attributes")
    public ResponseEntity<?> searchUserAttributes(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String value) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            List<UserAttributesResponse> results = userAttributeService.searchDynamicAttributes(name, value);
            if (results.isEmpty()) {
                baseResponse.setStatus(HttpStatus.NOT_FOUND.value());
                baseResponse.setMessage("Không tìm thấy kết quả phù hợp");
                baseResponse.setData(null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(baseResponse);
            } else {
                baseResponse.setStatus(HttpStatus.OK.value());
                baseResponse.setMessage("SUCCESS");
                baseResponse.setData(results);
                return ResponseEntity.ok(baseResponse);
            }
        } catch (Exception e) {
            baseResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            baseResponse.setMessage("Lỗi tìm kiếm: " + e.getMessage());
            baseResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(baseResponse);
        }
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

    @PostMapping("/attribute-groups/attributes/multiple/{attributeGroupId}")
    public ResponseEntity<BaseResponse> createMultipleGroupAttributes(
            @PathVariable Long attributeGroupId,
            @RequestBody MultipleUserAttributesRequest request
            ){
        List<UserAttributesResponse> responses = userAttributeService.createMultipleGroupAttributes(attributeGroupId, request);
        return ResponseEntity.ok(new BaseResponse(200, "Successfully added multiple attributes to the group.", responses));
    }

    @PostMapping("/attribute-groups/{attributeGroupId}/users/attributes")
    public ResponseEntity<BaseResponse> createGroupAttributeForUser(
            @PathVariable Long attributeGroupId,
            @RequestBody UserAttributesRequest request) {

        UserAttributesResponse response = userAttributeService.createGroupAttributeForUser(attributeGroupId, request);
        BaseResponse baseResponse = new BaseResponse(200, "Attribute successfully added to the user within the group", response);
        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping("/add-attribute-for-multiple-users")
    public ResponseEntity<BaseResponse> addAttributeForMultipleUsers(@RequestBody AddAttributeForMultipleUsersRequest request){
        try {
            List<UserAttributesResponse> responses = userAttributeService.addAttributeForMultipleUsers(request);
            BaseResponse baseResponse = new BaseResponse(HttpStatus.OK.value(), "Successfully added attribute for multiple users.", responses);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        } catch (EntityNotFoundException e){
            BaseResponse baseResponse = new BaseResponse(HttpStatus.NOT_FOUND.value(), e.getMessage(), null);
            return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e){
            BaseResponse baseResponse = new BaseResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error", null);
            return new ResponseEntity<>(baseResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Cập nhật thuộc tính trong nhóm
    @PutMapping("/attributeGroupId/attributes/{attributeId}")
    public ResponseEntity<BaseResponse> updateGroupAttribute(
            @PathVariable Long attributeId,
            @RequestBody UpdateGroupAttributeRequest updateGroupAttributeRequest) {

        UserAttributesResponse updatedAttribute = userAttributeService.updateGroupAttribute(attributeId, updateGroupAttributeRequest);
        return ResponseEntity.ok(new BaseResponse(200, "Attribute successfully updated.", updatedAttribute));
    }

    // Cập nhật thuộc tính cho người dùng trong nhóm
    @PutMapping("/{attributeGroupId}/users/attributes/{attributeId}")
    public ResponseEntity<BaseResponse> updateGroupAttributeForUser(
            @PathVariable Long attributeGroupId,
            @PathVariable Long attributeId,
            @RequestBody UserAttributesRequest request) {

        UserAttributesResponse updatedAttribute = userAttributeService.updateGroupAttributeForuser(attributeGroupId, attributeId, request);
        return ResponseEntity.ok(new BaseResponse(200, "Attribute for user successfully updated.", updatedAttribute));
    }

    @DeleteMapping("/attributes/{attributeId}")
    public ResponseEntity<BaseResponse> deleteUserAttribute(@PathVariable Long attributeId){
        userAttributeService.deleteUserAttrribute(attributeId);
        return ResponseEntity.ok(new BaseResponse(200, "Attribute successfully deleted ", null));
    }
}
