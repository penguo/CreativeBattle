package com.afordev.creativebattle.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by penguo on 2018-02-03.
 */

public class UserData implements Parcelable{
    private int userNo;
    private String name;
    private int exp;

    public UserData(int userNo, String name, int exp){
        this.userNo = userNo;
        this.name = name;
        this.exp = exp;
    }

    protected UserData(Parcel in) {
        userNo = in.readInt();
        name = in.readString();
        exp = in.readInt();
    }

    public static final Creator<UserData> CREATOR = new Creator<UserData>() {
        @Override
        public UserData createFromParcel(Parcel in) {
            return new UserData(in);
        }

        @Override
        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(userNo);
        parcel.writeString(name);
        parcel.writeInt(exp);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(userNo + ",");
        sb.append(name + ",");
        sb.append(exp);
        return sb.toString();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public String getName() {
        return name;
    }

    public int getExp() {
        return exp;
    }

    public int getUserNo() {
        return userNo;
    }
}
