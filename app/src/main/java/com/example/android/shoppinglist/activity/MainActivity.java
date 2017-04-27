package com.example.android.shoppinglist.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.shoppinglist.R;
import com.example.android.shoppinglist.adapter.ShoppingListAdapter;
import com.example.android.shoppinglist.files.DAO;
import com.example.android.shoppinglist.model.MainList;
import com.example.android.shoppinglist.model.ShoppingList;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MainList list;
    private ListView lw_ShoppingLists;
    private String dialogResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        list = new MainList();
        final DAO dao = new DAO("data.ser");
        list = dao.readFromFile(this);
      //  readFromFile();

        lw_ShoppingLists = (ListView) findViewById(R.id.lw_ShoppingLists);
        lw_ShoppingLists.setLongClickable(true);
        final ShoppingListAdapter adapter = new ShoppingListAdapter(this,list.getShoppingLists());
        lw_ShoppingLists.setAdapter(adapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_shoppingList);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createInsertDialog(dao);
                adapter.notifyDataSetChanged();
            }
        });

        lw_ShoppingLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ArticleActivity.class);
                intent.putExtra("INDEX", position);
                intent.putExtra("TOOLBAR",list.getShoppingLists().get(position).getName());
                startActivity(intent);
            }
        });
        lw_ShoppingLists.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                // TODO Auto-generated method stub

                createLongClickDialog(pos,dao);
                adapter.notifyDataSetChanged();
                return true;
            }
        });

    }

    public void createInsertDialog(final DAO dao){

        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Naziv nove soping liste:");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogResult = input.getText().toString();
                if(dialogResult!=null && !dialogResult.equals("")){
                    list.addShoppingList(dialogResult);
                    //updateFile();
                    dao.updateFile(list,MainActivity.this);
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


    public void createLongClickDialog(final int pos,final DAO dao){

        CharSequence colors[] = new CharSequence[] {"Obriši", "Otvori"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Izaberi opciju");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which==0){  //Obrisi
                    list.getShoppingLists().remove(pos);
                    //updateFile();
                    dao.updateFile(list,MainActivity.this);

                } else if (which == 1) {
                    Intent intent = new Intent(MainActivity.this, ArticleActivity.class);
                    intent.putExtra("INDEX", pos);
                    startActivity(intent);
                }
            }
        });
        builder.show();
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
