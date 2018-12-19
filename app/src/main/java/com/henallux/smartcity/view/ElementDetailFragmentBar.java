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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.henallux.smartcity.ApplicationObject.Application;
import com.henallux.smartcity.R;
import com.henallux.smartcity.Utils.Utils;
import com.henallux.smartcity.model.Bar;
import com.henallux.smartcity.model.OpeningPeriod;

public class ElementDetailFragmentBar extends Fragment {
    private Bar bar;
    private TextView titleFragmentDetail;
    private TextView descriptionElementDetailBar;
    private TextView email;
    private TextView cellPhone;
    private TextView phone;
    private TextView flagshipProduct;
    private TextView localisation;
    private Application applicationContext;
    private TextView scheduleBar;
    private ImageView imagesBar;

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
                if(this.bar.getNumeroGSM() != null)
                {
                    intent= new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:+32" + this.bar.getNumeroGSM()));
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getActivity(), "Numéro de téléphone non disponible !", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.itemCallButton :
                if(this.bar.getNumeroGSM() != null)
                {
                    intent= new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:+32" + this.bar.getNumeroFixe()));
                    startActivity(intent);

                }
                else{
                    Toast.makeText(getActivity(), "Numéro de téléphone non disponible !", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.itemSendMail :
                if(this.bar.getAdresseMail() != null){
                    intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{this.bar.getAdresseMail()});
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
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(this.bar.getRue() + " " + this.bar.getNumero() + ", Namur"));
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
        if(this.bar.getNumeroGSM() != null){
            cellPhone.setText("GSM : " + this.bar.getNumeroGSM());
        }
        phone = v.findViewById(R.id.phoneBar);
        if(this.bar.getNumeroFixe() != null){
            phone.setText("Fixe : " + this.bar.getNumeroFixe());
        }
        flagshipProduct = v.findViewById(R.id.flagshipProduct);
        if(this.bar.getProduitPhare() != null){
            flagshipProduct.setText(this.bar.getProduitPhare());
        }
        localisation = v.findViewById(R.id.localisation);
        if(this.bar.getRue() != null){
            String numero= (this.bar.getNumero() != null) ? this.bar.getNumero().toString() : "";
            this.localisation.setText(this.bar.getRue() + " " + numero);
        }
        scheduleBar = v.findViewById(R.id.scheduleBar);
        if(this.bar.getOpeningPeriod() != null){
            String scheduleText = "";
            for(OpeningPeriod horaire: this.bar.getOpeningPeriod())
            {
                scheduleText += Utils.getDay(horaire.getJour()) + ": " + horaire.getHoraireDebut() + " à " + horaire.getHoraireFin() + "\n";
            }
            scheduleBar.setText(scheduleText);
        }

        imagesBar= v.findViewById(R.id.imageBar);
        if(!this.bar.getImageCommerce().isEmpty()){
            Glide.with(this).load(this.bar.getImageCommerce().get(0).getUrl()).into(imagesBar);
        }
        return v;
    }

    public void setData(Bar bar)
    {
        this.bar= bar;
    }
}
