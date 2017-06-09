package com.evandromurilo.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by murilo on 6/9/17.
 */

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    int currScore = 0;
    int currLives = 3;
    Question currQuestion;

    TextView textQuestion;

    Button buttonAnswerA;
    Button buttonAnswerB;
    Button buttonAnswerC;

    ImageView imageHeart1;
    ImageView imageHeart2;
    ImageView imageHeart3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        textQuestion = (TextView)findViewById(R.id.textQuestion);

        buttonAnswerA = (Button)findViewById(R.id.buttonAnswerA);
        buttonAnswerB = (Button)findViewById(R.id.buttonAnswerB);
        buttonAnswerC = (Button)findViewById(R.id.buttonAnswerC);

        imageHeart1 = (ImageView)findViewById(R.id.imageHeart1);
        imageHeart2 = (ImageView)findViewById(R.id.imageHeart2);
        imageHeart3 = (ImageView)findViewById(R.id.imageHeart3);

        buttonAnswerA.setOnClickListener(this);
        buttonAnswerB.setOnClickListener(this);
        buttonAnswerC.setOnClickListener(this);

        Question.generateQuestions();
        setQuestion();
    }

    protected void setQuestion() {
        if (!Question.hasNextQuestion()) {
            Question.shuffleQuestions();
        }

        currQuestion = Question.nextQuestion();
        textQuestion.setText(currQuestion.question);
        buttonAnswerA.setText(currQuestion.alternatives[0]);
        buttonAnswerB.setText(currQuestion.alternatives[1]);
        buttonAnswerC.setText(currQuestion.alternatives[2]);
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
        }

        if (currLives < 0) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
        else {
            setQuestion();
        }
    }
}
