package com.henallux.smartcity.model;

import java.util.ArrayList;
import java.util.List;

public class Commerce {
    private int idCommerce;
    private String nomCommerce;
    private String rue;
    private int numero;
    private List<String> moyensPayements;
    private String description;
    private String produitPhare;
    private String parcoursProduitPhare;
    private int numeroGSM;
    private int numeroFixe ;
    private String adresseMail;
    private String urlPageFacebook;
    private int longitude;
    private int latitude;
    private int idCategorie;
    private int idPersonne;
    //private int rowVersion;
    private ArrayList<OpeningPeriod> openingPeriod;

    @Override
    public String toString() {
        return nomCommerce;
    }

    public int getIdCommerce() {
        return idCommerce;
    }

    public void setIdCommerce(int idCommerce) {
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

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
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

    public int getNumeroGSM() {
        return numeroGSM;
    }

    public void setNumeroGSM(int numeroGSM) {
        this.numeroGSM = numeroGSM;
    }

    public int getNumeroFixe() {
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

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public int getIdPersonne() {
        return idPersonne;
    }

    public void setIdPersonne(int idPersonne) {
        this.idPersonne = idPersonne;
    }
}
