package com.coditsuisse.team81.expensetracker;

        import java.util.ArrayList;
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
        import android.os.Build;

public class ListActivity extends Activity {

    private ArrayList<All> all;
    private ListView listView;
    private MyAdapter myAdapter;
    private DbHelper db;
    private String date,type;
    private int paid;
    private float amt;

    class ViewHolder
    {
        TextView dateTxtView,typeTxtView,amtTxtView,paidTxtView;
    }
    class MyAdapter extends BaseAdapter
    {
        @Override
        public int getCount() {
            return all.size();
        }

        @Override
        public Object getItem(int position) {
            return all.get(position);
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
                convertView = li.inflate(R.layout.listitem_all, null);
                holder.dateTxtView = (TextView) convertView.findViewById(R.id.all_date);
                holder.typeTxtView = (TextView) convertView.findViewById(R.id.all_type);
                holder.amtTxtView = (TextView) convertView.findViewById(R.id.all_amt);
                holder.paidTxtView = (TextView) convertView.findViewById(R.id.all1_paid);
                //associate the holder object with the convert view
                convertView.setTag(holder);
            }
            else
            {
                //convert view is not null (got from the recycler)
                //get the holder object from the reusable convert view
                holder = (ViewHolder) convertView.getTag();
            }
            All all1 = (All) getItem(position);
            holder.dateTxtView.setText(all1.getDate());
            holder.typeTxtView.setText(all1.getType());
            holder.amtTxtView.setText(Float.toString(all1.getAmt()));
            holder.paidTxtView.setText(Integer.toString(all1.getPaid()));
            return convertView;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        listView = (ListView) findViewById(R.id.lv_lv);
        db = new DbHelper(this);
        all = db.searchByAll();
        myAdapter = new MyAdapter();
        listView.setAdapter(myAdapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                date = ((TextView) findViewById(R.id.all_date)).getText().toString();
                amt = Float.parseFloat(((TextView) findViewById(R.id.all_amt)).getText().toString());
                type = ((TextView) findViewById(R.id.all_type)).getText().toString();
                paid = Integer.parseInt(((TextView) findViewById(R.id.all1_paid)).getText().toString());
                db.deleteDb(date, amt, type, paid);
                all.remove(position);
                myAdapter.notifyDataSetChanged();
                return false;
            }
        });
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