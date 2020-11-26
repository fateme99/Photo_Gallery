package com.example.photogallery.controller.fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.photogallery.R;
import com.example.photogallery.model.PhotoItem;
import com.example.photogallery.network.FlickrFetcher;
import com.example.photogallery.repository.PhotoRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PhotoGalleryFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private PhotoRepository mRepository;
    private int mPage_No=0;
    private PhotoAdapter mAdapter;

    public PhotoGalleryFragment() {
        // Required empty public constructor
    }


    public static PhotoGalleryFragment newInstance() {
        PhotoGalleryFragment fragment = new PhotoGalleryFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository = new PhotoRepository();
        FlickerThread flickerThread = new FlickerThread();
        flickerThread.execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_photo_gallery, container, false);
        findViews(view);
        initView();
        setListeners();
        return view;
    }

    private void setupAdapter(List<PhotoItem>items) {
        if (mAdapter==null)
            mAdapter = new PhotoAdapter(items);
        else
            mAdapter.setPhotoItems(items);

        mRecyclerView.setAdapter(mAdapter);

    }



    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_photo_gallery);

    }
    private void initView() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
    }
    private void setListeners(){
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!mRecyclerView.canScrollVertically(1)){
                    FlickerThread flickerThread=new FlickerThread();
                    flickerThread.execute();

                }
            }
        });
    }

    private class PhotoHolder extends RecyclerView.ViewHolder {
        private PhotoItem mPhoto;
        private TextView mTextView;

        public PhotoHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;
        }

        public void bindPhoto(PhotoItem item) {
            mPhoto = item;
            mTextView.setText(item.getTitle());
        }
    }

    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {
        private List<PhotoItem> mPhotoItems;

        public PhotoAdapter(List<PhotoItem> photoItems) {

            mPhotoItems=photoItems;
        }

        public List<PhotoItem> getPhotoItems() {
            return mPhotoItems;
        }

        public void setPhotoItems(List<PhotoItem> photoItems) {
            mPhotoItems.addAll(photoItems);
        }

        @NonNull
        @Override
        public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TextView textView = new TextView(getContext());
            return new PhotoHolder(textView);
        }

        @Override
        public void onBindViewHolder(@NonNull PhotoHolder holder, int position) {
            holder.bindPhoto(mPhotoItems.get(position));
        }

        @Override
        public int getItemCount() {
            return mPhotoItems.size();
        }


    }

    private class FlickerThread extends AsyncTask<Void, Void, List<PhotoItem>> {

        @Override
        protected List<PhotoItem> doInBackground(Void... voids) {
            List<PhotoItem>items=mRepository.fetchItems(++mPage_No);

            return items;


        }

        @Override
        protected void onPostExecute(List<PhotoItem> items) {
            super.onPostExecute(items);
            setupAdapter(items);
        }
    }



}