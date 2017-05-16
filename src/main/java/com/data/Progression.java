package com.data;

/**
 * This is a progression object that will be used to transfer data from the database to the servlet and vice-versa.
 * Created by Matthew on 4/4/2017.
 */
public class Progression {
    private String email;           // the user's unique email address
    private String tutId;           // the unique id of the tutorial
    private String classCode;       // the auto-generated tutorial class code
    private String score;           // the highest score the user has gotten with this tutorial
    private String attempts;        // the number of attempts the user has done with the tutorial

    // constructors
    public Progression() {}
    public Progression(String email, String tutId, String code, String score, String att) {
        this.email = email;
        this.tutId = tutId;
        classCode = code;
        this.score = score;
        attempts = att;
    }

    // basic accessor/mutator methods
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTutorialId() { return tutId; }
    public void setTutorialId(String id) { tutId = id; }
    public String getClassCode() { return classCode; }
    public void setClassCode(String code) { this.classCode = code; }
    public String getScore() { return score; }
    public void setScore(String score) { this.score = score; }
    public String getAttempts() { return attempts; }
    public void setAttempts(String attempts) { this.attempts = attempts; }
}
