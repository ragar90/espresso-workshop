package com.codesgood.espressoworkshop.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ChatMessage implements Parcelable {

    private String mSender;
    private String mMessage;

    public String getSender() {
        return mSender;
    }

    public void setSender(String mSender) {
        this.mSender = mSender;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mSender);
        dest.writeString(this.mMessage);
    }

    public ChatMessage(String sender, String message) {
        mSender = sender;
        mMessage = message;
    }

    protected ChatMessage(Parcel in) {
        this.mSender = in.readString();
        this.mMessage = in.readString();
    }

    public static final Parcelable.Creator<ChatMessage> CREATOR = new Parcelable.Creator<ChatMessage>() {
        @Override
        public ChatMessage createFromParcel(Parcel source) {
            return new ChatMessage(source);
        }

        @Override
        public ChatMessage[] newArray(int size) {
            return new ChatMessage[size];
        }
    };
}
