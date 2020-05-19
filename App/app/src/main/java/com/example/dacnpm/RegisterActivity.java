package com.example.dacnpm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {

    EditText password, repassword, email;
    Button register;
    CheckBox parent;
    int is_parent = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        password =(EditText)findViewById(R.id.edt_password);
        repassword =(EditText)findViewById(R.id.edt_password2);
        email =(EditText)findViewById(R.id.edt_email);
        register =(Button) findViewById(R.id.btn_register);
        parent =(CheckBox)findViewById(R.id.cb_parent);

        parent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    is_parent=1;
                }
                else {
                    is_parent=0;
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty(password)||isEmpty(repassword)||isEmpty(email))
                {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(password.getText().toString().equals(repassword.getText().toString())) {
                        RegisterUser();
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, repassword.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private void RegisterUser() {
        Call<registerStatus> call = RetrofitClient
                .getInstance()
                .getApi()
                .regisUser(email.getText().toString(), password.getText().toString(),is_parent);
        call.enqueue(new Callback<registerStatus>() {
            @Override
            public void onResponse(Call<registerStatus> call, Response<registerStatus> response) {
                registerStatus registerStatus = response.body();
                if(registerStatus.getMessage().equals("successful")){
                    Toast.makeText(RegisterActivity.this, registerStatus.getId()+" "+registerStatus.getEmail()+" "+
                            registerStatus.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent home = new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(home);
                }
                else {
                    Toast.makeText(RegisterActivity.this, registerStatus.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<registerStatus> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }
}
