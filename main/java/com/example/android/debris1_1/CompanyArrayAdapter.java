package com.example.android.debris1_1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Paaat VII on 26/03/2018.
 */

public class CompanyArrayAdapter extends ArrayAdapter<Company> {

    private boolean showCompanyNames;
    String currency;
    TextView companyHeading;
    TextView price;
    RatingBar ratingBar;
    ArrayList<Company> companyArrayList;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_company, parent, false); //TODO CHANGE TO SIMPLE LIST VIEW
        }





        // Get the {@link AndroidFlavor} object located at this position in the list
        Company currentCompany = getItem(position);

        companyHeading = (TextView) listItemView.findViewById(R.id.company_heading_list_item_company);

        if(showCompanyNames){
            companyHeading.setText(currentCompany.getName());
        } else {
            int companyNumber = position + 1;
            companyHeading.setText("Company " + companyNumber);
        }

        price = (TextView) listItemView.findViewById(R.id.company_price_list_item_company);
        price.setText(getCurrency() + currentCompany.getDefaultPriceForSkip() + "0");

        ratingBar = (RatingBar) listItemView.findViewById(R.id.rating_company_list_item);
        float rating = (float) currentCompany.getRating();
        ratingBar.setRating(rating);

//        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);
//        if (currentCompany.hasImage()) {
//            imageView.setImageResource(currentCompany.getImg());
//        }
//        else {
//            imageView.setVisibility(View.GONE);
//        }


        // Return the whole list item layout (containing 2 TextViews and 1 ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }

    @Override
    public int getCount(){
        return  companyArrayList.size();
    }

    @Override
    public Company getItem(int position){
        return companyArrayList.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }


    public CompanyArrayAdapter(Activity context, int resource, ArrayList<Company> companyArrayList) {

        super(context, resource, companyArrayList);
        showCompanyNames = true;
        currency = "£";
        this.companyArrayList = companyArrayList;
    }

    public CompanyArrayAdapter(Activity context, int resource, ArrayList<Company> companyArrayList, boolean showCompanyNames, String currency) {

        super(context, resource, companyArrayList);
        this.showCompanyNames = showCompanyNames;
        this.currency = currency;
        this.companyArrayList = companyArrayList;
    }

    public void setIfCompanyNamesAreShown(boolean areThey){
        showCompanyNames = areThey;
    }

    public String getCurrency(){
        return currency;
    }

    public void setCurrency(String currency){
        this.currency = currency;
    }




}
