package com.example.crm.controller;

import com.example.crm.payload.BaseResponse;
import com.example.crm.payload.response.UserAttributesResponse;
import com.example.crm.service.AttributeSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AttributeSearchController {
    @Autowired
    private AttributeSearchService attributeSearchService;
    @GetMapping("/attributes/search/{groupId}")
    public ResponseEntity<BaseResponse> searchAttributesInGroup(
            @PathVariable Long groupId,
            @RequestParam(required = false) String name
    ){
        List<UserAttributesResponse> responses = attributeSearchService.searchAttributes(groupId, name);
        return ResponseEntity.ok(new BaseResponse(200, "Search results", responses));
    }
}
