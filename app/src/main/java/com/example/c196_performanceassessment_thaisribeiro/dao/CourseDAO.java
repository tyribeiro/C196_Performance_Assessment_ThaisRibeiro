package com.example.c196_performanceassessment_thaisribeiro.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196_performanceassessment_thaisribeiro.entities.Course;

import java.util.List;

/**
 * Data Access Object (DAO) interface for managing `Course` entities in the database.
 */
@Dao
public interface CourseDAO {

    /**
     * Inserts a `Course` into the database.
     * If the `Course` already exists, the operation will be ignored.
     *
     * @param course The `Course` entity to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course course);

    /**
     * Updates an existing `Course` in the database.
     *
     * @param course The `Course` entity to be updated.
     */
    @Update
    void update(Course course);

    /**
     * Deletes a `Course` from the database.
     *
     * @param course The `Course` entity to be deleted.
     */
    @Delete
    void delete(Course course);

    /**
     * Retrieves all `Course` entities from the database,
     * ordered by their `courseID` in ascending order.
     *
     * @return A list of all `Course` entities in the database.
     */
    @Query("SELECT * FROM COURSES ORDER BY courseID ASC")
    List<Course> getAllCourses();
}
