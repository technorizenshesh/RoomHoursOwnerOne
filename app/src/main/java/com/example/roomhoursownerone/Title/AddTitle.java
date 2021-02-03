package com.example.roomhoursownerone.Title;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.roomhoursownerone.HouseOption.HouseOption;
import com.example.roomhoursownerone.MapsActivity;
import com.example.roomhoursownerone.R;

public class AddTitle extends AppCompatActivity {

    private RelativeLayout RR_title;
    private EditText edt_title;
    private EditText edt_description;


    String  City="";
    String  Community="";
    String  Country="";
    String  Street_number_floor="";
    String  ZipCode="";
    String latitude ="0.0";
    String longitude ="0.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_title);

        RR_title= findViewById(R.id.RR_title);
        edt_title= findViewById(R.id.edt_title);
        edt_description= findViewById(R.id.edt_description);

        Intent intent=getIntent();
        if(intent !=null)
        {
            City = intent.getStringExtra("City").toString();
            Community = intent.getStringExtra("Community").toString();
            Country = intent.getStringExtra("Country").toString();
            Street_number_floor = intent.getStringExtra("Street_number_floor").toString();
            ZipCode = intent.getStringExtra("ZipCode").toString();
            latitude = intent.getStringExtra("lat").toString();
            latitude = intent.getStringExtra("lon").toString();

        }

        RR_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Title =edt_title.getText().toString();
                String description =edt_description.getText().toString();
                if(Title.equalsIgnoreCase(""))
                {
                    Toast.makeText(AddTitle.this, "Please Enter Title.", Toast.LENGTH_SHORT).show();

                }else if(description.equalsIgnoreCase(""))
                {
                    Toast.makeText(AddTitle.this, "Please Enter description.", Toast.LENGTH_SHORT).show();

                }else {

                    Intent intent=new Intent(AddTitle.this, HouseOption.class);
                    intent.putExtra("City",City);
                    intent.putExtra("Community",Community);
                    intent.putExtra("Country",Country);
                    intent.putExtra("Street_number_floor",Street_number_floor);
                    intent.putExtra("ZipCode",ZipCode);
                    intent.putExtra("Title",Title);
                    intent.putExtra("description",description);
                    intent.putExtra("lat",latitude);
                    intent.putExtra("lon",longitude);
                    startActivity(intent);

                }
            }
        });
    }
}
