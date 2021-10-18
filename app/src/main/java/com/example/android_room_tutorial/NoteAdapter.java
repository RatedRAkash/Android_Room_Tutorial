package com.example.android_room_tutorial;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

//public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {
public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteHolder> {

    //private List<Note> notes = new ArrayList<>();
    OnItemClickListener listener;

    //Constructor
    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    //LIVEDATA kono UPDATE hobe eitai sheita Define korbo
    // 2 ta LIST er item moddhe DIFFERENCE or COMPARISON korbo ei "DiffUtil" er maddhobe
    private static final @NonNull DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {

        //2 ta alada item same hobe jokon tader "ID" same hobe
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        //Pura LIST er Content kokhon same hobe? Jokon sobar Old & Puran 2 tare ei Title, Description & Priority same hobe
        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {

            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getPriority() == newItem.getPriority();
        }
    };




    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new NoteHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote = getItem(position);
        holder.textViewTitle.setText(currentNote.getTitle());
        holder.textViewDescription.setText(currentNote.getDescription());
        holder.textViewPriority.setText(String.valueOf(currentNote.getPriority()));

    }

    //ListAdapter will take care of THIS
//    @Override
//    public int getItemCount() {
//        return notes.size();
//    }

//    public void setNotes(List<Note> notes) {
//        this.notes = notes;
//        notifyDataSetChanged();
//    }

    //Jei Position Swipe korlam Shei Position ee kon Note Ase... shei Note tar instance Return korbe
    public Note getNoteAtPos(int position) {
        Note note = getItem(position);
        return note;
    }


    //**********************NoteHolder Begin********************************************************
    //Single ROW er sokol kaaj ei NoteHolder class er vitor hobe
    class NoteHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);

            //UPDATE korar jonno jokon amra ekta ROW ke onek khon dhore rakbo tokon eita call hobe
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }

                }
            });


        }
    }
    //**********************NoteHolder End********************************************************


    public interface OnItemClickListener {
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


}
