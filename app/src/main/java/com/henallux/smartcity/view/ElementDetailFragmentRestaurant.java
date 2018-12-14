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
import android.widget.Toolbar;

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
    private TextView flagshipProduct;
    private TextView localisation;
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
            case R.id.itemCallGSMButton :
                if(this.restaurant.getNumeroGSM() != null)
                {
                    intent= new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + this.restaurant.getNumeroGSM()));
                    startActivity(intent);

                }
                else{
                    Toast.makeText(getActivity(), "Numéro de téléphone non disponible !", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.itemCallButton :
                if(this.restaurant.getNumeroGSM() != null)
                {
                    intent= new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + this.restaurant.getNumeroFixe()));
                    startActivity(intent);

                }
                else{
                    Toast.makeText(getActivity(), "Numéro de téléphone non disponible !", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.itemSendMail :
                if(this.restaurant.getAdresseMail() != null){
                    intent = new Intent(Intent.ACTION_SEND);
                    //TODO
                    //fixme
                    //ne remplit pas l'adresse du destinataire
                    intent.setData(Uri.parse("mailto:" + this.restaurant.getAdresseMail()));
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_EMAIL, this.restaurant.getAdresseMail());
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
        if(this.restaurant.getNumeroGSM() != null){
            cellPhone.setText("GSM : " + this.restaurant.getNumeroGSM());
        }
        phone = v.findViewById(R.id.phoneRestaurant);
        if(this.restaurant.getNumeroFixe() != null){
            phone.setText("Fixe : " + this.restaurant.getNumeroFixe());
        }
        flagshipProduct = v.findViewById(R.id.flagshipProduct);
        if(this.restaurant.getProduitPhare() != null){
            flagshipProduct.setText(this.restaurant.getProduitPhare());
        }
        localisation = v.findViewById(R.id.localisation);
        if(this.restaurant.getRue() != null){
            String numero= (this.restaurant.getNumero() != null) ? this.restaurant.getNumero().toString() : "";
            this.localisation.setText(this.restaurant.getRue() + " " + numero);
        }
        return v;
    }

    public void setData(Restaurant restaurant)
    {
        this.restaurant= restaurant;
    }
}
