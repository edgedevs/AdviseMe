/*
package com.advisemetech.adviseme;

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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
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

public class UserSearch extends Fragment {



    FloatingActionButton fb;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference reference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference,fvrtref,fvrt_listRef, questionRef;
    RecyclerView recyclerView;
    Boolean fvrtChecker = false;
    ImageView imageView,imageView2;
    ImageButton imageButtonMenu2;
    LinearLayout moreOption;

    DatabaseReference votesref,downvotesref,Allquetions, followRef, questionFollowersListRef;
    Boolean votechecker = false;
    Boolean downvotechecker = false;
    Boolean followchecker = false;
    String post_key;
    String search_text2;
    All_UserMmber member;

    FirebaseRecyclerAdapter adapter;

    // private RecyclerView recyclerView;
    // private UserAdapter userAdapter;
    private List<All_UserMmber> mUser;


    EditText search_bar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate( R.layout.fragment_user_search, container, false );

        databaseReference = database.getReference("All Users");

        recyclerView = view.findViewById(R.id.recycle_view2);
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( getContext() ) );



        search_bar = view.findViewById( R.id.search_bar2 );

        search_text2= search_bar.getText().toString();

        mUser = new ArrayList<>();



        FirebaseRecyclerOptions<All_UserMmber> options =
                new FirebaseRecyclerOptions.Builder<All_UserMmber>()
                        .setQuery(databaseReference,All_UserMmber.class)
                        .build();

        FirebaseRecyclerAdapter<All_UserMmber,UserViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<All_UserMmber, UserViewHolder>(options) {

                    @NonNull
                    @Override
                    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.user_item,parent,false);

                        return new UserViewHolder(view);
                    }

                    @Override
                    public void onBindViewHolder(@NonNull UserViewHolder holder, int position, @NonNull final All_UserMmber model) {

                       */
/* FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        final String currentUserid = user.getUid();

                        final String postkey = getRef(position).getKey();

                        questionFollowersListRef = database.getReference("AllQuestions").child(postkey).child("QuestionFollwersList");

                        // questionFollowersListRef.child(currentUserid).setValue(true);
                        // followRef = database.getReference("AllQuestions").child(postkey).child("followers");*//*



                        holder.setuser(getActivity(), model.getName(), model.getUid(), model.getProf(), model.getUrl());

                       */
/* final String que = getItem(position).getQuestion();
                        final String name = getItem(position).getName();
                        final String url = getItem(position).getUrl();
                        final String time = getItem(position).getTime();
                        final String privacy = getItem(position).getPrivacy();
                        final String userid = getItem(position).getUserid();*//*


                    }


                };



        firebaseRecyclerAdapter.startListening();

        recyclerView.setAdapter(firebaseRecyclerAdapter);

        //  adapter=firebaseRecyclerAdapter;

        readUsers();
        search_bar.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence search_text, int start, int before, int count) {
                searchUsers( search_text.toString().toLowerCase() );
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        } );

        return view;
    }

    private void setuser(FragmentActivity activity, String name, String uid, String prof, String url) {



    }

    private void searchUsers(String search_text){
        Query query = FirebaseDatabase.getInstance().getReference("All Users").orderByChild("name")
                .startAt( search_text )
                .endAt( search_text+"\uf8ff" );


        FirebaseRecyclerOptions<All_UserMmber> options =
                new FirebaseRecyclerOptions.Builder<All_UserMmber>()
                        .setQuery(query,All_UserMmber.class)
                        .build();


        FirebaseRecyclerAdapter<All_UserMmber,UserViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<All_UserMmber, UserViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull UserViewHolder holder, int position, @NonNull All_UserMmber model) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.user_item,parent,false);
                    }

                    @NonNull
                    @Override
                    public All_UserMmber onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.user_item,parent,false);

                        return new UserViewHolder(view);
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull Viewholder_Question holder, int position, @NonNull final QuestionMember model) {

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        final String currentUserid = user.getUid();

                        final String postkey = getRef(position).getKey();

                        questionFollowersListRef = database.getReference("All Users").child("QuestionFollwersList");

                        // questionFollowersListRef.child(currentUserid).setValue(true);
                        // followRef = database.getReference("AllQuestions").child(postkey).child("followers");


                        holder.setitem(getActivity(), model.getName(), model.getUrl(), model.getUserid(), model.getKey()
                                , model.getQuestion(), model.getPrivacy(), model.getTime());

                        */
/*final String que = getItem(position).getQuestion();
                        final String name = getItem(position).getName();
                        final String url = getItem(position).getUrl();
                        final String time = getItem(position).getTime();
                        final String privacy = getItem(position).getPrivacy();
                        final String userid = getItem(position).getUserid();*//*


                    }


                };



       */
/* query.addValueEventListener( new ValueEventListener() {
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
        } );*//*


        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    private void readUsers(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("All Users");
        reference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(search_bar.getText().toString().equals("")){
                    mUser.clear();
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                        All_UserMmber user = snapshot1.getValue( All_UserMmber.class );
                        mUser.add(user);
                    }
                    //adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );
    }




}
*/
