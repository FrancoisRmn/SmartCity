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
            case 1 : day="Lundi";
            case 2 : day="Mardi";
            case 3 : day="Mercredi";
            case 4 : day="Jeudi";
            case 5 : day="Vendredi";
            case 6 : day="Samedi";
            default: day="Dimanche";
        }
        return day;
    }
}
