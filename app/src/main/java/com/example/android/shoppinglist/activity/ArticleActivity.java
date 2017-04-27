package com.example.android.shoppinglist.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.shoppinglist.R;
import com.example.android.shoppinglist.adapter.ArticleAdapter;
import com.example.android.shoppinglist.model.MainList;
import com.example.android.shoppinglist.model.ShoppingList;

//import static com.example.android.shoppinglist.R.id.fab;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static com.example.android.shoppinglist.R.id.fab_add_article;

public class ArticleActivity extends AppCompatActivity {

    private ShoppingList list;
    private ListView lw_Articles;
    private String dialogResultName;
    private String dialogResultAmount;
    private MainList parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        int i = -1;
        if (extras != null) {
            i = (int) extras.get("INDEX");
        }
        lw_Articles = (ListView) findViewById(R.id.lw_Articles);
        parent = new MainList();
        readFromFile();
        list = parent.getShoppingLists().get(i);
        Toast.makeText(this, i +"  " +parent.getShoppingLists(), Toast.LENGTH_LONG).show();
        lw_Articles = (ListView) findViewById(R.id.lw_Articles);
        final ArticleAdapter adapter = new ArticleAdapter(this,list.getArticleList());
        lw_Articles.setAdapter(adapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(fab_add_article);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createInsertArticleDialog();
               // adapter.notifyDataSetChanged();
            }
        });
    }

    private void readFromFile() {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = openFileInput("data.ser");
            ois = new ObjectInputStream(fis);
            parent.setShoppingLists((ArrayList<ShoppingList>)ois.readObject());
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void createInsertArticleDialog(){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("jel ovo:");

        final EditText name = new EditText(this);
        name.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        builder.setView(name);

        final EditText amount = new EditText(this);
        amount.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        builder.setView(amount);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogResultName = name.getText().toString();
                dialogResultAmount = amount.getText().toString();
                if(dialogResultName!=null && !dialogResultName.equals("")){
                    list.addArticle(dialogResultName,dialogResultAmount);
                    updateFile();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }


    public void updateFile(){
        try {

            FileOutputStream fos = openFileOutput("data.ser", Context.MODE_PRIVATE);
            Toast.makeText(this,"upisujem"+parent.getShoppingLists().toString(),Toast.LENGTH_LONG).show();
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(parent.getShoppingLists());
            oos.close();
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
