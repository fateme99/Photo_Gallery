package com.example.photogallery.controller.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;

public class PhotoGalleryActivity extends AppCompatActivity {

    public static Intent newIntent(Context context){
        Intent intent=new Intent(context,PhotoGalleryActivity.class);
        return intent;
    }
}