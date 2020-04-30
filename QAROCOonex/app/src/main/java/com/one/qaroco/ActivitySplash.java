package com.one.qaroco;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ActivitySplash extends AppCompatActivity {

    ProgressBar splashProgress;
    int SPLASH_TIME = 3000; //This is 3 seconds
    SharedPreferences sp;
    SharedPreferences.Editor e;
    String ed = "";
    Integer F = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent z = new Intent(ActivitySplash.this, Main5Activity.class);
       startActivity(z);
     //  kisiekle();
     //  setContentView(R.layout.activity_splash);

 /*     //This is additional feature, used to run a progress bar
        splashProgress = findViewById(R.id.splashProgress);
        playProgress();

        //Code to start timer and take action after the timer ends
      new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sp = getSharedPreferences("loginbilgileri",MODE_PRIVATE);
                e = sp.edit();



                String mailtru = sp.getString("mail","null");
                String passwordtru = sp.getString("password","null");

                if (mailtru == "null" &&  mailtru == ""){
                    Intent z = new Intent(ActivitySplash.this, Main2Activity.class);
                    startActivity(z);

                    Log.e("retdsss","boş");

                }else{

                    Log.e("retdsss",mailtru);


                }


                File f = new File(
                        "/data/data/com.one.qaroco/shared_prefs/loginbilgileri.xml");
                if (f.exists()) {
                    Log.d("TAGdfddf", "1");
                }else {
                    Log.d("TAGdfddf", "2");
                    Intent z = new Intent(ActivitySplash.this, Main2Activity.class);
                    startActivity(z);
                }
                //Do any action here. Now we are moving to next page
                //Intent mySuperIntent = new Intent(ActivitySplash.this, MainActivity.class);
               // startActivity(mySuperIntent);

                //This 'finish()' is for exiting the app when back button pressed from Home page which is ActivityHome
                finish();


            }
        }, SPLASH_TIME);



*/
    }

  /*  @Override
    protected void onPause() {
        super.onPause();
        Intent D = new Intent(ActivitySplash.this, Main2Activity.class);
        startActivity(D);
    }

*/

    //Method to run progress bar for 5 seconds
    private void playProgress() {
        ObjectAnimator.ofInt(splashProgress, "progress", 300)
                .setDuration(5000)
                .start();
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

        Log.e("SharedPreferencesgg",mailtru+passwordtru);




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

                        Intent ii = new Intent(ActivitySplash.this, Main5Activity.class);
                        String strName = mailtru;
                        ii.putExtra("STRING_I_NEED", strName);

                        startActivity(ii);
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
                Log.e("loginhata","olumsuz");

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