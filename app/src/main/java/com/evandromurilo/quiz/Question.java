package com.evandromurilo.quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by murilo on 6/9/17.
 */

public class Question {
    static ArrayList<Question> questionList = new ArrayList<>();
    private static Iterator<Question> iterator;

    String question;
    String[] alternatives = new String[3];

    public Question(String question, String altA, String altB, String altC) {
        this.question = question;
        alternatives[0] = altA;
        alternatives[1] = altB;
        alternatives[2] = altC;
    }

    public Question(String question) {
        this.question = question;
        for (int i = 0; i < 3; ++i) alternatives[i] = "";
    }

    public Question() {
        this.question = "";
        for (int i = 0; i < 3; ++i) alternatives[i] = "";
    }

    public String getCorrectAnswer() {
        return alternatives[0];
    }

    public static void generateQuestions() {
        questionList.add(new Question("Com quantos anos Dom Pedro II foi coroado imperador do Brasil?",
                "14 anos",
                "24 anos",
                "18 anos"));
        questionList.add(new Question("O detetive mais famoso do mundo:",
               "Sherlock Holmes",
                "Maigret",
                "Captain D."));
        questionList.add(new Question("Sobrenome do criador deste quiz:",
                "Bronstrup",
                "Broesntrup",
                "Bronstroup"));

        shuffleQuestions();
    }

    public static void shuffleQuestions() {
        Collections.shuffle(questionList);
        iterator = questionList.iterator();
    }

    public static boolean hasNextQuestion() {
        return iterator.hasNext();
    }

    public static Question nextQuestion() {
        return iterator.next();
    }
}
