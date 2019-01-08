package com.henallux.smartcity.view;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.google.firebase.messaging.FirebaseMessaging;
import com.henallux.smartcity.applicationObject.Application;
import com.henallux.smartcity.R;
import com.henallux.smartcity.exception.CannotRetreiveUserIdException;
import com.henallux.smartcity.model.Actualite;
import com.henallux.smartcity.model.Favoris;
import com.henallux.smartcity.task.CreateFavorisAsyncTask;
import com.henallux.smartcity.task.DeleteFavorisAsyncTask;
import com.henallux.smartcity.utils.Constantes;
import com.henallux.smartcity.utils.Utils;
import com.henallux.smartcity.model.OpeningPeriod;
import com.henallux.smartcity.model.Restaurant;

import static com.henallux.smartcity.utils.Utils.getIdUser;

public class ElementDetailFragmentRestaurant extends Fragment {
    private Restaurant restaurant;
    private TextView titleFragmentDetail;
    private TextView descriptionElementDetailTitleRestaurant;
    private TextView email;
    private TextView cellPhone;
    private TextView phone;
    private TextView flagshipProduct;
    private TextView localisation;
    private Application application;
    private TextView scheduleRestaurant;
    private ImageView imagesRestaurant;
    private Button buttonNextImage;
    private int indexImage;
    private TextView textViewActualites;
    private SharedPreferences sharedPreferences;


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.tool_bar, menu);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if ((savedInstanceState != null) && (savedInstanceState.getSerializable("restaurant") != null)) {
            this.restaurant = (Restaurant)savedInstanceState.getSerializable("restaurant");
        }
        application = (Application)getActivity().getApplicationContext();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch(item.getItemId()){
            case R.id.itemCallGSMButton :
                if(this.restaurant.getNumeroGSM() != null)
                {
                    intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:+32" + this.restaurant.getNumeroGSM()));
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getActivity(), "Numéro de téléphone non disponible !", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.itemCallButton :
                if(this.restaurant.getNumeroGSM() != null)
                {
                    intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:+32" + this.restaurant.getNumeroFixe()));
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getActivity(), "Numéro de téléphone non disponible !", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.itemSendMail :
                if(this.restaurant.getAdresseMail() != null){
                    intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{this.restaurant.getAdresseMail()});
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
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(this.restaurant.getRue() + " " + this.restaurant.getNumero() + ", Namur"));
                // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                // Make the Intent explicit by setting the Google Maps package
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
                return true;
            case R.id.itemFavorite:
                if(application.isConnected()){
                    addCommerceToFav();
                    //TODO
                    //changer la couleur de l'icone et ajouté aux favoris de l'utilisateur
                }
                else{
                    Toast.makeText(getActivity(), Constantes.ADD_FAVORIS_MUST_BE_CONNECTED, Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.itemDeleteFavorite :
                if(!application.isConnected()){
                    Toast.makeText(getActivity(), Constantes.DELETE_FAVORIS_MUST_BE_CONNECTED, Toast.LENGTH_SHORT).show();
                }
                deleteCommerceFromFavoris();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void deleteCommerceFromFavoris(){
        try{
            int idUser = getIdUser(getActivity().getApplicationContext());
            new DeleteFavorisAsyncTask(getActivity(), new Favoris(this.restaurant.getIdCommerce(), idUser)).execute();
            //on se désabonne à googleFirebase pour recevoir les notifs quand depuis le backoffice une actualité d'un commerce favoris est créé
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(Utils.removeSpacesAndAccent(this.restaurant.getNomCommerce()));
            editor.commit();
            FirebaseMessaging.getInstance().unsubscribeFromTopic(this.restaurant.getNomCommerce());
        }
        catch(CannotRetreiveUserIdException e){
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        catch(Exception e){
            System.out.print(e.getMessage());
        }
    }

    private void addCommerceToFav() {
        try{
            int idUser = getIdUser(getActivity().getApplicationContext());
            new CreateFavorisAsyncTask(getActivity().getApplicationContext(), getActivity(), new Favoris(this.restaurant.getIdCommerce(), idUser)).execute();
            //on s'abonne à googleFirebase pour recevoir les notifs quand depuis le backoffice une actualité d'un commerce favoris est créé
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(Utils.removeSpacesAndAccent(this.restaurant.getNomCommerce()), true);
            editor.commit();
            if(application.isActivateNotifications())
                FirebaseMessaging.getInstance().subscribeToTopic(Utils.removeSpacesAndAccent(this.restaurant.getNomCommerce()));
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
            cellPhone.setText("GSM : +32" + this.restaurant.getNumeroGSM());
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

        scheduleRestaurant = v.findViewById(R.id.scheduleRestaurant);
        if(!this.restaurant.getOpeningPeriod().isEmpty()){
            String scheduleText = "";
            for(OpeningPeriod horaire: this.restaurant.getOpeningPeriod())
            {
                scheduleText += Utils.getDay(horaire.getJour()) + ": " + horaire.getHoraireDebut() + " à " + horaire.getHoraireFin() + "\n";
            }
            scheduleRestaurant.setText(scheduleText);
        }

        imagesRestaurant= v.findViewById(R.id.imageRestaurant);
        buttonNextImage = v.findViewById(R.id.buttonNextImageRestaurant);
        if(!this.restaurant.getImageCommerce().isEmpty()){
            Glide.with(this).load(this.restaurant.getImageCommerce().get(0).getUrl()).into(imagesRestaurant);
            this.indexImage = 0;
            //quand on click sur l'image on lance un nouveau fragment avec l'image affiché en grand
            imagesRestaurant.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = ElementDetailFragmentRestaurant.this.indexImage;
                    Fragment imageFullFragment = new ImageFullFragment();
                    ((ImageFullFragment) imageFullFragment).setData(ElementDetailFragmentRestaurant.this.restaurant.getImageCommerce().get(i).getUrl());
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, imageFullFragment);
                    fragmentTransaction.addToBackStack("imageFullFragment");
                    fragmentTransaction.commit();
                }
            });
            buttonNextImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(ElementDetailFragmentRestaurant.this.indexImage +1 < ElementDetailFragmentRestaurant.this.restaurant.getImageCommerce().size()) {
                        int i = ++ElementDetailFragmentRestaurant.this.indexImage;
                        Glide.with(ElementDetailFragmentRestaurant.this).load(ElementDetailFragmentRestaurant.this.restaurant.getImageCommerce().get(i).getUrl()).into(imagesRestaurant);
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
        if(!this.restaurant.getActualite().isEmpty()){
            String texteActu = "";
            int i = 1;
            for(Actualite actualite:this.restaurant.getActualite())
            {
                    texteActu += i + ". " + actualite.toString() + "\n";
                    i++;
            }
            this.textViewActualites.setText(texteActu);
        }
        return v;
    }

    public void setData(Restaurant restaurant)
    {
        this.restaurant= restaurant;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("restaurant", this.restaurant);
    }
}
