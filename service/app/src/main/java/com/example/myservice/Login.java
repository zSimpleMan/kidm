package com.example.myservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    Button btn_login;
    EditText edt_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = (Button) findViewById(R.id.btn_login);
        edt_code = (EditText) findViewById(R.id.edt_code);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckAndLogin();

            }
        });
    }

    private void CheckAndLogin(){
        String code = edt_code.getText().toString();
        if(code.equals("")){
            Toast.makeText(this, "Bạn chưa nhập code", Toast.LENGTH_SHORT).show();
        }
        else {
            Call<LoginStatus> call = RetrofitClient.getInstance().getApi()
                    .Login(code);
            call.enqueue(new Callback<LoginStatus>() {
                @Override
                public void onResponse(Call<LoginStatus> call, Response<LoginStatus> response) {
                    LoginStatus loginStatus = response.body();
                    Toast.makeText(Login.this, loginStatus.getMessage(), Toast.LENGTH_SHORT).show();
                    if(loginStatus.getSuccess()){
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        intent.putExtra("mycode", code);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<LoginStatus> call, Throwable t) {
                    Toast.makeText(Login.this, "Network error!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
