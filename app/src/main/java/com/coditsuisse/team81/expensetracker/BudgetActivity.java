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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BudgetActivity extends Activity {
    private static final int DATE_PICKER_END = 2;
    private static final int DATE_PICKER_START = 1;
    public EditText amt;
    public EditText start;
    public EditText end;
    public int i=1;
    private int year;
    private int month;
    private int day;
    private Button done;
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        setTitle("Budget");
        final SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        amt = (EditText) findViewById(R.id.edt_amt);
        start = (EditText) findViewById(R.id.edt_start);
        end = (EditText) findViewById(R.id.edt_end);
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month =c.get(Calendar.MONTH);
        day = c.get(Calendar.DATE);
        done = (Button) findViewById(R.id.button_done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateofexp = start.getText().toString();
                String dateofexp2 = end.getText().toString();
                Date date1=null,date2=null;
                if(!dateofexp.equals("") || !dateofexp2.equals(""))
                {
                    try {
                        date1 = formatter.parse(dateofexp);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    try {
                        date2 = formatter.parse(dateofexp2);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if(amt.getText().toString().equals("")||amt.getText().toString().equals(".")) {
                    Toast.makeText(BudgetActivity.this, "Invalid Amount", Toast.LENGTH_SHORT).show();
                }
                else {
                    float a = Float.parseFloat(amt.getText().toString());
                    if (dateofexp2.equals("") || dateofexp.equals("") || a == 0.0 ||date1.compareTo(date2)>0 )
                        Toast.makeText(BudgetActivity.this, "Try again", Toast.LENGTH_SHORT).show();
                    else {
                        SharedPreferences sharedPreferences = getSharedPreferences(SharedResources.MY_PREFERENCE, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putFloat(SharedResources.BUDGET, Float.parseFloat(amt.getText().toString()));
                        editor.commit();
                        finish();
                    }
                }
            }
        });
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

    public void onReset(View view)
    {

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

        Calendar c = Calendar.getInstance();
        int m=c.get(Calendar.MONTH);
        int y=c.get(Calendar.YEAR);
        int d=c.get(Calendar.DAY_OF_MONTH);
        String currentDate = (m+1) + "-" + d + "-" + y;
        Date current= null;
        try {
            c.setTime(sdf.parse(currentDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        amt.setText("0.00");
        start.setText(currentDate);
        c.add(Calendar.MONTH, 1);
        m=c.get(Calendar.MONTH);
        y=c.get(Calendar.YEAR);
        d=c.get(Calendar.DAY_OF_MONTH);
        String nextDate = (m+1) + "-" + d + "-" + y;
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

