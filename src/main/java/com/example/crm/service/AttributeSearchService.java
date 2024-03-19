package com.example.crm.service;

import com.example.crm.payload.request.AttributeSearchRequest;
import com.example.crm.payload.response.UserAttributesResponse;

import java.util.List;

public interface AttributeSearchService {
    List<UserAttributesResponse> searchAttributes(Long groupId, String name);
}
