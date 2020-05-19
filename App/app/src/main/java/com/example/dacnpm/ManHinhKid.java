package com.example.dacnpm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ManHinhKid extends AppCompatActivity {


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
                if (edt_themtn.getText().toString() !="")
                {
                    String tnn = edt_themtn.getText().toString();
                    arrayTnn.add(tnn);
                    edt_themtn.setText("");
                    adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(ManHinhKid.this,"bạn chưa thêm tin nhắn",Toast.LENGTH_LONG).show();
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
    }
}
