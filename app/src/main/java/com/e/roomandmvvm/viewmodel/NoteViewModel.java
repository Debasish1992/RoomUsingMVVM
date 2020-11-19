package com.e.roomandmvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.e.roomandmvvm.model.Note;
import com.e.roomandmvvm.repository.NoteRepository;

import java.util.List;


// ViewModel class
public class NoteViewModel extends AndroidViewModel {
    private NoteRepository noteRespository;
    private LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRespository = new NoteRepository(application);
        allNotes = noteRespository.getAllNotes();
    }

    public void insert(Note note){
        noteRespository.insert(note);
    }

    public void update(Note note){
        noteRespository.update(note);
    }

    public void delete(Note note){
        noteRespository.delete(note);
    }

    public void deleteAllNotes(){
        noteRespository.deleteAllNotes();
    }

    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }
}
