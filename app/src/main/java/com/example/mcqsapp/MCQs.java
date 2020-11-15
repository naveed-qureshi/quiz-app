package com.example.mcqsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;

public class MCQs extends AppCompatActivity {

    private static final long START_TIME_IN_MILLIS = 10000;
    private TextView mTextViewCountDown;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
     int random;

    private ArrayList<Question> mcqs;
    private int result,mcqNum;
    ArrayList<String> data;
    private View v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcqs);

        mTextViewCountDown = findViewById(R.id.timeTextView);
        startTimer();
        mcqNum = 0;
        result = 0;
        data = getIntent().getStringArrayListExtra("quizData");
        mcqs = arraylistToMCQ(data);
        Collections.shuffle(mcqs);
        showMCQ( mcqs , mcqNum);
    }
    private ArrayList<Question> arraylistToMCQ(ArrayList<String> data){
        ArrayList<Question> mcqs = new ArrayList<Question>();
        for(int i = 0; i<data.size(); i+=6)
            mcqs.add(new Question(data, i, i+5));
        return mcqs;
    }
    public void getNextMCQ(View view){
        v=view;
        resetTimer();

        startTimer();

        RadioButton[] opt = new RadioButton[4];
        opt[0] = findViewById(R.id.rb1);
        opt[1] = findViewById(R.id.rb2);
        opt[2] = findViewById(R.id.rb3);
        opt[3] = findViewById(R.id.rb4);
        for(int j = 0; j<4; j++)
            if(opt[j].isChecked()){
                if(opt[j].getText().equals(mcqs.get(mcqNum).getAnswer()))
                    result++;
                RadioGroup rdGrp = (RadioGroup) findViewById(R.id.rdGrp);
                rdGrp.clearCheck();
            }
        if(mcqNum == 9) {
            Intent seeResult = new Intent(this, Result.class);
            seeResult.putExtra("result",Integer.toString(result));
            seeResult.putStringArrayListExtra("data", data);
            seeResult.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(seeResult);
        }
        else {
            mcqNum++;
            showMCQ(mcqs, mcqNum);
        }
    }
    private void showMCQ(ArrayList<Question> mcqs , int i){
        random = new Random().nextInt(100)+4;
        TextView question = findViewById(R.id.questionView);
        TextView questionCount=findViewById(R.id.questionCount);
        questionCount.setText("Question "+(i+1)+" of 10");
        question.setText(mcqs.get(i).getQuestion());
        RadioButton[] opt = new RadioButton[4];
        opt[0] = findViewById(R.id.rb1);
        opt[1] = findViewById(R.id.rb2);
        opt[2] = findViewById(R.id.rb3);
        opt[3] = findViewById(R.id.rb4);
        for(int j = 0; j<4; j++)
        {
            opt[j].setText(mcqs.get(i).getOption(random%4));
            random++;
        }
        if(i == 9) {
            Button submit = findViewById(R.id.nxtBtn);
            submit.setText("Finish");
        }
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                mTimerRunning = false;

            }
        }.start();
        mTimerRunning = true;

    }
    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;

    }
    private void resetTimer() {
        mCountDownTimer.cancel();
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();

    }
    private void updateCountDownText() {
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = "Time left: "+seconds+" sec";
        mTextViewCountDown.setText(timeLeftFormatted);
        if(seconds==0 && mcqNum<9)
        {
            getNextMCQ(v);

        }

    }
}
