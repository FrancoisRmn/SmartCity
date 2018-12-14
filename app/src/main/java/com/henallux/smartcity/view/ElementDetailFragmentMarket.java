package com.henallux.smartcity.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.henallux.smartcity.R;
import com.henallux.smartcity.model.Market;

public class ElementDetailFragmentMarket extends Fragment {
    private Market market;
    private TextView titleFragmentDetail;
    private TextView descriptionElementDetailMarket;
    private TextView email;
    private TextView cellPhone;
    private TextView phone;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.tool_bar, menu);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch(item.getItemId()){
            case R.id.itemCallButton :
                if(this.market.getNumeroGSM() != null)
                {
                    intent= new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + this.market.getNumeroGSM()));
                    startActivity(intent);

                }
                else{
                    Toast.makeText(getActivity(), "Numéro de téléphone non disponible !", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.itemSendMail :
                if(this.market.getAdresseMail() != null){
                    intent = new Intent(Intent.ACTION_SEND);
                    //TODO
                    //fixme
                    //ne remplit pas l'adresse du destinataire
                    intent.setData(Uri.parse("mailto:" + this.market.getAdresseMail()));
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_EMAIL, this.market.getAdresseMail());
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Question :");
                    intent.putExtra(Intent.EXTRA_TEXT, "(Exemple) Bonjour, vous reste t'il des chaussures nikes + 46 en stocks ?");
                    try {
                        startActivity(Intent.createChooser(intent, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getActivity(),
                                "There is no email client installed.", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getActivity(), "Adresse email non disponible !", Toast.LENGTH_SHORT).show();
                }
            case R.id.itemRouteButton:
                //TODO
            default:
                return super.onOptionsItemSelected(item);
        }
    }


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
        if(this.market.getNumeroGSM() != null){
            cellPhone.setText("GSM : " + this.market.getNumeroGSM());
        }
        phone = v.findViewById(R.id.phoneBar);
        if(this.market.getNumeroFixe() != null){
            phone.setText("Fixe : " + this.market.getNumeroFixe());
        }
        return v;
    }

    public void setData(Market market)
    {
        this.market = market;
    }

}
