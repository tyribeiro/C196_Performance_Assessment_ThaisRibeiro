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
import com.example.c196_performanceassessment_thaisribeiro.entities.Term;
import com.example.c196_performanceassessment_thaisribeiro.helper.SelectedListItemUtility;

import java.util.List;
/**
 * Adapter for displaying a list of terms in a RecyclerView.
 * This adapter binds term data to views and handles item clicks.
 */
public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {
    private List<Term> m_Terms_Va;
    private final Context context_Va;
    private final LayoutInflater m_Inflater_Va;

    /**
     * Creates an instance of TermAdapter.
     *
     *   The context of the calling activity.
     */
    public TermAdapter(Context context_Va) {
        m_Inflater_Va = LayoutInflater.from(context_Va);
        this.context_Va = context_Va;
    }

    /**
     * ViewHolder for displaying term items in the RecyclerView.
     */
    public class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView term_Item_View_Va;
        /**
         * Creates an instance of TermViewHolder.
         *
         * @param itemView The view for each item in the RecyclerView.
         */
        public TermViewHolder(@NonNull View itemView) {
            super(itemView);
            term_Item_View_Va = itemView.findViewById(R.id.listItem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position_Va = getAdapterPosition();
                    final Term current_Term_Va = m_Terms_Va.get(position_Va);

                    SelectedListItemUtility.setSelected_Term_Va(current_Term_Va);
                }
            });
        }
    }

    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item_View_Va = m_Inflater_Va.inflate(R.layout.list_item_v2_sc, parent, false);
        return new TermViewHolder(item_View_Va);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {
        if (m_Terms_Va != null) {
            Term current_Va = m_Terms_Va.get(position);
            String name_Va = current_Va.getTitle();
            holder.term_Item_View_Va.setText(name_Va);
        } else {
            holder.term_Item_View_Va.setText("No Term Name");
        }
    }

    @Override
    public int getItemCount() {
        if (m_Terms_Va != null) {
            return m_Terms_Va.size();
        }
        return 0;
    }

    /**
     * Updates the list of terms and notifies the adapter of the change.
     *
     * @param   The new list of terms.
     */
    public void setTerms(List<Term> terms_Va) {
        m_Terms_Va = terms_Va;
        notifyDataSetChanged();
    }
}
