package com.rahulcompany.bombaymathouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText username,password;
    private String TAG = "LoginActivity.class";
    private String us,pa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        username = findViewById(R.id.username);
        password = findViewById(R.id.pasword);
    }

    public void submit(View view) {

        String u="",p="";
        try {
            u = username.getText().toString();
            p = password.getText().toString();
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
        if (sp.contains("u") && sp.contains("p")) {
            us = sp.getString("u", null);
            pa = sp.getString("p", null);
        } else {
            us=u;
            pa = p;
            sp.edit().putString("u", us).commit();
            sp.edit().putString("p", pa).commit();
        }


        if (us.equalsIgnoreCase(us) && p.equalsIgnoreCase(pa)) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }
        else {
            username.setError("Wrong Detail");
            password.setError("Wrong Detail");
        }
    }
}