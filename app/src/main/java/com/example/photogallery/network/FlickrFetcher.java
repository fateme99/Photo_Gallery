package com.example.photogallery.network;

import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FlickrFetcher {
    public static final String BASE_URL="https://www.flickr.com/services/rest";
    public static final String METHOD_RECENT="flickr.photos.getRecent";
    public static final String API_KEY="8af26fd53e94e56a95100f608102a272";
    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url=new URL(urlSpec);
        HttpURLConnection connection= (HttpURLConnection) url.openConnection();
        try{
            InputStream inputStream=connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
                throw new IOException();
            byte[] buffer=new byte[1024];
            int bytes_read=0;
            ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
            while ((bytes_read=inputStream.read(buffer) )!= -1){
                outputStream.write(buffer,0,bytes_read);
            }
            byte[] result=outputStream.toByteArray();
            inputStream.close();
            outputStream.close();
            return result;
        }finally {
            connection.disconnect();
        }


    }
    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }
    public String getRecentUrl(){
        Uri uri =Uri.parse(BASE_URL)
                .buildUpon()
                .appendQueryParameter("method",METHOD_RECENT)
                .appendQueryParameter("api_key",API_KEY)
                .appendQueryParameter("extras","url_s")
                .appendQueryParameter("format","json")
                .appendQueryParameter("nojsoncallback","1")
                .build();
        return uri.toString();
    }
}
