package com.codesgood.espressoworkshop.network.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Amilcar on 4/28/16.
 */
public class Answer implements Parcelable {
    @SerializedName("success")
    private Long mSuccess;

    @SerializedName("errorType")
    private String mErrorType;

    @SerializedName("errorMessage")
    private String mErrorMessage;

    @SerializedName("message")
    private Message mMessage;

    public Long getSuccess() {
        return mSuccess;
    }

    public String getErrorType() {
        return mErrorType;
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }

    public Message getMessage() {
        return mMessage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.mSuccess);
        dest.writeString(this.mErrorType);
        dest.writeString(this.mErrorMessage);
        dest.writeParcelable(this.mMessage, flags);
    }

    public Answer() {
    }

    protected Answer(Parcel in) {
        this.mSuccess = (Long) in.readValue(Long.class.getClassLoader());
        this.mErrorType = in.readString();
        this.mErrorMessage = in.readString();
        this.mMessage = in.readParcelable(Message.class.getClassLoader());
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
