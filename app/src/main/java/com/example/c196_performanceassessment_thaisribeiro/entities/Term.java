package com.example.c196_performanceassessment_thaisribeiro.entities;

import android.widget.EditText;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Represents a term in the application.
 * This class is an entity for the "terms" table in the database.
 */
@Entity(tableName = "terms")
public class Term {
    @PrimaryKey(autoGenerate = true)
    private int termID;
    private String title;
    private String startDate;
    private String endDate;

    /**
     * Constructs a Term with all fields.
     *
     * @param termID the unique ID of the term
     * @param title the title of the term
     * @param startDate the start date of the term
     * @param endDate the end date of the term
     */
    public Term(int termID, String title, String startDate, String endDate) {
        this.setTermID(termID);
        this.setTitle(title);
        this.setStartDate(startDate);
        this.setEndDate(endDate);
    }

    /**
     * Constructs a Term without a termID (used for new terms).
     *
     * @param title the title of the term
     * @param startDate the start date of the term
     * @param endDate the end date of the term
     */
    @Ignore
    public Term(String title, String startDate, String endDate) {
        this.setTitle(title);
        this.setStartDate(startDate);
        this.setEndDate(endDate);
    }

    /**
     * Sets the unique ID of the term.
     *
     * @param termID the unique ID to set
     */
    public void setTermID(int termID) {
        this.termID = termID;
    }

    /**
     * Sets the title of the term.
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the start date of the term.
     *
     * @param startDate the start date to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * Sets the end date of the term.
     *
     * @param endDate the end date to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the unique ID of the term.
     *
     * @return the unique ID of the term
     */
    public int getTermID() {
        return this.termID;
    }

    /**
     * Gets the title of the term.
     *
     * @return the title of the term
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Gets the start date of the term.
     *
     * @return the start date of the term
     */
    public String getStartDate() {
        return this.startDate;
    }

    /**
     * Gets the end date of the term.
     *
     * @return the end date of the term
     */
    public String getEndDate() {
        return this.endDate;
    }

    /**
     * Updates the fields of the term using values from EditText fields.
     *
     * @param termName the EditText containing the term title
     * @param startDate the EditText containing the term start date
     * @param endDate the EditText containing the term end date
     */
    public void updateFields_M(EditText termName, EditText startDate, EditText endDate) {
        this.setTitle(termName.getText().toString().trim());
        this.setStartDate(startDate.getText().toString());
        this.setEndDate(endDate.getText().toString());
    }
}
