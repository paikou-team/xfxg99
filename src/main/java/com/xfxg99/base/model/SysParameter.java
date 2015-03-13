package com.xfxg99.base.model;

public class SysParameter {
    private Integer id;

    private String key;

    private String value;

    private String name;

    private String groupname;

    private String edittype;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getEdittype() {
        return edittype;
    }

    public void setEdittype(String edittype) {
        this.edittype = edittype;
    }
}