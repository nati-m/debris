package com.example.android.debris1_1;

import java.util.Calendar;

/**
 * Created by Paaat VII on 31/03/2018.
 */

public class UserFeedback {

    private int rating; //will be from 1 to 5 stars
    private String review;
    private PublicUser publicUser;
    private Company company;
    private Calendar dateOrderEnded;
    private Calendar dateOfReview;

    public UserFeedback(int rating, String review, PublicUser publicUser, Company company, Calendar dateOrderEnded, Calendar dateOfReview) {
        this.rating = rating;
        this.review = review;
        this.publicUser = publicUser;
        this.company = company;
        this.dateOrderEnded = dateOrderEnded;
        this.dateOfReview = dateOfReview;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public PublicUser getPublicUser() {
        return publicUser;
    }

    public void setPublicUser(PublicUser publicUser) {
        this.publicUser = publicUser;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Calendar getDateOrderEnded() {
        return dateOrderEnded;
    }

    public void setDateOrderEnded(Calendar dateOrderEnded) {
        this.dateOrderEnded = dateOrderEnded;
    }

    public Calendar getDateOfReview() {
        return dateOfReview;
    }

    public void setDateOfReview(Calendar dateOfReview) {
        this.dateOfReview = dateOfReview;
    }
}
