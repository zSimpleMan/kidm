package com.example.dacnpm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class google_map extends FragmentActivity implements OnMapReadyCallback {

    String longitude;
    String latitude;

    GoogleMap map;
    LocationStatus locationStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.gg_map);
        mapFragment.getMapAsync(this::onMapReady);
        doFirstWork();
    }

    private void doFirstWork() {
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getCurrentLocation();
                mHandler.postDelayed(this, 3000);
            }
        }, 1000);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = new LatLng(10,106);
        map = googleMap;
        map.animateCamera( CameraUpdateFactory.zoomTo( 15.0f ) );
        map.addMarker(new MarkerOptions().position(latLng).title("My Kid"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
    }

    private void getCurrentLocation(){
        Call<LocationStatus> call = RetrofitClient.getInstance().getApi()
                .getlocationkid(41);
        call.enqueue(new Callback<LocationStatus>() {
            @Override
            public void onResponse(Call<LocationStatus> call, Response<LocationStatus> response) {
                locationStatus = response.body();
                Log.d("N", "onFailure: " + locationStatus.getResult() + locationStatus.getLatitude());

                LatLng latLng = new LatLng(locationStatus.getLatitude(), locationStatus.getLongitude());
                map.addMarker(new MarkerOptions().position(latLng).title("My Kid"));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));

            }

            @Override
            public void onFailure(Call<LocationStatus> call, Throwable t) {
                Log.d("N", "onFailure: ERROR");
            }
        });
    }
}
