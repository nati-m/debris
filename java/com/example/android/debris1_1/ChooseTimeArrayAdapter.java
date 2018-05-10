package com.example.android.debris1_1;

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

class ChooseTimeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView timeTextView;

    ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public ChooseTimeViewHolder(View itemView){
        super(itemView);
        timeTextView = itemView.findViewById(R.id.time_text_view_list_item_times);


        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition());
    }
}


public class ChooseTimeArrayAdapter extends RecyclerView.Adapter<ChooseTimeViewHolder> {

    ArrayList<String> timesList;
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


    public ChooseTimeArrayAdapter(ArrayList<String> timesList, Context context) {
        this.timesList = timesList;
        this.context = context;
        viewsArrayList = new ArrayList<>();
    }

    @Override
    public ChooseTimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_times, parent, false);
        ChooseTimeViewHolder customViewHolder = new ChooseTimeViewHolder(v);

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
    public void onBindViewHolder(final ChooseTimeViewHolder holder, final int position) {

        final String currentTime = timesList.get(position);

        holder.timeTextView.setText(currentTime);
    }



    @Override
    public int getItemCount() {
        return timesList.size();
    }
}
