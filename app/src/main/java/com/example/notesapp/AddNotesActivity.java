package com.example.notesapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddNotesActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    EditText page_title, page_content;
    ImageButton saveimgbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        page_content = findViewById(R.id.page_content);
        page_title = findViewById(R.id.page_title);
        saveimgbtn = findViewById(R.id.saveimgbtn);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        saveimgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = page_title.getText().toString();
                String notes = page_content.getText().toString();
                /*
                if (title.isEmpty()){
                    Toast.makeText(AddNotesActivity.this, "Title cannot be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    DocumentReference documentReference = firebaseFirestore.collection("Notes")
                            .document(firebaseUser.getEmail())
                            .collection("My Notes")
                            .document();

                    Map<String, Object> note = new HashMap<>();
                    note.put("title", title);
                    note.put("content", content);

                    documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(AddNotesActivity.this, "Note added", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(AddNotesActivity.this, HomeActivity.class));
                                    finish();
                                }
                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AddNotesActivity.this, "Please retry - Note not added", Toast.LENGTH_SHORT).show();
                                }
                    });
                }*/
                String userId = firebaseUser.getEmail();
                if (title.isEmpty()||notes.isEmpty()){
                    Toast.makeText(AddNotesActivity.this, "Field are required", Toast.LENGTH_SHORT).show();
                }else {
                    NoteModel note = new NoteModel(title, notes, userId);
                    firebaseFirestore.collection("notes")
                            .add(note)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(AddNotesActivity.this, "Note Added", Toast.LENGTH_SHORT).show();
                                    page_title.setText("");
                                    page_content.setText("");
                                    startActivity(new Intent(AddNotesActivity.this, HomeActivity.class));
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("FirestoreError", "Error adding note: " + e.getMessage());
                                    Toast.makeText(AddNotesActivity.this, "Please retry - Note not added", Toast.LENGTH_SHORT).show();
                                }
                            });


                }
            }
        });
    }
}