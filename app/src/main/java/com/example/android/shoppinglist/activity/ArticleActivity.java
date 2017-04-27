package com.example.android.shoppinglist.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.example.android.shoppinglist.R;
import com.example.android.shoppinglist.adapter.ArticleAdapter;
import com.example.android.shoppinglist.dao.DAO;
import com.example.android.shoppinglist.model.MainList;
import com.example.android.shoppinglist.model.ShoppingList;


import static com.example.android.shoppinglist.R.id.fab_add_article;

public class ArticleActivity extends AppCompatActivity {

    private ShoppingList list;
    private ListView lw_Articles;
    private String dialogResultName;
    private String dialogResultAmount;
    private MainList parent;
    private ArticleAdapter adapter;
    private final DAO dao = new DAO("data.ser");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_article);


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
        list = parent.getShoppingLists().get(i);
        lw_Articles = (ListView) findViewById(R.id.lw_Articles);
        adapter = new ArticleAdapter(this,list.getArticleList());
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
        CharSequence colors[] = new CharSequence[] {"Obriši", "Označi da je kupljeno","Preimenuj","Promeni količinu"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Izaberi opciju");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which==0){  //Obrisi
                    list.getArticleList().remove(pos);
                    dao.updateFile(parent,ArticleActivity.this);
                    adapter.notifyDataSetChanged();

                } else if (which == 1) {        //Oznaci da je kupljeno
                    list.getArticleList().get(pos).setCompleted(true);
                    list.checkIsCompleted();
                    dao.updateFile(parent,ArticleActivity.this);
                    adapter.notifyDataSetChanged();
                }else if(which == 2){
                    dialogPreimenuj(pos);
                }else if(which == 3){
                    dialogPromeniKolicinu(pos);
                }
            }
        });
        builder.show();
    }

    public void dialogPreimenuj(final int pos){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Novi naziv:");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String dialogResult = input.getText().toString();
                if(dialogResult!=null && !dialogResult.equals("")){
                    list.getArticleList().get(pos).setName(dialogResult);
                    dao.updateFile(parent,ArticleActivity.this);
                    adapter.notifyDataSetChanged();
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

    public void dialogPromeniKolicinu(final int pos){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Količina:");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String dialogResult = input.getText().toString();
                if(dialogResult!=null && !dialogResult.equals("")){
                    list.getArticleList().get(pos).setAmount(dialogResult);
                    dao.updateFile(parent,ArticleActivity.this);
                    adapter.notifyDataSetChanged();
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
                    adapter.notifyDataSetChanged();
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
