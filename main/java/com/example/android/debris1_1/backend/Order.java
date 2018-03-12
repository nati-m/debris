package com.example.android.debris1_1.backend;

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
    private Date date;
    private PublicUser user;
    //private Company skipCo;
    //private Company haulCo;
    //private Company tipCo;
    //private boolean permitRequired;
    //private Permit permit;
    private Council localCouncil;


    private String tempDateString;


    public Order (PublicUser user){
        this.user = user;
    }

    //Temp constructor including tempDateString
    public Order (PublicUser user, String addressLine1, String addressLine2, String postcode, String tempDateString){
        this.user = user;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2; //This may be "" but won't be null.
        this.postcode = postcode;
        this.tempDateString = tempDateString;
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

    public String getTempDateString(){
        return tempDateString;
    }

}
