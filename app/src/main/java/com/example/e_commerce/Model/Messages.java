package com.example.e_commerce.Model;


import java.util.Date;

public class MessageUser {
    private String mText;
    private String mSender;
    private Date mDate;
    private String mDateString;

    public MessageUser() {

    }

    public MessageUser(String mText, String mSender) {
        this.mText = mText;
        this.mSender = mSender;
    }

    public MessageUser(String mText, String mSender, Date mDate) {
        this.mText = mText;
        this.mSender = mSender;
        this.mDate = mDate;
    }

    public String getText() {
        return mText;
    }

    public void setText(String mText) {
        this.mText = mText;
    }

    public String getSender() {
        return mSender;
    }

    public void setSender(String mSender) {
        this.mSender = mSender;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public void setDateString(String mDateString) {
        this.mDateString = mDateString;
    }
}