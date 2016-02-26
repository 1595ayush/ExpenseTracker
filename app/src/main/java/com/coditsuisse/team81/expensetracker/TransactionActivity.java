package com.coditsuisse.team81.expensetracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

import java.util.ArrayList;

public class TransactionActivity extends Activity{

    private ListView listView;
    ArrayList <String> arrayList;
    private ArrayAdapter adapter;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        arrayList = new ArrayList<>();
        arrayList.add("ALL");
        arrayList.add("GENERAL");
        arrayList.add("CLOTHES");
        arrayList.add("FUEL");
        arrayList.add("GIFTS");
        arrayList.add("SHOPPING");
        arrayList.add("ENTERTAINMENT");
        arrayList.add("FOOD");
        arrayList.add("TRAVEL");
        arrayList.add("HOLIDAYS");
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        listView = (ListView) findViewById(R.id.trans_lv);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                String temp = (String) listView.getItemAtPosition(arg2);
                if(temp.equals("ALL"))
                {
                    Intent intent = new Intent(TransactionActivity.this,ListActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(TransactionActivity.this,CategoryActivity.class);
                    intent.putExtra("category",temp);
                    startActivity(intent);
                }
            }
        });
        listView.setAdapter(adapter);
    }
}
