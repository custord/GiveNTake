package com.roody.giventake;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class list extends AppCompatActivity implements View.OnClickListener {
    private DatabaseHelper myDB;
    Button btnAdd;
    ArrayList<favResults> favorResults = new ArrayList<>();
    MyCustomBaseAdapter adapter;
    ListView favorlist;
    SearchView searchview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        myDB = new DatabaseHelper(this);
        populateList();

        favorlist = (ListView) findViewById(R.id.favorlist);
        adapter = new MyCustomBaseAdapter(this, favorResults);
        favorlist.setAdapter(adapter);

        favorlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
            Object o = favorlist.getItemAtPosition(position);
            int index = favorResults.indexOf(o);
            favResults fr1 = new favResults();
            fr1 = favorResults.get(index);

            //Toast.makeText(list.this, fr1.getName(), Toast.LENGTH_SHORT).show();

            Intent i = new Intent(list.this, details.class);
            Bundle b = new Bundle();
            b.putInt("PASSED_ID", fr1.getId());
            i.putExtras(b);
            startActivity(i);

            }
        });

        btnAdd=(Button)findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

    }

    public void onClick(View v) {

        if (v == btnAdd) {
            Intent intent = new Intent(list.this, insert.class);
            startActivity(intent);
        }
    }

    public void populateList(){
        Cursor res = myDB.getAllData();
        if(res.getCount() == 0) {
            showMessage("Welcome", "Get started by adding a favor!");
            return;
        }

        while(res.moveToNext()) {
            favResults fr = new favResults();
            fr.setId(Integer.parseInt(res.getString(0)));
            fr.setFavor(res.getString(1));
            fr.setDate(res.getString(2));
            fr.setName(res.getString(3));
            favorResults.add(fr);
        }
    }

    private void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.setMessage(message);
        builder.show();
    }
}
