package com.cis.easyfarm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class notificationsRequest {

    @SerializedName("Index")
    @Expose
    private Integer index;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("Date")
    @Expose
    private String date;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}


