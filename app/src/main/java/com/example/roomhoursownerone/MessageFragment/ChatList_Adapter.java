package com.example.roomhoursownerone.MessageFragment;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.roomhoursownerone.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;


/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class ChatList_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<ChatConversationData> modelList;
    private ChatList_Adapter.OnItemClickListener mItemClickListener;

    public ChatList_Adapter(Context context, ArrayList<ChatConversationData> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<ChatConversationData> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public ChatList_Adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_list, viewGroup, false);

        return new ChatList_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Here you can fill your row view

        if (holder instanceof ChatList_Adapter.ViewHolder) {

            final ChatConversationData model = getItem(position);

            final ChatList_Adapter.ViewHolder genericViewHolder = (ChatList_Adapter.ViewHolder) holder;

            genericViewHolder.txt_receiverName.setText(model.getFirstName());
            genericViewHolder.txt_last_message.setText(model.getLastMessage());

            String ProfileImage=model.getImage().toString();
            if(ProfileImage !=null)
            {
                Glide.with(mContext).load(ProfileImage).placeholder(R.drawable.home_banner3)
                        .into(genericViewHolder.img_profile);
            }


        }
    }

    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public void SetOnItemClickListener(final ChatList_Adapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private ChatConversationData getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {

        void onItemClick(View view, int position, ChatConversationData model);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_receiverName;
        private TextView txt_last_message;
        private RoundedImageView img_profile;

        public ViewHolder(final View itemView) {
            super(itemView);

      this.txt_receiverName = itemView.findViewById(R.id.txt_receiverName);
      this.txt_last_message = itemView.findViewById(R.id.txt_last_message);
      this.img_profile = itemView.findViewById(R.id.img_profile);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));

                }
            });
        }
    }


}

