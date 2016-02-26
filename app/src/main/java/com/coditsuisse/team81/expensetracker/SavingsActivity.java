package com.coditsuisse.team81.expensetracker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SavingsActivity extends Activity {
    private static final int DATE_PICKER_END = 2;
    private static final int DATE_PICKER_START = 1;
    public EditText amt;
    public EditText start;
    public EditText end;
    private int year;
    private int month;
    private int day;
    public int i=1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);
        setTitle("Budget");
        amt = (EditText) findViewById(R.id.savingsedt_amt);
        start = (EditText) findViewById(R.id.savingsedt_start);
        end = (EditText) findViewById(R.id.savingsedt_end);
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month =c.get(Calendar.MONTH);
        day = c.get(Calendar.DATE);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=1;
                showDialog(DATE_PICKER_START);

            }
        });
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=2;
                showDialog(DATE_PICKER_END);
            }
        });
    }
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_START:
                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                return new DatePickerDialog(this, pickerListener, year, month,day);
            case DATE_PICKER_END:
                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                return new DatePickerDialog(this, pickerListener, year, month,day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener pickerListener;

    {
        pickerListener = new DatePickerDialog.OnDateSetListener() {

            // when dialog box is closed, below method will be called.
            @Override
            public void onDateSet(DatePicker view, int selectedYear,
                                  int selectedMonth, int selectedDay) {

                year = selectedYear;
                month = selectedMonth;
                day = selectedDay;

                // Show selected date
                if(i==1)
                    start.setText(new StringBuilder().append(month + 1)
                            .append("-").append(day).append("-").append(year));
                else
                    end.setText(new StringBuilder().append(month + 1)
                            .append("-").append(day).append("-").append(year));
            }
        };
    }

    public void onDone(View view)
    {
        String dateofexp = start.getText().toString();
        String dateofexp2 = end.getText().toString();
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        Date date1 = null,date2 = null;
        try {
            if(!dateofexp2.equals("") && !dateofexp.equals("")) {
                date1 = formatter.parse(dateofexp);
                date2 = formatter.parse(dateofexp2);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(amt.getText().toString().equals("")||amt.getText().toString().equals(".")) {
            Toast.makeText(SavingsActivity.this, "Invalid Amount", Toast.LENGTH_SHORT).show();
        }
        else {
            float a = Float.parseFloat(amt.getText().toString());

            if (dateofexp2.equals("") || dateofexp.equals("") || a == 0.0 || (date1.compareTo(date2) > 0))
                Toast.makeText(SavingsActivity.this, "Try again", Toast.LENGTH_SHORT).show();
            else {
                SharedPreferences sharedPreferences=getSharedPreferences(SharedResources.MY_PREFERENCE, Context.MODE_PRIVATE);;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putFloat(SharedResources.SAVINGS_GOAL,Float.parseFloat(amt.getText().toString()));
                editor.commit();
                finish();
            }
        }
    }
    public void onReset(View view)
    {

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

        Calendar c = Calendar.getInstance();
        int m=c.get(Calendar.MONTH);
        int y=c.get(Calendar.YEAR);
        int d=c.get(Calendar.DAY_OF_MONTH);
        String currentDate = (m+1) + "/" + d + "/" + y;
        Date current= null;
        try {
            c.setTime(sdf.parse(currentDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        amt.setText("0.00");
        start.setText(currentDate);
        c.add(Calendar.MONTH, 2);
        m=c.get(Calendar.MONTH);
        y=c.get(Calendar.YEAR);
        d=c.get(Calendar.DAY_OF_MONTH);
        String nextDate = m + "/" + d + "/" + y;
        end.setText(nextDate);
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