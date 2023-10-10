package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ReadNotesActivity extends AppCompatActivity {

    TextView page_title_details, page_content_details;
//    ImageButton editimgbtn, deleteimgbtn;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    int notes,title;
    List<NoteModel> reviewsdata = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_notes);

        page_title_details = findViewById(R.id.page_title_details);
        page_content_details = findViewById(R.id.page_content_details);
        //editimgbtn = findViewById(R.id.editimgbtn);
//        deleteimgbtn = findViewById(R.id.deleteimgbtn);

        Intent data = getIntent();
        page_content_details.setText(data.getStringExtra("notes"));
        page_title_details.setText(data.getStringExtra("title"));

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

//        editimgbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(view.getContext(), EditNotesActivity.class);
//                intent.putExtra("title", data.getStringExtra("title"));
//                intent.putExtra("notes", data.getStringExtra("notes"));
//                view.getContext().startActivity(intent);
//            }
//        });

//        deleteimgbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DocumentReference documentReference = firebaseFirestore.collection("notes").document();
//                documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Toast.makeText(ReadNotesActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(ReadNotesActivity.this, HomeActivity.class));
//                            }
//                }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.e("FirestoreError", "Error deleting note: " + e.getMessage());
//                                Toast.makeText(ReadNotesActivity.this, "Please retry - Note not deleted", Toast.LENGTH_SHORT).show();
//                            }
//                });
//
//                NoteModel noteModel;
//                FirebaseFirestore db = FirebaseFirestore.getInstance();
//                CollectionReference reviewsCollection = db.collection("notes");
//
//                Query query = reviewsCollection.whereEqualTo("notes", reviewsdata.get(notes).getNotes());
//                query.get().addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        QuerySnapshot querySnapshot = task.getResult();
//                        if (!querySnapshot.isEmpty()) {
//                            DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
//                            String documentId = documentSnapshot.getId();
//                            AlertDialog.Builder builder = new AlertDialog.Builder(ReadNotesActivity.this);
//                            builder.setTitle("Delete Note")
//                                    .setMessage("Are you sure you want to delete this note?")
//                                    .setPositiveButton("Delete", (dialog, which) -> {
//                                        removeReview(documentId);
//                                    })
//                                    .setNegativeButton("Cancel", null)
//                                    .show();
//                        } else {
//                            Toast.makeText(ReadNotesActivity.this, "No matching documents found", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        Toast.makeText(ReadNotesActivity.this, "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//            }
//        });

    }
//    @SuppressLint("NotifyDataSetChanged")
//    private void removeReview(String documentId) {
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        CollectionReference reviewsCollection = db.collection("notes");
//        reviewsCollection.document(documentId)
//                .delete()
//                .addOnSuccessListener(aVoid -> {
//                    Toast.makeText(ReadNotesActivity.this, "Note deleted successfully", Toast.LENGTH_SHORT).show();
////                    Log.d("Debug", "Document Reference Path: " + reviewsCollection.getPath());
//                })
//                .addOnFailureListener(e -> {
//                    Toast.makeText(ReadNotesActivity.this, "Failed to delete the Note", Toast.LENGTH_SHORT).show();
//                });
//    }
}