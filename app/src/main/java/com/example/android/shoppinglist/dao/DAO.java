package com.example.android.shoppinglist.dao;

import android.content.Context;

import com.example.android.shoppinglist.model.Article;
import com.example.android.shoppinglist.model.MainList;
import com.example.android.shoppinglist.model.ShoppingList;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Stevan on 4/27/2017.
 */

public class DAO {

    String file;

    public DAO(String file) {
        this.file = file;
    }

    public void updateFile(MainList list,Context context){
        try {

            Collections.sort(list.getShoppingLists(), new Comparator<ShoppingList>() {
                @Override
                public int compare(ShoppingList o1, ShoppingList o2) {
                    if(o1.isCompleted() == o2.isCompleted())
                        return 0;
                    if(o1.isCompleted()==true)
                        return 1;
                    if(o1.isCompleted()==false)
                        return -1;
                    return 0;
                }
            });

            for(ShoppingList sl: list.getShoppingLists()){
                Collections.sort(sl.getArticleList(), new Comparator<Article>() {
                    @Override
                    public int compare(Article o1, Article o2) {
                        if(o1.isCompleted() == o2.isCompleted())
                            return 0;
                        if(o1.isCompleted()==true)
                            return 1;
                        if(o1.isCompleted()==false)
                            return -1;
                        return 0;
                    }
                });
            }

            FileOutputStream fos = context.openFileOutput("data.ser", context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(list.getShoppingLists());
            oos.close();
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public MainList readFromFile(Context context){
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        MainList list = new MainList();
        try {
            fis = context.openFileInput("data.ser");
            ois = new ObjectInputStream(fis);
            list.setShoppingLists((ArrayList<ShoppingList>)ois.readObject());
            ois.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
