package com.example.photogallery.repository;

import android.util.Log;

import com.example.photogallery.model.PhotoItem;
import com.example.photogallery.network.FlickrFetcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PhotoRepository {
    private FlickrFetcher mFlickrFetcher;

    public PhotoRepository() {
        mFlickrFetcher=new FlickrFetcher();
    }

    public List<PhotoItem>fetchItems(){
        List<PhotoItem>items=new ArrayList<>();
        try {
            String response=mFlickrFetcher.getUrlString(mFlickrFetcher.getRecentUrl());
            JSONObject jsonBody=new JSONObject(response);
            items=parseJson(jsonBody);
        } catch (IOException | JSONException e) {
            Log.e("TAG", "fetchItems: "+e.getMessage(), e);
        }
        return items;
    }
    private List<PhotoItem>parseJson(JSONObject jsonBody) throws JSONException {
        List<PhotoItem>items=new ArrayList<>();
        JSONObject photosObject=jsonBody.getJSONObject("photos");
        JSONArray photoArray=photosObject.getJSONArray("photo");
        for (int i = 0; i <photoArray.length() ; i++) {
            JSONObject photoObject=photoArray.getJSONObject(i);
            if (!photoObject.has("url_s"))
                continue;
            String id=photoObject.getString("id");
            String title=photoObject.getString("title");
            String url_s=photoObject.getString("url_s");
            PhotoItem item=new PhotoItem(id,title,url_s);
            items.add(item);
        }
        return items;
    }


}
