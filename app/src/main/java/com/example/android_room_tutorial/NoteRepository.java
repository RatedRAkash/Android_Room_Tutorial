package com.example.android_room_tutorial;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import java.util.List;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application){

        NoteDatabase database = NoteDatabase.getInstance(application);
        Log.d("TAG", "NoteRepository: Constructor");

        noteDao = database.noteDao();//Room subclasses our Abstract Class... so "noteDao()" implementation Android Room auto generate kore dei

        allNotes = noteDao.getAllNotes();
    }

    //*************************************************
    //Only eishb METHOD gulai VIEWMODEL access korte parbe
    public void insert(Note note){
        new InsertNoteAsyncTask(noteDao).execute(note);
    }

    public void update(Note note){
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }

    public void delete(Note note){
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    public void deleteAllNotes(){
        new DeleteAllNotesAsyncTask(noteDao).execute();
    }

    //to Return All Notes
    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }
    //*****************************************************






    //BACKGROUND AsyncTask gula Start holo
    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void>{

        private NoteDao noteDao;

        private InsertNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }


    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void>{

        private NoteDao noteDao;

        private UpdateNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void>{

        private NoteDao noteDao;

        private DeleteNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void>{

        private NoteDao noteDao;

        private DeleteAllNotesAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }
    //BACKGROUND AsyncTask End


}