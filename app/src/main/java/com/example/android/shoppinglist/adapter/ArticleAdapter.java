package com.example.android.shoppinglist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.shoppinglist.R;
import com.example.android.shoppinglist.model.Article;

import java.util.ArrayList;

/**
 * Created by Stevan on 4/22/2017.
 */

public class ArticleAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Article> articles;
    LayoutInflater inflater;


    public ArticleAdapter(Context appContext, ArrayList<Article> list){
        this.context = appContext;
        this.articles = list;
        inflater = LayoutInflater.from(appContext);
    }

    public int getCount() {
        return articles.size();

    }
    @Override
    public Object getItem(int position) {
        return articles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.artice_list_item,null);
        TextView name = (TextView) convertView.findViewById(R.id.tw_articleItem);
        TextView amount = (TextView) convertView.findViewById(R.id.tw_amount);
        name.setText(articles.get(position).getName());
        amount.setText(articles.get(position).getAmount());
        return convertView;
    }


}
