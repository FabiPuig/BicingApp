package com.example.a20464654j.bicing;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * Created by 20464654j on 07/02/17.
 */

public class RefreshDataTask extends AsyncTask<Void, Void, Void> {

    private Context context;

    RefreshDataTask( Context context){
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... params) {

        ArrayList<Park> parkings = new ArrayList<>();

        parkings = CridaApi.extrauParkings();


        return null;
    }
}
