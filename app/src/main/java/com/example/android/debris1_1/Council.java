package com.example.android.debris1_1;

/**
 * Created by Paaat VII on 12/03/2018.
 */

public class Council {

    //This is copied from the old intelliJ version so some things may not be relevant, e.g. System.out.print

    private String name;
    private double defaultPrice; //This is the price if no membership is required, or if it is and we are a member
    private double nonMemberPrice; //this is only relevant if a membership is required for this council.
    //If it is not, it will be initiated as identical to defaultPrice and can be effectively ignored.

    //private double publicLiabilityInsurance; //this may not be necessary as all are £5million so far
    private boolean requiresMembership;
    private boolean weAreAMember;

    /*
    The first of 2 constructors. This is for the majority of councils that do not require a membership.
    By default it will set requiresMembership to false, weAreAMember to false and nonMemberPrice to defaultPrice.
    The name and defaultPrice are required.
     */
    protected Council(String name, double price){
        this.name = name;
        defaultPrice = price;
        nonMemberPrice = price;
        requiresMembership = false;
        weAreAMember = false;
    }

    /*
    The second of 2 constructors. This is for the minority of councils that require membership.
     */
    protected Council(String name, boolean requiresMembership, boolean weAreAMember, double nonMemberPrice, double defaultPrice){
        this.name = name;
        this.requiresMembership = requiresMembership;
        this.weAreAMember = weAreAMember;
        this.nonMemberPrice = nonMemberPrice;
        this.defaultPrice = defaultPrice;
    }

    /*
    This returns the price of a permit, which differs for each council.
    If the council requires membership, the price will differ if we are a member or not.
     */
    public double getPermitPrice(){
        if(requiresMembership && !weAreAMember){
            return nonMemberPrice;
        }
        else {
            return defaultPrice;
        }
    }

    public boolean isMembershipRequired(){
        return requiresMembership;
    }

    public void setIfWeAreAMember(boolean areWe){
        weAreAMember = areWe;
    }

    public boolean areWeAMember(){
        if(!requiresMembership){
            System.out.println("No membership is required for " + getName() + ", however.");
        }
        return weAreAMember;
    }

    public String getName(){
        return name;
    }

    public String toString(){
        return name + " Council";
    }
}
