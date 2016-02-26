package com.coditsuisse.team81.expensetracker;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SummaryActivity extends Activity {
    public DbHelper db;
    public static float budget = 0, totalExp = 0, saveGoal = 0;
    SharedPreferences sharedPreferences;
    private Button btnExpense, btnSavings, btnBudget ,btnChart;
    private TextView budg, exp, save, saveg;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        db = new DbHelper(this);
        btnChart = (Button) findViewById(R.id.btn_chart);
        btnExpense = (Button) findViewById(R.id.button_expense);
        btnBudget = (Button) findViewById(R.id.button_budget);
        btnSavings = (Button) findViewById(R.id.button_goal);
        budg = (TextView) findViewById(R.id.budget_rs);
        exp = (TextView) findViewById(R.id.expense_rs);
        save = (TextView) findViewById(R.id.savings_rs);
        saveg = (TextView) findViewById(R.id.sg_rs);

        sharedPreferences = getSharedPreferences(SharedResources.MY_PREFERENCE, Context.MODE_PRIVATE);

        budg.setText(Float.toString(sharedPreferences.getFloat(SharedResources.BUDGET, Context.MODE_PRIVATE)));

        btnExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent expIntent = new Intent(SummaryActivity.this, ExpensesActivity.class);
                startActivity(expIntent);
            }
        });

        btnSavings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent savingsIntent = new Intent(SummaryActivity.this, SavingsActivity.class);
                startActivity(savingsIntent);
            }
        });

        btnBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent budgetIntent = new Intent(SummaryActivity.this, BudgetActivity.class);
                startActivity(budgetIntent);
            }
        });
    }

    public void onChart(View v){
        Intent intent = new Intent(SummaryActivity.this,Chart.class);
        startActivity(intent);
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onResume() {
        super.onResume();

        totalExp = db.getExpense();
        saveGoal = sharedPreferences.getFloat(SharedResources.SAVINGS_GOAL, Context.MODE_PRIVATE);
        budget = sharedPreferences.getFloat(SharedResources.BUDGET, Context.MODE_PRIVATE);

        exp.setText(Float.toString(totalExp));
        budg.setText(Float.toString(budget));
        save.setText(Float.toString(budget - totalExp));
        saveg.setText(Float.toString(saveGoal));

        if ((totalExp / budget) >= 0.8 && budget != 0 && totalExp != 0) {
            Notification.Builder builder = new Notification.Builder(this);
            Notification notification = builder
                    .setSmallIcon(R.drawable.notification_template_icon_bg)
                    .setTicker("80% money used")
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.notification_template_icon_bg))
                    .setContentTitle("Oops Wallet Drying")
                    .setContentText("Only " + Float.toString(budget - totalExp) + " left for the month.")
                    .setWhen(System.currentTimeMillis())
                    .build();
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, notification);
        }

        if ((totalExp - budget) <= saveGoal && budget != 0 && totalExp != 0) {
            Notification.Builder builder = new Notification.Builder(this);
            Notification notification = builder
                    .setSmallIcon(R.drawable.notification_template_icon_bg)
                    .setTicker("Warning: Savings too less!")
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.notification_template_icon_bg))
                    .setContentTitle("Oops, not saving enought!")
                    .setContentText("Losing out on savings goal for the month.")
                    .setWhen(System.currentTimeMillis())
                    .build();
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, notification);
        }

    }


}
