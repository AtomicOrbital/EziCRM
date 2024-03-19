package com.example.crm.controller;

import com.example.crm.entity.AttributeGroupEntity;
import com.example.crm.payload.BaseResponse;
import com.example.crm.payload.request.AttributeGroupRequest;
import com.example.crm.payload.response.AttributeGroupResponse;
import com.example.crm.service.AttributeGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<BaseResponse> createAttributeGroup(AttributeGroupRequest attributeGroupRequest){
        BaseResponse baseResponse = new BaseResponse();
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
