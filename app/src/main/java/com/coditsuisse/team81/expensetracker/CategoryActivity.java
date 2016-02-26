package com.coditsuisse.team81.expensetracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoryActivity extends Activity {

    private DbHelper db;
    private ArrayList<Category> al;
    private ListView listView;
    private MyAdapter myAdapter;
    private String date, cat;
    private int paid;
    private float amt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_cat);
        db = new DbHelper(this);
        Intent i = getIntent();
        cat = i.getStringExtra("category");
        al = db.searchByCategory(cat);
        myAdapter = new MyAdapter();
        listView = (ListView) findViewById(R.id.lv_lv_cat);
        listView.setAdapter(myAdapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                date = ((TextView) findViewById(R.id.cat_date)).getText().toString();
                amt = Float.parseFloat(((TextView) findViewById(R.id.cat_amt)).getText().toString());
                paid = Integer.parseInt(((TextView) findViewById(R.id.cat_paid)).getText().toString());
                db.deleteDb(date, amt, cat, paid);
                al.remove(position);
                myAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    class ViewHolder
    {
        TextView dateTxtView,amtTxtView,paidTxtView;
    }
    class MyAdapter extends BaseAdapter
    {
        @Override
        public int getCount() {
            return al.size();
        }

        @Override
        public Object getItem(int position) {
            return al.get(position);
        }

        @Override
        public long getItemId(int position) {
            return(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView == null)
            {
                holder = new ViewHolder();
                LayoutInflater li = getLayoutInflater(); //to convert xml to java object
                convertView = li.inflate(R.layout.listitem_category, null);
                holder.dateTxtView = (TextView) convertView.findViewById(R.id.cat_date);
                holder.amtTxtView = (TextView) convertView.findViewById(R.id.cat_amt);
                holder.paidTxtView = (TextView) convertView.findViewById(R.id.cat_paid);
                //associate the holder object with the convert view
                convertView.setTag(holder);
            }
            else
            {
                //convert view is not null (got from the recycler)
                //get the holder object from the reusable convert view
                holder = (ViewHolder) convertView.getTag();
            }
            Category a = (Category) getItem(position);
            holder.dateTxtView.setText(a.getDate());
            holder.amtTxtView.setText(Float.toString(a.getAmt()));
            holder.paidTxtView.setText(Integer.toString(a.getPaid()));
            return convertView;
        }
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
