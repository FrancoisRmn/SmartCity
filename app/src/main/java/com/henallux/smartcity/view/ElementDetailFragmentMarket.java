package com.henallux.smartcity.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.henallux.smartcity.applicationObject.Application;
import com.henallux.smartcity.R;
import com.henallux.smartcity.utils.Utils;
import com.henallux.smartcity.model.Market;
import com.henallux.smartcity.model.OpeningPeriod;

public class ElementDetailFragmentMarket extends Fragment {
    private Market market;
    private TextView titleFragmentDetail;
    private TextView descriptionElementDetailMarket;
    private TextView email;
    private TextView cellPhone;
    private TextView phone;
    private TextView flagshipProduct;
    private TextView localisation;
    private Application applicationContext;
    private TextView scheduleMarket;
    private ImageView imagesMarket;
    private Button buttonNextImage;
    private int indexImage;

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
                if(this.market.getNumeroGSM() != null)
                {
                    intent= new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:+32" + this.market.getNumeroGSM()));
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getActivity(), "Numéro de téléphone non disponible !", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.itemCallButton :
                if(this.market.getNumeroGSM() != null)
                {
                    intent= new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:+32" + this.market.getNumeroFixe()));
                    startActivity(intent);

                }
                else{
                    Toast.makeText(getActivity(), "Numéro de téléphone non disponible !", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.itemSendMail :
                if(this.market.getAdresseMail() != null){
                    intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{this.market.getAdresseMail()});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Question :");
                    intent.putExtra(Intent.EXTRA_TEXT, "Bonjour, \n");
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
                return true;
            case R.id.itemRouteButton:
                // Create a Uri from an intent string. Use the result to create an Intent.
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(this.market.getRue() + " " + this.market.getNumero() + ", Namur"));
                // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                // Make the Intent explicit by setting the Google Maps package
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
                return true;
            case R.id.itemFavorite:
                applicationContext = (Application)getActivity().getApplicationContext();
                if(applicationContext.isConnected()){
                    Toast.makeText(getActivity(), "Commerce ajouté aux favoris", Toast.LENGTH_SHORT).show();
                    //TODO
                    //changer la couleur de l'icone et ajouté aux favoris de l'utilisateur
                }
                else{
                    Toast.makeText(getActivity(), "Vous devez être connecté pour ajouter un commerce aux favoris", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_element_detail_market, container, false);
        titleFragmentDetail = v.findViewById(R.id.titleFragmentElementDetailBar);
        titleFragmentDetail.setText(market.getNomCommerce());
        descriptionElementDetailMarket = v.findViewById(R.id.descriptionElementDetailMarket);
        if(this.market.getDescription() != null){
            descriptionElementDetailMarket.setText(this.market.getDescription());
        }
        email = v.findViewById(R.id.emailMarket);
        if(this.market.getAdresseMail() != null){
            email.setText("EMail : " + this.market.getAdresseMail());
        }
        cellPhone = v.findViewById(R.id.cellphoneMarket);
        if(this.market.getNumeroGSM() != null){
            cellPhone.setText("GSM : " + this.market.getNumeroGSM());
        }
        phone = v.findViewById(R.id.phoneBar);
        if(this.market.getNumeroFixe() != null){
            phone.setText("Fixe : " + this.market.getNumeroFixe());
        }
        flagshipProduct = v.findViewById(R.id.flagshipProduct);
        if(this.market.getProduitPhare() != null){
            flagshipProduct.setText(this.market.getProduitPhare());
        }
        localisation = v.findViewById(R.id.localisation);
        if(this.market.getRue() != null){
            String numero= (this.market.getNumero() != null) ? this.market.getNumero().toString() : "";
            this.localisation.setText(this.market.getRue() + " " + numero);
        }
        scheduleMarket = v.findViewById(R.id.scheduleMarket);
        if(this.market.getOpeningPeriod() != null){
            String scheduleText = "";
            for(OpeningPeriod horaire: this.market.getOpeningPeriod())
            {
                scheduleText += Utils.getDay(horaire.getJour()) + ": " + horaire.getHoraireDebut() + " à " + horaire.getHoraireFin() + "\n";
            }
            scheduleMarket.setText(scheduleText);
        }

        imagesMarket= v.findViewById(R.id.imageMarket);
        buttonNextImage = v.findViewById(R.id.buttonNextImageMarket);
        if(!this.market.getImageCommerce().isEmpty()){
            Glide.with(this).load(this.market.getImageCommerce().get(0).getUrl()).into(imagesMarket);
            this.indexImage = 1;
            //quand on click sur l'image on lance un nouveau fragment avec l'image affiché en grand
            imagesMarket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment imageFullFragment = new ImageFullFragment();
                    ((ImageFullFragment) imageFullFragment).setData(ElementDetailFragmentMarket.this.market.getImageCommerce().get(0).getUrl());
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, imageFullFragment);
                    fragmentTransaction.addToBackStack("imageFullFragment");
                    fragmentTransaction.commit();
                }
            });
            buttonNextImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(ElementDetailFragmentMarket.this.indexImage < ElementDetailFragmentMarket.this.market.getImageCommerce().size()) {
                        int i = ElementDetailFragmentMarket.this.indexImage;
                        Glide.with(ElementDetailFragmentMarket.this).load(ElementDetailFragmentMarket.this.market.getImageCommerce().get(i).getUrl()).into(imagesMarket);
                        ElementDetailFragmentMarket.this.indexImage++;
                    }
                    else{
                        Toast.makeText(getActivity(), "Plus d'images disponible !", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else{
            buttonNextImage.setVisibility(View.GONE);
        }
        return v;
    }

    public void setData(Market market)
    {
        this.market = market;
    }

}
