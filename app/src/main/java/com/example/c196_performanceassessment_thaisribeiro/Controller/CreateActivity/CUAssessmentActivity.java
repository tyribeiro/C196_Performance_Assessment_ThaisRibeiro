package com.example.c196_performanceassessment_thaisribeiro.Controller.CreateActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.c196_performanceassessment_thaisribeiro.R;
import com.example.c196_performanceassessment_thaisribeiro.database.Repository;
import com.example.c196_performanceassessment_thaisribeiro.entities.Assessment;
import com.example.c196_performanceassessment_thaisribeiro.entities.Course;
import com.example.c196_performanceassessment_thaisribeiro.helper.AssessmentUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.DateValidationUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.DialogMessagesUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.InputValidationUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.SwitchScreenUtility;

import java.util.ArrayList;
/**
 * {@code CUAssessmentActivity} is an Android activity responsible for creating and updating assessments.
 * It allows users to input assessment details such as name, information, type, and date ranges. The activity
 * interfaces with the database to save or update assessment records.
 *
 * <p>The class handles the following functionalities:
 * <ul>
 *     <li>Displays a form for entering or updating assessment details.</li>
 *     <li>Validates user input to ensure that required fields are filled and dates are correctly formatted.</li>
 *     <li>Checks that assessment dates fall within the course date range.</li>
 *     <li>Uses the repository to interact with the database and perform CRUD operations.</li>
 *     <li>Provides navigation options to cancel or save the assessment details.</li>
 * </ul>
 *
 * <p>When saving an assessment, the class distinguishes between adding a new assessment or updating an existing one
 * based on the value of the {@code add_Or_Update_Va} field. It also handles date validation to ensure that the
 * start date is not after the end date and that the dates are formatted correctly.
 *
 * <p>Instance variables include:
 * <ul>
 *     <li>{@code repository_Va}: An instance of the {@code Repository} class for database operations.</li>
 *     <li>{@code course_Id_Va}: The ID of the course associated with the assessment.</li>
 *     <li>{@code assessment_Id_Va}: The ID of the assessment being updated (if applicable).</li>
 *     <li>{@code assessment_Name_Va}, {@code assessment_Info_Va}, {@code start_Date_Va}, {@code end_Date_Va}:
 *         UI components for entering assessment details.</li>
 *     <li>{@code assessment_Type_Va}: A spinner for selecting the type of assessment.</li>
 *     <li>{@code save_Btn_Va}, {@code cancel_Btn_Va}: Buttons for saving or canceling the assessment operation.</li>
 *     <li>{@code add_Or_Update_Va}: A string indicating whether the activity is in add or update mode.</li>
 *     <li>{@code db_Assessment_List_Va}, {@code db_Course_List_Va}: Lists of assessments and courses from the database.</li>
 *     <li>{@code assessment_Va}: The assessment object being manipulated.</li>
 *     <li>{@code builder_Va}: An {@code AlertDialog.Builder} for creating confirmation dialogs.</li>
 * </ul>
 */

public class CUAssessmentActivity extends AppCompatActivity {
    Repository repository_Va;
    int course_Id_Va;
    int assessment_Id_Va;
    EditText assessment_Name_Va;
    EditText assessment_Info_Va;
    EditText start_Date_Va;
    EditText end_Date_Va;
    Spinner assessment_Type_Va;
    Button save_Btn_Va;
    Button cancel_Btn_Va;
    String add_Or_Update_Va;
    ArrayList<Assessment> db_Assessment_List_Va;
    ArrayList<Course> db_Course_List_Va;
    Assessment assessment_Va;
    AlertDialog.Builder builder_Va;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_update_as_sc);

        repository_Va = new Repository(getApplication());
        builder_Va = new AlertDialog.Builder(this);

        try {
            set_Db_Lists_M();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Intent intent_Va = getIntent();
        String activity_Came_From_Va = intent_Va.getStringExtra(SwitchScreenUtility.CAME_FROM_KEY_Va);

        add_Or_Update_Va = intent_Va.getStringExtra(SwitchScreenUtility.ADD_OR_UPDATE_SCREEN_KEY_Va);

        assessment_Name_Va = findViewById(R.id.create_assessment_name);
        assessment_Info_Va = findViewById(R.id.create_assessment_info);
        start_Date_Va = findViewById(R.id.create_assessment_start_date);
        end_Date_Va = findViewById(R.id.create_assessment_end_date);
        assessment_Type_Va = findViewById(R.id.create_assessment_type);
        save_Btn_Va = findViewById(R.id.create_assessment_save_btn);
        cancel_Btn_Va = findViewById(R.id.create_assessment_cancel_btn);

        setAssessment_Id_Va(intent_Va);
        setAssessment();
        setCourse_Id_Va(intent_Va);

        assessment_Type_Va.setAdapter(createAssessmentTypeListAdapter());

        setTitle(add_Or_Update_Va);
        set_Screen_Details_M();


        cancel_Btn_Va.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder_Va.setTitle(DialogMessagesUtility.CONFIRMATION_Va)
                        .setMessage(DialogMessagesUtility.CANCEL_CONFORMATION_MESSAGE_Va)
                        .setCancelable(true)
                        .setPositiveButton(DialogMessagesUtility.YES_Va, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (add_Or_Update_Va.equals(SwitchScreenUtility.ADD_ASSESSMENT_VAL_Va)) {
                                    switch_Screens(SwitchScreenUtility.getActClass_M(activity_Came_From_Va), SwitchScreenUtility.COURSE_ID_KEY_Va, String.valueOf(course_Id_Va));
                                } else {
                                    switch_Screens(SwitchScreenUtility.getActClass_M(activity_Came_From_Va), SwitchScreenUtility.ASSESSMENT_ID_KEY_Va, String.valueOf(assessment_Id_Va));
                                }
                            }
                        })
                        .setNegativeButton(DialogMessagesUtility.NO_Va, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();
            }
        });


        save_Btn_Va.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (InputValidationUtility.isI_FieldEmpty_M(assessment_Name_Va) || InputValidationUtility.isI_FieldEmpty_M(assessment_Info_Va) ||
                        InputValidationUtility.isI_FieldEmpty_M(start_Date_Va) || InputValidationUtility.isI_FieldEmpty_M(end_Date_Va) ||
                        InputValidationUtility.isI_FieldEmpty_M(assessment_Type_Va)) {
                    Toast.makeText(CUAssessmentActivity.this, DialogMessagesUtility.EMPTY_INPUT_FIELDS_Va, Toast.LENGTH_SHORT).show();
                    return;
                } else if (!DateValidationUtility.isDCorrectLength_M(start_Date_Va.getText().toString()) || !DateValidationUtility.isDCorrectLength_M(end_Date_Va.getText().toString())) {
                    Toast.makeText(CUAssessmentActivity.this, DialogMessagesUtility.DATE_IS_INCORRECT_LEN_Va, Toast.LENGTH_SHORT).show();
                    return;
                } else if (!DateValidationUtility.isD_ANumber_M(start_Date_Va.getText().toString()) || !DateValidationUtility.isD_ANumber_M(end_Date_Va.getText().toString())) {

                    Toast.makeText(CUAssessmentActivity.this, DialogMessagesUtility.INVALID_INPUT_FOR_DATE_VA, Toast.LENGTH_SHORT).show();
                    return;
                } else if (!DateValidationUtility.isD_FormattedCorrect_M(start_Date_Va.getText().toString()) || !DateValidationUtility.isD_FormattedCorrect_M(end_Date_Va.getText().toString())) {

                    Toast.makeText(CUAssessmentActivity.this, DialogMessagesUtility.DATE_IS_FORMATTED_INCORRECTLY_VA, Toast.LENGTH_SHORT).show();
                    return;
                }  else if (!DateValidationUtility.isS_DateTheSameOrBeforeEndDate_M(start_Date_Va.getText().toString(), end_Date_Va.getText().toString())) {

                    Toast.makeText(CUAssessmentActivity.this, DialogMessagesUtility.START_DATE_IS_AFTER_END_DATE_Va, Toast.LENGTH_SHORT).show();
                    return;
                }

                Assessment save_Assessment_Va = create_Assessment_M();

                if (!AssessmentUtility.areADatesWithinRangeOfCourseDates_M(save_Assessment_Va, course_Id_Va, db_Course_List_Va)) {
                    Toast.makeText(CUAssessmentActivity.this, DialogMessagesUtility.ASSESSMENT_DATES_NOT_IN_RANGE_OF_COURSE_DA_Va, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (add_Or_Update_Va.equals(SwitchScreenUtility.ADD_ASSESSMENT_VAL_Va)) {
                    try {
                        repository_Va.insert(save_Assessment_Va);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    Toast.makeText(CUAssessmentActivity.this, "Created " + save_Assessment_Va.getTitle(), Toast.LENGTH_SHORT).show();
                    switch_Screens(SwitchScreenUtility.getActClass_M(activity_Came_From_Va), SwitchScreenUtility.COURSE_ID_KEY_Va, String.valueOf(course_Id_Va));
                } else {
                    try {
                        repository_Va.update(save_Assessment_Va);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    Toast.makeText(CUAssessmentActivity.this, "Updated " + save_Assessment_Va.getTitle(), Toast.LENGTH_SHORT).show();
                    switch_Screens(SwitchScreenUtility.getActClass_M(activity_Came_From_Va), SwitchScreenUtility.ASSESSMENT_ID_KEY_Va, String.valueOf(assessment_Id_Va));
                }
            }
        });
    }

    Assessment create_Assessment_M() {
        if (add_Or_Update_Va.equals(SwitchScreenUtility.ADD_ASSESSMENT_VAL_Va)) {
            return new Assessment(assessment_Name_Va.getText().toString().trim(), assessment_Type_Va.getSelectedItem().toString(),
                    assessment_Info_Va.getText().toString().trim(), start_Date_Va.getText().toString(), end_Date_Va.getText().toString(), course_Id_Va);
        }
        assessment_Va.updateInputFields_M(assessment_Name_Va, assessment_Info_Va, assessment_Type_Va, start_Date_Va, end_Date_Va);

        return assessment_Va;
    }

    void set_Db_Lists_M() throws InterruptedException {
        db_Assessment_List_Va = (ArrayList<Assessment>) repository_Va.getM_All_Assessments_Va();
        db_Course_List_Va = (ArrayList<Course>) repository_Va.getM_All_Courses_Va();
    }

    void setCourse_Id_Va(Intent intent) {
        if (add_Or_Update_Va.equals(SwitchScreenUtility.ADD_ASSESSMENT_VAL_Va)) {
            course_Id_Va = Integer.valueOf(intent.getStringExtra(SwitchScreenUtility.COURSE_ID_KEY_Va));
        } else {
            course_Id_Va = assessment_Va.getCourseID();
        }
    }

    void setAssessment_Id_Va(Intent intent) {
        if (add_Or_Update_Va.equals(SwitchScreenUtility.ADD_ASSESSMENT_VAL_Va)) {
            return;
        }
        assessment_Id_Va = Integer.valueOf(intent.getStringExtra(SwitchScreenUtility.ASSESSMENT_ID_KEY_Va));
    }

    void setAssessment() {
        assessment_Va = AssessmentUtility.re_AssessmentFromDatabaseByAssessmentID_M(db_Assessment_List_Va, assessment_Id_Va);
    }

    void switch_Screens(Class class_Name_Va, String id_Key_Va, String id_Value_Va) {
        Intent intent_Va = new Intent(this, class_Name_Va);
        intent_Va.putExtra(id_Key_Va, id_Value_Va);
        startActivity(intent_Va);
    }

    ArrayAdapter<String> createAssessmentTypeListAdapter() {
        ArrayList<String> status_Options_List_Va = new ArrayList<>();

        status_Options_List_Va.add("Performance");
        status_Options_List_Va.add("Objective");

        return new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, status_Options_List_Va);
    }

    int getSpinnerAssessmentTypePosition(String status) {

        switch (status) {
            case "Performance":
                return 0;
            case "Objective":
                return 1;
        }
        return -1;
    }

    void set_Screen_Details_M() {
        if (add_Or_Update_Va.equals(SwitchScreenUtility.ADD_ASSESSMENT_VAL_Va)) {
            return;
        }
        assessment_Name_Va.setText(assessment_Va.getTitle());
        assessment_Info_Va.setText(assessment_Va.getInformation());
        assessment_Type_Va.setSelection(getSpinnerAssessmentTypePosition(assessment_Va.getType()));
        start_Date_Va.setText(assessment_Va.getStartDate());
        end_Date_Va.setText(assessment_Va.getEndDate());
    }
}