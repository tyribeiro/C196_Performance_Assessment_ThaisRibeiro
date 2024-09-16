package com.example.c196_performanceassessment_thaisribeiro.helper;

import com.example.c196_performanceassessment_thaisribeiro.entities.Assessment;
import com.example.c196_performanceassessment_thaisribeiro.entities.Course;
import com.example.c196_performanceassessment_thaisribeiro.helper.CourseUtility;

import java.time.LocalDate;
import java.util.ArrayList;
/**
 * Utility class for handling operations related to assessments.
 */
public class AssessmentUtility {
    /**
     * Retrieves an assessment from the database by its ID.
     *
     * @param dbAssessment_List_Va the list of assessments from the database
     * @param assessmentId the ID of the assessment to retrieve
     * @return the assessment with the specified ID, or null if not found
     */
    public static Assessment re_AssessmentFromDatabaseByAssessmentID_M(ArrayList<Assessment> dbAssessment_List_Va, int assessmentId) {
        if (dbAssessment_List_Va.size() == 0) {
            return null;
        }

        for (Assessment dbAssessment : dbAssessment_List_Va) {
            if (dbAssessment.getAssessmentID() == assessmentId) {
                return dbAssessment;
            }
        }
        return null;
    }
    /**
     * Retrieves all assessments for a given course ID.
     *
     * @param db_Assessment_List_Va the list of assessments from the database
     * @param course_ID_Va the ID of the course for which to retrieve assessments
     * @return a list of assessments associated with the specified course ID
     */
    public static ArrayList<Assessment> getAllAssessments_ForCourse_M(ArrayList<Assessment> db_Assessment_List_Va, int course_ID_Va) {
        if (db_Assessment_List_Va.size() == 0) {
            return new ArrayList<>();
        }

        ArrayList<Assessment> assessments_For_Course_Va = new ArrayList<>();

        for(Assessment dbAssessment : db_Assessment_List_Va) {
            if(dbAssessment.getCourseID() == course_ID_Va) {
                assessments_For_Course_Va.add(dbAssessment);
            }
        }
        return assessments_For_Course_Va;
    }
    /**
     * Checks if the dates of an assessment fall within the date range of a course.
     *
     * @param assessment_Va the assessment to check
     * @param course_Id_Va the ID of the course to compare against
     * @param db_Course_List_Va the list of courses from the database
     * @return true if the assessment dates are within the course dates, false otherwise
     */
    public static boolean areADatesWithinRangeOfCourseDates_M(Assessment assessment_Va, int course_Id_Va, ArrayList<Course> db_Course_List_Va) {
        Course course_Va = CourseUtility.retrieveCourseFromD_ByCourseID_M(db_Course_List_Va, course_Id_Va);

        if (course_Va == null) {
            return false;
        }

        LocalDate assessment_Start_Date_Va = LocalDate.parse(assessment_Va.getStartDate());
        LocalDate assessment_End_Date_Va = LocalDate.parse(assessment_Va.getEndDate());
        LocalDate course_Start_Date_Va = LocalDate.parse(course_Va.getStartDate());
        LocalDate c_EndDate_va = LocalDate.parse(course_Va.getEndDate());

        if (assessment_Start_Date_Va.isEqual(course_Start_Date_Va) && assessment_End_Date_Va.isEqual(c_EndDate_va)) {
            return true;
        } else if (assessment_Start_Date_Va.isAfter(course_Start_Date_Va) && assessment_End_Date_Va.isEqual(c_EndDate_va)) {
            return true;
        } else if (assessment_Start_Date_Va.isAfter(course_Start_Date_Va) && assessment_End_Date_Va.isBefore(c_EndDate_va)) {
            return true;
        } else if (assessment_Start_Date_Va.isEqual(course_Start_Date_Va) && assessment_End_Date_Va.isBefore(c_EndDate_va)) {
            return true;
        } else if (assessment_End_Date_Va.isEqual(c_EndDate_va) && assessment_Start_Date_Va.isAfter(course_Start_Date_Va)) {
            return true;
        } else if (assessment_Start_Date_Va.isEqual(course_Start_Date_Va) && assessment_End_Date_Va.isEqual(course_Start_Date_Va)) {
            return true;
        } else if (assessment_Start_Date_Va.isEqual(c_EndDate_va) && assessment_End_Date_Va.isEqual(c_EndDate_va)) {
            return true;
        }
        return false;
    }
}
