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

    Button btn1, btn2, btn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txt_email = (TextView)findViewById(R.id.txt_email);
        btn1 = (Button)findViewById(R.id.btn_1);
        btn2 = (Button)findViewById(R.id.btn_2);
        btn3 = (Button)findViewById(R.id.btn_3);

        Intent intent = getIntent();

        txt_email.setText(intent.getStringExtra("email"));
        viewLct = (Button)findViewById(R.id.btn_1);

        loginStatus user = (loginStatus) intent.getSerializableExtra("user");
        txt_email.setText(user.getEmail());

        viewLct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ggmapScreen = new Intent(HomeActivity.this, google_map.class);
                startActivity((ggmapScreen));
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileActive = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(profileActive);
            }
        });

    }
}
