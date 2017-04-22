package com.example.android.shoppinglist.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.shoppinglist.R;
import com.example.android.shoppinglist.adapter.ShoppingListAdapter;
import com.example.android.shoppinglist.model.MainList;
import com.example.android.shoppinglist.model.ShoppingList;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MainList list;
    private ListView lw_ShoppingLists;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        list = new MainList();
        list.addShoppingList("Pijaca");
        list.addShoppingList("Univer");
        list.addShoppingList("Maxi");
        lw_ShoppingLists = (ListView) findViewById(R.id.lw_ShoppingLists);
        final ShoppingListAdapter adapter = new ShoppingListAdapter(this,list.getShoppingLists());
        lw_ShoppingLists.setAdapter(adapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.addShoppingList("NoviItem");
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
