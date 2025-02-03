package com.example.moodnote;


import android.app.Application;
import androidx.lifecycle.LiveData;

import com.example.moodnote.MoodNote;
import com.example.moodnote.MoodNoteDao;
import com.example.moodnote.MoodNoteRoomDatabase;

import java.util.List;

// 4. Create Repository class to access multiple data sources (in this case, we have only one).
class MoodNoteRepository {

    private MoodNoteDao mNoteDao;
    private LiveData<List<MoodNote>> mAllNotes;

    // The DAO is passed into the repository constructor as opposed to the whole database. This is
    // because you only need access to the DAO, since it contains all the read/write methods for
    // the database. There's no need to expose the entire database to the repository.
    MoodNoteRepository(Application application) {
        MoodNoteRoomDatabase db = MoodNoteRoomDatabase.getDatabase(application);
        mNoteDao = db.noteDao();
        mAllNotes = mNoteDao.getAscendingNote();
    }

    // The getAllNotes method returns the LiveData list of notes from Room; we can do this because
    // of how we defined the getAscendingNote() method to return LiveData in the MoodNoteDao.
    // Room executes all queries on a separate thread. Then observed LiveData will notify the
    // observer on the main thread when the data has changed.
    LiveData<List<MoodNote>> getAllNotes() {
        return mAllNotes;
    }

    //We need to not run the insert on the main thread, so we use the ExecutorService we created
    // in the MoodNoteRoomDatabase to perform the insert on a background thread.
    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(MoodNote note) {
        MoodNoteRoomDatabase.databaseWriteExecutor.execute(() -> {
            mNoteDao.insert(note);
        });
    }

    void deleteAll(){
        MoodNoteRoomDatabase.databaseWriteExecutor.execute(() -> {
            mNoteDao.deleteAll();
        });
    }

    public void update(MoodNote note) {
        MoodNoteRoomDatabase.databaseWriteExecutor.execute(() -> {
            mNoteDao.update(note); // Update the note in the database
        });
    }

    public void delete(MoodNote note) {
        MoodNoteRoomDatabase.databaseWriteExecutor.execute(() -> mNoteDao.delete(note));  // Delete the specific note
    }


}
