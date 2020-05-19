package com.example.dacnpm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity {

    EditText email, password;
    Button login, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email= (EditText)findViewById(R.id.edt_email);
        password = (EditText)findViewById(R.id.edt_password);
        login = (Button)findViewById(R.id.btn_login);
        register = (Button)findViewById(R.id.btn_register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetData();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regis = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(regis);
            }
        });
    }

    private void GetData() {
        Call<loginStatus> call = RetrofitClient
                .getInstance()
                .getApi()
                .loginUser(email.getText().toString(),password.getText().toString());
        call.enqueue(new Callback<loginStatus>() {
            @Override
            public void onResponse(Call<loginStatus> call, Response<loginStatus> response) {
                    loginStatus loginStatus = response.body();
                    if(loginStatus.getEmail()==null){
                        Toast.makeText(MainActivity.this, loginStatus.getLogin(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(MainActivity.this, loginStatus.getLogin(), Toast.LENGTH_SHORT).show();
                        Intent home = new Intent(MainActivity.this,HomeActivity.class);
                        home.putExtra("email", loginStatus.getEmail());
                        startActivity(home);
                    }

            }

            @Override
            public void onFailure(Call<loginStatus> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
