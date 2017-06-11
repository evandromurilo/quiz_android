package com.evandromurilo.quiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences prefs;
    String dataName = "quizData";
    String scoreName = "highScore";

    TextView textHiScore;
    int hiScore;
    int defaultHiScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences(dataName, MODE_PRIVATE);
        hiScore = prefs.getInt(scoreName, defaultHiScore);

        textHiScore = (TextView)findViewById(R.id.textHiScore);
        textHiScore.setText("High Score: " + hiScore);

        Button buttonPlay = (Button)findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonPlay:
                Intent i = new Intent(this, GameActivity.class);
                startActivity(i);
                break;
        }
    }
}
