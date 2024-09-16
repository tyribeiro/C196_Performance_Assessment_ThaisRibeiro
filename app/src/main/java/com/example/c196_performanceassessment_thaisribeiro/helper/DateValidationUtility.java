package com.example.c196_performanceassessment_thaisribeiro.helper;

import java.time.LocalDate;

public class DateValidationUtility {

    /**
     * Checks if the given date string is formatted correctly.
     *
     * @param dateS_Va the date string in yyyy-mm-dd format
     * @return true if the date string is formatted correctly, false otherwise
     */
    public static boolean isD_FormattedCorrect_M(String dateS_Va) {
        if (!doesD_ContainDashes_M(dateS_Va)) {
            return false;
        } else if (!isD_MonthCorrectRange_M(dateS_Va)) {
            return false;
        } else if (!isD_DayCorrectRange_M(dateS_Va)) {
            return false;
        }
        return true;
    }
    /**
     * Checks if the given date string has the correct length.
     *
     * @param dateS_Va the date string to check
     * @return true if the date string length matches the expected length, false otherwise
     */
    public static boolean isDCorrectLength_M(String dateS_Va) {
        if (dateS_Va.length() > com.example.c196_performanceassessment_thaisribeiro.helper.DateFormatUtility.longD_Format_Va.length() || dateS_Va.length() < com.example.c196_performanceassessment_thaisribeiro.helper.DateFormatUtility.longD_Format_Va.length()) {
            return false;
        }
        return true;
    }
    /**
     * Checks if the month part of the given date string is within the valid range (1-12).
     *
     * @param dateS_Va the date string in yyyy-mm-dd format
     * @return true if the month is within the valid range, false otherwise
     */
    private static boolean isD_MonthCorrectRange_M(String dateS_Va) {
        final int MONTH_JANUARY_INT_Va = 1;
        final int MONTH_DECEMBER_INT_VA = 12;
        int date_Month_Va = Integer.parseInt(dateS_Va.substring(5, 7));

        return date_Month_Va >= MONTH_JANUARY_INT_Va && date_Month_Va <= MONTH_DECEMBER_INT_VA;
    }
    /**
     * Checks if the day part of the given date string is within the valid range (1-31).
     *
     * @param dateS_Va the date string in yyyy-mm-dd format
     * @return true if the day is within the valid range, false otherwise
     */
    private static boolean isD_DayCorrectRange_M(String dateS_Va) {
        final int MINIMUM_DAY_INT_VA = 1;
        final int MAXIMUM_DAY_INT_VA = 31;
        int dateDay_Va = Integer.parseInt(dateS_Va.substring(8, 10));

        return dateDay_Va >= MINIMUM_DAY_INT_VA && dateDay_Va <= MAXIMUM_DAY_INT_VA;
    }
    /**
     * Checks if the given date string contains dashes at the correct positions.
     *
     * @param dateS_Va the date string in yyyy-mm-dd format
     * @return true if the date string contains dashes at the correct positions, false otherwise
     */
    private static boolean doesD_ContainDashes_M(String dateS_Va) {
        final String dash_Va = "-";
        String d_DashOne_Va = String.valueOf(dateS_Va.toCharArray()[4]);
        String d_DashTwo_Va = String.valueOf(dateS_Va.toCharArray()[7]);

        return d_DashOne_Va.equals(dash_Va) && d_DashTwo_Va.equals(dash_Va);
    }
    /**
     * Checks if the start date is before the end date.
     *
     * @param startDateS_Va the start date string in yyyy-mm-dd format
     * @param endDateS_va the end date string in yyyy-mm-dd format
     * @return true if the start date is before the end date, false otherwise
     */
    public static boolean isStartD_BeforeEndDate_M(String startDateS_Va, String endDateS_va) {
        LocalDate start_Date_Va = LocalDate.parse(startDateS_Va);
        LocalDate end_Date_Va = LocalDate.parse(endDateS_va);

        return start_Date_Va.isBefore(end_Date_Va);
    }

    /**
     * Checks if the start date is the same as or before the end date.
     *
     * @param startDateS_Va the start date string in yyyy-mm-dd format
     * @param endDateS_Va the end date string in yyyy-mm-dd format
     * @return true if the start date is the same as or before the end date, false otherwise
     */
    public static boolean isS_DateTheSameOrBeforeEndDate_M(String startDateS_Va, String endDateS_Va) {
        LocalDate start_Date_Va = LocalDate.parse(startDateS_Va);
        LocalDate end_Date_Va = LocalDate.parse(endDateS_Va);

        if (start_Date_Va.isEqual(end_Date_Va)) {
            return true;
        } else return start_Date_Va.isBefore(end_Date_Va);
    }
    /**
     * Checks if the given string consists only of numeric characters.
     *
     * @param subS_Va the string to check
     * @return true if the string consists only of numeric characters, false otherwise
     */
    private static boolean isNumber_M(String subS_Va) {
        char[] char_Array_va = subS_Va.toCharArray();

        try {
            for (char element : char_Array_va) {
                int parsed_Num_Va = Integer.parseInt(String.valueOf(element));
            }

        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    /**
     * Checks if the year, month, and day parts of the given date string are numeric.
     *
     * @param dateStr the date string in yyyy-mm-dd format
     * @return true if the year, month, and day are all numeric, false otherwise
     */
    public static boolean isD_ANumber_M(String dateStr) {
        String dateYear_Va = dateStr.substring(0, 4);
        String dateMonth_Va = dateStr.substring(5, 7);
        String dateDay_Va = dateStr.substring(8, 10);

        if (!isNumber_M(dateYear_Va)) {
            return false;
        } else if (!isNumber_M(dateMonth_Va)) {
            return false;
        } else return isNumber_M(dateDay_Va);
    }
}
