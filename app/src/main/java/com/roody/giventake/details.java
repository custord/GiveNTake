package com.roody.giventake;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class details extends AppCompatActivity implements View.OnClickListener {
    int passedID;
    private DatabaseHelper myDB;
    Button btnBack, btnEdit, btnDelete;
    TextView textFavor, textDate, textName, textDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        myDB = new DatabaseHelper(this);


        Bundle extras = this.getIntent().getExtras();
        if (extras != null && extras.containsKey("PASSED_ID")) {
            passedID = extras.getInt("PASSED_ID");
        }

        textFavor=(TextView)findViewById(R.id.textFavor);
        textDate=(TextView)findViewById(R.id.textDate);
        textName=(TextView)findViewById(R.id.textName);
        textDescription=(TextView)findViewById(R.id.textDescription);

        btnBack=(Button)findViewById(R.id.btnBack);
        btnEdit=(Button)findViewById(R.id.btnEdit);
        btnDelete=(Button)findViewById(R.id.btnDelete);

        btnBack.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);


        populateList(passedID);

    }

    public void onClick(View v) {

        if (v == btnBack) {
            Intent intent = new Intent(details.this, list.class);
            startActivity(intent);
        }

        if (v == btnEdit) {
            Intent i = new Intent(details.this, edit.class);
            Bundle b = new Bundle();
            b.putInt("PASSED_ID", passedID);
            i.putExtras(b);
            startActivity(i);
        }

        if (v == btnDelete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(details.this);
            builder.setMessage("Are you sure you want to delete this favor?")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Log.d("Deleting: ", "Deleting..");

                            int deletedRows = myDB.deleteData(String.valueOf(passedID));

                            if(deletedRows > 0)
                                Toast.makeText(details.this, "Favor deleted successfully", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(details.this, "Error: Data deletion failed", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(details.this, list.class);
                            startActivity(intent);

                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });

            builder.create();
            builder.show();

        }
    }

    public void populateList(int id){
        Cursor res = myDB.getData(id);

        while(res.moveToNext()) {
            textFavor.setText(res.getString(1));
            textDate.setText(res.getString(2));
            textName.setText(res.getString(3));
            textDescription.setText(res.getString(4));
        }
    }
}
