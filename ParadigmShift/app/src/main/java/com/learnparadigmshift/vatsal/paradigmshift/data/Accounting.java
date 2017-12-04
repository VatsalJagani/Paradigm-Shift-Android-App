package com.learnparadigmshift.vatsal.paradigmshift.data;

/**
 * Created by VATSAL on 02/12/2017.
 */

public class Accounting {
    private int acId;
    private int friendId;
    /* SQLite database not support boolean so we use int instead */
    private int complete; /* complete-1 means this accounting has already done. Default value - 0 */
    private double amount;
    /* SQLite database not support date and time so we use string instead */
    private String date;
    private String time;
    private String purpose;


    public void setAcId(int acId) {
        this.acId = acId;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }


    public int getAcId() {
        return acId;
    }

    public int getFriendId() {
        return friendId;
    }

    public double getAmount() {
        return amount;
    }

    public int getComplete() {
        return complete;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getPurpose() {
        return purpose;
    }
}
