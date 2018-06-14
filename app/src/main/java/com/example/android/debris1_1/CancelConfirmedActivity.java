package com.example.android.debris1_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CancelConfirmedActivity extends AppCompatActivity {

    Button toMainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_confirmed);

        toMainMenu = findViewById(R.id.button_to_main_menu_cancel_confirmed);
        toMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPageIntent = new Intent(CancelConfirmedActivity.this, FrontPageLoggedInActivity.class);
                startActivity(nextPageIntent);
            }
        });
    }


    @Override
    public void onBackPressed() {
        return;
    }
}
