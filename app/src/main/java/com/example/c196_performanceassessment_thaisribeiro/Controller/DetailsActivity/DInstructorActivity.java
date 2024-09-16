package com.example.c196_performanceassessment_thaisribeiro.Controller.DetailsActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.c196_performanceassessment_thaisribeiro.R;
import com.example.c196_performanceassessment_thaisribeiro.Controller.CreateActivity.CUInstructorActivity;
import com.example.c196_performanceassessment_thaisribeiro.database.Repository;
import com.example.c196_performanceassessment_thaisribeiro.entities.CourseInstructor;
import com.example.c196_performanceassessment_thaisribeiro.helper.InstructorUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.SwitchScreenUtility;

import java.util.ArrayList;
/**
 * DInstructorActivity displays detailed information about a specific course instructor.
 * <p>
 * This activity presents the details of an instructor, including their name, email, and phone number.
 * Users can edit the instructor's information by navigating to the {@link CUInstructorActivity} for updating the instructor details.
 * </p>
 * <p>
 * The activity includes the following functionalities:
 * <ul>
 *   <li>Displays instructor details such as name, email, and phone number.</li>
 *   <li>Provides an option to edit the instructor's information.</li>
 *   <li>Facilitates navigation to the update screen for instructor details.</li>
 * </ul>
 * </p>
 * <p>
 * Components used by this activity:
 * <ul>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.database.Repository Repository} - For accessing instructor data.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.entities.CourseInstructor CourseInstructor} - Data model representing a course instructor.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.helper.InstructorUtility InstructorUtility} - Provides methods for retrieving instructor details.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.helper.SwitchScreenUtility SwitchScreenUtility} - Manages screen transitions and key constants.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.Controller.CreateActivity.CUInstructorActivity CUInstructorActivity} - Activity for editing or creating instructor details.</li>
 * </ul>
 * </p>
 */
public class DInstructorActivity extends AppCompatActivity {
    Repository repository_Va;
    int instructor_Id_Va;
    Button edit_Btn_Va;
    TextView name_Va;
    TextView email_Va;
    TextView phone_Number_Va;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_inst_sc);

        repository_Va = new Repository(getApplication());
        Intent intent_Va = getIntent();
        instructor_Id_Va = Integer.valueOf(intent_Va.getStringExtra(SwitchScreenUtility.INSTRUCTOR_ID_KEY_Va));

        edit_Btn_Va = findViewById(R.id.detailed_view_edit_btn);
        name_Va = findViewById(R.id.detailed_view_ci_name);
        email_Va = findViewById(R.id.detailed_view_ci_email);
        phone_Number_Va = findViewById(R.id.detailed_view_ci_number);

        setScreenDetails(instructor_Id_Va);

        edit_Btn_Va.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch_Screens_M(CUInstructorActivity.class, SwitchScreenUtility.CAME_FROM_KEY_Va,
                        SwitchScreenUtility.DETAILED_INSTRUCTOR_ACT_Va, SwitchScreenUtility.ADD_OR_UPDATE_SCREEN_KEY_Va,
                        SwitchScreenUtility.UPDATE_INSTRUCTOR_VAL_Va, SwitchScreenUtility.INSTRUCTOR_ID_KEY_Va, String.valueOf(instructor_Id_Va));
            }
        });
    }

    void setScreenDetails(int instructor_Id_Va) {
        CourseInstructor instructor_Va;

        try {
            instructor_Va = InstructorUtility.retrieveC_FromDatabaseByInstructorID_M((ArrayList<CourseInstructor>) repository_Va.getM_All_Course_Instructors_Va(), instructor_Id_Va);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        name_Va.setText(instructor_Va.getName());
        email_Va.setText(instructor_Va.getEmail());
        phone_Number_Va.setText(instructor_Va.getPhoneNumber());
    }

    void switch_Screens_M(Class screens_Va, String cameFrom_ScreenK_Va, String cameF_Screen_Value_Va, String addOr_Update_Screen_K_Va,
                          String add_UpdateScreen_Va, String id_Key_Va, String id_Value_Va) {

        Intent intent_Va = new Intent(this, screens_Va);

        intent_Va.putExtra(cameFrom_ScreenK_Va, cameF_Screen_Value_Va);
        intent_Va.putExtra(addOr_Update_Screen_K_Va, add_UpdateScreen_Va);
        intent_Va.putExtra(id_Key_Va, id_Value_Va);

        startActivity(intent_Va);
    }
}