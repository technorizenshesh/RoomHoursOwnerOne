package com.example.roomhoursownerone.CurrentLocation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roomhoursownerone.MapsActivity;
import com.example.roomhoursownerone.R;
import com.example.roomhoursownerone.Title.AddTitle;
import com.example.roomhoursownerone.Utills.RetrofitClients;
import com.example.roomhoursownerone.Utills.SessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentLocation extends AppCompatActivity {

    private RelativeLayout RR_Location;
    private Spinner spinnerAutonomous;
    private Spinner spinnerCity;
    private TextView edt_Street;
    private ProgressBar progressBar;

    private String code[] ={"Andalucía","Aragón","Asturias","Baleares","Canarias","Cantabria","Castilla-La Mancha","Castilla y León"
            ,"Cataluña","Extremadura","Galicia","Madrid","Murcia","Navarra","País Vasco","La Rioja"};

    private String code1[] ={"Andalucía","Almeria","Huesca","Oviedo","Palma de Mallorca","Santa Cruz de Tenerife","Santander","Albacete","Avila","Barcelona","Alicante","Badajoz","La Coruña","Madrid","Murcia","Pamplona","Bilbao","Logroño",
            "Cadiz","Teruel","Las Palmas de Gran Canaria","Ciudad Real","Burgos","Gerona","Castellon","Caceres","Lugo","SanSebastián","Cordoba","Zaragoza","Cuenca","Leon","Lerida","Valencia","Orense"
            ,"Vitoria","Granada","Guadalajara","Salamanca","Tarragona","Pontevedra","Huelva","Toledo","Segovia","Jaen","Soria","Malaga","Valladolid","Sevilla","Zamora"};

    private int flags[]= {R.drawable.flag};

    private TextView edt_country;
    private EditText edt_countryt_street;
    private EditText edt_zipCode;

    private ArrayList<StateDataModel> modelList_state = new ArrayList<>();
    private ArrayList<CityDataModel> modelList_city = new ArrayList<>();
    private SessionManager sessionManager;

    String  City="";
    String  Community="";
    String  Country="";
    String Street="";
    String ZipCode="";
    RelativeLayout RR_next;

    String latitude ="0.0";
    String longitude ="0.0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);

        RR_Location=findViewById(R.id.RR_Location);
        spinnerAutonomous=findViewById(R.id.spinnerAutonomous);
        spinnerCity=findViewById(R.id.spinnerCity);
        edt_country=findViewById(R.id.edt_country);
        progressBar=findViewById(R.id.progressBar);
        progressBar=findViewById(R.id.progressBar);
        RR_next=findViewById(R.id.RR_next);
        edt_countryt_street=findViewById(R.id.edt_countryt_street);
        edt_zipCode=findViewById(R.id.edt_zipCode);
        sessionManager = new SessionManager(CurrentLocation.this);

        Intent intent =getIntent();
        if(intent !=null)
        {
            Community = intent.getStringExtra("Community").toString();
            City = intent.getStringExtra("City").toString();
            Street = intent.getStringExtra("Street").toString();
            ZipCode = intent.getStringExtra("ZipCode").toString();
           latitude = intent.getStringExtra("lat").toString();
            longitude = intent.getStringExtra("lon").toString();

            edt_countryt_street.setText(Street);
            edt_zipCode.setText(ZipCode);
        }

     /*   CountrySpinnerAdapter customAdapter=new CountrySpinnerAdapter(CurerentLocation.this,flags,code);
        spinnerAutonomous.setAdapter(customAdapter);*/

        RR_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Street = edt_countryt_street.getText().toString();
                ZipCode = edt_zipCode.getText().toString();

                if(Street.equalsIgnoreCase(""))
                {
                    Toast.makeText(CurrentLocation.this, getString(R.string.street_flowr), Toast.LENGTH_SHORT).show();

                }if(ZipCode.equalsIgnoreCase(""))
                {
                    Toast.makeText(CurrentLocation.this, getString(R.string.zipcode), Toast.LENGTH_SHORT).show();

                }else
                {

                    Intent intent=new Intent(CurrentLocation.this, AddTitle.class);
                    intent.putExtra("City",City);
                    intent.putExtra("Community",Community);
                    intent.putExtra("Country",Country);
                    intent.putExtra("Street_number_floor",Country);
                    intent.putExtra("ZipCode",ZipCode);
                    intent.putExtra("lat",latitude);
                    intent.putExtra("lon",longitude);
                    startActivity(intent);
                    finish();

                }
            }
        });

        RR_Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(CurrentLocation.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        if (sessionManager.isNetworkAvailable()) {

            progressBar.setVisibility(View.VISIBLE);

            getStateAll();

        }else {

            Toast.makeText(this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }

    }

    private void getStateAll() {

        Call<StateModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .getState();

        call.enqueue(new Callback<StateModel>() {
            @Override
            public void onResponse(Call<StateModel> call, Response<StateModel> response) {

                try {
                    StateModel mygetAllList= response.body();

                    String status= mygetAllList.getStatus().toString();
                    String message= mygetAllList.getMessage().toString();

                    if (status.equalsIgnoreCase("1")) {

                        progressBar.setVisibility(View.GONE);

                        modelList_state = (ArrayList<StateDataModel>) mygetAllList.getResult();

                        Category(modelList_state,spinnerAutonomous);


                    } else {

                        Toast.makeText(CurrentLocation.this, message, Toast.LENGTH_SHORT).show();

                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(CurrentLocation.this, message, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<StateModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(CurrentLocation.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCityAll(String categoryId) {

        Call<CityModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_city(categoryId);

        call.enqueue(new Callback<CityModel>() {
            @Override
            public void onResponse(Call<CityModel> call, Response<CityModel> response) {

                try {
                    CityModel mygetAllList1= response.body();

                    String status= mygetAllList1.getStatus().toString();
                    String message= mygetAllList1.getMessage().toString();

                    if (status.equalsIgnoreCase("1")) {

                        progressBar.setVisibility(View.GONE);

                        modelList_city = (ArrayList<CityDataModel>) mygetAllList1.getResult();

                        CategoryCity(modelList_city,spinnerCity);


                    } else {

                        Toast.makeText(CurrentLocation.this, message, Toast.LENGTH_SHORT).show();

                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(CurrentLocation.this, message, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CityModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(CurrentLocation.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Category(final ArrayList<StateDataModel> Category, Spinner SpinnerNew) {

        CountrySpinnerAdapter customAdapter=new CountrySpinnerAdapter(CurrentLocation.this,flags,modelList_state);
        spinnerAutonomous.setAdapter(customAdapter);

        SpinnerNew.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                String  categoryId = Category.get(position).getId().toString();
                Community = Category.get(position).getName().toString();

                if (sessionManager.isNetworkAvailable()) {

                    progressBar.setVisibility(View.VISIBLE);

                    getCityAll(categoryId);

                }else {

                    Toast.makeText(CurrentLocation.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
                }

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> arg0)
            {

            }
        });
    }

    private void CategoryCity(final ArrayList<CityDataModel> Category, Spinner SpinnerNew) {

        CitySpinnerAdapter customAdapter1=new CitySpinnerAdapter(CurrentLocation.this,flags,Category);
        spinnerCity.setAdapter(customAdapter1);

        SpinnerNew.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                String  categoryId = Category.get(position).getId().toString();

                City = Category.get(position).getName().toString();

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> arg0)
            {

            }
        });
    }

}

