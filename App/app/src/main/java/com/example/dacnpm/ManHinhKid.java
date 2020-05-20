package com.example.dacnpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ManHinhKid extends AppCompatActivity {

    final int CALL_PHONE_PERMISSION_REQUEST_CODE = 1;

    Button btnSOS, btnchat, btn_themtn;
    EditText edt_themtn;
    ListView listview_tnnhanh;
    ArrayList<String> arrayTnn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_kid);

        btnSOS = (Button)findViewById(R.id.btnsos);
        btnchat = (Button)findViewById(R.id.btnchat);
        btn_themtn =(Button)findViewById(R.id.btn_themtn);
        edt_themtn =(EditText)findViewById(R.id.edt_themtn);
        listview_tnnhanh = (ListView)findViewById(R.id.listview_tnnhanh);

        arrayTnn = new ArrayList<>();

        arrayTnn.add("Con ở nhà rồi");
        arrayTnn.add("Con đang ở trường");
        arrayTnn.add("Con đang đi");
        arrayTnn.add("Mọi thứ ổn ạ");

        ArrayAdapter adapter = new ArrayAdapter(
                ManHinhKid.this,
                android.R.layout.simple_list_item_1,
                arrayTnn
        );

        listview_tnnhanh.setAdapter(adapter);

        btn_themtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_themtn.getText().toString().matches(""))
                {
                    Toast.makeText(ManHinhKid.this,"bạn chưa thêm tin nhắn",Toast.LENGTH_LONG).show();
                }
                else {
                    String tnn = edt_themtn.getText().toString();
                    arrayTnn.add(tnn);
                    edt_themtn.setText("");
                    adapter.notifyDataSetChanged();
                }
            }
        });

        listview_tnnhanh.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                arrayTnn.remove(position);
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        if(checkPermission(Manifest.permission.CALL_PHONE)) {
            return;
        }else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    CALL_PHONE_PERMISSION_REQUEST_CODE);
        }

        btnSOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCall();
            }
        });
    }

    public boolean checkPermission(String permission)
    {
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }


    public void onCall() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    CALL_PHONE_PERMISSION_REQUEST_CODE);
        } else {
            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:0394174233")));
        }
    }
}
