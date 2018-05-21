package com.example.android.debris1_1.CompanyUserActivities;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.android.debris1_1.ItemClickListener;
import com.example.android.debris1_1.Order;
import com.example.android.debris1_1.R;
import java.util.ArrayList;

class OrderConfirmedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView numberOfAndSizeOfSkips;
    public TextView toPostcode;
    public TextView deliveryTimeAndDate;
    public TextView collectionTimeAndDate;
    public TextView orderID;
    public TextView paymentAmount;

    Button settingsButton;

    ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public OrderConfirmedViewHolder(View itemView) {
        super(itemView);
        numberOfAndSizeOfSkips = itemView.findViewById(R.id.number_and_type_of_skips_ordered_confirmed_orders_company_view);
        toPostcode = itemView.findViewById(R.id.to_this_address_and_postcode_confirmed_orders_company_view);
        deliveryTimeAndDate = itemView.findViewById(R.id.to_deliver_skip_date_confirmed_orders_company_view);
        collectionTimeAndDate = itemView.findViewById(R.id.collection_date_list_confirmed_orders_company_view);
        orderID = itemView.findViewById(R.id.order_id_confirmed_orders_company_view);
        paymentAmount = itemView.findViewById(R.id.payment_amount_confirmed_orders_company_view);
        settingsButton = itemView.findViewById(R.id.settings_button_confirmed_orders_company_view);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition());
    }
}


public class AllOrdersRecyclerViewAdapter extends RecyclerView.Adapter<OrderConfirmedViewHolder>
    {
        ArrayList<Order> orderList;
        Context context;
        int indexOfRow; //this will be changed
        View.OnClickListener mClickListener;
        ArrayList<View> viewsArrayList;
        //This ArrayList keeps all the views, as in all the columns, in one place. This allows for
        //the views to be accessed to, for example, change the background color of the selected view
        //and restore all the other views to the normal background colour.

        public ArrayList<View> getViewsArrayList () {
        return viewsArrayList;
    }


    public AllOrdersRecyclerViewAdapter(ArrayList<Order> orderList, Context context)
        {
            this.orderList = orderList;
            this.context = context;
            viewsArrayList = new ArrayList<>();
        }

        @Override
        public OrderConfirmedViewHolder onCreateViewHolder (ViewGroup parent,int viewType){


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_confirmed_orders_company_view, parent, false);
        OrderConfirmedViewHolder customViewHolder = new OrderConfirmedViewHolder(v);

        customViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mClickListener.onClick(v);
            }
        });

        viewsArrayList.add(customViewHolder.itemView);


        if (viewsArrayList.size() % 2 == 0) {
            customViewHolder.itemView.setBackgroundColor(Color.WHITE);
        } else {
            customViewHolder.itemView.setBackgroundColor(customViewHolder.itemView.getContext().getResources().getColor(R.color.blueListBackground));
        }

        return customViewHolder;
    }


        protected void setClickListener (View.OnClickListener callback){
        mClickListener = callback;
    }

        @Override
        public void onBindViewHolder ( final OrderConfirmedViewHolder holder, final int position){

        final Order currentOrder = orderList.get(position);

        String numberOfAndTypeOfSkip = currentOrder.getNumberOfSkipsOrdered() + " x " + currentOrder.getSkipTypeString();
        if (currentOrder.getNumberOfSkipsOrdered() > 1) {
            numberOfAndTypeOfSkip += "S"; //This adds an "s" to the word "skip" if there are more than one
        }
        holder.numberOfAndSizeOfSkips.setText(numberOfAndTypeOfSkip);

        String addressAndPostcode;
        //Decides whether to add a second line for the address based on if the user has inputted one
        if(currentOrder.getAddressLine2().length() > 1){
            addressAndPostcode = "To: " + currentOrder.getAddressLine1() + "\n" + currentOrder.getAddressLine2() + "\n" + currentOrder.getPostCode();
        } else {
            addressAndPostcode = "To: " + currentOrder.getAddressLine1() + "\n" + currentOrder.getPostCode();
        }
        holder.toPostcode.setText(addressAndPostcode);

        String deliveryTimeAndDate = "Delivery: " + currentOrder.getDateOfSkipArrivalString();
        holder.deliveryTimeAndDate.setText(deliveryTimeAndDate);

        //TODO TIME as well as date

        String collectionTimeAndDate = "Collection: UNSPECIFIED";
        if (currentOrder.getCollectionDateSpecified()) {
            collectionTimeAndDate = "Collection: " + currentOrder.getDateOfSkipCollectionString();
        }
        holder.collectionTimeAndDate.setText(collectionTimeAndDate);

        String orderID = currentOrder.getOrderUIDakaFirebaseDatabaseKey();
        holder.orderID.setText(orderID);

        String price = "£" + currentOrder.getPrice();
        holder.paymentAmount.setText(price);


    }

        @Override
        public int getItemCount() {
            return orderList.size();
        }

}
