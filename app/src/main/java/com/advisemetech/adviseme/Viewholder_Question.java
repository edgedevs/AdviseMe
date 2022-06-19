package com.advisemetech.adviseme;

import android.app.Application;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import es.dmoral.toasty.Toasty;

public class Viewholder_Question extends RecyclerView.ViewHolder implements View.OnClickListener {



    ImageView imageView, followIco, upvoteIco, shareIco,moreIco, downIco,  ProfileImg, RequestImgBtn, notificationImage ;

    boolean requestChkr;

    TextView nameTv,timeTv,ansTv,upvoteTv,votesNoTv, requestBtn,  notifiQuestionTV, notifiTextTv, notificationTv, questiontv, mark;

    int votesCount;
    LinearLayout upvoteBtn, moreBtn, followBtn, followView;
    DatabaseReference reference;
   // FirebaseDatabase database;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    String senderRequestKey;

  //  ImageView imageView;
    TextView time_result,name_result,question_result,deletebtn,replybtn,replybtn1, question_text;
    LinearLayout answer_btn;
    DatabaseReference favouriteref, followersListRef, markAllRef;



    Boolean followChecker = false;

    public Viewholder_Question(@NonNull View itemView) {
        super(itemView);
    }



    public void setuseritem(FragmentActivity activity, String name, String url, String qualification, String verification){




        ImageView imageView = itemView.findViewById(R.id.profile_img_user_item);
        TextView nametv = itemView.findViewById(R.id.name_user_item);
        TextView VerificationTv = itemView.findViewById(R.id.verified_adviser_user_item);
        TextView QualificaionTv = itemView.findViewById(R.id.qualification_user_item);




        requestBtn = itemView.findViewById(R.id.send_request_user_item);


        Picasso.get().load(url).into(imageView);
        nametv.setText(name);
        QualificaionTv.setText(verification);
        // VerificationTv.setText(qualification);

    }



    public void setNotificationItem(FragmentActivity activity,String notificationText, String questionText, String qUserimgUrl)
    {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String currentUserid = user.getUid();

        markAllRef=database.getReference("All Users").child(currentUserid).child("Notifications");

        ImageView imageView = itemView.findViewById(R.id.profile_img_user_notification);
        notificationTv = itemView.findViewById(R.id.notification_text);
        questiontv = itemView.findViewById(R.id.question_textView);





        markAllRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child("notificationCh").getValue().equals("true")){
                    notificationTv.setTextColor(Color.parseColor("#bdbdbd"));

                }else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        /*mark=itemView.findViewById(R.id.mark_all_read);

        if(markChkr.equals(true))
        {
            notificationTv.setTextColor(Color.parseColor("#bdbdbd"));
        }*/
        Picasso.get().load(qUserimgUrl).into(imageView);
        notificationTv.setText(notificationText);
        questiontv.setText(questionText);

    }


   public void setitem(FragmentActivity activity,String name,String url,String userid,String key,String question,String privacy,
                  String time){


        database = FirebaseDatabase.getInstance();
        time_result = itemView.findViewById(R.id.time_que_item_tv);
        name_result = itemView.findViewById(R.id.name_que_item_tv);
        question_result = itemView.findViewById(R.id.que_item_tv);

        imageView = itemView.findViewById(R.id.iv_que_item);
        //replybtn = itemView.findViewById(R.id.reply_item_que);
       answer_btn=  itemView.findViewById(R.id.answer_layout_id);
       moreBtn= itemView.findViewById(R.id.more_layout_id);

       RequestImgBtn= itemView.findViewById(R.id.ques_request_icon_id);

       followView = itemView.findViewById(R.id.follow_item_id);

       followIco = itemView.findViewById(R.id.q_follow_icon_id);


       imageView.setOnClickListener(this);






        Picasso.get().load(url).into(imageView);
        name_result.setText(name);
        question_result.setText(question);
        time_result.setText(time);

   }


    /*public void favouriteChecker(final String postkey) {
        fvrt_btn = itemView.findViewById(R.id.fvrt_f2_item);


        favouriteref = database.getReference("favourites");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = user.getUid();

        favouriteref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child(postkey).hasChild(uid)){
                    fvrt_btn.setImageResource(R.drawable.ic_baseline_turned_in_24);
                }else {
                    fvrt_btn.setImageResource(R.drawable.ic_baseline_turned_in_not_24);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }*/


    public void followerChecker(final String postkey) {

        followChecker = true;


        database = FirebaseDatabase.getInstance();
       // favouriteref = database.getReference("followers");
        followersListRef = database.getReference("AllQuestions").child(postkey).child("QuestionFollwersList");

        database = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = user.getUid();


       //


        followersListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.hasChild(uid)){
                    followIco.setImageResource(R.drawable.follow_icon_blue);
                }else {
                    followIco.setImageResource(R.drawable.ic_follow_icon_grey);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }





    public  void  upvoteChecker(final String postkey){

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("votes");
        upvoteIco= itemView.findViewById(R.id.ques_upvote_icon_id);
        upvoteBtn= itemView.findViewById(R.id.upvote_layout_id);

        votesNoTv = itemView.findViewById(R.id.upvote_counter);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String currentuid = user.getUid();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child(postkey).hasChild(currentuid)){
                   // upvoteTv.setText("VOTED");
                    votesCount = (int)snapshot.child(postkey).getChildrenCount();
                    votesNoTv.setText(Integer.toString(votesCount));
                    upvoteIco.setImageResource(R.drawable.ic_upvote_clicked);
                }else {
                    //upvoteTv.setText("UPVOTE");
                    votesCount = (int)snapshot.child(postkey).getChildrenCount();
                    votesNoTv.setText(Integer.toString(votesCount));
                    upvoteIco.setImageResource(R.drawable.ic_upvote);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


   /* public  void  followChecker(final String postkey){

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("followersCounter");
        followIco= itemView.findViewById(R.id.ques_follow_icon_id);
        followBtn= itemView.findViewById(R.id.follow_layout_id);
        //votesNoTv = itemView.findViewById(R.id.upvote_counter);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String currentuid = user.getUid();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child(postkey).hasChild(currentuid)){
                    // upvoteTv.setText("VOTED");
                  //  votesCount = (int)snapshot.child(postkey).getChildrenCount();
                   // votesNoTv.setText(Integer.toString(votesCount));
                    followIco.setImageResource(R.drawable.follow_icon_blue);
                }else {
                    //upvoteTv.setText("UPVOTE");
                  //  votesCount = (int)snapshot.child(postkey).getChildrenCount();
                 //   votesNoTv.setText(Integer.toString(votesCount));
                    followIco.setImageResource(R.drawable.ic_follow_icon_grey);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }*/








    public void setitemRelated(Application activity, String name, String url, String userid, String key, String question, String privacy,
                               String time){

       TextView timetv = itemView.findViewById(R.id.related_time_que_item_tv);
       ImageView imageView = itemView.findViewById(R.id.related_iv_que_item);
       TextView nametv = itemView.findViewById(R.id.related_name_que_item_tv);
       TextView quetv = itemView.findViewById(R.id.related_que_item_tv);
        replybtn1= itemView.findViewById(R.id.related_reply_item_que);

       Picasso.get().load(url).into(imageView);
       nametv.setText(name);
       timetv.setText(time);
       quetv.setText(question);



    }


   /* public void setuseritem(FragmentActivity activity, String name, String url, String qualification, String verification){


        ImageView imageView = itemView.findViewById(R.id.profile_img_user_item);
        TextView nametv = itemView.findViewById(R.id.name_user_item);
        TextView VerificationTv = itemView.findViewById(R.id.verified_adviser_user_item);
        TextView QualificaionTv = itemView.findViewById(R.id.qualification_user_item);


        Picasso.get().load(url).into(imageView);
        nametv.setText(name);
        QualificaionTv.setText(verification);
       // VerificationTv.setText(qualification);



    }*/


    public void setitemdelete(Application activity, String name, String url, String userid, String key, String question, String privacy,
                               String time){


        TextView timetv = itemView.findViewById(R.id.del_time_que_item_tv);
        ImageView imageView = itemView.findViewById(R.id.delete_iv_que_item);
        TextView nametv = itemView.findViewById(R.id.del_name_que_item_tv);
        TextView quetv = itemView.findViewById(R.id.del_que_item_tv);
         deletebtn= itemView.findViewById(R.id.delete_item_que_tv);

        Picasso.get().load(url).into(imageView);
        nametv.setText(name);
        timetv.setText(time);
        quetv.setText(question);



    }



    @Override
    public void onClick(View v) {

    }
}














