package com.example.moodnote;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


// MoodNoteViewHolder: This ViewHolder binds the data for a single item (MoodNote) in the RecyclerView
// It holds references to the Views in the item layout (such as TextView, ImageView) for efficient data binding.
class MoodNoteViewHolder extends RecyclerView.ViewHolder {
    private final TextView noteDate;
    private final TextView noteContent;
    private final ImageView noteMood;
    private final ImageView noteDayNight;
    private final ImageButton btnDelete;

    // Constructor that initializes the Views (this is called when a ViewHolder is created)
    private MoodNoteViewHolder(View itemView, final MoodNoteListAdapter.OnDeleteClickListener deleteClickListener) {
        super(itemView);

        noteDate = itemView.findViewById(R.id.TVDateContent);
        noteContent = itemView.findViewById(R.id.TVNoteContent);
        noteMood = itemView.findViewById(R.id.IVMood);
        noteDayNight = itemView.findViewById(R.id.IVDayNight);

        btnDelete = itemView.findViewById(R.id.btnDelete); // Initialize the delete button

        // Set up the delete button click listener
        btnDelete.setOnClickListener(v -> {
            if (deleteClickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    deleteClickListener.onDeleteClick(position); // Notify the adapter
                }
            }
        });
    }

    // Method to bind the data (from MoodNote) to the Views in the ViewHolder

    public void bind(String date, int mood, boolean daynight, String note) {
        noteDate.setText(date);
        noteContent.setText(note);
        if (mood == 1)
            noteMood.setImageResource(R.drawable.happy);
        else if (mood == 2)
            noteMood.setImageResource(R.drawable.angry);
        else
            noteMood.setImageResource(R.drawable.sad);

        if (daynight == true)
            noteDayNight.setImageResource(R.drawable.day);
        else
            noteDayNight.setImageResource(R.drawable.night);
    }


    // Factory method to create a new ViewHolder for a list item view
    static MoodNoteViewHolder create(ViewGroup parent, MoodNoteListAdapter.OnDeleteClickListener deleteClickListener) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.individual_item_view, parent, false);
        return new MoodNoteViewHolder(view, deleteClickListener);
    }


}
