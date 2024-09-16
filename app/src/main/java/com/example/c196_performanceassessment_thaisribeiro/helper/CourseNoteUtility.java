package com.example.c196_performanceassessment_thaisribeiro.helper;

import com.example.c196_performanceassessment_thaisribeiro.entities.CourseNote;

import java.util.ArrayList;
/**
 * Utility class for handling operations related to course notes.
 */
public class CourseNoteUtility {

    /**
     * Retrieves a course note from the database by its ID.
     *
     * @param dbNote_List_Va the list of course notes from the database
     * @param note_Id_Va the ID of the note to retrieve
     * @return the course note with the specified ID, or null if not found
     */
    public static CourseNote retrieveN_FromDatabaseByNoteID_m(ArrayList<CourseNote> dbNote_List_Va, int note_Id_Va) {
        if (dbNote_List_Va.size() == 0) {
            return null;
        }

        for (CourseNote dbNote : dbNote_List_Va) {
            if (dbNote.getCourseNoteID() == note_Id_Va) {
                return dbNote;
            }
        }
        return null;
    }

}
