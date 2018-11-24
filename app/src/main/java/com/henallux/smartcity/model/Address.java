package com.henallux.smartcity.model;

public class Address {
private String rue;
private String codePostal;
private int numero;
    public Address(){}

    public Address(String rue, String codePostal, int numero) {
        this.rue = rue;
        this.codePostal = codePostal;
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Address{" +
                "rue='" + rue + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", numero=" + numero +
                '}';
    }
}
