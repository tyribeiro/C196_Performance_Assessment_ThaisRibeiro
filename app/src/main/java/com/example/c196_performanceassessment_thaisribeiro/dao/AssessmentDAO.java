package com.example.c196_performanceassessment_thaisribeiro.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196_performanceassessment_thaisribeiro.entities.Assessment;

import java.util.List;

/**
 * Data Access Object (DAO) interface for managing `Assessment` entities in the database.
 */
@Dao
public interface AssessmentDAO {

    /**
     * Inserts an `Assessment` into the database.
     * If the `Assessment` already exists, the operation will be ignored.
     *
     * @param assessment The `Assessment` entity to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assessment assessment);

    /**
     * Updates an existing `Assessment` in the database.
     *
     * @param assessment The `Assessment` entity to be updated.
     */
    @Update
    void update(Assessment assessment);

    /**
     * Deletes an `Assessment` from the database.
     *
     * @param assessment The `Assessment` entity to be deleted.
     */
    @Delete
    void delete(Assessment assessment);

    /**
     * Retrieves all `Assessment` entities from the database,
     * ordered by their `assessmentID` in ascending order.
     *
     * @return A list of all `Assessment` entities in the database.
     */
    @Query("SELECT * FROM ASSESSMENTS ORDER BY assessmentID ASC")
    List<Assessment> getAllAssessments();
}
