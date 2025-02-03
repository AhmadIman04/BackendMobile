package com.example.moodnote;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.lifecycle.LiveData;
import androidx.room.Update;

@Dao
public interface MoodNoteDao {

    // Insert a MoodNote, ignoring conflicts
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(MoodNote note);

    // Delete all MoodNotes
    @Query("DELETE FROM mood_note_table")
    void deleteAll();

    @Delete
    void delete(MoodNote note);  // Delete a specific note

    // Get all notes ordered by date in ascending order
    @Query("SELECT * FROM mood_note_table ORDER BY mDate ASC")
    LiveData<List<MoodNote>> getAscendingNote();

    @Update
    void update(MoodNote note); // Method to update the note
}