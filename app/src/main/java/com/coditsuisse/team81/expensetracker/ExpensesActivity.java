package com.coditsuisse.team81.expensetracker;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
public class
		ExpensesActivity extends ActionBarActivity {

	public ArrayList<String> arrayList;
	public ArrayAdapter<String> adapter;
	public ListView listView;
	public Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expenses);
        setTitle("Category");
		listView = (ListView) findViewById(R.id.lv);
		arrayList = new ArrayList<>();
		//ADD CATEGORIES
		arrayList.add("GENERAL");
        arrayList.add("HEALTH");
		arrayList.add("CLOTHES");
		arrayList.add("FUEL");
		arrayList.add("ENTERTAINMENT");
		arrayList.add("GIFTS");
		arrayList.add("SHOPPING");
		arrayList.add("EDUCATION");
		arrayList.add("FOOD");
		arrayList.add("TRAVEL");
        arrayList.add("HOLIDAYS");
		arrayList.add("INVESTMENT");
		adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);

		intent = new Intent(this,DetailsActivity.class);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String temp = (String) listView.getItemAtPosition(arg2);
				intent.putExtra("title", temp);
				startActivity(intent);
			}
		});
		listView.setAdapter(adapter);
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.expenses, menu);
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
