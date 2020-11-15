package com.example.mcqsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import static java.util.Arrays.asList;

public class MainActivity extends AppCompatActivity {
Button startButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton=findViewById(R.id.startBtn);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] McqsArray = getResources().getStringArray(R.array.Mcqs);
                final ArrayList<String> data = new ArrayList<String>(asList(McqsArray));
                Intent doQuiz = new Intent(MainActivity.this, MCQs.class);
                doQuiz.putStringArrayListExtra("quizData", data);
                startActivity(doQuiz);
            }
        });
    }

}