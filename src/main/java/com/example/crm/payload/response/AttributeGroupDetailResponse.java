package com.example.crm.payload.response;

import java.util.List;

public class AttributeGroupDetailResponse {
    private Long attributeGroupId;
    private String name;
    private List<AttributeDetailResponse> attributes;

    public AttributeGroupDetailResponse() {
    }

    public AttributeGroupDetailResponse(Long attributeGroupId, String name, List<AttributeDetailResponse> attributes) {
        this.attributeGroupId = attributeGroupId;
        this.name = name;
        this.attributes = attributes;
    }

    public Long getAttributeGroupId() {
        return attributeGroupId;
    }

    public void setAttributeGroupId(Long attributeGroupId) {
        this.attributeGroupId = attributeGroupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AttributeDetailResponse> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeDetailResponse> attributes) {
        this.attributes = attributes;
    }
}
