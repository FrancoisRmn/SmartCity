package com.henallux.smartcity.model;

import java.util.ArrayList;
import java.util.List;

public class Commerce {
    private Integer idCommerce;
    private String nomCommerce;
    private String rue;
    private Integer numero;
    private List<String> moyensPayements;
    private String description;
    private String produitPhare;
    private String parcoursProduitPhare;
    private Integer numeroGsm;
    private Integer numeroFixe ;
    private String adresseMail;
    private String urlPageFacebook;
    private Integer longitude;
    private Integer latitude;
    private Integer idCategorie;
    private Integer idPersonne;
    private ArrayList<OpeningPeriod> openingPeriod;
    private ArrayList<imageCommerce> imageCommerce;
    private ArrayList<Actualite> actualite;


    public Integer getNumeroGsm() {
        return numeroGsm;
    }

    public ArrayList<Actualite> getActualite() {
        return actualite;
    }


    public ArrayList<com.henallux.smartcity.model.imageCommerce> getImageCommerce() {
        return imageCommerce;
    }

    public ArrayList<OpeningPeriod> getOpeningPeriod() {
        return openingPeriod;
    }


    @Override
    public String toString() {
        return nomCommerce;
    }



    public String getNomCommerce() {
        return nomCommerce;
    }

    public String getRue() {
        return rue;
    }


    public Integer getNumero() {
        return numero;
    }


    public List<String> getMoyensPayements() {
        return moyensPayements;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProduitPhare() {
        return produitPhare;
    }

    public Integer getNumeroGSM() {
        return numeroGsm;
    }


    public Integer getNumeroFixe() {
        return numeroFixe;
    }


    public String getAdresseMail() {
        return adresseMail;
    }


    public String getUrlPageFacebook() {
        return urlPageFacebook;
    }


    public Integer getLongitude() {
        return longitude;
    }


    public Integer getLatitude() {
        return latitude;
    }


    public Integer getIdCategorie() {
        return idCategorie;
    }


    public Integer getIdPersonne() {
        return idPersonne;
    }
}
