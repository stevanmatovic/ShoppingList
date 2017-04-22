package com.example.android.shoppinglist.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.android.shoppinglist.model.ShoppingList;

import java.util.ArrayList;

/**
 * Created by Stevan on 4/22/2017.
 */

public class ShoppingListAdapter extends BaseAdapter {

    private ArrayList<ShoppingList> shoppingLists;
    @Override
    public int getCount() {
        return shoppingLists.size();
    }

    @Override
    public Object getItem(int position) {
        return shoppingLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
