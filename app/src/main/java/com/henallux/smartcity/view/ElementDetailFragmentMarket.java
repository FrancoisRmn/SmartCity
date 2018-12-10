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
import com.henallux.smartcity.model.Market;

public class ElementDetailFragmentMarket extends Fragment {
    private Market market;
    private TextView titleFragmentDetail;
    private TextView descriptionElementDetailMarket;
    private TextView email;
    private TextView cellPhone;
    private TextView phone;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_element_detail_market, container, false);
        titleFragmentDetail = v.findViewById(R.id.titleFragmentElementDetailMarket);
        titleFragmentDetail.setText(market.getNomCommerce());
        descriptionElementDetailMarket = v.findViewById(R.id.descriptionElementDetailMarket);
        if(this.market.getDescription() != null){
            descriptionElementDetailMarket.setText(this.market.getDescription());
        }
        email = v.findViewById(R.id.emailMarket);
        if(this.market.getAdresseMail() != null){
            email.setText("EMail : " + this.market.getAdresseMail());
        }
        cellPhone = v.findViewById(R.id.cellphoneBar);
        if(this.market.getNumeroGSM() != 0){
            cellPhone.setText("GSM : " + this.market.getNumeroGSM());
        }
        phone = v.findViewById(R.id.phoneBar);
        if(this.market.getNumeroFixe() != 0){
            phone.setText("Fixe : " + this.market.getNumeroFixe());
        }
        return v;
    }

    public void setData(Market market)
    {
        this.market = market;
    }

}
