package com.e.roomandmvvm.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.e.roomandmvvm.model.Note;
import com.e.roomandmvvm.R;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {
    List<Note> allNotes = new ArrayList<>();
    Context context;
    private onRecyclerViewItemClickListener onItemClickListener;

    public NoteAdapter(/*List<Note> allNotes, Context context*/) {
        /*this.allNotes = allNotes;
        this.context = context;*/
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote = allNotes.get(position);
        holder.noteTitle.setText(currentNote.getTitle());
        holder.noteDesc.setText(currentNote.getDescription());
        holder.notePriority.setText(String.valueOf(currentNote.getPriority()));
    }

    public Note getNoteAt(int position) {
        return allNotes.get(position);
    }

    @Override
    public int getItemCount() {
        return allNotes.size();
    }

    public void setAllNotes(List<Note> notes) {
        this.allNotes = notes;
        notifyDataSetChanged();
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView noteTitle;
        private TextView noteDesc;
        private TextView notePriority;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.tvNoteTitle);
            noteDesc = itemView.findViewById(R.id.tvNoteDesc);
            notePriority = itemView.findViewById(R.id.tvNotePriority);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (onItemClickListener != null && position != RecyclerView.NO_POSITION)
                    onItemClickListener.onItemClicked(allNotes.get(position));
            });
        }
    }

    public interface onRecyclerViewItemClickListener {
        void onItemClicked(Note note);
    }

    public void setOnItemClickListener(onRecyclerViewItemClickListener listener) {
        this.onItemClickListener =  listener;
    }
}
