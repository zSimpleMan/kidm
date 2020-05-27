package com.example.dacnpm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

public class google_map extends FragmentActivity implements OnMapReadyCallback {

    String longitude;
    String latitude;

    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.gg_map);
        mapFragment.getMapAsync(this::onMapReady);

    }

    public  void  loopRequest()
    {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getCurrentLocation();

                Float lo = Float.parseFloat(longitude);
                Float la = Float.parseFloat(latitude);

                handler.postDelayed(this, 3000);
            }
        },1000);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng VietNam = new LatLng(10.762622,106.660172);
        map.animateCamera( CameraUpdateFactory.zoomTo( 15.0f ) );
        map.addMarker(new MarkerOptions().position(VietNam).title("Việt Nam"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(VietNam, 17));
    }

    public void getCurrentLocation(){
        String URL ="http://10.0.3.2:3000/api/users/kid-getlocation/41";

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.e("Longitude: ", response.getString("longitude"));
                            Log.e("Attitude: ", response.getString("latitude"));
                            longitude = response.getString("longitude");
                            latitude = response.getString("latitude");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("rest response", error.toString());
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
}
