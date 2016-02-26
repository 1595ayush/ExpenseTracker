package com.coditsuisse.team81.expensetracker;

/**
 * Created by abc on 12-09-2015.
 */
public class All {
    private String date;
    private float amt;
    private String type;
    private int paid;
    public All(String date,float amt,int paid,String type)
    {
        super();
        this.amt = amt;
        this.date = date;
        this.type = type;
        this.paid=paid;
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
    public int getPaid() {
        return paid;
    }
    public void setPaid(int paid) {
        this.paid = paid;
    }
}
