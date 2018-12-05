package com.henallux.smartcity.model;

import java.util.List;

public class Bar {
    private String nomCommerce;
    private String rue;
    private int numero;
    private List<String> moyensPayements;
    private String description;
    private String produitPhare;
    private String parcoursProduitPhare;
    private int numGSM;
    private int numTel;
    private String mail;
    private String urlPageFacebook;
    private int coordGPS;
    private List<Promotion> promotions;

    public Bar() { }
    public Bar(String nomCommerce) {
        this.nomCommerce = nomCommerce;
    }

    public String toString() {
        return this.nomCommerce;
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

    public int getNumGSM() {
        return numGSM;
    }

    public void setNumGSM(int numGSM) {
        this.numGSM = numGSM;
    }

    public int getNumTel() {
        return numTel;
    }

    public void setNumTel(int numTel) {
        this.numTel = numTel;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUrlPageFacebook() {
        return urlPageFacebook;
    }

    public void setUrlPageFacebook(String urlPageFacebook) {
        this.urlPageFacebook = urlPageFacebook;
    }

    public int getCoordGPS() {
        return coordGPS;
    }

    public void setCoordGPS(int coordGPS) {
        this.coordGPS = coordGPS;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }
}