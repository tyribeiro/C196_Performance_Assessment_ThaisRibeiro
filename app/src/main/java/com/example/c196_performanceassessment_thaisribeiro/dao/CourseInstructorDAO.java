package com.example.c196_performanceassessment_thaisribeiro.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196_performanceassessment_thaisribeiro.entities.CourseInstructor;

import java.util.List;

/**
 * Data Access Object (DAO) interface for managing `CourseInstructor` entities in the database.
 */
@Dao
public interface CourseInstructorDAO {

    /**
     * Inserts a `CourseInstructor` into the database.
     * If the `CourseInstructor` already exists, the operation will be ignored.
     *
     * @param courseInstructor The `CourseInstructor` entity to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CourseInstructor courseInstructor);

    /**
     * Updates an existing `CourseInstructor` in the database.
     *
     * @param courseInstructor The `CourseInstructor` entity to be updated.
     */
    @Update
    void update(CourseInstructor courseInstructor);

    /**
     * Deletes a `CourseInstructor` from the database.
     *
     * @param courseInstructor The `CourseInstructor` entity to be deleted.
     */
    @Delete
    void delete(CourseInstructor courseInstructor);

    /**
     * Retrieves all `CourseInstructor` entities from the database,
     * ordered by their `instructorID` in ascending order.
     *
     * @return A list of all `CourseInstructor` entities in the database.
     */
    @Query("SELECT * FROM COURSE_INSTRUCTORS ORDER BY instructorID ASC")
    List<CourseInstructor> getAllCourseInstructors();
}
