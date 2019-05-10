package com.akozlev.oscilearn;

public class Lesson {


    private int lessonNum;
    private String title;
    private String desc;
    private boolean completed;
    private String patchName;

    public Lesson(String title, String desc, boolean completed, String patchName){
        this.title = title;
        this.desc = desc;
        this.completed = completed;
        this.patchName = patchName;
    }

    public String getPatchName() {
        return patchName;
    }

    public void setPatchName(String patchName) {
        this.patchName = patchName;
    }

    public int getLessonNum() {
        return lessonNum;
    }

    public void setLessonNum(int lessonNum) {
        this.lessonNum = lessonNum;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
