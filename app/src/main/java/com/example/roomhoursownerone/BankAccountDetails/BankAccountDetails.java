package com.example.roomhoursownerone.BankAccountDetails;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.roomhoursownerone.AddPhoto.AddPhoto;
import com.example.roomhoursownerone.Done.DoneActivity;
import com.example.roomhoursownerone.LoginScreen.LoginActivity;
import com.example.roomhoursownerone.Preference;
import com.example.roomhoursownerone.R;
import com.example.roomhoursownerone.Utills.RetrofitClients;
import com.example.roomhoursownerone.Utills.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BankAccountDetails extends AppCompatActivity {

    private RelativeLayout RR_next_bank_detaols;

    private EditText edt_bak_holderName;
    private EditText edt_bankName;
    private EditText edt_bank_ac;
    private EditText edt_bank_code;
    private EditText edt_Area;

    private ProgressBar progressBar;
    private SessionManager sessionManager;
    private String android_id="";

    String BankHolderName ="";
    String BankName ="";
    String Banck_Ac ="";
    String ban_Code ="";
    String Area ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_account_details);

        RR_next_bank_detaols=findViewById(R.id.RR_next_bank_detaols);
        edt_bak_holderName=findViewById(R.id.edt_bak_holderName);
        edt_bankName=findViewById(R.id.edt_bankName);
        edt_bank_ac=findViewById(R.id.edt_bank_ac);
        edt_bank_code=findViewById(R.id.edt_bank_code);
        edt_Area=findViewById(R.id.edt_Area);
        progressBar = findViewById(R.id.progressBar);

        //android device Id
        android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        sessionManager = new SessionManager(this);

        RR_next_bank_detaols.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 BankHolderName=edt_bak_holderName.getText().toString();
                 BankName=edt_bankName.getText().toString();
                 Banck_Ac=edt_bank_ac.getText().toString();
                 ban_Code=edt_bank_code.getText().toString();
                 Area=edt_Area.getText().toString();

                if(BankHolderName.equalsIgnoreCase(""))
                {
                    Toast.makeText(BankAccountDetails.this, "Please Enter Bank Holder Name", Toast.LENGTH_SHORT).show();

                }else if(BankName.equalsIgnoreCase(""))
                {
                    Toast.makeText(BankAccountDetails.this, "Please Enter Bank Name", Toast.LENGTH_SHORT).show();

                }else if(Banck_Ac.equalsIgnoreCase(""))
                {
                    Toast.makeText(BankAccountDetails.this, "Please Enter Bank Account Number", Toast.LENGTH_SHORT).show();

                }else if(ban_Code.equalsIgnoreCase(""))
                {
                    Toast.makeText(BankAccountDetails.this, "Please Enter Code", Toast.LENGTH_SHORT).show();

                }else if(Area.equalsIgnoreCase(""))
                {
                    Toast.makeText(BankAccountDetails.this, "Please Enter Area", Toast.LENGTH_SHORT).show();

                }else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    RR_next_bank_detaols.setEnabled(false);
                    if (sessionManager.isNetworkAvailable()) {

                        callbankMethod();

                    }else {
                        Toast.makeText(BankAccountDetails.this, getResources().getString(R.string.checkInternet), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void callbankMethod() {

        String user_id = Preference.get(BankAccountDetails.this,Preference.KEY_USER_ID);
        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .addBankDetails(user_id,BankHolderName,BankName,Banck_Ac,ban_Code,Area);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    RR_next_bank_detaols.setEnabled(true);

                    progressBar.setVisibility(View.GONE);

                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    if (status.equalsIgnoreCase("1")){


                        Intent intent=new Intent(BankAccountDetails.this, DoneActivity.class);
                        startActivity(intent);
                        finishAffinity();

                    }else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(BankAccountDetails.this, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                } catch (IOException e) {

                    e.printStackTrace();

                }finally {
                    progressBar.setVisibility(View.GONE);
                    RR_next_bank_detaols.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                RR_next_bank_detaols.setEnabled(true);
                Toast.makeText(BankAccountDetails.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
