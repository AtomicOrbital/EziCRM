package com.example.crm.service;

import com.example.crm.payload.request.AttributeSearchRequest;
import com.example.crm.payload.response.UserAttributesResponse;

import java.util.List;

public interface AttributeSearch {
    List<UserAttributesResponse> searchAttributes(AttributeSearchRequest searchRequest)
}
