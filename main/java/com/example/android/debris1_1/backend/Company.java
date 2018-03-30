package com.example.android.debris1_1.backend;

import java.util.Comparator;

/**
 * Created by Paaat VII on 12/03/2018.
 */

public class Company {

    private String name;
    private String postcode;
    private String email;
    private String phoneNumber;
    private int maxi_skip_maximum;
    private int midi_skip_maximum;
    private int mini_skip_maximum;
    private int dumpy_bag_maximum;
    private int maxiSkips;
    private int midiSkips;
    private int miniSkips;
    private int dumpyBags;
    private double rating;
    private double defaultPriceForSkip;
    private boolean isSelectedInCompanyList;

    public Company (String name, String postcode){
        this.name = name;
        this.postcode = postcode;
        maxi_skip_maximum=0;
        midi_skip_maximum=0;
        mini_skip_maximum=0;
        dumpy_bag_maximum=0;
        defaultPriceForSkip = 0;
        rating = 2.5;
        isSelectedInCompanyList = false;

        resetEachSkipTypeNumberToMaximum();
    }

    public Company (String name, String postcode, String email, String phoneNumber, double defaultPriceForSkip){
        this.name = name;
        this.postcode = postcode;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.defaultPriceForSkip = defaultPriceForSkip;
        maxi_skip_maximum=0;
        midi_skip_maximum=0;
        mini_skip_maximum=0;
        dumpy_bag_maximum=0;
        rating = 2.5;
        isSelectedInCompanyList = false;

        resetEachSkipTypeNumberToMaximum();
    }

    public Company (String name, String postcode, String email, String phoneNumber, double defaultPriceForSkip, int maxi_skip_maximum, int midi_skip_maximum, int mini_skip_maximum, int dumpy_bag_maximum){
        this.name = name;
        this.postcode = postcode;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.defaultPriceForSkip = defaultPriceForSkip;
        this.maxi_skip_maximum = maxi_skip_maximum;
        this.midi_skip_maximum = midi_skip_maximum;
        this.mini_skip_maximum = mini_skip_maximum;
        this.dumpy_bag_maximum = dumpy_bag_maximum;
        rating = 2.5;
        isSelectedInCompanyList = false;

        resetEachSkipTypeNumberToMaximum();
    }

    //Not sure if these are relevant any more, except isSkipCo? As they will handle everything?
    private boolean isSkipCo;
    private boolean isHaulCo;
    private boolean isTipCo;

    /*
    This sets all of the skip types to the maximum amount possible, i.e. the company has all its skips available.
    This is called during construction but can also be used to reset if need be.
    Be warned that this effect is irreversible.
     */
    public void resetEachSkipTypeNumberToMaximum(){
        maxiSkips = maxi_skip_maximum;
        midiSkips = midi_skip_maximum;
        miniSkips = mini_skip_maximum;
        dumpyBags = dumpy_bag_maximum;
    }

    //TODO Make these again but include date!!!
    public boolean areMaxiSkipsAvailable(int numberOfMaxiSkipsWanted){
        int number = maxiSkips;
        number -= numberOfMaxiSkipsWanted;
        if (number >= 0){
            return true;
        }
        return false;
    }

    public boolean areMidiSkipsAvailable(int numberOfMidiSkipsWanted){
        int number = midiSkips;
        number -= numberOfMidiSkipsWanted;
        if (number >= 0){
            return true;
        }
        return false;
    }

    public boolean areMiniSkipsAvailable(int numberOfMiniSkipsWanted){
        int number = miniSkips;
        number -= numberOfMiniSkipsWanted;
        if (number >= 0){
            return true;
        }
        return false;
    }

    public boolean areDumpyBangsAvailable(int numberOfDumpyBagsWanted){
        int number = dumpyBags;
        number -= numberOfDumpyBagsWanted;
        if (number >= 0){
            return true;
        }
        return false;
    }

    public void setMaxiSkips(int numberOfMaxiSkips){
        maxiSkips = numberOfMaxiSkips;
    }

    public void setMidiSkips(int numberOfMidiSkips){
        midiSkips = numberOfMidiSkips;
    }

    public void setMiniSkips(int numberOfMiniSkips){
        miniSkips = numberOfMiniSkips;
    }

    public void setDumpyBags(int numberOfDumpyBags){
        dumpyBags = numberOfDumpyBags;
    }

    public void setMaxi_skip_maximum(int newMaximum){
        maxi_skip_maximum = newMaximum;
    }

    public void setMidi_skip_maximum(int newMaximum){
        midi_skip_maximum = newMaximum;
    }

    public void setMini_skip_maximum(int newMaximum){
        mini_skip_maximum = newMaximum;
    }

    public void setDumpy_bag_maximum(int newMaximum){
        dumpy_bag_maximum = newMaximum;
    }

    public void setDefaultPriceForSkip(double newDefaultPriceForSkip){
        defaultPriceForSkip = newDefaultPriceForSkip;
    }

    public void setIsSelectedInCompanyList(boolean isIt){
        isSelectedInCompanyList = isIt;
    }

    public String getName(){
        return name;
    }

    public double getDefaultPriceForSkip(){
        return defaultPriceForSkip;
    }

    public static Comparator<Company> priceComparator = new Comparator<Company>() {
        @Override
        public int compare(Company c1, Company c2) {
            double compareDouble = c1.getDefaultPriceForSkip() - c2.getDefaultPriceForSkip();
            if(compareDouble > 0){
                return 1;
            } else {
                return -1;
            }
        }
    };

    public static Comparator<Company> ratingComparator = new Comparator<Company>() {
        @Override
        public int compare(Company c1, Company c2) {
            double compareDouble = c1.getRating() - c2.getRating();
            if (compareDouble < 0) {
                return 1;
            } else {
                return -1;
            }
        }
    };


    public void setRating(double rating){
        this.rating = rating;
    }

    public double getRating(){
        return rating;
    }

    public boolean getIsSelectedInCompanyList(){
        return isSelectedInCompanyList;
    }

}
