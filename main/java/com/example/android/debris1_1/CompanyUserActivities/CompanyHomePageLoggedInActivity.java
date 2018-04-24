package com.example.android.debris1_1.CompanyUserActivities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.android.debris1_1.CompanyRecyclerViewArrayAdapter;
import com.example.android.debris1_1.Control;
import com.example.android.debris1_1.R;

public class CompanyHomePageLoggedInActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_home_page_logged_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ConfirmOrdersFromPublicUsersRecyclerViewArrayAdapter arrayAdapter = new ConfirmOrdersFromPublicUsersRecyclerViewArrayAdapter(ControlCompanyView.getINSTANCE().getCurrentOrders(), this);

        recyclerView = findViewById(R.id.recycler_view_company_home_page_logged_in);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(arrayAdapter);

      //  for (int i = 0; i < companyRecyclerViewArrayAdapter.getViewsArrayList().size(); i++){
//  //                  if (i == pos){
//                        companyRecyclerViewArrayAdapter.getViewsArrayList().get(i).setBackgroundColor(Color.CYAN);
//                    }
//                    else companyRecyclerViewArrayAdapter.getViewsArrayList().get(i).setBackgroundColor(getResources().getColor(R.color.background));
//                }

//        companyRecyclerViewArrayAdapter = new CompanyRecyclerViewArrayAdapter(getRelevantCompaniesArrayList(), this);
//
//        companyRecyclerView = findViewById(R.id.recycler_view_companies_single_day_view);
//        companyRecyclerView.setHasFixedSize(true);
//        companyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        companyRecyclerView.setAdapter(companyRecyclerViewArrayAdapter);
//
//        companyRecyclerViewArrayAdapter.setClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int pos = companyRecyclerView.getChildViewHolder(v).getAdapterPosition();
//                Control.CONTROL.setCompanySelectedInRecyclerView(Control.CONTROL.getSingleDayViewCompanies().get(pos));
//
//                String priceString = "£" + Control.CONTROL.getCompanySelectedInRecyclerView().getDefaultPriceDifferentDependingOnSkipType(skipType) + "0";
//                priceSubtotal.setText(priceString);
//
//                for (int i = 0; i < companyRecyclerViewArrayAdapter.getViewsArrayList().size(); i++){
//                    if (i == pos){
//                        companyRecyclerViewArrayAdapter.getViewsArrayList().get(i).setBackgroundColor(Color.CYAN);
//                    }
//                    else companyRecyclerViewArrayAdapter.getViewsArrayList().get(i).setBackgroundColor(getResources().getColor(R.color.background));
//                }
//            }
//        });


    }

}
