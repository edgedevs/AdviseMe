package com.advisemetech.adviseme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class AskActivity extends AppCompatActivity {

    EditText editText, tags;
    Button button;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference AllQuestions,UserQuestions, questionFollowersListRef;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    QuestionMember member;
    String name,url,privacy,uid, postkey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserid = user.getUid();

        editText = findViewById(R.id.ask_et_question);
        tags = findViewById(R.id.tags_et_question);
        button = findViewById(R.id.btn_submit11);
        documentReference = db.collection("user").document(currentUserid);

        AllQuestions = database.getReference("AllQuestions");

        UserQuestions = database.getReference("UserQuestions").child(currentUserid);

        member = new QuestionMember();



        /*Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            uid = bundle.getString("u");
            postkey = bundle.getString("p");
        }else {
            Toast.makeText(this, "Error ", Toast.LENGTH_SHORT).show();
        }*/

        questionFollowersListRef = database.getReference("AllQuestions").child("QuestionFollwersList");



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String question = editText.getText().toString();
                String qTags = tags.getText().toString();

                Calendar cdate = Calendar.getInstance();
                SimpleDateFormat currentdate = new SimpleDateFormat("dd-MMMM-yyyy");
                final  String savedate = currentdate.format(cdate.getTime());

                Calendar ctime = Calendar.getInstance();
                SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss");
                final String savetime = currenttime.format(ctime.getTime());


                String time = savedate +":"+ savetime;


                if (question != null){

                    member.setQuestion(question);
                    member.setName(name);
                    member.setPrivacy(privacy);
                    member.setUrl(url);
                    member.setUserid(uid);
                    member.setTime(time);


                    String id = UserQuestions.push().getKey();
                    UserQuestions.child(id).setValue(member);


                    String child = AllQuestions.push().getKey();
                    member.setKey(id);
                    AllQuestions.child(child).setValue(member);
                    AllQuestions.child(child).child("tags").setValue(qTags);
                   // questionFollowersListRef = database.getReference("AllQuestions").child(currentUserid).child("QuestionFollwersList");


                   // questionFollowersListRef.child(child).setValue(member);


                    //Toast.makeText(AskActivity.this, "Submitted", Toast.LENGTH_SHORT).show();
                    Toasty.success(AskActivity.this, "Submitted!", Toast.LENGTH_SHORT, true).show();
                }else {

                  //  Toast.makeText(AskActivity.this, "Please ask a question", Toast.LENGTH_SHORT).show();
                    Toasty.info(AskActivity.this, "Please ask a question.", Toast.LENGTH_SHORT, true).show();
                }


            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();



        documentReference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.getResult().exists()){
                             name = task.getResult().getString("name");
                             url = task.getResult().getString("url");
                            privacy = task.getResult().getString("privacy");
                            uid = task.getResult().getString("uid");

                        }else {
                           // Toast.makeText(AskActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            Toasty.error(AskActivity.this, "Error", Toast.LENGTH_SHORT, true).show();

                        }

                    }
                });

    }}