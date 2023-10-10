package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;
import com.example.notesapp.RegisterActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {
   TextView showName, showEmail, showPassword,rFullname;
    ImageButton editprofileimgbtn, deleteprofileimgbtn;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
//        showName = findViewById(R.id.showName);
        showEmail = findViewById(R.id.showEmail);
        showPassword = findViewById(R.id.showPassword);
//        editprofileimgbtn = findViewById(R.id.editprofileimgbtn);
        deleteprofileimgbtn = findViewById(R.id.deleteprofileimgbtn);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {
            // User is logged in
//            String name = firebaseUser.getUid();
            String email = firebaseUser.getEmail();
            String password = "******";
            // Set the retrieved data to the EditText or TextView elements
//            showName.setText(name);
            showEmail.setText(email);
            showPassword.setText(password);
        }

        deleteprofileimgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (firebaseUser != null) {
                    firebaseUser.delete()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ProfileActivity.this, "User Deleted", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ProfileActivity.this,RegisterActivity.class));
                                } else {
                                    Toast.makeText(ProfileActivity.this, "User not Deleted", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

    }
}