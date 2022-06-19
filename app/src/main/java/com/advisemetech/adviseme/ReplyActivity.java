 package com.advisemetech.adviseme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class ReplyActivity extends AppCompatActivity {



    String uid,question,post_key,key, notisendrUid;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference reference ,reference2;

    TextView nametv,questiontv,tvreply;
    RecyclerView recyclerView;
    ImageView imageViewQue,imageViewUser;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    Boolean fvrtChecker = false;
    DatabaseReference downvotesref,votesref,Allquetions, fvrtref, ansFvrt_ListRef, ansRef, answerReportRef;
    AnswerMember member; String anwerkey;
    Boolean votechecker = false;
    Boolean downvotechecker = false;
    String position3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);



        nametv = findViewById(R.id.name_reply_tv);
        questiontv = findViewById(R.id.que_reply_tv);
        imageViewQue = findViewById(R.id.iv_que_user);
        imageViewUser = findViewById(R.id.iv_reply_user);
        tvreply = findViewById(R.id.answer_tv);

       /* LinearLayout sharePost;
        sharePost= findViewById(R.id.ans_share_item);*/

        recyclerView = findViewById(R.id.rv_ans);
        recyclerView.setLayoutManager(new LinearLayoutManager(ReplyActivity.this));



        Bundle extra = getIntent().getExtras();
        if (extra != null){
            uid = extra.getString("uid");
            post_key = extra.getString("postkey");
            question = extra.getString("q");
            notisendrUid=extra.getString("notiSendrUid");
            position3=extra.getString("position");



           // key = extra.getString("key");
        }else {
            Toast.makeText(this, "opps", Toast.LENGTH_SHORT).show();
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentuid = user.getUid();

        Allquetions = database.getReference("AllQuestions").child(post_key).child("Answer");

        votesref = database.getReference("votes");
        downvotesref = database.getReference("downVotes");

        ansFvrt_ListRef = database.getReference("favoriteList").child(uid);
        fvrtref = database.getReference("AnsFavourites");

        reference = db.collection("user").document(uid);
        reference2 = db.collection("user").document(currentuid);

        member = new AnswerMember();


        tvreply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ReplyActivity.this,AnswerActivity.class);
                intent.putExtra("u",uid);
               // intent.putExtra("q",question);
                intent.putExtra("p",post_key);






                //  intent.putExtra("key",privacy);


                startActivity(intent);

            }
        });





    }

    @Override
    protected void onStart() {
        super.onStart();
       // question user refernce
        reference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().exists()){
                            String url = task.getResult().getString("url");
                            String name = task.getResult().getString("name");
                            Picasso.get().load(url).into(imageViewQue);
                            questiontv.setText(question);
                            nametv.setText(name);
                        }else {
                            Toast.makeText(ReplyActivity.this, "error", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
      // refernce for replying user
        reference2.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().exists()){
                            String url = task.getResult().getString("url");
                            Picasso.get().load(url).into(imageViewUser);

                        }else {
                            Toast.makeText(ReplyActivity.this, "error", Toast.LENGTH_SHORT).show();
                        }

                    }
                });





        FirebaseRecyclerOptions<AnswerMember> options =
                new FirebaseRecyclerOptions.Builder<AnswerMember>()
                        .setQuery(Allquetions,AnswerMember.class)
                        .build();

        FirebaseRecyclerAdapter<AnswerMember,AnsViewholder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<AnswerMember, AnsViewholder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AnsViewholder holder, int position, @NonNull final AnswerMember model) {

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        final String currentUserid = user.getUid();

                        final String postkey = getRef(position).getKey();
                        final String que = question;
                        final String name = getItem(position).getName();
                        final String answer = getItem(position).getAnswer();
                       // final String answerKey = getItem(position).getKey();
                        final String url = getItem(position).getUrl();
                        final  String time = getItem(position).getTime();
                       // final String privacy = getItem(position).getPrivacy();
                        final String userid = uid;

                        holder.setAnswer(getApplication(),model.getName(),model.getAnswer()
                                ,model.getUid(),model.getTime(),model.getUrl(),model.getAnswerImageUrl());




                        holder.downVoteChecker(postkey);
                        holder.ansDownvoteView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
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

                                                downvotechecker = false;
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                        });





                        holder.upvoteChecker(postkey);
                        holder.anUpvoteView.setOnClickListener(new View.OnClickListener() {
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




                        holder.ansShareView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent shareIntent =   new Intent(android.content.Intent.ACTION_SEND);
                                shareIntent.setType("text/plain");
                                shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Insert Subject here");
                                String app_url = " https://play.google.com/store/apps/details?id=my.example.javatpoint";
                                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,app_url);
                                startActivity(Intent.createChooser(shareIntent, "Share via"));
                            }
                        });



                        holder.ansMoreView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                PopupMenu popup = new PopupMenu(ReplyActivity.this, view);
                                MenuInflater inflater = popup.getMenuInflater();
                                inflater.inflate(R.menu.ans_post_more_menu, popup.getMenu());




                                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem item) {
                                        switch (item.getItemId()) {

                                            case R.id.report_ans:
                                            {
                                                final AlertDialog.Builder builder = new AlertDialog.Builder(ReplyActivity.this);


                                                builder.setTitle("Report Answer");

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

                                                            answerReportRef= database.getReference().child("Answer Reports");
                                                            answerReportRef.child(userid).child("report detail").setValue(selectedItem);

                                                            // Display the selected item's text on snack bar

                                                        });

                                                // Set the a;ert dialog positive button
                                                builder.setPositiveButton("Submit", (dialogInterface, i) -> {
                                                    Toasty.success(ReplyActivity.this, "Submitted!", Toast.LENGTH_SHORT, true).show();
                                                });

                                                // Create the alert dialog
                                                AlertDialog dialog = builder.create();

                                                // Finally, display the alert dialog
                                                dialog.show();
                                            }
                                            return true;
                                            case R.id.delete_ans:
                                            {


                                                final AlertDialog.Builder builder = new AlertDialog.Builder(ReplyActivity.this);


                                                builder.setTitle("Delete Answer");
                                                builder.setPositiveButton("Delete", (dialogInterface, i) -> {

                                                    ansRef= database.getReference("AllQuestions").child(post_key).child("Answer").child(postkey);
                                                    ansRef.removeValue();

                                                    Toasty.success(ReplyActivity.this, "Deleted", Toast.LENGTH_SHORT, true).show();

                                                   /* ansRef.addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                            if (snapshot.hasChild(member.getUid())){
                                                                ansRef.child(member.getUid()).removeChildren();
                                                            }



                                                           // Toasty.success(ReplyActivity.this, "Deleted", Toast.LENGTH_SHORT, true).show();

                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                        }
                                                    });*/


                                                    /*DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                                    Query applesQuery = ref.child("AllQuestions").child("Answer").orderByChild("time").equalTo("10-January-2021:10:59:11");*/

/*
                                                    applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                            for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                                                appleSnapshot.getRef().removeValue();
                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(DatabaseError databaseError) {

                                                        }
                                                    });
*/




                                                });






                                                builder.setNegativeButton(
                                                        "Cancel", (dialogInterface, i) -> {
                                                        });
                                                AlertDialog dialog = builder.create();

                                                // Finally, display the alert dialog
                                                dialog.show();
                                            }
                                            return true;

                                            case R.id.bookmark_ans:
                                            {
                                                fvrtChecker = true;

                                                fvrtref.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                        if (fvrtChecker.equals(true)){
                                                            if (snapshot.child(postkey).hasChild(currentUserid)){
                                                                fvrtref.child(postkey).removeValue();
                                                              //  delete(time);
                                                                Toast.makeText(ReplyActivity.this, "Removed from favourite", Toast.LENGTH_SHORT).show();
                                                                fvrtChecker = false;
                                                            }else {


                                                                fvrtref.child(postkey).child(postkey).setValue(true);
                                                                member.setName(name);
                                                                member.setTime(time);
                                                               // member.setPrivacy(privacy);
                                                                member.setUid(userid);
                                                                member.setUrl(url);
                                                                member.setAnswer(answer);

                                                                //  String id = fvrt_listRef.push().getKey();
                                                                ansFvrt_ListRef.child(postkey).setValue(member);
                                                                fvrtChecker = false;

                                                                Toasty.success(ReplyActivity.this, "Added to favourite!", Toast.LENGTH_SHORT, true).show();


                                                            }
                                                        }

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
                                            }

                                            return true;

                                            case R.id.iv_que_item:
                                            {
                                                Intent intent = new Intent(ReplyActivity.this,CreateProfile.class);
                                                startActivity(intent);
                                            }

                                            return true;

                                            case R.id.edit_ans:
                                            {


                                               /* Map<String, Object> result = new HashMap<>();
                                                result.put("uid", member.uid);
                                                result.put("name", name);
                                                result.put("time", time);
                                                result.put("answer", answer);
                                                result.put("url", url);

                                                ansRef= database.getReference("AllQuestions").child(post_key).child("Answer").child(uid);

                                                ansRef.updateChildren(result);*/


                                                ansRef= database.getReference("AllQuestions").child(post_key).child("Answer").child(postkey);
                                                ansRef.child("edited").setValue("false");

                                               /* Button button = findViewById(R.id.btn_answer_submit);
                                                button.setVisibility(View.INVISIBLE);

                                                LinearLayout l_layout;
                                                l_layout = (LinearLayout) findViewById(R.id.edit_ans_linear_layout);
                                                l_layout.setVisibility(View.VISIBLE);*/


                                                Intent intent = new Intent(ReplyActivity.this,EditAnswer.class);
                                                intent.putExtra("u",uid);
                                                intent.putExtra("p",post_key);
                                                intent.putExtra("ans",answer);
                                                intent.putExtra("cd","2");
                                                intent.putExtra("imgUrl",model.getAnswerImageUrl());
                                                intent.putExtra("ansPostKey",postkey);

                                                startActivity(intent);





                                            }
                                            return true;


                                            default:
                                                return false;
                                        }
                                    }
                                });




                                popup.show();
                            }
                        });
                }





                    @NonNull
                    @Override
                    public AnsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.ans_layout,parent,false);

                        return new AnsViewholder(view);



                    }
                };
        firebaseRecyclerAdapter.startListening();

        recyclerView.setAdapter(firebaseRecyclerAdapter);


    }

    void delete(String time){

        Query query = ansFvrt_ListRef.orderByChild("time").equalTo(time);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                    dataSnapshot1.getRef().removeValue();

                    Toast.makeText(ReplyActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



}