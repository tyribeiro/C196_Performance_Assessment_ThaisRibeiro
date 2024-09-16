package com.example.c196_performanceassessment_thaisribeiro.helper;

import com.example.c196_performanceassessment_thaisribeiro.Controller.MainActivity;
import com.example.c196_performanceassessment_thaisribeiro.Controller.CreateActivity.CUCourseActivity;
import com.example.c196_performanceassessment_thaisribeiro.Controller.CreateActivity.CUInstructorActivity;
import com.example.c196_performanceassessment_thaisribeiro.Controller.DetailsActivity.DAssessmentActivity;
import com.example.c196_performanceassessment_thaisribeiro.Controller.DetailsActivity.DCourseActivity;
import com.example.c196_performanceassessment_thaisribeiro.Controller.DetailsActivity.DInstructorActivity;
import com.example.c196_performanceassessment_thaisribeiro.Controller.DetailsActivity.DNoteActivity;
import com.example.c196_performanceassessment_thaisribeiro.Controller.DetailsActivity.DTermActivity;
import com.example.c196_performanceassessment_thaisribeiro.Controller.ListActivity.LOfInstructorsActivity;
import com.example.c196_performanceassessment_thaisribeiro.Controller.ListActivity.LOfTermsActivity;
import com.example.c196_performanceassessment_thaisribeiro.Controller.MainActivity;

/**
 * Utility class for handling screen navigation by mapping screen names to their respective Activity classes.
 */
public class SwitchScreenUtility {
    // Constants representing different screen names

    public static final String LIST_OF_TERMS_ACT_Va = "ListOfTermsActivity";
    public static final String LIST_OF_INSTRUCTORS_ACT_Va = "ListOfInstructorsActivity";
    public static final String HOME_SCREEN_ACT_Va = "MainActivity";
    public static final String DETAILED_TERM_ACT_Va = "DetailedTermActivity";
    public static final String DETAILED_NOTE_ACT_Va = "DetailedNoteActivity";
    public static final String DETAILED_COURSE_ACT_Va = "DetailedCourseActivity";
    public static final String DETAILED_INSTRUCTOR_ACT_Va = "DetailedInstructorActivity";
    public static final String DETAILED_ASSESSMENT_ACT_Va = "DetailedAssessmentActivity";
    public static final String CREATE_OR_UPDATE_COURSE_ACT_Va = "CreateOrUpdateCourseActivity";
    public static final String CREATE_OR_UPDATE_INSTRUCTOR_ACT_Va = "CreateOrUpdateInstructorActivity";
    // Constants representing action values for adding or updating entities

    public static final String ADD_TERM_VALUE_Va = "Add Term";
    public static final String ADD_COURSE_VAL_Va = "Add Course";
    public static final String ADD_INSTRUCTOR_VAL_Va = "Add Instructor";
    public static final String ADD_NOTE_VAL_Va = "Add Note";
    public static final String ADD_ASSESSMENT_VAL_Va = "Add Assessment";
    public static final String UPDATE_TERM_VAL_Va = "Update Term";
    public static final String UPDATE_ASSESSMENT_VAL_Va = "Update Assessment";
    public static final String UPDATE_COURSE_VAL_Va = "Update Course";
    public static final String UPDATE_NOTE_VAL_Va = "Update Note";
    public static final String UPDATE_INSTRUCTOR_VAL_Va = "Update Instructor";

    // Constants representing various key values used for intent extras

    public static final String TERM_ID_KEY_Va = "term_id";
    public static final String ASSESSMENT_ID_KEY_Va = "assessment_id";
    public static final String COURSE_NOTE_ID_KEY_Va = "course_note_id";
    public static final String ADD_OR_UPDATE_SCREEN_KEY_Va = "add_or_update_screen";
    public static final String CAME_FROM_ADD_OR_UPDATE_SCREEN_KEY_Va = "came_from_add_or_update_screen";
    public static final String COURSE_ID_KEY_Va = "course_id";
    public static final String INSTRUCTOR_ID_KEY_Va = "instructor_id";
    public static final String CAME_FROM_KEY_Va = "came_from";
    public static final String CAME_FROM_KEY2_Va = "came_from2";


    /**
     * Retrieves the corresponding Activity class for a given screen name.
     *
     * @param actName_Va The name of the screen for which to retrieve the Activity class.
     * @return The corresponding Activity class, or null if the screen name is not recognized.
     */
    public static Class getActClass_M(String actName_Va) {
        Class cName_Va = null;
        // Map screen names to their respective Activity classes

        switch (actName_Va) {
            case HOME_SCREEN_ACT_Va:
                cName_Va = MainActivity.class;
                break;
            case LIST_OF_TERMS_ACT_Va:
                cName_Va = LOfTermsActivity.class;
                break;
            case DETAILED_TERM_ACT_Va:
                cName_Va = DTermActivity.class;
                break;
            case DETAILED_NOTE_ACT_Va:
                cName_Va = DNoteActivity.class;
                break;
            case DETAILED_COURSE_ACT_Va:
                cName_Va = DCourseActivity.class;
                break;
            case DETAILED_INSTRUCTOR_ACT_Va:
                cName_Va = DInstructorActivity.class;
                break;
            case DETAILED_ASSESSMENT_ACT_Va:
                cName_Va = DAssessmentActivity.class;
                break;
            case LIST_OF_INSTRUCTORS_ACT_Va:
                cName_Va = LOfInstructorsActivity.class;
                break;
            case CREATE_OR_UPDATE_COURSE_ACT_Va:
                cName_Va = CUCourseActivity.class;
                break;
            case CREATE_OR_UPDATE_INSTRUCTOR_ACT_Va:
                cName_Va = CUInstructorActivity.class;
                break;
        }
        return cName_Va;
    }
}
