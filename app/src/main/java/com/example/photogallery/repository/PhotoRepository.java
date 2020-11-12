package com.example.photogallery.repository;

import com.example.photogallery.model.PhotoItem;

import java.util.List;

public class PhotoRepository {
    List<PhotoItem>mPhotoItems;


    public List<PhotoItem> getPhotoItems() {
        return mPhotoItems;
    }

    public void setPhotoItems(List<PhotoItem> photoItems) {
        mPhotoItems = photoItems;
    }
}
