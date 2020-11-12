package com.example.photogallery.controller.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import com.example.photogallery.R;
import com.example.photogallery.model.PhotoItem;
import com.example.photogallery.repository.PhotoRepository;

import java.util.List;


public class PhotoGalleryFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private PhotoRepository mRepository;
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
        mRepository=new PhotoRepository();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_photo_gallery, container, false);
        findViews(view);
        initView();
        setupAdapter();
        return view;
    }

    private void setupAdapter() {
        PhotoAdapter adapter=new PhotoAdapter(mRepository.getPhotoItems());
        mRecyclerView.setAdapter(adapter);
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
    }

    private void findViews(View view) {
        mRecyclerView=view.findViewById(R.id.recycler_photo_gallery);
    }
    private class PhotoHolder extends RecyclerView.ViewHolder{
        private PhotoItem mPhoto;
        private TextView mTextView;
        public PhotoHolder(@NonNull View itemView) {
            super(itemView);
            mTextView= (TextView) itemView;
        }
        public void bindPhoto(PhotoItem item){
            mPhoto=item;
            mTextView.setText(item.getTitle());
        }
    }
    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder>{
        private List<PhotoItem> mPhotoItems;

        public PhotoAdapter(List<PhotoItem> photoItems) {
            mPhotoItems = photoItems;
        }

        public List<PhotoItem> getPhotoItems() {
            return mPhotoItems;
        }

        public void setPhotoItems(List<PhotoItem> photoItems) {
            mPhotoItems = photoItems;
        }

        @NonNull
        @Override
        public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TextView textView=new TextView(getContext());
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

}