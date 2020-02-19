package com.kathir.roomdbexample.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kathir.roomdbexample.R;

public class NewNoteActivity extends AppCompatActivity {
    private EditText etNewNote;
    public static final String NOTE_ADDED = "new_note";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        etNewNote = findViewById(R.id.etNewNote);

        Button button = findViewById(R.id.bAdd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                if (TextUtils.isEmpty(etNewNote.getText())){
                    setResult(RESULT_CANCELED);
                }else {
                    String note=etNewNote.getText().toString();
                    resultIntent.putExtra(NOTE_ADDED,note);
                    setResult(RESULT_OK,resultIntent);
                }
                finish();
            }
        });
    }
}
