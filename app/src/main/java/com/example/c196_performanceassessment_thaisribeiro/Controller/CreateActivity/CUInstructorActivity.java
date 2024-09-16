package com.example.c196_performanceassessment_thaisribeiro.Controller.CreateActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.c196_performanceassessment_thaisribeiro.R;
import com.example.c196_performanceassessment_thaisribeiro.database.Repository;
import com.example.c196_performanceassessment_thaisribeiro.entities.CourseInstructor;
import com.example.c196_performanceassessment_thaisribeiro.helper.DialogMessagesUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.InstructorUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.InputValidationUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.SwitchScreenUtility;

import java.util.ArrayList;
/**
 * CUInstructorActivity is an Android Activity class responsible for creating and updating CourseInstructor entities.
 * <p>
 * This activity allows users to input details for a new instructor or modify details of an existing instructor.
 * It interacts with a database through the Repository class to perform insert and update operations.
 * </p>
 * <p>
 * The activity also handles navigation to and from other activities and displays confirmation dialogs when canceling actions.
 * </p>
 * <p>
 * It includes the following functionalities:
 * <ul>
 *   <li>Retrieve and display existing instructor data if updating.</li>
 *   <li>Validate input fields to ensure they are not empty.</li>
 *   <li>Check if the instructor already exists in the database before saving.</li>
 *   <li>Save or update instructor data in the database.</li>
 *   <li>Handle navigation and screen transitions based on user actions and data.</li>
 * </ul>
 * </p>
 * <p>
 * This class uses the following helper classes:
 * <ul>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.database.Repository Repository} - For database operations.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.entities.CourseInstructor CourseInstructor} - Data model for instructor information.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.helper.DialogMessagesUtility DialogMessagesUtility} - For standardized dialog messages.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.helper.InstructorUtility InstructorUtility} - For instructor-related utilities.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.helper.InputValidationUtility InputValidationUtility} - For validating user inputs.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.helper.SwitchScreenUtility SwitchScreenUtility} - For managing screen transitions.</li>
 * </ul>
 * </p>
 */
public class CUInstructorActivity extends AppCompatActivity {
    Repository repository_Va;
    EditText name_Va;
    EditText email_va;
    EditText phone_Number_Va;
    Button save_Btn_Va;
    Button cancel_Btn_Va;
    ArrayList<CourseInstructor> db_Instructor_List_Va;
    String add_Or_Update_Va;
    int instructor_Id_Va;
    AlertDialog.Builder builder_Va;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_update_in_sc);

        repository_Va = new Repository(getApplication());
        builder_Va = new AlertDialog.Builder(this);

        try {
            db_Instructor_List_Va = (ArrayList<CourseInstructor>) repository_Va.getM_All_Course_Instructors_Va();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Intent intent_Va = getIntent();

        String activity_Came_From_Va = intent_Va.getStringExtra(SwitchScreenUtility.CAME_FROM_KEY_Va);
        add_Or_Update_Va = intent_Va.getStringExtra(SwitchScreenUtility.ADD_OR_UPDATE_SCREEN_KEY_Va);

        setInstructor_Id_Va(intent_Va);

        setTitle(add_Or_Update_Va);

        name_Va = findViewById(R.id.create_ci_name);
        email_va = findViewById(R.id.create_ci_email);
        phone_Number_Va = findViewById(R.id.create_ci_phone_number);
        save_Btn_Va = findViewById(R.id.create_ci_save_btn);
        cancel_Btn_Va = findViewById(R.id.create_ci_cancel_btn);

        setScreenDetails(add_Or_Update_Va, intent_Va);

        save_Btn_Va.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (InputValidationUtility.isI_FieldEmpty_M(name_Va) || InputValidationUtility.isI_FieldEmpty_M(email_va) ||
                        InputValidationUtility.isI_FieldEmpty_M(phone_Number_Va)) {
                    Toast.makeText(CUInstructorActivity.this, DialogMessagesUtility.EMPTY_INPUT_FIELDS_Va, Toast.LENGTH_SHORT).show();
                    return;
                }

                CourseInstructor save_Instructor_Va = getInstructorFor_Add_Update_M();

                if (InstructorUtility.doesC_InstructorExistInDatabase_M(save_Instructor_Va, db_Instructor_List_Va)) {

                    Toast.makeText(CUInstructorActivity.this, DialogMessagesUtility.INSTRUCTOR_ALREADY_E_Va, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (add_Or_Update_Va.equals(SwitchScreenUtility.ADD_INSTRUCTOR_VAL_Va)) {
                    try {

                        repository_Va.insert(save_Instructor_Va);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    if (activity_Came_From_Va.equals(SwitchScreenUtility.CREATE_OR_UPDATE_COURSE_ACT_Va)) {
                        Toast.makeText(CUInstructorActivity.this, "Created " + save_Instructor_Va.getName(), Toast.LENGTH_SHORT).show();
                        switchs_Screens_M(SwitchScreenUtility.getActClass_M(activity_Came_From_Va), SwitchScreenUtility.CAME_FROM_KEY_Va,
                                SwitchScreenUtility.CREATE_OR_UPDATE_INSTRUCTOR_ACT_Va, SwitchScreenUtility.CAME_FROM_KEY2_Va,
                                intent_Va.getStringExtra(SwitchScreenUtility.CAME_FROM_KEY2_Va), SwitchScreenUtility.CAME_FROM_ADD_OR_UPDATE_SCREEN_KEY_Va,
                                intent_Va.getStringExtra(SwitchScreenUtility.CAME_FROM_ADD_OR_UPDATE_SCREEN_KEY_Va), SwitchScreenUtility.TERM_ID_KEY_Va,
                                intent_Va.getStringExtra(SwitchScreenUtility.TERM_ID_KEY_Va), SwitchScreenUtility.COURSE_ID_KEY_Va,
                                intent_Va.getStringExtra(SwitchScreenUtility.COURSE_ID_KEY_Va));
                    } else {
                        Toast.makeText(CUInstructorActivity.this, "Created " + save_Instructor_Va.getName(), Toast.LENGTH_SHORT).show();
                        switchs_Screens_M(SwitchScreenUtility.getActClass_M(activity_Came_From_Va));
                    }

                } else {
                    try {

                        repository_Va.update(save_Instructor_Va);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    Toast.makeText(CUInstructorActivity.this, "Updated " + save_Instructor_Va.getName(), Toast.LENGTH_SHORT).show();
                    switchs_Screens_M(SwitchScreenUtility.getActClass_M(activity_Came_From_Va), SwitchScreenUtility.INSTRUCTOR_ID_KEY_Va, String.valueOf(instructor_Id_Va));
                }
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

                                if (activity_Came_From_Va.equals(SwitchScreenUtility.DETAILED_INSTRUCTOR_ACT_Va)) {
                                    switchs_Screens_M(SwitchScreenUtility.getActClass_M(activity_Came_From_Va), SwitchScreenUtility.INSTRUCTOR_ID_KEY_Va, String.valueOf(instructor_Id_Va));
                                } else if (activity_Came_From_Va.equals(SwitchScreenUtility.CREATE_OR_UPDATE_COURSE_ACT_Va)) {
                                    switchs_Screens_M(SwitchScreenUtility.getActClass_M(activity_Came_From_Va), SwitchScreenUtility.CAME_FROM_KEY_Va,
                                            SwitchScreenUtility.CREATE_OR_UPDATE_INSTRUCTOR_ACT_Va, SwitchScreenUtility.CAME_FROM_KEY2_Va,
                                            intent_Va.getStringExtra(SwitchScreenUtility.CAME_FROM_KEY2_Va), SwitchScreenUtility.CAME_FROM_ADD_OR_UPDATE_SCREEN_KEY_Va,
                                            intent_Va.getStringExtra(SwitchScreenUtility.CAME_FROM_ADD_OR_UPDATE_SCREEN_KEY_Va), SwitchScreenUtility.TERM_ID_KEY_Va,
                                            intent_Va.getStringExtra(SwitchScreenUtility.TERM_ID_KEY_Va), SwitchScreenUtility.COURSE_ID_KEY_Va,
                                            intent_Va.getStringExtra(SwitchScreenUtility.COURSE_ID_KEY_Va));
                                } else {
                                    switchs_Screens_M(SwitchScreenUtility.getActClass_M(activity_Came_From_Va));
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

    void switchs_Screens_M(Class screen_Name_Va) {

        Intent intent_Va = new Intent(this, screen_Name_Va);

        startActivity(intent_Va);
    }

    void switchs_Screens_M(Class screen_Va, String id_Key_Va, String id_Value_Va) {

        Intent intent_Va = new Intent(this, screen_Va);

        intent_Va.putExtra(id_Key_Va, id_Value_Va);

        startActivity(intent_Va);
    }

    void switchs_Screens_M(Class screenName_Va, String came_From_Key1_Va, String came_From_Value1_Va, String came_From_Key2_Va,
                           String came_From_Value2_Va, String came_From_AddOr_UpdateKey_Va, String From_Add_UpdateValue_Va,
                           String id_Key1_Va, String id_Value1_Va, String id_Key2_Va, String id_Value_2_Va) {

        Intent intent_Va = new Intent(this, screenName_Va);

        intent_Va.putExtra(came_From_Key1_Va, came_From_Value1_Va);
        intent_Va.putExtra(came_From_Key2_Va, came_From_Value2_Va);
        intent_Va.putExtra(came_From_AddOr_UpdateKey_Va, From_Add_UpdateValue_Va);
        intent_Va.putExtra(id_Key1_Va, id_Value1_Va);
        intent_Va.putExtra(id_Key2_Va, id_Value_2_Va);

        startActivity(intent_Va);
    }

    void setScreenDetails(String add_Update_Va, Intent intent_Va) {
        if (add_Update_Va.equals(SwitchScreenUtility.ADD_INSTRUCTOR_VAL_Va)) {
            return;
        }

        CourseInstructor instructor_Va = InstructorUtility.retrieveC_FromDatabaseByInstructorID_M(db_Instructor_List_Va, instructor_Id_Va);

        name_Va.setText(instructor_Va.getName());
        email_va.setText(instructor_Va.getEmail());
        phone_Number_Va.setText(instructor_Va.getPhoneNumber());
    }

    void setInstructor_Id_Va(Intent intent_Va) {
        if (add_Or_Update_Va.equals(SwitchScreenUtility.ADD_INSTRUCTOR_VAL_Va)) {
            return;
        }
        instructor_Id_Va = Integer.valueOf(intent_Va.getStringExtra(SwitchScreenUtility.INSTRUCTOR_ID_KEY_Va));
    }

    CourseInstructor getInstructorFor_Add_Update_M() {
        if (add_Or_Update_Va.equals(SwitchScreenUtility.ADD_INSTRUCTOR_VAL_Va)) {
            return new CourseInstructor(name_Va.getText().toString().trim(),
                    phone_Number_Va.getText().toString(), email_va.getText().toString());
        }
        CourseInstructor instructor_Va = InstructorUtility.retrieveC_FromDatabaseByInstructorID_M(db_Instructor_List_Va, Integer.valueOf(instructor_Id_Va));

        instructor_Va.setName(name_Va.getText().toString().trim());
        instructor_Va.setEmail(email_va.getText().toString());
        instructor_Va.setPhoneNumber(phone_Number_Va.getText().toString());

        return instructor_Va;
    }
}