package com.example.dacnpm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.text.TextUtils.isEmpty;

public class ProfileActivity extends AppCompatActivity {

    int userid=0;
    TextView txtemail;
    EditText edtname, edtaddress, edtbirthday, edtphone;
    Button btn_save, btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtemail = (TextView)findViewById(R.id.txt_email);

        edtname = (EditText)findViewById(R.id.edt_name);
        edtaddress = (EditText)findViewById(R.id.edt_address);
        edtbirthday = (EditText)findViewById(R.id.edt_birthday);
        edtphone = (EditText)findViewById(R.id.edt_phone);

        btn_save = (Button)findViewById(R.id.btn_save);
        btn_back = (Button)findViewById(R.id.btn_home);

        Intent intent = getIntent();
        loginStatus user = (loginStatus) intent.getSerializableExtra("user");
        userid = user.getId();

        txtemail.setText(user.getEmail());

        SetValues();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(ProfileActivity.this, HomeActivity.class);
                home.putExtra("user", user);
                startActivity(home);
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check_isEmpty())
                {
                    UpdateProfile();
                }
            }

            private void UpdateProfile() {
                Call<profileStatus> call = RetrofitClient
                        .getInstance().getApi()
                        .updateProfile(userid, edtname.getText().toString(),
                                edtaddress.getText().toString(), edtbirthday.getText().toString(), edtphone.getText().toString());
                call.enqueue(new Callback<profileStatus>() {
                    @Override
                    public void onResponse(Call<profileStatus> call, Response<profileStatus> response) {
                        profileStatus profileStatus = response.body();
                        Toast.makeText(ProfileActivity.this, profileStatus.getResult(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<profileStatus> call, Throwable t) {
                        Toast.makeText(ProfileActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    protected void SetValues()
    {
        Call<profileStatus> call = RetrofitClient
                .getInstance()
                .getApi()
                .getProfile(userid);
        call.enqueue(new Callback<profileStatus>() {
            @Override
            public void onResponse(Call<profileStatus> call, Response<profileStatus> response) {
                profileStatus profilestatus = response.body();
                if(profilestatus.getResult().equals("successful"))
                {
                    edtname.setText(profilestatus.getName());
                    edtaddress.setText(profilestatus.getAddress());
                    edtbirthday.setText(profilestatus.getBirthday());
                    edtphone.setText(profilestatus.getPhone());
                }
            }

            @Override
            public void onFailure(Call<profileStatus> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected boolean check_isEmpty()
    {
        if(isEmpty(edtname.getText())||isEmpty(edtaddress.getText())||isEmpty(edtbirthday.getText())||isEmpty(edtphone.getText()))
        {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
