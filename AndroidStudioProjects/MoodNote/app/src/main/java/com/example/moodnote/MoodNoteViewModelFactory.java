package com.example.moodnote;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

// MoodNoteViewModelFactory: A custom factory class that provides instances of MoodNoteViewModel
// This is necessary because ViewModel needs to be created with the Application context.
public class MoodNoteViewModelFactory implements ViewModelProvider.Factory {
    private final Application mApplication;

    // Constructor to initialize the application context
    public MoodNoteViewModelFactory(Application application) {
        mApplication = application;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        // Check if the requested ViewModel is an instance of MoodNoteViewModel
        if (modelClass.isAssignableFrom(MoodNoteViewModel.class)) {
            // Return an instance of MoodNoteViewModel with the Application context
            return (T) new MoodNoteViewModel(mApplication);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
