package com.example.corona_data;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class CountryData extends AsyncTask<String,Void,String> {

    String result="";
    String infected_data,recovered_data,deaths_data;

    @Override
    protected String doInBackground(String... urls) {

        URL url;

        HttpsURLConnection myConnection = null;
        try{
            url = new URL(urls[0]);
            myConnection = (HttpsURLConnection) url.openConnection();
            InputStream in = myConnection.getInputStream();
            InputStreamReader myStreamReader = new InputStreamReader(in);

            int data = myStreamReader.read();

            while(data!= -1){
                char currentCharacter = (char)data;
                result = result + currentCharacter;
                data = myStreamReader.read();
            }



        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try{

            JSONObject myObject = new JSONObject(result);

            JSONObject confirmed = new JSONObject(myObject.getString("confirmed"));
            JSONObject recovered = new JSONObject(myObject.getString("recovered"));
            JSONObject deaths = new JSONObject(myObject.getString("deaths"));

            infected_data = confirmed.getString("value");
            recovered_data = recovered.getString("value");
            deaths_data = deaths.getString("value");

            MainActivity.infected.setText("Infected : "+infected_data);
            MainActivity.recovered.setText("Recovered "+recovered_data);
            MainActivity.deaths.setText("Deaths "+deaths_data);



        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
