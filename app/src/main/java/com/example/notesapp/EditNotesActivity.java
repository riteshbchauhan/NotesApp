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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import java.util.HashMap;
import java.util.Map;

public class EditNotesActivity extends AppCompatActivity {

    Intent data;
    EditText edit_page_title, edit_page_content;
    ImageButton saveimgbtn2;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    DocumentReference docRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notes);

        edit_page_title = findViewById(R.id.edit_page_title);
        edit_page_content = findViewById(R.id.edit_page_content);
        saveimgbtn2 = findViewById(R.id.saveimgbtn2);

        data = getIntent();

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        firebaseFirestore.collection("notes").document(firebaseUser.getUid());

        String notetitle = data.getStringExtra("title");
        String notecontent = data.getStringExtra("notes");
        edit_page_content.setText(notecontent);
        edit_page_title.setText(notetitle);

        saveimgbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/* String newtitle = edit_page_title.getText().toString();
                String newcontent = edit_page_content.getText().toString();

                if (newtitle.isEmpty() || newcontent.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fields are required", Toast.LENGTH_SHORT).show();

                } else {
                    docRef = firebaseFirestore.collection("notes").document(firebaseUser.getEmail());

                    Map<String, Object> updates = new HashMap<>();
                    updates.put("title", newtitle);
                    updates.put("notes", newcontent);

                    docRef.update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(EditNotesActivity.this, "Update Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(EditNotesActivity.this, "Update Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                        Log.d("Debug", "Document Reference Path: " + docRef.getPath());
                            }
                        }
                    });
                }
            }
        });*/

                String newTitle = edit_page_title.getText().toString();
                String newContent = edit_page_content.getText().toString();

                updateDocument(newTitle, newContent);

/*
        saveimgbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    docRef.update(newtitle, newcontent)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(EditNotesActivity.this, "Update Sucessfully", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                    docRef = firebaseFirestore.collection("notes").document(firebaseUser.getUid());
                    Map<String, Object> updates = new HashMap<>();
                    updates.put("title", newtitle);
                    updates.put("notes", newcontent);

                    docRef.update(newtitle, newcontent)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(EditNotesActivity.this, "Update Successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(EditNotesActivity.this, "Update Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        Log.d("Debug", "Document Reference Path: " + docRef.getPath());
                                    }
                                }
                            });
    }

    /*private void updateData() {
        String newTitle = edit_page_title.getText().toString();
        String newContent = edit_page_content.getText().toString();

        String documentId = docRef.getId();
        // Assuming you have a document reference, replace 'your_collection' and 'your_document_id' accordingly
        docRef = firebaseFirestore.collection("notes").document(docRef.getId());

        firebaseFirestore.runTransaction(new Transaction.Function<Void>() {
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                transaction.update(docRef, "title", newTitle);
                transaction.update(docRef, "notes", newContent);
                return null;
            }
        }).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(EditNotesActivity.this, "Data updated!", Toast.LENGTH_SHORT).show();
                } else {
                    Exception e = task.getException();
                    Log.d("Debug", "Document Reference Path: " + docRef.getPath());
                    Toast.makeText(EditNotesActivity.this, "Error:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    // Display an error message or handle the error as needed
                }
            }
        });
    }*/
            }
        });
    }

    private void updateDocument(String newTitle, String newContent) {
        String uid = firebaseUser.getEmail();

        String documentPath = "notes/" + uid;

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.document(documentPath);

        Map<String, Object> updates = new HashMap<>();
        updates.put("title", newTitle);
        updates.put("notes", newContent);

        docRef.update(updates)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Update Successful", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e.getMessage().contains("NOT_FOUND")) {
                            Toast.makeText(getApplicationContext(), "No Document to Update", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Update Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}