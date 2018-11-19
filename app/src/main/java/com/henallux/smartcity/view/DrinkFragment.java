package com.henallux.smartcity.view;

import android.os.Bundle;
import android.os.TransactionTooLargeException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.henallux.smartcity.R;

public class DrinkFragment extends Fragment {
    private Button mButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_drink, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        mButton = view.findViewById(R.id.buttonTest);
        mButton.setText("123");
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ElementDetailFragment();
                ElementDetailFragment elementDetailFragment = new ElementDetailFragment();
                FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, elementDetailFragment).commit();
            }
        });
    }
}
