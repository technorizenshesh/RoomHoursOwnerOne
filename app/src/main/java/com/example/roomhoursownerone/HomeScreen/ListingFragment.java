package com.example.roomhoursownerone.HomeScreen;


import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomhoursownerone.AllImageView.AllImageViewpager;
import com.example.roomhoursownerone.AllRoomImage.AllRoomsImage;
import com.example.roomhoursownerone.CurrentLocation.CurrentLocation;
import com.example.roomhoursownerone.GPSTracker;
import com.example.roomhoursownerone.HomeScreen.ApiModel.GetFavModel;
import com.example.roomhoursownerone.HomeScreen.ApiModel.GetFavModelData;
import com.example.roomhoursownerone.Preference;
import com.example.roomhoursownerone.R;
import com.example.roomhoursownerone.Utills.RetrofitClients;
import com.example.roomhoursownerone.Utills.SessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListingFragment extends Fragment {

    private View view;
    private RecyclerView recycler_best_match;
    private FavRoomAllRecyclerViewAdapter mAdapter_best_match;
    private ArrayList<GetFavModelData> modelList_best_match = new ArrayList<>();
    String android_id ="";
    public static ProgressBar progressBar;
    private SessionManager sessionManager;
    private TextView txt_emty;
    private RelativeLayout RR_fav;
    private RelativeLayout RR_addRoom;
    private ImageView img_addRooms;

    GPSTracker gpsTracker;
    String latitude ="0.0";
    String longitude ="0.0";

    public ListingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // view = inflater.inflate(R.layout.fragment_main, container, false);
        view = inflater.inflate(R.layout.fragment_fav, container, false);

        findView();

        //android device Id
        android_id = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        sessionManager = new SessionManager(getActivity());


        progressBar.setVisibility(View.VISIBLE);

        gpsTracker=new GPSTracker(getActivity());
        if(gpsTracker.canGetLocation()){
            latitude = String.valueOf(gpsTracker.getLatitude());
            longitude = String.valueOf(gpsTracker.getLongitude());
        }else{
            gpsTracker.showSettingsAlert();
        }

        if (sessionManager.isNetworkAvailable()) {

            getMyRooms();

        }else {
            Toast.makeText(getActivity(), getResources().getString(R.string.checkInternet), Toast.LENGTH_SHORT).show();
        }

        img_addRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), CurrentLocation.class);
                intent.putExtra("Community","");
                intent.putExtra("City","");
                intent.putExtra("Street","");
                intent.putExtra("ZipCode","");
                intent.putExtra("lat",latitude);
                intent.putExtra("lon",longitude);
                startActivity(intent);

            }
        });

        RR_addRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), CurrentLocation.class);
                intent.putExtra("Community","");
                intent.putExtra("City","");
                intent.putExtra("Street","");
                intent.putExtra("ZipCode","");
                intent.putExtra("lat",latitude);
                intent.putExtra("lon",longitude);
                startActivity(intent);
            }
        });


        return view;
    }

    private void findView() {
        recycler_best_match=view.findViewById(R.id.recycler_best_match);
        progressBar=view.findViewById(R.id.progressBar);
        txt_emty=view.findViewById(R.id.txt_emty);
        RR_fav=view.findViewById(R.id.RR_fav);
        RR_addRoom=view.findViewById(R.id.RR_addRoom);
        img_addRooms=view.findViewById(R.id.img_addRooms);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    //showMarker();

    private void setAdapterBestMatch(ArrayList<GetFavModelData> modelList_best_match) {

        mAdapter_best_match = new FavRoomAllRecyclerViewAdapter(getActivity(), modelList_best_match, ListingFragment.this);
        recycler_best_match.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recycler_best_match.setLayoutManager(linearLayoutManager);
        recycler_best_match.setAdapter(mAdapter_best_match);

        mAdapter_best_match.SetOnItemClickListener(new FavRoomAllRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, GetFavModelData model) {

                Preference.save(getActivity(),Preference.KEY_Room_ID,model.getId());

                Intent intent = new Intent(getActivity(), AllImageViewpager.class);
                startActivity(intent);

            }
        });
    }

    public void getMyRooms() {

        String User_Id= Preference.get(getActivity(),Preference.KEY_USER_ID);

        Call<GetFavModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_room_detailsBY_id(User_Id);

        call.enqueue(new Callback<GetFavModel>() {
            @Override
            public void onResponse(Call<GetFavModel> call, Response<GetFavModel> response) {
                try {

                    progressBar.setVisibility(View.GONE);

                    GetFavModel myclass= response.body();

                    String status = myclass.getStatus();
                    String result = myclass.getMessage();

                    if (status.equalsIgnoreCase("1")){
                        txt_emty.setVisibility(View.GONE);
                        RR_fav.setVisibility(View.VISIBLE);
                        modelList_best_match = (ArrayList<GetFavModelData>) myclass.getResult();

                        setAdapterBestMatch(modelList_best_match);

                    }else {
                        RR_fav.setVisibility(View.GONE);
                        txt_emty.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    txt_emty.setVisibility(View.VISIBLE);
                    RR_fav.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetFavModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                txt_emty.setVisibility(View.VISIBLE);
                RR_fav.setVisibility(View.GONE);
            }
        });
    }



}
