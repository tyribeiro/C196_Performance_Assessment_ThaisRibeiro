package com.example.c196_performanceassessment_thaisribeiro.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.c196_performanceassessment_thaisribeiro.dao.AssessmentDAO;
import com.example.c196_performanceassessment_thaisribeiro.dao.CourseDAO;
import com.example.c196_performanceassessment_thaisribeiro.dao.CourseInstructorDAO;
import com.example.c196_performanceassessment_thaisribeiro.dao.CourseNoteDAO;
import com.example.c196_performanceassessment_thaisribeiro.dao.TermDAO;
import com.example.c196_performanceassessment_thaisribeiro.entities.Assessment;
import com.example.c196_performanceassessment_thaisribeiro.entities.Course;
import com.example.c196_performanceassessment_thaisribeiro.entities.CourseInstructor;
import com.example.c196_performanceassessment_thaisribeiro.entities.CourseNote;
import com.example.c196_performanceassessment_thaisribeiro.entities.Term;

/**
 * DatabaseBuilder is an abstract class that extends RoomDatabase.
 * It provides an instance of the Room database for the application,
 * including DAOs (Data Access Objects) for interacting with the database tables.
 */
@Database(entities = {Assessment.class, Course.class, CourseInstructor.class, CourseNote.class, Term.class}, version = 15, exportSchema = false)
public abstract class DatabaseBuilder extends RoomDatabase {

    /**
     * Gets the DAO for interacting with the Assessment table.
     *
     * @return An instance of AssessmentDAO.
     */
    public abstract AssessmentDAO assessmentDAO();

    /**
     * Gets the DAO for interacting with the Course table.
     *
     * @return An instance of CourseDAO.
     */
    public abstract CourseDAO courseDAO();

    /**
     * Gets the DAO for interacting with the CourseInstructor table.
     *
     * @return An instance of CourseInstructorDAO.
     */
    public abstract CourseInstructorDAO courseInstructorDAO();

    /**
     * Gets the DAO for interacting with the CourseNote table.
     *
     * @return An instance of CourseNoteDAO.
     */
    public abstract CourseNoteDAO courseNoteDAO();

    /**
     * Gets the DAO for interacting with the Term table.
     *
     * @return An instance of TermDAO.
     */
    public abstract TermDAO termDAO();

    // Singleton instance of the database
    private static volatile DatabaseBuilder INSTANCE;

    /**
     * Provides a singleton instance of the Room database.
     * If the instance is null, it creates a new database instance.
     *
     * @param context The application context.
     * @return The singleton instance of DatabaseBuilder.
     */
    static DatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    DatabaseBuilder.class, "MyDatabaseBuilder.db")
                            .fallbackToDestructiveMigration()  // Handles migrations by recreating the database
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
