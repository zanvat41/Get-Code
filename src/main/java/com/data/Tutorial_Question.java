package com.data;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * This is an object that stores a tutorial question.
 * Created by Matthew on 4/6/2017.
 */
public class Tutorial_Question implements Serializable {
    private String qid;                     // the question number
    private String question;                // the question itself
    private String quesType;                // the type of question, i.e. multiple choice, coding, short answer
    private ArrayList<String> choices;      // the list of choices if this is a multiple choice question
    private String answer;                  // the answer to the question

    // constructors
    public Tutorial_Question() {
        choices = new ArrayList<String>();
    }
    public Tutorial_Question(String id, String question, String type, ArrayList<String> choices, String answer) {
        qid = id;
        this.question = question;
        quesType = type;
        this.choices = choices;
        this.answer = answer;
    }

    // basic accessor/mutator methods
    public String getQid() { return qid; }
    public void setQid(String number) { qid = number; }
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }
    public String getQuesType() { return quesType; }
    public void setQuesType(String quesType) { this.quesType = quesType; }
    public ArrayList<String> getChoices() { return choices; }
    public void setChoices(ArrayList<String> choices) { this.choices = choices; }
    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }
    public char answerAsLetter() {return (char)(97 + Integer.parseInt(answer)); }
}
