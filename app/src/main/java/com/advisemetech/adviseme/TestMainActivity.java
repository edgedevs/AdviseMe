package com.advisemetech.adviseme;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;


public class TestMainActivity extends AppCompatActivity {

    private ArrayList<RadioGroup> radioGroups = new ArrayList<>();
    private String [] questionText = new String[30];
    private int[] AnswerArray = new int[20];




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);

        //ADS

        //setAdView();
        LinearLayout mLinearLayout = findViewById(R.id.LinearLayScroll);
        setRadioGroups();
        createLayout(mLinearLayout,AnswerArray);



    }


    public void setRadioGroups() {

        String[] sAnswers = getResources().getStringArray(R.array.AnswerArray);
        String[] sQuestions = getResources().getStringArray(R.array.QuestionArray);

        for (int i = 0; i < sQuestions.length; i++) {

            final RadioGroup mNewQuestion = new RadioGroup(this);
            mNewQuestion.setId(i + 6); //ID is set to (i+6) b/c Listener causes button selection problem if id's of RadioGroup and RadioGButtons are same.
            setQuestionText(i, sQuestions[i]);  //Getting question text from array

            for (int j = 0; j < sAnswers.length; j++) {

                RadioButton button = new RadioButton(this);
                button.setId(j+1);
                button.setText(sAnswers[j]);
                mNewQuestion.addView(button);

            }

            radioGroups.add(mNewQuestion);
        }
    }

    public RadioGroup getRadioGroups(int id){
        return radioGroups.get(id);
    }

    public void setQuestionText(int id, String sQuestions){

        this.questionText[id] = sQuestions;

    }

    public String getQuestionText(int id){
        return questionText[id];
    }

    private void createLayout(final LinearLayout mLinearLayout, final int AnswerArray[] ){


        //restartTestBtn
        final Button restartTestBtn = new Button(this);
        restartTestBtn.setText("Restart Test");
        restartTestBtn.setTextSize(16);
        restartTestBtn.setBackgroundColor(getResources().getColor(R.color.greenPrimary));
        restartTestBtn.setTextColor(Color.WHITE);
        restartTestBtn.setBackground(getResources().getDrawable(R.drawable.rounded_btn));


        //quitTestBtn
        final Button quitTestBtn = new Button(this);
        quitTestBtn.setText("Quit Test");
        quitTestBtn.setTextSize(16);
        quitTestBtn.setBackgroundColor(getResources().getColor(R.color.greenPrimary));
        quitTestBtn.setTextColor(Color.WHITE);
        quitTestBtn.setBackground(getResources().getDrawable(R.drawable.rounded_btn));




        //submitButton
        final Button submitButton = new Button(this);
        submitButton.setText("Submit");
        submitButton.setTextSize(16);

        //Button Parameters
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0,40,40,40);
        submitButton.setBackgroundColor(getResources().getColor(R.color.greenPrimary));
        submitButton.setTextColor(Color.WHITE);
        submitButton.setBackground(getResources().getDrawable(R.drawable.rounded_btn));
        submitButton.setLayoutParams(layoutParams);
        restartTestBtn.setLayoutParams(layoutParams);
        quitTestBtn.setLayoutParams(layoutParams);



        //Answers
        final Answers FinalAnswer = Answers.getInstance(); // Getting the singleton

        //RadioButton Creation
        for (int i =0; i< radioGroups.size(); i++) {

            //Setting Question Text
            TextView questionTextView = new TextView(this);
            questionTextView.setText(i + 1 + " " + getQuestionText(i));
            questionTextView.setTypeface(null, Typeface.BOLD);
            questionTextView.setTextSize(16);

            //RadioGroup Question
            final  RadioGroup question = getRadioGroups(i);
            mLinearLayout.addView(questionTextView);
            mLinearLayout.addView(question);


            //Listener For RadioGroup/Button
            question.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    int GroupID = group.getId();
                    GroupID = GroupID - 6;//Remember !! we added 6 b/c of the listener conflict.
                    AnswerArray[GroupID] = checkedId;

                }
            });

        }

        mLinearLayout.addView(submitButton);
        mLinearLayout.addView(restartTestBtn);
        mLinearLayout.addView(quitTestBtn);


        submitButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){

                int extroversion = 0;
                int agreeableness = 1;
                int conscientousness = 2;
                int neuroticism = 3;
                int openness = 4;

                FinalAnswer.ScoreAnswers(extroversion , AnswerArray);
                FinalAnswer.ScoreAnswers(agreeableness  , AnswerArray);
                FinalAnswer.ScoreAnswers(conscientousness , AnswerArray);
                FinalAnswer.ScoreAnswers(neuroticism , AnswerArray);
                FinalAnswer.ScoreAnswers(openness , AnswerArray);



                Intent intent = new Intent(TestMainActivity.this, ResultActivity.class);
                startActivity(intent);



            }

        });





        quitTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  finish();




                final AlertDialog.Builder builder2 = new AlertDialog.Builder(TestMainActivity.this);
                builder2.setTitle("Quit Test?");
                builder2.setPositiveButton("Quit", (dialogInterface, i) -> {

                    finish();
                });

                builder2.setNegativeButton(
                        "Cancel", (dialogInterface, i) -> {

                        });
                AlertDialog dialog = builder2.create();
                dialog.show();



            }
        });



        restartTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final AlertDialog.Builder builder3 = new AlertDialog.Builder(TestMainActivity.this);
                builder3.setTitle("Restart Test?");
                builder3.setPositiveButton("Restart", (dialogInterface, i) -> {

                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                });

                builder3.setNegativeButton(
                        "Cancel", (dialogInterface, i) -> {

                        });
                AlertDialog dialog = builder3.create();
                dialog.show();



            }
        });




    }






}

