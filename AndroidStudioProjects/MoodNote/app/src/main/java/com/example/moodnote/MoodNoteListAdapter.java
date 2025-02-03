package com.example.moodnote;

import static com.example.moodnote.MainActivity.NEW_UPDATE_ACTIVITY_REQUEST_CODE;

import android.app.Activity;
import android.content.Intent;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

// A RecyclerView Adapter for binding a list of MoodNote items to the RecyclerView
// It extends ListAdapter, which is a more efficient version of a RecyclerView.Adapter and automatically
public class MoodNoteListAdapter extends ListAdapter<MoodNote, MoodNoteViewHolder> {

    private OnItemClickListener mListener;
    private OnDeleteClickListener mDeleteClickListener;


    //Accepts a DiffUtil.ItemCallback which is used to efficiently compare the list items
    // and determine which items are the same or have different contents.
    public MoodNoteListAdapter(@NonNull DiffUtil.ItemCallback<MoodNote> diffCallback,OnDeleteClickListener deleteClickListener) {
        super(diffCallback);// Pass the diffCallback to the superclass (ListAdapter)
        mDeleteClickListener = deleteClickListener;
    }




    // onCreateViewHolder: Called when a new ViewHolder is needed.
    // It inflates the individual item layout and creates a new instance of MoodNoteViewHolder.
    // Relation: This method is where the `MoodNoteViewHolder` is instantiated and connected to the RecyclerView.
    @Override
    public MoodNoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MoodNoteViewHolder viewHolder = MoodNoteViewHolder.create(parent,mDeleteClickListener);
        viewHolder.itemView.setOnClickListener(v -> {
            int position = viewHolder.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && mListener != null) {
                mListener.onItemClick(getItem(position)); // Trigger item click
            }
        });
        return viewHolder;
    }



    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public interface OnItemClickListener {
        void onItemClick(MoodNote moodNote);
    }
    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }


    // onBindViewHolder: Called to bind data to a ViewHolder.
    // The position argument specifies which item in the list to bind.
    // Relation: The `MoodNoteViewHolder` is populated with data here by calling its `bind()` method.
    @Override
    public void onBindViewHolder(MoodNoteViewHolder holder, int position) {
        // Get the current MoodNote object at the specified position
        MoodNote current = getItem(position);

        // Use the bind method of MoodNoteViewHolder to set the data for the item (date, mood, day/night, and note content)
        holder.bind(current.getmDate(), current.getmMood(), current.getmDayNight(), current.getmNote());

        holder.itemView.setOnClickListener(v -> {
            // Create an Intent to start UpdateNoteActivity
            Intent intent = new Intent(holder.itemView.getContext(), UpdateNoteActivity.class);

            // Pass the current note's data as extras in the Intent
            intent.putExtra(UpdateNoteActivity.ExtraID, current.getId());  // Pass the note's ID
            intent.putExtra(UpdateNoteActivity.ExtraDate, current.getmDate());
            intent.putExtra(UpdateNoteActivity.ExtraMood, current.getmMood());
            intent.putExtra(UpdateNoteActivity.ExtraDayNight, current.getmDayNight());
            intent.putExtra(UpdateNoteActivity.ExtraNote, current.getmNote());

            // Start UpdateNoteActivity for a result
            ((Activity) holder.itemView.getContext()).startActivityForResult(intent, NEW_UPDATE_ACTIVITY_REQUEST_CODE);
        });




    }



    // NoteDiff: A DiffUtil.ItemCallback class to efficiently compare the items in the list.
    // This ensures that only the changed items in the list are updated, improving performance.
    static class NoteDiff extends DiffUtil.ItemCallback<MoodNote> {

        // areItemsTheSame: Determines if two items are the same (based on their reference).
        // This is called by DiffUtil to check if the items are the same, not just their content.
        @Override
        public boolean areItemsTheSame(@NonNull MoodNote oldItem, @NonNull MoodNote newItem) {
            return oldItem == newItem;
        }


        // areContentsTheSame: Determines if the contents of two items are the same.
        // This method compares the content of the MoodNote (e.g., the note text).
        @Override
        public boolean areContentsTheSame(@NonNull MoodNote oldItem, @NonNull MoodNote newItem) {
            return oldItem.getmNote().equals(newItem.getmNote());
        }
    }


}
