package com.example.c196_performanceassessment_thaisribeiro.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196_performanceassessment_thaisribeiro.entities.CourseNote;

import java.util.List;

/**
 * Data Access Object (DAO) interface for managing `CourseNote` entities in the database.
 */
@Dao
public interface CourseNoteDAO {

    /**
     * Inserts a `CourseNote` into the database.
     * If the `CourseNote` already exists, the operation will be ignored.
     *
     * @param courseNote The `CourseNote` entity to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CourseNote courseNote);

    /**
     * Updates an existing `CourseNote` in the database.
     *
     * @param courseNote The `CourseNote` entity to be updated.
     */
    @Update
    void update(CourseNote courseNote);

    /**
     * Deletes a `CourseNote` from the database.
     *
     * @param courseNote The `CourseNote` entity to be deleted.
     */
    @Delete
    void delete(CourseNote courseNote);

    /**
     * Retrieves all `CourseNote` entities from the database,
     * ordered by their `courseNoteID` in ascending order.
     *
     * @return A list of all `CourseNote` entities in the database.
     */
    @Query("SELECT * FROM COURSE_NOTES ORDER BY courseNoteID ASC")
    List<CourseNote> getAllCourseNotes();
}
