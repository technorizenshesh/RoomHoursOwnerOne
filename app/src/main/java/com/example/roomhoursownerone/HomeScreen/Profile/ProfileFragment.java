package com.example.roomhoursownerone.HomeScreen.Profile;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.roomhoursownerone.CurrentLocation.CurrentLocation;
import com.example.roomhoursownerone.GPSTracker;
import com.example.roomhoursownerone.HomeScreen.HomeActivity;
import com.example.roomhoursownerone.LoginScreen.LoginActivity;
import com.example.roomhoursownerone.LoginScreen.LoginModel;
import com.example.roomhoursownerone.Preference;
import com.example.roomhoursownerone.R;
import com.example.roomhoursownerone.Utills.RetrofitClients;
import com.example.roomhoursownerone.Utills.SessionManager;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private View view;
    private TextView txt_name;
    private TextView txt_email;
    private TextView txt_logout;
    String android_id ="";
    private ProgressBar progressBar;
    private SessionManager sessionManager;
    private RelativeLayout RR_bank_details;
    private RelativeLayout RR_chnagePassword;
    private RelativeLayout RR_Add_newRoom;

    private RelativeLayout RR_slectet_language;

    GPSTracker gpsTracker;
    String latitude ="0.0";
    String longitude ="0.0";

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // view = inflater.inflate(R.layout.fragment_main, container, false);
        view = inflater.inflate(R.layout.activity_profile, container, false);

        progressBar =view.findViewById(R.id.progressBar);
        RR_bank_details =view.findViewById(R.id.RR_bank_details);
        txt_name =view.findViewById(R.id.txt_name);
        txt_email =view.findViewById(R.id.txt_email);
        txt_logout =view.findViewById(R.id.txt_logout);
        RR_chnagePassword =view.findViewById(R.id.RR_chnagePassword);
        RR_Add_newRoom =view.findViewById(R.id.RR_Add_newRoom);
        RR_slectet_language =view.findViewById(R.id.RR_slectet_language);


        //android device Id
        android_id = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        sessionManager = new SessionManager(getActivity());


        progressBar.setVisibility(View.VISIBLE);
        if (sessionManager.isNetworkAvailable()) {
            callProfileApi();
        }else {
            Toast.makeText(getActivity(), getResources().getString(R.string.checkInternet), Toast.LENGTH_SHORT).show();
        }
        txt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Preference.clearPreference(getActivity());

                Intent intent=new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();

            }
        });

        RR_slectet_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                languageAlert(v);
            }
        });
        RR_Add_newRoom.setOnClickListener(new View.OnClickListener() {
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


        RR_bank_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

          /*      Intent intent=new Intent(getActivity(), BankDetailsActivity.class);
                startActivity(intent);*/
            }
        });

        RR_chnagePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             /*   Intent intent=new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent);*/
            }
        });

        gpsTracker=new GPSTracker(getActivity());
        if(gpsTracker.canGetLocation()){
            latitude = String.valueOf(gpsTracker.getLatitude());
            longitude = String.valueOf(gpsTracker.getLongitude());
        }else{
            gpsTracker.showSettingsAlert();
        }

        return view;
    }



    @Override
    public void onResume() {
        super.onResume();
    }
    //showMarker();

    private void callProfileApi() {

        String UserId = Preference.get(getActivity(), Preference.KEY_USER_ID);

        Call<LoginModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .getProfile(UserId);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                try {

                    LoginModel myLogin = response.body();

                    if (myLogin.getStatus().equalsIgnoreCase("1")){

                        String FName = myLogin.getResult().getFirstName().toString();
                        String Email = myLogin.getResult().getEmail().toString();

                        txt_name.setText(FName);
                        txt_email.setText(Email);

                    }else {

                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), myLogin.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void languageAlert(View view) {

        final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.language_alert, null);

        CardView btn_okay = mView.findViewById(R.id.btn_okay);

        final RadioButton english_btn = mView.findViewById(R.id.english_btn);
        final RadioButton  spanish_btn = mView.findViewById(R.id.spanish_btn);
        RadioGroup radioGroup = mView.findViewById(R.id.radio);


        alert.setView(mView);
        final AlertDialog alertDialog = alert.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.setCanceledOnTouchOutside(false);

        String lang= Preference.get(getActivity(),Preference.KEY_language);

        //  String lang=PrefManager.get(mContext,"lang");
        Log.e("lang",lang);

        if (lang.equals("es")&&lang!=null){
            english_btn.setChecked(false);
            spanish_btn.setChecked(true);
        }else
        {
            Preference.save(getActivity(),Preference.KEY_language,"en");

            english_btn.setChecked(true);
            spanish_btn.setChecked(false);
            updateResources(getActivity(),"en");

        }


        english_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(getApplicationContext(), "Select English", Toast.LENGTH_LONG).show();
                updateResources(getActivity(),"en");
                Preference.save(getActivity(),Preference.KEY_language,"en");
                english_btn.setChecked(true);
                spanish_btn.setChecked(false);
            }
        });
        spanish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(getApplicationContext(), "Select Spanish" , Toast.LENGTH_LONG).show();
                updateResources(getActivity(),"es");
                Preference.save(getActivity(),Preference.KEY_language,"es");
                english_btn.setChecked(false);
                spanish_btn.setChecked(true);

            }
        });


        btn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });

        alertDialog.show();

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
