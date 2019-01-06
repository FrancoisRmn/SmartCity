package com.henallux.smartcity.utils;

import android.widget.EditText;


public class Utils {
    public static boolean isEmpty(EditText editText)
    {
        return editText.getText().toString().trim().length() == 0;
    }

    public static String getDay(int dayNumber)
    {
        String day="";
        switch (dayNumber)
        {
            case 0 : day="Dimanche";
                break;
            case 1 : day="Lundi";
                break;
            case 2 : day="Mardi";
                break;
            case 3 : day="Mercredi";
                break;
            case 4 : day="Jeudi";
                break;
            case 5 : day="Vendredi";
                break;
            case 6 : day="Samedi";
                break;
            default: day="Pas de jour disponible";
        }
        return day;
    }
    public static String getErrorMessage(int statusCode)
    {
        String message="";
        switch (statusCode)
        {
            case 401: message = "Votre session est expiré";
                break;
            case 403 : message = "Ce contenu ne vous est pas accessible";
             default: message = "Erreur inconnue";
        }
        return message + " (status code :"+statusCode+")";
    }
}
