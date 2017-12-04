package com.learnparadigmshift.vatsal.paradigmshift.data;

/**
 * Created by VATSAL on 02/12/2017.
 */

public class MainListModel {
    private int friendId;

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public int getFriendId() {
        return friendId;
    }

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private double amount;

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }


    // need to add methods for comparision by hashmap

    @Override
    public int hashCode() {
        return friendId;
    }

    @Override
    public boolean equals(Object obj) {
        if(friendId==((MainListModel)obj).getFriendId()){
            return true;
        }
        return false;
    }
}
