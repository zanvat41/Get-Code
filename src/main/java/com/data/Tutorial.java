package com.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This is a tutorial object that will be used to transfer data from the database to the servlet and vice-versa.
 * Created by Matthew on 4/4/2017.
 */

public class Tutorial implements Serializable {
    private String id;                                      // the unique id for the tutorial
    private String name;                                    // the name of the tutorial
    private String language;                                   // the language of the tutorial, i.e. Java, Python
    private String creator;                                 // the email address of the user who created the tutorial
    private String classCode;                               // the unique, auto-generated class code for the tutorial
    private String archived;                                // states whether the tutorial has been archived
    private ArrayList<Tutorial_Text> texts;                 // the list of text objects for the tutorial
    private ArrayList<Tutorial_Heading> headings;           // the list of heading objects for the tutorial
    private ArrayList<Tutorial_Picture> pictures;           // the list of picture objects for the tutorial
    private ArrayList<Tutorial_Video> videos;               // the list of video objects for the tutorial
    private ArrayList<Tutorial_Question> questions;         // the list of question objects for the tutorial
    private ArrayList<Tutorial_Code> codes;                 // the list of coding question objects for the tutorial

    // constructors
    public Tutorial() {
        id = "";
        name = "";
        language = "";
        creator = "";
        classCode = "";
        archived = "";
        texts = new ArrayList<Tutorial_Text>();
        headings = new ArrayList<Tutorial_Heading>();
        pictures = new ArrayList<Tutorial_Picture>();
        videos = new ArrayList<Tutorial_Video>();
        questions = new ArrayList<Tutorial_Question>();
        codes = new ArrayList<Tutorial_Code>();
    }

    public Tutorial(String id, String name, String language, String creator, String classCode, String archived) {
        this.id = id;
        this.name = name;
        this.language = language;
        this.creator = creator;
        this.classCode = classCode;
        this.archived = archived;
        texts = new ArrayList<Tutorial_Text>();
        headings = new ArrayList<Tutorial_Heading>();
        pictures = new ArrayList<Tutorial_Picture>();
        videos = new ArrayList<Tutorial_Video>();
        questions = new ArrayList<Tutorial_Question>();
        codes = new ArrayList<Tutorial_Code>();
    }

    // basic accessor/mutator methods
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String lang) {
        language = lang;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getArchived() {
        return archived;
    }

    public void setArchived(String archived) {
        this.archived = archived;
    }

    public ArrayList<Tutorial_Text> getTutorialTexts() {
        return texts;
    }

    public void setTutorialTexts(ArrayList<Tutorial_Text> list) {
        texts = list;
    }

    public ArrayList<Tutorial_Heading> getTutorialHeadings() {
        return headings;
    }

    public void setTutorialHeadings(ArrayList<Tutorial_Heading> list) {
        headings = list;
    }

    public ArrayList<Tutorial_Picture> getTutorialPictures() {
        return pictures;
    }

    public void setTutorialPictures(ArrayList<Tutorial_Picture> list) {
        pictures = list;
    }

    public ArrayList<Tutorial_Video> getTutorialVideos() {
        return videos;
    }

    public void setTutorialVideos(ArrayList<Tutorial_Video> list) {
        videos = list;
    }

    public ArrayList<Tutorial_Question> getTutorialQuestions() {
        return questions;
    }

    public void setTutorialQuestions(ArrayList<Tutorial_Question> list) {
        questions = list;
    }

    public ArrayList<Tutorial_Code> getTutorialCodes() {
        return codes;
    }

    public void setTutorialCodes(ArrayList<Tutorial_Code> list) {
        codes = list;
    }
}