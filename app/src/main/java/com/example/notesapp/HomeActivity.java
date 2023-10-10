package com.example.notesapp;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HomeActivity extends AppCompatActivity {

    DrawerLayout drawer_layout;
    NavigationView navigationView;
    Toolbar appbar;
    ActionBarDrawerToggle drawerToggle;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    RecyclerView recycler_view;
    FloatingActionButton addfloatbtn;
    FirebaseFirestore firebaseFirestore;
    NotesAdapter notesAdapter;
    private CollectionReference reviewsCollection;


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        super.onBackPressed();
    }
/*
    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView notetitle;
        private TextView notecontent;
        LinearLayout note;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            notetitle = itemView.findViewById(R.id.titletextview);
            notecontent = itemView.findViewById(R.id.contenttextview);
            note = itemView.findViewById(R.id.notecard);
        }
    }*/

    protected void onStart() {
        super.onStart();
//        noteAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
//
//        if (noteAdapter != null) {
//            noteAdapter.startListening();
//        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        drawer_layout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        appbar = findViewById(R.id.appbar);
        setSupportActionBar(appbar);

        drawerToggle = new ActionBarDrawerToggle(this, drawer_layout, appbar, R.string.open, R.string.close);
        drawer_layout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        FirebaseApp.initializeApp(this);

        addfloatbtn = findViewById(R.id.addfloatbtn);
        addfloatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, AddNotesActivity.class));
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_home) {

                } else if (id == R.id.nav_profile) {
                    startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                }
                if (id == R.id.nav_about) {
                    startActivity(new Intent(HomeActivity.this, AboutUsActivity.class));
                } else if (id == R.id.nav_contact) {
                    //I created a website and this Intent redirect to the website
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://securenotes.great-site.net/contact-us/")));
                } else if (id == R.id.nav_privacy_policy) {
                    //I created a website and this Intent redirect to the website
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://securenotes.great-site.net/privacy-policy/")));
                } else if (id == R.id.nav_logout) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                    Toast.makeText(HomeActivity.this, "Logout Sucessfully", Toast.LENGTH_SHORT).show();
                }
                drawer_layout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        reviewsCollection = firebaseFirestore.collection("notes");

        fetchnotes();

//
//        Query query = firebaseFirestore.collection("notes").document(firebaseUser.getEmail()).collection("myNotes").orderBy("title", Query.Direction.ASCENDING);
//        FirestoreRecyclerOptions<NoteModel> allusernotes = new FirestoreRecyclerOptions.Builder<NoteModel>().setQuery(query, NoteModel.class).build();
//        noteAdapter = new FirestoreRecyclerAdapter<NoteModel, NoteViewHolder>(allusernotes) {
//
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            protected void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, int i, @NonNull NoteModel note) {
//
//                ImageButton moreimgbtn = noteViewHolder.itemView.findViewById(R.id.more_imgbtn);
//
//                int colourcode = getRandomcolor();
//                noteViewHolder.note.setBackgroundColor(noteViewHolder.itemView.getResources().getColor(colourcode, null));
//
//                noteViewHolder.notetitle.setText(note.getTitle());
//                noteViewHolder.notecontent.setText(note.getNotes());
//
//                String docId = noteAdapter.getSnapshots().getSnapshot(i).getId();
//
//                noteViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        //note detail activity...
//
//                        Intent intent = new Intent(view.getContext(), ReadNotesActivity.class);
//                        intent.putExtra("title", note.getTitle());
//                        intent.putExtra("content", note.getNotes());
//                        intent.putExtra("noteId", docId);
//
//                        //startActivity(intent);
//                        view.getContext().startActivity(intent);
//
//                        // Toast.makeText(getApplicationContext(),"This is Clicked",Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//                moreimgbtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
//                        popupMenu.setGravity(Gravity.END);
//                        popupMenu.getMenu().add("Edit").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//                            @Override
//                            public boolean onMenuItemClick(MenuItem menuItem) {
//
//                                Intent intent = new Intent(view.getContext(), EditNotesActivity.class);
//                                intent.putExtra("title", note.getTitle());
//                                intent.putExtra("content", note.getNotes());
//                                intent.putExtra("noteId", docId);
//                                view.getContext().startActivity(intent);
//                                return false;
//                            }
//                        });
//
//                        popupMenu.getMenu().add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//                            @Override
//                            public boolean onMenuItemClick(MenuItem menuItem) {
//                                // Toast.makeText(view.getContext(),"This note is deleted",Toast.LENGTH_SHORT).show();
//                                DocumentReference documentReference = firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("myNotes").document(docId);
//                                documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void aVoid) {
//                                        Toast.makeText(view.getContext(), "This note is deleted", Toast.LENGTH_SHORT).show();
//                                    }
//                                }).addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Toast.makeText(view.getContext(), "Failed To Delete", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//
//                                return false;
//                            }
//                        });
//                        popupMenu.show();
//                    }
//                });
//
//            }
//
//            @NonNull
//            @Override
//            public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_layoutfor_rv, parent, false);
//                return new NoteViewHolder(view);
//            }
//        };
    }
    private void fetchnotes() {
        reviewsCollection.orderBy("title", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<NoteModel> reviews = queryDocumentSnapshots.toObjects(NoteModel.class);
                    notesAdapter = new NotesAdapter(reviews, this);
                    recycler_view.setAdapter(notesAdapter);
                })
                .addOnFailureListener(e -> {
                    // Handle the error
                });
    }
}