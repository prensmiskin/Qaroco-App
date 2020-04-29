package com.one.qaroco;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener {
    private int izinKontrol;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    private GoogleMap mMap;
    Fragment fragment;

    private Button btnz,buttonone,buttonx;
    private TextView textone,texttwo;
    private String konumSaglayici = "gps";
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        textone = findViewById(R.id.textone);
        texttwo = findViewById(R.id.texttwo);

        btnz = findViewById(R.id.btn);
        btnz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Üçüncü",Toast.LENGTH_SHORT).show();

                Log.e("gffg","fgfggf");



            }
        });




    }

    public void ff(){

        izinKontrol = ContextCompat.checkSelfPermission(MapsActivity2.this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(izinKontrol != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MapsActivity2.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},100);

        }else{

            Location konum = locationManager.getLastKnownLocation(konumSaglayici);
            if(konum!=null){
                onLocationChanged(konum);
            }else{
                textone.setText("Konum Aktif DEĞİL :");
                texttwo.setText("Konum Aktif DEĞİL :");

            }


        }
    }




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setPadding(2, 250, 120, 0);

        izinKontrol = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (izinKontrol != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity2.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.INTERNET},100);



        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100){
            izinKontrol = ContextCompat.checkSelfPermission(MapsActivity2.this, Manifest.permission.ACCESS_FINE_LOCATION);


            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getApplicationContext(), "Kayıt Başarılı", Toast.LENGTH_LONG).show();


                Location konum = locationManager.getLastKnownLocation(konumSaglayici);
                if(konum!=null){
                    onLocationChanged(konum);
                }else{
                    textone.setText("Konum Aktif DEĞİL :");
                    texttwo.setText("Konum Aktif DEĞİL :");

                }


            }else{
                Toast.makeText(getApplicationContext(), "olumsuz", Toast.LENGTH_LONG).show();

            }
        }
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onLocationChanged(Location location) {
        double  enlem = location.getLatitude();
        double  boylam = location.getLongitude();





        textone.setText("Boylam :"+enlem);
        texttwo.setText("Enlem :"+boylam);

        LatLng sydneya = new LatLng(enlem, boylam);
        mMap.addMarker(new MarkerOptions().position(sydneya).title("QAROCOsss"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydneya));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
