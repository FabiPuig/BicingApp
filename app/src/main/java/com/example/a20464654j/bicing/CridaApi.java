package com.example.a20464654j.bicing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by 20464654j on 07/02/17.
 */

public class CridaApi {

    private static String urlApi = "http://wservice.viabicing.cat/v2/stations";

    static ArrayList<Park> extrauParkings(){

        ArrayList<Park> parks = new ArrayList<>();

        try{
            String respostaJson = HttpUtils.get( urlApi );
        }catch ( IOException e){
            e.printStackTrace();
        }

        return null;
    }

    static ArrayList<Park> tractaJson( String infoJSON){

        ArrayList<Park> parks = new ArrayList<>();

        try{

            JSONObject data = new JSONObject( infoJSON );
            JSONArray parkingsJSON = data.getJSONArray( "stations" );

            for (int i = 0; i < parkingsJSON.length(); i++) {
                JSONObject o = parkingsJSON.getJSONObject( i );

                Park park = new Park();
                park.setName( o.getString("streetName" + ", " + o.getString("streetNumber") ) );
                park.setId( o.getString( "id" ) );
                park.setLat( o.getString( "latitude" ) );
            }

        }catch ( JSONException e){
            e.printStackTrace();
        }

        return null;    // temporal

    }
}
