package com.example.c196_performanceassessment_thaisribeiro.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Represents a note associated with a course in the application.
 * This entity is stored in the 'course_notes' table in the database.
 */
@Entity(tableName = "course_notes")
public class CourseNote {
    @PrimaryKey(autoGenerate = true)
    private int courseNoteID;
    private String note;

    private int courseID;
    /**
     * Constructs a new CourseNote with the specified ID, note content, and course ID.
     *
     * @param courseNoteID the unique ID of the course note
     * @param note the content of the note
     * @param courseID the ID of the course associated with this note
     */
    public CourseNote(int courseNoteID, String note, int courseID) {
        this.setCourseNoteID(courseNoteID);
        this.setCourseID(courseID);
        this.setNote(note);
    }
    /**
     * Constructs a new CourseNote with the specified note content and course ID.
     * This constructor is ignored by Room database.
     *
     * @param note the content of the note
     * @param courseID the ID of the course associated with this note
     */
    @Ignore
    public CourseNote(String note, int courseID) {
        this.setCourseID(courseID);
        this.setNote(note);
    }

    /**
     * Sets the course ID associated with this note.
     *
     * @param courseID the ID of the course
     */
    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    /**
     * Sets the content of the note.
     *
     * @param note the content of the note
     */
    public void setNote(String note) {
        this.note = note;
    }
    /**
     * Sets the unique ID of the course note.
     *
     * @param courseNoteID the unique ID of the course note
     */
    public void setCourseNoteID(int courseNoteID) {
        this.courseNoteID = courseNoteID;
    }
    /**
     * Returns the unique ID of the course note.
     *
     * @return the unique ID of the course note
     */
    public int getCourseNoteID() {
        return this.courseNoteID;
    }
    /**
     * Returns the ID of the course associated with this note.
     *
     * @return the ID of the course
     */
    public int getCourseID() {
        return this.courseID;
    }
    /**
     * Returns the content of the note.
     *
     * @return the content of the note
     */
    public String getNote() {
        return this.note;
    }
}