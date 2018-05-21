package com.example.android.debris1_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.debris1_1.CompanyUserActivities.CompanyHomePageLoggedInActivity;

import java.util.ArrayList;

public class TestTownActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_town);

        Button shortcutPublicUser = (Button) findViewById(R.id.shortcut_to_logged_in_homepage);
        shortcutPublicUser.setOnClickListener(new View.OnClickListener() {
            @Override
            //This code will be run when the button is clicked on.
            public void onClick(View view) {

                PublicUser dummyUserForShortcutToWork = new PublicUser("Test Profile Johnson", "NE6 5SQ", "pattest@mail.com", new ArrayList<Order>());
                Control.CONTROL.setCurrentUser(dummyUserForShortcutToWork);
                //Control.CONTROL.initDummyOrders(); //This makes there be 4 dummy orders in various stages of completion to test with

                Intent nextPageIntent = new Intent(TestTownActivity.this, FrontPageLoggedInActivity.class);
                startActivity(nextPageIntent);
            }
        });

        Button shortcutCompanyLogin = findViewById(R.id.test_company_login_page);
        shortcutCompanyLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Anything else needed

                Intent nextPageIntent = new Intent(TestTownActivity.this, CompanyHomePageLoggedInActivity.class);
                startActivity(nextPageIntent);
            }
        });
    }
}
