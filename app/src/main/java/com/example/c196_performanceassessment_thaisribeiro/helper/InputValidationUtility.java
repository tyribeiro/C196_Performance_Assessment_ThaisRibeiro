package com.example.c196_performanceassessment_thaisribeiro.helper;

import android.widget.EditText;
import android.widget.Spinner;

/**
 * Utility class for input validation.
 * This class provides methods to check whether input fields, such as EditText and Spinner, are empty.
 */
public class InputValidationUtility {

    /**
     * Checks whether the given EditText field is empty.
     *
     * @param edit_Text_Va The EditText field to check.
     * @return true if the EditText field is empty, false otherwise.
     */
    public static boolean isI_FieldEmpty_M(EditText edit_Text_Va) {
        if(edit_Text_Va.getText().toString().length() == 0) {
            return true;
        }
        return false;
    }
    /**
     * Checks whether the given Spinner has a selected item.
     *
     * @param spinner_Va The Spinner to check.
     * @return true if the Spinner has no selected item, false otherwise.
     */
    public static boolean isI_FieldEmpty_M(Spinner spinner_Va) {
        if(spinner_Va.getSelectedItem() != null) {
            return false;
        }
        return true;
    }
}
