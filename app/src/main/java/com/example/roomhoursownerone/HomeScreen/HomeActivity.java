package com.example.roomhoursownerone.HomeScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.roomhoursownerone.HomeScreen.Profile.ProfileFragment;
import com.example.roomhoursownerone.MessageFragment.MessageFragment;
import com.example.roomhoursownerone.R;

public class HomeActivity extends AppCompatActivity {

    private Fragment fragment;
    RelativeLayout RR_profile;
    RelativeLayout RR_Home;
    RelativeLayout RR_chat;

    private ImageView img_home;
    private ImageView img_fav;
    private ImageView img_map;
    private ImageView img_chat;
    private ImageView img_profile;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(
                    this, R.color.Mehroon_one));
        }

        RR_Home =findViewById(R.id.RR_Home);
        RR_profile =findViewById(R.id.RR_profile);
        RR_chat =findViewById(R.id.RR_chat);

        img_home= findViewById(R.id.img_home);
        img_chat= findViewById(R.id.img_chat);
        img_profile= findViewById(R.id.img_profile);


        RR_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment = new ListingFragment();
                loadFragment(fragment);

                img_home.setImageResource(R.mipmap.home);
                img_chat.setImageResource(R.mipmap.chat_one);
                img_profile.setImageResource(R.drawable.profile_new_icon);

            }
        });


        RR_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment = new MessageFragment();
                loadFragment(fragment);

                img_home.setImageResource(R.mipmap.home_one);
                img_chat.setImageResource(R.mipmap.chat);
                img_profile.setImageResource(R.drawable.profile_new_icon);
            }
        });

        RR_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment = new ProfileFragment();
                loadFragment(fragment);


                img_home.setImageResource(R.mipmap.home_one);
                img_chat.setImageResource(R.mipmap.chat_one);
                img_profile.setImageResource(R.drawable.profile_new);

            }
        });

        fragment = new ListingFragment();
        loadFragment(fragment);

    }

    public void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_homeContainer, fragment);
        transaction.addToBackStack("home");
        transaction.commit();
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        fragment = new ListingFragment();
        loadFragment(fragment);
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getString(R.string.please), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
                finish();

            }
        }, 2000);
    }

}