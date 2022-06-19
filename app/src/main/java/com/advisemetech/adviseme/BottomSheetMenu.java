package com.advisemetech.adviseme;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class BottomSheetMenu extends BottomSheetDialogFragment {


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference reference ;
    CardView cv_privacy,cv_logout,cv_delete, fbCv, feedBackCv,aboutCv, termsCv, shareAppCV , adviserAccVerification;
    FirebaseAuth mAuth;
    DatabaseReference df;
    DocumentReference reference2;
    DatabaseReference userReportRef;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseUser mCurrentUser;
    String url,name,currentid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = getLayoutInflater().inflate(R.layout.bottom_sheet_menu,null);


       cv_delete = view.findViewById(R.id.cv_delete);
       cv_logout = view.findViewById(R.id.cv_logout);
       cv_privacy = view.findViewById(R.id.cv_privacy);
       shareAppCV=view.findViewById(R.id.share_app_item);
        feedBackCv = view.findViewById(R.id.give_feedback_item);
        aboutCv = view.findViewById(R.id.about_app);
        termsCv = view.findViewById(R.id.privacy_policy_item);
        adviserAccVerification=view.findViewById(R.id.request_adviser_acc_CV);

       mAuth = FirebaseAuth.getInstance();





        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        currentid= user.getUid();

        df = FirebaseDatabase.getInstance().getReference("All Users");
       reference = db.collection("user").document(currentid);
       reference.get()
               .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                       if (task.getResult().exists()){
                           url = task.getResult().getString("url");

                       }else {

                       }
                   }
               });


     //  mCurrentUser = mAuth.getCurrentUser();


        feedBackCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                dialog.setContentView(R.layout.custom_dialog);
                dialog.setCancelable(false);

                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(dialog.getWindow().getAttributes());
                layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

                dialog.findViewById(R.id.custom_dialog_btn_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.findViewById(R.id.custom_dialog_btn_submit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                dialog.getWindow().setAttributes(layoutParams);
            }
        });

        aboutCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                dialog.setContentView(R.layout.about_us_dialog);
                dialog.setCancelable(true);

                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(dialog.getWindow().getAttributes());
                layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

                dialog.show();
                dialog.getWindow().setAttributes(layoutParams);
            }
        });

        shareAppCV.setOnClickListener(new View.OnClickListener() {
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

        termsCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getBaseContext(),
                        AdviseMeTerms.class);

                getActivity().startActivity(intent);
            }
        });

        adviserAccVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userid = user.getUid();


                userReportRef=database.getReference().child("All Users").child("Adviser Verification Requests").child(userid);
                //


                final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
                builder.setTitle("Send Request for Verified Adviser Account?");

                builder.setPositiveButton("Submit", (dialogInterface, i) -> {

                    userReportRef.child("request details").setValue("Please Register my account for verified adviser");
                    Toast.makeText(getContext(), "Request Submitted", Toast.LENGTH_LONG).show();
                    // Toasty.success(getActivity(), "Submitted!", Toast.LENGTH_SHORT, true).show();
                });

                builder.setNegativeButton("Cancel", (dialogInterface, i) -> {


                    Toast.makeText(getContext(), "Canceled", Toast.LENGTH_LONG).show();
                    // Toasty.success(getActivity(), "Submitted!", Toast.LENGTH_SHORT, true).show();
                });


                // Create the alert dialog
                androidx.appcompat.app.AlertDialog dialog = builder.create();

                // Finally, display the alert dialog
                dialog.show();

            }
        });


        /*Intent intent = new Intent(getActivity().getBaseContext(),
                TargetActivity.class);
        intent.putExtra("message", message);
        getActivity().startActivity(intent);
*/

        cv_logout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               logout();
           }
       });
       cv_privacy.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               startActivity(new Intent(getActivity(),PrivacyActivity.class));
           }
       });
       cv_delete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {


               AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
               builder.setTitle("Delete Profile")
                       .setMessage("Are you sure to delete")
                       .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {


                               reference.delete()
                                       .addOnSuccessListener(new OnSuccessListener<Void>() {
                                           @Override
                                           public void onSuccess(Void aVoid) {


                                               Query query = df.orderByChild("uid").equalTo(currentid);
                                               query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                   @Override
                                                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                       for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                                           dataSnapshot.getRef().removeValue();
                                                       }
                                                   }

                                                   @Override
                                                   public void onCancelled(@NonNull DatabaseError error) {

                                                   }
                                               });

                                               StorageReference ref = FirebaseStorage.getInstance().getReferenceFromUrl(url);
                                               ref.delete()
                                                       .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                           @Override
                                                           public void onComplete(@NonNull Task<Void> task) {


                                                               Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                                                           }
                                                       });
                                           }
                                       });


                           }
                       })
                       .setNegativeButton("No", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {

                           }
                       });
               builder.create();
               builder.show();

           }
       });



       return view;
    }

    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Logout")
                .setMessage("Are you sure to logout")
                .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        mAuth.signOut();
                        startActivity(new Intent(getActivity(),LoginActivity.class));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        builder.create();
        builder.show();
    }
}
