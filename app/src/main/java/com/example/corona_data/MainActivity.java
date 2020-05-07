package com.example.corona_data;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    public static  TextView place;
    public static TextView infected;
    public static TextView recovered;
    public static TextView deaths;
    public ImageView imageView;


    public static Spinner resourceSpinner;
    //Country List
    public static ArrayList<String> resourceSpinnerList = new ArrayList<>();
    public static ArrayAdapter<String> resourceSpinnerArrayAdapter;



    //Api + path  https://covid19.mathdro.id/api/countries/Bangladesh
    final String Base_URL ="https://covid19.mathdro.id/api";
    final String path ="/countries/";
    public String country ="https://covid19.mathdro.id/api";
    String state="";
    String global = "Global";
    String countryName ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resourceSpinner = findViewById(R.id.rSpinner);
        resourceSpinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, resourceSpinnerList);
        resourceSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        place = findViewById(R.id.place);
        imageView = findViewById(R.id.logo);
        infected = findViewById(R.id.infected);
        recovered = findViewById(R.id.recovered);
        deaths = findViewById(R.id.deaths);

        resourceSpinner.setAdapter(resourceSpinnerArrayAdapter);

        //Country list will be fetch in the spinner
        Country countryInSpinner = new Country();
        countryInSpinner.execute();

        resourceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String cName = adapterView.getItemAtPosition(i).toString();

                Toast.makeText(adapterView.getContext(), "Selected: " + cName,Toast.LENGTH_LONG).show();
                //Setting country name in UI
                place.setText(cName);

                String c ="https://covid19.mathdro.id/api";

                String isCountry = "/countries/";
                //Updating api link
                country= c+isCountry+cName;
                //Getting the selected country from the spinner
                countryName=cName;

                Log.d("C1",country);


                //Checking Is any country selected by user or selected Global
                if( countryName == global ){
                    country = "https://covid19.mathdro.id/api";
                    CountryData countryData = new CountryData();
                    countryData.execute(country);
                }else{
                    country = "https://covid19.mathdro.id/api/countries/"+countryName;
                    CountryData countryData = new CountryData();
                    countryData.execute(country);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });


    }
}
