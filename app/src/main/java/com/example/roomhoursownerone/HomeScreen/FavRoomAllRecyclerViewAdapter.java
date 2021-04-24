package com.example.roomhoursownerone.HomeScreen;


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
import com.example.roomhoursownerone.AlertManager;
import com.example.roomhoursownerone.HomeScreen.ApiModel.GetFavModel;
import com.example.roomhoursownerone.HomeScreen.ApiModel.GetFavModelData;
import com.example.roomhoursownerone.Preference;
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
public class FavRoomAllRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    int pos = 0;
    private Context mContext;
    private ArrayList<GetFavModelData> modelList;
    private OnItemClickListener mItemClickListener;
    Fragment fragment;
    private View promptsView;
    private AlertDialog  alertDialog;

    public FavRoomAllRecyclerViewAdapter(Context context, ArrayList<GetFavModelData> modelList, Fragment fragment) {
        this.mContext = context;
        this.modelList = modelList;
        this.fragment = fragment;
    }

    public void updateList(ArrayList<GetFavModelData> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_fav, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final GetFavModelData model = getItem(position);
            final ViewHolder genericViewHolder = (ViewHolder) holder;

            genericViewHolder.txt_selection_mode.setText(model.getSlectTionMode().toString());
            genericViewHolder.product_name.setText(model.getTitle().toString());


             List<String> image_URL=new ArrayList<>();

            if(image_URL !=null)
            {
                String image_URL1= model.getImage().get(0).toString();

                Glide.with(mContext).load(image_URL1).placeholder(R.drawable.dummy)
                        .into(genericViewHolder.image);
            }

            genericViewHolder.RR_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            genericViewHolder.RR_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDaliog(position,model.getId());

                }
            });

        }
    }


    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private GetFavModelData getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {

        void onItemClick(View view, int position, GetFavModelData model);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_selection_mode;
        private TextView product_name;
        private RelativeLayout RR_edit;
        private RelativeLayout RR_remove;
        private ImageView image;

        public ViewHolder(final View itemView) {
            super(itemView);

            this.txt_selection_mode=itemView.findViewById(R.id.txt_selection_mode);
            this.product_name=itemView.findViewById(R.id.product_name);
            this.image=itemView.findViewById(R.id.image);
            this.RR_edit=itemView.findViewById(R.id.RR_edit);
            this.RR_remove=itemView.findViewById(R.id.RR_remove);

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
                Toast.makeText(mContext, R.string.check, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

