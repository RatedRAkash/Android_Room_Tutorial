package com.example.android_room_tutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddEditNoteActivity extends AppCompatActivity {

    //Best PRACTICE hocche package er name er por "DOT" diye name ta dewa
    public static final String EXTRA_ID =
            "com.example.android_room_tutorial.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.example.android_room_tutorial.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.example.android_room_tutorial.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY =
            "com.example.android_room_tutorial.EXTRA_PRIORITY";

    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        numberPickerPriority = findViewById(R.id.number_picker_priority);

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

        //Close Button er ICON add korlam
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);


        //Activity er TITLE set kora Begin
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");

            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
        } else {
            setTitle("Add Note");
        }
        //Activity er TITLE set kora End


    }


    //OptionsMenu BEGIN
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);

        return true; //True return kora mane amra Menu ta Display korte chai
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    //OptionsMenu END


    private void saveNote() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int priority = numberPickerPriority.getValue();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a Title and Description", Toast.LENGTH_SHORT).show();
            return;
        }

        else {
            Intent data = new Intent();

            data.putExtra(EXTRA_TITLE, title);
            data.putExtra(EXTRA_DESCRIPTION, description);
            data.putExtra(EXTRA_PRIORITY, priority);

            //ID=-1 howa mane holo ei name er kono DATA ekono database ee saave hui nai...Save nah howa mane amra ekon DATA CREATE kortasi...
            //ID!=-1 howa mane Data SAVE ase... arr amra UPDATE/EDIT row kortasi
            int id = getIntent().getIntExtra(EXTRA_ID, -1);
            if (id != -1) {
                data.putExtra(EXTRA_ID, id);
            }

            setResult(RESULT_OK, data); // Eita mane amader Result tik tak set huise nki taar jonno
            finish();
        }

    }


}