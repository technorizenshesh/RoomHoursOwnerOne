package com.example.roomhoursownerone.AllRoomImage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roomhoursownerone.HomeScreen.ApiModel.GetFavModel;
import com.example.roomhoursownerone.HomeScreen.ApiModel.GetFavModelData;
import com.example.roomhoursownerone.HomeScreen.FavRoomAllRecyclerViewAdapter;
import com.example.roomhoursownerone.HomeScreen.ListingFragment;
import com.example.roomhoursownerone.Preference;
import com.example.roomhoursownerone.R;
import com.example.roomhoursownerone.Utills.RetrofitClients;
import com.example.roomhoursownerone.Utills.SessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllRoomsImage extends AppCompatActivity {

    RecyclerView recycler_all_image;
    private All_Room_hourRecyclerViewAdapter mAdapter_best_match;
    private ArrayList<AllImageModelData> modellist = new ArrayList<>();

    TextView txt_emty;
    ProgressBar progressBar;

    private SessionManager sessionManager;
    String android_id ="";
    private RelativeLayout RR_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_rooms_image);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(
                    this, R.color.Mehroon_one));
        }

        txt_emty=findViewById(R.id.txt_emty);
        recycler_all_image=findViewById(R.id.recycler_all_image);
        progressBar=findViewById(R.id.progressBar);
        RR_back=findViewById(R.id.RR_back);


        //android device Id
        android_id = Settings.Secure.getString(AllRoomsImage.this.getContentResolver(), Settings.Secure.ANDROID_ID);
        sessionManager = new SessionManager(AllRoomsImage.this);


        RR_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        if (sessionManager.isNetworkAvailable()) {
            progressBar.setVisibility(View.VISIBLE);

            getAllRoomImage();

        }else {
            Toast.makeText(AllRoomsImage.this, getResources().getString(R.string.checkInternet), Toast.LENGTH_SHORT).show();
        }

    }

    private void setAdapter(ArrayList<AllImageModelData> modellist) {

        mAdapter_best_match = new All_Room_hourRecyclerViewAdapter(AllRoomsImage.this, modellist);
        recycler_all_image.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AllRoomsImage.this);
        recycler_all_image.setLayoutManager(linearLayoutManager);
        recycler_all_image.setAdapter(mAdapter_best_match);

        mAdapter_best_match.SetOnItemClickListener(new All_Room_hourRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, AllImageModelData model) {

           /*     Intent intent = new Intent(getActivity(), CheckInActivity.class);
                startActivity(intent);*/
            }
        });
    }

    public void getAllRoomImage() {

        String Room_id= Preference.get(AllRoomsImage.this,Preference.KEY_Room_ID);

        Call<AllImageModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_room_image(Room_id);

        call.enqueue(new Callback<AllImageModel>() {
            @Override
            public void onResponse(Call<AllImageModel> call, Response<AllImageModel> response) {
                try {

                    progressBar.setVisibility(View.GONE);

                    AllImageModel myclass= response.body();

                    String status = myclass.getStatus();
                    String result = myclass.getMessage();

                    if (status.equalsIgnoreCase("1")){
                        txt_emty.setVisibility(View.GONE);

                        modellist = (ArrayList<AllImageModelData>) myclass.getResult();

                        setAdapter(modellist);

                    }else {
                        txt_emty.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    txt_emty.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<AllImageModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                txt_emty.setVisibility(View.VISIBLE);
            }
        });
    }


}