package com.example.android.debris1_1;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ThrowAwayCompanyListTest extends AppCompatActivity {

    RecyclerView companyRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<Company> companyList;
    TextView price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_throw_away_company_list_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        companyRecyclerView = findViewById(R.id.recycler_view_test);
        price = findViewById(R.id.price_text_view_throw_away);
        layoutManager = new LinearLayoutManager(this);
        companyRecyclerView.setHasFixedSize(true);
        companyRecyclerView.setLayoutManager(layoutManager);

        getData();




    }

    private void getData(){

        companyList = new ArrayList<>();
        companyList.addAll(Control.CONTROL.getCurrentCompanies());

        //companyRecyclerView.set

        //Create the adapter
        final CompanyRecyclerViewArrayAdapter adapter = new CompanyRecyclerViewArrayAdapter(companyList, this);
        companyRecyclerView.setAdapter(adapter);

        adapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = companyRecyclerView.getChildViewHolder(v).getAdapterPosition();
                Control.CONTROL.setCompanySelectedInRecyclerView(companyList.get(pos));

                String priceString = "£" + Control.CONTROL.getCompanySelectedInRecyclerView().getDefaultPriceForSkip() + "0";
                price.setText(priceString);


            }
        });










        //TODO set the price to same as the selected company's
//        String priceText = "£" + Control.CONTROL.getCompanySelectedInRecyclerView().getDefaultPriceDifferentDependingOnSkipType(Skip.MIDI_SKIP) + "0";
//        price.setText(priceText);

    }

}
