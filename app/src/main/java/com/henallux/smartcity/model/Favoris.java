package com.henallux.smartcity.model;

public class Favoris {
    private Integer idFavoris;
    private Integer idCommerce;
    private Integer idUser;

    public Favoris(Integer idCommerce, Integer idUser) {
        this.idCommerce = idCommerce;
        this.idUser = idUser;
    }

    public Integer getIdFavoris() {
        return idFavoris;
    }

    public Integer getIdCommerce() {
        return idCommerce;
    }

    public Integer getIdUser() {
        return idUser;
    }
}
