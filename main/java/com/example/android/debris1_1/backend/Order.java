package com.example.android.debris1_1.backend;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Paaat VII on 12/03/2018.
 */

public class Order {

    //private static final Map<OrderID, Order> ORDERS_AND_THEIR_IDS = new HashMap<>();
    //Now we're using databases this won't work but it's here as a reminder


    private String addressLine1;
    private String addressLine2;
    private String postcode;
    //private Date date;
    private Calendar dateOfSkipArrival;
    private PublicUser user;
    private double price;
    private Company skipCo;
    private boolean permitRequired;
    //private Permit permit;
    private Council localCouncil;
    private ArrayList<Skip> skipsOrdered;


    private String tempDateString;
    //private Company haulCo;
    //private Company tipCo;

    public Order (PublicUser user){
        this.user = user;
    }

//    //Temp constructor including tempDateString
//    public Order (PublicUser user, String addressLine1, String addressLine2, String postcode){
//        this.user = user;
//        this.addressLine1 = addressLine1;
//        this.addressLine2 = addressLine2; //This may be "" but won't be null.
//        this.postcode = postcode;
//        //this.tempDateString = tempDateString;
//
//    }

    public Order (PublicUser user, String addressLine1, String addressLine2, String postcode, ArrayList<Skip> skipsOrdered, Calendar calendarWithDateOfSkipArrival){
        this.user = user;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2; //This may be "" but won't be null.
        this.postcode = postcode;
        this.skipsOrdered = skipsOrdered;
        dateOfSkipArrival = calendarWithDateOfSkipArrival;
        //this.tempDateString = tempDateString;

    }



    public void setChosenSkipCo(Company company){
        skipCo = company;
    }

    public void setifPermitRequired(boolean permitRequired){
        this.permitRequired = permitRequired;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public void setDateOfSkipArrival(Calendar newDateOfSkipArrival){
        dateOfSkipArrival = newDateOfSkipArrival;
    }


    public String getAddressLine1(){
        return addressLine1;
    }

    public String getAddressLine2(){
        return addressLine2;
    }

    public String getPostCode(){
        return postcode;
    }

    public ArrayList<Skip> getSkipsOrderedArrayList(){
        return skipsOrdered;
    }

    public Calendar getDateOfSkipArrival(){
        return dateOfSkipArrival;
    }

    public String getDateOfSkipArrivalAsAString(){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
        String toReturn = simpleDateFormat.format(dateOfSkipArrival.getTime());

        return toReturn;
    }

    public Company getSkipCo(){
        return skipCo;
    }

    public double getPrice(){
        return price;
    }


}
