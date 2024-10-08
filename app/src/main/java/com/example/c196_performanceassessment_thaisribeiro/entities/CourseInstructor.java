package com.example.c196_performanceassessment_thaisribeiro.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Represents an instructor associated with a course in the application.
 * This entity is stored in the 'course_instructors' table in the database.
 */
@Entity(tableName = "course_instructors")
public class CourseInstructor {
    /** The unique ID of the instructor, auto-generated by the database. */
    @PrimaryKey(autoGenerate = true)
    private int instructorID;

    /** The name of the instructor. */
    private String name;

    /** The phone number of the instructor. */
    private String phoneNumber;

    /** The email address of the instructor. */
    private String email;
    /**
     * Constructs a new CourseInstructor with the specified ID, name, phone number, and email.
     *
     * @param instructorID the unique ID of the instructor
     * @param name the name of the instructor
     * @param phoneNumber the phone number of the instructor
     * @param email the email address of the instructor
     */
    public CourseInstructor(int instructorID, String name, String phoneNumber, String email) {
        this.setInstructorID(instructorID);
        this.setName(name);
        this.setPhoneNumber(phoneNumber);
        this.setEmail(email);
    }
    /**
     * Constructs a new CourseInstructor with the specified name, phone number, and email.
     * This constructor is ignored by Room database.
     *
     * @param name the name of the instructor
     * @param phoneNumber the phone number of the instructor
     * @param email the email address of the instructor
     */
    @Ignore
    public CourseInstructor(String name, String phoneNumber, String email) {
        this.setName(name);
        this.setPhoneNumber(phoneNumber);
        this.setEmail(email);
    }

    /**
     * Sets the unique ID of the instructor.
     *
     * @param instructorID the unique ID of the instructor
     */
    public void setInstructorID(int instructorID) {
        this.instructorID = instructorID;
    }

    /**
     * Sets the name of the instructor.
     *
     * @param name the name of the instructor
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Sets the phone number of the instructor.
     *
     * @param phoneNumber the phone number of the instructor
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    /**
     * Sets the email address of the instructor.
     *
     * @param email the email address of the instructor
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Returns the unique ID of the instructor.
     *
     * @return the unique ID of the instructor
     */
    public int getInstructorID() {
        return this.instructorID;
    }
    /**
     * Returns the name of the instructor.
     *
     * @return the name of the instructor
     */
    public String getName() {
        return this.name;
    }
    /**
     * Returns the phone number of the instructor.
     *
     * @return the phone number of the instructor
     */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    /**
     * Returns the email address of the instructor.
     *
     * @return the email address of the instructor
     */
    public String getEmail() {
        return this.email;
    }
    /**
     * Returns a string representation of the instructor, which is the instructor's name.
     *
     * @return the name of the instructor
     */
    @Override
    public String toString() {
        return this.name;
    }
}