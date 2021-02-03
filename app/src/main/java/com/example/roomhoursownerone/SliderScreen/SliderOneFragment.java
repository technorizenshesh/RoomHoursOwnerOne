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
public class SliderOneFragment extends Fragment {

    private ImageView img_skip;

    public SliderOneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View  view = inflater.inflate(R.layout.fragment_slider_one, container, false);

        img_skip = view.findViewById(R.id.img_skip);
        img_skip.setOnClickListener(new View.OnClickListener() {
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
