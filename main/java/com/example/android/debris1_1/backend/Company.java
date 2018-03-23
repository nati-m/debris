package com.example.android.debris1_1.backend;

/**
 * Created by Paaat VII on 12/03/2018.
 */

public class Company {

    private String name;
    private String email;
    private String phoneNumber;
    private int maxi_skip_maximum;
    private int midi_skip_maximum;
    private int mini_skip_maximum;
    private int dumpy_bag_maximum;

    private double defaultPriceForSkip;

    public Company (String name){
        this.name = name;
        maxi_skip_maximum=0;
        midi_skip_maximum=0;
        mini_skip_maximum=0;
        dumpy_bag_maximum=0;
        defaultPriceForSkip = 0;
    }

    public Company (String name, String email, String phoneNumber, double defaultPriceForSkip){
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.defaultPriceForSkip = defaultPriceForSkip;
        maxi_skip_maximum=0;
        midi_skip_maximum=0;
        mini_skip_maximum=0;
        dumpy_bag_maximum=0;
    }

    public Company (String name, String email, String phoneNumber, double defaultPriceForSkip, int maxi_skip_maximum, int midi_skip_maximum, int mini_skip_maximum, int dumpy_bag_maximum){
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.defaultPriceForSkip = defaultPriceForSkip;
        this.maxi_skip_maximum = maxi_skip_maximum;
        this.midi_skip_maximum = midi_skip_maximum;
        this.mini_skip_maximum = mini_skip_maximum;
        this.dumpy_bag_maximum = dumpy_bag_maximum;
    }

    //Not sure if these are relevant any more, except isSkipCo? As they will handle everything?
    private boolean isSkipCo;
    private boolean isHaulCo;
    private boolean isTipCo;


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



}
