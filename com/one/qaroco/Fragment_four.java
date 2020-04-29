package com.one.qaroco;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.annotation.OnSymbolClickListener;
import com.mapbox.mapboxsdk.plugins.annotation.OnSymbolDragListener;
import com.mapbox.mapboxsdk.plugins.annotation.OnSymbolLongClickListener;
import com.mapbox.mapboxsdk.plugins.annotation.Symbol;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions;


public class Fragment_four extends Fragment implements
        OnMapReadyCallback {
    private MapView mapView;
    private static final String MAKI_ICON_CAFE = "cafe-15";
    private static final String MAKI_ICON_HARBOR = "harbor-15";
    private static final String MAKI_ICON_AIRPORT = "airport-15";
    private SymbolManager symbolManager;
    public double f =60.169091 ;
    public double g =24.939876 ;
    private Symbol symbol;
    private final String MAPKIT_API_KEY = "f38d41ba-b312-42e0-be26-1086722172d3";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mapbox.getInstance(getActivity() , "pk.eyJ1Ijoib3V6aGFuZ3VtdXMiLCJhIjoiY2s2ZXoyd2UxMXdyMDNscGs3YXJ0c3AxeSJ9.6lGALk8uD-8iraVofMLQKA");
        View rootView = inflater.inflate(R.layout.fragment_four_layout, container, false);


        Mapbox.getInstance(getActivity(), getString(R.string.access_token));


        mapView = rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return rootView;

    }


    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        mapboxMap.setStyle(Style.DARK, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {

                // Set up a SymbolManager instance
                symbolManager = new SymbolManager(mapView, mapboxMap, style);

                symbolManager.setIconAllowOverlap(true);
                symbolManager.setTextAllowOverlap(true);

                // Add symbol at specified lat/lon
                symbol = symbolManager.create(new SymbolOptions()
                        .withLatLng(new LatLng(60.169091, 24.939876))
                        .withIconImage(MAKI_ICON_HARBOR)
                        .withIconSize(2.0f)
                        .withDraggable(true));

                // Add click listener and change the symbol to a cafe icon on click
                symbolManager.addClickListener(new OnSymbolClickListener() {
                    @Override
                    public void onAnnotationClick(Symbol symbol) {
                        Toast.makeText(getActivity(),
                                getString(R.string.clicked_symbol_toast), Toast.LENGTH_SHORT).show();
                       symbol.setIconImage(MAKI_ICON_CAFE);
                       symbolManager.update(symbol);
                    }
                });

                // Add long click listener and change the symbol to an airport icon on long click
                symbolManager.addLongClickListener((new OnSymbolLongClickListener() {
                    @Override
                    public void onAnnotationLongClick(Symbol symbol) {
                        Toast.makeText(getActivity(),
                                getString(R.string.long_clicked_symbol_toast), Toast.LENGTH_SHORT).show();
                        symbol.setIconImage(MAKI_ICON_AIRPORT);
                        symbolManager.update(symbol);
                    }
                }));

                symbolManager.addDragListener(new OnSymbolDragListener() {
                    @Override
                    // Left empty on purpose
                    public void onAnnotationDragStarted(Symbol annotation) {
                    }

                    @Override
                    // Left empty on purpose
                    public void onAnnotationDrag(Symbol symbol) {
                    }

                    @Override
                    // Left empty on purpose
                    public void onAnnotationDragFinished(Symbol annotation) {
                    }
                });
                Toast.makeText(getContext(),
                        getString(R.string.google_maps_key), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}