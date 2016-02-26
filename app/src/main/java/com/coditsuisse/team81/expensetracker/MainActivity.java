package com.coditsuisse.team81.expensetracker;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.Toast;


public class MainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost = getTabHost();

        TabHost.TabSpec summarySpec = tabHost.newTabSpec("Summary");
        summarySpec.setIndicator("Summary");
        Intent summaryIntent = new Intent(this, SummaryActivity.class);
        summarySpec.setContent(summaryIntent);

        TabHost.TabSpec transactionSpec = tabHost.newTabSpec("Transaction");
        transactionSpec.setIndicator("Transaction");
        Intent transactionIntent = new Intent(this, TransactionActivity.class);
        transactionSpec.setContent(transactionIntent);

        TabHost.TabSpec liabilitySpec = tabHost.newTabSpec("Liabilities");
        liabilitySpec.setIndicator("Liabilities");
        Intent liabilityIntent = new Intent(this, LiabilityActivity.class);
        liabilitySpec.setContent(liabilityIntent);

        tabHost.addTab(summarySpec);
        tabHost.addTab(transactionSpec);
        tabHost.addTab(liabilitySpec);
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
