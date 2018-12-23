package com.henallux.smartcity.dataAccess;

import android.util.Log;

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
        // try {
        BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
        while((line = br.readLine()) != null){
            jsonString.append(line);
        }
        br.close();
        //} catch (Exception ex) {
        //  ex.printStackTrace();
        //}
        uc.disconnect();
        Log.i("Login",jsonString.toString());
        //return jsonString.toString();
        return JsonToStringToken(jsonString.toString());
    }

    public static String JsonToStringToken(String stringJson) throws Exception
    {
        String token="";
        JSONObject jsonToken = new JSONObject(stringJson);
        token = jsonToken.getString("access_token");
        return token;
    }
}
