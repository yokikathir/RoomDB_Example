package com.kathir.roomdbexample.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kathir.roomdbexample.R;
import com.kathir.roomdbexample.adapter.NoteModelAdapter;
import com.kathir.roomdbexample.db.Note;
import com.kathir.roomdbexample.viewmodel.NoteViewModel;

import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements NoteModelAdapter.OnDeleteClickListener{
    private static final int NEW_NOTE_ACTIVITY_RESULTS_CODE = 1;
    public static final int UPDATE_NOTE_ACTIVITY_REQUEST_CODE = 2;
    public String TAG = this.getClass().getSimpleName();
    private NoteViewModel noteViewModel;
    private NoteModelAdapter noteModelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        noteModelAdapter=new NoteModelAdapter(this,this);
        recyclerView.setAdapter(noteModelAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewNoteActivity.class);
                startActivityForResult(intent, NEW_NOTE_ACTIVITY_RESULTS_CODE);

            }
        });
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                noteModelAdapter.setNotes(notes);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_NOTE_ACTIVITY_RESULTS_CODE && resultCode == RESULT_OK) {
            final String note_id = UUID.randomUUID().toString();
            Note note = new Note(note_id, data.getStringExtra(NewNoteActivity.NOTE_ADDED));
            noteViewModel.insert(note);
            Toast.makeText(
                    getApplicationContext(),
                    "Saved",
                    Toast.LENGTH_LONG).show();
        }else if (requestCode == UPDATE_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            // Code to update the note
            Note note = new Note(
                    data.getStringExtra(EditNoteActivity.NOTE_ID),
                    data.getStringExtra(EditNoteActivity.UPDATED_NOTE));
            noteViewModel.update(note);

            Toast.makeText(
                    getApplicationContext(),
                    "Updaded",
                    Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Not saved",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void OnDeleteClickListener(Note myNote) {
        noteViewModel.delete(myNote);
    }


}
