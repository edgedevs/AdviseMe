package com.advisemetech.adviseme;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

public class TestFragment  extends Fragment  {



    Button startTestButton;
    TextView learnButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {




        View view = inflater.inflate(R.layout.fragment_test, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        learnButton=getActivity().findViewById(R.id.learnBtn);
        startTestButton=getActivity().findViewById(R.id.start_test_btn);









        startTestButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), FirstActivity.class);
                startActivity(intent);
            }

        });

        learnButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                startActivity(new Intent(getActivity(), CollapsingDemoActivity.class));

            }

        });

        //setAdView();
      //  Button button = getActivity().findViewById(R.id.Button_agree);
      //  setButton(button);

        //Intent intent = new Intent(getActivity(),FirstActivity.class);
       // startActivity(intent);
    }

   /* private void setButton(Button button){

        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), TestMainActivity.class);
                startActivity(intent);
            }

        });




    }

*/
}
