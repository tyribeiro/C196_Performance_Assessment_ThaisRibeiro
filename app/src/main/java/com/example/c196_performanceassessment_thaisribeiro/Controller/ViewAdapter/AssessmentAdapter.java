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
import com.example.c196_performanceassessment_thaisribeiro.entities.Assessment;
import com.example.c196_performanceassessment_thaisribeiro.helper.SelectedListItemUtility;

import java.util.List;
/**
 * RecyclerView.Adapter subclass for displaying a list of assessments in a RecyclerView.
 * <p>
 * This adapter binds Assessment objects to views displayed in a RecyclerView. It handles
 * the creation and binding of view holders, and updates the displayed list when data changes.
 * It also manages click events for each item to allow selection of assessments.
 * </p>
 */
public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {
    private List<Assessment> m_Assessments_Va;
    private final Context context_Va;
    private final LayoutInflater m_Inflater_Va;
    /**
     * Constructs an AssessmentAdapter with the given context.
     *
     * @param context_Va the context associated with this adapter
     */
    public AssessmentAdapter(Context context_Va) {
        m_Inflater_Va = LayoutInflater.from(context_Va);
        this.context_Va = context_Va;
    }

    /**
     * ViewHolder for displaying a single assessment item in the RecyclerView.
     */
    public class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessment_Item_View_Va;
        /**
         * Creates a new ViewHolder for an assessment item.
         *
         * @param itemView the view for the individual item
         */
        public AssessmentViewHolder(@NonNull View itemView) {
            super(itemView);
            assessment_Item_View_Va = itemView.findViewById(R.id.listItem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos_Va = getAdapterPosition();
                    final Assessment current_Assessment_Va = m_Assessments_Va.get(pos_Va);

                    SelectedListItemUtility.setSelected_Assessment_Va(current_Assessment_Va);
                }
            });
        }
    }

    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item_View_Va = m_Inflater_Va.inflate(R.layout.list_item_v2_sc, parent, false);
        return new AssessmentAdapter.AssessmentViewHolder(item_View_Va);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {
        if (m_Assessments_Va != null) {
            Assessment current_Va = m_Assessments_Va.get(position);
            String name_Va = current_Va.getTitle();
            holder.assessment_Item_View_Va.setText(name_Va);
        } else {
            holder.assessment_Item_View_Va.setText("No Assessment Name");
        }
    }

    @Override
    public int getItemCount() {
        if (m_Assessments_Va != null) {
            return m_Assessments_Va.size();
        }
        return 0;
    }

    /**
     * Updates the list of assessments and notifies the adapter of the changes.
     *
     * @param assessments the new list of assessments
     */
    public void setAssessments(List<Assessment> assessments) {
        m_Assessments_Va = assessments;
        notifyDataSetChanged();
    }
}
