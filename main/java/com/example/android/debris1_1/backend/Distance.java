package com.example.android.debris1_1.backend;

/**
 * Created by Paaat VII on 21/03/2018.
 */

public class Distance {

    private String distanceAsString;
    private int distanceAsInt;

    public Distance(){
        distanceAsInt = -404; //An error value
    }

    public String getDistanceAsString(){
        return distanceAsString;
    }

    public int getDistanceAsInt(){
        if (distanceAsString != null) {
            this.distanceAsInt = Integer.parseInt(distanceAsString);
        }

        return distanceAsInt;
    }

    public void setDistanceAsString(String distanceAsString){
        this.distanceAsString = distanceAsString;
    }

}
