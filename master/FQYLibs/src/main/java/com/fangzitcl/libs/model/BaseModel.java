package com.fangzitcl.libs.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BaseModel implements Parcelable {

    public BaseModel() {
    }


    protected BaseModel(Parcel in) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

}
