package com.example.android.debris1_1;

/**
 * Created by Paaat VII on 19/03/2018.
 */

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class AddressLookup{

        private String simplyserver = "http://www.simplylookupadmin.co.uk";
        private String datakey = "W_B7C4975860B64332998F4398F8DBA0";
        private String line1;
        private String line2;
        private String line3;
        private String town;
        private String county;
        private String postcode;
        //private String Country;
        //Node node;

        public AddressLookup(){}

        public int findDistanceBetweenTwoPostcodesInKM(String postcode1, String postcode2) {


            //http://www.simplylookupadmin.co.uk/JSONservice/JSONSearchForPostZonData.aspx?datakey=W_B7C4975860B64332998F4398F8DBA0&postcode=NE20%209DA&homepostcode=NE4%209EN

            String url = "http://www.simplylookupadmin.co.uk/JSONservice/JSONSearchForPostZonData.aspx?datakey=" + datakey +"&postcode=" + postcode1 + "&homepostcode=" + postcode2;
            final Distance distance = new Distance();


            int toReturn = distance.getDistanceAsInt();


            return toReturn;
        }







        //XML service also exists (as in not JSON)
        //simplyserver + "/XMLService/SearchForThoroughfareAddress.aspx?datakey=" + datakey + "&postcode=" + postcodeToSearch
        //http://www.simplylookupadmin.co.uk/XMLService/SearchForThoroughfareAddress.aspx?datakey=W_B7C4975860B64332998F4398F8DBA0&postcode=NE45AB


        public String getAddressFromPostcode(String postcodeToSearch) throws Exception{

            String url = "http://www.simplylookupadmin.co.uk/JSONservice/JSONSearchForAddress.aspx?datakey=W_B7C4975860B64332998F4398F8DBA0&postcode=" + postcodeToSearch;

            URL urlObject = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlObject.openConnection();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.out.println(response.toString());

            JSONObject myResponse = new JSONObject(response.toString());






            Address address = new Address(line1, line2, line3, town, county, postcode);
            //return address;
            //the return type has been changed to String for this test...

            return response.toString();
        }


    }

