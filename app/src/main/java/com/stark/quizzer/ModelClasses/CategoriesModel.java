package com.stark.quizzer.ModelClasses;


import android.net.Uri;

import java.util.List;

public class CategoriesModel
{
    private String name;
    private List<String> sets;
    private String image;
    private String key;
    public CategoriesModel()
    {
        // Default constructor required for calls to DataSnapshot.getValue(CategoriesModel.class)
    }

    public CategoriesModel(String name, List<String> sets, String image, String key) {
        this.name = name;
        this.sets = sets;
        this.image = image;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSets() {
        return sets;
    }

    public void setSets(List<String> sets) {
        this.sets = sets;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
