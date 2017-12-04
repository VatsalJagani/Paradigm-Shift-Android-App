package com.learnparadigmshift.vatsal.paradigmshift.data;

/**
 * Created by VATSAL on 02/12/2017.
 */

public class Friend {
    private int friendId;

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public int getFriendId() {
        return friendId;
    }

    private String name;

    public void setName(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    private String fullName;

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    private String mobileNo;

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    private String emailAddress;

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
}
