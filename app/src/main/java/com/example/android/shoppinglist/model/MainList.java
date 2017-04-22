package com.example.android.shoppinglist.model;

import java.util.ArrayList;

/**
 * Created by Stevan on 4/22/2017.
 */

public class MainList {

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
}
