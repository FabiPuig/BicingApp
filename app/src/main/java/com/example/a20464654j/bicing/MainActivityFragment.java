package com.example.a20464654j.bicing;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexvasilkov.events.Events;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.clustering.RadiusMarkerClusterer;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private MapView map;
    private MyLocationNewOverlay myLocationNewOverlay;
    private ScaleBarOverlay scaleBarOverlay;
    private CompassOverlay compassOverlay;
    private IMapController iMapController;
    private RadiusMarkerClusterer parkingMakers;

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

        putMakers();

        map.invalidate();

        return view;
    }

    private void putMakers(){

        setupMakerOverlay();

    }

    @Override
    public void onStart() {
        super.onStart();

        Events.register( this );

        //provem l'extraccio de dades de l¡api
        RefreshDataTask task = new RefreshDataTask( getActivity().getApplicationContext() );
        task.execute();
    }

    private void setupMakerOverlay(){

        parkingMakers = new RadiusMarkerClusterer( getContext() );
        map.getOverlays().add( parkingMakers );

        Drawable clusterIconD = getResources().getDrawable( R.drawable.marker_cluster);
        Bitmap clusterIcon = ((BitmapDrawable) clusterIconD).getBitmap();

        parkingMakers.setIcon( clusterIcon );
        parkingMakers.setRadius( 100 );

    }

    private void initializeMap(){
        map.setTileSource( TileSourceFactory.HIKEBIKEMAP );
        map.setTilesScaledToDpi( true );

        map.setBuiltInZoomControls( true );
        map.setMultiTouchControls( true );
    }

    private void setZoom(){
        iMapController = map.getController();
        iMapController.setZoom( 14 );

        GeoPoint startPoint = new GeoPoint(41.378889, 2.14);
        iMapController.setCenter( startPoint );

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

    @Events.Subscribe("show-makers")
    private void putMakers(ArrayList<Park> parks){

        setupMakerOverlay();

        if( parks != null){
            for ( Park p: parks) {

                Marker marker = new Marker( map );

                GeoPoint point = new GeoPoint(
                        Double.parseDouble( p.getLat() ),
                        Double.parseDouble( p.getLon() )
                );

                marker.setPosition( point );

                marker.setAnchor( Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM );

                // places totals del parking
                int plazas = p.getBykes() + p.getSlots();
                // percentatge d'ocupacio
                int percent = p.getBykes() * 100 / plazas;

                if( percent < 25 ){
                    if( p.getType().equalsIgnoreCase( "bike" )){
                        marker.setIcon( getResources().getDrawable( R.drawable.b0_25 ) );
                    }else{
                        marker.setIcon( getResources().getDrawable( R.drawable.e0_25 ) );
                    }
                }else if( percent >= 25 && percent < 50){
                    if( p.getType().equalsIgnoreCase( "bike" )){
                        marker.setIcon( getResources().getDrawable( R.drawable.b25_50 ) );
                    }else{
                        marker.setIcon( getResources().getDrawable( R.drawable.e25_50 ) );
                    }
                }else if( percent >= 50 && percent < 75 ){
                    if( p.getType().equalsIgnoreCase( "bike" )){
                        marker.setIcon( getResources().getDrawable( R.drawable.b50_75 ) );
                    }else{
                        marker.setIcon( getResources().getDrawable( R.drawable.e50_75 ) );
                    }
                }else{
                    if( p.getType().equalsIgnoreCase( "bike" )){
                        marker.setIcon( getResources().getDrawable( R.drawable.b75_100 ) );
                    }else{
                        marker.setIcon( getResources().getDrawable( R.drawable.e75_100 ) );
                    }
                }

                if( p.getType().equalsIgnoreCase( "bike" ) ){
                    marker.setSnippet( "Bici" );
                } else {
                    marker.setSnippet( "Bici-Electrica" );
                }

                marker.setImage( getResources().getDrawable( R.drawable.parking ) );
                marker.setTitle( p.getName() );

                int disponibles = p.getSlots();

                marker.setSubDescription( "Places disponibles : " + disponibles );
                marker.setAlpha( 0.6f );

                parkingMakers.add(marker);

                parkingMakers.invalidate();
                map.invalidate();

            }

        }

    }
}
