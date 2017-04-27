package com.example.android.shoppinglist.model;

import android.content.Context;
import android.widget.Toast;

import com.example.android.shoppinglist.activity.MainActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Stevan on 4/22/2017.
 */

public class MainList implements Serializable{

    ArrayList<ShoppingList> shoppingLists;

    public MainList() {
        this.shoppingLists = new ArrayList<ShoppingList>();

    }

    public ArrayList<ShoppingList> getShoppingLists() {

        return shoppingLists;
    }

    public void setShoppingLists(ArrayList<ShoppingList> shoppingLists) {
        this.shoppingLists = shoppingLists;
    }

    public void addShoppingList(String name){
        shoppingLists.add(new ShoppingList(new ArrayList<Article>(),name));
    }

    @Override
    public String toString() {
        return "MainList{" +
                "shoppingLists=" + shoppingLists +
                '}';
    }
}
