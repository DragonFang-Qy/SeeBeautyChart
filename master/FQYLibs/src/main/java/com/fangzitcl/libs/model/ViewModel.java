package com.fangzitcl.libs.model;

import android.os.Parcel;

public class ViewModel extends BaseModel {

    public static final Creator<ViewModel> CREATOR = new Creator<ViewModel>() {
        @Override
        public ViewModel createFromParcel(Parcel source) {
            return new ViewModel(source);
        }

        @Override
        public ViewModel[] newArray(int size) {
            return new ViewModel[size];
        }
    };
    private int mResID;

    public ViewModel() {
    }

    public ViewModel(int resID) {
        this.mResID = resID;
    }

    protected ViewModel(Parcel in) {
        super(in);
        this.mResID = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.mResID);
    }

    public int getResID() {
        return mResID;
    }

    public void setResID(int mResID) {
        this.mResID = mResID;
    }
}
