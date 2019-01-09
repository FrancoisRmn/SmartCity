package com.henallux.smartcity.utils;

import android.content.Context;
import android.widget.EditText;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.henallux.smartcity.applicationObject.Application;
import com.henallux.smartcity.exception.CannotRetreiveUserIdException;
import com.henallux.smartcity.model.Payload;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


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
            default: day=Constantes.NO_DAY_UNAVAILABLE;
        }
        return day;
    }
    public static String getErrorMessage(int statusCode)
    {
        String message;
        switch (statusCode)
        {
            case 401: message = Constantes.SESSION_EXPIRED;
                break;
            case 403 : message = Constantes.CONTENT_UNAVAILABLE;
             default: message = Constantes.ERROR_MESSAGE;
        }
        return message + " (status code :"+statusCode+")";
    }

    public static class DateDeserializer implements JsonDeserializer<Date> {

        @Override
        public Date deserialize(JsonElement element, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
            String date = element.getAsString();

            SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

            try {
                return formatter.parse(date);
            } catch (ParseException e) {

                return null;
            }
        }
    }

    public static int getIdUser (Context context) throws Exception
    {
        Application application = (Application)context;
        int idUser =0;
        String payload = JWTUtils.decoded(application.getToken());
        Payload payloadModel;
        if(!payload.contains("uid")){
            throw new CannotRetreiveUserIdException(Constantes.ERROR_MESSAGE_USERID);
        }
        JSONObject jsonPayload = new JSONObject(payload);
        payloadModel = new Payload(Integer.parseInt(jsonPayload.getString("uid")));
        idUser = payloadModel.getUid();
        return idUser;
    }

    public static String stripAccents(String s)
    {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }

    public static String removeSpacesAndAccent(String s)
    {
        s = stripAccents(s);
        s = s.replaceAll("\\s+","");
        return s;
    }

    public static boolean verificationEmail(String email) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        return (email.matches(regex));
    }
}
