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
import com.henallux.smartcity.exception.CannotRetreiveUserIdException;
import com.henallux.smartcity.model.Actualite;
import com.henallux.smartcity.model.Favoris;
import com.henallux.smartcity.model.Payload;
import com.henallux.smartcity.task.CreateFavorisAsyncTask;
import com.henallux.smartcity.utils.JWTUtils;
import com.henallux.smartcity.utils.Utils;
import com.henallux.smartcity.model.Bar;
import com.henallux.smartcity.model.OpeningPeriod;

import org.json.JSONObject;

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
    private Button buttonNextImage;
    private int indexImage;
    private TextView textViewActualites;


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.tool_bar, menu);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if ((savedInstanceState != null) && (savedInstanceState.getSerializable("bar") != null)) {
            this.bar = (Bar)savedInstanceState.getSerializable("bar");
        }
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
                    addCommerceToFav();
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

    private void addCommerceToFav() {
        try{
            Application application = (Application)getActivity().getApplicationContext();
            //int idUser = Utils.getUserId(application.getToken());
            String payload = JWTUtils.decoded(application.getToken());
            int idUser =0;
            Payload payloadModel;
            if(payload.contains("uid")){
                JSONObject jsonPayload = new JSONObject(payload);
                payloadModel = new Payload(Integer.parseInt(jsonPayload.getString("uid")));
                idUser = payloadModel.getUid();
            }
            else{
                throw new CannotRetreiveUserIdException("Impossible de récupérer l'identidiant de l'utilisateur !");
            }
            new CreateFavorisAsyncTask(applicationContext, getActivity(), new Favoris(this.bar.getIdCommerce(), idUser)).execute();
        }
        catch(CannotRetreiveUserIdException e){
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        catch(Exception e){
            System.out.print(e.getMessage());
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
        if(!this.bar.getOpeningPeriod().isEmpty()){
            String scheduleText = "";
            for(OpeningPeriod horaire: this.bar.getOpeningPeriod())
            {
                scheduleText += Utils.getDay(horaire.getJour()) + ": " + horaire.getHoraireDebut() + " à " + horaire.getHoraireFin() + "\n";
            }
            scheduleBar.setText(scheduleText);
        }

        imagesBar= v.findViewById(R.id.imageBar);
        buttonNextImage = v.findViewById(R.id.buttonNextImageBar);
        if(!this.bar.getImageCommerce().isEmpty()){
            Glide.with(this).load(this.bar.getImageCommerce().get(0).getUrl()).into(imagesBar);
            this.indexImage = 1;
            //quand on click sur l'image on lance un nouveau fragment avec l'image affiché en grand
            imagesBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment imageFullFragment = new ImageFullFragment();
                    ((ImageFullFragment) imageFullFragment).setData(ElementDetailFragmentBar.this.bar.getImageCommerce().get(0).getUrl());
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, imageFullFragment);
                    fragmentTransaction.addToBackStack("imageFullFragment");
                    fragmentTransaction.commit();
                }
            });
            buttonNextImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(ElementDetailFragmentBar.this.indexImage < ElementDetailFragmentBar.this.bar.getImageCommerce().size()) {
                        int i = ElementDetailFragmentBar.this.indexImage;
                        Glide.with(ElementDetailFragmentBar.this).load(ElementDetailFragmentBar.this.bar.getImageCommerce().get(i).getUrl()).into(imagesBar);
                        ElementDetailFragmentBar.this.indexImage++;
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
        //actualites
        this.textViewActualites = v.findViewById(R.id.textViewActualites);
        if(!this.bar.getActualite().isEmpty()){
            String texteActu = "";
            int i = 1;
            for(Actualite actualite:this.bar.getActualite())
            {
                texteActu += i + ". " + actualite.toString() + "\n";
                i++;
            }
            this.textViewActualites.setText(texteActu);
        }
        return v;
    }

    public void setData(Bar bar)
    {
        this.bar= bar;
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("bar", this.bar);
    }
}
