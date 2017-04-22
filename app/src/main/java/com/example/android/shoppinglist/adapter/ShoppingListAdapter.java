package com.example.android.shoppinglist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.shoppinglist.R;
import com.example.android.shoppinglist.model.ShoppingList;

import java.util.ArrayList;

/**
 * Created by Stevan on 4/22/2017.
 */

public class ShoppingListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ShoppingList> shoppingLists;
    LayoutInflater inflater;


    public ShoppingListAdapter(Context appContext, ArrayList<ShoppingList> list){
        this.context = appContext;
        this.shoppingLists = list;
        inflater = LayoutInflater.from(appContext);
    }

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

        convertView = inflater.inflate(R.layout.shopping_list_item,null);
        TextView name = (TextView) convertView.findViewById(R.id.tw_shoppingListItem);
        name.setText(shoppingLists.get(position).toString());
        return convertView;
    }
}
