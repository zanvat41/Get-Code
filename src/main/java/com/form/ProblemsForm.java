package com.form;

import com.data.*;

import java.util.ArrayList;

/**
 * Created by Kieran on 4/6/2017.
 */
public class ProblemsForm {
    private int language;
    private ArrayList<Tutorial_Question> mcs;
    private ArrayList<Tutorial_Code> codes;

    public ProblemsForm() {
        language = 0;
        mcs = new ArrayList<Tutorial_Question>();
        codes = new ArrayList<Tutorial_Code>();
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public ArrayList<Tutorial_Question> getMcs() {
        return mcs;
    }

    public void setMcs(ArrayList<Tutorial_Question> mcs) {
        this.mcs = mcs;
    }

    public ArrayList<Tutorial_Code> getCodes() { return codes; }

    public void setCodes(ArrayList<Tutorial_Code> codes) {
        this.codes = codes;
    }

}