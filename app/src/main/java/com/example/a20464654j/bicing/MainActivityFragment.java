package com.example.a20464654j.bicing;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.MapView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private MapView map;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.fragment_main, container, false);

        map = (MapView) view.findViewById( R.id.map );
        map.setTileSource(TileSourceFactory.HIKEBIKEMAP );
        map.setTilesScaledToDpi( true );

        map.setBuiltInZoomControls( true );
        map.setMultiTouchControls( true );


        return view;
    }
}
