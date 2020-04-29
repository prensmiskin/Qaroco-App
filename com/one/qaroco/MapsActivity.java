package com.one.qaroco;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.legacy.app.ActionBarDrawerToggle;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;


public class MapsActivity  extends FragmentActivity implements OnMapReadyCallback,LocationListener,NavigationView.OnNavigationItemSelectedListener {

    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
   private NavigationView navigationView;
   private DrawerLayout drawerLayout;
   private Toolbar toolbar;


    private int izinKontrol;
    private Button btn,buttonone,buttonx;
    private TextView textone,texttwo;
    private String konumSaglayici = "gps";
    private LocationManager locationManager;

    private GoogleMap mMap;
    private double d, e;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        navigationView = findViewById(R.id.nav);
        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);


navigationView.setNavigationItemSelectedListener(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);



        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        buttonone = findViewById(R.id.buttonone);
        buttonx = findViewById(R.id.buttonx);
        buttonx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);

                Intent i = new Intent(getBaseContext(), MainActivity.class);

                startActivity(i);
            }
        });


        buttonone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);



                }else {
                    drawerLayout.openDrawer(GravityCompat.START);

                }


            }

        });
     btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ff();


            }
        });


        Log.e("ccc", "ccc");

       textone = findViewById(R.id.textone);
        texttwo = findViewById(R.id.texttwo);



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_menu,menu);
        return true;
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);

                    }else{
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory
                     (Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);



        }
    }

    public void ff(){

        izinKontrol = ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(izinKontrol != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MapsActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},100);

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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100){
            izinKontrol = ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);


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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setPadding(0, 150, 0, 0);

        // Add a marker in Sydney and move the camera




    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if(menuItem.getItemId() == R.id.buttonx){
            Log.e("r", "r");


        }
        return false;
    }
}






























