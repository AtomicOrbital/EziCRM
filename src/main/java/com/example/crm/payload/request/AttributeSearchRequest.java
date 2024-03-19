package com.example.crm.payload.request;

public class AttributeSearchRequest {
    private Long attributeGroupId;
    private String searchTerm;

    public AttributeSearchRequest() {
    }

    public AttributeSearchRequest(Long attributeGroupId, String searchTerm) {
        this.attributeGroupId = attributeGroupId;
        this.searchTerm = searchTerm;
    }

    public Long getAttributeGroupId() {
        return attributeGroupId;
    }

    public void setAttributeGroupId(Long attributeGroupId) {
        this.attributeGroupId = attributeGroupId;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }
}
