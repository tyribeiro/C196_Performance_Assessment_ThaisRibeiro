package com.example.c196_performanceassessment_thaisribeiro.helper;

import com.example.c196_performanceassessment_thaisribeiro.entities.Assessment;
import com.example.c196_performanceassessment_thaisribeiro.entities.Course;
import com.example.c196_performanceassessment_thaisribeiro.entities.CourseInstructor;
import com.example.c196_performanceassessment_thaisribeiro.entities.CourseNote;
import com.example.c196_performanceassessment_thaisribeiro.entities.Term;
/**
 * Utility class for managing the currently selected term, course, instructor, assessment, and note.
 * This class provides static methods to set and retrieve the selected items across the application.
 */
public class SelectedListItemUtility {
    /** The currently selected term. */
    private static Term selected_Term_Va = null;

    /** The currently selected course. */
    private static Course selected_Course_Va = null;

    /** The currently selected instructor. */
    private static CourseInstructor selected_Instructor_Va = null;

    /** The currently selected assessment. */
    private static Assessment selected_Assessment_Va = null;

    /** The currently selected course note. */
    private static CourseNote selected_Note_Va = null;


    /**
     * Sets the currently selected term.
     *
     * @param term The term to set as selected.
     */
    public static void setSelected_Term_Va(Term term) {
        selected_Term_Va = term;
    }
    /**
     * Sets the currently selected course.
     *
     * @param course The course to set as selected.
     */
    public static void setSelected_Course_Va(Course course) {
        selected_Course_Va = course;
    }

    /**
     * Sets the currently selected instructor.
     *
     * @param instructor The instructor to set as selected.
     */
    public static void setSelected_Instructor_Va(CourseInstructor instructor) {
        selected_Instructor_Va = instructor;
    }

    /**
     * Sets the currently selected assessment.
     *
     * @param assessment The assessment to set as selected.
     */
    public static void setSelected_Assessment_Va(Assessment assessment) {
        selected_Assessment_Va = assessment;
    }

    /**
     * Sets the currently selected course note.
     *
     * @param courseNote The course note to set as selected.
     */
    public static void setSelected_Note_Va(CourseNote courseNote) {
        selected_Note_Va = courseNote;
    }
    /**
     * Retrieves the currently selected term.
     *
     * @return The currently selected term, or null if no term is selected.
     */
    public static Term getSelected_Term_Va() {
        return selected_Term_Va;
    }
    /**
     * Retrieves the currently selected instructor.
     *
     * @return The currently selected instructor, or null if no instructor is selected.
     */
    public static CourseInstructor getSelected_Instructor_Va() {
        return selected_Instructor_Va;
    }
    /**
     * Retrieves the currently selected course.
     *
     * @return The currently selected course, or null if no course is selected.
     */
    public static Course getSelected_Course_Va() {
        return selected_Course_Va;
    }
    /**
     * Retrieves the currently selected assessment.
     *
     * @return The currently selected assessment, or null if no assessment is selected.
     */
    public static Assessment getSelected_Assessment_Va() {
        return selected_Assessment_Va;
    }

    /**
     * Retrieves the currently selected course note.
     *
     * @return The currently selected course note, or null if no note is selected.
     */
    public static CourseNote getSelected_Note_Va() {
        return selected_Note_Va;
    }
}
