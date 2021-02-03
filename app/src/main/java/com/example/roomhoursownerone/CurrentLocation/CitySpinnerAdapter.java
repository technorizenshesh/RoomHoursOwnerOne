 package com.example.roomhoursownerone.CurrentLocation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.roomhoursownerone.R;

import java.util.ArrayList;

 public class CitySpinnerAdapter extends BaseAdapter {
    Context context;
    int flags[];
     String code[];
     private ArrayList<CityDataModel> modelList;
    String[] countryNames;
    LayoutInflater inflter;
    TextView countrycode;
    ImageView icon;

    public CitySpinnerAdapter(Context applicationContext, int[] flags, ArrayList<CityDataModel> code) {
        this.context = applicationContext;
        this.flags = flags;
        this.modelList = code;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return modelList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.spinner_layout, null);
     //   icon = (ImageView) view.findViewById(R.id.img_flag);
        countrycode = (TextView) view.findViewById(R.id.textview);
   // icon.setImageResource(flags[i]);
        countrycode.setText(modelList.get(i).getName());

        return view;

    }
}

