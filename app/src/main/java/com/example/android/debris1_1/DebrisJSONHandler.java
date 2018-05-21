package com.example.android.debris1_1;

import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DebrisJSONHandler {

    public static final DebrisJSONHandler INSTANCE = new DebrisJSONHandler();

    private DebrisJSONHandler(){}

    protected boolean isPostcodeValid(String postcode) throws IOException {

        String urlString = "https://api.postcodes.io/postcodes/" + postcode + "/validate/";
        URL url = new URL(urlString);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


        String jsonData = "";
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        while (line != null) {
            line = bufferedReader.readLine();
            jsonData = jsonData + line;
        }

        //Object objectToBeTurnedIntoJSONObject = new JSONParser





        return false;
    }
}
