package com.advisemetech.adviseme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateProfile extends AppCompatActivity {

    EditText etname,etBio,etProfession,etEmail,etWeb;
    Button button;
    TextView  catButton;
    ImageView imageView, attachmentButton;
    ProgressBar progressBar;
    Uri imageUri;
    UploadTask uploadTask;
    StorageReference storageReference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    AwesomeValidation awesomeValidation;
    private static final int PICK_IMAGE =1;
    All_UserMmber member;
    String currentUserId, ct, str, imgUrl;


    private List<String> categories=new ArrayList<>();



    boolean[] booleans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);


        ct="";
        categories.add(ct);

        booleans = new boolean[]{
                false, false, false

        };

        member = new All_UserMmber();
        imageView = findViewById(R.id.iv_cp);

        etBio = findViewById(R.id.et_bio_cp);

        etEmail = findViewById(R.id.et_email_cp);
        etname = findViewById(R.id.et_name_cp);
        etProfession = findViewById(R.id.et_profession_cp);
        etWeb = findViewById(R.id.et_web_cp);
        button = findViewById(R.id.btn_cp);
        catButton= findViewById(R.id.catBtn);

        progressBar = findViewById(R.id.progressbar_cp);

        awesomeValidation.addValidation(CreateProfile.this, R.id.et_email_cp, Patterns.EMAIL_ADDRESS, R.string.emailerror);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        currentUserId = user.getUid();


        documentReference = db.collection("user").document(currentUserId);
        storageReference = FirebaseStorage.getInstance().getReference("Profile images");
        databaseReference = database.getReference("All Users");
        button.setOnClickListener(view -> uploadData());
        updateDatabase();

        imageView.setOnClickListener(view -> {
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

    private void uploadData() {

        /*if(imageUri==null){

            *//*Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/adviseme-3a26c.appspot.com/o/profile_place_holder.png?alt=media&token=52132737-c3e7-435f-80d3-69de7a2bd071");
            //Log.d("URI created: " + uri);
            imageUri=uri;
            //ImageU

           // imageUri=*//*




        }*/

        if(imageUri !=null){
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()+"."+getFileExt(imageUri));
            fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {



                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {




                            //= uri.toString();

                           // answerMember.setUrl(url);
                           // answerMember.setAnswerImageUrl(ansImgUrl);*/


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






        //Uri uri = Uri.parse("android.resource://your.package.here/drawable/R.drawable.no_image_available");         imageview.setImageURI(uri);
        final String name = etname.getText().toString();
        final String bio = etBio.getText().toString();
        final String web = etWeb.getText().toString();
        final String prof = etProfession.getText().toString();
        final String email = etEmail.getText().toString();

        if (!TextUtils.isEmpty(name) || !TextUtils.isEmpty(bio)  || !TextUtils.isEmpty(web)  || !TextUtils.isEmpty(prof)
                || !TextUtils.isEmpty(email) )
        {

            Map<String,String > profile = new HashMap<>();
            profile.put("name",name);
            profile.put("prof",prof);

          //  profile.put("url",imageUri.toString());
            profile.put("email",email);
            profile.put("web",web);
            profile.put("bio",bio);
            profile.put("uid",currentUserId);
            profile.put("privacy","Public");
            member.setName(name);
            member.setProf(prof);
            member.setUid(currentUserId);


            if(imageUri !=null){
                final StorageReference fileReference = storageReference.child(System.currentTimeMillis()+"."+getFileExt(imageUri));
                fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {



                        fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                profile.put("url",uri.toString());
                                imgUrl=uri.toString();
                                 member.setUrl(imgUrl.toString());


                                //= uri.toString();

                                // answerMember.setUrl(url);
                                // answerMember.setAnswerImageUrl(ansImgUrl);*/


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


            if(imgUrl==null)
            {
                profile.put("url","https://firebasestorage.googleapis.com/v0/b/adviseme-3a26c.appspot.com/o/profile_place_holder.png?alt=media&token=52132737-c3e7-435f-80d3-69de7a2bd071");
                imgUrl="https://firebasestorage.googleapis.com/v0/b/adviseme-3a26c.appspot.com/o/profile_place_holder.png?alt=media&token=52132737-c3e7-435f-80d3-69de7a2bd071";
                member.setUrl(imgUrl);

            }

            databaseReference.child(currentUserId).setValue(member);

            databaseReference.child(currentUserId).child("intrests").setValue(str);


            documentReference.set(profile)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(CreateProfile.this, "Profile Created", Toast.LENGTH_SHORT).show();


                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(CreateProfile.this,Fragment2.class);
                                    startActivity(intent);
                                }
                            }, 3000);
                        }
                    });

        }else{

            Toast.makeText(this, "Please fill all Fields", Toast.LENGTH_SHORT).show();
        }


        /*final String name = etname.getText().toString();

        final String bio = etBio.getText().toString();
        final String web = etWeb.getText().toString();
        final String prof = etProfession.getText().toString();
        final String email = etEmail.getText().toString();

        if (!TextUtils.isEmpty(name) || !TextUtils.isEmpty(bio)  || !TextUtils.isEmpty(web)  || !TextUtils.isEmpty(prof)
                || !TextUtils.isEmpty(email) || imageUri != null ){





            progressBar.setVisibility(View.VISIBLE);
            final StorageReference reference = storageReference.child(System.currentTimeMillis()+ "."+getFileExt(imageUri));
            Toast.makeText(CreateProfile.this, reference.toString(), Toast.LENGTH_SHORT).show();
            uploadTask = reference.putFile(imageUri);

            Task<Uri> urlTask = uploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()){
                    throw  task.getException();
                }

                return reference.getDownloadUrl();
            }).addOnCompleteListener(task -> {

                if (task.isSuccessful()){


                    Uri downloadUri = task.getResult();

                    Map<String,String > profile = new HashMap<>();
                    profile.put("name",name);
                    profile.put("prof",prof);
                    profile.put("url",downloadUri.toString());
                    profile.put("email",email);
                    profile.put("web",web);
                    profile.put("bio",bio);
                    profile.put("uid",currentUserId);
                    profile.put("privacy","Public");

                    member.setName(name);
                    member.setProf(prof);
                    member.setUid(currentUserId);
                    member.setUrl(downloadUri.toString());

                    databaseReference.child(currentUserId).setValue(member);

                    databaseReference.child(currentUserId).child("intrests").setValue(str);





                    documentReference.set(profile)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(CreateProfile.this, "Profile Created", Toast.LENGTH_SHORT).show();

*//*
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(CreateProfile.this,Fragment1.class);
                                            startActivity(intent);
                                        }
                                    },2000);

 *//*

                                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(CreateProfile.this,Fragment2.class);
                                            startActivity(intent);
                                        }
                                    }, 3000);
                                }
                            });
                }

            });

        }else {
            Toast.makeText(this, "Please fill all Fields", Toast.LENGTH_SHORT).show();
        }*/
    }



    void updateDatabase(){

        catButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Build an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateProfile.this);

                // String array for alert dialog multi choice items
                String[] colors = new String[]{
                        "Matric",
                        "Intermediate Part 1",
                        "Intermediate Part 2",
                        "Medical",
                        "Engineering",
                        "Law",
                        "Commerce",
                        "E-CAT",
                        "M-CAT",
                        "Career Guidence",
                        "Bachelor",
                        "Master"
                };

                // Boolean array for initial selected items
                final boolean[] checkedIntrests = new boolean[]{
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        true,
                        false,
                        false

                };


                final List<String> categoryList = Arrays.asList(colors);

                builder.setMultiChoiceItems(colors, checkedIntrests, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                        // Update the current focused item's checked status
                        checkedIntrests[which] = isChecked;

                        // Get the current focused item
                        String currentItem = categoryList.get(which);

                    }
                });

                // Specify the dialog is not cancelable
                builder.setCancelable(false);

                // Set a title for alert dialog
                builder.setTitle("Select your intrests");

                // Set the positive/yes button click listener
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        for (int i = 0; i<checkedIntrests.length; i++){
                            boolean checked = checkedIntrests[i];
                            if (checked) {

                                categories.add(categoryList.get(i));
                            }
                        }


                        StringBuilder strbul=new StringBuilder();
                        for(String str : categories)
                        {
                            strbul.append(str);
                            //for adding comma between elements
                            strbul.append(",");
                        }
                        //just for removing last comma
                        //strbul.setLength(strbul.length()-1);
                        str=strbul.toString();





                    }
                });

                // Set the negative/no button click listener
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when click the negative button
                    }
                });

                // Set the neutral/cancel button click listener
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when click the neutral button
                    }
                });

                AlertDialog dialog = builder.create();
                // Display the alert dialog on interface
                dialog.show();
            }
        });


    }


    public static boolean isEmptyString(String text) {
        return (text == null || text.trim().equals("null") || text.trim()
                .length() <= 0);
    }
}