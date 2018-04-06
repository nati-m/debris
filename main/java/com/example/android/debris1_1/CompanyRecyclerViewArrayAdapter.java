package com.example.android.debris1_1;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView companyName;
    public TextView price;
    public RatingBar ratingBar;

    ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public CustomViewHolder(View itemView){
        super(itemView);
        companyName = itemView.findViewById(R.id.company_heading_list_item_company);
        price = itemView.findViewById(R.id.company_price_list_item_company);
        ratingBar = itemView.findViewById(R.id.rating_company_list_item);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition());
    }
}


public class CompanyRecyclerViewArrayAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    ArrayList<Company> companyList;
    Context context;
    int indexOfRow; //this will be changed
    View.OnClickListener mClickListener;
    ArrayList<View> viewsArrayList;
    //This ArrayList keeps all the views, as in all the columns, in one place. This allows for
    //the views to be accessed to, for example, change the background color of the selected view
    //and restore all the other views to the normal background colour.

    public ArrayList<View> getViewsArrayList(){
        return viewsArrayList;
    }


    public CompanyRecyclerViewArrayAdapter(ArrayList<Company> companyList, Context context) {
        this.companyList = companyList;
        this.context = context;
        viewsArrayList = new ArrayList<>();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_company, parent, false);
        CustomViewHolder customViewHolder = new CustomViewHolder(v);

        customViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onClick(v);
            }
        });

        viewsArrayList.add(customViewHolder.itemView);

        return customViewHolder;
    }

    protected void setClickListener(View.OnClickListener callback){
        mClickListener = callback;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.companyName.setText(companyList.get(position).getName());
        String priceString = "£" + companyList.get(position).getDefaultPriceForSkip() + "0";
        holder.price.setText(priceString);
        float rating = (float) companyList.get(position).getRating();
        holder.ratingBar.setRating(rating);



    }

    @Override
    public int getItemCount() {
        return companyList.size();
    }
}
