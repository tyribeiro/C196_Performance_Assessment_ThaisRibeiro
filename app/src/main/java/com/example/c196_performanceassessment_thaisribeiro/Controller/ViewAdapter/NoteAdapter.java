package com.example.c196_performanceassessment_thaisribeiro.Controller.ViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196_performanceassessment_thaisribeiro.R;
import com.example.c196_performanceassessment_thaisribeiro.entities.CourseNote;
import com.example.c196_performanceassessment_thaisribeiro.helper.SelectedListItemUtility;

import java.util.List;
/**
 * Adapter for displaying a list of course notes in a RecyclerView.
 * This adapter binds course note data to views and handles item clicks.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private List<CourseNote> m_Course_Notes_Va;
    private final Context context_Va;
    private final LayoutInflater m_Inflater_Va;

    /**
     * Creates an instance of NoteAdapter.
     *
     * @param context_Va The context of the calling activity.
     */
    public NoteAdapter(Context context_Va) {
        m_Inflater_Va = LayoutInflater.from(context_Va);
        this.context_Va = context_Va;
    }
    /**
     * ViewHolder for displaying course note items in the RecyclerView.
     */
    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView note_Item_View_Va;
        /**
         * Creates an instance of NoteViewHolder.
         *
         * @param itemView The view for each item in the RecyclerView.
         */
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            note_Item_View_Va = itemView.findViewById(R.id.listItem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position_Va = getAdapterPosition();
                    final CourseNote current_Note_Va = m_Course_Notes_Va.get(position_Va);

                    SelectedListItemUtility.setSelected_Note_Va(current_Note_Va);
                }
            });
        }
    }

    @NonNull
    @Override
    public NoteAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item_View_Va = m_Inflater_Va.inflate(R.layout.list_item_v2_sc, parent, false);
        return new NoteAdapter.NoteViewHolder(item_View_Va);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.NoteViewHolder holder, int position) {
        if (m_Course_Notes_Va != null) {
            CourseNote current_Va = m_Course_Notes_Va.get(position);
            String name_Va = current_Va.getNote();
            holder.note_Item_View_Va.setText(name_Va);
        } else {
            holder.note_Item_View_Va.setText("No Course Note");
        }
    }

    @Override
    public int getItemCount() {
        if (m_Course_Notes_Va != null) {
            return m_Course_Notes_Va.size();
        }
        return 0;
    }
    /**
     * Updates the list of course notes and notifies the adapter of the change.
     *
     * @param courseNote The new list of course notes.
     */
    public void setNotes(List<CourseNote> courseNote) {
        m_Course_Notes_Va = courseNote;
        notifyDataSetChanged();
    }
}
