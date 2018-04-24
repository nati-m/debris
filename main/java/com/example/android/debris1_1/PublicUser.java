package com.example.android.debris1_1;

import java.util.ArrayList;

/**
 * Created by Paaat VII on 12/03/2018.
 */

public class PublicUser {

    private String name;
    private String postCode;
    private String email;
    private String firebaseUid;
    private boolean firebaseDatabaseFilledOut;
    private ArrayList<Order> thisUsersOrders;

    public PublicUser(){} //A blank constructor is required to let there be a Firebase database of this object

    public PublicUser(String name, String postCode, String email, ArrayList<Order> thisUsersOrders){
        this.name = name;
        this.postCode = postCode;
        this.email = email;
        this.thisUsersOrders = thisUsersOrders;
        firebaseDatabaseFilledOut = false;
    }

    public PublicUser(String name, String postCode, String email, ArrayList<Order> thisUsersOrders, boolean firebaseDatabaseFilledOut, String firebaseUid){
        this.name = name;
        this.postCode = postCode;
        this.email = email;
        this.thisUsersOrders = thisUsersOrders;
        this.firebaseDatabaseFilledOut = firebaseDatabaseFilledOut;
        this.firebaseUid = firebaseUid;
    }






    public String getName(){
        return name;
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

    public String getFirebaseUid(){
        return firebaseUid;
    }

    public boolean isFirebaseDatabaseFilledOut() {
        return firebaseDatabaseFilledOut;
    }

    protected void setThisUsersOrders(ArrayList<Order> thisUsersOrders) {
        this.thisUsersOrders = thisUsersOrders;
    }

    protected void setPostCode(String postCode){
        this.postCode = postCode;
    }

    protected void setFirebaseDatabaseFilledOut(boolean isIt){
        firebaseDatabaseFilledOut = isIt;
    }


}
