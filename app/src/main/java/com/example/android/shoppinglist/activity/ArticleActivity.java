package com.example.android.shoppinglist.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.example.android.shoppinglist.R;
import com.example.android.shoppinglist.model.ShoppingList;

//import static com.example.android.shoppinglist.R.id.fab;
import static com.example.android.shoppinglist.R.id.fab_add_article;

public class ArticleActivity extends AppCompatActivity {

    private ShoppingList list;
    private ListView lw_Articles;
    private String dialogResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //list = extras.get("LIST");
            //list = extras.getParcelableArrayList("LIST")
            //The key argument here must match that used in the other activity
        }

        lw_Articles = (ListView) findViewById(R.id.lw_Articles);

        FloatingActionButton fab = (FloatingActionButton) findViewById(fab_add_article);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
