package com.example.myapplication;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.myapplication.JsonData.LocationStatus;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {
    String mycode;

    Marker marker = null;

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
                mHandler.postDelayed(this, 6000);
            }
        }, 1000);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        getCurrentLocation();
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
                if(marker != null){
                    marker.remove();
                    marker = null;
                }
                marker = map.addMarker(new MarkerOptions().position(latLng).title("My Kid")
                        .icon(bitmapDescriptorFromVector(MainActivity.this, R.drawable.ic_accessibility_black_24dp)));

                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));

            }

            @Override
            public void onFailure(Call<LocationStatus> call, Throwable t) {
                Log.d("N", "onFailure: ERROR");
            }
        });
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_accessibility_black_24dp);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 40, vectorDrawable.getIntrinsicHeight() + 20);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

}
