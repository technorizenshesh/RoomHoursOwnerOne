package com.example.roomhoursownerone.CalenderScreen;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.roomhoursownerone.AddPhoto.AddPhoto;
import com.example.roomhoursownerone.Preference;
import com.example.roomhoursownerone.R;
import com.example.roomhoursownerone.Utills.RetrofitClients;
import com.example.roomhoursownerone.Utills.SessionManager;
import com.savvi.rangedatepicker.CalendarPickerView;
import com.savvi.rangedatepicker.SubTitle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.AlgorithmParameterGenerator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalenderActivityTwo extends AppCompatActivity {

    CalendarPickerView calendar;
    RelativeLayout RR_next;
    RelativeLayout RR_reset;

    String PrivateBath ="";
    String airCondition ="";
    String heating_one ="";

    String  City="";
    String  Community="";
    String  Country="";
    String  Title="";
    String  description="";

    String SelectionMode="";
    String oneHour_price="";
    String twoHour_price="";
    String threeHour_price="";
    String fourHour_price="";
    String night_price="";
    String  Street_number_floor="";
    String  ZipCode="";
    String android_id ="";
    private ProgressBar progressBar;
    private SessionManager sessionManager;

    int endDateSeleted = 0;
    int SatrtDate = 0;
    Calendar nextYear;
    Calendar lastYear;

    String latitude ="";
    String longitude ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_two);

        findView();

   Intent intent=getIntent();
        if(intent !=null)
        {
            City = intent.getStringExtra("City").toString();
            Community = intent.getStringExtra("Community").toString();
            Country = intent.getStringExtra("Country").toString();
            Street_number_floor = intent.getStringExtra("Street_number_floor").toString();
            ZipCode = intent.getStringExtra("ZipCode").toString();
            Title = intent.getStringExtra("Title").toString();
            description = intent.getStringExtra("description").toString();
            PrivateBath = intent.getStringExtra("PrivateBath").toString();
            airCondition = intent.getStringExtra("airCondition").toString();
            heating_one = intent.getStringExtra("heating_one").toString();
            SelectionMode = intent.getStringExtra("SelectionMode").toString();
            oneHour_price = intent.getStringExtra("oneHour_price").toString();
            twoHour_price = intent.getStringExtra("twoHour_price").toString();
            threeHour_price = intent.getStringExtra("threeHour_price").toString();
            fourHour_price = intent.getStringExtra("fourHour_price").toString();
            night_price = intent.getStringExtra("night_price").toString();
            latitude = intent.getStringExtra("lat").toString();
            latitude = intent.getStringExtra("lon").toString();
        }


        nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 10);

        lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -0);

        calendar.init(lastYear.getTime(), nextYear.getTime(), new SimpleDateFormat("MMMM, YYYY", Locale.getDefault()))
                .inMode(CalendarPickerView.SelectionMode.RANGE);

        RR_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Date c = Calendar.getInstance().getTime();
//                System.out.println("Current time => " + c);
//
//                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
//                String formattedDate = df.format(c);


              /*  Toast.makeText(CalenderActivityTwo.this, c+"", Toast.LENGTH_SHORT).show();
                Toast.makeText(CalenderActivityTwo.this, formattedDate+"", Toast.LENGTH_SHORT).show();*/

               RR_next.setEnabled(false);
                if (sessionManager.isNetworkAvailable()) {

                    progressBar.setVisibility(View.VISIBLE);

                  callAddDetailsApi();


                }else {
                    Toast.makeText(CalenderActivityTwo.this, getResources().getString(R.string.checkInternet), Toast.LENGTH_SHORT).show();
                }
            }
        });

        RR_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar.clearSelectedDates();

            }
        });
    }

    private void findView() {

        calendar = findViewById(R.id.calendar_view);
        RR_next = findViewById(R.id.RR_next);
        sessionManager = new SessionManager(this);
        progressBar = findViewById(R.id.progressBar);
        RR_reset = findViewById(R.id.RR_reset);

        calendar.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
          @Override
          public void onDateSelected(Date StartDate) {

              SatrtDate = StartDate.getDate();

          }
          @Override
          public void onDateUnselected(Date endDate) {

               endDateSeleted = endDate.getDate();

          }
      });

    }


    private void callAddDetailsApi() {

        String Userid = Preference.get(CalenderActivityTwo.this,Preference.KEY_USER_ID);

        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .add_details(Userid,"Spain",Community,City,ZipCode,Street_number_floor,SelectionMode,Title,description,PrivateBath,airCondition,heating_one
                        ,"500","500",oneHour_price,twoHour_price,threeHour_price,fourHour_price,
                        night_price,"20/01/2020",latitude,longitude);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    progressBar.setVisibility(View.GONE);
                    RR_next.setEnabled(true);
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    JSONObject resultOne = jsonObject.getJSONObject("result");

                    String Room_id = resultOne.getString("id");

                    if (status.equalsIgnoreCase("1")){

                        Preference.save(CalenderActivityTwo.this,Preference.KEY_Room_ID,Room_id);

                        Toast.makeText(CalenderActivityTwo.this, getString(R.string.success), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(CalenderActivityTwo.this, AddPhoto.class);
                        startActivity(intent);
                        finish();


                    }else {

                        Toast.makeText(CalenderActivityTwo.this, getString(R.string.login_unsucces), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    RR_next.setEnabled(true);
                    e.printStackTrace();

                } catch (IOException e) {
                    RR_next.setEnabled(true);
                    e.printStackTrace();

                }finally {
                    progressBar.setVisibility(View.GONE);
                    //  btn_login.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                RR_next.setEnabled(true);
                Toast.makeText(CalenderActivityTwo.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}