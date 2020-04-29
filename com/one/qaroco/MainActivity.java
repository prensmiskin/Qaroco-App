package com.one.qaroco;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {
    Button btn_login,btn_register;
    EditText name,nametwo,namethree,mail,password,tc;
    Context context;
    SharedPreferences sp;
    SharedPreferences.Editor e;

    int i=0;

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        kisiekle();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     // Intent i = new Intent(getBaseContext(), Main5Activity.class);

      // startActivity(i);

        btn_login = findViewById(R.id.btn_login);
        name = findViewById(R.id.name);
        nametwo = findViewById(R.id.nametwo);
        namethree = findViewById(R.id.namethree);







        tc = findViewById(R.id.tc);
        mail = findViewById(R.id.mail);
        password = findViewById(R.id.password);

        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://goldgym.pro/qarocoregister.php";
                final String username = name.getText().toString().trim();
                final String usernametwo = nametwo.getText().toString().trim();
                final String usernamethree = namethree.getText().toString().trim();
                final String name = mail.getText().toString().trim();
                final String passwords = password.getText().toString().trim();
                final String tcs = tc.getText().toString().trim();



                StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Cevap",response);



                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String succes = jsonObject.getString("success");





                            if (succes.equals("36")){
                                Toast.makeText(getApplicationContext(), "Kayıt Başarılı", Toast.LENGTH_LONG).show();

                            }else  if (succes.equals("2")){
                                //Toast.makeText(getApplicationContext(), "Üye Adı Daha Önce Alınmış", Toast.LENGTH_LONG).show();

                            }
                            if (succes.equals("12")) {
                                   Toast.makeText(getApplicationContext(), "Tc Kimlik Bilgileri Hatalı", Toast.LENGTH_LONG).show();



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

                        params.put("kisi_ad",username);
                        params.put("kisi_adtwo",usernametwo);
                        params.put("kisi_adthree",usernamethree);
                        params.put("kisi_tel",name);
                        params.put("kisi_password",passwords);
                        params.put("kisi_tc",tcs);

                        return params;
                    }
                };
                Volley.newRequestQueue(MainActivity.this).add(istek);

                Log.e("ttt","ttt");
        Toast.makeText(getApplicationContext(), "Bilgilerinizi Doğru Girdiyseniz Size Bir Mail Gönderdik", Toast.LENGTH_LONG).show();

            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), Main2Activity.class);
                startActivity(i);
            }
        });

    }

    public void kisiekle(){
        sp = getSharedPreferences("loginbilgileri",MODE_PRIVATE);
        e = sp.edit();




        String mailtru = sp.getString("mail","null");
        String passwordtru = sp.getString("password","null");

        //  edittextone.setText("d");
        //edittexttwo.setText("d");

        String url = "https://goldgym.pro/qarocologin.php";
      //  final String username = edittextone.getText().toString().trim();
     //   final String name = edittexttwo.getText().toString().trim();

     /*   e.putString("mail",username);
        e.putString("password",name);
        e.commit();
*/
     //   String mailtr = sp.getString("mail","null");
      //  String passwordtr = sp.getString("password","null");
       // Log.e("SharedPreferencestwo",mailtr+passwordtr);

       // Log.e("SharedPreferences",mailtr+passwordtr);




      //  Log.e("sssggg",name+username);



        StringRequest istek = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Cevap",response);

                try{

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    String sesion = jsonObject.getString("sesion");


                    if (success.equals("1")){
                        Log.e("LOGİN","BAŞARILI");

                        Intent ii = new Intent(MainActivity.this, Main5Activity.class);
                        String strName = mailtru;
                        ii.putExtra("STRING_I_NEED", strName);

                        Intent i = new Intent(getBaseContext(), Main5Activity.class);
                        i.putExtra("sesion",mailtru);
                        startActivity(i);
                    }else {
                        Log.e("LOGİNbaşarızıs","olumsuz");
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

                params.put("kisi_ad",mailtru);
                params.put("kisi_tel",passwordtru);
                return params;


            }
        };
        Volley.newRequestQueue(this).add(istek);


    }

}

