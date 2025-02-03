package com.example.moodnote;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

// MoodNoteViewModel: A ViewModel that holds the UI-related data (MoodNote) in a lifecycle-conscious way
// It acts as a bridge between the UI (Activity/Fragment) and the repository to provide data to the UI.
public class MoodNoteViewModel extends AndroidViewModel {

    //Added a private member variable to hold a reference to the repository.
    private MoodNoteRepository mRepository;
    private final LiveData<List<MoodNote>> mAllNotes;

    //Implemented a constructor that creates the MoodNoteRepository.
    //In the constructor, initialized the allNotes LiveData using the repository.
    public MoodNoteViewModel (Application application) {
        super(application);
        mRepository = new MoodNoteRepository(application);
        mAllNotes = mRepository.getAllNotes();
    }
    //Added a getAllNotes() method to return a cached list of words.
    LiveData<List<MoodNote>> getAllNotes() { return mAllNotes; }

    // Created a wrapper insert() method that calls the Repository's insert() method.
    // In this way, the implementation of insert() is encapsulated from the UI.
    public void insert(MoodNote note) { mRepository.insert(note); }

    public void deleteAll(){
        mRepository.deleteAll();
    }

    public void update(MoodNote note) {
        mRepository.update(note); // Update the note in the repository
    }

    public void delete(MoodNote note) {
        mRepository.delete(note);
    }
}
