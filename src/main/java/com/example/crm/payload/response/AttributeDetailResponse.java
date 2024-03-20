package com.example.crm.payload.response;

public class AttributeDetailResponse {
    private Long attributeId;
    private String name;
    private String value;

    public AttributeDetailResponse() {
    }

    public AttributeDetailResponse(Long attributeId, String name, String value) {
        this.attributeId = attributeId;
        this.name = name;
        this.value = value;
    }

    public Long getAttributeId() {
        return attributeId;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
