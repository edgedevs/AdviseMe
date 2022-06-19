package com.advisemetech.adviseme;

import android.app.Application;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

public class AnsViewholder extends RecyclerView.ViewHolder {

    ImageView imageView_Ans, upvoteImage, downvoteImage, shareImage, moreImage, ansImg;
    TextView tvNameAns,tvTimeAns,ansTV,ansUpvoteCounter,ansDownvoteCounter;

    LinearLayout anUpvoteView, ansDownvoteView, ansShareView, ansMoreView;
    int votesCount;
    int downvotesCount;
    DatabaseReference reference;
    FirebaseDatabase database;

    Boolean votechecker = false;
    Boolean downVotechecker = false;
    AnswerMember member = new AnswerMember();


    public AnsViewholder(@NonNull View itemView) {
        super(itemView);
    }

    public void  setAnswer(Application application , String name,String answer, String uid, String time, String url, String ansImgUrl){

        imageView_Ans = itemView.findViewById(R.id.imageView_ans);

        upvoteImage = itemView.findViewById(R.id.ans_upvote_ico);
        downvoteImage = itemView.findViewById(R.id.ans_downvote_ico);

        tvNameAns = itemView.findViewById(R.id.tv_name_ans);
        tvTimeAns = itemView.findViewById(R.id.tv_time_ans);
        ansTV = itemView.findViewById(R.id.tv_ans);




        anUpvoteView= itemView.findViewById(R.id.ans_upvote_item);
        ansDownvoteView= itemView.findViewById(R.id.ans_downvote_item);
        ansShareView= itemView.findViewById(R.id.ans_share_item);
        ansMoreView= itemView.findViewById(R.id.ans_more_item);
        ansUpvoteCounter = itemView.findViewById(R.id.ans_upvote_counter);
        ansDownvoteCounter = itemView.findViewById(R.id.ans_downvote_counter);
        ansImg=itemView.findViewById(R.id.image_Reply_item);

        tvNameAns.setText(name);
        tvTimeAns.setText(time);
        ansTV.setText(answer);
        Picasso.get().load(url).into(imageView_Ans);




        int newHeight = 400; // New height in pixels
        int newWidth = 400; // New width in pixels
        ansImg.requestLayout();

        ansImg.getLayoutParams().width = newWidth;
        ansImg.getLayoutParams().height = newHeight;
        ansImg.setScaleType(ImageView.ScaleType.FIT_XY);
        Picasso.get().load(ansImgUrl).into(ansImg);
    }





    public  void  upvoteChecker(final String postkey){
        votechecker = true;
         database = FirebaseDatabase.getInstance();
        reference = database.getReference("votes");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String currentuid = user.getUid();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child(postkey).hasChild(currentuid)){
                    //upvoteTv.setText("VOTED");
                    votesCount = (int)snapshot.child(postkey).getChildrenCount();
                    ansUpvoteCounter.setText(Integer.toString(votesCount));
                    upvoteImage.setImageResource(R.drawable.ans_ic_upvote_clicked);

                }else {
                    //upvoteTv.setText("UPVOTE");
                    votesCount = (int)snapshot.child(postkey).getChildrenCount();
                    ansUpvoteCounter.setText(Integer.toString(votesCount));
                    upvoteImage.setImageResource(R.drawable.ans_ic_upvote);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }




    public  void  downVoteChecker(final String postkey){
        downVotechecker = true;
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("downVotes");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String currentuid = user.getUid();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child(postkey).hasChild(currentuid)){
                    //upvoteTv.setText("VOTED");
                    downvotesCount = (int)snapshot.child(postkey).getChildrenCount();
                    ansDownvoteCounter.setText(Integer.toString(downvotesCount));
                   downvoteImage.setImageResource(R.drawable.ic_downvote_clicked);


                }else {
                    //upvoteTv.setText("UPVOTE");
                    downvotesCount = (int)snapshot.child(postkey).getChildrenCount();
                    ansDownvoteCounter.setText(Integer.toString(downvotesCount));
                  downvoteImage.setImageResource(R.drawable.ans_ic_downvote);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



}
