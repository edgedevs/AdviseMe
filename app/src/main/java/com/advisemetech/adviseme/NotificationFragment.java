package com.advisemetech.adviseme;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class NotificationFragment extends Fragment implements View.OnClickListener{



    FloatingActionButton fb;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference reference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference,fvrtref,fvrt_listRef, questionsRequestRef, notificationsRequestRef, markAllRef;
    RecyclerView recyclerView;
    Boolean fvrtChecker = false;
    ImageView imageView,imageView2;

    ImageButton imageButtonMenu2;
    LinearLayout moreOption;

    TextView requestBtn, markAllTv;
    boolean markChecker=false;

    DatabaseReference votesref,downvotesref,Allquetions, followRef, questionFollowersListRef;
    Boolean votechecker = false;
    Boolean downvotechecker = false;
    Boolean followchecker = false;
    String post_key;
    String search_text2, quest_key, quest_uid, quest_text, question_user_name, quest_time, current_user_key;
    QuestionMember member;

    NotificationMember notiMember;

    FirebaseRecyclerAdapter adapter;

    // private RecyclerView recyclerView;
    // private UserAdapter userAdapter;
    private List<All_UserMmber> mUsers;


    EditText search_bar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View view = inflater.inflate( R.layout.fragment_notifications, container, false );

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String currentUserid = user.getUid();

        markAllRef=database.getReference("All Users").child(currentUserid).child("Notifications").child("notificationCh");
        markAllRef.setValue("false");

        notiMember= new NotificationMember();




       /* quest_key = getArguments().getString("q_Key");
        quest_uid = getArguments().getString("q_uid");
        quest_text = getArguments().getString("ques");
        quest_time= getArguments().getString("q_time");

        question_user_name= getArguments().getString("u_name");
        current_user_key= getArguments().getString("current_user");


*/

        databaseReference = database.getReference("All Users");

        recyclerView = view.findViewById( R.id.recycle_view_notifications );
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( getContext() ) );


        mUsers = new ArrayList<>();
     /*   search_bar = view.findViewById( R.id.search_bar2 );

        search_text2= search_bar.getText().toString();*/




        return view;
    }


    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);




        searchUsers();

        markAllTv= getActivity().findViewById(R.id.mark_all_read);
        markAllTv.setOnClickListener(this);



        markAllTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                final String currentUserid = user.getUid();
                Toasty.success(getActivity(), "Clicked", Toast.LENGTH_SHORT, true).show();

                markAllRef=database.getReference("All Users").child(currentUserid).child("Notifications").child("notificationCh");
                markAllRef.setValue("true");
                // searchUsers();

            }
        });


        /*search_bar.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence search_text, int start, int count, int after) {

                searchUsers( search_text.toString().toLowerCase() );

            }

            @Override
            public void onTextChanged(CharSequence search_text, int start, int before, int count) {
                searchUsers( search_text.toString().toLowerCase() );
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        } );*/





    }
    private void searchUsers(){
        //Query query = FirebaseDatabase.getInstance().getReference("All Users").orderByChild("name");

       /* NotificationMember ntMmbr= new NotificationMember();
        final String currentUserid2= ntMmbr.getReceiverKey();*/


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String currentUserid = user.getUid();




       // questionsRequestRef=database.getReference("All Users").child(currentUserid).child("Notifications").child("Request Notifications");
        Query query = database.getReference("All Users").child(currentUserid).child("Notifications").child("Request Notifications").orderByChild("time");


        FirebaseRecyclerOptions<NotificationMember> options =
                new FirebaseRecyclerOptions.Builder<NotificationMember>()
                        .setQuery(query,NotificationMember.class)
                        .build();


        FirebaseRecyclerAdapter<NotificationMember,Viewholder_Question> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<NotificationMember, Viewholder_Question>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull Viewholder_Question holder, int position, @NonNull NotificationMember model) {

                        Context context;
                        CharSequence text;
                       // Toasty.success(getActivity(), model.getNotificationText(), Toast.LENGTH_SHORT, true).show();

                        holder.setNotificationItem(getActivity(),  model.getNotificationText(),  model.getQuestionText(),model.getSenderImgUrl());






                    }



                    @NonNull
                    @Override
                    public Viewholder_Question onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.notification_item,parent,false);

                        return new Viewholder_Question(view);
                    }

                    /*@Override
                    protected void onBindViewHolder(@NonNull Viewholder_Question holder, int position, @NonNull final NotificationMember model) {

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        final String currentUserid = user.getUid();

                        final String userKey = getRef(position).getKey();



                        questionFollowersListRef = database.getReference("AllQuestions").child(userKey).child("QuestionFollwersList");

                        holder.setNotificationItem(getActivity(),  model.getName(),  model.getProf(),model.getUrl());

                         questionFollowersListRef.child(currentUserid).setValue(true);
                         followRef = database.getReference("AllQuestions").child(postkey).child("followers");



                         final String que = getItem(position).getQuestion();
                        final String name = getItem(position).getName();
                        final String url = getItem(position).getUrl();
                        final String time = getItem(position).getTime();
                        final String privacy = getItem(position).getPrivacy();
                        final String userid = getItem(position).getUserid();

                        holder.requestBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                quest_key = getArguments().getString("q_Key");
                                quest_uid = getArguments().getString("q_uid");
                                quest_text = getArguments().getString("ques");
                                question_user_name= getArguments().getString("u_name");
                                current_user_key= getArguments().getString("current_user");


                                questionsRequestRef= database.getReference("All Users").child("Notifications").child("Requests for Answers");
                                String id = questionsRequestRef.push().getKey();

                                questionsRequestRef.child(id).child("question post name").setValue(question_user_name);
                                questionsRequestRef.child(id).child("question key").setValue(quest_key);
                                questionsRequestRef.child(id).child("question text").setValue(quest_text);
                                questionsRequestRef.child(id).child("request-receiver key").setValue(userKey);
                                questionsRequestRef.child(id).child("request-sender key").setValue(current_user_key);


                                questionsRequestRef= database.getReference("All Users").child("Answer Requests").child(quest_uid);



                                Calendar ctime = Calendar.getInstance();
                                SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss");
                                final String savetime = currenttime.format(ctime.getTime());

                                time = savedate +":"+ savetime;

                                Toasty.success(getActivity(), "Request sent", Toast.LENGTH_SHORT, true).show();


                                tv1.setTextColor(Color.parseColor("#bdbdbd"));
                                 tv1.setText("Request Sent");


                            }
                        });


                    }*/



                };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.mark_all_read:
            {
                Toasty.success(getActivity(), "Clicked", Toast.LENGTH_SHORT, true).show();
            }
                break;
        }




    }
}
