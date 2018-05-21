package com.example.android.debris1_1;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Paaat VII on 12/03/2018.
 */

public class Company {

    private String name;
    private String postcode;
    private String email;
    private String phoneNumber;
    private double rating = -1; //An error value to show no rating has been set


    public Company(){} //For firebase to function

    public Company (String name, String postcode){
        this.name = name;
        this.postcode = postcode;

    }

    public Company (String name, String postcode, String email, String phoneNumber){
        this.name = name;
        this.postcode = postcode;
        this.email = email;
        this.phoneNumber = phoneNumber;
        rating = 2.5;

    }

    public Company (String name, String postcode, String email, String phoneNumber, double rating){
        this.name = name;
        this.postcode = postcode;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.rating = rating;

    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }


    protected void setRating(double rating){
        this.rating = rating;
    }

    protected double getRating(){
        return rating;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
