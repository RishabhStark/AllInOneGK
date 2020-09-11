package com.stark.quizzer;

public class CurrentAffairs_Model {
    private String title,body;

    public CurrentAffairs_Model(String title, String body) {
        this.title = title;
        this.body = body;
    } public CurrentAffairs_Model() {
       // firebase contructor
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
