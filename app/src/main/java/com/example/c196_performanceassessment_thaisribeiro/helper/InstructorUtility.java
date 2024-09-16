package com.example.c196_performanceassessment_thaisribeiro.helper;

import com.example.c196_performanceassessment_thaisribeiro.entities.Course;
import com.example.c196_performanceassessment_thaisribeiro.entities.CourseInstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for handling operations related to course instructors.
 * This class provides methods to retrieve instructors from the database,
 * check for instructor existence, and determine if an instructor is associated with any courses.
 */
public class InstructorUtility {
    /**
     * Retrieves a course instructor from the database by their ID.
     *
     * @param dbInstructorList The list of instructors in the database.
     * @param instructorId The ID of the instructor to retrieve.
     * @return The {@link CourseInstructor} object if found, or null if not found.
     */
    public static CourseInstructor retrieveC_FromDatabaseByInstructorID_M(ArrayList<CourseInstructor> dbInstructorList, int instructorId) {
        if (dbInstructorList.size() == 0) {
            return null;
        }

        for (CourseInstructor dbInstructor : dbInstructorList) {
            if (dbInstructor.getInstructorID() == instructorId) {
                return dbInstructor;
            }
        }
        return null;
    }
    /**
     * Checks whether a course instructor exists in the database.
     * This method compares the name, phone number, and email of the given instructor
     * against the list of instructors in the database.
     *
     * @param addCourseInstructor The instructor to check.
     * @param dbCourseInstructorList The list of instructors in the database.
     * @return true if the instructor exists in the database, false otherwise.
     */
    public static boolean doesC_InstructorExistInDatabase_M(CourseInstructor addCourseInstructor, List<CourseInstructor> dbCourseInstructorList) {
        if (dbCourseInstructorList.size() == 0) {
            return false;
        }

        for(CourseInstructor dbCourseInstructor : dbCourseInstructorList) {
            if (dbCourseInstructor.getInstructorID() == addCourseInstructor.getInstructorID()) {
                continue;
            }

            if(addCourseInstructor.getName().toLowerCase().equals(dbCourseInstructor.getName().toLowerCase()) &&
                    addCourseInstructor.getPhoneNumber().equals(dbCourseInstructor.getPhoneNumber()) &&
                    addCourseInstructor.getEmail().equals(dbCourseInstructor.getEmail())) {
                return true;
            }
        }
        return false;
    }
    /**
     * Determines whether a given instructor is associated with any courses.
     *
     * @param instructor_Id_Va The ID of the instructor.
     * @param db_Course_List_Va The list of courses in the database.
     * @return true if the instructor is associated with any courses, false otherwise.
     */
    public static boolean doesInstructorHaveC_M(int instructor_Id_Va, ArrayList<Course> db_Course_List_Va) {
        if (db_Course_List_Va.size() == 0) {
            return false;
        }

        for (Course dbCourse : db_Course_List_Va) {
            if (dbCourse.getInstructorID() == instructor_Id_Va) {
                return true;
            }
        }
        return false;
    }
}
