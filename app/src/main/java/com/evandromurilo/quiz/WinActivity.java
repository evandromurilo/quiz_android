package com.evandromurilo.quiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WinActivity extends AppCompatActivity implements View.OnClickListener, MediaPlayer.OnCompletionListener {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    String dataName = "quizData";
    String scoreName = "highScore";

    Button buttonPlayAgain;
    TextView textScore;

    int hiScore;
    int defaultHiScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        Bundle bundle = getIntent().getExtras();

        prefs = getSharedPreferences(dataName, MODE_PRIVATE);
        hiScore = prefs.getInt(scoreName, defaultHiScore);

        buttonPlayAgain = (Button)findViewById(R.id.buttonPlayAgain);
        textScore = (TextView)findViewById(R.id.textScore);

        if (bundle.getInt("score") > hiScore) {
            textScore.setText("New High Score: " + bundle.getInt("score"));
            hiScore = bundle.getInt("score");
            editor = prefs.edit();
            editor.putInt(scoreName, hiScore);
            editor.commit();
        }
        else {
            textScore.setText("Score: " + bundle.getInt("score"));
        }

        buttonPlayAgain.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonPlayAgain:
                Intent intent = new Intent(this, GameActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        mediaPlayer.stop();
        mediaPlayer.release();
    }
}
