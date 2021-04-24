package com.example.roomhoursownerone.AllRoomImage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllImageModel {

    @SerializedName("result")
    @Expose
    private List<AllImageModelData> result = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

    public List<AllImageModelData> getResult() {
        return result;
    }

    public void setResult(List<AllImageModelData> result) {
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
