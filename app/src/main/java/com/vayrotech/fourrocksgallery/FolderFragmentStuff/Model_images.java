package com.vayrotech.fourrocksgallery.FolderFragmentStuff;

import java.util.ArrayList;
import java.util.Date;

//images and folder path

public class Model_images {
    String str_folder, str_folderPath, str_title;
    Date str_dateModified;
    ArrayList<String> al_imagepath;

    public String getStr_folder() {
        return str_folder;
    }

    public void setStr_folder(String str_folder) {
        this.str_folder = str_folder;
    }

    public ArrayList<String> getAl_imagepath() {
        return al_imagepath;
    }

    public void setAl_imagepath(ArrayList<String> al_imagepath) {
        this.al_imagepath = al_imagepath;
    }

    public String toString() {
        return str_folder;

    }

    public String getStr_folderPath() {
        return str_folderPath;
    }


    public void setStr_folderPath(String str_folderPath) {
        this.str_folderPath = str_folderPath;
    }


    public String getStr_title() {
        return str_title;
    }

    public void setStr_title(String str_title) {
        this.str_title = str_title;
    }

    public Date getStr_dateModified() {
        return str_dateModified;
    }

    public void setStr_dateModified(Date str_dateModified) {
        this.str_dateModified = str_dateModified;
    }


}