package com.example.crm.payload.response;

public class UserAttributesResponse {
    private Long attributeid;
    private Long userid;
    private Long attributegroupid;
    private String name;
    private String value;

    public UserAttributesResponse() {
    }

    public UserAttributesResponse(Long attributeid, Long userid, Long attributegroupid, String name, String value) {
        this.attributeid = attributeid;
        this.userid = userid;
        this.attributegroupid = attributegroupid;
        this.name = name;
        this.value = value;
    }

    public Long getAttributeid() {
        return attributeid;
    }

    public void setAttributeid(Long attributeid) {
        this.attributeid = attributeid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getAttributegroupid() {
        return attributegroupid;
    }

    public void setAttributegroupid(Long attributegroupid) {
        this.attributegroupid = attributegroupid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
