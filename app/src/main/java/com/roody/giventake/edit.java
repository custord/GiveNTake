package com.roody.giventake;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class edit extends AppCompatActivity implements View.OnClickListener{
    private DatabaseHelper myDB;
    Button btnFavor, btnDate, btnCancel, btnSave;
    EditText favor, date, name, description;
    private int mYear, mMonth, mDay, mHour, mMinute;
    int passedID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        myDB = new DatabaseHelper(this);

        Bundle extras = this.getIntent().getExtras();
        if (extras != null && extras.containsKey("PASSED_ID")) {
            passedID = extras.getInt("PASSED_ID");
        }

        btnDate=(Button)findViewById(R.id.btnDate);
        btnFavor=(Button)findViewById(R.id.btnFavor);
        btnCancel=(Button)findViewById(R.id.btnCancel);
        btnSave=(Button)findViewById(R.id.btnSave);

        date=(EditText) findViewById(R.id.date);
        favor=(EditText) findViewById(R.id.favor);
        name=(EditText) findViewById(R.id.name);
        description=(EditText) findViewById(R.id.description);

        btnDate.setOnClickListener(this);
        btnFavor.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        populateList(passedID);
    }

    public void populateList(int id){
        Cursor res = myDB.getData(id);

        while(res.moveToNext()) {
            favor.setText(res.getString(1));
            date.setText(res.getString(2));
            name.setText(res.getString(3));
            description.setText(res.getString(4));
        }
    }

    public void onClick(View v) {

        if (v == btnSave) {

            Log.d("Updating: ", "Updating..");
            try {

                Boolean updated = myDB.updateRecords(String.valueOf(passedID),
                        favor.getText().toString(),
                        date.getText().toString(),
                        name.getText().toString(),
                        description.getText().toString());
                if (updated)
                    Toast.makeText(edit.this, "Favor updated successfully", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(edit.this, "Error: Favor update failed", Toast.LENGTH_SHORT).show();
            } catch(Exception ex) {
                Toast.makeText(edit.this, "Error: Favor update failed", Toast.LENGTH_SHORT).show();
            }

            Intent i = new Intent(edit.this, details.class);
            Bundle b = new Bundle();
            b.putInt("PASSED_ID", passedID);
            i.putExtras(b);
            startActivity(i);
        }

        if (v == btnDate) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

        if (v == btnFavor) {
            AlertDialog.Builder builder = new AlertDialog.Builder(edit.this);
            builder.setMessage("Favor given or taken?")
                    .setPositiveButton("GIVEN", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            favor.setText("GIVEN");

                        }
                    })
                    .setNegativeButton("TAKEN", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            favor.setText("TAKEN");
                        }
                    });

            builder.create();
            builder.show();
        }

        if (v == btnCancel) {
            Intent i = new Intent(edit.this, details.class);
            Bundle b = new Bundle();
            b.putInt("PASSED_ID", passedID);
            i.putExtras(b);
            startActivity(i);
        }
    }
}
