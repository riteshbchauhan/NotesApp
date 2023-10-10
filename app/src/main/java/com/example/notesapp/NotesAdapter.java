package com.example.notesapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {
    Context mcontext;
    List<NoteModel> reviewsdata = new ArrayList<>();
    private FirebaseFirestore db;

    public NotesAdapter(List<NoteModel> reviews, Context context) {
        this.reviewsdata = reviews;
        this.mcontext = context;
        db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public NotesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_layoutfor_rv, parent, false);
        NotesAdapter.MyViewHolder mvh = new NotesAdapter.MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        NoteModel noteModel = reviewsdata.get(position);

        holder.contenttextview.setText(reviewsdata.get(position).getNotes());
        holder.titletextview.setText(reviewsdata.get(position).getTitle());
        holder.notecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mcontext, ReadNotesActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("title", reviewsdata.get(position).getTitle());
                intent.putExtra("notes", reviewsdata.get(position).getNotes());
                mcontext.startActivity(intent);
            }
        });


        holder.delete_imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteDialog(noteModel);
            }
        });

    }

    private void showDeleteDialog(NoteModel review) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reviewsCollection = db.collection("notes");

        Query query = reviewsCollection.whereEqualTo("notes", review.getNotes());
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (!querySnapshot.isEmpty()) {
                    DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                    String documentId = documentSnapshot.getId();
                    AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
                    builder.setTitle("Delete Note")
                            .setMessage("Are you sure you want to delete this note?")
                            .setPositiveButton("Delete", (dialog, which) -> {
                                removeReview(documentId);
                            })
                            .setNegativeButton("Cancel", null)
                            .show();
                } else {
                    Toast.makeText(mcontext, "No matching documents found", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mcontext, "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void removeReview(String documentId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reviewsCollection = db.collection("notes");

        reviewsCollection.document(documentId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(mcontext, "Note deleted successfully", Toast.LENGTH_SHORT).show();
                  Log.d("Debug", "Document Reference Path: " + reviewsCollection.getPath());
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(mcontext, "Failed to delete the note", Toast.LENGTH_SHORT).show();
                });
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return reviewsdata.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titletextview, contenttextview;
        CardView notecard;
        ImageButton delete_imgbtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titletextview = itemView.findViewById(R.id.titletextview);
            contenttextview = itemView.findViewById(R.id.contenttextview);
            notecard = itemView.findViewById(R.id.notecard);

            delete_imgbtn = itemView.findViewById(R.id.delete_imgbtn);
        }
    }
}
