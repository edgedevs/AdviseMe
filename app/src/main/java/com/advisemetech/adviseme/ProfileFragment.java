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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

import es.dmoral.toasty.Toasty;


public class ProfileFragment extends Fragment implements View.OnClickListener {


    ImageView imageView;

    TextView nameEt,profEt,bioEt,emailEt,webEt,imageButtonEdit, followerCounter, postCounter, followUserTV, ReportUserTV;
    ImageButton imageButtonMenu;
    DocumentReference reference;
    DatabaseReference userReportRef, followRef;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    Button feedButton;
    FirebaseFirestore firestore;
    String url= "", uid= "", bio="", email="", web="", prof="", name= "";

    String url2= "", uid2= "", bio2="", email2="", web2="", prof2="", name2= "";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile,container,false);

        uid2= getArguments().getString("q_uid");
        url2= getArguments().getString("url");
        name2= getArguments().getString("u_name");



        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();

        firestore = FirebaseFirestore.getInstance();
        reference = firestore.collection("user").document(uid2);


       // followUserTV, ReportUserTV












        followUserTV = getActivity().findViewById(R.id.followUser_id);
        ReportUserTV = getActivity().findViewById(R.id.reportUser_id);
        imageView = getActivity().findViewById(R.id.profile_img);
        nameEt = getActivity().findViewById(R.id.profile_name);
        profEt = getActivity().findViewById(R.id.user_profession);
        bioEt = getActivity().findViewById(R.id.user_bio);
        emailEt = getActivity().findViewById(R.id.user_email);
        webEt = getActivity().findViewById(R.id.user_website);



       /* Picasso.get().load(url).into(imageView);
        nameEt.setText(name);
        bioEt.setText(bio);
        emailEt.setText(email);
        webEt.setText(web);
        profEt.setText(prof);*/



      /* imageButtonMenu.setOnClickListener(this);

       imageButtonEdit.setOnClickListener(this);
       imageView.setOnClickListener(this);
       webEt.setOnClickListener(this);*/
       // feedButton.setOnClickListener(this);
        followUserTV.setOnClickListener(this);
        ReportUserTV.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();

        switch (view.getId()){
            case R.id.followUser_id:


            {

                followRef=database.getReference().child("All Users").child(uid2).child("User Followers");

                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("Want Follow User?");

                // Initializing an array of flowers


                // Set the a;ert dialog positive button
                builder.setPositiveButton("Yes", (dialogInterface, i) -> {

                    followRef.child(userid).child("follwer id").setValue(userid);
                    Toast.makeText(getContext(), "Followed", Toast.LENGTH_LONG).show();
                    // Toasty.success(getActivity(), "Submitted!", Toast.LENGTH_SHORT, true).show();
                });

                builder.setNegativeButton("Cancel", (dialogInterface, i) -> {


                    Toast.makeText(getContext(), "Canceled", Toast.LENGTH_LONG).show();

                });


                // Create the alert dialog
                AlertDialog dialog = builder.create();

                // Finally, display the alert dialog
                dialog.show();

            }

                break;
            case R.id.reportUser_id:

            {

                userReportRef=database.getReference().child("All Users").child(uid2).child("User Reports");
                String id = userReportRef.push().getKey();
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());


                builder.setTitle("Report Question");

                // Initializing an array of flowers
                final String[] reason = new String[]{
                        "Harassment",
                        "Spam",
                        "Insincere",
                        "Poorly Written",
                        "Incorrect Topics"
                };

                // Item click listener
                builder.setSingleChoiceItems(
                        reason, // Items list
                        -1, // Index of checked item (-1 = no selection)
                        (dialogInterface, i) -> {

                            // Get the alert dialog selected item's text
                            String selectedItem = Arrays.asList(reason).get(i);
                            userReportRef.child(id).child("report details").setValue(selectedItem);


                            //questionReportRef= database.getReference().child("Question Reports");
                            //questionReportRef.child(userid).child("report detail").setValue(selectedItem);



                            // Display the selected item's text on snack bar

                        });

                // Set the a;ert dialog positive button
                builder.setPositiveButton("Submit", (dialogInterface, i) -> {


                    Toast.makeText(getContext(), "Report Submitted", Toast.LENGTH_LONG).show();
                   // Toasty.success(getActivity(), "Submitted!", Toast.LENGTH_SHORT, true).show();
                });

                // Create the alert dialog
                AlertDialog dialog = builder.create();

                // Finally, display the alert dialog
                dialog.show();

            }

                break;
          /*  case R.id.iv_f1:
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
                break;*/

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
                        String u_id = task.getResult().getString("uid");

                        uid=u_id;
                         url = task.getResult().getString("url");
                        String profResult = task.getResult().getString("prof");

                        Picasso.get().load(url).into(imageView);
                        nameEt.setText(nameResult);
                        bioEt.setText(bioResult);
                        emailEt.setText(emailResult);
                        webEt.setText(webResult);
                        profEt.setText(profResult);




                       /* uid= uid2;
                        url= url2;
                        name= name2;
                        Picasso.get().load(url).into(imageView);
                        nameEt.setText(name);*/
                        // bioEt.setText(bio);
                         // emailEt.setText(email);
                         // webEt.setText(web);
                         // profEt.setText(prof);


                    }else {
                        Toast.makeText(getContext(), "Data is not retrieved", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(),CreateProfile.class);
                        startActivity(intent);
                    }
                });


    }


}
