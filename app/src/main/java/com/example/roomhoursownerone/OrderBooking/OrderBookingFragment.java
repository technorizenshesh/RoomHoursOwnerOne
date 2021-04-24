package com.example.roomhoursownerone.OrderBooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomhoursownerone.MessageFragment.ChatActivity;
import com.example.roomhoursownerone.MessageFragment.ChatConversation;
import com.example.roomhoursownerone.MessageFragment.ChatConversationData;
import com.example.roomhoursownerone.MessageFragment.ChatList_Adapter;
import com.example.roomhoursownerone.Preference;
import com.example.roomhoursownerone.R;
import com.example.roomhoursownerone.Utills.RetrofitClients;
import com.example.roomhoursownerone.Utills.SessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderBookingFragment extends Fragment {

    ImageView iv_back;
    CardView message_card1;

   private RecyclerView recycler_view_chat;
    ProgressBar progressBar;
    private SessionManager sessionManager;

    ArrayList<ChatConversationData> arrayList = new ArrayList<>();
    ChatList_Adapter mAdapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_message, container, false);


      //  message_card1=view.findViewById(R.id.message_card1);
        recycler_view_chat = view.findViewById(R.id.recycler_view_chat);
        progressBar = view.findViewById(R.id.progressBar);
        sessionManager = new SessionManager(getActivity());

      /*  message_card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //listener.click(new HomeFragment(listener));
                startActivity(new Intent(getActivity(), ChatActivity.class));
            }
        });*/


        if (sessionManager.isNetworkAvailable()) {

            progressBar.setVisibility(View.VISIBLE);

            getConversationDetails();

        }else {

            Toast.makeText(getActivity(), R.string.checkInternet, Toast.LENGTH_SHORT).show();

        }

        return  view;
    }

    private void setAdapter(ArrayList<ChatConversationData> arrayList) {
        mAdapter = new ChatList_Adapter(getActivity(),arrayList);
        recycler_view_chat.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recycler_view_chat.setLayoutManager(linearLayoutManager);
        recycler_view_chat.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new ChatList_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ChatConversationData model) {

                Preference.save(getActivity(),Preference.KEY_ReceiverId,model.getReceiverId());
                Preference.save(getActivity(), Preference.KEY_SenderId,model.getSenderId());
                Preference.save(getActivity(), Preference.KEY_student_Name,model.getFirstName());

                startActivity(new Intent(getActivity(), ChatActivity.class));

            }
        });
    }

    private void getConversationDetails() {

       //String ReceiverId = Preference.get(getActivity(),Preference.KEY_USER_ID);
       String ReceiverId ="5";

        Call<ChatConversation> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_conversation_detail(ReceiverId);
        call.enqueue(new Callback<ChatConversation>() {
            @Override
            public void onResponse(Call<ChatConversation> call, Response<ChatConversation> response) {

                progressBar.setVisibility(View.GONE);

                try {

                    ChatConversation finallyPr = response.body();

                    String status   = finallyPr.getStatus ();
                    String message = finallyPr.getMessage();

                    if (status.equalsIgnoreCase("1")) {

                        arrayList = (ArrayList<ChatConversationData>) finallyPr.getResult();

                        setAdapter(arrayList);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ChatConversation> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }


}