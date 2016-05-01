package com.codesgood.espressoworkshop.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public class UnitTestExample implements Parcelable {

    private String mFirstName;
    private String mLastName;

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getFullName(){
        return mFirstName + " " + getLastName();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mFirstName);
        dest.writeString(this.mLastName);
    }

    public UnitTestExample(String name, String lastName) {
        mFirstName = name;
        mLastName = lastName;
    }

    protected UnitTestExample(Parcel in) {
        this.mFirstName = in.readString();
        this.mLastName = in.readString();
    }

    public static final Parcelable.Creator<UnitTestExample> CREATOR = new Parcelable.Creator<UnitTestExample>() {
        @Override
        public UnitTestExample createFromParcel(Parcel source) {
            return new UnitTestExample(source);
        }

        @Override
        public UnitTestExample[] newArray(int size) {
            return new UnitTestExample[size];
        }
    };
}
