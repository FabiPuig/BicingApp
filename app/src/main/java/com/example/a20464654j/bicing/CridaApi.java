package com.example.a20464654j.bicing;

import android.util.Log;

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
            return tractaJson( respostaJson );
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
                park.setId( o.getString( "id" ) );
                if( !o.getString("streetName").contains("(PK)") ){
                    park.setName( o.getString("streetName" ) + ", " + o.getString("streetNumber")  );
                }else{
                    park.setName( o.getString("streetName" ).substring( 5, o.getString( "streetName").length()  ) + ", " + o.getString("streetNumber")  );
                }
                park.setLat( o.getString( "latitude" ) );
                park.setLon( o.getString( "longitude" ));
                park.setType( o.getString( "type" ) );
                park.setSlots( Integer.parseInt( o.getString( "slots" ) ) );
                park.setBykes( Integer.parseInt( o.getString( "bikes" ) ) );

                Log.d("DEBBUG", park.toString() );

                parks.add( park );
            }

            return parks;

        }catch ( JSONException e){
            e.printStackTrace();
        }

        return null;    // temporal

    }
}
