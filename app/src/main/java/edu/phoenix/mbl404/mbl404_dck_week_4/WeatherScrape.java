package edu.phoenix.mbl404.mbl404_dck_week_4;

import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherScrape extends AsyncTask<Void,Void,Void> {

    //Class variables

    private String weatherURL, dataParsed;
    private String data = "";

    //Constructor (pass in API URL)

    public WeatherScrape (String wURL){
        this.weatherURL = wURL;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            //Prepares the http connection and reads the returning result, in this case JSON.
            URL url = new URL(this.weatherURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }

            //Stuff JSON into a java JSON object for parsing

            JSONObject rawData = new JSONObject(data);

            //Split off two elements of the object for parsing.

            JSONArray conditions = (JSONArray) rawData.get("weather");
            JSONObject temperature = (JSONObject) rawData.get("main");

            //Create a string with the data found in the main object and the separate elements.

            dataParsed = "Location:  " + rawData.get("name") + "\n" +
                    "Conditions:  " + conditions.getJSONObject(0).get("main") + "\n" +
                    "Temperature(F):  " + temperature.get("temp");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        //Sets the data display to the formatted data.

        MainActivity.dataDisplay.setText(this.dataParsed);
    }
}