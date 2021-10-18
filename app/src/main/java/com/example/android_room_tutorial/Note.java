package com.example.android_room_tutorial;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;

    // "@Ignore" keyword use kore oi Column Table ee Add hobe nah
    // "@ColumnInfo(name="priority_column")" keyword use kore oi Column er NAME change korte parbo

    private String title;
    private String description;
    private int priority;

    public Note(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    //Setter Method
    public void setId(int id) {
        this.id = id;
    }

    //Getter Method BEGIN
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }
    //Getter Method END
}
