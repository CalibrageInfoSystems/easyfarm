package com.cis.easyfarm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenObject {

    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("DeviseToken")
    @Expose
    private String deviseToken;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDeviseToken() {
        return deviseToken;
    }

    public void setDeviseToken(String deviseToken) {
        this.deviseToken = deviseToken;
    }

}
