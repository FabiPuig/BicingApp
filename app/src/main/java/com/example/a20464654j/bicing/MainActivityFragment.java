package com.example.a20464654j.bicing;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private MapView map;
    private MyLocationNewOverlay myLocationNewOverlay;
    private ScaleBarOverlay scaleBarOverlay;
    private CompassOverlay compassOverlay;
    private IMapController iMapController;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.fragment_main, container, false);

        map = (MapView) view.findViewById( R.id.map );

        initializeMap();
        setZoom();
        setOverlays();

        map.invalidate();


        return view;
    }

    private void initializeMap(){
        map.setTileSource(TileSourceFactory.HIKEBIKEMAP );
        map.setTilesScaledToDpi( true );

        map.setBuiltInZoomControls( true );
        map.setMultiTouchControls( true );
    }

    private void setZoom(){
        iMapController = map.getController();
        iMapController.setZoom( 15 );
    }

    private void setOverlays(){

        final DisplayMetrics dm = getResources().getDisplayMetrics();

        myLocationNewOverlay = new MyLocationNewOverlay(
                getContext(),
                new GpsMyLocationProvider( getContext() ),
                map
        );

        myLocationNewOverlay.enableMyLocation();
        myLocationNewOverlay.runOnFirstFix(new Runnable() {
            @Override
            public void run() {
                iMapController.animateTo( myLocationNewOverlay.getMyLocation());
            }
        });

        scaleBarOverlay = new ScaleBarOverlay( map );
        scaleBarOverlay.setCentred( true );
        scaleBarOverlay.setScaleBarOffset( dm.widthPixels / 2 , 10 );

        compassOverlay = new CompassOverlay(
                getContext(),
                new InternalCompassOrientationProvider( getContext() ),
                map
        );

        compassOverlay.enableCompass();

        map.getOverlays().add( myLocationNewOverlay );
        map.getOverlays().add( this.scaleBarOverlay );
        map.getOverlays().add( this.compassOverlay );
    }
}
