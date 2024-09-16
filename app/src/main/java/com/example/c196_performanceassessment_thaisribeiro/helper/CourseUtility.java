package com.example.c196_performanceassessment_thaisribeiro.helper;

import com.example.c196_performanceassessment_thaisribeiro.entities.Course;
import com.example.c196_performanceassessment_thaisribeiro.entities.CourseNote;
import com.example.c196_performanceassessment_thaisribeiro.entities.Term;
import com.example.c196_performanceassessment_thaisribeiro.helper.TermUtility;

import java.time.LocalDate;
import java.util.ArrayList;
/**
 * Utility class for handling operations related to courses.
 */
public class CourseUtility {
    /**
     * Checks if a given course's dates are within the range of a term's dates.
     *
     * @param course_Va the course to be checked
     * @param term_Id_Va the ID of the term to compare against
     * @param db_Term_List_Va the list of terms from the database
     * @return true if the course's dates are within the term's dates, false otherwise
     */
    public static boolean areCourse_WithinRangeOfTermDates_M(Course course_Va, int term_Id_Va, ArrayList<Term> db_Term_List_Va) {
        Term term_V = TermUtility.retrieveTerm_From_Db_By_Term_ID_M(db_Term_List_Va, term_Id_Va);

        if (term_V == null) {
            return false;
        }

        LocalDate courseStartD_Va = LocalDate.parse(course_Va.getStartDate());
        LocalDate courseEndD_Va = LocalDate.parse(course_Va.getEndDate());
        LocalDate termStartD_Va = LocalDate.parse(term_V.getStartDate());
        LocalDate termEndDa_Va = LocalDate.parse(term_V.getEndDate());

        if (courseStartD_Va.isEqual(termStartD_Va) && courseEndD_Va.isEqual(termEndDa_Va)) {
            return true;
        } else if (courseStartD_Va.isAfter(termStartD_Va) && courseEndD_Va.isEqual(termEndDa_Va)) {
            return true;
        } else if (courseStartD_Va.isAfter(termStartD_Va) && courseEndD_Va.isBefore(termEndDa_Va)) {
            return true;
        } else if (courseStartD_Va.isEqual(termStartD_Va) && courseEndD_Va.isBefore(termEndDa_Va)) {
            return true;
        } else if (courseEndD_Va.isEqual(termEndDa_Va) && courseStartD_Va.isAfter(termStartD_Va)) {
            return true;
        } else if (courseStartD_Va.isEqual(termStartD_Va) && courseEndD_Va.isEqual(termStartD_Va)) {
            return true;
        } else return courseStartD_Va.isEqual(termEndDa_Va) && courseEndD_Va.isEqual(termEndDa_Va);
    }
    /**
     * Checks if a course already exists for a term.
     *
     * @param allCoursesFTerm_Va the list of all courses for the term
     * @param add_Course_Va the course to be checked for existence
     * @return true if the course exists, false otherwise
     */
    public static boolean doesCourseExistForT_M(ArrayList<Course> allCoursesFTerm_Va, Course add_Course_Va) {
        if (allCoursesFTerm_Va == null) {
            return false;
        }


        for(Course dbCourse : allCoursesFTerm_Va) {
            if (dbCourse.getCourseID() == add_Course_Va.getCourseID()) {
                continue;
            }

            if(add_Course_Va.getTitle().toLowerCase().equals(dbCourse.getTitle().toLowerCase())) {
                return true;
            }
        }
        return false;
    }
    /**
     * Retrieves all courses for a given term.
     *
     * @param courseListFDatabase_Va the list of all courses from the database
     * @param term_Id_Va the ID of the term to filter by
     * @return a list of courses for the specified term
     */
    public static ArrayList<Course> getAllCoursesForT_M(ArrayList<Course> courseListFDatabase_Va, int term_Id_Va) {
        if (courseListFDatabase_Va.size() == 0) {
            return null;
        }

        ArrayList<Course> coursesF_Term_Va = new ArrayList<>();

        for (Course course : courseListFDatabase_Va) {
            if (course.getTermID() == term_Id_Va) {
                coursesF_Term_Va.add(course);
            }
        }
        return coursesF_Term_Va;
    }
    /**
     * Retrieves a course from the database by its ID.
     *
     * @param dbCourse_List_Va the list of courses from the database
     * @param course_Id_Va the ID of the course to retrieve
     * @return the course with the specified ID, or null if not found
     */
    public static Course retrieveCourseFromD_ByCourseID_M(ArrayList<Course> dbCourse_List_Va, int course_Id_Va) {
        if (dbCourse_List_Va.size() == 0) {
            return null;
        }

        for (Course dbCourse : dbCourse_List_Va) {
            if (dbCourse.getCourseID() == course_Id_Va) {
                return dbCourse;
            }
        }
        return null;
    }

    /**
     * Retrieves all notes for a given course.
     *
     * @param dbNote_List_Va the list of all course notes from the database
     * @param course_Id_Va the ID of the course to filter notes by
     * @return a list of notes for the specified course
     */
    public static ArrayList<CourseNote> getAllN_For_Course_M(ArrayList<CourseNote> dbNote_List_Va, int course_Id_Va) {
        if (dbNote_List_Va.size() == 0) {
            return new ArrayList<>();
        }

        ArrayList<CourseNote> notesF_Course_Va = new ArrayList<>();

        for(CourseNote dbNote : dbNote_List_Va) {
            if(dbNote.getCourseID() == course_Id_Va) {
                notesF_Course_Va.add(dbNote);
            }
        }
        return notesF_Course_Va;
    }
}
