package com.example.roomhoursownerone.SignUpScreen;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.roomhoursownerone.LoginScreen.LoginActivity;
import com.example.roomhoursownerone.LoginScreen.LoginModel;
import com.example.roomhoursownerone.R;
import com.example.roomhoursownerone.Utills.RetrofitClients;
import com.example.roomhoursownerone.Utills.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUpActivity extends AppCompatActivity {

    private TextView txt_allready_registed;
    private RelativeLayout RR_sign;
    private EditText edt_firstName;
    private EditText edt_email;
    private EditText edt_password;
    private EditText edt_Confirm_password;

    String FirstName="";
    String email="";
    String password="";
    String Confirm_password="";

    String android_id ="";
    private ProgressBar progressBar;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(
                    this, R.color.mehroon));
        }

        //android device Id
        android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        sessionManager = new SessionManager(this);

        SetuUi();

    }

    private void SetuUi() {
        progressBar = findViewById(R.id.progressBar);
        txt_allready_registed=findViewById(R.id.txt_allready_registed);
        edt_firstName=findViewById(R.id.edt_firstName);
        edt_email=findViewById(R.id.edt_email);
        edt_password=findViewById(R.id.edt_password);
        edt_Confirm_password=findViewById(R.id.edt_Confirm_password);
        RR_sign=findViewById(R.id.RR_sign);

        txt_allready_registed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        RR_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validation();
            }
        });

    }

    private void validation() {
         FirstName=edt_firstName.getText().toString();
         email=edt_email.getText().toString();
         password=edt_password.getText().toString();
         Confirm_password=edt_Confirm_password.getText().toString();

        if(FirstName.equalsIgnoreCase("")) {

            Toast.makeText(this, "Please Enter full name.", Toast.LENGTH_SHORT).show();


        }else if(!isValidEmail(email))
        {
            Toast.makeText(this, "Please Enter email.", Toast.LENGTH_SHORT).show();


        }else if(password.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter password.", Toast.LENGTH_SHORT).show();

        }else if(Confirm_password.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter password.", Toast.LENGTH_SHORT).show();

        }else if(!Confirm_password.equalsIgnoreCase(password))
        {
            Toast.makeText(this, "Don't match Password..", Toast.LENGTH_SHORT).show();

        }else
        {
            progressBar.setVisibility(View.VISIBLE);
            RR_sign.setEnabled(false);
            if (sessionManager.isNetworkAvailable()) {
                callSignUpApi();
            }else {
                Toast.makeText(SignUpActivity.this, getResources().getString(R.string.checkInternet), Toast.LENGTH_SHORT).show();
            }

        }
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void callSignUpApi() {

        Call<LoginModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .signupApi(FirstName,email,password,android_id,"25.00","25.00","Owner");
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                try {
                    LoginModel myLogin = response.body();
                    if (myLogin.getStatus().equalsIgnoreCase("1")){
                        Intent intent=new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    }else {
                        progressBar.setVisibility(View.GONE);
                        RR_sign.setEnabled(true);
                        Toast.makeText(SignUpActivity.this, myLogin.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    progressBar.setVisibility(View.GONE);
                    RR_sign.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                RR_sign.setEnabled(true);
                Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
