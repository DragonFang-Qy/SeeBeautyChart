package com.a704084109qq.news.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.fangzitcl.libs.model.BaseModel;

public class BeautyPicModel extends BaseModel implements Item {

    public static final Parcelable.Creator<BeautyPicModel> CREATOR
            = new Parcelable.Creator<BeautyPicModel>() {
        public BeautyPicModel createFromParcel(Parcel in) {
            return new BeautyPicModel(in);
        }

        public BeautyPicModel[] newArray(int size) {
            return new BeautyPicModel[size];
        }
    };
    private int Type = 2;
    private String ctime; // 创建时间
    private String title; // 标题
    private String description; // 简介
    private String picUrl; // 图片链接
    private String url; // 网址链接

    public BeautyPicModel(Parcel in) {
        super(in);
        ctime = in.readString();
        title = in.readString();
        description = in.readString();
        picUrl = in.readString();
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ctime);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(picUrl);
        dest.writeString(url);
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int getType() {
        return Type;
    }
}

