package com.example.android.debris1_1;

import java.util.HashMap;
import java.util.Map;

/**
 * There are only 4 instances of Skip: MAXI_SKIP, MIDI_SKIP, MINI_SKIP and DUMPY_BAG.
 * These can be accessed by static methods with their specific names, or by the static method
 * getSkipByIntSize, which allows a Skip object to be returned depending on size specified.
 * There are static size ints for each Skip size which should be used to this end.
 * The skip size as a String or int can be returned if need be.
 */

public class Skip {

    private String skipTypeString;
    private int skipType;

    public static final int MAXI_SKIP_8YD = 8;
    public static final int MIDI_SKIP_4YD = 4;
    public static final int MINI_SKIP_2YD = 2;
    public static final int DUMPY_BAG_SMALLEST = 1;

    //There are only 4 instances of Skip, and they can't be edited.
    //Every ArrayList of Skips will contain only copies of these objects.
    //This avoids unnecessary memory use and the possibility to initialise a Skip Object
    //with an incompatible size.
    public static final Skip MAXI_SKIP = new Skip(MAXI_SKIP_8YD);
    public static final Skip MIDI_SKIP = new Skip(MIDI_SKIP_4YD);
    public static final Skip MINI_SKIP = new Skip(MINI_SKIP_2YD);
    public static final Skip DUMPY_BAG = new Skip(DUMPY_BAG_SMALLEST);

    private Skip (int skipType8Maxi4Midi2Mini1DumpyBag){
        skipType = skipType8Maxi4Midi2Mini1DumpyBag;
        setSkipTypeStringFromIntType();
    }

    public static Skip getSkipByIntSize(int skipType){
        if (skipType == MAXI_SKIP_8YD){
            return MAXI_SKIP;
        }
        else if (skipType == MIDI_SKIP_4YD){
            return MIDI_SKIP;
        }
        else if (skipType == MINI_SKIP_2YD){
            return MINI_SKIP;
        }
        else if (skipType == DUMPY_BAG_SMALLEST){
            return DUMPY_BAG;
        }
        return null;
    }

    public static Skip getMaxiSkip(){
        return MAXI_SKIP;
    }

    public static Skip getMidiSkip(){
        return MIDI_SKIP;
    }

    public static Skip getMiniSkip(){
        return MINI_SKIP;
    }

    public static Skip getDumpyBag(){
        return DUMPY_BAG;
    }

    public void setSkipTypeStringFromIntType(){
        if (skipType == MAXI_SKIP_8YD){
            skipTypeString = "Maxi Skip (8yd³)";
        }
        else if (skipType == MIDI_SKIP_4YD){
            skipTypeString = "Midi Skip (4yd³)";
        }
        else if (skipType == MINI_SKIP_2YD){
            skipTypeString = "Mini Skip (2yd³)";
        }
        else if (skipType == DUMPY_BAG_SMALLEST){
            skipTypeString = "Skip Bag";
        }
        else {
            skipTypeString = "No real size selected";
        }
    }

    public String getSkipTypeAsString(){
        return skipTypeString;
    }

    public String getSkipTypeAsSimplerString(){
        if (skipType == MAXI_SKIP_8YD){
            return "MAXI";
        }
        if (skipType == MIDI_SKIP_4YD){
            return "MIDI";
        }
        if (skipType == MINI_SKIP_2YD){
            return "MINI";
        }
        if (skipType == DUMPY_BAG_SMALLEST){
            return "SKIPBAG";
        }
        return "No real size selected";
    }


    /*
    This returns the skip type as an int. The int is how many yards the skip is, and dumpy bags are
    represented by the number 1.
     */
    public int getSkipTypeAsInt(){
        return skipType;
    }

}
