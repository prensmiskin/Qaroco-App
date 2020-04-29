# Oğuzhan Gümüş


# Detay

[![Kişisel Web Sayfam](https://www.oguzhangumus.net/)](https://www.oguzhangumus.net/)




# Neden Mapbox ? 
 Çünkü google map ile kıyaslandığında çok ekonomik. Mapbox geliştiricilere 250.000 request ücretsiz kullanım sunuyor.
 Google ise sadece 1 ay ücretsiz sunum yapıyor. Bu da mapbox'ın çok iyi bir harita hizmeti olduğunu kanıtlıyor.
 Ayrıca tutorial'ları çok güzel ve anlaşılır. 
 



# Kullandığım Kütüphaneler
```
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'androidx.appcompat:appcompat:1.1.0'
    implementation 'com.google.android.material:material:1.1.0'
    implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
    implementation 'androidx.vectordrawable:vectordrawable:1.1.0'
    implementation 'androidx.navigation:navigation-fragment:2.2.2'
    implementation 'androidx.navigation:navigation-ui:2.2.2'
    implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'
    implementation 'com.google.android.gms:play-services-maps:17.0.0'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    testImplementation 'junit:junit:4.13'
    androidTestImplementation 'androidx.test.ext:junit:1.1.1'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
    implementation 'com.android.volley:volley:1.1.1'
    implementation 'com.github.GrenderG:Toasty:1.4.2'
    implementation 'com.google.android.gms:play-services-location:17.0.0'
    implementation 'androidx.appcompat:appcompat:1.2.0-beta01'
    implementation 'com.sothree.slidinguppanel:library:3.4.0'
    implementation 'com.mapbox.mapboxsdk:mapbox-android-sdk:8.6.2'
    implementation 'com.mapbox.mapboxsdk:mapbox-android-navigation:0.43.0-alpha.1'
    implementation 'com.mapbox.mapboxsdk:mapbox-android-plugin-locationlayer:0.11.0'
    implementation 'com.mapbox.mapboxsdk:mapbox-android-plugin-annotation-v9:0.8.0'
    implementation 'com.mapbox.mapboxsdk:mapbox-android-sdk:8.4.0'
    implementation 'com.squareup.retrofit2:retrofit:2.8.1'
    implementation 'com.mapbox.mapboxsdk:mapbox-android-plugin-locationlayer:0.11.0'
    implementation 'com.mapbox.mapboxsdk:mapbox-android-plugin-places-v9:0.11.0'


}

# Usage of Video-Player-Manager
```
 @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                initSearchFab();

                addUserLocations();

                // Add the symbol layer icon to map for future use
                style.addImage(symbolIconId, BitmapFactory.decodeResource(
                        getActivity().getResources(), R.drawable.map_default_map_marker));

                // Create an empty GeoJSON source using the empty feature collection
                setUpSource(style);

                // Set up a new symbol layer for displaying the searched location's feature coordinates
                setupLayer(style);
            }
        });
    }

```
Put multiple VideoPlayerViews into your xml file.
In most cases you also need a images above that will be shown when playback is stopped.
```
 StringRequest istek = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
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
        }, new com.android.volley.Response.ErrorListener() {
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
                params.put("en",en);
                params.put("boy",boy);
                params.put("entwo",entwo);
                params.put("boytwo",boytwo);


                return params;
            }
        };
        Volley.newRequestQueue(getActivity().getApplicationContext()).add(istek);


    }
```


# The Demo of Video-Player-Manager:
![video_player_manager_demo](https://user-images.githubusercontent.com/40228440/80598239-b7ce4280-8a31-11ea-8ed9-7810fd8d1a12.gif)


```
# The Demo of List-Visibility-Utils:
![visibility_utils_demo](https://user-images.githubusercontent.com/40228440/80598643-565aa380-8a32-11ea-84b7-e72768b95168.gif)


# License

Copyright 2020 Oğuzhan Gümüş

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
