package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.myapplication.JsonData.LocationStatus;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {
    String mycode;

    GoogleMap map;
    LocationStatus locationStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            mycode = (String) bundle.get("mycode");
        }
        else {
            Intent intent1 = new Intent(MainActivity.this, Login.class);
            startActivity(intent1);
        }

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.mymap);
        mapFragment.getMapAsync(this::onMapReady);
        doFirstWork();
    }

    private void doFirstWork() {
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getCurrentLocation();
                mHandler.postDelayed(this, 100000);
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
                .GetLocation(mycode);
        call.enqueue(new Callback<LocationStatus>() {
            @Override
            public void onResponse(Call<LocationStatus> call, Response<LocationStatus> response) {
                locationStatus = response.body();
                Log.d("N", "onFailure: " + locationStatus.getMessage());

                LatLng latLng = new LatLng(locationStatus.getLatitute(), locationStatus.getLongitute());
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
