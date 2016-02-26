package com.coditsuisse.team81.expensetracker;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract.Contacts.Data;
import android.widget.Toast;

public class DbHelper {

    private static final String DATABASE_NAME = "MyExpenses.sqlite";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_EXP = "expenses";
    private static final String COL_DATE = "date";
    private static final String COL_AMT = "amount";
    private static final String COL_TYPE = "type";
    private static final String COL_PAID = "paid";

    private static final String CREATE_TABLE_EXP = "create table "+TABLE_EXP+"("+
            COL_DATE+" text not null, "+
            COL_AMT+" float not null, "+
            COL_TYPE+" text not null, "+
            COL_PAID+" int not null)";
    private SQLiteDatabase db;
    public DbHelper(Context context) {
        MySqliteHelper helper= new MySqliteHelper(context);
        db=helper.getWritableDatabase();
    }

    public int insertExp(String date, float amt,String type, int paid) {
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_DATE, date);
        contentValues.put(COL_AMT, amt);
        contentValues.put(COL_TYPE, type);
        contentValues.put(COL_PAID, paid);
        long rowId=db.insert(TABLE_EXP, null, contentValues);
        if(rowId==-1)
            return 0;
        return 1;
    }

    public ArrayList<All> searchByAll()
    {
        Cursor cursor;
        int amtColIndex,dateColIndex,paidColIndex,typeColIndex;
        ArrayList<All> all = new ArrayList<All>();
        cursor = db.query(TABLE_EXP, new String[]{COL_DATE, COL_AMT, COL_TYPE, COL_PAID}, null, null, null, null, null);
        amtColIndex = cursor.getColumnIndex(COL_AMT);
        dateColIndex = cursor.getColumnIndex(COL_DATE);
        paidColIndex= cursor.getColumnIndex(COL_PAID);
        typeColIndex=cursor.getColumnIndex(COL_TYPE);

        if(!cursor.moveToFirst())
            return all;
        cursor.moveToLast();
        do
        {
            all.add(new All(cursor.getString(dateColIndex),
                    cursor.getFloat(amtColIndex),
                    cursor.getInt(paidColIndex),
                    cursor.getString(typeColIndex)));
        }while(cursor.moveToPrevious());
        return all;
    }

    public ArrayList<Category> searchByCategory(String categoryName)
    {
        Cursor cursor;
        int dateColIndex,paidColIndex,amtColIndex;
        ArrayList<Category> categories = new ArrayList<Category>();
        String selection = COL_TYPE + " like "+ "\"" + categoryName + "\"";
        cursor = db.query(TABLE_EXP, new String[]{COL_DATE, COL_AMT, COL_PAID}, selection, null, null, null, null);
        amtColIndex = cursor.getColumnIndex(COL_AMT);
        paidColIndex = cursor.getColumnIndex(COL_PAID);
        dateColIndex = cursor.getColumnIndex(COL_DATE);

        if(!cursor.moveToFirst())
            return categories;
        cursor.moveToLast();
        do
        {
            categories.add(new Category(cursor.getString(dateColIndex),
                    cursor.getFloat(amtColIndex),
                    cursor.getInt(paidColIndex)));
        }while(cursor.moveToPrevious());
        cursor.close();
        return categories;
    }

    public ArrayList<Liability> getLiability()
    {
        Cursor cursor;
        int dateColIndex,typeColIndex,amtColIndex;
        ArrayList<Liability> liabilities = new ArrayList<Liability>();
        String selection = COL_PAID + " = 0";
        cursor = db.query(TABLE_EXP, new String[]{COL_DATE, COL_TYPE, COL_AMT}, selection, null, null, null, null);
        amtColIndex = cursor.getColumnIndex(COL_AMT);
        typeColIndex = cursor.getColumnIndex(COL_TYPE);
        dateColIndex = cursor.getColumnIndex(COL_DATE);
        if(!cursor.moveToFirst())
            return liabilities;
        cursor.moveToLast();
        do
        {
            liabilities.add(new Liability(cursor.getString(dateColIndex),
                    cursor.getString(typeColIndex),
                    cursor.getFloat(amtColIndex)));
        }while(cursor.moveToPrevious());
        cursor.close();
        return liabilities;
    }

    public int deleteDb(String date,Float amt,String cat,int paid)
    {
        String select = COL_DATE + " like \"" + date +"\" AND "+ COL_TYPE + " like \"" + cat+ "\" AND " +
                COL_AMT + " = " + amt+ " AND " + COL_PAID + " = " + paid;
        db.delete(TABLE_EXP, select, null);
        return 0;
    }
    public float getExpense()
    {
       Cursor cursor;
        float expense = 0;
        cursor=db.query(TABLE_EXP,new String[]{COL_AMT},null,null,null,null,null);
        int amtColIndex = cursor.getColumnIndex(COL_AMT);
        if(!cursor.moveToFirst())
            return 0;
        cursor.moveToLast();
        do
        {
                expense = expense + cursor.getFloat(amtColIndex);
        }while(cursor.moveToPrevious());
        cursor.close();
        return expense;
    }
    class MySqliteHelper extends SQLiteOpenHelper{

        public MySqliteHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_EXP);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}