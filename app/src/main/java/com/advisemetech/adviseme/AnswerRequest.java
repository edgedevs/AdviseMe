package com.advisemetech.adviseme;

import android.content.Intent;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class AnswerRequest extends Fragment {



    FloatingActionButton fb;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference reference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference,fvrtref,fvrt_listRef, questionsRequestRef, notificationsRequestRef, senderNameRef;
    RecyclerView recyclerView;
    Boolean fvrtChecker = false;
    ImageView imageView,imageView2;
    ImageButton imageButtonMenu2;
    LinearLayout moreOption;

    TextView requestBtn;

    DatabaseReference votesref,downvotesref,Allquetions, followRef, questionFollowersListRef;
    Boolean votechecker = false;
    Boolean downvotechecker = false;
    Boolean followchecker = false;
    String post_key, senderImageUrl;
    String search_text2, quest_key, quest_uid, quest_text, question_user_name, quest_time, current_user_key, senderNotificationTxt, current_user_name, time;
    QuestionMember member;
    NotificationMember notiMember;

    FirebaseRecyclerAdapter adapter;

    // private RecyclerView recyclerView;
    All_UserMmber usrMember=new All_UserMmber();
    AnswerMember answerMember= new AnswerMember();
    // private UserAdapter userAdapter;
    private List<All_UserMmber> mUsers;


    EditText search_bar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        notiMember= new NotificationMember();

        View view = inflater.inflate( R.layout.fragment_user_search, container, false );

        quest_key = getArguments().getString("q_Key");
        quest_uid = getArguments().getString("q_uid");
        quest_text = getArguments().getString("ques");
        quest_time= getArguments().getString("q_time");

        question_user_name= getArguments().getString("u_name");
        current_user_key= getArguments().getString("current_user");
        senderImageUrl=getArguments().getString("sender_url");





        databaseReference = database.getReference("All Users");

        recyclerView = view.findViewById( R.id.recycle_view2 );
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( getContext() ) );


        mUsers = new ArrayList<>();
        search_bar = view.findViewById( R.id.search_bar2 );

        search_text2= search_bar.getText().toString();








        return view;
    }


    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



     /*   databaseReference = database.getReference("All Users");

        recyclerView = getActivity().findViewById( R.id.recycle_view2 );
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( getContext() ) );



        search_bar = getActivity().findViewById( R.id.search_bar2 );

        search_text2= search_bar.getText().toString();

        mUsers = new ArrayList<>();*/

        searchUsers(search_text2);




        search_bar.addTextChangedListener( new TextWatcher() {
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
        } );





    }
    private void searchUsers(String search_text){


        Query query = FirebaseDatabase.getInstance().getReference("All Users").orderByChild("name")
                .startAt( search_text )
                .endAt( search_text+"\uf8ff" );


        FirebaseRecyclerOptions<All_UserMmber> options =
                new FirebaseRecyclerOptions.Builder<All_UserMmber>()
                        .setQuery(query,All_UserMmber.class)
                        .build();


        FirebaseRecyclerAdapter<All_UserMmber,Viewholder_Question> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<All_UserMmber, Viewholder_Question>(options) {
                    @NonNull
                    @Override
                    public Viewholder_Question onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.user_item,parent,false);

                        return new Viewholder_Question(view);
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull Viewholder_Question holder, int position, @NonNull final All_UserMmber model) {

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        final String currentUserid = user.getUid();

                        final String userKey = getRef(position).getKey();
                        //final String userName = String.valueOf(getRef(position).child("name"));



                        questionFollowersListRef = database.getReference("AllQuestions").child(userKey).child("QuestionFollwersList");

                        holder.setuseritem(getActivity(), model.getName(), model.getUrl(), model.getProf(), model.getProf());

                        // questionFollowersListRef.child(currentUserid).setValue(true);
                        // followRef = database.getReference("AllQuestions").child(postkey).child("followers");



                       // final String que = getItem(position).getQuestion();
                        final String name = getItem(position).getName();
                        final String url = getItem(position).getUrl();
                        //final String time = getItem(position).getTime();
                        //final String privacy = getItem(position).getPrivacy();
                        //final String userid = getItem(position).getUserid();

                        holder.requestBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Calendar cdate = Calendar.getInstance();
                                SimpleDateFormat currentdate = new SimpleDateFormat("dd-MMMM-yyyy");

                                Calendar ctime = Calendar.getInstance();
                                SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss");
                                final  String savedate = currentdate.format(cdate.getTime());
                                final String savetime = currenttime.format(ctime.getTime());

                                time = savedate +":"+ savetime;



                                quest_key = getArguments().getString("q_Key");
                                quest_uid = getArguments().getString("q_uid");
                                quest_text = getArguments().getString("ques");
                                question_user_name= getArguments().getString("u_name");
                                current_user_key= getArguments().getString("current_user");
                                current_user_name=getArguments().getString("current_user_name");
                                senderImageUrl=getArguments().getString("sender_url");

                                notiMember.setQesUsrName(question_user_name);
                                notiMember.setReceiverKey(userKey);
                                notiMember.setSenderKey(current_user_key);
                                notiMember.setQuestionText(quest_text);
                                notiMember.setQuestionKey(quest_key);
                                notiMember.setRequestTime(time);
                                notiMember.setSenderImgUrl(senderImageUrl);
                                notiMember.setAnsCheck("false");

                                //senderNameRef=database.getReference("All users").child(userKey);
                               senderNotificationTxt=current_user_name+" send you request for answer";

                                notiMember.setNotificationText(senderNotificationTxt);
                               questionsRequestRef= database.getReference("All Users").child(userKey).child("Notifications").child("Request Notifications");
                                String id = questionsRequestRef.push().getKey();

                                notiMember.setRequestKey(id);
                                questionsRequestRef.child(quest_key).setValue(notiMember);
                                answerMember.setAnsRequestKey(id);

                                holder.senderRequestKey=currentUserid;



                               /* Calendar ctime = Calendar.getInstance();
                                SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss");
                                final String savetime = currenttime.format(ctime.getTime());

                                time = savedate +":"+ savetime;*/


                                Toasty.success(getActivity(), "Request sent", Toast.LENGTH_SHORT, true).show();

                                TextView tv1 = getActivity().findViewById(R.id.send_request_user_item);

                                holder.requestBtn.setTextColor(Color.parseColor("#bdbdbd"));
                                holder.requestBtn.setText("Request Sent");
                            }
                        });


                    }


                };



 /*query.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mQuestion.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    QuestionMember quest = snapshot1.getValue(QuestionMember.class);
                    mQuestion.add(quest);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );*/




        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }


















    private void readUsers(){


        Query query = FirebaseDatabase.getInstance().getReference("All Users").orderByChild("name");


        FirebaseRecyclerOptions<All_UserMmber> options =
                new FirebaseRecyclerOptions.Builder<All_UserMmber>()
                        .setQuery(query,All_UserMmber.class)
                        .build();


        FirebaseRecyclerAdapter<All_UserMmber,Viewholder_Question> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<All_UserMmber, Viewholder_Question>(options) {
                    @NonNull
                    @Override
                    public Viewholder_Question onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.user_item,parent,false);

                        return new Viewholder_Question(view);
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull Viewholder_Question holder, int position, @NonNull final All_UserMmber model) {

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        final String currentUserid = user.getUid();

                        final String userKey = getRef(position).getKey();
                        //final String userName = String.valueOf(getRef(position).child("name"));



                        questionFollowersListRef = database.getReference("AllQuestions").child(userKey).child("QuestionFollwersList");

                        holder.setuseritem(getActivity(), model.getName(), model.getUrl(), model.getProf(), model.getProf());

                        // questionFollowersListRef.child(currentUserid).setValue(true);
                        // followRef = database.getReference("AllQuestions").child(postkey).child("followers");



                        // final String que = getItem(position).getQuestion();
                        final String name = getItem(position).getName();
                        final String url = getItem(position).getUrl();
                        //final String time = getItem(position).getTime();
                        //final String privacy = getItem(position).getPrivacy();
                        //final String userid = getItem(position).getUserid();

                        holder.requestBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Calendar cdate = Calendar.getInstance();
                                SimpleDateFormat currentdate = new SimpleDateFormat("dd-MMMM-yyyy");

                                Calendar ctime = Calendar.getInstance();
                                SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss");
                                final  String savedate = currentdate.format(cdate.getTime());
                                final String savetime = currenttime.format(ctime.getTime());

                                time = savedate +":"+ savetime;



                                quest_key = getArguments().getString("q_Key");
                                quest_uid = getArguments().getString("q_uid");
                                quest_text = getArguments().getString("ques");
                                question_user_name= getArguments().getString("u_name");
                                current_user_key= getArguments().getString("current_user");
                                current_user_name=getArguments().getString("current_user_name");
                                senderImageUrl=getArguments().getString("sender_url");

                                notiMember.setQesUsrName(question_user_name);
                                notiMember.setReceiverKey(userKey);
                                notiMember.setSenderKey(current_user_key);
                                notiMember.setQuestionText(quest_text);
                                notiMember.setQuestionKey(quest_key);
                                notiMember.setRequestTime(time);
                                notiMember.setSenderImgUrl(senderImageUrl);
                                notiMember.setAnsCheck("false");










                                //senderNameRef=database.getReference("All users").child(userKey);

                                senderNotificationTxt=current_user_name+" send you request for answer";



                                notiMember.setNotificationText(senderNotificationTxt);


                                questionsRequestRef= database.getReference("All Users").child(userKey).child("Notifications").child("Request Notifications");
                                String id = questionsRequestRef.push().getKey();

                                notiMember.setRequestKey(id);
                                questionsRequestRef.child(id).setValue(notiMember);

                                answerMember.setAnsRequestKey(quest_key);

                               /* questionsRequestRef.child(id).child("question post name").setValue(question_user_name);
                                questionsRequestRef.child(id).child("question key").setValue(quest_key);
                                questionsRequestRef.child(id).child("question text").setValue(quest_text);
                                questionsRequestRef.child(id).child("request-receiver key").setValue(userKey);
                                questionsRequestRef.child(id).child("request-sender key").setValue(current_user_key);*/

                                //questionsRequestRef= database.getReference("All Users").child("Answer Requests").child(quest_uid).setValue(notiMember);



                               /* Calendar ctime = Calendar.getInstance();
                                SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss");
                                final String savetime = currenttime.format(ctime.getTime());

                                time = savedate +":"+ savetime;*/






                                Toasty.success(getActivity(), "Request sent", Toast.LENGTH_SHORT, true).show();

                                TextView tv1 = getActivity().findViewById(R.id.send_request_user_item);
                                //tv1.setTextColor(Color.parseColor("#bdbdbd"));
                                // tv1.setText("Request Sent");

                                holder.requestBtn.setTextColor(Color.parseColor("#bdbdbd"));
                                holder.requestBtn.setText("Request Sent");
                            }
                        });


                    }


                };



 /*query.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mQuestion.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    QuestionMember quest = snapshot1.getValue(QuestionMember.class);
                    mQuestion.add(quest);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );*/




        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }




}
