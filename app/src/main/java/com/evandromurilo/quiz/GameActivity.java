package com.evandromurilo.quiz;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Random;

/**
 * Created by murilo on 6/9/17.
 */

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    int currScore = 0;
    int currLives = 3;
    Question currQuestion;

    TextView textQuestion;
    TextView textScore;

    Button buttonAnswerA;
    Button buttonAnswerB;
    Button buttonAnswerC;

    ImageView imageHeart1;
    ImageView imageHeart2;
    ImageView imageHeart3;

    SoundPool soundPool;
    int soundAchievement;
    int soundLostItem;
    int soundSuccessful;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        try {
            AssetManager assetManager = getAssets();
            AssetFileDescriptor descriptor;

            descriptor = assetManager.openFd("achievement.mp3");
            soundAchievement = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("lostitem.mp3");
            soundLostItem = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("successful.mp3");
            soundSuccessful = soundPool.load(descriptor, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        textQuestion = (TextView)findViewById(R.id.textQuestion);
        textScore = (TextView)findViewById(R.id.textScore);

        buttonAnswerA = (Button)findViewById(R.id.buttonAnswerA);
        buttonAnswerB = (Button)findViewById(R.id.buttonAnswerB);
        buttonAnswerC = (Button)findViewById(R.id.buttonAnswerC);

        imageHeart1 = (ImageView)findViewById(R.id.imageHeart1);
        imageHeart2 = (ImageView)findViewById(R.id.imageHeart2);
        imageHeart3 = (ImageView)findViewById(R.id.imageHeart3);

        buttonAnswerA.setOnClickListener(this);
        buttonAnswerB.setOnClickListener(this);
        buttonAnswerC.setOnClickListener(this);

        textScore.setText("Score: " + currScore);
        Question.generateQuestions();
        Question.shuffleQuestions();
        setQuestion();
    }

    protected void setQuestion() {
        if (!Question.hasNextQuestion()) {
            Intent intent = new Intent(this, WinActivity.class);
            currScore += currLives*2;
            intent.putExtra("score", currScore);
            soundPool.play(soundAchievement, 1, 1, 0, 0, 1);
            startActivity(intent);
            return;
        }

        currQuestion = Question.nextQuestion();
        textQuestion.setText(currQuestion.question);

        switch (new Random().nextInt(3)) {
            case 0:
                buttonAnswerA.setText(currQuestion.alternatives[0]);
                buttonAnswerB.setText(currQuestion.alternatives[1]);
                buttonAnswerC.setText(currQuestion.alternatives[2]);
                break;
            case 1:
                buttonAnswerA.setText(currQuestion.alternatives[1]);
                buttonAnswerB.setText(currQuestion.alternatives[0]);
                buttonAnswerC.setText(currQuestion.alternatives[2]);
                break;
            case 2:
                buttonAnswerA.setText(currQuestion.alternatives[2]);
                buttonAnswerB.setText(currQuestion.alternatives[1]);
                buttonAnswerC.setText(currQuestion.alternatives[0]);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        String givenAnswer = "";

        switch (view.getId()) {
            case R.id.buttonAnswerA:
                givenAnswer = ""+buttonAnswerA.getText();
                break;
            case R.id.buttonAnswerB:
                givenAnswer = ""+buttonAnswerB.getText();
                break;
            case R.id.buttonAnswerC:
                givenAnswer = ""+buttonAnswerC.getText();
                break;
        }

        if (givenAnswer.equals(currQuestion.getCorrectAnswer())) {
            currScore++;
            textScore.setText("Score: " + currScore);
            soundPool.play(soundSuccessful, 1, 1, 0, 0, 1);
        }
        else {
            currLives--;
            switch (currLives) {
                case 2:
                    imageHeart3.setImageResource(R.mipmap.heart_empty);
                    break;
                case 1:
                    imageHeart2.setImageResource(R.mipmap.heart_empty);
                    break;
                case 0:
                    imageHeart1.setImageResource(R.mipmap.heart_empty);
                    break;
            }
            soundPool.play(soundLostItem, 1, 1, 0, 0, 1);
        }

        if (currLives < 0) {
            Intent intent = new Intent(this, WinActivity.class);
            intent.putExtra("score", currScore);
            startActivity(intent);
        }
        else {
            setQuestion();
        }
    }
}
