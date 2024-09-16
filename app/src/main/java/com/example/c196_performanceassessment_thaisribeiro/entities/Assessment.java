package com.example.c196_performanceassessment_thaisribeiro.entities;

import android.widget.EditText;
import android.widget.Spinner;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Entity class representing an Assessment.
 * This class is annotated with Room's @Entity to define the table structure for storing assessments.
 */
@Entity(tableName = "assessments")
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessmentID;
    private String title;
    private String type;
    private String information;
    private String startDate;
    private String endDate;

    private int courseID;

    /**
     * Constructor for creating an Assessment object with an ID.
     *
     * @param assessmentID Unique identifier for the assessment.
     * @param title Title of the assessment.
     * @param type Type of the assessment (e.g., Objective, Performance).
     * @param information Additional information about the assessment.
     * @param startDate Start date of the assessment.
     * @param endDate End date of the assessment.
     * @param courseID Foreign key representing the associated course ID.
     */
    public Assessment(int assessmentID, String title, String type, String information, String startDate, String endDate, int courseID) {
        this.setAssessmentID(assessmentID);
        this.setTitle(title);
        this.setInformation(information);
        this.setType(type);
        this.setStartDate(startDate);
        this.setEndDate(endDate);
        this.setCourseID(courseID);
    }

    /**
     * Constructor for creating an Assessment object without an ID. Used primarily by Room.
     *
     * @param title Title of the assessment.
     * @param type Type of the assessment (e.g., Objective, Performance).
     * @param information Additional information about the assessment.
     * @param startDate Start date of the assessment.
     * @param endDate End date of the assessment.
     * @param courseID Foreign key representing the associated course ID.
     */
    @Ignore
    public Assessment(String title, String type, String information, String startDate, String endDate, int courseID) {
        this.setTitle(title);
        this.setInformation(information);
        this.setType(type);
        this.setStartDate(startDate);
        this.setEndDate(endDate);
        this.setCourseID(courseID);
    }

    // Getters and setters

    /**
     * Sets the unique identifier for the assessment.
     *
     * @param assessmentID The ID to set.
     */
    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    /**
     * Sets the title of the assessment.
     *
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets additional information about the assessment.
     *
     * @param information The information to set.
     */
    public void setInformation(String information) {
        this.information = information;
    }

    /**
     * Sets the type of the assessment.
     *
     * @param type The type to set (e.g., Objective, Performance).
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Sets the start date of the assessment.
     *
     * @param startDate The start date to set.
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * Sets the end date of the assessment.
     *
     * @param endDate The end date to set.
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * Sets the foreign key representing the associated course ID.
     *
     * @param courseID The course ID to set.
     */
    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    /**
     * Returns the unique identifier of the assessment.
     *
     * @return The assessment ID.
     */
    public int getAssessmentID() {
        return this.assessmentID;
    }

    /**
     * Returns the title of the assessment.
     *
     * @return The title.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Returns additional information about the assessment.
     *
     * @return The information.
     */
    public String getInformation() {
        return this.information;
    }

    /**
     * Returns the type of the assessment.
     *
     * @return The type (e.g., Objective, Performance).
     */
    public String getType() {
        return this.type;
    }

    /**
     * Returns the start date of the assessment.
     *
     * @return The start date.
     */
    public String getStartDate() {
        return this.startDate;
    }

    /**
     * Returns the end date of the assessment.
     *
     * @return The end date.
     */
    public String getEndDate() {
        return this.endDate;
    }

    /**
     * Returns the foreign key representing the associated course ID.
     *
     * @return The course ID.
     */
    public int getCourseID() {
        return this.courseID;
    }

    /**
     * Updates the Assessment fields from user input fields.
     *
     * @param assessmentName The EditText field for the assessment name.
     * @param assessmentInfo The EditText field for the assessment information.
     * @param assessmentType The Spinner field for selecting the assessment type.
     * @param startDate The EditText field for the start date.
     * @param endDate The EditText field for the end date.
     */
    public void updateInputFields_M(EditText assessmentName, EditText assessmentInfo, Spinner assessmentType, EditText startDate, EditText endDate) {
        setTitle(assessmentName.getText().toString().trim());
        setInformation(assessmentInfo.getText().toString().trim());
        setType(assessmentType.getSelectedItem().toString());
        setStartDate(startDate.getText().toString());
        setEndDate(endDate.getText().toString());
    }
}
