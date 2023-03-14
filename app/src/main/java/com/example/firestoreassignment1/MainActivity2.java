package com.example.firestoreassignment1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity implements noteAdapter.ItemClickListener, noteAdapter.ItemClickListener2 {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Note> items;
    noteAdapter adapter;
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    RecyclerView rv;
    EditText updateName;
    ImageView delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        updateName = findViewById(R.id.update_note);
        rv = findViewById(R.id.rvRest);
        items = new ArrayList<Note>();
        adapter = new noteAdapter(this, items, this, this);
        delete = findViewById(R.id.delete);
        GetAllNotes();
    }

    private void GetAllNotes() {

        db.collection("Notes").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots.isEmpty()) {
                            Log.d("drn", "onSuccess: LIST EMPTY");
                            return;
                        } else {
                            for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                if (documentSnapshot.exists()) {
                                    String id = documentSnapshot.getId();
                                    String note1 = documentSnapshot.getString("note");
                                    Note note= new Note(id, note1);
                                    items.add(note);
                                    rv.setLayoutManager(layoutManager);
                                    rv.setHasFixedSize(true);
                                    rv.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                    Log.e("LogDATA", items.toString());
                                }
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("LogDATA", "get failed with ");


            }
        });
    }


    public void Delete(final Note note) {
        db.collection("Notes").document(note.getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.e("sara", "deleted");
                        items.remove(note);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("sara", "fail");
                    }
                });
    }


    public void updateUser(final Note note) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Name");
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog, null);
        builder.setView(customLayout);
        builder.setPositiveButton(
                "Update",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        updateName = customLayout.findViewById(R.id.update_note);

                        db.collection("Notes").document(note.getId()).update("note", updateName.getText().toString())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("sara", "DocumentSnapshot successfully updated!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("sara", "Error updating document", e);
                                    }
                                });
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onItemClick(int position, String id) {
        Delete(items.get(position));
    }

    @Override
    public void onItemClick2(int position, String id) {
        updateUser(items.get(position));
    }
    //    public void onItemClick(int position, String id) {
//        Delete(items.get(position));
//    }
//
//    public void onItemClick2(int position, String id) {
//
//        updateUser(items.get(position));
//
//    }


}