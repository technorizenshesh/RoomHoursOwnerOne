package com.example.roomhoursownerone.MessageFragment;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.roomhoursownerone.Preference;
import com.example.roomhoursownerone.R;

import java.util.ArrayList;


/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class Chat_conversasion_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<GetChatData> modelList;
    private Chat_conversasion_Adapter.OnItemClickListener mItemClickListener;

    public Chat_conversasion_Adapter(Context context, ArrayList<GetChatData> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<GetChatData> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public Chat_conversasion_Adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_conversation, viewGroup, false);

        return new Chat_conversasion_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Here you can fill your row view

        if (holder instanceof Chat_conversasion_Adapter.ViewHolder) {

            final GetChatData model = getItem(position);

            final Chat_conversasion_Adapter.ViewHolder genericViewHolder = (Chat_conversasion_Adapter.ViewHolder) holder;


            String UserId = Preference.get(mContext, Preference.KEY_ReceiverId);

            if(UserId.equalsIgnoreCase(model.getReceiverId()))
            {
                genericViewHolder.LL_right_layOut.setVisibility(View.VISIBLE);
                genericViewHolder.LL_left_layOut.setVisibility(View.GONE);

                genericViewHolder.txt_Right.setText(model.getChatMessage());
                genericViewHolder.txt_status_Right.setText(model.getStatus());


            }else
            {
                genericViewHolder.LL_right_layOut.setVisibility(View.GONE);
                genericViewHolder.LL_left_layOut.setVisibility(View.VISIBLE);

                genericViewHolder.txt_left.setText(model.getChatMessage());
                genericViewHolder.txt_status_left.setText(model.getStatus());
            }

        }
    }

    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public void SetOnItemClickListener(final Chat_conversasion_Adapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private GetChatData getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {

        void onItemClick(View view, int position, GetChatData model);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout LL_left_layOut;
        private LinearLayout LL_right_layOut;
        private TextView txt_left;
        private TextView txt_Right;
        private TextView txt_status_left;
        private TextView txt_status_Right;

        public ViewHolder(final View itemView) {
            super(itemView);

        this.LL_left_layOut = itemView.findViewById(R.id.LL_left_layOut);
        this.LL_right_layOut = itemView.findViewById(R.id.LL_right_layOut);
        this.txt_left = itemView.findViewById(R.id.txt_left);
        this.txt_Right = itemView.findViewById(R.id.txt_Right);
        this.txt_status_left = itemView.findViewById(R.id.txt_status_left);
        this.txt_status_Right = itemView.findViewById(R.id.txt_status_Right);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));

                }
            });
        }
    }


}

