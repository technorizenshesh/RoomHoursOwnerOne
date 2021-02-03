package com.example.roomhoursownerone.Pricelist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.roomhoursownerone.CalenderScreen.CalenderActivityTwo;
import com.example.roomhoursownerone.R;

public class PriceListActivity extends AppCompatActivity {

    private RelativeLayout RR_next_prices;
    private RelativeLayout RR_recommend;
    private RelativeLayout RR_customized;
    private RelativeLayout RR_terms;

    private ImageView img_recommend;
    private ImageView img_customized;
    private ImageView img_terms;

    boolean recommend=true;
    boolean customized=true;
    boolean terms=true;

    private EditText edt_one;
    private EditText edt_two;
    private EditText edt_three;
    private EditText edt_four;
    private EditText edt_five;
    private EditText edt_six;

    private EditText edt_one_recommed;
    private EditText edt_two_recommed;
    private EditText edt_three_recommed;
    private EditText edt_four_recommed;
    private EditText edt_five_recommed;
    private EditText edt_six_recommed;

    String PrivateBath ="";
    String airCondition ="";
    String heating_one ="";

    String  City="";
    String  Community="";
    String  Country="";
    String  Street_number_floor="";
    String  ZipCode="";
    String  Title="";
    String  description="";

    String oneHour_price="";
    String twoHour_price="";
    String threeHour_price="";
    String fourHour_price="";
    String night_price="";
    String weekend_price="";

    LinearLayout LL_customized_price;
    LinearLayout LL_recommed_price;


    boolean isSelectedPrice=false;

    String SelectionMode = "Customized";
    String latitude ="";
    String longitude ="";
    int onehr,twoHr,threeHr,fourHr,fiveHr,SixHr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_list);

        RR_terms=findViewById(R.id.RR_terms);

        edt_one=findViewById(R.id.edt_one);
        edt_two=findViewById(R.id.edt_two);
        edt_three=findViewById(R.id.edt_three);
        edt_four=findViewById(R.id.edt_four);
        edt_five=findViewById(R.id.edt_five);
        edt_six=findViewById(R.id.edt_six);

        edt_one_recommed=findViewById(R.id.edt_one_recommed);
        edt_two_recommed=findViewById(R.id.edt_two_recommed);
        edt_three_recommed=findViewById(R.id.edt_three_recommed);
        edt_four_recommed=findViewById(R.id.edt_four_recommed);
        edt_five_recommed=findViewById(R.id.edt_five_recommed);
        edt_six_recommed=findViewById(R.id.edt_six_recommed);

        RR_next_prices=findViewById(R.id.RR_next_prices);
        RR_recommend=findViewById(R.id.RR_recommend);
        RR_customized=findViewById(R.id.RR_customized);
        img_recommend=findViewById(R.id.img_recommend);
        img_customized=findViewById(R.id.img_customized);
        img_terms=findViewById(R.id.img_terms);
        LL_customized_price=findViewById(R.id.LL_customized_price);
        LL_recommed_price=findViewById(R.id.LL_recommed_price);

        edt_one_recommed.setEnabled(false);
        edt_two_recommed.setEnabled(false);
        edt_three_recommed.setEnabled(false);
        edt_four_recommed.setEnabled(false);
        edt_five_recommed.setEnabled(false);
        edt_six_recommed.setEnabled(false);


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
            latitude = intent.getStringExtra("lat").toString();
            latitude = intent.getStringExtra("lon").toString();

        }



        RR_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LL_customized_price.setVisibility(View.GONE);
                LL_recommed_price.setVisibility(View.VISIBLE);

                img_recommend.setImageResource(R.drawable.check_icon);
                img_customized.setImageResource(R.drawable.circle_gray);

                isSelectedPrice =true;

                SelectionMode = "Recommend";

            }
        });

        RR_customized.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LL_customized_price.setVisibility(View.VISIBLE);
                LL_recommed_price.setVisibility(View.GONE);

                img_recommend.setImageResource(R.drawable.circle_gray);
                img_customized.setImageResource(R.drawable.check_icon);

                isSelectedPrice = false;

                SelectionMode = "Customized";

            }
        });

        RR_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(terms)
                {
                    img_terms.setImageResource(R.drawable.check_icon);
                    terms =false;
                }else
                {
                    img_terms.setImageResource(R.drawable.circle_gray);
                    terms =true;
                }

            }
        });


        RR_next_prices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSelectedPrice)
                {
                    oneHour_price= "10";
                    twoHour_price= "15";
                    threeHour_price= "20";
                    fourHour_price= "25";
                    night_price= "30";
                    weekend_price= "35";

                }else
                {
                    oneHour_price= edt_one.getText().toString();
                    twoHour_price= edt_two.getText().toString();
                    threeHour_price= edt_three.getText().toString();
                    fourHour_price= edt_four.getText().toString();
                    night_price= edt_five.getText().toString();
                    weekend_price= edt_six.getText().toString();
                }


                if(!oneHour_price.equalsIgnoreCase(""))
                {
                    onehr = Integer.parseInt(oneHour_price);

                }  if(!twoHour_price.equalsIgnoreCase(""))
                {
                    twoHr = Integer.parseInt(twoHour_price);

                }if(!threeHour_price.equalsIgnoreCase(""))
                {
                    threeHr = Integer.parseInt(threeHour_price);

                }if(!fourHour_price.equalsIgnoreCase(""))
                {
                    fourHr = Integer.parseInt(fourHour_price);

                }if(!night_price.equalsIgnoreCase(""))
                {
                    fiveHr = Integer.parseInt(night_price);

                }if(!weekend_price.equalsIgnoreCase(""))
                {
                    SixHr = Integer.parseInt(weekend_price);
                }

                if(oneHour_price.equalsIgnoreCase(""))
                {
                    Toast.makeText(PriceListActivity.this, "Please Enter One Hour Price", Toast.LENGTH_SHORT).show();

                }else if(twoHour_price.equalsIgnoreCase("") || twoHr<onehr)
                {
                    Toast.makeText(PriceListActivity.this, "X1 hour prices must be higher than X-2 hours.", Toast.LENGTH_SHORT).show();

                }else if(threeHour_price.equalsIgnoreCase("") || threeHr<twoHr)
                {
                    Toast.makeText(PriceListActivity.this, "X2 hour prices must be higher than X-3 hours.", Toast.LENGTH_SHORT).show();

                }else if(fourHour_price.equalsIgnoreCase("") || fourHr<threeHr)
                {
                    Toast.makeText(PriceListActivity.this, "X3 hour prices must be higher than X-4 hours.", Toast.LENGTH_SHORT).show();

                }else if(night_price.equalsIgnoreCase("")|| fiveHr<fourHr)
                {

                    Toast.makeText(PriceListActivity.this, "X4 hour prices must be higher than X-5 hours.", Toast.LENGTH_SHORT).show();

                }else if(weekend_price.equalsIgnoreCase("")|| SixHr<fiveHr)
                {
                    Toast.makeText(PriceListActivity.this, "X5 hour prices must be higher than X-6 hours.", Toast.LENGTH_SHORT).show();

                }else
                {

                    Intent intent=new Intent(PriceListActivity.this, CalenderActivityTwo.class);
                    intent.putExtra("City",City);
                    intent.putExtra("Community",Community);
                    intent.putExtra("Country",Country);
                    intent.putExtra("Street_number_floor",Street_number_floor);
                    intent.putExtra("ZipCode",ZipCode);
                    intent.putExtra("Title",Title);
                    intent.putExtra("description",description);
                    intent.putExtra("PrivateBath",PrivateBath);
                    intent.putExtra("airCondition",airCondition);
                    intent.putExtra("heating_one",heating_one);
                    intent.putExtra("SelectionMode",SelectionMode);

                    intent.putExtra("oneHour_price",oneHour_price);
                    intent.putExtra("twoHour_price",twoHour_price);
                    intent.putExtra("threeHour_price",threeHour_price);
                    intent.putExtra("fourHour_price",fourHour_price);
                    intent.putExtra("night_price",night_price);

                    intent.putExtra("lat",latitude);
                    intent.putExtra("lon",longitude);

                    startActivity(intent);

                }

                /*Intent intent = new Intent(PriceListActivity.this, CalenderActivityOne.class);
                startActivity(intent);*/

            }
        });
    }
}
