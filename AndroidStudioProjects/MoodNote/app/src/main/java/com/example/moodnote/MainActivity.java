package com.example.moodnote;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements MoodNoteListAdapter.OnDeleteClickListener {

    public static final int NEW_NOTE_ACTIVITY_REQUEST_CODE = 1;
    public static final int NEW_UPDATE_ACTIVITY_REQUEST_CODE = 2;
    private MoodNoteViewModel mNoteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.RVMode);

        // Create an instance of MoodNoteListAdapter and pass the NoteDiff callback to it.
        // The NoteDiff callback is used to compare items in the list and optimize updates.
        final MoodNoteListAdapter adapter = new MoodNoteListAdapter(new MoodNoteListAdapter.NoteDiff(),this);
        adapter.setOnItemClickListener(moodNote -> {
            Intent intent = new Intent(MainActivity.this, UpdateNoteActivity.class);
            intent.putExtra(UpdateNoteActivity.ExtraDate, moodNote.getmDate());
            intent.putExtra(UpdateNoteActivity.ExtraMood, moodNote.getmMood());
            intent.putExtra(UpdateNoteActivity.ExtraDayNight, moodNote.getmDayNight());
            intent.putExtra(UpdateNoteActivity.ExtraNote, moodNote.getmNote());
            startActivityForResult(intent, 2); // Launch the update activity
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MoodNoteViewModelFactory factory = new MoodNoteViewModelFactory(getApplication());

        mNoteViewModel = new ViewModelProvider(this,factory).get(MoodNoteViewModel.class);

        mNoteViewModel.getAllNotes().observe(this, notes -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(notes);
        });



        // Initiates the process
        // to open AddNoteActivity where they can add a new note.
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener( view -> {
            // Create an Intent to start AddNoteActivity
            Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);

            // Start AddNoteActivity and wait for the result using the specified request code
            startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE);
        });

        FloatingActionButton DeleteAllBtn = findViewById(R.id.DeleteAllBtn);
        DeleteAllBtn.setOnClickListener(view -> {
            mNoteViewModel.deleteAll();
        });





    }


    //Once AddNoteActivity finishes, this method is triggered to receive the data
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check if the requestCode matches the one used to start AddNoteActivity,
        // and check if the result is RESULT_OK (indicating data was entered correctly).
        if (requestCode == NEW_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            // Extract the data sent from AddNoteActivity using the provided keys
            int mood = Integer.parseInt(data.getStringExtra(AddNoteActivity.ExtraMood));
            boolean daynight = Boolean.parseBoolean(data.getStringExtra(AddNoteActivity.ExtraDayNight));

            // Create a new MoodNote object with the data
            MoodNote note = new MoodNote(data.getStringExtra(AddNoteActivity.ExtraDate), mood,
                    daynight, data.getStringExtra(AddNoteActivity.ExtraNote));

            // Insert the new note into the ViewModel (which will be saved to the database)
            mNoteViewModel.insert(note);
        }
        else if (requestCode == NEW_UPDATE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            // Extract the data sent from UpdateNoteActivity using the provided keys
            int mood = Integer.parseInt(data.getStringExtra(UpdateNoteActivity.ExtraMood));
            boolean daynight = Boolean.parseBoolean(data.getStringExtra(UpdateNoteActivity.ExtraDayNight));
            String date = data.getStringExtra(UpdateNoteActivity.ExtraDate);
            String noteText = data.getStringExtra(UpdateNoteActivity.ExtraNote);

            // Extract the note ID (passed from the UpdateNoteActivity)
            int noteId = data.getIntExtra(UpdateNoteActivity.ExtraID, -1);

            if (noteId == -1) {
                // Show an error message if the ID is invalid (this shouldn't happen if data is passed correctly)
                Toast.makeText(getApplicationContext(), "Error: Invalid note ID", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create a new MoodNote object with the updated data
            MoodNote updatedNote = new MoodNote(date, mood, daynight, noteText);
            updatedNote.setId(noteId); // Set the ID of the note to ensure the correct note is updated

            // Update the note in the ViewModel (which will update the database)
            mNoteViewModel.update(updatedNote);

        } else {
            // If no valid result or data is entered, show a toast message indicating no note was saved
            Toast.makeText(getApplicationContext(), R.string.empty_not_saved, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDeleteClick(int position) {
        MoodNote note = mNoteViewModel.getAllNotes().getValue().get(position); // Get the note at the specified position
        mNoteViewModel.delete(note); // Delete the note from the ViewModel (which will delete it from the database)
    }
}
