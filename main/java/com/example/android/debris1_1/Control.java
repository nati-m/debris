package com.example.android.debris1_1;

import com.example.android.debris1_1.backend.PublicUser;

/**
 * Created by Paaat VII on 12/03/2018.
 *
 *
 * There is ONE instance of this class called CONTROL (all caps).
 * It contains the constants for e.g. which user is logged in or which company is being ordered from at this moment.
 * It resets upon startup, though later in development logins will be allowed to be constant (stay signed in),
 * and in this case the instance of this class will begin with the current user.
 */

public class Control {

    protected static Control CONTROL = new Control();

    private PublicUser currentUser;

    private Control(){}

    protected PublicUser getCurrentUser(){
        return currentUser;
    }

    protected void setCurrentUser(PublicUser publicUser){
        currentUser = publicUser;
    }


}
