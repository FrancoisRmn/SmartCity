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
import com.henallux.smartcity.model.Restaurant;

import org.w3c.dom.Text;

public class ElementDetailFragmentRestaurant extends Fragment {
    private Restaurant restaurant;
    private TextView titleFragmentDetail;
    private TextView descriptionElementDetailTitleRestaurant;
    private TextView email;
    private TextView cellPhone;
    private TextView phone;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_element_detail_restaurant, container, false);
        titleFragmentDetail = v.findViewById(R.id.titleFragmentElementDetailRestaurant);
        titleFragmentDetail.setText(restaurant.getNomCommerce());
        descriptionElementDetailTitleRestaurant = v.findViewById(R.id.descriptionElementDetailRestaurant);
        if(this.restaurant.getDescription() != null)
        {
            descriptionElementDetailTitleRestaurant.setText(this.restaurant.getDescription());
        }
        email = v.findViewById(R.id.emailRestaurant);
        if(this.restaurant.getAdresseMail() != null){
            email.setText("Email : " + this.restaurant.getAdresseMail());
        }
        cellPhone = v.findViewById(R.id.cellphoneRestaurant);
        if(this.restaurant.getNumeroGSM() != 0){
            cellPhone.setText("GSM : " + this.restaurant.getNumeroGSM());
        }
        phone = v.findViewById(R.id.phoneRestaurant);
        if(this.restaurant.getNumeroFixe() != 0){
            phone.setText("Fixe : " + this.restaurant.getNumeroFixe());
        }
        return v;
    }

    public void setData(Restaurant restaurant)
    {
        this.restaurant= restaurant;
    }
}
