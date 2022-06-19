package com.advisemetech.adviseme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import es.dmoral.toasty.Toasty;

public class PrivacyActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {


    String[] status = {"Choose any one","Public","Private"};

    TextView status_tv;
    Spinner spinner;
    Button button;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        button = findViewById(R.id.btn_privacy);
        status_tv = findViewById(R.id.tv_status);
        spinner = findViewById(R.id.spinner);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentid = user.getUid();
        reference = db.collection("user").document(currentid);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,status);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePrivacy();
            }
        });

    }




    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

        Toast.makeText(this, "Please Select a value", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        reference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                   if (task.getResult().exists()){
                       String privacy_result = task.getResult().getString("privacy");
                       status_tv.setText(privacy_result);

                   }else {
                       Toast.makeText(PrivacyActivity.this, "error", Toast.LENGTH_SHORT).show();
                   }

                    }
                });
    }

    private void savePrivacy() {

        final  String value = spinner.getSelectedItem().toString();
        if (value == "Choose any one"){

            Toasty.info(this, "Please Select a value.", Toast.LENGTH_SHORT, true).show();
        }else {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String currentid = user.getUid();
            final  DocumentReference sDoc = db.collection("user").document(currentid);
            db.runTransaction(new Transaction.Function<Void>() {
                @Override
                public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                    DocumentSnapshot snapshot = transaction.get(sDoc);


                    transaction.update(sDoc, "privacy",value );



                    // Success
                    return null;
                }
            }).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toasty.success(PrivacyActivity.this, "Privacy Updated!", Toast.LENGTH_SHORT, true).show();
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toasty.error(PrivacyActivity.this, "This is an error toast.", Toast.LENGTH_SHORT, true).show();
                        }
                    });

        }

    }
}





