package com.coditsuisse.team81.expensetracker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

public class DetailsActivity extends Activity {

    private static final int DATE_PICKER_START =1 ;
    public DbHelper db;
	public Intent intent;
	public TextView title;
	public EditText dateofexpense,amt;
	public ToggleButton paid;
	public Button add;
    Calendar c = Calendar.getInstance();
    int day = c.get(Calendar.DAY_OF_MONTH);
    int month = c.get(Calendar.MONTH);
    int year = c.get(Calendar.YEAR);

    @Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        intent = getIntent();
        title = (TextView) findViewById(R.id.heading);
        title.setText(intent.getStringExtra("title"));
        add = (Button) findViewById(R.id.btn_add);
        amt = (EditText) findViewById(R.id.edt_amount);
        paid = (ToggleButton) findViewById(R.id.toggle);
        dateofexpense = (EditText) findViewById(R.id.date);
        db = new DbHelper(this);
        dateofexpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_PICKER_START);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateofexp = dateofexpense.getText().toString();
                int i =0;
                float a =Float.parseFloat(amt.getText().toString());
                if(dateofexp.equals("") || a==0.0)
                    Toast.makeText(DetailsActivity.this, "Try again", Toast.LENGTH_SHORT).show();
                else {
                    if (paid.isChecked())
                        i = 1;
                    i = db.insertExp(dateofexp,a,title.getText().toString(), i);
                    if (i == 1)
                        finish();
                    else
                        Toast.makeText(DetailsActivity.this, "Try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_START:
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
                    dateofexpense.setText(new StringBuilder().append(month + 1)
                            .append("-").append(day).append("-").append(year));

            }
        };
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
