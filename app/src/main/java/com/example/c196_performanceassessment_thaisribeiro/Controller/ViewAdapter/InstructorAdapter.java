package com.example.c196_performanceassessment_thaisribeiro.Controller.ViewAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196_performanceassessment_thaisribeiro.R;
import com.example.c196_performanceassessment_thaisribeiro.entities.CourseInstructor;
import com.example.c196_performanceassessment_thaisribeiro.helper.SelectedListItemUtility;

import java.util.List;

/**
 * Adapter for displaying a list of course instructors in a RecyclerView.
 * This adapter binds course instructor data to views and handles item clicks.
 */
public class InstructorAdapter extends RecyclerView.Adapter<InstructorAdapter.InstructorViewHolder> {
    private List<CourseInstructor> m_Instructors_Va;
    private final Context context_Va;
    private final LayoutInflater m_Inflater_Va;
    /**
     * Creates an instance of InstructorAdapter.
     *
     * @param context_Va The context of the calling activity.
     */
    public InstructorAdapter(Context context_Va) {
        m_Inflater_Va = LayoutInflater.from(context_Va);
        this.context_Va = context_Va;
    }

    /**
     * ViewHolder for displaying course instructor items in the RecyclerView.
     */
    public class InstructorViewHolder extends RecyclerView.ViewHolder {
        private final TextView instructor_Item_View_Va;

        /**
         * Creates an instance of InstructorViewHolder.
         *
         * @param itemView The view for each item in the RecyclerView.
         */
        public InstructorViewHolder(@NonNull View itemView) {
            super(itemView);
            instructor_Item_View_Va = itemView.findViewById(R.id.listItem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position_Va = getAdapterPosition();
                    final CourseInstructor current_Instructor_Va = m_Instructors_Va.get(position_Va);

                    SelectedListItemUtility.setSelected_Instructor_Va(current_Instructor_Va);
                }
            });
        }
    }

    @NonNull
    @Override
    public InstructorAdapter.InstructorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item_View_Va = m_Inflater_Va.inflate(R.layout.list_item_v2_sc, parent, false);
        return new InstructorAdapter.InstructorViewHolder(item_View_Va);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull InstructorAdapter.InstructorViewHolder holder, int position) {
        if (m_Instructors_Va != null) {
            CourseInstructor current_Va = m_Instructors_Va.get(position);
            String name_Va = current_Va.getName();
            holder.instructor_Item_View_Va.setText(name_Va);
        } else {
            holder.instructor_Item_View_Va.setText("No Instructor Name");
        }
    }

    @Override
    public int getItemCount() {
        if (m_Instructors_Va != null) {
            return m_Instructors_Va.size();
        }
        return 0;
    }
    /**
     * Updates the list of course instructors and notifies the adapter of the change.
     *
     * @param instructors The new list of course instructors.
     */
    public void setInstructors(List<CourseInstructor> instructors) {
        m_Instructors_Va = instructors;
        notifyDataSetChanged();
    }
}
