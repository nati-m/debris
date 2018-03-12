package com.example.android.debris1_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class HireHomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button getASkip = (Button) findViewById(R.id.button_get_a_skip);
        getASkip.setOnClickListener(new View.OnClickListener() {
            @Override
            //This code will be run when the button is clicked on.
            public void onClick(View view) {
                Intent nextPageIntent = new Intent(HireHomePageActivity.this, HireSingleDayViewActivity.class);
                startActivity(nextPageIntent);
            }
        });

        Spinner skipSizeSpinner = (Spinner) findViewById(R.id.skip_size_spinner_hire_home_page);

        // Spinner click listener
        //skipSizeSpinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        ArrayList<String> spinnerCategories = new ArrayList<>();
        spinnerCategories.add("Maxi Skip (8yd)");
        spinnerCategories.add("Midi Skip (4yd)");
        spinnerCategories.add("Mini Skip (2yd)");
        spinnerCategories.add("Dumpy Bag");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerCategories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        skipSizeSpinner.setAdapter(dataAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
