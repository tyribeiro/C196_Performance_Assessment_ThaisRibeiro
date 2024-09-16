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

import com.example.c196_performanceassessment_thaisribeiro.Controller.ListActivity.LOfInstructorsActivity;
import com.example.c196_performanceassessment_thaisribeiro.R;
import com.example.c196_performanceassessment_thaisribeiro.database.Repository;
import com.example.c196_performanceassessment_thaisribeiro.entities.Course;
import com.example.c196_performanceassessment_thaisribeiro.entities.CourseInstructor;
import com.example.c196_performanceassessment_thaisribeiro.entities.Term;
import com.example.c196_performanceassessment_thaisribeiro.helper.CourseUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.DateValidationUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.DialogMessagesUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.InputValidationUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.StatusSpinnerUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.SwitchScreenUtility;
import com.example.c196_performanceassessment_thaisribeiro.Controller.CreateActivity.CUInstructorActivity;

import java.util.ArrayList;
/**
 * This activity is responsible for creating or updating a course in the application.
 * It allows users to input or modify details about a course, such as its name, status,
 * information, start date, end date, and instructor. The activity handles validation of
 * input fields and dates, and provides options to save or cancel changes.
 *
 * The activity interacts with a repository to perform database operations and displays
 * appropriate messages to the user based on their actions. It also manages navigation between
 * different screens and ensures that courses are correctly associated with terms and instructors.
 *
 * Key features include:
 * - Setting up UI components such as EditTexts, Spinners, and Buttons.
 * - Validating input data including dates and course details.
 * - Inserting or updating course records in the database.
 * - Navigating to other activities, such as the InstructorActivity.
 * - Handling user interactions with dialogs for confirmation and navigation.
 *
 * The activity uses utilities for date validation, input validation, and status handling to
 * ensure data integrity and user experience.
 *
 * <p>Note: This class assumes that necessary data, such as lists of courses, instructors, and terms,
 * are available through the Repository and relevant utility classes.</p>
 *
 * @see com.example.c196_performanceassessment_thaisribeiro.database.Repository
 * @see com.example.c196_performanceassessment_thaisribeiro.helper.DateValidationUtility
 * @see com.example.c196_performanceassessment_thaisribeiro.helper.InputValidationUtility
 * @see com.example.c196_performanceassessment_thaisribeiro.entities.Course
 * @see com.example.c196_performanceassessment_thaisribeiro.entities.CourseInstructor
 * @see com.example.c196_performanceassessment_thaisribeiro.entities.Term
 */
public class CUCourseActivity extends AppCompatActivity {
    Repository repository_Va;
    String activity_Came_From_Va;
    String activity_Came_From2_Va;
    String add_Or_Update_Va;
    int term_Id_Va;
    int course_Id_Va;
    EditText class_Name_Va;
    EditText class_Info_Va;
    Spinner class_Status_Va;
    Spinner select_Instructor_Va;
    EditText start_Date_Va;
    EditText end_Date_Va;
    Button save_Btn_Va;
    Button cancel_Btn_Va;
    Button add_CI_Btn_va;
    ArrayList<Course> db_Course_List_Va;
    ArrayList<CourseInstructor> db_Instructor_List_Va;
    ArrayList<Term> db_Term_List_Va;
    AlertDialog.Builder builder_Va;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_update_sc);

        repository_Va = new Repository(getApplication());
        builder_Va = new AlertDialog.Builder(this);

        try {
            setDatabaseListVariables();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Intent intent = getIntent();
        activity_Came_From_Va = intent.getStringExtra(SwitchScreenUtility.CAME_FROM_KEY_Va);

        setActivity_Came_From2_Va(intent);
        setAdd_Or_Update_Va(intent);

        setCourse_Id_Va(intent);
        setTermID(intent);
        setTitle(add_Or_Update_Va);

        class_Status_Va = findViewById(R.id.create_class_select_status);
        class_Name_Va = findViewById(R.id.create_class_name);
        class_Info_Va = findViewById(R.id.create_class_info);
        start_Date_Va = findViewById(R.id.create_class_start_date);
        end_Date_Va = findViewById(R.id.create_class_end_date);
        save_Btn_Va = findViewById(R.id.create_class_save_btn);
        cancel_Btn_Va = findViewById(R.id.create_class_cancel_btn);
        add_CI_Btn_va = findViewById(R.id.create_class_add_ci_btn);
        select_Instructor_Va = findViewById(R.id.create_class_select_ci);

        class_Status_Va.setAdapter(createStatusListAdapter());
        select_Instructor_Va.setAdapter(create_Instructor_List_Adapter_M());

        setSpinnerSelected_Instructor_M();
        setScreenDetails();


        save_Btn_Va.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (InputValidationUtility.isI_FieldEmpty_M(class_Name_Va) || InputValidationUtility.isI_FieldEmpty_M(class_Info_Va) ||
                        InputValidationUtility.isI_FieldEmpty_M(class_Status_Va) || InputValidationUtility.isI_FieldEmpty_M(select_Instructor_Va) ||
                        InputValidationUtility.isI_FieldEmpty_M(start_Date_Va) || InputValidationUtility.isI_FieldEmpty_M(end_Date_Va)) {
                    Toast.makeText(CUCourseActivity.this, DialogMessagesUtility.EMPTY_INPUT_FIELDS_Va, Toast.LENGTH_SHORT).show();
                    return;
                } else if (!DateValidationUtility.isDCorrectLength_M(start_Date_Va.getText().toString()) || !DateValidationUtility.isDCorrectLength_M(end_Date_Va.getText().toString())) {
                    Toast.makeText(CUCourseActivity.this, DialogMessagesUtility.DATE_IS_INCORRECT_LEN_Va, Toast.LENGTH_SHORT).show();
                    return;
                } else if (!DateValidationUtility.isD_ANumber_M(start_Date_Va.getText().toString()) || !DateValidationUtility.isD_ANumber_M(end_Date_Va.getText().toString())) {
                    Toast.makeText(CUCourseActivity.this, DialogMessagesUtility.INVALID_INPUT_FOR_DATE_VA, Toast.LENGTH_SHORT).show();
                    return;
                } else if (!DateValidationUtility.isD_FormattedCorrect_M(start_Date_Va.getText().toString()) ||
                        !DateValidationUtility.isD_FormattedCorrect_M(end_Date_Va.getText().toString())) {
                    Toast.makeText(CUCourseActivity.this, DialogMessagesUtility.DATE_IS_FORMATTED_INCORRECTLY_VA, Toast.LENGTH_SHORT).show();
                    return;
                } else if (!DateValidationUtility.isS_DateTheSameOrBeforeEndDate_M(start_Date_Va.getText().toString(), end_Date_Va.getText().toString())) {
                    Toast.makeText(CUCourseActivity.this, DialogMessagesUtility.START_DATE_IS_AFTER_END_DATE_Va, Toast.LENGTH_SHORT).show();
                    return;
                }

                Course save_Course_Va = getCourseForAddOr_Update_M();

                if (!CourseUtility.areCourse_WithinRangeOfTermDates_M(save_Course_Va, term_Id_Va, db_Term_List_Va)) {
                    Toast.makeText(CUCourseActivity.this, DialogMessagesUtility.COURSE_DATES_NOT_IN_RANGE_OF_TERM_DA_VA, Toast.LENGTH_SHORT).show();
                    return;
                } else if (CourseUtility.doesCourseExistForT_M(CourseUtility.getAllCoursesForT_M(db_Course_List_Va, term_Id_Va), save_Course_Va)) {

                    Toast.makeText(CUCourseActivity.this, DialogMessagesUtility.COURSE_ALREADY_EXISTS_FOR_T_Va, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (activity_Came_From_Va.equals(SwitchScreenUtility.CREATE_OR_UPDATE_INSTRUCTOR_ACT_Va)) {
                    activity_Came_From_Va = activity_Came_From2_Va;
                }

                if (add_Or_Update_Va.equals(SwitchScreenUtility.ADD_COURSE_VAL_Va)) {
                    try {
                        repository_Va.insert(save_Course_Va);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    Toast.makeText(CUCourseActivity.this, "Created " + save_Course_Va.getTitle(), Toast.LENGTH_SHORT).show();
                    switch_Screen_M(SwitchScreenUtility.getActClass_M(activity_Came_From_Va), SwitchScreenUtility.TERM_ID_KEY_Va, String.valueOf(term_Id_Va));

                } else {
                    try {
                        repository_Va.update(save_Course_Va);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    Toast.makeText(CUCourseActivity.this, "Updated " + save_Course_Va.getTitle(), Toast.LENGTH_SHORT).show();
                    switch_Screen_M(SwitchScreenUtility.getActClass_M(activity_Came_From_Va), SwitchScreenUtility.TERM_ID_KEY_Va,
                            String.valueOf(term_Id_Va), SwitchScreenUtility.COURSE_ID_KEY_Va, String.valueOf(course_Id_Va));
                }
            }
        });


        add_CI_Btn_va.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (activity_Came_From_Va.equals(SwitchScreenUtility.CREATE_OR_UPDATE_INSTRUCTOR_ACT_Va)) {
                    activity_Came_From_Va = activity_Came_From2_Va;
                }
                // switchs_Screen_Va(LOfInstructorsActivity.class, SwitchScreenUtility.CAME_FROM_KEY_Va, SwitchScreenUtility.CREATE_OR_UPDATE_COURSE_ACT_Va);

                switch_Screen_M(CUInstructorActivity.class, SwitchScreenUtility.CAME_FROM_KEY_Va, SwitchScreenUtility.CREATE_OR_UPDATE_COURSE_ACT_Va,
                        SwitchScreenUtility.CAME_FROM_KEY2_Va, activity_Came_From_Va, SwitchScreenUtility.ADD_OR_UPDATE_SCREEN_KEY_Va, SwitchScreenUtility.ADD_INSTRUCTOR_VAL_Va,
                        SwitchScreenUtility.CAME_FROM_ADD_OR_UPDATE_SCREEN_KEY_Va, add_Or_Update_Va, SwitchScreenUtility.TERM_ID_KEY_Va, String.valueOf(term_Id_Va),
                        SwitchScreenUtility.COURSE_ID_KEY_Va, String.valueOf(course_Id_Va));
            }

        });


        cancel_Btn_Va.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder_Va.setTitle(DialogMessagesUtility.CONFIRMATION_Va)
                        .setMessage(DialogMessagesUtility.CANCEL_CONFORMATION_MESSAGE_Va)
                        .setCancelable(true)
                        .setPositiveButton(DialogMessagesUtility.YES_Va, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if (activity_Came_From_Va.equals(SwitchScreenUtility.CREATE_OR_UPDATE_INSTRUCTOR_ACT_Va)) {
                                    activity_Came_From_Va = activity_Came_From2_Va;
                                }

                                if (activity_Came_From_Va.equals(SwitchScreenUtility.DETAILED_COURSE_ACT_Va)) {
                                    switch_Screen_M(SwitchScreenUtility.getActClass_M(activity_Came_From_Va), SwitchScreenUtility.TERM_ID_KEY_Va,
                                            String.valueOf(term_Id_Va), SwitchScreenUtility.COURSE_ID_KEY_Va, String.valueOf(course_Id_Va));
                                } else {
                                    switch_Screen_M(SwitchScreenUtility.getActClass_M(activity_Came_From_Va), SwitchScreenUtility.TERM_ID_KEY_Va, String.valueOf(term_Id_Va));
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
    }
    void switchs_Screen_Va(Class screenN_Va, String key_Name_Va, String value_Va) {

        Intent intent_Va = new Intent(this, screenN_Va);

        intent_Va.putExtra(key_Name_Va, value_Va);

        startActivity(intent_Va);
    }
    void setDatabaseListVariables() throws InterruptedException {

        db_Course_List_Va = (ArrayList<Course>) repository_Va.getM_All_Courses_Va();
        db_Instructor_List_Va = (ArrayList<CourseInstructor>) repository_Va.getM_All_Course_Instructors_Va();
        db_Term_List_Va = (ArrayList<Term>) repository_Va.getM_All_Terms_Va();
    }

    ArrayAdapter<String> createStatusListAdapter() {
        ArrayList<String> statusOptionsList = new ArrayList<>();

        statusOptionsList.add(StatusSpinnerUtility.PLAN_TO_TAKE_Va);
        statusOptionsList.add(StatusSpinnerUtility.IN_PROGRESS_Va);
        statusOptionsList.add(StatusSpinnerUtility.COMPLETED_Va);
        statusOptionsList.add(StatusSpinnerUtility.DROPPED_va);

        return new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusOptionsList);
    }

    int getSpinnerStatusPosition(String status) {

        switch (status) {
            case StatusSpinnerUtility.PLAN_TO_TAKE_Va:
                return 0;
            case StatusSpinnerUtility.IN_PROGRESS_Va:
                return 1;
            case StatusSpinnerUtility.COMPLETED_Va:
                return 2;
            case StatusSpinnerUtility.DROPPED_va:
                return 3;
        }
        return -1;
    }

    ArrayAdapter<CourseInstructor> create_Instructor_List_Adapter_M() {
        return new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, db_Instructor_List_Va);
    }

    void switch_Screen_M(Class class_Name_Va, String id_Key_Va, String id_Value_Va) {
        Intent intent_Va = new Intent(this, class_Name_Va);
        intent_Va.putExtra(id_Key_Va, id_Value_Va);
        startActivity(intent_Va);
    }

    void switch_Screen_M(Class class_Name_Va, String id_Key1_Va, String id_Value1_Va, String id_Key2_Va, String id_Value2_Va) {
        Intent intent_Va = new Intent(this, class_Name_Va);
        intent_Va.putExtra(id_Key1_Va, id_Value1_Va);
        intent_Va.putExtra(id_Key2_Va, id_Value2_Va);
        startActivity(intent_Va);
    }

    void switch_Screen_M(Class class_Name_Va, String came_From_Key1_Va, String came_From_Value1_Va, String came_From_Key2_Va,
                         String cameFrom_Value_Va, String add_Or_UpdateSc_Va, String add_Or_Update_Sc_Va,
                         String came_From_Add_Va, String came_From_Update_ScreenValue_Va, String id_Key1_Va,
                         String id_Value1_Va, String id_Key2_Va, String id_Value2_Va) {
        Intent intent_Va = new Intent(this, class_Name_Va);

        intent_Va.putExtra(came_From_Key1_Va, came_From_Value1_Va);
        intent_Va.putExtra(came_From_Key2_Va, cameFrom_Value_Va);
        intent_Va.putExtra(add_Or_UpdateSc_Va, add_Or_Update_Sc_Va);
        intent_Va.putExtra(came_From_Add_Va, came_From_Update_ScreenValue_Va);
        intent_Va.putExtra(id_Key1_Va, id_Value1_Va);
        intent_Va.putExtra(id_Key2_Va, id_Value2_Va);
        startActivity(intent_Va);
    }

    void setAdd_Or_Update_Va(Intent intent) {
        if (activity_Came_From_Va.equals(SwitchScreenUtility.CREATE_OR_UPDATE_INSTRUCTOR_ACT_Va)) {
            add_Or_Update_Va = intent.getStringExtra(SwitchScreenUtility.CAME_FROM_ADD_OR_UPDATE_SCREEN_KEY_Va);
        } else {
            add_Or_Update_Va = intent.getStringExtra(SwitchScreenUtility.ADD_OR_UPDATE_SCREEN_KEY_Va);
        }
    }

    void setCourse_Id_Va(Intent intent) {
        if (!activity_Came_From_Va.equals(SwitchScreenUtility.DETAILED_TERM_ACT_Va)) {
            course_Id_Va = Integer.valueOf(intent.getStringExtra(SwitchScreenUtility.COURSE_ID_KEY_Va));
        }
    }

    void setTermID(Intent intent) {
        if (activity_Came_From_Va.equals(SwitchScreenUtility.DETAILED_COURSE_ACT_Va)) {
            term_Id_Va =  (CourseUtility.retrieveCourseFromD_ByCourseID_M(db_Course_List_Va, course_Id_Va).getTermID());
        } else {
            term_Id_Va = Integer.valueOf(intent.getStringExtra(SwitchScreenUtility.TERM_ID_KEY_Va));
        }
    }

    void setActivity_Came_From2_Va(Intent intent) {
        if (activity_Came_From_Va.equals(SwitchScreenUtility.DETAILED_COURSE_ACT_Va)) {
            activity_Came_From2_Va = activity_Came_From_Va;
        } else {
            activity_Came_From2_Va = intent.getStringExtra(SwitchScreenUtility.CAME_FROM_KEY2_Va);
        }
    }

    void setSpinnerSelected_Instructor_M() {
        if (!activity_Came_From_Va.equals(SwitchScreenUtility.DETAILED_COURSE_ACT_Va)) {
            return;
        }
        int instructor_For_Course_Va = CourseUtility.retrieveCourseFromD_ByCourseID_M(db_Course_List_Va, course_Id_Va).getInstructorID();

        select_Instructor_Va.setSelection(getSpinnerSelectedInstructor_Position_m(instructor_For_Course_Va));
    }

    Course getCourseForAddOr_Update_M() {
        if (add_Or_Update_Va.equals(SwitchScreenUtility.ADD_COURSE_VAL_Va)) {
            return new Course(class_Name_Va.getText().toString().trim(), class_Status_Va.getSelectedItem().toString(),
                    class_Info_Va.getText().toString().trim(), start_Date_Va.getText().toString(), end_Date_Va.getText().toString(),
                    term_Id_Va, ((CourseInstructor) select_Instructor_Va.getSelectedItem()).getInstructorID());
        }

        Course update_Course_Va = CourseUtility.retrieveCourseFromD_ByCourseID_M(db_Course_List_Va, course_Id_Va);

        update_Course_Va.updateFields_M(class_Name_Va, class_Info_Va, class_Status_Va, select_Instructor_Va, start_Date_Va, end_Date_Va);

        return update_Course_Va;
    }

    int getSpinnerSelectedInstructor_Position_m(int instructorIDForCourse) {
        if (db_Instructor_List_Va.size() == 0) {
            return -1;
        }

        int instructor_Index_Va = 0;
        for (CourseInstructor db_Instructor_Va : db_Instructor_List_Va) {
            if (db_Instructor_Va.getInstructorID() == instructorIDForCourse) {
                return instructor_Index_Va;
            }
            instructor_Index_Va++;
        }
        return -1;
    }

    void setScreenDetails() {
        if (add_Or_Update_Va.equals(SwitchScreenUtility.ADD_COURSE_VAL_Va)) {
            return;
        }

        Course course_Va = CourseUtility.retrieveCourseFromD_ByCourseID_M(db_Course_List_Va, course_Id_Va);

        class_Name_Va.setText(course_Va.getTitle());
        class_Info_Va.setText(course_Va.getInformation());
        class_Status_Va.setSelection(getSpinnerStatusPosition(course_Va.getStatus()));
        start_Date_Va.setText(course_Va.getStartDate());
        end_Date_Va.setText(course_Va.getEndDate());
    }
}