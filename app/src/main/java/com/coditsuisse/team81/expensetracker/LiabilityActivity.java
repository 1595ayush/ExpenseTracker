package com.coditsuisse.team81.expensetracker;

import android.app.Activity;
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


public class LiabilityActivity extends Activity {

    private DbHelper db;
    private ArrayList<Liability> al;
    private ListView listView;
    private MyAdapter myAdapter;
    private String date,type;
    private int paid;
    private float amt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liability);

        db = new DbHelper(this);
        al = db.getLiability();
        myAdapter = new MyAdapter();
        listView = (ListView) findViewById(R.id.lia_lv);
        listView.setAdapter(myAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                date = ((TextView)findViewById(R.id.lia_date)).getText().toString();
                amt = Float.parseFloat(((TextView) findViewById(R.id.lia_amt)).getText().toString());
                type = ((TextView)findViewById(R.id.lia_type)).getText().toString();
                paid = 0;
                db.deleteDb(date, amt, type, paid);
                db.insertExp(date,amt,type, 1);
                al.remove(position);
                myAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        al = db.getLiability();
        myAdapter.notifyDataSetChanged();

    }

    class ViewHolder
    {
        TextView dateTxtView,typeTxtView,amtTxtView;
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
                convertView = li.inflate(R.layout.listitem_liability, null);
                holder.dateTxtView = (TextView) convertView.findViewById(R.id.lia_date);
                holder.typeTxtView = (TextView) convertView.findViewById(R.id.lia_type);
                holder.amtTxtView = (TextView) convertView.findViewById(R.id.lia_amt);
                //associate the holder object with the convert view
                convertView.setTag(holder);
            }
            else
            {
                //convert view is not null (got from the recycler)
                //get the holder object from the reusable convert view
                holder = (ViewHolder) convertView.getTag();
            }
            Liability l = (Liability) getItem(position);
            holder.dateTxtView.setText(l.getDate());
            holder.typeTxtView.setText(l.getType());
            holder.amtTxtView.setText(Float.toString(l.getAmt()));
            return convertView;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_liability, menu);
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
