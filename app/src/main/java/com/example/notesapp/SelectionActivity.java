package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SelectionActivity extends AppCompatActivity {

    Button btnSelectRegister, btnSelectLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        btnSelectRegister = findViewById(R.id.btnSelectRegister);
        btnSelectLogin = findViewById(R.id.btnSelectLogin);

        btnSelectRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectionActivity.this, RegisterActivity.class));
                Toast.makeText(SelectionActivity.this, "Your redirect to Register Form", Toast.LENGTH_SHORT).show();
            }
        });
        btnSelectLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectionActivity.this, LoginActivity.class));
                Toast.makeText(SelectionActivity.this, "Your redirect to Login Form", Toast.LENGTH_SHORT).show();
            }
        });
    }
}