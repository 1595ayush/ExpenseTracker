package com.coditsuisse.team81.expensetracker;

/**
 * Created by abc on 12-09-2015.
 */

public class Liability {
    private String date;
    private float amt;
    private String type;
    public Liability(String date,String type,float amt)
    {
        super();
        this.amt = amt;
        this.date = date;
        this.type = type;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public float getAmt() {
        return amt;
    }
    public void setAmt(float amt) {
        this.amt = amt;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

}
