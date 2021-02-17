package com.zz.crow.vo;

import java.io.Serializable;

public class MemberLoginVO implements Serializable {

    private Integer id;

    private String email;

    private String username;

    private String loginacct;


    public MemberLoginVO(){}

    public MemberLoginVO(Integer id, String email, String username, String loginacct) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.loginacct = loginacct;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLoginacct() {
        return loginacct;
    }

    public void setLoginacct(String loginacct) {
        this.loginacct = loginacct;
    }


    @Override
    public String toString() {
        return "MemberLoginVO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", loginacct='" + loginacct + '\'' +
                '}';
    }
}
