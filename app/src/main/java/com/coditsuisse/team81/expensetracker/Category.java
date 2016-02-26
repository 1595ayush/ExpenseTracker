package com.coditsuisse.team81.expensetracker;

/**
 * Created by abc on 12-09-2015.
 */
public class Category {
        private String date;
        private float amt;
        private int paid;
        public Category(String date,float amt,int paid)
        {
            super();
            this.amt = amt;
            this.date = date;
            this.setPaid(paid);
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
        public int getPaid() {
            return paid;
        }
        public void setPaid(int paid) {
            this.paid = paid;
        }
}
