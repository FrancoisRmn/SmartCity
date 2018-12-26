package com.henallux.smartcity.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.henallux.smartcity.R;

public class ImageFullFragment extends Fragment {
    private String urlImage;
    private ImageView imageView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_imagefull, container, false);
        imageView = v.findViewById(R.id.imageView);
        if(!urlImage.isEmpty()) {
            Glide.with(this).load(urlImage).into(imageView);
        }
        return v;
    }

    public void setData(String urlImage)
    {
        this.urlImage= urlImage;
    }

}
