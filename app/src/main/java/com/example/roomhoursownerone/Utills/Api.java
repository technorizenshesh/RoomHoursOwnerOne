package com.example.roomhoursownerone.Utills;

import com.example.roomhoursownerone.AllRoomImage.AllImageModel;
import com.example.roomhoursownerone.CurrentLocation.CityModel;
import com.example.roomhoursownerone.CurrentLocation.StateModel;
import com.example.roomhoursownerone.HomeScreen.ApiModel.GetFavModel;
import com.example.roomhoursownerone.LoginScreen.LoginModel;
import com.example.roomhoursownerone.MessageFragment.ChatConversation;
import com.example.roomhoursownerone.MessageFragment.GetChat;


import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {

    String signUp ="signUp";
    String login ="login";
    String SocialloginApi ="social_login";
    String forgotPassword ="forgetPassword";
    String logout ="logout";
    String getProfile ="get_profile";
    String getState = "get_state";
    String addVideo = "addVideo";
    String notification = "getNotification";
    String updateProfilePicture = "uploadProfilePicture";
    String changePassword = "changePassword";
    String updateAddress = "Create_address";
    String addLike = "addLikes";
    String dislike = "unLikeById";
    String get_city = "get_city";
    String get_room_detailsBY_id = "get_room_detailsBY_id";
    String get_room_image = "get_all_room_image";
    String delete_room_details = "delete_room_details";
    String getALLfriend = "getALLfriend";
    String getBuyingFriend = "getBuyingFriend";
    String getSellingFriend = "getSellingFriend";
    String addInterest = "addInterest";
    String getHome = "getHome";
    String getCommentList = "getCommentList";
    //String add_to_photo = "add_photo_room";
    String add_to_photo = "add_photo_room_new";
    String add_details = "add_details";
    String addBankDetails = "addBankDetails";
    String get_conversation_detail ="get_conversation_detail";
    String get_chat ="get_chat";
    String insert_chat ="insert_chat";

   /* @Multipart
    @POST(add_to_photo)
    Call<ResponseBody>add_to_photo(
            @Part("user_id") RequestBody user_id,
            @Part("room_id ") RequestBody room_id ,
           // @Part MultipartBody.Part part
    );
*/
   @Multipart
   @POST(add_to_photo)
   Call<ResponseBody>add_to_photo(
           @Part("user_id") RequestBody user_id,
           @Part("room_id") RequestBody room_id,
           @Part List<MultipartBody.Part> file);


/*@Multipart
    @POST(add_to_photo)
    Call<ResponseBody>add_to_photo(
            @Part("user_id") RequestBody user_id,
            @Part("room_id ") RequestBody room_id,
            @Part MultipartBody.Part[] surveyImage
    );*/






   @FormUrlEncoded
   @POST(signUp)
    Call<LoginModel>signupApi(
           @Field("first_name") String first_name,
           @Field("email") String email,
           @Field("password") String password,
           @Field("register_id") String register_id,
           @Field("lat") String lat,
           @Field("lon") String lon,
           @Field("type") String type
   );

   @FormUrlEncoded
   @POST(login)
    Call<ResponseBody>loginApi(
           @Field("email") String email,
           @Field("password") String password,
           @Field("register_id") String register_id,
           @Field("lat") String lat,
           @Field("lon") String lon,
           @Field("type") String type
   );

   @FormUrlEncoded
   @POST(addBankDetails)
    Call<ResponseBody>addBankDetails(
           @Field("user_id") String user_id,
           @Field("AccounHolderName") String AccounHolderName,
           @Field("BankName") String BankName,
           @Field("Bank_Account_Number") String Bank_Account_Number,
           @Field("Bank_CODE") String Bank_CODE,
           @Field("area_code") String area_code
   );

    @FormUrlEncoded
    @POST(add_details)
    Call<ResponseBody>add_details(
            @Field("user_id") String user_id,
            @Field("country") String country,
            @Field("street") String street,
            @Field("city") String city,
            @Field("ZipCode") String ZipCode,
            @Field("StreetFlorNumber") String StreetFlorNumber,
            @Field("slectTionMode") String slectTionMode,
            @Field("title") String title,
            @Field("description") String description,
            @Field("PrivateBathRoom") String PrivateBathRoom,
            @Field("AirCondition") String AirCondition,
            @Field("Heating") String Heating,
            @Field("RecommendedPrice") String RecommendedPrice,
            @Field("CustomizedPrices") String CustomizedPrices,
            @Field("price_one_hour") String price_one_hour,
            @Field("Price_two_hours") String Price_two_hours,
            @Field("Price_three_hour") String Price_three_hour,
            @Field("Price_four_sex_hour") String Price_four_sex_hour,
            @Field("Prices_notche") String Prices_notche,
            @Field("room_available_date") String room_available_date,
            @Field("lat") String lat,
            @Field("lon") String lon
    );

   @FormUrlEncoded
   @POST(SocialloginApi)
    Call<ResponseBody>SocialloginApi(
           @Field("first_name") String first_name,
           @Field("email") String email,
           @Field("register_id") String register_id,
           @Field("social_id") String social_id,
           @Field("lat") String lat,
           @Field("lon") String lon

   );

   @FormUrlEncoded
   @POST(getProfile)
    Call<LoginModel>getProfile(
           @Field("user_id") String user_id
   );

   @FormUrlEncoded
    @POST(forgotPassword)
    Call<ResponseBody> forgotPasswordApi(
           @Field("email") String email
   );

   @FormUrlEncoded
    @POST(logout)
    Call<ResponseBody>logoutApi(
           @Field("user_id") String userID
   );



    @Multipart
    @POST(updateProfilePicture)
    Call<ResponseBody>updateProfileImageApi(
            @Part("user_id") RequestBody id,
            @Part MultipartBody.Part part
    );

    @FormUrlEncoded
    @POST(changePassword)
    Call<ResponseBody>changePasswordApi(
            @Field("user_id") String userID,
            @Field("old_password") String oldPassword,
            @Field("new_password") String newPassword
    );

    @FormUrlEncoded
    @POST(updateAddress)
    Call<ResponseBody>updateAddressApi(
            @Field("user_id") String userID,
            @Field("local_address") String localAddress,
            @Field("city") String city,
            @Field("state") String state,
            @Field("country") String country
    );

    @FormUrlEncoded
    @POST(addLike)
    Call<ResponseBody>addLikeApi(
            @Field("user_id") String userID,
            @Field("video_id") String videoId
    );

    @FormUrlEncoded
    @POST(dislike)
    Call<ResponseBody>dislikeApi(
            @Field("user_id") String userID,
            @Field("video_id") String videoID
    );


  @POST(getState)
    Call<StateModel> getState();

    @FormUrlEncoded
    @POST(get_city)
    Call<CityModel>get_city(
            @Field("state_id") String userID
    );

    @FormUrlEncoded
    @POST(get_room_detailsBY_id)
    Call<GetFavModel>get_room_detailsBY_id(
            @Field("user_id") String user_id
    );

  @FormUrlEncoded
    @POST(get_room_image)
    Call<AllImageModel>get_room_image(
            @Field("room_id") String room_id
    );

    @FormUrlEncoded
    @POST(delete_room_details)
    Call<ResponseBody>delete_room_details(
            @Field("room_id") String room_id
    );


    @FormUrlEncoded
    @POST(get_conversation_detail)
    Call<ChatConversation>get_conversation_detail(
            @Field("receiver_id") String receiver_id
    );

    @FormUrlEncoded
    @POST(get_chat)
    Call<GetChat>get_chat(
            @Field("sender_id") String sender_id,
            @Field("receiver_id") String receiver_id
    );

    @FormUrlEncoded
    @POST(insert_chat)
    Call<ResponseBody>insert_chat(
            @Field("sender_id") String sender_id,
            @Field("receiver_id") String receiver_id,
            @Field("chat_message") String chat_message
    );

}
