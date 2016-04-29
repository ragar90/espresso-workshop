package com.codesgood.espressoworkshop.network.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Message implements Parcelable {

    @SerializedName("chatBotName")
    private String mChatBotName;

    @SerializedName("chatBotID")
    private Long mChatBotID;

    @SerializedName("message")
    private String mMessage;

    @SerializedName("emotion")
    private String mEmotion;

    public String getChatBotName() {
        return mChatBotName;
    }

    public Long getChatBotID() {
        return mChatBotID;
    }

    public String getMessage() {
        return mMessage;
    }

    public String getEmotion() {
        return mEmotion;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mChatBotName);
        dest.writeValue(this.mChatBotID);
        dest.writeString(this.mMessage);
        dest.writeString(this.mEmotion);
    }

    public Message() {
    }

    protected Message(Parcel in) {
        this.mChatBotName = in.readString();
        this.mChatBotID = (Long) in.readValue(Long.class.getClassLoader());
        this.mMessage = in.readString();
        this.mEmotion = in.readString();
    }

    public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}