package com.henallux.smartcity.model;

import java.io.Serializable;

public class OpeningPeriod implements Serializable {
    private int idHoraire;
    private String horaireDebut;
    private String horaireFin;
    private int idCommerce;
    private int jour;

    public int getIdHoraire() {
        return idHoraire;
    }

    public String getHoraireDebut() {
        return horaireDebut;
    }

    public String getHoraireFin() {
        return horaireFin;
    }

    public int getIdCommerce() {
        return idCommerce;
    }

    public int getJour() {
        return jour;
    }
}
