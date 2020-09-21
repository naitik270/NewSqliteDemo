package com.example.myapplication.Classes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Database.DataBaseOperations;
import com.example.myapplication.Database.RegistrationColumn;
import com.example.myapplication.R;

public class LoginActivity extends AppCompatActivity {


    TextView txt_register_here;
    Button btn_login;
    EditText edt_mobile;
    DataBaseOperations mDataBaseOperations;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDataBaseOperations = new DataBaseOperations(getApplicationContext());

        edt_mobile = findViewById(R.id.edt_mobile);
        txt_register_here = findViewById(R.id.txt_register_here);
        txt_register_here.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
            startActivity(intent);
        });

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(v -> {

            boolean recordExists = DataBaseOperations.checkAlreadyExist(RegistrationColumn.TABLE_NAME,
                    RegistrationColumn.reg_phone, edt_mobile.getText().toString());

            if (recordExists) {
                Toast.makeText(getApplicationContext(), "Record is already exist!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Please enter valid mobile no!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
