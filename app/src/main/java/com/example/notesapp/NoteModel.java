package com.example.notesapp;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

// Note.java
public class NoteModel {
    private String title;
    private String notes;

    private String userId;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    public NoteModel() {
        // Default constructor required for Firestore
    }

    public NoteModel(String title, String notes, String userId) {
        this.title = title;
        this.notes = notes;
        this.userId = userId;
    }
    public NoteModel(String title, String notes){
        this.title = title;
        this.notes = notes;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
// Getter and setter methods

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
