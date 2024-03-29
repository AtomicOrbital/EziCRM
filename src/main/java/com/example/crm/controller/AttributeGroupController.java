package com.example.crm.controller;

import com.example.crm.entity.AttributeGroupEntity;
import com.example.crm.payload.BaseResponse;
import com.example.crm.payload.request.AttributeGroupRequest;
import com.example.crm.payload.response.AttributeGroupDetailResponse;
import com.example.crm.payload.response.AttributeGroupResponse;
import com.example.crm.service.AttributeGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/attribute-groups")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AttributeGroupController {
    @Autowired
    private AttributeGroupService attributeGroupService;

    @GetMapping
    public ResponseEntity<BaseResponse> getAllAtributeGroups(){
        BaseResponse baseResponse = new BaseResponse();
        try {
            List<AttributeGroupResponse> groups = attributeGroupService.getAllAttributeGroup();
            baseResponse.setStatus(200);
            baseResponse.setMessage("Fetch Success");
            baseResponse.setData(groups);
            return ResponseEntity.ok(baseResponse);
        } catch (Exception e){
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @GetMapping("/details")
    public ResponseEntity<?> getAllAttributesWithDetails(){
        BaseResponse baseResponse = new BaseResponse();
        try {
            List<AttributeGroupDetailResponse> groupsWithDetails = attributeGroupService.getAllAttributeGroupsWithDetails();
            baseResponse.setStatus(200);
            baseResponse.setMessage("SUCCESS");
            baseResponse.setData(groupsWithDetails);
            return ResponseEntity.ok(baseResponse);
        } catch (Exception e){
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/details-with-paging")
    public ResponseEntity<?> getAllGroupsAndAttributesWithPaging(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size
    ){
        BaseResponse baseResponse = new BaseResponse();
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<AttributeGroupDetailResponse> attributeGroupDetailResponses = attributeGroupService.getAllGroupsAndAttributesWithPaging(pageable);
            baseResponse.setStatus(200);
            baseResponse.setMessage("SUCCESS");
            baseResponse.setData(attributeGroupDetailResponses);
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getAttributeGroupById(@PathVariable Long id){
        BaseResponse baseResponse = new BaseResponse();
        try {
            AttributeGroupResponse attributeGroupResponse = attributeGroupService.getAttributeGroupById(id);
            baseResponse.setStatus(200);
            baseResponse.setMessage("Fetch Success");
            baseResponse.setData(attributeGroupResponse);
            return ResponseEntity.ok(baseResponse);
        }catch (Exception e){
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @PostMapping
    public ResponseEntity<BaseResponse> createAttributeGroup(@Valid AttributeGroupRequest attributeGroupRequest, BindingResult bindingResult){
        BaseResponse baseResponse = new BaseResponse();
        if(bindingResult.hasErrors()){
            String errorMessgae = bindingResult.getFieldError().getDefaultMessage();
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(errorMessgae);
            return ResponseEntity.badRequest().body(baseResponse);
        }
        try {
            AttributeGroupResponse savedAttributeGroup = attributeGroupService.createAttributeGroup(attributeGroupRequest);
            baseResponse.setStatus(201);
            baseResponse.setMessage("Create Attribute Group Success");
            baseResponse.setData(savedAttributeGroup);
            return ResponseEntity.ok(baseResponse);
        } catch(Exception e){
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> updateAttributeGroup(@PathVariable Long id, @RequestBody AttributeGroupRequest attributeGroupRequest){
        BaseResponse baseResponse = new BaseResponse();
        try {
            AttributeGroupResponse updatedAttributeGroup = attributeGroupService.updateAttributeGroup(id, attributeGroupRequest);
            baseResponse.setStatus(HttpStatus.OK.value());
            baseResponse.setMessage("Updated Attribute Group Success");
            baseResponse.setData(updatedAttributeGroup);
            return ResponseEntity.ok(baseResponse);
        } catch(Exception e){
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteAttributeGroup(@PathVariable Long id){
        BaseResponse baseResponse = new BaseResponse();
        try {
            attributeGroupService.deleteAttributeGroup(id);
            baseResponse.setStatus(HttpStatus.OK.value());
            baseResponse.setMessage("Delete Attribute Group Success");
            baseResponse.setData(null); // Không cần trả về dữ liệu khi xóa
            return ResponseEntity.ok(baseResponse);
        } catch(Exception e){
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(baseResponse);
        }
    }
}
