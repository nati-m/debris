package com.example.android.debris1_1;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;

/**
 * Created by Paaat VII on 12/03/2018.
 */

public class PublicUser {

    private String name;
    private String postCode;
    private String addressLine1;
    private String addressLine2;
    private String email;
    private String firebaseUid;
    private boolean firebaseDatabaseFilledOut;
    private ArrayList<Order> thisUsersOrders;
    private boolean isCompanyUser;
    private int points;
    private String additionalEmail1;
    private String additionalEmail2;

    public PublicUser() {
    } //A blank constructor is required to let there be a Firebase database of this object

    public PublicUser(String name, String postCode, String email, ArrayList<Order> thisUsersOrders) {
        this.name = name;
        this.postCode = postCode;
        this.email = email;
        this.thisUsersOrders = thisUsersOrders;
        firebaseDatabaseFilledOut = false;
        isCompanyUser = false;
        points = 0;
    }

    public PublicUser(String name, String AddressLine1, String AddressLine2, String postCode, String email, ArrayList<Order> thisUsersOrders, boolean firebaseDatabaseFilledOut, String firebaseUid, int points) {
        this.name = name;
        this.postCode = postCode;
        this.email = email;
        this.thisUsersOrders = thisUsersOrders;
        this.firebaseDatabaseFilledOut = firebaseDatabaseFilledOut;
        this.firebaseUid = firebaseUid;
        isCompanyUser = false;
        this.points = points;
        addressLine1 = AddressLine1;
        addressLine2 = AddressLine2;
    }

    public PublicUser(String name, String AddressLine1, String AddressLine2, String postCode, String email, ArrayList<Order> thisUsersOrders, boolean firebaseDatabaseFilledOut, String firebaseUid, int points, boolean isCompanyUser) {
        this.name = name;
        this.postCode = postCode;
        this.email = email;
        this.thisUsersOrders = thisUsersOrders;
        this.firebaseDatabaseFilledOut = firebaseDatabaseFilledOut;
        this.firebaseUid = firebaseUid;
        this.points = points;
        this.isCompanyUser = isCompanyUser;
        addressLine1 = AddressLine1;
        addressLine2 = AddressLine2;
    }


    public String getName() {
        return name;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<Order> getThisUsersOrders() {
        return thisUsersOrders;
    }

    public String getFirebaseUid() {
        return firebaseUid;
    }

    public boolean isFirebaseDatabaseFilledOut() {
        return firebaseDatabaseFilledOut;
    }

    protected void setThisUsersOrders(ArrayList<Order> thisUsersOrders) {
        this.thisUsersOrders = thisUsersOrders;
    }

    protected void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    protected void setFirebaseDatabaseFilledOut(boolean isIt) {
        firebaseDatabaseFilledOut = isIt;
    }


    public boolean getIsCompanyUser() {
        return isCompanyUser;
    }

    public void setIsCompanyUser(boolean companyUser) {
        isCompanyUser = companyUser;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }


    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAdditionalEmail1() {
        return additionalEmail1;
    }

    public void setAdditionalEmail1(String additionalEmail1) {
        this.additionalEmail1 = additionalEmail1;
    }

    public String getAdditionalEmail2() {
        return additionalEmail2;
    }

    public void setAdditionalEmail2(String additionalEmail2) {
        this.additionalEmail2 = additionalEmail2;
    }

    @Exclude
    public int getNumberOfAdditionalEmails(){
        int i = 0;
        if(additionalEmail1 != null){
            i++;
        }
        if(additionalEmail2 != null){
            i++;
        }
        return i;
    }

}
