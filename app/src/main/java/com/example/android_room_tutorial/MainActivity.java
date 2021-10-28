package com.example.android_room_tutorial;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    private NoteViewModel noteViewModel;


    //*****************onCreate BEGIN************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setHasFixedSize(true);

        final NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);

        //ADD Note korar Button
        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_note);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });



        //RECYCLER er VIEW Gula SWIPE kore DELETE korar Code
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                int position = viewHolder.getAdapterPosition();
                Note note = adapter.getNoteAtPos(position);
                noteViewModel.delete(note);

                Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
        //RECYCLER er VIEW Gula SWIPE kore DELETE korar Code END


        //onnek khon PRESS kore kono ROW dhore rakhle jei ClickListener call hobe taar code
        adapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);

                intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.getId());
                Log.d("ID_TAG",String.valueOf(note.getId()));

                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.getTitle());
                intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, note.getDescription());
                intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY, note.getPriority());

                startActivityForResult(intent,EDIT_NOTE_REQUEST);
            }
        });
        //ClickListener er code END




        //*******************"NoteViewModel" er Instance Create korlam******************************
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        //*******************"NoteViewModel"******************************

        noteViewModel.getAllNotes().observe(MainActivity.this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                //Update RecyclerView
                //Toast.makeText(MainActivity.this, "onChanged", Toast.LENGTH_SHORT).show();
                //adapter.setNotes(notes);

                //"submitList" is a Method of "ListAdapter"
                adapter.submitList(notes);
            }
        });


    }
    //*****************onCreate END************************







    //*****************Next Activity te giye Result anar jonno onActivityResult er Code****************************************
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==ADD_NOTE_REQUEST && resultCode==RESULT_OK){
            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE); // getStringExtra te "Key" ta pass korbo jeitar moddhe "Title" er data ta ache
            String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);

            Note note = new Note(title,description,priority);
            noteViewModel.insert(note);

            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();
        }

        else if(requestCode==EDIT_NOTE_REQUEST && resultCode==RESULT_OK){

            int id = data.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1);
            if(id==-1) {
                Toast.makeText(this, "Note can't be Updated", Toast.LENGTH_SHORT).show();
            }


            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE); // getStringExtra te "Key" ta pass korbo jeitar moddhe "Title" er data ta ache
            String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);

            Note note = new Note(title,description,priority);
            //jehetu NOTE already Created ase...tai NOTE er ID amra UPDATE korbo
            note.setId(id);
            noteViewModel.update(note);

            Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();
        }


        else{ //AddEditNoteActivity te ge BACK button click korle ei CASE ee ashbe
            Toast.makeText(this, "Note Not Saved", Toast.LENGTH_SHORT).show();
        }

    }
    //*****************onActivityResult er Code END****************************************




    //**********************MenuOptions er Code START****************************************

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_all_notes:
                noteViewModel.deleteAllNotes();
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //**********************MenuOptions er Code END****************************************

}