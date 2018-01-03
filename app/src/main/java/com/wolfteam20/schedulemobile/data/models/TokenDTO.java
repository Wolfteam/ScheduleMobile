package com.wolfteam20.schedulemobile.data.models;

import java.util.Date;

/**
 * Created by Efrain Bastidas on 12/31/2017.
 */

public class TokenDTO {
    public String Username;
    public Date CreateDate;
    public Date ExpiricyDate;
    public String AuthenticationToken;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public Date getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(Date createDate) {
        CreateDate = createDate;
    }

    public Date getExpiricyDate() {
        return ExpiricyDate;
    }

    public void setExpiricyDate(Date expiricyDate) {
        ExpiricyDate = expiricyDate;
    }

    public String getAuthenticationToken() {
        return AuthenticationToken;
    }

    public void setAuthenticationToken(String authenticationToken) {
        AuthenticationToken = authenticationToken;
    }
}
