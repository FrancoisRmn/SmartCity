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
    private Integer numeroGSM;
    private Integer numeroFixe ;
    private String adresseMail;
    private String urlPageFacebook;
    private Integer longitude;
    private Integer latitude;
    private Integer idCategorie;
    private Integer idPersonne;
    private ArrayList<OpeningPeriod> openingPeriod;
    private ArrayList<imageCommerce> imageCommerce;

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

    public Integer getIdCommerce() {
        return idCommerce;
    }

    public void setIdCommerce(Integer idCommerce) {
        this.idCommerce = idCommerce;
    }

    public String getNomCommerce() {
        return nomCommerce;
    }

    public void setNomCommerce(String nomCommerce) {
        this.nomCommerce = nomCommerce;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public List<String> getMoyensPayements() {
        return moyensPayements;
    }

    public void setMoyensPayements(List<String> moyensPayements) {
        this.moyensPayements = moyensPayements;
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

    public void setProduitPhare(String produitPhare) {
        this.produitPhare = produitPhare;
    }

    public String getParcoursProduitPhare() {
        return parcoursProduitPhare;
    }

    public void setParcoursProduitPhare(String parcoursProduitPhare) {
        this.parcoursProduitPhare = parcoursProduitPhare;
    }

    public Integer getNumeroGSM() {
        return numeroGSM;
    }

    public void setNumeroGSM(Integer numeroGSM) {
        this.numeroGSM = numeroGSM;
    }

    public Integer getNumeroFixe() {
        return numeroFixe;
    }

    public void setNumeroFixe(int numeroFixe) {
        this.numeroFixe = numeroFixe;
    }

    public String getAdresseMail() {
        return adresseMail;
    }

    public void setAdresseMail(String mail) {
        this.adresseMail = adresseMail;
    }

    public String getUrlPageFacebook() {
        return urlPageFacebook;
    }

    public void setUrlPageFacebook(String urlPageFacebook) {
        this.urlPageFacebook = urlPageFacebook;
    }

    public Integer getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public Integer getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public Integer getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public Integer getIdPersonne() {
        return idPersonne;
    }

    public void setIdPersonne(int idPersonne) {
        this.idPersonne = idPersonne;
    }
}
