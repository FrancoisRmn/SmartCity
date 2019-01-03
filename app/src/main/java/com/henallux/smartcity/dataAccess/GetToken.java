package com.henallux.smartcity.dataAccess;

import android.util.Log;

import com.henallux.smartcity.exception.ImpossibleToFetchBarsException;
import com.henallux.smartcity.exception.ImpossibleToFetchToken;
import com.henallux.smartcity.utils.Constantes;
import com.henallux.smartcity.utils.Utils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetToken {
    public static String makePostRequest(String stringUrl, String payload) throws Exception {
        URL url = new URL(stringUrl);
        HttpURLConnection uc = (HttpURLConnection) url.openConnection();
        String line;
        StringBuffer jsonString = new StringBuffer();

        uc.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        uc.setRequestMethod("POST");
        uc.setDoInput(true);
        uc.setInstanceFollowRedirects(false);
        uc.connect();
        OutputStreamWriter writer = new OutputStreamWriter(uc.getOutputStream(), "UTF-8");
        writer.write(payload);
        writer.close();
        int responseCode = uc.getResponseCode();
        if(responseCode == HttpURLConnection.HTTP_OK)
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            while((line = br.readLine()) != null){
                jsonString.append(line);
            }
            br.close();
            uc.disconnect();
            return JsonToStringToken(jsonString.toString());
        }
        else
        {
            throw new ImpossibleToFetchToken(Constantes.ERROR_MESSAGE_TOKEN + ", " + Utils.getErrorMessage(responseCode));
        }

    }

    public static String JsonToStringToken(String stringJson) throws Exception
    {
        String token="";
        JSONObject jsonToken = new JSONObject(stringJson);
        token = jsonToken.getString("access_token");
        return token;
    }
}
