package com.form;
import com.data.*;

import java.util.ArrayList;

/**
 * Created by Kieran on 4/6/2017.
 */
public class TutorialForm {
    private String name;
    private int language;
    private ArrayList<Tutorial_Text> texts;
    private ArrayList<Tutorial_Heading> headings;
    private ArrayList<Tutorial_Picture> pictures;
    private ArrayList<Tutorial_Video> videos;
    private String classCode;

    public TutorialForm() {
        name = "";
        language = 0;
        texts = new ArrayList<Tutorial_Text>();
        headings = new ArrayList<Tutorial_Heading>();
        pictures = new ArrayList<Tutorial_Picture>();
        videos = new ArrayList<Tutorial_Video>();
        classCode = "none";
    }

    public String getName() {
        return name;
    }

    public int getLanguage() {
        return language;
    }

    public ArrayList<Tutorial_Text> getTexts() {
        return texts;
    }

    public ArrayList<Tutorial_Heading> getHeadings() {
        return headings;
    }

    public ArrayList<Tutorial_Picture> getPictures() {
        return pictures;
    }

    public ArrayList<Tutorial_Video> getVideos() {
        return videos;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public void setTexts(ArrayList<Tutorial_Text> texts) {
        this.texts = texts;
    }

    public void setHeadings(ArrayList<Tutorial_Heading> headings) {
        this.headings = headings;
    }

    public void setPictures(ArrayList<Tutorial_Picture> pictures) {
        this.pictures = pictures;
    }

    public void setVideos(ArrayList<Tutorial_Video> videos) {
        this.videos = videos;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }
}