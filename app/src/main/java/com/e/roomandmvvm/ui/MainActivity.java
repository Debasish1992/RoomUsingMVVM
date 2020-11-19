package com.e.roomandmvvm.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.e.roomandmvvm.model.Note;
import com.e.roomandmvvm.R;
import com.e.roomandmvvm.viewmodel.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements
        NoteAdapter.onRecyclerViewItemClickListener {
    private NoteViewModel noteViewModel;
    FloatingActionButton fabAddNotes;
    Toolbar toolbar;
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getting the toolbar
        toolbar = findViewById(R.id.toolbar);
        //setting the title
        toolbar.setTitle("Notes");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar);
        RecyclerView rv = findViewById(R.id.rvNotes);
        fabAddNotes = findViewById(R.id.fabAddNotes);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);
        NoteAdapter adapter = new NoteAdapter();
        rv.setAdapter(adapter);
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, notes -> adapter.setAllNotes(notes));
        fabAddNotes.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,
                    AddNote.class);
            startActivityForResult(intent, ADD_NOTE_REQUEST);
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(rv);

        adapter.setOnItemClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddNote.EXTRA_TITLE);
            String desc = data.getStringExtra(AddNote.EXTRA_DESC);
            int priority = data.getIntExtra(AddNote.EXTRA_PRIORITY, 1);
            Note note = new Note(title, desc, priority);
            noteViewModel.insert(note);
            Toast.makeText(this, "Saved Successfully.", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK){
            String title = data.getStringExtra(AddNote.EXTRA_TITLE);
            String desc = data.getStringExtra(AddNote.EXTRA_DESC);
            int priority = data.getIntExtra(AddNote.EXTRA_PRIORITY, 1);
            int id = data.getIntExtra(AddNote.EXTRA_ID, 1);
            if(id == -1){
                Toast.makeText(this, "Note can not be updated.", Toast.LENGTH_SHORT).show();
                return;
            }
            Note note = new Note(title, desc, priority);
            note.set_id(id);
            noteViewModel.update(note);
            Toast.makeText(this, "Note updated successfully.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClicked(Note note) {
        Intent intent = new Intent(MainActivity.this, AddNote.class);
        intent.putExtra(AddNote.EXTRA_TITLE, note.getTitle());
        intent.putExtra(AddNote.EXTRA_DESC, note.getDescription());
        intent.putExtra(AddNote.EXTRA_PRIORITY, note.getPriority());
        intent.putExtra(AddNote.EXTRA_ID, note.get_id());
        startActivityForResult(intent, EDIT_NOTE_REQUEST);
    }
}