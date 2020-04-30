package com.one.qaroco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.MapFragment;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Main5Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private Fragment fragment;
    MapsActivity2 mapsActivity2;
    private int izinKontrol;
    private LocationManager locationManager;
    private String konumSaglayici = "gps";
    private TextView textView4,textView1;
    public String t = "hhh";
    String sesion;

    SharedPreferences sp;
    SharedPreferences.Editor e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer);
        textView4 = findViewById(R.id.textView4);
        Toast.makeText(getApplicationContext(),"Birinci",Toast.LENGTH_SHORT).show();

        setSupportActionBar(toolbar);

       kisiekle();
        Intent i = new Intent(Main5Activity.this, Fragment_Seven.class);
        String strName = "sesion";
        i.putExtra("NEED", strName);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
           sesion = bundle.getString("STRING_I_NEED");


            Toast.makeText(getApplicationContext(),"Kullanıcı Mail"+sesion,Toast.LENGTH_SHORT).show();


        }
       // fragment = new Fragment_four();
        fragment = new Fragment_Seven();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_tutucu,fragment).commit();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,0,0);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
      navigationView.setNavigationItemSelectedListener(this);
      /*  View baslik = navigationView.inflateHeaderView(R.layout.navigation_baslik);

        final String username = textView1.getText().toString().trim();

        TextView tv = (TextView)baslik.findViewById(R.id.textView4);
        tv.setText(""+username);*/
    }



    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() ==  R.id.nav_item_birinci){
            Log.e("fragment","1");
            fragment = new Fragment_Seven();
            Toast.makeText(getApplicationContext(),"Birinci",Toast.LENGTH_SHORT).show();
        }
        if (menuItem.getItemId() ==  R.id.nav_item_ikinci){
            Toast.makeText(getApplicationContext(),"Birinci",Toast.LENGTH_SHORT).show();
            fragment = new Fragmnet_İkinci();
            Log.e("fragment","1");

        }
        if (menuItem.getItemId() ==  R.id.nav_item_ucuncu){
            Log.e("fragmentddd","1");

            Toast.makeText(getApplicationContext(),"çIKIŞ",Toast.LENGTH_SHORT).show();


            e  =  getSharedPreferences("loginbilgileri", MODE_PRIVATE).edit();
            e.clear();
            e.commit();


            Intent i = new Intent(getBaseContext(), Main2Activity.class);
            startActivity(i);

        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_tutucu,fragment).commit();

        drawer.closeDrawer(GravityCompat.START);
        fragment = new Fragment_four();

        return true;


    }
    public void kisiekle(){
        String url = "https://goldgym.pro/qarocoveri.php";



        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Cevap",response);


                try{
                    JSONObject jsonObject = new JSONObject(response);
                    String succes = jsonObject.getString("employees");
                    JSONArray dal = jsonObject.getJSONArray("employees");

                    for (int i=0; i<dal.length();i++){
                        JSONObject f = dal.getJSONObject(i);
                        String name = f.getString("name");
                        navigationView.setNavigationItemSelectedListener(Main5Activity.this::onNavigationItemSelected);
                        View baslik = navigationView.inflateHeaderView(R.layout.navigation_baslik);

                        // final String username = textView1.getText().toString().trim();

                        TextView tv = (TextView)baslik.findViewById(R.id.textView4);
                        tv.setText(""+name);
                        Log.e("succes",name);
                        Toast.makeText(getApplicationContext(), "Hoş Geldin"+name, Toast.LENGTH_LONG).show();



                    }









                    if (succes.equals("1")){
                        Toast.makeText(getApplicationContext(), "Kayıt Başarılı", Toast.LENGTH_LONG).show();

                    }else  if (succes.equals("2")){
                        //Toast.makeText(getApplicationContext(), "Üye Adı Daha Önce Alınmış", Toast.LENGTH_LONG).show();

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

                params.put("kisi_ad",sesion);


                return params;
            }
        };
        Volley.newRequestQueue(this).add(istek);

    }


}
