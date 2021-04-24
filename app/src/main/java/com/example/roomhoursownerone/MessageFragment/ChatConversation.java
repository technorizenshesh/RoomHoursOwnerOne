package com.example.roomhoursownerone.MessageFragment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatConversation {

    @SerializedName("result")
    @Expose
    private List<ChatConversationData> result = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<ChatConversationData> getResult() {
        return result;
    }

    public void setResult(List<ChatConversationData> result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


