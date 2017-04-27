package com.example.android.shoppinglist.files;

import android.content.Context;

import com.example.android.shoppinglist.model.MainList;
import com.example.android.shoppinglist.model.ShoppingList;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

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
