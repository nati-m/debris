package com.example.android.debris1_1.backend;

import java.util.Date;

/**
 * Created by Paaat VII on 12/03/2018.
 */

public class Order {

    //private static final Map<OrderID, Order> ORDERS_AND_THEIR_IDS = new HashMap<>();
    //Now we're using databases this won't work but it's here as a reminder


    private String address;
    private Date date;
    private PublicUser user;
    //private Company skipCo;
    //private Company haulCo;
    //private Company tipCo;
    //private boolean permitRequired;
    //private Permit permit;
    //private Council localCouncil;

    public Order (PublicUser user){
        this.user = user;
    }



}
