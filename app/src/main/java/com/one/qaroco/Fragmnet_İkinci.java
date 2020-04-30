package com.one.qaroco;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Fragmnet_İkinci extends Fragment implements LocationListener {
    MapView mMapView;
    private GoogleMap googleMap;
    private int izinKontrol;
    private static final int REQUEST_CODE = 101;
    private String konumSaglayici = "gps";
    Context context;
    private LocationManager locationManager;
    private TextView textone,texttwo;
    private GoogleMap mMap;
    private Button btnkorgo;
    private static final String TAG = "LocationFragment";
    double longitudeDouble;
    double latitudeDouble;

    String snippet;
    String title;
    Location location;
    String myAddress;

    String LocationId;
    String CityName;
    String imageURL;
    LatLng CENTER = null;
    Double enlem,boylam;
    Button slideButton,b1, b2,b3,b4,btnSaatSec;
    SlidingDrawer slidingDrawer;
    EditText edittextone,edittexttwo,edittextthree,edittextfour;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ikinci_layout, container, false);
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);



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




        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);

                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
                }else{
                    //Do your work
                }
                googleMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Map
              /*  LatLng sydney = new LatLng(enlem, boylam);
                googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


*/


            }
        });



        return rootView;
    }
    public void ff(){

        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
        }else{

            Location konum = locationManager.getLastKnownLocation(konumSaglayici);
            if(konum!=null){
                Toast.makeText(getContext(), "olumsuz", Toast.LENGTH_LONG).show();

            }else{


            }


        }

    }


    public Fragmnet_İkinci() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        Log.i(TAG, "onResume");
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
        }else{
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }
    }
    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
        locationManager.removeUpdates(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onLocationChanged(Location location) {
        double  enlem = location.getLatitude();
        double  boylam = location.getLongitude();
        Log.i(TAG, String.valueOf(location.getLatitude()));
        Log.i(TAG, String.valueOf(location.getLongitude()));

        Log.e("lokasyon", String.valueOf(enlem));

      //  textone.setText("Boylam :"+location.getLatitude());
        //texttwo.setText("Enlem :"+location.getLongitude());

      //  textone.setText("Boylam :"+enlem);
     //   texttwo.setText("Enlem :"+boylam);

        enlem = location.getLatitude();
        boylam = location.getLongitude();

        LatLng sydney = new LatLng(enlem, boylam);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

        // For zooming automatically to the location of the marker
    // CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
     //   googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.i(TAG, "Provider " + provider + " has now status: " + status);

    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.i(TAG, "Provider " + provider + " is enabled");

    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i(TAG, "Provider " + provider + " is disabled");

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100){
            int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);


            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getContext(), "Kayıt Başarılı", Toast.LENGTH_LONG).show();


                Location konum = locationManager.getLastKnownLocation(konumSaglayici);
                if(konum!=null){
                    onLocationChanged(konum);
                }else{
                    textone.setText("Konum Aktif DEĞİL :");
                    texttwo.setText("Konum Aktif DEĞİL :");

                }


            }else{
                Toast.makeText(getContext(), "olumsuz", Toast.LENGTH_LONG).show();

            }
        }
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

}