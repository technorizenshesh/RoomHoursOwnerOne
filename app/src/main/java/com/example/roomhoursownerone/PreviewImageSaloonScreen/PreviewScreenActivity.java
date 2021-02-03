package com.example.roomhoursownerone.PreviewImageSaloonScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.roomhoursownerone.AddPhoto.AddPhoto;
import com.example.roomhoursownerone.R;

import java.util.ArrayList;

public class PreviewScreenActivity extends AppCompatActivity {

    private RecyclerView recycler_get_Myproduct;
    private PreviewImage_Adapter mAdapter;
    private RelativeLayout RR_back;
    private ArrayList<PreviewModel> modelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_screen);

        recycler_get_Myproduct=findViewById(R.id.recycler_preview);
        RR_back=findViewById(R.id.RR_back);
        RR_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        setAdapter();
    }


    private void setAdapter() {

       // modelList.add(new PreviewModel(R.drawable.add_pic));

        mAdapter = new PreviewImage_Adapter(PreviewScreenActivity.this, AddPhoto.istBitImgAll);

        recycler_get_Myproduct.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PreviewScreenActivity.this);
        recycler_get_Myproduct.setLayoutManager(linearLayoutManager);
        recycler_get_Myproduct.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new PreviewImage_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Bitmap model) {

                // startActivity(new Intent(AddedProductListActivity.this, AddProductActivity.class))

            }
        });
    }
}