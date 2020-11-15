package com.example.mcqsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Result extends AppCompatActivity {
TextView status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        TextView result = findViewById(R.id.resultView);
        status=findViewById(R.id.status);
        int res=Integer.parseInt(getIntent().getStringExtra("result"));
        if(res>=5)
            status.setText("Congratulations! You passed.");
        else
            status.setText("Sorry you fail...Try again.");
        result.setText("Score: "+res+"/10");
    }
    public void restartQuiz(View view){
        Intent restartQuiz = new Intent(this, MainActivity.class);
        restartQuiz.putStringArrayListExtra("data", getIntent().getStringArrayListExtra("data"));
        startActivity(restartQuiz);
    }
    public void exitApp(View view){
        finish();
        System.exit(0);
    }
}