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
import com.example.c196_performanceassessment_thaisribeiro.entities.Course;
import com.example.c196_performanceassessment_thaisribeiro.helper.SelectedListItemUtility;

import java.util.List;
/**
 * Adapter for displaying a list of courses in a RecyclerView.
 * This adapter binds course data to views and handles item clicks.
 */
public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    private List<Course> m_Courses_Va;
    private final Context context_Va;
    private final LayoutInflater m_Inflater_Va;

    /**
     * Creates an instance of CourseAdapter.
     *
     * @param context_Va The context of the calling activity.
     */
    public CourseAdapter(Context context_Va) {
        m_Inflater_Va = LayoutInflater.from(context_Va);
        this.context_Va = context_Va;
    }

    /**
     * ViewHolder for displaying course items in the RecyclerView.
     */
    public class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView course_Item_View_Va;
        /**
         * Creates an instance of CourseViewHolder.
         *
         * @param itemView The view for each item in the RecyclerView.
         */
        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            course_Item_View_Va = itemView.findViewById(R.id.listItem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position_Va = getAdapterPosition();
                    final Course current_Course_Va = m_Courses_Va.get(position_Va);

                    SelectedListItemUtility.setSelected_Course_Va(current_Course_Va);
                }
            });
        }
    }

    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item_View_Va = m_Inflater_Va.inflate(R.layout.list_item_v2_sc, parent, false);
        return new CourseAdapter.CourseViewHolder(item_View_Va);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if (m_Courses_Va != null) {
            Course current_Va = m_Courses_Va.get(position);
            String name_Va = current_Va.getTitle();
            holder.course_Item_View_Va.setText(name_Va);
        } else {
            holder.course_Item_View_Va.setText("No Course Name");
        }
    }

    @Override
    public int getItemCount() {
        if (m_Courses_Va != null) {
            return m_Courses_Va.size();
        }
        return 0;
    }

    /**
     * Updates the list of courses and notifies the adapter of the change.
     *
     * @param courses The new list of courses.
     */
    public void setCourses(List<Course> courses) {
        m_Courses_Va = courses;
        notifyDataSetChanged();
    }
}
