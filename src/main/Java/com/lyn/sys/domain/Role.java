package com.lyn.sys.domain;
// 角色实体类
public class Role {

    private Integer roleid; // 角色id

    private String rolename; // 角色名称

    private String roledesc; // 角色描述

    private Integer available; // 该角色是否可用

    // SET、GET
    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public String getRolename() {
        return rolename;
    }
    //
    public void setRolename(String rolename) {
        this.rolename = rolename == null ? null : rolename.trim();
    }

    public String getRoledesc() {
        return roledesc;
    }
    //
    public void setRoledesc(String roledesc) {
        this.roledesc = roledesc == null ? null : roledesc.trim();
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }
}