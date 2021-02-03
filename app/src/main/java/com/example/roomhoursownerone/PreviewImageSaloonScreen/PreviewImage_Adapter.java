package com.example.roomhoursownerone.PreviewImageSaloonScreen;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomhoursownerone.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class PreviewImage_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<Bitmap> modelList;
    private OnItemClickListener mItemClickListener;
    private View promptsView;
    private AlertDialog alertDialog;
    private Button btn_no;
    private Button btn_yes;

    public PreviewImage_Adapter(Context context, ArrayList<Bitmap> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<Bitmap> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.preview_img, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Here you can fill your row view

        if (holder instanceof ViewHolder) {

            final Bitmap model = getItem(position);

            final ViewHolder genericViewHolder = (ViewHolder) holder;

           genericViewHolder.img_add.setImageBitmap(modelList.get(position));

        }
    }

    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private Bitmap getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {

        void onItemClick(View view, int position, Bitmap model);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_add;

        public ViewHolder(final View itemView) {
            super(itemView);

           this.img_add = itemView.findViewById(R.id.img_add);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));
                }
            });
        }
    }


}

