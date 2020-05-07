package com.example.corona_data;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import static com.example.corona_data.MainActivity.resourceSpinner;
import static com.example.corona_data.MainActivity.resourceSpinnerArrayAdapter;
import static com.example.corona_data.MainActivity.resourceSpinnerList;

public class Country extends AsyncTask<Void,Void,Void> {

    String result="";
    String countries ="https://covid19.mathdro.id/api/countries";

    @Override
    protected Void doInBackground(Void... urls) {

        URL url;



        HttpsURLConnection myConnection = null;
        try{
            url = new URL(countries);
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
    protected void onPostExecute(Void s) {
        super.onPostExecute(s);

        try{

            JSONObject myObject = new JSONObject(result);

            JSONArray countryArray = new JSONArray(myObject.getString("countries"));

            Log.d("CountryTest",countryArray.toString());

            resourceSpinnerList.add("Global");

            for (int i = 0; i <countryArray.length() ; i++) {
                JSONObject countryName =  (JSONObject)countryArray.get(i);
                resourceSpinnerList.add((String) countryName.get("name"));
                // Log.d("CountryName",countryName.get("name").toString()+"\n");
            }

            resourceSpinner.setAdapter(resourceSpinnerArrayAdapter);

           // Log.d("CountryList",resourceSpinnerList.toString()+"\n");



        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
