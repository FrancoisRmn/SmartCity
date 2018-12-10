package com.henallux.smartcity.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.henallux.smartcity.R;
import com.henallux.smartcity.model.Bar;

public class ElementDetailFragmentBar extends Fragment {
    private Bar bar;
    private TextView titleFragmentDetail;
    private TextView descriptionElementDetailBar;
    private TextView email;
    private TextView cellPhone;
    private TextView phone;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_element_detail_bar, container, false);
        titleFragmentDetail = v.findViewById(R.id.titleFragmentElementDetailBar);
        titleFragmentDetail.setText(bar.getNomCommerce());
        descriptionElementDetailBar = v.findViewById(R.id.descriptionElementDetailBar);
        if(this.bar.getDescription() != null)
        {
            descriptionElementDetailBar.setText(this.bar.getDescription());
        }
        email = v.findViewById(R.id.emailBar);
        if(this.bar.getAdresseMail() != null){
            email.setText("EMail : " + this.bar.getAdresseMail());
        }
        cellPhone = v.findViewById(R.id.cellphoneBar);
        if(this.bar.getNumeroGSM() != 0){
            cellPhone.setText("GSM : " + this.bar.getNumeroGSM());
        }
        phone = v.findViewById(R.id.phoneBar);
        if(this.bar.getNumeroFixe() != 0){
            phone.setText("Fixe : " + this.bar.getNumeroFixe());
        }
        return v;
    }

    public void setData(Bar bar)
    {
        this.bar= bar;
    }
}
