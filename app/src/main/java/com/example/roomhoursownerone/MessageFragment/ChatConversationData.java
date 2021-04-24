package com.example.roomhoursownerone.MessageFragment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatConversationData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lon")
    @Expose
    private String lon;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("social_id")
    @Expose
    private String socialId;
    @SerializedName("date_time")
    @Expose
    private String dateTime;
    @SerializedName("ios_register_id")
    @Expose
    private Object iosRegisterId;
    @SerializedName("register_id")
    @Expose
    private String registerId;
    @SerializedName("no_of_message")
    @Expose
    private Integer noOfMessage;
    @SerializedName("last_message")
    @Expose
    private String lastMessage;
    @SerializedName("request_id")
    @Expose
    private String requestId;
    @SerializedName("request_title")
    @Expose
    private Object requestTitle;
    @SerializedName("date")
    @Expose
    private Object date;
    @SerializedName("sender_id")
    @Expose
    private String senderId;
    @SerializedName("receiver_id")
    @Expose
    private String receiverId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Object getIosRegisterId() {
        return iosRegisterId;
    }

    public void setIosRegisterId(Object iosRegisterId) {
        this.iosRegisterId = iosRegisterId;
    }

    public String getRegisterId() {
        return registerId;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    public Integer getNoOfMessage() {
        return noOfMessage;
    }

    public void setNoOfMessage(Integer noOfMessage) {
        this.noOfMessage = noOfMessage;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Object getRequestTitle() {
        return requestTitle;
    }

    public void setRequestTitle(Object requestTitle) {
        this.requestTitle = requestTitle;
    }

    public Object getDate() {
        return date;
    }

    public void setDate(Object date) {
        this.date = date;
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


}
