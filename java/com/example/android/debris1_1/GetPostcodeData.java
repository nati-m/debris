package com.example.android.debris1_1;

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

/**
 * Created by Paaat VII on 30/03/2018.
 */

public class GetPostcodeData extends AsyncTask<Void, Void, Void> {

    String jsonData = "";
    URL url;
    boolean complete;
    boolean failed;
    GetAndHandlePostcodeData getAndHandlePostcodeData;
    String distanceBetweenTwoPostcodes = "-999";

    public GetPostcodeData(URL url, GetAndHandlePostcodeData getAndHandlePostcodeData) {
        this.url = url;
        complete = false;
        failed = false;
        this.getAndHandlePostcodeData = getAndHandlePostcodeData;
    }

    public GetPostcodeData(URL url) {
        this.url = url;
        complete = false;
        failed = false;
    }

    public String getDistanceBetweenTwoPostcodesString(){
        return distanceBetweenTwoPostcodes;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        try {
            //URL LocalUrl = new URL("https://api.myjson.com/bins/pyu07");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                jsonData = jsonData + line;
            }
            JSONArray records;; //TODO GET RECORDS ARRAY
        } catch (MalformedURLException e) {
            e.printStackTrace();
            failed = true;
        } catch (IOException e) {
            e.printStackTrace();
            failed = true;
        }


//          THIS IS ALL 2 POSTCODE STUFF WHICH I'M NOT TEST RIGHT NOW
//        try {
//            JSONArray jsonArray = new JSONArray(jsonData);
//            for(int i = 0; i < jsonArray.length(); i++){
//                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
//                //Object geoDistance = jsonObject.get("pz_geodistancetohomepostcode");
//                distanceBetweenTwoPostcodes = jsonObject.getString("pz_geodistancetohomepostcode");
//                }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//            failed = true;
//        }



        complete = true;
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        HTTPTestActivity.textView.setText(jsonData);
        //getAndHandlePostcodeData.setDistanceBetweenTwoPostcodes(distanceBetweenTwoPostcodes);
        //MainActivity.testTextView.setText(jsonData);
        //MainActivity.getTextViewForJSON().setText(this.jsonData);
    }

    protected String getJsonData(){
        return jsonData;
    }

    protected boolean isComplete(){
        return complete;
    }
}
