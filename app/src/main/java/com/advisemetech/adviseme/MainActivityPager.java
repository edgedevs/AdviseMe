package com.advisemetech.adviseme;

import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;


import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivityPager extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pager);


        findViewById(R.id.demo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivityPager.this, CollapsingDemoActivity.class));
            }
        });

       /* findViewById(R.id.demo2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FABDemo.class));
            }
        });

        findViewById(R.id.demo3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SearchBarDemoActivity.class));
            }
        });*/
    }
}
