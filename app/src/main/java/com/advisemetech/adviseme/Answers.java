package com.advisemetech.adviseme;

public class Answers {


    private int [] AnswerArray = new int[5];
    private static Answers instance;

    public static Answers getInstance(){
        if (instance == null){
            instance = new Answers();
        }
        return instance;
    }

    private Answers(){

    }

    //sets the answer
    private void setAnswer(int trait, int answer){
        AnswerArray[trait] = answer;
    }

    public int getAnswers(int trait) {
        return AnswerArray[trait];
    }

    private int Normalize(int answer){

        int intNormalized = 0;
        double min = 4;
        double max = 20;
        double normalized = ((answer - min)/(max - min)) *100;
        intNormalized = (int) normalized;
        return intNormalized;
    }

    public void ScoreAnswers(int trait, int[] AnswerArray) {

        int answer = 0;

        if(trait != 4){
            for (int i = 0; i <= (AnswerArray.length); i++) {

                if (i == trait) {
                    answer = answer + AnswerArray[i];
                } else if (i == (trait + 5)) {
                    answer = answer + (6 - AnswerArray[i]);
                } else if (i == (trait + 10)) {
                    answer = answer + AnswerArray[i];
                } else if (i == (trait + 15)) {
                    answer = answer + (6 - AnswerArray[i]);

                }
            }
        }
        else if (trait == 4){
            for (int i = 0; i <= (AnswerArray.length); i++) {
                if (i == trait) {
                    answer = answer + AnswerArray[i];
                } else if (i == (trait + 5)) {
                    answer = answer + (6 - AnswerArray[i]);
                } else if (i == (trait + 10)) {
                    answer = answer + (6 - AnswerArray[i]);
                } else if (i == (trait + 15)) {
                    answer = answer + (6 - AnswerArray[i]);
                }
            }
        }

        int normalize = Normalize(answer);
        setAnswer(trait,normalize);

    }





}








