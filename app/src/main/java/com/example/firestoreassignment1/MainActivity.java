package com.example.firestoreassignment1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText editTextNote;
    Button buttonSave;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNote = findViewById(R.id.et_note);
        buttonSave = findViewById(R.id.btn_save);


    }


    public void saveToFirebase(View v) {

        String notelabel = editTextNote.getText().toString();

        Map<String, Object> note1 = new HashMap<>();
        if (!notelabel.isEmpty()) {
            note1.put("note", notelabel);
            db.collection("Notes")
                    .add(note1)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                              @Override
                                              public void onSuccess(DocumentReference documentReference) {
                                                  openActivity2();
                                                  Log.e("TAG", "Data added successfully to database");
                                              }
                                          }
                    )

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("TAG", "Failed to add database");

                        }
                    });

        } else {
            Toast.makeText(this, "Please Fill fields", Toast.LENGTH_SHORT).show();
        }

    }

    public void openActivity2() {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
}