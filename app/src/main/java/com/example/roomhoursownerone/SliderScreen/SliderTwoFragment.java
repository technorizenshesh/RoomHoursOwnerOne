package com.example.roomhoursownerone.SliderScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;

import com.example.roomhoursownerone.MainActivityLogin;
import com.example.roomhoursownerone.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SliderTwoFragment extends Fragment {

    private ImageView img_skip_two;
    public SliderTwoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_slider_two, container, false);

        img_skip_two = view.findViewById(R.id.img_skip_two);
        img_skip_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), MainActivityLogin.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }
}
