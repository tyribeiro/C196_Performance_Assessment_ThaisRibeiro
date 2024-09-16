package com.example.c196_performanceassessment_thaisribeiro.helper;

import androidx.annotation.NonNull;

import com.example.c196_performanceassessment_thaisribeiro.entities.Term;

import java.time.LocalDate;
import java.util.ArrayList;
/**
 * Utility class for operations related to Terms in the database.
 */
public class TermUtility {
    /**
     * Checks if a term name already exists in the database, excluding the term being checked.
     *
     * @param databaseListOfTerms_Va A list of terms from the database.
     * @param term_Va                The term to check against.
     * @return true if a term with the same name exists in the database, otherwise false.
     */
    public static boolean doesTermNameExistInDb_M(@NonNull ArrayList<Term> databaseListOfTerms_Va, Term term_Va) {
        if (databaseListOfTerms_Va.size() == 0) {
            return false;
        }

        for (Term termInDatabase : databaseListOfTerms_Va) {
            if (termInDatabase.getTermID() != term_Va.getTermID() && term_Va.getTitle().equals(termInDatabase.getTitle())) {
                return true;

            }
        }
        return false;
    }

    /**
     * Retrieves a term from the database list by its ID.
     *
     * @param db_Term_List_Va The list of terms from the database.
     * @param term_Id_Va       The ID of the term to retrieve.
     * @return The term with the specified ID, or null if not found.
     */
    public static Term retrieveTerm_From_Db_By_Term_ID_M(ArrayList<Term> db_Term_List_Va, int term_Id_Va) {
        if (db_Term_List_Va.size() == 0) {
            return null;
        }

        for (Term term : db_Term_List_Va) {
            if (term.getTermID() == term_Id_Va) {
                return term;
            }
        }
        return null;
    }
    /**
     * Checks if the date range of a term overlaps with any term in the database.
     *
     * @param dbTermList_Va The list of terms from the database.
     * @param term_Va       The term to check against.
     * @return true if the term's date range overlaps with any term in the database, otherwise false.
     */
    public static boolean doesTermDateOverlapWithTermInDb_M(@NonNull ArrayList<Term> dbTermList_Va, Term term_Va) {
        if (dbTermList_Va.size() == 0) {
            return false;
        }

        LocalDate start_Date_Va = LocalDate.parse(term_Va.getStartDate());
        LocalDate end_Date_Va = LocalDate.parse(term_Va.getEndDate());

        int num_OfOverlappingDates_Va = 0;

        for (Term termInDatabase : dbTermList_Va) {
            if (termInDatabase.getTermID() == term_Va.getTermID()) {
                num_OfOverlappingDates_Va--;
                continue;
            }

            LocalDate db_Start_Date_Va = LocalDate.parse(termInDatabase.getStartDate());
            LocalDate db_End_Date_Va = LocalDate.parse(termInDatabase.getEndDate());

            if (end_Date_Va.isEqual(start_Date_Va)) {
                return false;
            } else if (end_Date_Va.isBefore(db_Start_Date_Va)) {
                return false;
            } else if(start_Date_Va.isEqual(db_End_Date_Va)) {
                return false;
            } else if (start_Date_Va.isAfter(db_End_Date_Va)) {
                return false;
            }
            num_OfOverlappingDates_Va++;
        }

        if (num_OfOverlappingDates_Va <= 0 ) {
            return false;
        }
        return true;
    }
}
