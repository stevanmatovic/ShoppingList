package com.example.android.shoppinglist.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Stevan on 4/22/2017.
 */

public class ShoppingList implements Serializable{

    private ArrayList<Article> articleList;
    private String name;
    private boolean isCompleted;
    private boolean isProtected;
    private String password;

    public ShoppingList(ArrayList<Article> articleList, String name) {
        this.articleList = articleList;
        this.name = name;
        this.isCompleted = false;
        this.isProtected = false;
        this.password = "";
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

    public boolean isProtected() {
        return isProtected;
    }

    public void setProtected(boolean aProtected) {
        isProtected = aProtected;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addArticle(String name, String amount){
        if (amount==null){
            amount = "";
        }
        articleList.add(new Article(name,amount));
    }

    public void checkIsCompleted() {
        this.isCompleted = true;
        for(Article a: articleList){
            if (!a.isCompleted()) {
                this.isCompleted = false;
                break;
            }
        }
    }
}
