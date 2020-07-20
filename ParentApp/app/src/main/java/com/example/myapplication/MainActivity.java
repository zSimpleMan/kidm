package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    String mycode;

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
    }
}
