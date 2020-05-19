package com.example.dacnpm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    TextView txt_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txt_email = (TextView)findViewById(R.id.txt_email);
        Intent intent = getIntent();
        txt_email.setText(intent.getStringExtra("email"));

    }
}
