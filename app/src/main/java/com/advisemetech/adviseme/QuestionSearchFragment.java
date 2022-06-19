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

public class QuestionSearchFragment extends Fragment {



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
    QuestionMember member;

    FirebaseRecyclerAdapter adapter;

   // private RecyclerView recyclerView;
   // private UserAdapter userAdapter;
    private List<QuestionMember> mQuestion;


    EditText search_bar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate( R.layout.fragment_question_search, container, false );

        databaseReference = database.getReference("AllQuestions");

        recyclerView = view.findViewById( R.id.recycle_view );
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( getContext() ) );



        search_bar = view.findViewById( R.id.search_bar );

        search_text2= search_bar.getText().toString();

        String searchInputToLower = search_bar.getText().toString().toLowerCase();

        String searchInputTOUpper = search_bar.getText().toString().toUpperCase();

        mQuestion = new ArrayList<>();



       /* FirebaseRecyclerOptions<QuestionMember> options =
                new FirebaseRecyclerOptions.Builder<QuestionMember>()
                        .setQuery(databaseReference,QuestionMember.class)
                        .build();

        FirebaseRecyclerAdapter<QuestionMember,Viewholder_Question> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<QuestionMember, Viewholder_Question>(options) {
                    @NonNull
                    @Override
                    public Viewholder_Question onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.question_item,parent,false);

                        return new Viewholder_Question(view);
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull Viewholder_Question holder, int position, @NonNull final QuestionMember model) {

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        final String currentUserid = user.getUid();

                        final String postkey = getRef(position).getKey();

                        questionFollowersListRef = database.getReference("AllQuestions").child(postkey).child("QuestionFollwersList");

                        // questionFollowersListRef.child(currentUserid).setValue(true);
                        // followRef = database.getReference("AllQuestions").child(postkey).child("followers");


                        holder.setitem(getActivity(), model.getName(), model.getUrl(), model.getUserid(), model.getKey()
                                , model.getQuestion(), model.getPrivacy(), model.getTime());

                        final String que = getItem(position).getQuestion();
                        final String name = getItem(position).getName();
                        final String url = getItem(position).getUrl();
                        final String time = getItem(position).getTime();
                        final String privacy = getItem(position).getPrivacy();
                        final String userid = getItem(position).getUserid();

                    }


                };



        firebaseRecyclerAdapter.startListening();

        recyclerView.setAdapter(firebaseRecyclerAdapter);*/

      //  adapter=firebaseRecyclerAdapter;

        readQuestions();
        search_bar.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence search_text, int start, int before, int count) {
                searchQuestions( search_text.toString().toLowerCase(),searchInputTOUpper,searchInputToLower );
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        } );

        return view;
    }

    private void searchQuestions(String search_text, String toUppr, String toLowr){
        Query query = FirebaseDatabase.getInstance().getReference("AllQuestions").orderByChild("question")
                .startAt( toUppr )
                .endAt( toLowr+"\uf8ff" );


        FirebaseRecyclerOptions<QuestionMember> options =
                new FirebaseRecyclerOptions.Builder<QuestionMember>()
                        .setQuery(query,QuestionMember.class)
                        .build();


        FirebaseRecyclerAdapter<QuestionMember,Viewholder_Question> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<QuestionMember, Viewholder_Question>(options) {
                    @NonNull
                    @Override
                    public Viewholder_Question onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.question_item,parent,false);

                        return new Viewholder_Question(view);
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull Viewholder_Question holder, int position, @NonNull final QuestionMember model) {

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        final String currentUserid = user.getUid();

                        final String postkey = getRef(position).getKey();

                        questionFollowersListRef = database.getReference("AllQuestions").child(postkey).child("QuestionFollwersList");

                        // questionFollowersListRef.child(currentUserid).setValue(true);
                        // followRef = database.getReference("AllQuestions").child(postkey).child("followers");


                        holder.setitem(getActivity(), model.getName(), model.getUrl(), model.getUserid(), model.getKey()
                                , model.getQuestion(), model.getPrivacy(), model.getTime());

                        /*final String que = getItem(position).getQuestion();
                        final String name = getItem(position).getName();
                        final String url = getItem(position).getUrl();
                        final String time = getItem(position).getTime();
                        final String privacy = getItem(position).getPrivacy();
                        final String userid = getItem(position).getUserid();*/

                    }


                };



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
        } );*/

        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    private void readQuestions(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("AllQuestions");
        reference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(search_bar.getText().toString().equals("")){
                    mQuestion.clear();
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
    }




}
