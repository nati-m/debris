package com.example.android.debris1_1.CompanyUserActivities;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.debris1_1.Control;
import com.example.android.debris1_1.ItemClickListener;
import com.example.android.debris1_1.Order;
import com.example.android.debris1_1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

class OrderFromPublicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView numberOfAndSizeOfSkips;
    public TextView toPostcode;
    public TextView deliveryTimeAndDate;
    public TextView collectionTimeAndDate;
    public TextView orderID;
    public TextView paymentAmount;
    public TextView expires;

    Button confirmOrderButton;
    Button declineOrderButton;

    ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public OrderFromPublicViewHolder(View itemView){
        super(itemView);
        numberOfAndSizeOfSkips = itemView.findViewById(R.id.number_and_type_of_skips_orders_from_public_list);
        toPostcode = itemView.findViewById(R.id.to_this_postcode_orders_from_public_list);
        deliveryTimeAndDate = itemView.findViewById(R.id.to_deliver_skip_date_orders_from_public_list);
        collectionTimeAndDate = itemView.findViewById(R.id.collection_date_list_orders_from_public);
        orderID = itemView.findViewById(R.id.order_id_orders_from_public);
        paymentAmount = itemView.findViewById(R.id.payment_amount_list_orders_from_public);
        expires = itemView.findViewById(R.id.expires_by_time_list_orders_from_public);
        confirmOrderButton = itemView.findViewById(R.id.confirm_order_button_list_orders_from_public);
        declineOrderButton = itemView.findViewById(R.id.decline_order_button_list_orders_from_public);


        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition());
    }
}


 public class ConfirmOrdersFromPublicUsersRecyclerViewArrayAdapter extends RecyclerView.Adapter<OrderFromPublicViewHolder> {

    ArrayList<Order> orderList;
    Context context;
    int indexOfRow; //this will be changed
    View.OnClickListener mClickListener;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EE dd MMM yy");
    ArrayList<View> viewsArrayList;
    //This ArrayList keeps all the views, as in all the columns, in one place. This allows for
    //the views to be accessed to, for example, change the background color of the selected view
    //and restore all the other views to the normal background colour.

    public ArrayList<View> getViewsArrayList(){
        return viewsArrayList;
    }


    public ConfirmOrdersFromPublicUsersRecyclerViewArrayAdapter(ArrayList<Order> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
        viewsArrayList = new ArrayList<>();
    }

    @Override
    public OrderFromPublicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_order_from_public, parent, false);
        OrderFromPublicViewHolder customViewHolder = new OrderFromPublicViewHolder(v);

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



     protected void setClickListener(View.OnClickListener callback){
        mClickListener = callback;
    }

    @Override
    public void onBindViewHolder(final OrderFromPublicViewHolder holder, final int position) {

        final Order currentOrder = orderList.get(position);

        String numberOfAndTypeOfSkip = currentOrder.getNumberOfSkipsOrdered() + " x " + currentOrder.getSkipTypeString();
        if (currentOrder.getNumberOfSkipsOrdered() > 1){
            numberOfAndTypeOfSkip += "S"; //This adds an "s" to the word "skip" if there are more than one
        }
        holder.numberOfAndSizeOfSkips.setText(numberOfAndTypeOfSkip);

        String areaCodeFromPostcode = currentOrder.getPostCode().split(" ")[0];
        //This just shows the companies the first part of the postcode, e.g. NE1.
        //Standards from users entering postcode Strings in HireHomePageActivity ensure they are split by a space at the right place.
        String postcode = "Area: " + areaCodeFromPostcode;
        holder.toPostcode.setText(postcode);

        String deliveryTimeAndDate = "Delivery: " + currentOrder.getDateOfSkipArrivalString();
        holder.deliveryTimeAndDate.setText(deliveryTimeAndDate);

        //TODO TIME as well as date

        String collectionTimeAndDate = "Collection: UNSPECIFIED";
        if(currentOrder.getCollectionDateSpecified()){
            collectionTimeAndDate = "Collection: " + currentOrder.getDateOfSkipCollectionString();
        }
        holder.collectionTimeAndDate.setText(collectionTimeAndDate);

        String orderID = currentOrder.getOrderUIDakaFirebaseDatabaseKey();
        holder.orderID.setText(orderID);

        String price = "£" + currentOrder.getPrice();
        holder.paymentAmount.setText(price);

        String offerExpires = workOutWhenOfferExpires(currentOrder);
        holder.expires.setText(offerExpires);

        holder.confirmOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO Add an "ARE YOU SURE?"




                DatabaseReference unconfirmedOrders = FirebaseDatabase.getInstance().getReference().child("orders").child("unconfirmed");
                unconfirmedOrders.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(currentOrder.getOrderUIDakaFirebaseDatabaseKey())){
                            holder.declineOrderButton.setVisibility(View.GONE);
                            holder.confirmOrderButton.setText("✓");
                            holder.confirmOrderButton.setTextSize(33);
                            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.greenConfirmLight));
                            ControlCompanyView.getINSTANCE().getConfirmedOrdersByThisCompany().add(currentOrder);
                            DatabaseReference thisOrderUnconfirmedDatabaseReference = FirebaseDatabase.getInstance().getReference().child("orders").child("unconfirmed").child(currentOrder.getOrderUIDakaFirebaseDatabaseKey());
                            thisOrderUnconfirmedDatabaseReference.removeValue();
                            DatabaseReference thisOrderConfirmedDatabaseReference = FirebaseDatabase.getInstance().getReference().child("orders").child("confirmed").child(Control.CONTROL.getCurrentUser().getFirebaseUid()).child(currentOrder.getOrderUIDakaFirebaseDatabaseKey());
                            currentOrder.setCompanyUID(Control.CONTROL.getCurrentUser().getFirebaseUid()); //TODO separate firebase user and company UIDs
                            thisOrderConfirmedDatabaseReference.setValue(currentOrder);
                        }
                        else {
                            //Someone else has beaten them to it with confirming the job
                            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
                            holder.declineOrderButton.setVisibility(View.GONE);
                            holder.confirmOrderButton.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                            holder.confirmOrderButton.setText("Too late! Someone else nabbed the job");
                            holder.confirmOrderButton.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });




                //Todo Remove from Unconfirmed and send to Confirmed






                holder.confirmOrderButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //do nothing
                    }
                });
            }
        });

        holder.declineOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.confirmOrderButton.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                holder.confirmOrderButton.setText("✗");
                holder.confirmOrderButton.setTextSize(33);
                holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));

                ControlCompanyView.getINSTANCE().getDeclinedOrders().add(currentOrder);

                holder.confirmOrderButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //do nothing
                    }
                });
                holder.declineOrderButton.setVisibility(View.GONE);


                //This doesn't delete an item, just moves the red item down to the bottom and mixes up the order...
//                notifyItemRemoved(position);
//                notifyItemRangeChanged(position, getItemCount());
            }
        });







    }



    private String workOutWhenOfferExpires(Order order){

        //Todo

        return "TOMORROW, 5PM";
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}

