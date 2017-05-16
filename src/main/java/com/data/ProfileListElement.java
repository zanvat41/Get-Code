package com.data;

import java.io.Serializable;

/**
 * The Java object that is created to represent a tutorial and a user's progression
 * Created by tyler on 4/6/2017.
 */
public class ProfileListElement implements Serializable{

    private String tutorialId;              // the tutorial's unique id
    private String tutorialName;            // the name of the tutorial
    private String tutorialLanguage;        // the tutorial's language, i.e. Java, Python
    private String tutorialCreator;         // the email of the user who created the tutorial
    private String tutorialClassCode;       // the unique auto-generated code
    private String userScore;               // the highest score the user has gotten on the tutorial
    private String userAttempts;            // the number of attempts the user has done with the tutorial

    /** Constructor used when a user has not done this tutorial before */
    public ProfileListElement(String id, String name, String language, String creator, String classCode) {
        tutorialId = id;
        tutorialName = name;
        tutorialLanguage = language;
        tutorialCreator = creator;
        tutorialClassCode = classCode;
        userScore = "0";
        userAttempts = "0";
    }

    /** Constructor used when a user has done this tutorial before */
    public ProfileListElement(String id, String name, String language, String creator, String classCode, String score, String attempts) {
        tutorialId = id;
        tutorialName = name;
        tutorialLanguage = language;
        tutorialCreator = creator;
        tutorialClassCode = classCode;
        userScore = score;
        userAttempts = attempts;
    }

    public String getTutorialId() {
        return tutorialId;
    }
    public String getTutorialName() {
        return tutorialName;
    }
    public String getTutorialLanguage() {
        return tutorialLanguage;
    }
    public String getTutorialCreator() {
        return tutorialCreator;
    }
    public String getTutorialClassCode() {
        return tutorialClassCode;
    }
    public String getUserScore() {
        return userScore;
    }
    public String getUserAttempts() {
        return userAttempts;
    }

    public void setTutorialId(String id) {
        tutorialId = id;
    }
    public void setTutorialName(String name) {
        tutorialName = name;
    }
    public void setTutorialLanguage(String language) {
        tutorialLanguage = language;
    }
    public void setTutorialCreator(String creator) {
        tutorialCreator = creator;
    }
    public void setTutorialClassCode(String classCode) {
        tutorialClassCode = classCode;
    }
    public void setUserScore(String score) {
        userScore = score;
    }
    public void setUserAttempts(String attempts) {
        userAttempts = attempts;
    }
}
