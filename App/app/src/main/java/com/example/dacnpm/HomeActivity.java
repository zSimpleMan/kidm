package com.example.dacnpm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    TextView txt_email;
    Button viewLct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txt_email = (TextView)findViewById(R.id.txt_email);
        Intent intent = getIntent();
        txt_email.setText(intent.getStringExtra("email"));
        viewLct = (Button)findViewById(R.id.btnViewLoct);
        viewLct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ggmapScreen = new Intent(HomeActivity.this, google_map.class);
                startActivity(ggmapScreen);
            }
        });

    }
}
