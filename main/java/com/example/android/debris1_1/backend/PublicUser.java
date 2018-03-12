package com.example.android.debris1_1.backend;

/**
 * Created by Paaat VII on 12/03/2018.
 */

public class PublicUser {

    private String name;
    private String address;
    private String postCode;
    private String email;

    public PublicUser(String name, String postCode, String email){
        this.name = name;
        this.postCode = postCode;
        this.email = email;
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



}
