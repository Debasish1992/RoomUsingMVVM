package com.e.roomandmvvm.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.e.roomandmvvm.model.Note;


@Database(entities = Note.class, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    private static NoteDatabase instance;
    public abstract NoteDAO noteDao();

    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }


    // CallBack to be trigger while the Room Database is created for very first time
    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    // Async task is responsible for inserting some dummy notes while creating the Database
    private static class PopulateDbAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDAO noteDao;
        public PopulateDbAsyncTask(NoteDatabase db) {
           noteDao = db.noteDao();
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insertNote(new Note("Title_1",
                    "Description_1", 1));
            noteDao.insertNote(new Note("Title_2",
                    "Description_2", 2));
            noteDao.insertNote(new Note("Title_3",
                    "Description_3", 3));
            return null;
        }
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}
