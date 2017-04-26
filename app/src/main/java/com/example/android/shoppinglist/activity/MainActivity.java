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
        readFromFile();
        lw_ShoppingLists = (ListView) findViewById(R.id.lw_ShoppingLists);
        lw_ShoppingLists.setLongClickable(true);
        final ShoppingListAdapter adapter = new ShoppingListAdapter(this,list.getShoppingLists());
        lw_ShoppingLists.setAdapter(adapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_shoppingList);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createInsertDialog();
                adapter.notifyDataSetChanged();
            }
        });

        lw_ShoppingLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ArticleActivity.class);
                intent.putExtra("INDEX", position);
                startActivity(intent);
            }
        });
        lw_ShoppingLists.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                // TODO Auto-generated method stub
                createLongClickDialog(pos);
                adapter.notifyDataSetChanged();

                return true;
            }
        });

    }


    private void readFromFile() {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = openFileInput("data.ser");
            ois = new ObjectInputStream(fis);
            list.setShoppingLists((ArrayList<ShoppingList>)ois.readObject());
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void createInsertDialog(){

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


    public void createLongClickDialog(final int pos){

        CharSequence colors[] = new CharSequence[] {"Obriši", "Otvori"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Izaberi opciju");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which==0){  //Obrisi
                    list.getShoppingLists().remove(pos);
                    updateFile();
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

    public void updateFile(){
        try {

            FileOutputStream fos = openFileOutput("data.ser", Context.MODE_PRIVATE);
            Toast.makeText(this,"upisujem"+list.getShoppingLists().toString(),Toast.LENGTH_LONG).show();
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(list.getShoppingLists());
            oos.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
