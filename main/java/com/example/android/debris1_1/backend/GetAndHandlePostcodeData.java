package com.example.android.debris1_1.backend;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Paaat VII on 30/03/2018.
 */

public class GetAndHandlePostcodeData {

    String postcode1;
    String postcode2;
    URL singlePostcodeURL;
    URL twoPostcodeURL;
    String distanceBetweenTwoPostcodes;

    public GetAndHandlePostcodeData(String postcode){
        postcode1 = postcode;
        String urlString = "http://www.simplylookupadmin.co.uk/JSONservice/JSONSearchForAddress.aspx?datakey=W_B7C4975860B64332998F4398F8DBA0&postcode=" + postcode;
        try {
            singlePostcodeURL = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        distanceBetweenTwoPostcodes = "didn't work";
    }

    public GetAndHandlePostcodeData(String postcode1, String postcode2){
        this.postcode1 = postcode1;
        this.postcode2 = postcode2;
        String urlString = "http://www.simplylookupadmin.co.uk/JSONservice/JSONSearchForPostZonData.aspx?datakey=W_B7C4975860B64332998F4398F8DBA0&postcode=" + postcode1 + "&homepostcode=" + postcode2;
        try {
            twoPostcodeURL = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void setSinglePostcodeURL(URL url){
        singlePostcodeURL = url;
    }

    public void setTwoPostcodeURL(URL url){
        twoPostcodeURL = url;
    }

    public String getSingleAddressJSONDataAsString() {
        GetPostcodeData getPostcodeData = new GetPostcodeData(singlePostcodeURL, this);
        String toReturn = "no";
        getPostcodeData.execute();

        while(!getPostcodeData.isComplete()) {
            if(getPostcodeData.failed){
                return "Attempt Failed";
            }
        }
        if(getPostcodeData.isComplete()) {
            toReturn = getPostcodeData.getJsonData();
        }
        return toReturn;
    }

    public String getDistanceBetweenTwoPostcodesAsAString()  {
       GetPostcodeData getPostcodeData = new GetPostcodeData(twoPostcodeURL, this);
        getPostcodeData.execute();

        while(!getPostcodeData.isComplete()){
            if(getPostcodeData.failed){
                return "Attempt Failed";
            }
        }
        if(getPostcodeData.isComplete()){
            return distanceBetweenTwoPostcodes;
        }

        return "Attempt Failed as wasn't complete";



    }

    public void setDistanceBetweenTwoPostcodes(String distanceBetweenTwoPostcodes){
        this.distanceBetweenTwoPostcodes = distanceBetweenTwoPostcodes;
    }


}
