package com.vayrotech.fourrocksgallery.OldStuff_WillDeleteSoon;

import java.util.Date;

public class Cell implements Comparable< Cell >{
    private String title, path, classification;
    private Date date;

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getPath() {

        return path;
    }

    public void setPath(String path) {

        this.path = path;
    }

    public Date getDate() {

        return date;
    }

    public void setDate(Date date) {

        this.date = date;
    }


    public void setClassification(String Classification){
        this.classification = classification;

    }

    public String getClassification(){
        return classification;
    }


    public String toString(){
    return title + " | " + date + " | " + path;

}

//start programming sorting shit
    public int compareTo(Cell c){
        return this.getDate().compareTo(c.getDate());

    }


}
