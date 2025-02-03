package com.example.moodnote;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;
import androidx.room.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "mood_note_table")
public class MoodNote {
    @PrimaryKey(autoGenerate = true)
    public int mNoteID;

    public String mNote;
    @NonNull
    public String mDate;
    @NonNull
    public int mMood;
    @NonNull
    public boolean mDayNight;

    // Constructor
    public MoodNote(@NonNull String date, @NonNull int mood, @NonNull boolean dayNight, String note) {
        this.mDate = date;
        this.mMood = mood;
        this.mDayNight = dayNight;
        this.mNote = note;
    }

    // Getters
    public String getmDate() { return mDate; }
    public int getmMood() { return mMood; }
    public boolean getmDayNight() { return mDayNight; }
    public String getmNote() { return mNote; }

    public void setId(int id) { this.mNoteID = id; }

    public int getId() { return mNoteID; }
}