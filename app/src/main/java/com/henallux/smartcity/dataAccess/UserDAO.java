package com.henallux.smartcity.dataAccess;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class UserDAO {
    private AsyncTask getUserAsyncTask;

    public boolean login() throws Exception{
        Log.i("Login","Début de login dans UserDAO");
        getUserAsyncTask = new GetUserAsyncTask().execute("");
        return true;
    }

    public static String makePostRequest(String stringUrl, String payload) throws IOException {
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
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            while((line = br.readLine()) != null){
                jsonString.append(line);
            }
            br.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        uc.disconnect();
        Log.i("Login",jsonString.toString());
        return jsonString.toString();
    }

    private class GetUserAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                String response = makePostRequest("https://sc-nconnect.azurewebsites.net/api/jwt",
                        "{\n" +
                                "\t\"Username\":\"janedoe\",\n" +
                                "\t\"Password\":\"123\"\n" +
                                "}");
                return response;
            } catch (IOException ex) {
                ex.printStackTrace();
                return "";
            }
        }

    }
}
