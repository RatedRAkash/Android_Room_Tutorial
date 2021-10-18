package com.example.android_room_tutorial;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class}, version=1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase singleton_instance; //Eita ke SINGLETON korbo

    //Room subclasses our Abstract Class... so "noteDao()" implementation Android Room auto generate kore dei
    public abstract NoteDao noteDao(); // Ekta TABLE or ENTITY return korbe eirokom ekta Method amra Create korbo

    public static synchronized NoteDatabase getInstance(Context context) {
        if (singleton_instance == null){
            singleton_instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration() //Version Number increase korte chai taile full Database Drop kore Migrate korar jonno ei Method
                    .addCallback(roomCallback) //Database Popluate korar jonno
                    .build();
        }

        return singleton_instance;
    }






    //*********Initial DATABASE Population CODE***************

    //Database 1st Time OnCreate er somoy jeno amra DATABASE FILL-UP ovostha ee takhe...
    // "Data Popluate" korar jonno ei CALLBACK method er kaaj
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(singleton_instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{

        private NoteDao noteDao;

        private PopulateDbAsyncTask(NoteDatabase db){
            noteDao=db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            noteDao.insert(new Note("Title 1","Description 1",1));
            noteDao.insert(new Note("Title 2","Description 2",2));
            noteDao.insert(new Note("Title 3","Description 3",3));
            Log.d("TAG", "doInBackground: run");

            return null;
        }
    }

    //*********DATABASE Population CODE End***************


}