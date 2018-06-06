package com.example.android.debris1_1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;


import java.util.ArrayList;

public class HireSkipArrivalTimeChoosingActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ChooseTimeArrayAdapter chooseTimeArrayAdapter;
    ArrayList<String> times;
    String selectedTime;
    Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_skip_arrival_time_choosing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_view_choose_time_skip_arrival);


        selectedTime = "";
        times = new ArrayList<>();
        times.add("8am-10am"); times.add("9am-11am"); times.add("10am-12pm"); times.add("11am-1pm"); times.add("12pm-2pm");
        //TODO if not saturday
        times.add("1pm-3pm"); times.add("2pm-4pm"); times.add("3pm-5pm");

        chooseTimeArrayAdapter = new ChooseTimeArrayAdapter(times, this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chooseTimeArrayAdapter);

        chooseTimeArrayAdapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = recyclerView.getChildViewHolder(v).getAdapterPosition();

                selectedTime = times.get(pos);

                for (int i = 0; i < chooseTimeArrayAdapter.getViewsArrayList().size(); i++){
                    if (i == pos){
                        chooseTimeArrayAdapter.getViewsArrayList().get(i).setBackgroundColor(Color.CYAN);
                    }
                    else chooseTimeArrayAdapter.getViewsArrayList().get(i).setBackgroundColor(getResources().getColor(R.color.background));
                }
            }
        });

        continueButton = findViewById(R.id.button_continue_with_no_pick_up_time_choose_arrival_time_activity);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPageIntent = new Intent(HireSkipArrivalTimeChoosingActivity.this, HireReviewOrderActivity.class);
                startActivity(nextPageIntent);
            }
        });


    }

}
