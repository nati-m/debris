package com.example.android.debris1_1.backend;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Paaat VII on 23/03/2018.
 */

public class Skip {

    String skipType;

    public Skip(String skipType){
        this.skipType = skipType;
    }

    public String getSkipType(){
        return skipType;
    }

    /*
    This returns the skip type as an int. The int is how many yards the skip is, and dumpy bags are
    represented by the number 1.
    If the skip type is invalid, it returns the error amount -999.
     */
    public int getSkipTypeAsInt(){
        if (skipType.equals("Maxi Skip (8yd)")){
            return 8;
        }
        if (skipType.equals("Midi Skip (4yd)")){
            return 4;
        }
        if (skipType.equals("Mini Skip (2yd)")){
            return 2;
        }
        if (skipType.equals("Dumpy Bag")){
            return 1;
        }
        else return -999;
    }

    public void setSkipType(String skipType){
       this.skipType = skipType;
    }

}
