package com.advisemetech.adviseme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;




public class FirstActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        //setAdView();
        Button button = findViewById(R.id.button_agree);
        setButton(button);


    }



    private void setButton(Button button){

        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(FirstActivity.this, TestMainActivity.class);
                startActivity(intent);
            }

        });




    }



}
