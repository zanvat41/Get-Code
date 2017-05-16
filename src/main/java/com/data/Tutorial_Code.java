package com.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This is an object that stores a tutorial question.
 * Created by Kieran on 4/6/2017.
 */
public class Tutorial_Code implements Serializable{
    private String qid;             // the question number
        private String language;        // the programming language (Java, Python)
        private String desc;            // the description of the problem, i.e. what to code
        private String code;            // the source code for the problem
        private ArrayList<String> inputs;
        private ArrayList<String> outputs;

        // constructors
    public Tutorial_Code() {
            inputs = new ArrayList<String>();
            outputs = new ArrayList<String>();
        }
    public Tutorial_Code(String qid, String language, String code, String desc, ArrayList<String> inputs, ArrayList<String> outputs) {
            this.qid = qid;
            this.language = language;
            this.code = code;
            this.desc = desc;
            this.inputs = inputs;
            this.outputs = outputs;
    }

    // basic accessor/mutator methods
    public String getQid() { return qid; }
    public void setQid(String number) { qid = number; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getDesc() { return desc; }
    public void setDesc(String desc) { this.desc = desc; }
    public ArrayList<String> getInputs() { return inputs; }
    public void setInputs(ArrayList<String> inputs) { this.inputs = inputs; }
    public ArrayList<String> getOutputs() { return outputs; }
    public void setOutputs(ArrayList<String> outputs) { this.outputs = outputs; }
}
