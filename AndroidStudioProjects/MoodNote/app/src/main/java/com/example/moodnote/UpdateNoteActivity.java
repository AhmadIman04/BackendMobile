package com.example.moodnote;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class UpdateNoteActivity extends AppCompatActivity {

    public static final String ExtraID = "id";
    public static final String ExtraDate = "date";
    public static final String ExtraMood = "mood";
    public static final String ExtraDayNight = "daynight";
    public static final String ExtraNote = "note";

    private EditText ETDate, ETNote;
    private RadioButton RBHappy, RBNeutral, RBUnhappy, RBDay, RBNight;
    private MoodNoteViewModel mNoteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        // Initialize views
        ETDate = findViewById(R.id.ETDate);
        ETNote = findViewById(R.id.ETNote);
        RBHappy = findViewById(R.id.RBHappy);
        RBNeutral = findViewById(R.id.RBNeutral);
        RBUnhappy = findViewById(R.id.RBUnhappy);
        RBDay = findViewById(R.id.RBDay);
        RBNight = findViewById(R.id.RBNight);

        // Initialize ViewModel
        mNoteViewModel = new ViewModelProvider(this).get(MoodNoteViewModel.class);

        // Get the data passed from the MainActivity
        Intent intent = getIntent();
        String date = intent.getStringExtra(ExtraDate);
        String note = intent.getStringExtra(ExtraNote);
        int mood = intent.getIntExtra(ExtraMood, 0);
        boolean dayNight = intent.getBooleanExtra(ExtraDayNight, true);
        int noteId = intent.getIntExtra(ExtraID, -1);

        // Populate the fields with the existing note data
        ETDate.setText(date);
        ETNote.setText(note);
        if (mood == 1) RBHappy.setChecked(true);
        else if (mood == 2) RBNeutral.setChecked(true);
        else RBUnhappy.setChecked(true);

        if (dayNight) RBDay.setChecked(true);
        else RBNight.setChecked(true);

        final Button button = findViewById(R.id.BtnSave);
        button.setOnClickListener(view -> {

            //Once the user adds a note and clicks 'Save',
            // this part sends the data back to MainActivity.

            Intent replyIntent = new Intent();
            String updateddate = ETDate.getText().toString();
            String updatednode = ETNote.getText().toString();
            int updatedmood;
            if (RBHappy.isChecked())
                updatedmood = 1;
            else if (RBUnhappy.isChecked())
                updatedmood = 3;
            else
                updatedmood = 2;

            boolean updateddayNight = true;
            if (RBNight.isChecked())
                updateddayNight = false;
            if (TextUtils.isEmpty(ETDate.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            }
            else if (TextUtils.isEmpty(ETNote.getText())){
                setResult(RESULT_CANCELED, replyIntent);
            }
            else {
                // Add the entered data as extras to the replyIntent
                replyIntent.putExtra(ExtraDate, updateddate);
                replyIntent.putExtra(ExtraNote, updatednode);
                replyIntent.putExtra(ExtraMood, Integer.toString(updatedmood));
                replyIntent.putExtra(ExtraDayNight, Boolean.toString(updateddayNight));
                replyIntent.putExtra(ExtraID, noteId);

                // Set the result as RESULT_OK and attach the replyIntent which contains the data
                setResult(RESULT_OK, replyIntent);
            }
            // Finish AddNoteActivity and return to MainActivity
            finish();
        });
    }
}