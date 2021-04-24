package com.example.roomhoursownerone.SignUpScreen;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.roomhoursownerone.CurrentLocation.CurrentLocation;
import com.example.roomhoursownerone.GPSTracker;
import com.example.roomhoursownerone.HomeScreen.HomeActivity;
import com.example.roomhoursownerone.LoginScreen.LoginActivity;
import com.example.roomhoursownerone.LoginScreen.LoginModel;
import com.example.roomhoursownerone.MainActivityLogin;
import com.example.roomhoursownerone.Preference;
import com.example.roomhoursownerone.R;
import com.example.roomhoursownerone.Utills.RetrofitClients;
import com.example.roomhoursownerone.Utills.SessionManager;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUpActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private TextView txt_allready_registed;
    private RelativeLayout RR_sign;
    private RelativeLayout RR_google_login;
    private RelativeLayout RR_faceBook_login;
    private EditText edt_firstName;
    private EditText edt_email;
    private EditText edt_password;
    private EditText edt_Confirm_password;
    private static final String TAG = "HashKey";
    String FirstName="";
    String email="";
    String password="";
    String Confirm_password="";

    String android_id ="";
    private ProgressBar progressBar;
    private SessionManager sessionManager;

    GPSTracker gpsTracker;
    String latitude ="0.0";
    String longitude ="0.0";

    //Google SignIn
    private SignInButton signInButton;
    FirebaseAuth mAuth;
    private final static int RC_SIGN_IN = 1;
    private GoogleApiClient googleApiClient;

    //FaceBook
    CallbackManager mCallbackManager;
    LoginButton loginButton;

    String token="";
    String result="";

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

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        token = task.getResult();
                        Log.e("token",token);
                    }
                });

        gpsTracker=new GPSTracker(this);


        sessionManager = new SessionManager(this);

        gpsTracker=new GPSTracker(this);
        if(gpsTracker.canGetLocation()){
            latitude = String.valueOf(gpsTracker.getLatitude());
            longitude = String.valueOf(gpsTracker.getLongitude());
        }else{
            gpsTracker.showSettingsAlert();
        }

        SetuUi();

          RR_google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, RC_SIGN_IN);
            }
        });
        RR_faceBook_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.performClick();
            }
        });
        //FirebaseApp.initializeApp();

        //Google SignIn
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //FaceBook
        mCallbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("TAG", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }
            @Override
            public void onCancel() {
                Toast.makeText(SignUpActivity.this, "btnCancel", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "facebook:onCancel");
                // ...
            }
            @Override
            public void onError(FacebookException error) {
                Toast.makeText(SignUpActivity.this, "Btnerrror", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "facebook:onError", error);
                // ...
            }
        });

    }

    private void SetuUi() {
        progressBar = findViewById(R.id.progressBar);
        txt_allready_registed=findViewById(R.id.txt_allready_registed);
        edt_firstName=findViewById(R.id.edt_firstName);
        edt_email=findViewById(R.id.edt_email);
        edt_password=findViewById(R.id.edt_password);
        edt_Confirm_password=findViewById(R.id.edt_Confirm_password);
        RR_sign=findViewById(R.id.RR_sign);
        RR_google_login=findViewById(R.id.RR_google_login);
        RR_faceBook_login=findViewById(R.id.RR_faceBook_login);
        loginButton=findViewById(R.id.loginButton);

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

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("TAG", "handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();

                            String UsernAME=user.getDisplayName();
                            String email=user.getEmail();
                            String SocialId=user.getUid();
                            Uri Url=user.getPhotoUrl();

                    if (sessionManager.isNetworkAvailable()) {

                                progressBar.setVisibility(View.VISIBLE);

                        SocialLoginMethod(UsernAME,"123456",email,"fghgh",SocialId);

                            }else {

                                Toast.makeText(SignUpActivity.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
                            }


                        } else {

                            Toast.makeText(SignUpActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    //Google Login
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent( data );
            handleSignInResult( result );
        }else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();

            String UsernAME=account.getDisplayName();
            String email=account.getEmail();
            String SocialId=account.getId();
            Uri Url=account.getPhotoUrl();

           /* Intent intent = new Intent(SignUpActivity.this, CurrentLocation.class);
            intent.putExtra("Community","");
            intent.putExtra("City","");
            intent.putExtra("Street","");
            intent.putExtra("ZipCode","");
            intent.putExtra("lat",latitude);
            intent.putExtra("lon",longitude);
            startActivity(intent);
            finishAffinity();*/

          if (sessionManager.isNetworkAvailable()) {

                progressBar.setVisibility(View.VISIBLE);

              SocialLoginMethod(UsernAME,"123456",email,"fghgh",SocialId);

            }else {

                Toast.makeText(SignUpActivity.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
            }



            Toast.makeText( this,getString(R.string.login_success), Toast.LENGTH_SHORT ).show();


        } else {

            Toast.makeText( this, getString(R.string.login_unsucces), Toast.LENGTH_SHORT ).show();

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void callSignUpApi() {

        Call<LoginModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .signupApi(FirstName,email,password,token,latitude,longitude,"Owner");
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

    private void SocialLoginMethod(String FirstName,String Password,String email,String register_id,String socialId) {

        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .SocialloginApi(FirstName,email,token,socialId,latitude,longitude);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressBar.setVisibility(View.GONE);
                try {
                    RR_google_login.setEnabled(true);
                    RR_faceBook_login.setEnabled(true);
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    result = jsonObject.getString("result");

                    JSONObject resultOne = jsonObject.getJSONObject("result");

                    String UserId = resultOne.getString("id");
                    String check_status = resultOne.getString("check_status");
                    if (status.equalsIgnoreCase("1")){

                        Preference.save(SignUpActivity.this,Preference.KEY_USER_ID,UserId);

                        if(check_status.equalsIgnoreCase("1"))
                        {
                            Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finishAffinity();

                        }else {

                            Intent intent = new Intent(SignUpActivity.this, CurrentLocation.class);
                            intent.putExtra("Community","");
                            intent.putExtra("City","");
                            intent.putExtra("Street","");
                            intent.putExtra("ZipCode","");
                            intent.putExtra("lat",latitude);
                            intent.putExtra("lon",longitude);
                            startActivity(intent);
                            finishAffinity();
                        }
                    }else {

                        RR_google_login.setEnabled(true);
                        RR_faceBook_login.setEnabled(true);
                        Toast.makeText(SignUpActivity.this, result, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(SignUpActivity.this, result, Toast.LENGTH_SHORT).show();
                    RR_google_login.setEnabled(true);
                    RR_faceBook_login.setEnabled(true);
                } catch (IOException e) {
                    RR_google_login.setEnabled(true);
                    RR_faceBook_login.setEnabled(true);

                }finally {
                    // Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
                    RR_google_login.setEnabled(true);
                    RR_faceBook_login.setEnabled(true);
                    //  btn_login.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                // btn_login.setEnabled(true);
                RR_google_login.setEnabled(true);
                RR_faceBook_login.setEnabled(true);
                Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
