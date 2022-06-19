package com.advisemetech.adviseme;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class CareerAdviceActivity extends ResultActivity {

    int extroversionValue, agreeablenessValue, conscientiousnessValue, neuroticismValue, opennessValue;
    TextView traitAdviceTitle, traitAdviceDesc, careerDesc, highTraitTitle;
    ImageView tratCareerImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_career_advice);

        traitAdviceTitle= findViewById(R.id.trait_career_title_tv_id);
        traitAdviceDesc= findViewById(R.id.trait_career_desc_tv_id);
        careerDesc= findViewById(R.id.careers_desc);
        tratCareerImage=findViewById(R.id.trait_image_id);
        highTraitTitle =findViewById(R.id.high_careers_title);

        Bundle extra = getIntent().getExtras();
        if (extra != null){
            extroversionValue = extra.getInt("ex");
            agreeablenessValue = extra.getInt("ag");
            conscientiousnessValue = extra.getInt("co");
            neuroticismValue=extra.getInt("ne");
            opennessValue=extra.getInt("op");


            if(extroversionValue>=agreeablenessValue&&extroversionValue>=agreeablenessValue&&extroversionValue>=conscientiousnessValue&&extroversionValue>=neuroticismValue&&extroversionValue>=opennessValue){

                //Toasty.success(CareerAdviceActivity.this,"extroversionValue"+ String.valueOf(extroversionValue), Toast.LENGTH_SHORT, true).show();
                traitAdviceTitle.setText(getString(R.string.extraversion_advice));
                traitAdviceDesc.setText(getString(R.string.extraversion_advice1));
                highTraitTitle.setText(getString(R.string.high_conscientiousness_careers));
                careerDesc.setText(getString(R.string.extraversion_careers));

                tratCareerImage.setImageResource(R.drawable.afb_extraversion);


            }
            else if(agreeablenessValue>=extroversionValue&&agreeablenessValue>=conscientiousnessValue&&agreeablenessValue>=neuroticismValue&&agreeablenessValue>=opennessValue){

               // Toasty.success(CareerAdviceActivity.this, "agreeablenessValue"+String.valueOf(agreeablenessValue), Toast.LENGTH_SHORT, true).show();
                traitAdviceTitle.setText(getString(R.string.agreeableness_advice));
                traitAdviceDesc.setText(getString(R.string.agreeableness_advice1));
                highTraitTitle.setText(getString(R.string.high_agreeableness_careers));
                careerDesc.setText(getString(R.string.agreeableness_careers));
                tratCareerImage.setImageResource(R.drawable.afb_agreeableness);


            }
            else if(conscientiousnessValue>=extroversionValue&&conscientiousnessValue>=agreeablenessValue&&conscientiousnessValue>=neuroticismValue&&conscientiousnessValue>=opennessValue){

              //  Toasty.success(CareerAdviceActivity.this, "conscientiousnessValue"+String.valueOf(conscientiousnessValue), Toast.LENGTH_SHORT, true).show();
                traitAdviceTitle.setText(getString(R.string.conscientiousness_advice));
                traitAdviceDesc.setText(getString(R.string.conscientiousness_advice1));
                careerDesc.setText(getString(R.string.conscientiousness_careers));
                highTraitTitle.setText(getString(R.string.high_conscientiousness_careers));
                tratCareerImage.setImageResource(R.drawable.afb_conscientiousness);
            }else if(neuroticismValue>=extroversionValue&&neuroticismValue>=agreeablenessValue&&neuroticismValue>=conscientiousnessValue&&neuroticismValue>=opennessValue){

               // Toasty.success(CareerAdviceActivity.this, "neuroticismValue"+String.valueOf(neuroticismValue), Toast.LENGTH_SHORT, true).show();
                traitAdviceTitle.setText(getString(R.string.neuroticism_advice));
                traitAdviceDesc.setText(getString(R.string.neuroticism_advic1));
                highTraitTitle.setText(getString(R.string.high_neuroticism_careers));
                careerDesc.setText(getString(R.string.neuroticism_careers));
                tratCareerImage.setImageResource(R.drawable.afb_neuroticism);
            }
            else{
               // Toasty.success(CareerAdviceActivity.this,"opennessValue"+ String.valueOf(opennessValue), Toast.LENGTH_SHORT, true).show();
                traitAdviceTitle.setText(getString(R.string.openness_advice));
                traitAdviceDesc.setText(getString(R.string.openness_advice1));
                highTraitTitle.setText(getString(R.string.high_openness_careers));
                careerDesc.setText(getString(R.string.openness_careers));
                tratCareerImage.setImageResource(R.drawable.afb_openness);

            }




            // key = extra.getString("key");
        }else {
            Toast.makeText(this, "opps", Toast.LENGTH_SHORT).show();
        }



    }


 /*   public class JavaExample{

        public static void main(String[] args) {

            int num1 = 10, num2 = 20, num3 = 7;

            if( num1 >= num2 && num1 >= num3)
                System.out.println(num1+" is the largest Number");

            else if (num2 >= num1 && num2 >= num3)
                System.out.println(num2+" is the largest Number");

            else
                System.out.println(num3+" is the largest Number");
        }
    }
*/
}
