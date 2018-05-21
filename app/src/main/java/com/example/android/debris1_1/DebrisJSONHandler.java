package com.example.android.debris1_1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DebrisJSONHandler {

    //https://www.geeksforgeeks.org/parse-json-java/

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

        JSONParser parser = new JSONParser();

        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) parser.parse(jsonData);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        boolean toReturn;
        try {
            toReturn = jsonObject.getBoolean("result");
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }


        return toReturn;
    }
}
