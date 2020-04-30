package com.one.qaroco;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SlidingDrawer;
import android.widget.Toast;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.maps.SupportMapFragment;
import com.mapbox.mapboxsdk.plugins.annotation.OnSymbolClickListener;
import com.mapbox.mapboxsdk.plugins.annotation.OnSymbolDragListener;
import com.mapbox.mapboxsdk.plugins.annotation.OnSymbolLongClickListener;
import com.mapbox.mapboxsdk.plugins.annotation.Symbol;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import org.json.JSONException;
import org.json.JSONObject;
import static android.os.Looper.getMainLooper;
public class FragmentBirinci extends Fragment implements PermissionsListener {
    private MapboxMap mapboxMap;
    private PermissionsManager permissionsManager;
    private Fragment fragment;
    Button slideButton,btnkorgo, b2,b3,b4,btnSaatSec;
    SlidingDrawer slidingDrawer;
    EditText edittextone,edittexttwo,edittextthree,edittextfour;
    Location location;
    LocationEngine locationEngine;
    LocationManager locationManager;
    Double  latitude;
    Double longitude;
    Context context;
    private MapView mapView;
    private static final String MAKI_ICON_CAFE = "cafe-15";
    private static final String MAKI_ICON_HARBOR = "harbor-15";
    private static final String MAKI_ICON_AIRPORT = "airport-15";
    private SymbolManager symbolManager;
    public double f =60.169091 ;
    public double g =24.939876 ;
    private Symbol symbol;
    private final String MAPKIT_API_KEY = "f38d41ba-b312-42e0-be26-1086722172d3";
    private static final long DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L;
    private static final long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5;
    private LocationChangeListeningActivityLocationCallback callback =
            new LocationChangeListeningActivityLocationCallback(this);
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mapbox.getInstance(getActivity() , "pk.eyJ1Ijoib3V6aGFuZ3VtdXMiLCJhIjoiY2s2ZXoyd2UxMXdyMDNscGs3YXJ0c3AxeSJ9.6lGALk8uD-8iraVofMLQKA");
        View rootView = inflater.inflate(R.layout.fragment_birinci_layout, container, false);
        Mapbox.getInstance(getActivity(), getString(R.string.access_token));
        mapView = rootView.findViewById(R.id.mapView);
        edittextone = rootView.findViewById(R.id.edittextone);
        edittexttwo = rootView.findViewById(R.id.edittexttwo);
        edittextthree = rootView.findViewById(R.id.edittextthree);

        edittextfour = rootView.findViewById(R.id.edittextfour);
        btnkorgo = rootView.findViewById(R.id.btnkorgo);
        btnkorgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kisiekle();
                Toast.makeText(getContext(), "Kayıt Başarılı", Toast.LENGTH_LONG).show();
            }
        });
        slideButton = (Button) rootView.findViewById(R.id.slideButton);
        slidingDrawer = (SlidingDrawer) rootView.findViewById(R.id.SlidingDrawer);
        slidingDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {
                slideButton.setText("Kapat");
            }
        });
        slidingDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
            @Override
            public void onDrawerClosed() {
                slideButton.setText("Kargo Gönder");
            }
        });
        // Mapbox access token is configured here. This needs to be called either in your application
        // object or in the same activity which contains the mapview.
        Mapbox.getInstance(getActivity(), getString(R.string.access_token));
        // Create supportMapFragment
        SupportMapFragment mapFragment;
        if (savedInstanceState == null) {
            // Create fragment
            final FragmentTransaction transaction = getFragmentManager().beginTransaction();
            // Build a Mapbox map
            MapboxMapOptions options = MapboxMapOptions.createFromAttributes(getActivity(), null);
            options.camera(new CameraPosition.Builder()
                    .target(new LatLng(40.1544380, -32.9916710))
                    .zoom(0)
                    .build());
            // Create map fragment
            mapFragment = SupportMapFragment.newInstance(options);
            // Add map fragment to parent container
            transaction.add(R.id.location_frag_container, mapFragment, "com.mapbox.map");
            transaction.commit();
        } else {
            mapFragment = (SupportMapFragment) getFragmentManager().findFragmentByTag("com.mapbox.map");
        }
        if (mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(@NonNull MapboxMap mapboxMap) {
                    FragmentBirinci.this.mapboxMap = mapboxMap;
                    List<Feature> symbolLayerIconFeatureList = new ArrayList<>();
                    symbolLayerIconFeatureList.add(Feature.fromGeometry(
                            Point.fromLngLat(-40.127088, -32.991457)));
                    symbolLayerIconFeatureList.add(Feature.fromGeometry(
                            Point.fromLngLat(-40.128527, -33.005412)));
                    symbolLayerIconFeatureList.add(Feature.fromGeometry(
                            Point.fromLngLat(-40.124899, -30.583266)));
                    mapboxMap.setStyle(Style.TRAFFIC_DAY, new Style.OnStyleLoaded() {
                        @Override
                        public void onStyleLoaded(@NonNull Style style) {

                            enableLocationComponent(style);
                            // Set up a SymbolManager instance
                            symbolManager = new SymbolManager(mapView, mapboxMap, style);

                            symbolManager.setIconAllowOverlap(true);
                            symbolManager.setTextAllowOverlap(true);

                            // Add symbol at specified lat/lon

                            symbol = symbolManager.create(new SymbolOptions()
                                    .withLatLng(new LatLng(40.1544380, 32.9916710))
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
            });
        }
        return rootView;
    }
    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(getActivity())) {
            // Get an instance of the LocationComponent.
            LocationComponent locationComponent = mapboxMap.getLocationComponent();
            // Log.e("lokasyon", String.valueOf(locationComponent.isLocationComponentActivated()));
            // Activate the LocationComponent
            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions.builder(getActivity(), loadedMapStyle).build());
            // Enable the LocationComponent so that it's actually visible on the map
            locationComponent.setLocationComponentEnabled(true);
            // Set the LocationComponent's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);
            // Set the LocationComponent's render mode
            locationComponent.setRenderMode(RenderMode.NORMAL);
            initLocationEngine();
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(getActivity());
            //fragment = new FragmentBirinci();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(getActivity(), R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            Toast.makeText(getActivity(), R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            getActivity().finish();
        }
    }
    private void setCameraPosition(Location location){
        mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()),13.0));
    }
    public void kisiekle(){
        String url = "https://goldgym.pro/qarocokorgo.php";
        final String one = edittextone.getText().toString().trim();
        final String two = edittexttwo.getText().toString().trim();
        final String three = edittextthree.getText().toString().trim();
        final String four = edittextfour.getText().toString().trim();
        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Cevap",response);
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    String succes = jsonObject.getString("success");
                    if (succes.equals("1")){
                        Toast.makeText(getContext(), "Kayıt Başarılı", Toast.LENGTH_LONG).show();
                    }else  if (succes.equals("2")){
                        Toast.makeText(getContext(), "Üye Adı Daha Önce Alınmış", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("al",one);
                params.put("et",two);
                params.put("tel",three);
                params.put("saat",four);
                return params;
            }
        };
        Volley.newRequestQueue(getActivity().getApplicationContext()).add(istek);
    }
/*
    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        mapboxMap.setStyle(Style.DARK, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                enableLocationComponent(style);

                // Set up a SymbolManager instance
                symbolManager = new SymbolManager(mapView, mapboxMap, style);

                symbolManager.setIconAllowOverlap(true);
                symbolManager.setTextAllowOverlap(true);

                // Add symbol at specified lat/lon
                symbol = symbolManager.create(new SymbolOptions()
                        .withLatLng(new LatLng(40.12443999, 32.9916726))
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
*/
    private class LocationChangeListeningActivityLocationCallback
            implements LocationEngineCallback<LocationEngineResult> {
        private final WeakReference<FragmentBirinci> activityWeakReference;
        LocationChangeListeningActivityLocationCallback(FragmentBirinci activity) {
            this.activityWeakReference = new WeakReference<>(activity);
        }
        /**
         * The LocationEngineCallback interface's method which fires when the device's location has changed.
         *
         * @param result the LocationEngineResult object which has the last known location within it.
         */
        @Override
        public void onSuccess(LocationEngineResult result) {
            FragmentBirinci activity = activityWeakReference.get();
            if (activity != null) {
                Location location = result.getLastLocation();
                if (location == null) {
                    return;
                }
                // Create a Toast which displays the new location's coordinates
                Log.e("LOKASYONE", String.valueOf(result.getLastLocation().getLatitude()));
              /*  Toast.makeText(getContext(), String.format(activity.getString(R.string.new_location),
                        String.valueOf(result.getLastLocation().getLatitude()),
                        String.valueOf(result.getLastLocation().getLongitude())),
                        Toast.LENGTH_SHORT).show();*/
                // Pass the new location to the Maps SDK's LocationComponent
                if (activity.mapboxMap != null && result.getLastLocation() != null) {
                    activity.mapboxMap.getLocationComponent().forceLocationUpdate(result.getLastLocation());
                }
            }
        }
        /**
         * The LocationEngineCallback interface's method which fires when the device's location can't be captured
         *
         * @param exception the exception message
         */
        @Override
        public void onFailure(@NonNull Exception exception) {
            FragmentBirinci activity = activityWeakReference.get();
            if (activity != null) {
                Toast.makeText(getContext(), exception.getLocalizedMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
    @SuppressLint("MissingPermission")
    private void initLocationEngine() {
        locationEngine = LocationEngineProvider.getBestLocationEngine(getContext());
        LocationEngineRequest request = new LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
                .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME).build();
        locationEngine.requestLocationUpdates(request, callback, getMainLooper());
        locationEngine.getLastLocation(callback);
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



