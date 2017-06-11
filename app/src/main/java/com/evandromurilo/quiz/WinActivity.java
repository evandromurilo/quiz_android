package com.evandromurilo.quiz;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WinActivity extends AppCompatActivity implements View.OnClickListener, MediaPlayer.OnCompletionListener {
    Button buttonPlayAgain;
    TextView textScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        Bundle bundle = getIntent().getExtras();

        buttonPlayAgain = (Button)findViewById(R.id.buttonPlayAgain);
        textScore = (TextView)findViewById(R.id.textScore);

        textScore.setText("Score: " + bundle.getInt("score"));

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
