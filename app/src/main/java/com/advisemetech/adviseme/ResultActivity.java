package com.advisemetech.adviseme;

import android.content.Intent;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;


public class ResultActivity extends AppCompatActivity {

    private RadarChart mRadarChart;
    private HorizontalBarChart mHorizontalBarChart1;
    private HorizontalBarChart mHorizontalBarChart2;
    private HorizontalBarChart mHorizontalBarChart3;
    private HorizontalBarChart mHorizontalBarChart4;
    private HorizontalBarChart mHorizontalBarChart5;

    private TextView mConscientiousnessTextView;
    private TextView mAgreeablenessTextView;
    private TextView mExtroversionTextView;
    private TextView mOpennessTextView;
    private TextView mNeuroticismTextview;




    Button suggestingAdviceBtn, saveResults, shareResults;

    DatabaseReference databaseReference,saveResultsRef;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String currentUserid = user.getUid();

    String extroversion="", agreeableness="", conscientiousness="", neuroticism="", openness="";
    int extroversionValue, agreeablenessValue, conscientiousnessValue, neuroticismValue, opennessValue;

   /* String [] extroversion= new String[1];
    String [] agreeableness = new String[1];
    String [] conscientiousness= new String[1];
    String [] neuroticism= new String[1];
    String [] openness= new String[1];*/
    PersonalityResultsModel personalityResultsModel;





    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        personalityResultsModel= new PersonalityResultsModel();


        suggestingAdviceBtn = findViewById(R.id.advice_button);
        saveResults = findViewById(R.id.save_button);
        shareResults=findViewById(R.id.share_results_button);



        saveResults.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){


               savePersonalityResults();

            }

        });

        shareResults.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){


                sharePersonalityResults();

            }

        });










        mRadarChart = findViewById(R.id.RADAR_CHART);
        mHorizontalBarChart1 = findViewById(R.id.HORIZONTAL_BAR_CHART_1);
        mHorizontalBarChart2 = findViewById(R.id.HORIZONTAL_BAR_CHART_2);
        mHorizontalBarChart3 = findViewById(R.id.HORIZONTAL_BAR_CHART_3);
        mHorizontalBarChart4 = findViewById(R.id.HORIZONTAL_BAR_CHART_4);
        mHorizontalBarChart5 = findViewById(R.id.HORIZONTAL_BAR_CHART_5);


        setRadarChart(mRadarChart);
        setHorizontalChart(mHorizontalBarChart1, 0);
        setHorizontalChart(mHorizontalBarChart2, 1);
        setHorizontalChart(mHorizontalBarChart3, 2);
        setHorizontalChart(mHorizontalBarChart4,3);
        setHorizontalChart(mHorizontalBarChart5,4);
        setTexTViews();




        suggestingAdviceBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                Answers answers = Answers.getInstance();


                extroversionValue = answers.getAnswers(0);
                agreeablenessValue=answers.getAnswers(1);
                conscientiousnessValue = answers.getAnswers(2);
                neuroticismValue=answers.getAnswers(3);
                opennessValue=answers.getAnswers(4);


                Intent intent = new Intent(getApplicationContext(),CareerAdviceActivity.class);
                intent.putExtra("ex",extroversionValue);
                intent.putExtra("ag",agreeablenessValue);
                intent.putExtra("co",conscientiousnessValue);
                intent.putExtra("ne",neuroticismValue);
                intent.putExtra("op",opennessValue);
                startActivity(intent);



               // Intent intent = new Intent(getApplicationContext(),CareerAdviceActivity.class);
               // intent.putExtra("ex",extroversionValue);
              //  startActivity(intent);



              /*  Intent intent = new Intent(ResultActivity.this,CareerAdviceActivity.class);
                intent.putExtra("ex",extroversionValue);
                 intent.putExtra("ag",agreeablenessValue);
                intent.putExtra("co",conscientiousnessValue);
                intent.putExtra("ne",neuroticismValue);
                intent.putExtra("op",opennessValue);
                startActivity(intent);
*/

                //  intent.putExtra("key",privacy);





              //  CareerAdvice fragment = new CareerAdvice();
              //  FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

               // Bundle args = new Bundle();
               /* args.putString("extroversion",String.valueOf(extroversionValue));
                args.putString("agreeableness",String.valueOf(agreeablenessValue));
                args.putString("conscientiousness",String.valueOf(conscientiousnessValue));
                args.putString("neuroticism",String.valueOf(neuroticismValue));
                args.putString("openness",String.valueOf(opennessValue));*/




                //fragment.setArguments(args);
               // transaction.replace(R.id.frame_layout, fragment);
               // transaction.commit();

/*
                 args.putString("q_Key", model.getKey());
                args.putString("q_uid", userid );
                args.putString("ques", que);
                args.putString("q_time", time);
                args.putString("u_name", name);
                args.putString("current_user", currentUserid);
                args.putString("current_user_name", notificationSndrname);
                args.putString("sender_url", notificationSndrUrl);
*/

                /*




                fragment.setArguments(args);
                transaction.replace(R.id.frame_layout, fragment);
                transaction.commit();*/





            }

        });


    }

    private void sharePersonalityResults() {



        Intent shareIntent =   new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT,"My Personality Trait Assessment Test Results:");
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, extroversion);
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, agreeableness);
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, conscientiousness);
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, neuroticism);
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, openness);
        startActivity(Intent.createChooser(shareIntent, "Share via"));

    }


    private class MyValueFormatter extends ValueFormatter {

        @Override
        public String getBarLabel(BarEntry barEntry) {

            int intEntry = (int) barEntry.getY();
            return (intEntry) + "%";

        }

        @Override
        public String getRadarLabel(RadarEntry radarEntry) {
            int intEntry = (int) radarEntry.getValue();
            return (intEntry) + "%";
        }

        @Override
        public String getAxisLabel(float value, AxisBase axis) {

            int intEntry = (int) value;
            return Integer.toString(intEntry);
        }
    }

    private ArrayList<RadarEntry> mRadarEntries(){

        final Answers FinalAnswer = Answers.getInstance();
        ArrayList<RadarEntry> datavalues = new ArrayList<RadarEntry>();
        datavalues.add(new RadarEntry(FinalAnswer.getAnswers(0)));
        datavalues.add(new RadarEntry(FinalAnswer.getAnswers(1)));
        datavalues.add(new RadarEntry(FinalAnswer.getAnswers(2)));
        datavalues.add(new RadarEntry(FinalAnswer.getAnswers(3)));
        datavalues.add(new RadarEntry(FinalAnswer.getAnswers(4)));
        return datavalues;
    }

    private void setHorizontalChart(HorizontalBarChart mHorizontalBarChart, int trait){

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        Answers answers = Answers.getInstance();
        int entry = answers.getAnswers(trait);
        barEntries.add(new BarEntry(1, entry));

        //COLORS
        int color = getResources().getColor(R.color.colorPrimary);

        //

        //Bar DataSet
        BarDataSet barDataSet;
        barDataSet = new BarDataSet(barEntries, "");
        barDataSet.setValueTextSize(14);
        barDataSet.setColor(color);   //Color of Bar
        barDataSet.setLabel(""); // Turning Unnecessary Label Off
        barDataSet.setFormSize(0); // Getting Rid of Red Square
        barDataSet.setValueFormatter(new MyValueFormatter());

        //BarData Intialization
        BarData barData = new BarData(barDataSet);
        mHorizontalBarChart.setData(barData);

        //AXIS
        XAxis xAxis;
        xAxis = mHorizontalBarChart.getXAxis();
        xAxis.setAxisMaximum(2);
        xAxis.setAxisMinimum(0);
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setTextColor(Color.WHITE); //SMALL TEXT COLOR to the RIGHT
        xAxis.setGridColor(Color.WHITE);

        YAxis yAxis;
        yAxis = mHorizontalBarChart.getAxisRight();
        yAxis.setAxisMaximum(100);
        yAxis.setAxisMinimum(0);

        YAxis yAxis1;
        yAxis1 = mHorizontalBarChart.getAxisLeft();
        yAxis1.setAxisMinimum(0);
        yAxis1.setAxisMaximum(100);
        yAxis1.setGridColor(Color.BLACK); //Horizontal Grid Color


        mHorizontalBarChart.animateXY(6000,6000, Easing.EaseOutSine);
        //Description Settings
        Description description = new Description();;
        description.setEnabled(false);
        mHorizontalBarChart.setDescription(description);

        //DISABLING TOUCH INTERACTIONS!
        mHorizontalBarChart.setClickable(false);
        mHorizontalBarChart.setDragEnabled(false);
        mHorizontalBarChart.setPinchZoom(false);
        mHorizontalBarChart.setHighlightPerTapEnabled(false);
        mHorizontalBarChart.setHighlightFullBarEnabled(false);
        mHorizontalBarChart.setHighlightPerDragEnabled(false);
        mHorizontalBarChart.setAutoScaleMinMaxEnabled(false);
        mHorizontalBarChart.setDrawGridBackground(false);
        mHorizontalBarChart.setDuplicateParentStateEnabled(false);
        mHorizontalBarChart.setTouchEnabled(false);
    }

    private void setRadarChart(RadarChart mRadarChart){

        String[] labels = {"Extroversion","Agreeableness", "Conscientiousness", "Neuroticism", "Openness"};
        RadarDataSet radarDataSet = new RadarDataSet(mRadarEntries(),"Personality");


        //COLORS
        int color = getResources().getColor(R.color.colorPrimaryDark);

        //GETTING RID OF STUFF
        radarDataSet.setLabel(""); //UNNECESSARY LABEL
        radarDataSet.setColor(Color.CYAN);// Color of LINES
        radarDataSet.setLineWidth(20);  //Red width
        radarDataSet.setFormSize(0); // Getting rid of Square

        //Setting the insides of the dataset
        radarDataSet.setFillColor(color);
        radarDataSet.setDrawFilled(true);
        radarDataSet.setLineWidth(2);


        // Set the Width of the Line
        radarDataSet.setValueTextColor(Color.WHITE); //Color of Filling
        radarDataSet.setValueTextSize(15);


        //RADAR DATA
        RadarData radarData = new RadarData();
        radarData.addDataSet(radarDataSet);
        radarData.setValueFormatter(new MyValueFormatter());

        //XAXIS
        XAxis xAxis = mRadarChart.getXAxis();
        xAxis.setAvoidFirstLastClipping(false);
        xAxis.setAxisMaximum(10000);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setTextColor(Color.WHITE);


        //xAxis.setTextColor(Color.WHITE);

        //Turn off Description
        Description description = new Description();
        description.setText(" ");

        //YAXIS
        YAxis yAxis = mRadarChart.getYAxis();
        yAxis.setMaxWidth(100);
        yAxis.setMinWidth(0);
        yAxis.setAxisMinimum(0);
        yAxis.setAxisMaximum(80);
        yAxis.setValueFormatter(new MyValueFormatter());
        yAxis.setTextColor(Color.GRAY);
        yAxis.setTextSize(10);

        //ANIMATIONS
        mRadarChart.animateXY(1200,1100, Easing.EaseOutQuad);
        mRadarChart.setData(radarData);
        mRadarChart.setDescription(description);

        //WEB COLOR
        mRadarChart.setWebLineWidth(4);
        //mRadarChart.setWebColor(Color.WHITE);

        //WEB COLOR INNNER
        mRadarChart.setWebLineWidthInner(4);
        // mRadarChart.setWebColorInner(Color.WHITE);

        mRadarChart.setBackgroundColor(Color.BLACK);

    }

    private void setTexTViews(){
        mAgreeablenessTextView = findViewById(R.id.TEXT_VIEW_AGREEABLENESS_DESCRIPTION);
        mConscientiousnessTextView = findViewById(R.id.TEXT_VIEW_CONSCIENTIOUSNESS_DESCRIPTION);
        mExtroversionTextView = findViewById(R.id.TEXT_VIEW_EXTROVERSION_DESCRIPTION);
        mOpennessTextView = findViewById(R.id.TEXT_VIEW_OPENNESS_DESCRIPTION);
        mNeuroticismTextview = findViewById(R.id.TEXT_VIEW_NEUROTICISM_DESCRIPTION);


        String[] Extroversion = getResources().getStringArray(R.array.ARRAY_EXTROVERSION);
        String[] Agreeableness = getResources().getStringArray(R.array.ARRAY_AGREEABLENESS);
        String[] Conscientiousness = getResources().getStringArray(R.array.ARRAY_CONSCIENTIOUSNESS);
        String[] Neuroticism = getResources().getStringArray(R.array.ARRAY_NEUROTICISM);
        String[] Openness = getResources().getStringArray(R.array.ARRAY_OPENNESS);

      //  String extroversion, agreeableness, conscientiousness, neuroticism, openness;

        extroversion=setDescription(mExtroversionTextView, 0,Extroversion);
        agreeableness=setDescription(mAgreeablenessTextView,1,Agreeableness);
        conscientiousness= setDescription(mConscientiousnessTextView,2,Conscientiousness);
        neuroticism= setDescription(mNeuroticismTextview,3,Neuroticism);
        openness=setDescription(mOpennessTextView,4,Openness);



    }

    private String setDescription(TextView textView, int trait, String[] traitarray){


        String result= "";
       /* String [] result=new String[1];
        result[0]=" ";
        result[1]=" ";*/
        Answers answers = Answers.getInstance();
        int personality_trait = answers.getAnswers(trait);

        if(personality_trait <= 25){
            textView.setText(traitarray[0]);

           // result[0]=String.valueOf(personality_trait).trim();
           // result[1]=traitarray[0];

            result= traitarray[0];


        }
        else if (personality_trait == 50){
            textView.setText(traitarray[4]);

           // result[0]=String.valueOf(personality_trait).trim();
           // result[1]=traitarray[4];

            result= traitarray[4];

        }
        else if(personality_trait > 25 && answers.getAnswers(trait) < 50){
            textView.setText(traitarray[1]);

           // result[0]=String.valueOf(personality_trait).trim();
           // result[1]=traitarray[1];
            result= traitarray[1];

        }
        else if(personality_trait > 50 && answers.getAnswers(trait) <= 75){
            textView.setText(traitarray[2]);

           // result[0]=String.valueOf(personality_trait).trim();
         //   result[1]=traitarray[2];
            result= traitarray[2];

        }
        else if(personality_trait > 75){
            textView.setText(traitarray[3]);

           // result[0]=String.valueOf(personality_trait).trim();
          //  result[1]=traitarray[3];
            result= traitarray[3];

        }

        return result;
    }


    private void savePersonalityResults(){


        String time;
        Calendar cdate = Calendar.getInstance();
        SimpleDateFormat currentdate = new SimpleDateFormat("dd-MMMM-yyyy");
        final  String savedate = currentdate.format(cdate.getTime());
        Calendar ctime = Calendar.getInstance();
        SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss");
        final String savetime = currenttime.format(ctime.getTime());
        time = savedate +":"+ savetime;

        personalityResultsModel.setUid(currentUserid);
        personalityResultsModel.setTime(time);
        personalityResultsModel.setExtroversion(extroversion);
        personalityResultsModel.setNeuroticism(neuroticism);
        personalityResultsModel.setAgreeableness(agreeableness);
        personalityResultsModel.setConscientiousness(conscientiousness);
        personalityResultsModel.setOpenness(openness);

        saveResultsRef= database.getReference().child("All Users").child(currentUserid).child("Personality Test Results");
        String id = saveResultsRef.push().getKey();
        saveResultsRef.child(id).setValue(personalityResultsModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toasty.success(ResultActivity.this, "saved!", Toast.LENGTH_SHORT, true).show();
            }
        });



        //  CareerAdvice fragment = new CareerAdvice();
        //  FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();







    }


}

