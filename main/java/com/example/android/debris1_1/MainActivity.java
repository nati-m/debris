package com.example.android.debris1_1;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    public static TextView testTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button nextPageButton = (Button) findViewById(R.id.front_page_button);
        nextPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            //This code will be run when the button is clicked on.
            public void onClick(View view) {
                Intent nextPageIntent = new Intent(MainActivity.this, FrontPageNotSignedIn.class);
                startActivity(nextPageIntent);
            }
        });

        Button shortcut = (Button) findViewById(R.id.shortcut_to_logged_in_homepage);
        shortcut.setOnClickListener(new View.OnClickListener() {
            @Override
            //This code will be run when the button is clicked on.
            public void onClick(View view) {

                PublicUser dummyUserForShortcutToWork = new PublicUser("Shortcut Alan", "NE6 5SQ", "alan@nala.com", Control.CONTROL.getOrdersFromThisUser());
                Control.CONTROL.setCurrentUser(dummyUserForShortcutToWork);


                Intent nextPageIntent = new Intent(MainActivity.this, FrontPageLoggedInActivity.class);
                startActivity(nextPageIntent);
            }
        });

        //TESTING GETTING A JSON OBJECT

//        testTextView = (TextView) findViewById(R.id.testTestTestTestTest);
//        String JSONData = "Not this time pal";
//
//        URL url = null;
//        try {
//            url = new URL("http://www.simplylookupadmin.co.uk/JSONservice/JSONSearchForPostZonData.aspx?datakey=W_B7C4975860B64332998F4398F8DBA0&postcode=NE30%202AY&homepostcode=NE4%209EN");
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        GetPostcodeData getPostcodeData = new GetPostcodeData(url);
//
//        GetAndHandlePostcodeData getAndHandlePostcodeData = new GetAndHandlePostcodeData("NE4 5AB", "NE6 5SQ");
//        JSONData = getAndHandlePostcodeData.getDistanceBetweenTwoPostcodesAsAString();
//        testTextView.setText(JSONData);

//        AddressLookup addressLookup = new AddressLookup();
//        int dist = -99;
//        dist = addressLookup.findDistanceBetweenTwoPostcodesInKM("NE4 9EN", "NE77XA"); //won't work yet
//        String addressText = "It didn't work";
//
//        try {
//            addressText = addressLookup.getAddressFromPostcode("NE4 5AB");
//
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//        testTextView.setText(addressText);


//        try {
//            dist = addressLookup.findDistanceBetweenTwoPostcodesInKM("NE4 9EN", "NE77XA");
//        } catch (JSONException e){
//            e.printStackTrace();
//        }

        //testTextView.setText("" + dist);


//        JsonObjectRequest JSONRequest = new JsonObjectRequest
//                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//
//
//
//
//                        testTextView.setText(response.toString());
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // TODO: Handle error
//
//                    }
//                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
