package com.example.android.debris1_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;

public class HTTPTestActivity extends AppCompatActivity {

    EditText editText;
    Button button;
    static TextView textView;
    GetPostcodeData getPostcodeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_test);

        editText = findViewById(R.id.edittext_http_test);
        button = findViewById(R.id.button_http_test);
        textView = findViewById(R.id.http_test_text_view);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postcode = editText.getText().toString();
                String urlString = "http://www.simplylookupadmin.co.uk/JSONservice/JSONSearchForAddress.aspx?datakey=W_B7C4975860B64332998F4398F8DBA0&postcode=" + postcode;
                try {
                    URL url = new URL(urlString);
                    getPostcodeData = new GetPostcodeData(url);
                    getPostcodeData.execute();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
