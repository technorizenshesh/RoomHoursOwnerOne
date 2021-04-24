package com.example.roomhoursownerone.HomeScreen.ApiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetFavModelData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("ZipCode")
    @Expose
    private String zipCode;
    @SerializedName("StreetFlorNumber")
    @Expose
    private String streetFlorNumber;
    @SerializedName("slectTionMode")
    @Expose
    private String slectTionMode;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lon")
    @Expose
    private String lon;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("PrivateBathRoom")
    @Expose
    private String privateBathRoom;
    @SerializedName("AirCondition")
    @Expose
    private String airCondition;
    @SerializedName("Heating")
    @Expose
    private String heating;
    @SerializedName("RecommendedPrice")
    @Expose
    private String recommendedPrice;
    @SerializedName("CustomizedPrices")
    @Expose
    private String customizedPrices;
    @SerializedName("price_one_hour")
    @Expose
    private String priceOneHour;
    @SerializedName("Price_two_hours")
    @Expose
    private String priceTwoHours;
    @SerializedName("Price_three_hour")
    @Expose
    private String priceThreeHour;
    @SerializedName("Price_four_sex_hour")
    @Expose
    private String priceFourSexHour;
    @SerializedName("Prices_notche")
    @Expose
    private String pricesNotche;
    @SerializedName("room_available_date")
    @Expose
    private String roomAvailableDate;
    @SerializedName("IsFav")
    @Expose
    private String isFav;
    @SerializedName("image")
    @Expose
    private List<String> image = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreetFlorNumber() {
        return streetFlorNumber;
    }

    public void setStreetFlorNumber(String streetFlorNumber) {
        this.streetFlorNumber = streetFlorNumber;
    }

    public String getSlectTionMode() {
        return slectTionMode;
    }

    public void setSlectTionMode(String slectTionMode) {
        this.slectTionMode = slectTionMode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrivateBathRoom() {
        return privateBathRoom;
    }

    public void setPrivateBathRoom(String privateBathRoom) {
        this.privateBathRoom = privateBathRoom;
    }

    public String getAirCondition() {
        return airCondition;
    }

    public void setAirCondition(String airCondition) {
        this.airCondition = airCondition;
    }

    public String getHeating() {
        return heating;
    }

    public void setHeating(String heating) {
        this.heating = heating;
    }

    public String getRecommendedPrice() {
        return recommendedPrice;
    }

    public void setRecommendedPrice(String recommendedPrice) {
        this.recommendedPrice = recommendedPrice;
    }

    public String getCustomizedPrices() {
        return customizedPrices;
    }

    public void setCustomizedPrices(String customizedPrices) {
        this.customizedPrices = customizedPrices;
    }

    public String getPriceOneHour() {
        return priceOneHour;
    }

    public void setPriceOneHour(String priceOneHour) {
        this.priceOneHour = priceOneHour;
    }

    public String getPriceTwoHours() {
        return priceTwoHours;
    }

    public void setPriceTwoHours(String priceTwoHours) {
        this.priceTwoHours = priceTwoHours;
    }

    public String getPriceThreeHour() {
        return priceThreeHour;
    }

    public void setPriceThreeHour(String priceThreeHour) {
        this.priceThreeHour = priceThreeHour;
    }

    public String getPriceFourSexHour() {
        return priceFourSexHour;
    }

    public void setPriceFourSexHour(String priceFourSexHour) {
        this.priceFourSexHour = priceFourSexHour;
    }

    public String getPricesNotche() {
        return pricesNotche;
    }

    public void setPricesNotche(String pricesNotche) {
        this.pricesNotche = pricesNotche;
    }

    public String getRoomAvailableDate() {
        return roomAvailableDate;
    }

    public void setRoomAvailableDate(String roomAvailableDate) {
        this.roomAvailableDate = roomAvailableDate;
    }

    public String getIsFav() {
        return isFav;
    }

    public void setIsFav(String isFav) {
        this.isFav = isFav;
    }

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }


}
