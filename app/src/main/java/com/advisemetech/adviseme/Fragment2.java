package com.advisemetech.adviseme;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
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

import java.util.Arrays;

import es.dmoral.toasty.Toasty;

public class Fragment2 extends Fragment implements View.OnClickListener {

    FloatingActionButton fb;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference reference, ref2;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference,fvrtref,fvrt_listRef, questionRef, questionReportRef;
    RecyclerView recyclerView;
    Boolean fvrtChecker = false;
    ImageView imageView,imageView2,searchVw;
    ImageButton imageButtonMenu2;
    LinearLayout moreOption;

    DatabaseReference votesref,downvotesref,Allquetions, followRef, questionFollowersListRef;
    Boolean votechecker = false;
    Boolean downvotechecker = false;
    Boolean followchecker = false;
    String post_key, notificationSndrname, notificationSndrUrl, sendrUid;
   // private List<UserItem> mUser;
    All_UserMmber userMember;
    QuestionMember member;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {




        View view = inflater.inflate(R.layout.fragment2, container, false);

        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    @Nullable
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserid = user.getUid();




      /*  Allquetions = database.getReference("AllQuestions").child(post_key).child("Answer");
        votesref = database.getReference("votes");*/


        votesref = database.getReference("votes");
        downvotesref = database.getReference("downvotes");

        // followRef = database.getReference("followers");

        // ansFvrt_ListRef = database.getReference("favoriteList").child(uid);





        recyclerView = getActivity().findViewById(R.id.rv_f2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        databaseReference = database.getReference("AllQuestions");
        questionRef=database.getReference("AllQuestions");

       // Query qry=  database.getReference("AllQuestions").orderByChild("u");
        member = new QuestionMember();
        fvrtref = database.getReference("favourites");
        fvrt_listRef = database.getReference("favoriteList").child(currentUserid);

        //  questionFollowersListRef= database.getReference("QuestionFollowersList").child(currentUserid);




        imageView = getActivity().findViewById(R.id.iv_f2);

        searchVw = getActivity().findViewById(R.id.search_imag);

        //imageButtonMenu2 = getActivity().findViewById(R.id.ib_menu_f1);
        fb = getActivity().findViewById(R.id.floatingActionButton);
        reference = db.collection("user").document(currentUserid);

        searchVw.setOnClickListener(this);
        fb.setOnClickListener(this);
        imageView.setOnClickListener(this);

        //imageButtonMenu2.setOnClickListener(this);





        FirebaseRecyclerOptions<QuestionMember> options =
                new FirebaseRecyclerOptions.Builder<QuestionMember>()
                        .setQuery(databaseReference,QuestionMember.class)
                        .build();

        FirebaseRecyclerAdapter<QuestionMember,Viewholder_Question> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<QuestionMember, Viewholder_Question>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull Viewholder_Question holder, int position, @NonNull final QuestionMember model) {

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        final String currentUserid = user.getUid();
                        final String currentUsername = user.getDisplayName();
                        final  String postkey = getRef(position).getKey();


                        questionFollowersListRef = database.getReference("AllQuestions").child(postkey).child("QuestionFollwersList");

                        // questionFollowersListRef.child(currentUserid).setValue(true);
                        // followRef = database.getReference("AllQuestions").child(postkey).child("followers");


                        ref2 = db.collection("user").document(currentUserid);
                        //readUsers();
                       // holder.setuseritem(getActivity(), ref2.get()., userMember.getUrl(), userMember.getProf(), userMember.getProf());
                        holder.setitem(getActivity(),model.getName(),model.getUrl(),model.getUserid(),model.getKey()
                                ,model.getQuestion(),model.getPrivacy(),model.getTime());

                        final String que = getItem(position).getQuestion();
                        final String name = getItem(position).getName();
                        final String url = getItem(position).getUrl();
                        final  String time = getItem(position).getTime();
                        final String privacy = getItem(position).getPrivacy();
                        final String userid = getItem(position).getUserid();
                      //  final String prof = getItem(position);

                        final String userKey = getRef(position).getKey();


/*
                        private ArrayList<All_UserMmber> createSampleData(){
                            ArrayList<All_UserMmber> items = new ArrayList<>();
                            items.add(new All_UserMmber(mUser));

                            return items;
                        }*/

                        // questionFollowersListRef = database.getReference("AllQuestions").child("QuestionFollwersList");

                        holder.question_result.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getActivity(),ReplyActivity.class);
                                intent.putExtra("uid",userid);
                                intent.putExtra("q",que);
                                intent.putExtra("postkey",postkey);
                                intent.putExtra("notiSendrUid",sendrUid);

                                //  intent.putExtra("key",privacy);
                                startActivity(intent);

                            }
                        });

                        holder.imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                ProfileFragment fragment = new ProfileFragment();
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                                Bundle args = new Bundle();
                               // args.putString("q_Key", model.getKey());
                                args.putString("q_uid", userid);
                                args.putString("url", url);
                                args.putString("u_name", name);
                             //   args.putString("ques", que);
                               // args.putString("q_time", time);

                             //   args.putString("current_user", currentUserid);
                                fragment.setArguments(args);
                                transaction.replace(R.id.frame_layout, fragment);
                                transaction.commit();

                            }
                        });

                        holder.RequestImgBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                AnswerRequest fragment = new AnswerRequest();
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                                Bundle args = new Bundle();
                                args.putString("q_Key", model.getKey());
                                args.putString("q_uid", userid );
                                args.putString("ques", que);
                                args.putString("q_time", time);
                                args.putString("u_name", name);
                                args.putString("current_user", currentUserid);
                                args.putString("current_user_name", notificationSndrname);
                                args.putString("sender_url", notificationSndrUrl);




                                fragment.setArguments(args);
                                transaction.replace(R.id.frame_layout, fragment);
                                transaction.commit();

                            }
                        });





                        holder.answer_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getActivity(),ReplyActivity.class);
                                intent.putExtra("uid",userid);
                                intent.putExtra("q",que);
                                intent.putExtra("postkey",postkey);


                                startActivity(intent);




                            }



                        });


                      /*  holder.answer_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getActivity(),ReplyActivity.class);
                                intent.putExtra("uid",userid);
                                intent.putExtra("q",que);
                                intent.putExtra("postkey",postkey);
                                //  intent.putExtra("key",privacy);
                                startActivity(intent);
                            }



                        });*/















                        //  holder.favouriteChecker(postkey);

                       /* holder.fvrt_btn.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {

                                fvrtChecker = true;

                                fvrtref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        if (fvrtChecker.equals(true)){
                                            if (snapshot.child(postkey).hasChild(currentUserid)){
                                                fvrtref.child(postkey).child(currentUserid).removeValue();
                                                delete(time);
                                                Toast.makeText(getActivity(), "Removed from favourite", Toast.LENGTH_SHORT).show();
                                                fvrtChecker = false;
                                            }else {


                                                fvrtref.child(postkey).child(currentUserid).setValue(true);
                                                member.setName(name);
                                                member.setTime(time);
                                                member.setPrivacy(privacy);
                                                member.setUserid(userid);
                                                member.setUrl(url);
                                                member.setQuestion(que);

                                              //  String id = fvrt_listRef.push().getKey();
                                                fvrt_listRef.child(postkey).setValue(member);
                                                fvrtChecker = false;

                                                Toast.makeText(getActivity(), "Added to favourite", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                        });*/


                       /* holder.moreBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                PopupMenu popup = new PopupMenu(getActivity(), view);
                                MenuInflater inflater = popup.getMenuInflater();

                                inflater.inflate(R.menu.context_menu, popup.getMenu());
                                popup.show();
                            }
                        });*/


                        holder.moreBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                PopupMenu popup = new PopupMenu(getActivity(), view);
                                MenuInflater inflater = popup.getMenuInflater();

                                inflater.inflate(R.menu.context_menu, popup.getMenu());

                                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem item) {
                                        switch (item.getItemId()) {
                                            case R.id.share:
                                            {
                                                Intent shareIntent =   new Intent(android.content.Intent.ACTION_SEND);
                                                shareIntent.setType("text/plain");
                                                shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Insert Subject here");
                                                String app_url = " https://play.google.com/store/apps/details?id=my.example.javatpoint";
                                                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,app_url);
                                                startActivity(Intent.createChooser(shareIntent, "Share via"));
                                            }
                                            return true;
                                            case R.id.report:
                                            {
                                                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());


                                                builder.setTitle("Report Question");

                                                // Initializing an array of flowers
                                                final String[] flowers = new String[]{
                                                        "Harassment",
                                                        "Spam",
                                                        "Insincere",
                                                        "Poorly Written",
                                                        "Incorrect Topics"
                                                };

                                                // Item click listener
                                                builder.setSingleChoiceItems(
                                                        flowers, // Items list
                                                        -1, // Index of checked item (-1 = no selection)
                                                        (dialogInterface, i) -> {
                                                            // Get the alert dialog selected item's text
                                                            String selectedItem = Arrays.asList(flowers).get(i);

                                                            questionReportRef= database.getReference().child("Question Reports");
                                                            questionReportRef.child(userid).child("report detail").setValue(selectedItem);



                                                            // Display the selected item's text on snack bar

                                                        });

                                                // Set the a;ert dialog positive button
                                                builder.setPositiveButton("Submit", (dialogInterface, i) -> {
                                                    Toasty.success(getActivity(), "Submitted!", Toast.LENGTH_SHORT, true).show();
                                                });

                                                // Create the alert dialog
                                                AlertDialog dialog = builder.create();

                                                // Finally, display the alert dialog
                                                dialog.show();
                                            }
                                            return true;
                                            case R.id.delete_qp:
                                            {
                                                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());


                                                builder.setTitle("Delete Question");
                                                builder.setPositiveButton("Delete", (dialogInterface, i) -> {

                                                    questionRef.child(postkey).removeValue();

                                                    Toasty.success(getActivity(), "Deleted", Toast.LENGTH_SHORT, true).show();
                                                });

                                                builder.setNegativeButton(
                                                        "Cancel", (dialogInterface, i) -> {

                                                        });
                                                AlertDialog dialog = builder.create();

                                                // Finally, display the alert dialog
                                                dialog.show();
                                            }
                                            return true;

                                            case R.id.bookmark_question:
                                            {
                                                fvrtChecker = true;

                                                fvrtref.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                        if (fvrtChecker.equals(true)){
                                                            if (snapshot.child(postkey).hasChild(currentUserid)){
                                                                fvrtref.child(postkey).child(currentUserid).removeValue();
                                                                delete(time);
                                                                Toast.makeText(getActivity(), "Removed from favourite", Toast.LENGTH_SHORT).show();
                                                                fvrtChecker = false;
                                                            }else {


                                                                fvrtref.child(postkey).child(currentUserid).setValue(true);
                                                                member.setName(name);
                                                                member.setTime(time);
                                                                member.setPrivacy(privacy);
                                                                member.setUserid(userid);
                                                                member.setUrl(url);
                                                                member.setQuestion(que);

                                                                //  String id = fvrt_listRef.push().getKey();
                                                                fvrt_listRef.child(postkey).setValue(member);
                                                                fvrtChecker = false;

                                                                Toasty.success(getActivity(), "Added to favourite!", Toast.LENGTH_SHORT, true).show();


                                                            }
                                                        }

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
                                            }

                                            return true;

                                            case R.id.q_downvote:
                                            {
                                                downvotechecker = true;
                                                downvotesref.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                        if (downvotechecker.equals(true)){
                                                            if (snapshot.child(postkey).hasChild(currentUserid)){
                                                                downvotesref.child(postkey).child(currentUserid).removeValue();

                                                                downvotechecker = false;
                                                            }else {
                                                                downvotesref.child(postkey).child(currentUserid).setValue(true);
                                                                Toasty.success(getActivity(), "Question Downvoted", Toast.LENGTH_SHORT, true).show();

                                                                downvotechecker = false;
                                                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
                                            }
                                            default:
                                                return false;
                                        }
                                    }
                                });
                                popup.show();
                            }
                        });





                        holder.upvoteChecker(postkey);
                        holder.upvoteBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                votechecker = true;
                                votesref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        if (votechecker.equals(true)){
                                            if (snapshot.child(postkey).hasChild(currentUserid)){
                                                votesref.child(postkey).child(currentUserid).removeValue();

                                                votechecker = false;
                                            }else {
                                                votesref.child(postkey).child(currentUserid).setValue(true);

                                                votechecker = false;
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                        });


                        holder.followerChecker(postkey);
                        holder.followView.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                followchecker = true;
                                questionFollowersListRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        if (followchecker.equals(true)){
                                            if (snapshot.hasChild(currentUserid)){
                                                questionFollowersListRef.child(currentUserid).removeValue();
                                                followchecker = false;
                                            }else {
                                                questionFollowersListRef.child(currentUserid).setValue(true);
                                                followchecker = false;





                                                /*member.setName(name);
                                                member.setTime(time);
                                                // member.setPrivacy(privacy);
                                                member.setUserid(userid);
                                                member.setUrl(url);
                                                member.setQuestion(que);
                                                //  String id = fvrt_listRef.push().getKey();
                                                questionFollowersListRef.child(postkey).setValue(member);*/


                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                        });




                    }

                    @NonNull
                    @Override
                    public Viewholder_Question onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.question_item,parent,false);

                        return new Viewholder_Question(view);



                    }
                };
        firebaseRecyclerAdapter.startListening();

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }


   /* private void readUsers(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("AllQuestions");
        reference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(search_bar.getText().toString().equals("")){
                    m.clear();
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                        QuestionMember user = snapshot1.getValue( QuestionMember.class );
                        mQuestion.add(user);
                    }
                    //adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );
    }*/




    private void readUsers(){

        databaseReference = database.getReference("All Users");

        recyclerView = getActivity().findViewById( R.id.recycle_view2 );
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( getContext() ) );

        FirebaseDatabase database = FirebaseDatabase.getInstance();
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

                        final String postkey = getRef(position).getKey();

                        //questionFollowersListRef = database.getReference("AllQuestions").child(postkey).child("QuestionFollwersList");

                        holder.setuseritem(getActivity(), model.getName(), model.getUrl(), model.getProf(), model.getProf());

                        // questionFollowersListRef.child(currentUserid).setValue(true);
                        // followRef = database.getReference("AllQuestions").child(postkey).child("followers");



                       /* final String que = getItem(position).getQuestion();
                        final String name = getItem(position).getName();
                        final String url = getItem(position).getUrl();
                        final String time = getItem(position).getTime();
                        final String privacy = getItem(position).getPrivacy();
                        final String userid = getItem(position).getUserid();*/


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
    void delete(String time){

        Query query = fvrt_listRef.orderByChild("time").equalTo(time);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                    dataSnapshot1.getRef().removeValue();

                    Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_f2:
                BottomSheetF2 bottomSheetF2 = new BottomSheetF2();
                bottomSheetF2.show(getFragmentManager(),"bottom");
                break;
            case R.id.floatingActionButton:
                Intent intent = new Intent(getActivity(), AskActivity.class);
                startActivity(intent);
                break;
            case R.id.ib_menu_f1:
                BottomSheetMenu bottomSheetMenu2 = new BottomSheetMenu();
                bottomSheetMenu2.show(getFragmentManager(),"bottomsheet");
                break;
            case R.id.search_imag:
            {

                QuestionSearchFragment fragment = new QuestionSearchFragment();
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
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().exists()){
                            String uid = task.getResult().getString("uid");
                            sendrUid =uid;
                            String url = task.getResult().getString("url");
                            notificationSndrUrl=url;
                            String name2 = task.getResult().getString("name");
                            notificationSndrname =name2;


                            Picasso.get().load(url).into(imageView);
                        }else {
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}


