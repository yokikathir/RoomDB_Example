package com.kathir.roomdbexample.viewmodel;

import android.app.Application;

import android.util.Log;


import com.kathir.roomdbexample.database.NoteRoomDatabase;
import com.kathir.roomdbexample.db.Note;
import com.kathir.roomdbexample.interfaceDao.NoteDao;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class EditNoteViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private NoteDao noteDao;
    private NoteRoomDatabase db;

    public EditNoteViewModel(@NonNull Application application) {
        super(application);
        Log.i(TAG, "Edit ViewModel");
        db = NoteRoomDatabase.getNoteRoomDatabase(application);
        noteDao = db.noteDao();
    }

    public LiveData<Note> getNote(String noteId) {
        return noteDao.getNote(noteId);
    }
}
