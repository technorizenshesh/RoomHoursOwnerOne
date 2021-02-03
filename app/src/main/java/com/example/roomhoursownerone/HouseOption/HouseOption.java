package com.example.roomhoursownerone.HouseOption;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.roomhoursownerone.Pricelist.PriceListActivity;
import com.example.roomhoursownerone.R;
import com.example.roomhoursownerone.Title.AddTitle;

public class HouseOption extends AppCompatActivity {

    private RelativeLayout RR_next_house;
    private RelativeLayout RR_bath;
    private RelativeLayout RR_air_condition;
    private RelativeLayout RR_heating;

    private ImageView img_one;
    private ImageView img_two;
    private ImageView img_three;

    boolean bath = false;
    boolean air = false;
    boolean heating = false;

    String PrivateBath ="false";
    String airCondition ="false";
    String heating_one ="true";
    String  City="";
    String  Community="";
    String  Country="";
    String  Street_number_floor="";
    String  ZipCode="";
    String  Title="";
    String  description="";
    String latitude ="";
    String longitude ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_option);

        RR_next_house=findViewById(R.id.RR_next_house);
        RR_bath=findViewById(R.id.RR_bath);
        RR_air_condition=findViewById(R.id.RR_air_condition);
        RR_heating=findViewById(R.id.RR_heating);
        img_one=findViewById(R.id.img_one);
        img_two=findViewById(R.id.img_two);
        img_three=findViewById(R.id.img_three);

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
            latitude = intent.getStringExtra("lat").toString();
            latitude = intent.getStringExtra("lon").toString();

        }

        RR_bath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bath)
                {
                    PrivateBath = "false";
                    img_one.setImageResource(R.drawable.circle_gray);
                    bath =false;
                }else
                {
                    PrivateBath = "true";
                    img_one.setImageResource(R.drawable.check_icon);
                    bath =true;
                }

            }
        });

        RR_air_condition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(air)
                {
                    airCondition = "false";
                    img_two.setImageResource(R.drawable.circle_gray);
                    air =false;
                }else
                {
                    airCondition = "true";
                    img_two.setImageResource(R.drawable.check_icon);
                    air =true;
                }
            }
        });

        RR_heating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(heating)
                {
                     heating_one = "true";
                    img_three.setImageResource(R.drawable.check_icon);
                    heating =false;
                }else
                {
                     heating_one = "false";
                    img_three.setImageResource(R.drawable.circle_gray);
                    heating =true;
                }

            }
        });


        RR_next_house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(HouseOption.this, PriceListActivity.class);
                intent.putExtra("City",City);
                intent.putExtra("Community",Community);
                intent.putExtra("Street_number_floor",Street_number_floor);
                intent.putExtra("ZipCode",ZipCode);
                intent.putExtra("Country",Country);
                intent.putExtra("Title",Title);
                intent.putExtra("description",description);
                intent.putExtra("PrivateBath",PrivateBath);
                intent.putExtra("airCondition",airCondition);
                intent.putExtra("heating_one",heating_one);
                intent.putExtra("lat",latitude);
                intent.putExtra("lon",longitude);
                startActivity(intent);

/*                Intent intent = new Intent(HouseOption.this, PriceListActivity.class);
                startActivity(intent);*/
            }
        });
    }
}
