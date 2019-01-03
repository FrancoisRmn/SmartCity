package com.henallux.smartcity.model;

import java.io.Serializable;
import java.util.Date;

public class Actualite implements Serializable {
    private Integer idActualite;
    private String libelle;
    private String texte;
    private Date date;
    private Integer idCommerce;
    private Integer idSiteTouristique;

    @Override
    public String toString() {
        return this.libelle + "\t" + this.texte + (this.date != null ?"("+this.date.toString()+")":"");
    }
}
