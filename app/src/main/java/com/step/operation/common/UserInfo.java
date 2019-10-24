package com.step.operation.common;

public class UserInfo {
    private String d_code;
    private String token;
    private Integer userId;
    private Integer role;
    private String opUserPhone;

    public String getD_code() {
        return d_code;
    }

    public void setD_code(String d_code) {
        this.d_code = d_code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getOpUserPhone() {
        return opUserPhone;
    }

    public void setOpUserPhone(String opUserPhone) {
        this.opUserPhone = opUserPhone;
    }
}
