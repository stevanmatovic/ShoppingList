package com.example.android.shoppinglist.model;

import java.util.ArrayList;

/**
 * Created by Stevan on 4/22/2017.
 */

public class ShoppingList {

    private ArrayList<Article> articleList;
    private String name;
    private boolean isCompleted;

    public ShoppingList(ArrayList<Article> articleList, String name) {
        this.articleList = articleList;
        this.name = name;
        this.isCompleted = false;
    }

    public ArrayList<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(ArrayList<Article> articleList) {
        this.articleList = articleList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    @Override
    public String toString() {
        return name;
    }
}
