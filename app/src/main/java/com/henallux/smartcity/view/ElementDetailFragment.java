package com.henallux.smartcity.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.henallux.smartcity.R;
import com.henallux.smartcity.model.Market;

public class ElementDetailFragment extends Fragment {
    private Market market;
    private TextView titleFragmentDetail;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_element_detail, container, false);
        titleFragmentDetail = v.findViewById(R.id.titleFragmentElementDetail);
        titleFragmentDetail.setText(market.getNomCommerce());
        return v;
    }

    public void setData(Market market)
    {
        this.market = market;
    }
}
