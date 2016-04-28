package com.codesgood.espressoworkshop.network.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Answer implements Parcelable {

    @SerializedName("result")
    private int mResult;

    @SerializedName("id")
    private int mId;

    @SerializedName("response")
    private String mResponse;

    @SerializedName("msg")
    private String mMsg;

    public int getResult() {
        return mResult;
    }

    public int getId() {
        return mId;
    }

    public String getResponse() {
        return mResponse;
    }

    public String getMsg() {
        return mMsg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mResult);
        dest.writeInt(this.mId);
        dest.writeString(this.mResponse);
        dest.writeString(this.mMsg);
    }

    public Answer() {
    }

    protected Answer(Parcel in) {
        this.mResult = in.readInt();
        this.mId = in.readInt();
        this.mResponse = in.readString();
        this.mMsg = in.readString();
    }

    public static final Parcelable.Creator<Answer> CREATOR = new Parcelable.Creator<Answer>() {
        @Override
        public Answer createFromParcel(Parcel source) {
            return new Answer(source);
        }

        @Override
        public Answer[] newArray(int size) {
            return new Answer[size];
        }
    };
}
