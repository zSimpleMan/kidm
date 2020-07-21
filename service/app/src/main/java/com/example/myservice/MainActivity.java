package com.example.myservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    String mycode;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onResume() {
        super.onResume();
        if (broadcastReceiver == null) {
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    double la = (double) intent.getExtras().get("latitude");
                    double lo = (double) intent.getExtras().get("longitude");

                    Call<LocationStatus> call = RetrofitClient.getInstance().getApi()
                            .UpdateLocation(mycode, la, lo);
                    call.enqueue(new Callback<LocationStatus>() {
                        @Override
                        public void onResponse(Call<LocationStatus> call, Response<LocationStatus> response) {
                            Log.d("m", "onResponse: " + response.body().getMessage());
                        }

                        @Override
                        public void onFailure(Call<LocationStatus> call, Throwable t) {
                            Log.d("m", "onResponse: ff" );
                        }
                    });

                }
            };
            registerReceiver(broadcastReceiver, new IntentFilter("location_update"));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
        }
    }


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

        if (runtime_permissions()){
            enable_button();
        }
    }
    private void enable_button() {

                Intent i = new Intent(getApplicationContext(), GPS_Service.class);
                startService(i);

    }

    private boolean runtime_permissions(){
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission
                .ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest
                .permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            return false;
        }
        return  true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED){
                enable_button();
            }
            else {
                runtime_permissions();
            }
        }
    }
}
