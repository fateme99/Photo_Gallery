package com.example.photogallery.model;

public class PhotoItem {
    private String mId;
    private String mTitle;
    private String mUrl_s;

    public PhotoItem() {
    }

    public PhotoItem(String id, String title, String url_s) {
        mId = id;
        mTitle = title;
        mUrl_s = url_s;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUrl_s() {
        return mUrl_s;
    }

    public void setUrl_s(String url_s) {
        mUrl_s = url_s;
    }
}
