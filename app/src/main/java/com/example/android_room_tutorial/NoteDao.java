package com.example.android_room_tutorial;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    // "@Insert", "@Update", "@Delete" er sob IMPLEMENTATION shb kisur IMPLEMENTATION gula Android ROOM kore dibe
    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    // "@Query" keyword use kore jate kore amra "Custom" Query korte pari
    @Query("DELETE FROM note_table")
    void deleteAllNotes();

    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    LiveData<List<Note>> getAllNotes();
}
