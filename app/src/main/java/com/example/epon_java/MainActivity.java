package com.example.epon_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginbtn = findViewById(R.id.loginbtn);
        Button registration = findViewById(R.id.registration);

        loginbtn.setOnClickListener(this);
        registration.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginbtn:
                Toast.makeText(this, "Login Page", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,MemberLogin.class);
                startActivity(intent);
        }
        switch (v.getId()){
            case R.id.registration:
                Toast.makeText(this, "Registration Page", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,Registration.class);
                startActivity(intent);
        }

    }
}