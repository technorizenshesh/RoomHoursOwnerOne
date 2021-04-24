package com.example.roomhoursownerone.HomeScreen.ApiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetFavModel {

    @SerializedName("result")
    @Expose
    private List<GetFavModelData> result = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

    public List<GetFavModelData> getResult() {
        return result;
    }

    public void setResult(List<GetFavModelData> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
