package com.advisemetech.adviseme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class AnswerActivity extends AppCompatActivity  {


    String uid,que,postkey, ansr, code, url2, answPostk;
    EditText editText;
    Button button;
    Uri imageUri, ansimguri;
    ImageView imageView, attachmentButton, ansImgV;
    UploadTask uploadTask;
    StorageReference storageReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    DatabaseReference databaseReference, ansRef, databaseReference3;
    private static final int PICK_IMAGE =1;

    AnswerMember answerMember;
    NotificationMember notiMember;
    All_UserMmber allUserMmber;

    List<NotificationMember> notMembrList;
    QuestionMember questionMember;
    String posi;

    private List<String> notificationList;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference Allquestions, editRef, notificationsAnsRequestRef, notificationsAnsRequestRef2;
    String name,url,time, key, ansImgUrl, notisendrUid, notisendrUidNew,  notisendrUidNewOne;
     Boolean editAns;
    String currentUserId,   notificationText;

   LinearLayout l_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        answerMember = new AnswerMember();
        allUserMmber= new All_UserMmber();
        questionMember= new QuestionMember();

        notificationList = new ArrayList<String>();

        notiMember= new NotificationMember();
        editText = findViewById(R.id.answer_et);
        button = findViewById(R.id.btn_answer_submit);

        attachmentButton= findViewById(R.id.ans_attachment);



        imageView = findViewById(R.id.image_Reply3);







        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               saveAnswer();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){

            uid = bundle.getString("u");
            postkey = bundle.getString("p");






/*
            ansr = bundle.getString("ans");
           // code = bundle.getString("cd");
            url2 = bundle.getString("imgUrl");
            answPostk= bundle.getString("ansPostKey");*/

           //ansRef= database.getReference("AllQuestions").child(postkey).child("Answer").child(answPostk).child("edited");
            //ansRef.child("edited").setValue("false");

/*

            if(ansRef.child("edited").equals("true"))
            {

                editText.setText(ansr);

                int newHeight = 300;
                int newWidth = 300;
                imageView.requestLayout();
                imageView.getLayoutParams().width = newWidth;
                imageView.getLayoutParams().height = newHeight;
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                l_layout.setVisibility(View.VISIBLE);

                Picasso.get().load(url2).into(imageView);

               // button.setVisibility(View.INVISIBLE);
            }
*/


            //editRef= database.getReference("AllQuestions").child(ansPostKey).child("Answer").child(postkey);










        }else {
            Toast.makeText(this, "Error ", Toast.LENGTH_SHORT).show();
        }

        Allquestions = database.getReference("AllQuestions").child(postkey).child("Answer");



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        currentUserId = user.getUid();
        documentReference = db.collection("user").document(currentUserId);
        storageReference = FirebaseStorage.getInstance().getReference("Post images");
        //databaseReference = database.getReference("All Users");
       // button.setOnClickListener(view -> uploadData());

        attachmentButton.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent,PICK_IMAGE);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try{
            if (requestCode == PICK_IMAGE || resultCode == RESULT_OK ||
                    data != null || data.getData() != null) {
                imageUri = data.getData();


                int newHeight = 300; // New height in pixels
                int newWidth = 300; // New width in pixels
                imageView.requestLayout();

                imageView.getLayoutParams().width = newWidth;
                imageView.getLayoutParams().height = newHeight;
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                Picasso.get().load(imageUri).into(imageView);
            }

        }catch (Exception e){
            Toast.makeText(this, "Error",Toast.LENGTH_SHORT);

        }


    }


    private String getFileExt(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType((contentResolver.getType(uri)));
    }


    void editAnswer(){






            String answer2 = editText.getText().toString().trim();




        if(imageUri !=null){
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()+"."+getFileExt(imageUri));
            fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //  Toast.makeText(ChallengeUserInputDetails.this,"Upload Succesfull",Toast.LENGTH_LONG).show();

                    // Upload upload = new Upload(mName.getText().toString().trim(),taskSnapshot.getDownloadUrl().toString());
                    // String uploadId = mDatabaseReference.push().getKey();
                    // mDatabaseReference.child(uploadId).setValue(upload);


                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {


                            ansImgUrl = uri.toString();
                            answerMember.setAnswer(answer2);
                            answerMember.setAnswerImageUrl(ansImgUrl);

                            ansRef= database.getReference("AllQuestions").child(postkey).child("Answer").child(answPostk);
                            ansRef.child("answer").setValue(answer2);
                            ansRef.child("answerImageUrl").setValue(ansImgUrl);

                            Toasty.success(AnswerActivity.this, "Edited!", Toast.LENGTH_SHORT, true).show();

                            ansRef= database.getReference("AllQuestions").child(postkey).child("Answer").child(answPostk).child("edited");
                             ansRef.child("edited").setValue("false");

                            button = findViewById(R.id.btn_answer_submit);
                            button.setVisibility(View.VISIBLE);
                            LinearLayout l_layout= (LinearLayout) findViewById(R.id.edit_ans_linear_layout);
                            l_layout.setVisibility(View.INVISIBLE);


                            return;

                        }
                    });







                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        }else{
            Toast.makeText(this,"No File Added",Toast.LENGTH_LONG).show();
        }


    }



    void saveAnswer(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();
        String key = user.getUid();
        String answer = editText.getText().toString();


        Calendar cdate = Calendar.getInstance();
        SimpleDateFormat currentdate = new SimpleDateFormat("dd-MMMM-yyyy");
        final  String savedate = currentdate.format(cdate.getTime());

        Calendar ctime = Calendar.getInstance();
        SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss");
        final String savetime = currenttime.format(ctime.getTime());

        time = savedate +":"+ savetime;


            /*final StorageReference reference = storageReference.child(System.currentTimeMillis()+ "."+getFileExt(imageUri));
            uploadTask = reference.putFile(imageUri);
*/


        if(imageUri !=null){
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()+"."+getFileExt(imageUri));
            fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //  Toast.makeText(ChallengeUserInputDetails.this,"Upload Succesfull",Toast.LENGTH_LONG).show();

                    // Upload upload = new Upload(mName.getText().toString().trim(),taskSnapshot.getDownloadUrl().toString());
                    // String uploadId = mDatabaseReference.push().getKey();
                    // mDatabaseReference.child(uploadId).setValue(upload);


                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {




                            ansImgUrl = uri.toString();
                            /*answerMember.setAnswer(answer);
                            answerMember.setTime(time);
                            answerMember.setName(name);
                            answerMember.setUid(userid);*/

                            answerMember.setAnswerImageUrl(ansImgUrl);
                           /* String id = Allquestions.push().getKey();
                            Allquestions.child(id).setValue(answerMember);
                            ansRef= database.getReference("AllQuestions").child(postkey).child("Answer").child(id);
                            ansRef.child("edited").setValue("false");*/




/*

                            Query query = notificationsAnsRequestRef.orderByChild("time").equalTo(time);
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
*/
                           // DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();



                            //String ansRequestKey = notificationsAnsRequestRef2.push().getKey();








                            /*DatabaseReference reference = FirebaseDatabase.getInstance().getReference("AllQuestions");
                            reference.addValueEventListener( new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    questionMember=snapshot.getValue(QuestionMember.class);
                                    notisendrUidNew = questionMember.getUserid();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            } );
*/

                           // DatabaseReference notificationsAnsRequestRef6=database.getReference("");
                           // notificationText= name+" give answer for your request";
/*
                            FirebaseDatabase.getInstance().getReference().child("AllQuestions").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {



                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });*/





/*

                            notiMember= notMembrList.get(Integer.parseInt(posi));

                            notisendrUidNewOne= notiMember.getRequestKey();
                            DatabaseReference notificationsAnsRequestRef4=database.getReference("All Users/"+answerMember.getUid()+"/Notifications/");


                          //database.getReference("All Users").child(userKey).child("Notifications").child("Request Notifications");

                            //DatabaseReference notificationsAnsRequestRef4=database.getReference("All Users/"+answerMember.getUid()+"/Notifications/Answer Notifications");

                            notificationText= name+" answered on your"+notisendrUidNewOne;
                            notificationsAnsRequestRef4.child("hellonew").setValue(notificationText);



*/



                            //DatabaseReference notificationsAnsRequestRef3=database.getReference("All Users").child(notiMember.getRequestKey()).child("Notifications").child("Answer Notifications");

                           // Query queryChkr = database.getReference("All Users").child("0inLu6blWiaAlVlLXCSmTuQWHVQ2").child("Notifications").child("Request Notifications").child("-MRnDMNDt6pK_hOzY1y-");

                            //snapshot.child(userid).child("Notifications").child("Request Notifications").child() child("requestKey").getValue()==notiMember.getRequestKey()

/*
                            notificationsAnsRequestRef3.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    if (snapshot.exists()) {


                                        String NotiSendrKey= snapshot.child("senderKey").getValue().toString().trim();

                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        String userid = user.getUid();



                                       // DatabaseReference notificationsAnsRequestRef=database.getReference("All Users").child(userid).child("Notifications").child("Answer Notifications");

                                        // notificationText= name+" answered on your requested question.";
                                        //notificationsAnsRequestRef =database.getReference("All Users").child(userid).child("Notifications").child("Answer Notifications");
                                        notificationsAnsRequestRef4.child("receiverNotification").setValue(notificationText);



                                        // notificationsAnsRequestRef2.child("answer user name");


                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
*/










                            // saveProfile();
                        }
                    });







                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        }else{
            //Toast.makeText(this,"No File Added",Toast.LENGTH_LONG).show();
        }


        answerMember.setAnswer(answer);
        answerMember.setTime(time);
        answerMember.setName(name);
        answerMember.setUrl(url);
        answerMember.setUid(userid);
        String id = Allquestions.push().getKey();
        Allquestions.child(id).setValue(answerMember);
        ansRef= database.getReference("AllQuestions").child(postkey).child("Answer").child(id);
        ansRef.child("edited").setValue("false").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toasty.success(AnswerActivity.this, "Submitted!", Toast.LENGTH_SHORT, true).show();

            }
        });












      /*

        if(code.equals("2"))
        {
            String answer2 = editText.getText().toString().trim();

            if (code.equals("2")){


                if(imageUri !=null){
                    final StorageReference fileReference = storageReference.child(System.currentTimeMillis()+"."+getFileExt(imageUri));
                    fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //  Toast.makeText(ChallengeUserInputDetails.this,"Upload Succesfull",Toast.LENGTH_LONG).show();

                            // Upload upload = new Upload(mName.getText().toString().trim(),taskSnapshot.getDownloadUrl().toString());
                            // String uploadId = mDatabaseReference.push().getKey();
                            // mDatabaseReference.child(uploadId).setValue(upload);


                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {


                                    ansImgUrl = uri.toString();
                                    member.setAnswer(answer2);
                                    member.setAnswerImageUrl(ansImgUrl);

                                    ansRef= database.getReference("AllQuestions").child(postkey).child("Answer").child(answPostk);
                                    ansRef.child("answer").setValue(answer2);
                                    ansRef.child("answerImageUrl").setValue(ansImgUrl);

                                    Toasty.success(AnswerActivity.this, "Edited!", Toast.LENGTH_SHORT, true).show();
                                    code=null;
                                    return;



                                }
                            });







                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

                }else{
                    Toast.makeText(this,"No File Added",Toast.LENGTH_LONG).show();
                }




            }
        }else{


            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String userid = user.getUid();
            String key = user.getUid();
            String answer = editText.getText().toString();


            Calendar cdate = Calendar.getInstance();
            SimpleDateFormat currentdate = new SimpleDateFormat("dd-MMMM-yyyy");
            final  String savedate = currentdate.format(cdate.getTime());

            Calendar ctime = Calendar.getInstance();
            SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss");
            final String savetime = currenttime.format(ctime.getTime());

            time = savedate +":"+ savetime;


            final StorageReference reference = storageReference.child(System.currentTimeMillis()+ "."+getFileExt(imageUri));
            uploadTask = reference.putFile(imageUri);



            if(imageUri !=null){
                final StorageReference fileReference = storageReference.child(System.currentTimeMillis()+"."+getFileExt(imageUri));
                fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //  Toast.makeText(ChallengeUserInputDetails.this,"Upload Succesfull",Toast.LENGTH_LONG).show();

                        // Upload upload = new Upload(mName.getText().toString().trim(),taskSnapshot.getDownloadUrl().toString());
                        // String uploadId = mDatabaseReference.push().getKey();
                        // mDatabaseReference.child(uploadId).setValue(upload);


                        fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                ansImgUrl = uri.toString();
                                member.setAnswer(answer);
                                member.setTime(time);
                                member.setName(name);
                                member.setUid(userid);
                                member.setUrl(url);
                                member.setAnswerImageUrl(ansImgUrl);
                                String id = Allquestions.push().getKey();
                                Allquestions.child(id).setValue(member);



                                // saveProfile();
                            }
                        });







                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

            }else{
                Toast.makeText(this,"No File Added",Toast.LENGTH_LONG).show();
            }




            Toasty.success(this, "Submitted!", Toast.LENGTH_SHORT, true).show();




        }*/





    }

    @Override
    protected void onStart() {
        super.onStart();





        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();
        FirebaseFirestore d = FirebaseFirestore.getInstance();
        DocumentReference reference;
        reference = d.collection("user").document(userid);





        reference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().exists()){
                             url = task.getResult().getString("url");
                             name = task.getResult().getString("name");

                        }else {
                           // Toast.makeText(AnswerActivity.this, "error", Toast.LENGTH_SHORT).show();
                            Toasty.error(AnswerActivity.this, "Error", Toast.LENGTH_SHORT, true).show();
                        }

                    }
                });




        /*FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference3=database.getReference("AllQuestions");

        // Query phoneQuery = ref.orderByChild(postkey).equalTo(postkey);
        databaseReference3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    NotificationMember notiMember =singleSnapshot.getValue(NotificationMember.class);

                    notMembrList.add(notiMember);

                }


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/





    }
}

