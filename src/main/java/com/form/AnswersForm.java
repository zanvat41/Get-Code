package com.form;

import com.data.Tutorial_Code;
import com.data.Tutorial_Question;

import java.util.ArrayList;

/**
 * Created by Kieran on 4/6/2017.
 */
public class AnswersForm {
    private ArrayList<Integer> answers;
    private ArrayList<Tutorial_Code> codes;

    public AnswersForm() {
        answers = new ArrayList<Integer>();
        codes = new ArrayList<Tutorial_Code>();
    }

    public ArrayList<Integer> getAnswers() { return answers; }

    public void setAnswers(ArrayList<Integer> answers) {
        this.answers = answers;
    }

    public ArrayList<Tutorial_Code> getCodes() { return codes; }

    public void setCodes(ArrayList<Tutorial_Code> codes) {
        this.codes = codes;
    }

}