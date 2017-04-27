package com.example.android.shoppinglist.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.shoppinglist.R;
import com.example.android.shoppinglist.adapter.ArticleAdapter;
import com.example.android.shoppinglist.files.DAO;
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_article);

        final DAO dao = new DAO("data.ser");

        Bundle extras = getIntent().getExtras();
        int i = -1;
        String toolbarText = null;
        if (extras != null) {
            i = (int) extras.get("INDEX");
            toolbarText = (String) extras.get("TOOLBAR");
        }
        if(toolbarText!=null && !toolbarText.equals(""));
            toolbar.setTitle(toolbarText);

        setSupportActionBar(toolbar);

        lw_Articles = (ListView) findViewById(R.id.lw_Articles);
        parent = new MainList();
        parent = dao.readFromFile(this);
        //readFromFile();
        list = parent.getShoppingLists().get(i);
        Toast.makeText(this, i +"  " +parent.getShoppingLists(), Toast.LENGTH_LONG).show();
        lw_Articles = (ListView) findViewById(R.id.lw_Articles);
        final ArticleAdapter adapter = new ArticleAdapter(this,list.getArticleList());
        lw_Articles.setAdapter(adapter);

        lw_Articles.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                createLongClickDialog(position,dao);
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(fab_add_article);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createInsertArticleDialog(dao);
                adapter.notifyDataSetChanged();
            }
        });
    }


    private void createLongClickDialog(final int pos,final DAO dao){
        CharSequence colors[] = new CharSequence[] {"Obriši", "Označi da je kupljeno"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Izaberi opciju");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which==0){  //Obrisi
                    list.getArticleList().remove(pos);
                    //updateFile();
                    dao.updateFile(parent,ArticleActivity.this);

                } else if (which == 1) {
                    list.getArticleList().get(pos).setCompleted(true);
                    list.checkIsCompleted();
                    dao.updateFile(parent,ArticleActivity.this);
                    //updateFile();
                }
            }
        });
        builder.show();
    }


    public void createInsertArticleDialog(final DAO dao){

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText name = new EditText(this);
        name.setHint("Ime artikla");
        layout.addView(name);
        final EditText amount = new EditText(this);
        amount.setHint("Količina");
        layout.addView(amount);

        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setView(layout);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogResultName = name.getText().toString();
                dialogResultAmount = amount.getText().toString();
                if(dialogResultName!=null && !dialogResultName.equals("")){
                    list.addArticle(dialogResultName,dialogResultAmount);
                    list.setCompleted(false);
                    dao.updateFile(parent,ArticleActivity.this);
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

}
