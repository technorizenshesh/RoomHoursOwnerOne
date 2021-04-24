package com.example.roomhoursownerone.MessageFragment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetChatData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sender_id")
    @Expose
    private String senderId;
    @SerializedName("receiver_id")
    @Expose
    private String receiverId;
    @SerializedName("chat_message")
    @Expose
    private String chatMessage;
    @SerializedName("chat_image")
    @Expose
    private String chatImage;
    @SerializedName("chat_audio")
    @Expose
    private String chatAudio;
    @SerializedName("chat_video")
    @Expose
    private String chatVideo;
    @SerializedName("chat_document")
    @Expose
    private String chatDocument;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lon")
    @Expose
    private String lon;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("sender_detail")
    @Expose
    private SenderDetailModel senderDetail;
    @SerializedName("receiver_detail")
    @Expose
    private ReceiverChatModel receiverDetail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }

    public String getChatImage() {
        return chatImage;
    }

    public void setChatImage(String chatImage) {
        this.chatImage = chatImage;
    }

    public String getChatAudio() {
        return chatAudio;
    }

    public void setChatAudio(String chatAudio) {
        this.chatAudio = chatAudio;
    }

    public String getChatVideo() {
        return chatVideo;
    }

    public void setChatVideo(String chatVideo) {
        this.chatVideo = chatVideo;
    }

    public String getChatDocument() {
        return chatDocument;
    }

    public void setChatDocument(String chatDocument) {
        this.chatDocument = chatDocument;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public SenderDetailModel getSenderDetail() {
        return senderDetail;
    }

    public void setSenderDetail(SenderDetailModel senderDetail) {
        this.senderDetail = senderDetail;
    }

    public ReceiverChatModel getReceiverDetail() {
        return receiverDetail;
    }

    public void setReceiverDetail(ReceiverChatModel receiverDetail) {
        this.receiverDetail = receiverDetail;
    }
}


