package com.advisemetech.adviseme;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.MotionEvent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;




public class Fragment1 extends Fragment implements View.OnClickListener{


    ImageView imageView;
    TextView nameEt,profEt,bioEt,emailEt,webEt,imageButtonEdit;
    ImageButton imageButtonMenu;
    DocumentReference reference;
    Button feedButton;
    FirebaseFirestore firestore;
    String url;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment1,container,false);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();

        firestore = FirebaseFirestore.getInstance();
        reference = firestore.collection("user").document(userid);



        imageView = getActivity().findViewById(R.id.iv_f1);
        nameEt = getActivity().findViewById(R.id.tv_name_f1);
        profEt = getActivity().findViewById(R.id.tv_prof_f1);
        bioEt = getActivity().findViewById(R.id.tv_bio_f1);
        emailEt = getActivity().findViewById(R.id.tv_email_f1);
        webEt = getActivity().findViewById(R.id.tv_web_f1);

        imageButtonEdit = getActivity().findViewById(R.id.ib_edit_f1);
        imageButtonMenu = getActivity().findViewById(R.id.ib_menu_f1);
        feedButton = getActivity().findViewById(R.id.btn_sendmessage_f1);



       imageButtonMenu.setOnClickListener(this);

       imageButtonEdit.setOnClickListener(this);
       imageView.setOnClickListener(this);
       webEt.setOnClickListener(this);
        feedButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ib_edit_f1:
                Intent intent = new Intent(getActivity(),UpdateProfile.class);
                startActivity(intent);
                break;
            case R.id.ib_menu_f1:
                BottomSheetMenu bottomSheetMenu = new BottomSheetMenu();
                bottomSheetMenu.show(getFragmentManager(),"bottomsheet");

                break;
            case R.id.iv_f1:
                Intent intent1 = new Intent(getActivity(),ImageActivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_web_f1:
                try {
                    String url = webEt.getText().toString();
                    Intent intent2 = new Intent(Intent.ACTION_VIEW);
                    intent2.setData(Uri.parse(url));
                    startActivity(intent2);
                }catch (Exception e){
                    Toast.makeText(getActivity(), "Invalid Url", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_sendmessage_f1:
            {

                Fragment2 fragment = new Fragment2();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, fragment);
                transaction.commit();
            }
                break;

        }
    }

    @Override
    public void onStart() {
        super.onStart();


        reference.get()
                .addOnCompleteListener(task -> {

                    if (task.getResult().exists()){

                        String nameResult = task.getResult().getString("name");
                        String bioResult = task.getResult().getString("bio");
                        String emailResult = task.getResult().getString("email");
                        String webResult = task.getResult().getString("web");
                         url = task.getResult().getString("url");
                        String profResult = task.getResult().getString("prof");


                        Picasso.get().load(url).into(imageView);
                        nameEt.setText(nameResult);
                        bioEt.setText(bioResult);
                        emailEt.setText(emailResult);
                        webEt.setText(webResult);
                        profEt.setText(profResult);


                    }else {
                        Toast.makeText(getContext(), "Data is not retrieved", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(),CreateProfile.class);
                        startActivity(intent);
                    }
                });


    }


}
