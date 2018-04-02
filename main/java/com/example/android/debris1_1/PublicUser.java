package com.example.android.debris1_1;

import java.util.ArrayList;

/**
 * Created by Paaat VII on 12/03/2018.
 */

public class PublicUser {

    private String name;
    private String address;
    private String postCode;
    private String email;

    private ArrayList<Order> thisUsersOrders;

    public PublicUser(String name, String postCode, String email){
        this.name = name;
        this.postCode = postCode;
        this.email = email;
    }

    public PublicUser(String name, String postCode, String email, ArrayList<Order> thisUsersOrders){
        this.name = name;
        this.postCode = postCode;
        this.email = email;
        this.thisUsersOrders = thisUsersOrders;

    }

    public PublicUser(String name, String address, String postCode, String email) {
        this.name = name;
        this.address = address;
        this.postCode = postCode;
        this.email = email;
    }





    public String getName(){
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getEmail(){
        return email;
    }
    public ArrayList<Order> getThisUsersOrders() {
        return thisUsersOrders;
    }

    public void setThisUsersOrders(ArrayList<Order> thisUsersOrders) {
        this.thisUsersOrders = thisUsersOrders;
    }


}
