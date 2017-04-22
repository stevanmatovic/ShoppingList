package com.example.android.shoppinglist.model;

/**
 * Created by Stevan on 4/22/2017.
 */

public class Article {

    private String name;
    private String amount;
    private boolean isCompleted;

    public Article(String name, String amount) {
        this.name = name;
        this.amount = amount;
        this.isCompleted = false;
    }

    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
