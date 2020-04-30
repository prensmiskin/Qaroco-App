package com.one.qaroco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Main2Activity extends AppCompatActivity {
    Button btn_login,btn_register;
    EditText edittextone,edittexttwo;
    SharedPreferences sp;
    SharedPreferences.Editor e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        edittextone = findViewById(R.id.edittextone);
        edittexttwo = findViewById(R.id.edittexttwo);


        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);

            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                kisiekle();
            }
        });
      //  sp = getSharedPreferences("loginbilgileri",MODE_PRIVATE);
      //  e = sp.edit();


      //  edittextone.setText(mailtru);
      //  edittexttwo.setText(passwordtru);
        kisiekle();
    }

    public void kisiekle(){
         sp = getSharedPreferences("loginbilgileri",MODE_PRIVATE);
         e = sp.edit();




      //  edittextone.setText("d");

        //edittexttwo.setText("d");

        String url = "https://goldgym.pro/qarocologin.php";
        final String username = edittextone.getText().toString().trim();
        final String name = edittexttwo.getText().toString().trim();


        String mailtr = sp.getString("mail","null");
        String passwordtr = sp.getString("password","null");


        e.putString("mail",username);
        e.putString("password",name);
        e.commit();
        Log.e("SharedPreferencestwo",username+name);

        Log.e("SharedPreferences",mailtr+passwordtr);

        Log.e("sssggg",name+username);

        if ( mailtr == ""&&  mailtr == "null" && mailtr == null ){
            e.putString("mail",username);
            e.putString("password",name);

            e.commit();

            Log.e("retd","boş");
            Log.e("testxxx",mailtr+passwordtr);


        }else{

            Log.e("retd","dolu");




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

                        Intent bundle = new Intent(Main2Activity.this, Main5Activity.class);
                        String strName = username;
                        bundle.putExtra("STRING_I_NEED", strName);

                        Intent i = new Intent(getBaseContext(), Main5Activity.class);
                     i.putExtra("sesion",username);
                        startActivity(i);
                    }else {
                        Log.e("LOGİNbaşarızıs","olumsuz");
                        kisiekle();

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
                params.put("kisi_tel",name);
                return params;


            }
        };
        Volley.newRequestQueue(this).add(istek);


    }
    }
}
