package com.example.roomhoursownerone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.roomhoursownerone.SliderScreen.SliderActivity;

import java.util.Locale;

public class SelectLanugage extends AppCompatActivity {

    ImageView RRArbic;
    ImageView RREng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_lanugage);

        init();
    }
        private void init() {
            RRArbic =findViewById(R.id.RRArbic);
            RREng =findViewById(R.id.RREng);

            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(ContextCompat.getColor(
                        this, R.color.mehroon));
            }



            RREng.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Preference.save(SelectLanugage.this,Preference.KEY_language,"en");

                    updateResources(SelectLanugage.this,"en");

                    startActivity(new Intent(SelectLanugage.this,SliderActivity.class));

                }
            });

            RRArbic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Preference.save(SelectLanugage.this,Preference.KEY_language,"es");

                    updateResources(SelectLanugage.this,"es");
                    startActivity(new Intent(SelectLanugage.this, SliderActivity.class));
                }
            });


        }

        private static void updateResources(Context context, String language) {

            Locale locale = new Locale(language);
            Locale.setDefault(locale);

            Resources resources = context.getResources();

            Configuration configuration = resources.getConfiguration();
            configuration.locale = locale;

            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }


    }