/*
package com.advisemetech.adviseme;

import android.app.Application;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {



    ImageView imageView, followIco, upvoteIco, shareIco,moreIco, downIco,  ProfileImg, RequestImgBtn ;
    TextView nameTv,profTv,verrificationTv,upvoteTv,votesNoTv;
    int votesCount;
    LinearLayout upvoteBtn, moreBtn, followBtn, followView;
    DatabaseReference reference;
    FirebaseDatabase database;

    //  ImageView imageView;
    TextView verification_result,name_result,proession_result,deletebtn,replybtn,replybtn1, question_text;
    LinearLayout answer_btn;
    DatabaseReference favouriteref, followersListRef;

    Boolean followChecker = false;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void setuser(FragmentActivity activity,String name,String uid,String prof,String url){


        database = FirebaseDatabase.getInstance();
        verification_result = itemView.findViewById(R.id.verified_adviser_user_item);
        name_result = itemView.findViewById(R.id.name_user_item);
        proession_result = itemView.findViewById(R.id.qualification_user_item);

        imageView = itemView.findViewById(R.id.profile_img_user_item);



        imageView.setOnClickListener(this);


        Picasso.get().load(url).into(imageView);
        name_result.setText(name);
        proession_result.setText(prof);
        verification_result.setText(url);

    }





    @Override
    public void onClick(View v) {

    }
}














*/
