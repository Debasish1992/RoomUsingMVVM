package com.e.roomandmvvm.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.e.roomandmvvm.model.Note;
import com.e.roomandmvvm.R;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteHolder> {
    //List<Note> allNotes = new ArrayList<>();
    Context context;
    private onRecyclerViewItemClickListener onItemClickListener;


    protected NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.get_id() == newItem.get_id();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getPriority() == newItem.getPriority();
        }
    };

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote = getItem(position);
        holder.noteTitle.setText(currentNote.getTitle());
        holder.noteDesc.setText(currentNote.getDescription());
        holder.notePriority.setText(String.valueOf(currentNote.getPriority()));
    }

    public Note getNoteAt(int position) {
        return getItem(position);
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
                    onItemClickListener.onItemClicked(getItem(position));
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
