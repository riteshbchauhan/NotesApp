package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class AboutUsActivity extends AppCompatActivity {
    ImageButton link_imgbtn;
    Toolbar toolbar;
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        toolbar = findViewById(R.id.toolbar);

        link_imgbtn =findViewById(R.id.link_imgbtn);
        link_imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //I created a website and this Intent redirect to the website
                Intent aboutUrl = new Intent(Intent.ACTION_VIEW, Uri.parse("http://securenotes.great-site.net/introduction/"));
                startActivity(aboutUrl);
            }
        });
    }
}