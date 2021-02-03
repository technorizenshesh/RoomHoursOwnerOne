package com.example.roomhoursownerone.AddPhoto;

import android.Manifest;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.darsh.multipleimageselect.activities.AlbumSelectActivity;
import com.darsh.multipleimageselect.helpers.Constants;
import com.example.roomhoursownerone.BankAccountDetails.BankAccountDetails;
import com.example.roomhoursownerone.CurrentLocation.CurrentLocation;
import com.example.roomhoursownerone.Done.DoneActivity;
import com.example.roomhoursownerone.Preference;
import com.example.roomhoursownerone.PreviewImageSaloonScreen.PreviewImage_Adapter;
import com.example.roomhoursownerone.PreviewImageSaloonScreen.PreviewModel;
import com.example.roomhoursownerone.PreviewImageSaloonScreen.PreviewScreenActivity;
import com.example.roomhoursownerone.R;
import com.example.roomhoursownerone.Utills.FileUtil;
import com.example.roomhoursownerone.Utills.RetrofitClients;
import com.example.roomhoursownerone.Utills.SessionManager;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPhoto extends AppCompatActivity {

    public  static ArrayList<Bitmap> istBitImgAll=new ArrayList<>();

    private RelativeLayout RR_add_photo;
    private RelativeLayout RR_next;
    private LinearLayout LL_check;
    private TextView txt_image;
    private ImageView img_check;
    File imageFilePath_one =null;
    File compressedImage_one =null;

    int Count =0;
    private ProgressBar progressBar;
    private SessionManager sessionManager;

    int PICK_IMAGE_MULTIPLE = 1;
    String imageEncoded;
    ImageView img_add;
    List<String> imagesEncodedList;
    ArrayList<Bitmap> mArrayUri = new ArrayList<Bitmap>();

    boolean isImageSelected =false;
    File imageFilePath_multile =null;
    ArrayList<File> listImage =new ArrayList<>();

    ArrayList<MultipartBody.Part> partsImage = new ArrayList<>();
    ArrayList< MultipartBody.Part> surveyImagesParts=new ArrayList<>();

    private RecyclerView recycler_preview;
    private PreviewImage_Adapter mAdapter;
    private RelativeLayout RR_preview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        RR_add_photo=findViewById(R.id.RR_add_photo);
        RR_next=findViewById(R.id.RR_next);
        img_check=findViewById(R.id.img_check);
        LL_check=findViewById(R.id.LL_check);
        txt_image=findViewById(R.id.txt_image);
        progressBar=findViewById(R.id.progressBar);
        img_add=findViewById(R.id.img_add);
        recycler_preview=findViewById(R.id.recycler_preview);
        RR_preview=findViewById(R.id.RR_preview);


        RR_preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AddPhoto.this, PreviewScreenActivity.class);
                startActivity(intent);
            }
        });


        sessionManager = new SessionManager(this);

        RR_add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Intent intent=new Intent(AddPhoto.this, CurerentLocation.class);
                startActivity(intent);*/
                Dexter.withActivity(AddPhoto.this)
                        .withPermissions(Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {

                                    Intent intent = new Intent();
                                    intent.setType("image/*");
                                    intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 10); //set desired image limit here
                                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                                    intent.setAction(Intent.ACTION_GET_CONTENT);
                                    startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_MULTIPLE);

                                   /* Intent intent = new Intent(AddPhoto.this, AlbumSelectActivity.class);
                                    intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 10); //set desired image limit here
                                    startActivityForResult(intent, Constants.REQUEST_CODE);*/

                                  /*  Intent intent = CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).getIntent(AddPhoto.this);
                                    startActivityForResult(intent, 1);*/

                                } else {

                                    showSettingDialogue();
                                }
                            }
                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();

            }
        });
        RR_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isImageSelected)
                {
                    progressBar.setVisibility(View.VISIBLE);
                    add_to_photo();
                 /*   Intent intent=new Intent(AddPhoto.this, BankAccountDetails.class);
                    startActivity(intent);*/

                }else
                {
                    Toast.makeText(AddPhoto.this, "Please enter Minimum one Image", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    private void showSettingDialogue() {

        AlertDialog.Builder builder = new AlertDialog.Builder(AddPhoto.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                openSetting();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();

    }

    private void openSetting() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", this.getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(AddPhoto.this.getContentResolver(), resultUri);
                    // Glide.with(getContext()).load(bitmap).circleCrop().into(profileImageView);
                    imageFilePath_one = FileUtil.from(this, resultUri);
                    img_check.setImageBitmap(bitmap);
                   // Count++;
                    img_check.setVisibility(View.VISIBLE);
                   *//* LL_check.setVisibility(View.VISIBLE);
                    txt_image.setText(Count+" Image Upload");*//*

                    if (sessionManager.isNetworkAvailable()) {
                        RR_add_photo.setEnabled(false);
                        progressBar.setVisibility(View.VISIBLE);

                        add_to_photo();
                        // callLoginApiSocial();

                    }else {
                        Toast.makeText(AddPhoto.this, getResources().getString(R.string.checkInternet), Toast.LENGTH_SHORT).show();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    compressedImage_one = new Compressor(this)
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(75)
                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
                            .compressToFile(imageFilePath_one);
                    Log.e("ActivityTag", "imageFilePAth: " + compressedImage_one);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            // When an Image is picked
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                imagesEncodedList = new ArrayList<String>();
                if(data.getData()!=null){

                    Uri mImageUri=data.getData();

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded  = cursor.getString(columnIndex);
                    cursor.close();

                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {
                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(AddPhoto.this.getContentResolver(), uri);
                            mArrayUri.add(bitmap);

                            imageFilePath_multile = FileUtil.from(this, uri);
                            listImage.add(imageFilePath_multile);
                            istBitImgAll.add(bitmap);
                            // Get the cursor
                            Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded  = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            cursor.close();

                        }
                        RR_preview.setVisibility(View.VISIBLE);
                        setAdapter(istBitImgAll);
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                    }
                }
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {

            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

        isImageSelected =true;

        super.onActivityResult(requestCode, resultCode, data);
    }


  /* private void add_to_photo() {

       String Userid=  "20";
       String room_Id=   "18";

       MultipartBody.Part[] surveyImagesParts = new MultipartBody.Part[listImage.size()];

       for (int index = 0; index < listImage.size(); index++) {

           File file = new File(listImage
                   .get(index)
                   .getName());
           RequestBody surveyBody = RequestBody.create(MediaType.parse("image/*"),
                   file);
           surveyImagesParts[index] = MultipartBody.Part.createFormData("image",
                   file.getName(),
                   surveyBody);
       }

       Log.i("surveyImagesParts",""+surveyImagesParts);

       RequestBody UserId = RequestBody.create(MediaType.parse("text/plain"), Userid);
       RequestBody room_id = RequestBody.create(MediaType.parse("text/plain"), room_Id );

        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .add_to_photo(UserId,room_id ,surveyImagesParts);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {

                    RR_add_photo.setEnabled(true);

                    progressBar.setVisibility(View.GONE);

                    JSONObject jsonObject = new JSONObject(response.body().string());

                    String status   = jsonObject.getString ("status");
                    String message = jsonObject.getString("message");


                    if (status.equalsIgnoreCase("1")) {

                        img_check.setVisibility(View.VISIBLE);
                        LL_check.setVisibility(View.VISIBLE);
                        txt_image.setText("Image Upload");

                        Intent intent=new Intent(AddPhoto.this, DoneActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(AddPhoto.this, message, Toast.LENGTH_SHORT).show();
                      //  checkOut_cartId.setEnabled(true);
                    }

                } catch (JSONException e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(AddPhoto.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (IOException e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(AddPhoto.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                RR_add_photo.setEnabled(true);
                Toast.makeText(AddPhoto.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    private void add_to_photo () {

        String Userid=  Preference.get(AddPhoto.this,Preference.KEY_USER_ID);
        //  String Userid=  "12";
        String room_Id=  Preference.get(AddPhoto.this,Preference.KEY_Room_ID);
        //String room_Id=  "19";

        MultipartBody.Part[] surveyImagesParts = new MultipartBody.Part[listImage
                .size()];

        MultipartBody.Part imgFile = null;
        if (listImage == null) {

        } else {
            for(int index = 0; index <listImage.size();index++)
            {
                RequestBody requestFileOne = RequestBody.create(MediaType.parse("image/*"), listImage.get(index));
                imgFile = MultipartBody.Part.createFormData("image", listImage.get(index).getName(), requestFileOne);
                surveyImagesParts[index] =imgFile;
            }
        }

        RequestBody UserId = RequestBody.create(MediaType.parse("text/plain"), Userid);
        RequestBody room_id = RequestBody.create(MediaType.parse("text/plain"), room_Id );

        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .add_to_photo(UserId,room_id ,surveyImagesParts);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {

                    RR_add_photo.setEnabled(true);

                    progressBar.setVisibility(View.GONE);

                    JSONObject jsonObject = new JSONObject(response.body().string());

                    String status   = jsonObject.getString ("status");
                    String message = jsonObject.getString("message");


                    if (status.equalsIgnoreCase("1")) {

                        Toast.makeText(AddPhoto.this, message, Toast.LENGTH_SHORT).show();

                        Count++;
                        img_check.setVisibility(View.VISIBLE);
                        LL_check.setVisibility(View.VISIBLE);
                        txt_image.setText("Image Upload");

                        Intent intent=new Intent(AddPhoto.this, DoneActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(AddPhoto.this, message, Toast.LENGTH_SHORT).show();
                        //  checkOut_cartId.setEnabled(true);
                    }

                } catch (JSONException e) {
                    Toast.makeText(AddPhoto.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (IOException e) {
                    Toast.makeText(AddPhoto.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                RR_add_photo.setEnabled(true);
                Toast.makeText(AddPhoto.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setAdapter(ArrayList<Bitmap> istBitImgAll) {

        recycler_preview.setVisibility(View.VISIBLE);
        img_add.setVisibility(View.GONE);

        // modelList.add(new PreviewModel(R.drawable.add_pic));

        mAdapter = new PreviewImage_Adapter(AddPhoto.this, istBitImgAll);

        recycler_preview.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recycler_preview.setLayoutManager(linearLayoutManager);
        recycler_preview.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new PreviewImage_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Bitmap model) {

                Intent intent=new Intent(AddPhoto.this, PreviewScreenActivity.class);
                startActivity(intent);

            }
        });
    }
}
