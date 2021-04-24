package com.example.roomhoursownerone.AllRoomImage;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.roomhoursownerone.HomeScreen.ApiModel.GetFavModelData;
import com.example.roomhoursownerone.R;
import com.example.roomhoursownerone.Utills.RetrofitClients;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class All_Room_hourRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    int pos = 0;
    private Context mContext;
    private ArrayList<AllImageModelData> modelList;
    private OnItemClickListener mItemClickListener;
    private View promptsView;
    private AlertDialog  alertDialog;

    public All_Room_hourRecyclerViewAdapter(Context context, ArrayList<AllImageModelData> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<AllImageModelData> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_all_img, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Here you can fill your row view
        if (holder instanceof ViewHolder) {

            final AllImageModelData model = getItem(position);

            final ViewHolder genericViewHolder = (ViewHolder) holder;

            String image_URL1= model.getImage().toString();

                if(image_URL1 !=null)
                {
                    Glide.with(mContext).load(image_URL1).placeholder(R.drawable.dummy)
                            .into(genericViewHolder.RROm_image);
                }

        }
    }


    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private AllImageModelData getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {

        void onItemClick(View view, int position, AllImageModelData model);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView RROm_image;

        public ViewHolder(final View itemView) {
            super(itemView);

            this.RROm_image=itemView.findViewById(R.id.RROm_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));

                }
            });
        }
    }

    private void AlertDaliog(int position,String id) {

        LayoutInflater li;
        Button btn_no,btn_yes;
        AlertDialog.Builder alertDialogBuilder;
        li = LayoutInflater.from(mContext);
        li = LayoutInflater.from(mContext);
        promptsView = li.inflate(R.layout.alert_dailoge, null);
        btn_no = (Button) promptsView.findViewById(R.id.btn_no);
        btn_yes = (Button) promptsView.findViewById(R.id.btn_yes);
        alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setView(promptsView);

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                modelList.remove(position);
                notifyDataSetChanged();
                getFavuriteApi(id);
                alertDialog.dismiss();
                //   Toast.makeText(mContext,"Sucessfully  Delete Site ",Toast.LENGTH_SHORT).show();
            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    public void getFavuriteApi(String id) {

        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .delete_room_details(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    JSONObject jsonObject = new JSONObject(response.body().string());

                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");


                    if (status.equalsIgnoreCase("1")){

                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(mContext, "Please Check Network", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

