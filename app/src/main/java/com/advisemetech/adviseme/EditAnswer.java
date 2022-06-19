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

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class EditAnswer extends AppCompatActivity  {


    String uid,que,postkey, ansr, code, url2, answPostk;
    EditText editText;
    Button button;
    Uri imageUri, ansimguri;
    ImageView imageView, attachmentButton, ansImgV;
    UploadTask uploadTask;
    StorageReference storageReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    DatabaseReference databaseReference, ansRef;
    private static final int PICK_IMAGE =1;

    AnswerMember member;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference Allquestions, editRef;
    String name,url,time, key, ansImgUrl;
    Boolean editAns;
    String currentUserId;

    LinearLayout l_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_answer);

        member = new AnswerMember();
        editText = findViewById(R.id.answer_et2);
        button = findViewById(R.id.btn_answer_submit2);

        attachmentButton= findViewById(R.id.ans_attachment2);



        imageView = findViewById(R.id.image_Reply2);








        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editAnswer2();
            }
        });


        Bundle bundle = getIntent().getExtras();
        if (bundle != null){

            uid = bundle.getString("u");
            postkey = bundle.getString("p");

            ansr = bundle.getString("ans");
             code = bundle.getString("cd");
            url2 = bundle.getString("imgUrl");
            answPostk= bundle.getString("ansPostKey");

           // editRef= database.getReference("AllQuestions").child(postkey).child("Answer").child(answPostk).child("edited");
            //ansRef.child("edited").setValue("false");

            editText.setText(ansr);

            int newHeight = 300;
            int newWidth = 300;
            imageView.requestLayout();
            imageView.getLayoutParams().width = newWidth;
            imageView.getLayoutParams().height = newHeight;
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            Picasso.get().load(url2).into(imageView);



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


    void editAnswer2(){


        try {

            ansRef= database.getReference("AllQuestions").child(postkey).child("Answer").child(answPostk);
            String answer2 = editText.getText().toString().trim();
            member.setAnswer(answer2);
            ansRef.child("answer").setValue(answer2);


            ansRef= database.getReference("AllQuestions").child(postkey).child("Answer").child(answPostk).child("edited");
            ansRef.setValue("true");
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
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
                                member.setAnswerImageUrl(ansImgUrl);
                                ansRef= database.getReference("AllQuestions").child(postkey).child("Answer").child(answPostk);
                                ansRef.child("answerImageUrl").setValue(ansImgUrl);
                                ansRef= database.getReference("AllQuestions").child(postkey).child("Answer").child(answPostk).child("edited");
                                ansRef.setValue("true");


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
        } catch (Exception e) {
            e.printStackTrace();
        }


        ansRef= database.getReference("AllQuestions").child(postkey).child("Answer").child(answPostk).child("edited");
        //UsersRef = FirebaseDatabase.getInstance().getReference();

        ansRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot snapshot) {
                if (snapshot.getValue().equals("true")) {

                    Toasty.success(EditAnswer.this, "Edited!", Toast.LENGTH_SHORT, true).show();

                    // it exists!
                }else{
                    // does not exist
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }



    @Override
    protected void onStart() {
        super.onStart();
    }
}

