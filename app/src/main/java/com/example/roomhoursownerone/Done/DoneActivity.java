package com.example.roomhoursownerone.Done;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.roomhoursownerone.R;

public class DoneActivity extends AppCompatActivity {

    private RelativeLayout RR_Done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        RR_Done=findViewById(R.id.RR_Done);
        RR_Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent intent = new Intent(DoneActivity.this, HomeActivity.class);
                startActivity(intent);*/
            }
        });
    }
}
