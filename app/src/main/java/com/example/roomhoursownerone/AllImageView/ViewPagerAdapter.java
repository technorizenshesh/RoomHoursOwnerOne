package com.example.roomhoursownerone.AllImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.roomhoursownerone.AllRoomImage.AllImageModelData;
import com.example.roomhoursownerone.R;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    ArrayList<AllImageModelData> modellist;

    public ViewPagerAdapter(Context context, ArrayList<AllImageModelData> modellist) {
        this.context = context;
        this.modellist = modellist;
    }

    @Override
    public int getCount() {
        return modellist.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_layout, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
       // imageView.setImageResource(modellist.get(position).getImage());

        String image_URL1= modellist.get(position).getImage();

        if(image_URL1 !=null)
        {
            Glide.with(context).load(image_URL1).placeholder(R.drawable.room_one)
                    .into(imageView);
        }

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}
