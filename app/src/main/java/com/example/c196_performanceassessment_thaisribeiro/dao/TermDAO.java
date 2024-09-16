package com.example.c196_performanceassessment_thaisribeiro.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196_performanceassessment_thaisribeiro.entities.Term;

import java.util.List;

/**
 * Data Access Object (DAO) interface for managing `Term` entities in the database.
 */
@Dao
public interface TermDAO {

    /**
     * Inserts a `Term` into the database.
     * If the `Term` already exists, the operation will be ignored.
     *
     * @param term The `Term` entity to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Term term);

    /**
     * Updates an existing `Term` in the database.
     *
     * @param term The `Term` entity to be updated.
     */
    @Update
    void update(Term term);

    /**
     * Deletes a `Term` from the database.
     *
     * @param term The `Term` entity to be deleted.
     */
    @Delete
    void delete(Term term);

    /**
     * Retrieves all `Term` entities from the database,
     * ordered by their `termID` in ascending order.
     *
     * @return A list of all `Term` entities in the database.
     */
    @Query("SELECT * FROM TERMS ORDER BY termID ASC")
    List<Term> getAllTerms();
}
