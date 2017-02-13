package com.example.a20464654j.bicing;

import android.content.Context;
import android.os.AsyncTask;

import com.alexvasilkov.events.Events;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by 20464654j on 07/02/17.
 */

public class RefreshDataTask extends AsyncTask<Void, Void, Void> {

    private Context context;
    private static ArrayList<Park> parkings;

    RefreshDataTask( Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        Events.post("inici-cargando");
    }

    @Override
    protected Void doInBackground(Void... params) {

        parkings = new ArrayList<>();

        parkings = CridaApi.extrauParkings();


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Events.create("show-makers").param( parkings ).post();
        Events.post("fin-cargando");

    }
}
