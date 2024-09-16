package com.example.c196_performanceassessment_thaisribeiro.Controller.ListActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.c196_performanceassessment_thaisribeiro.R;
import com.example.c196_performanceassessment_thaisribeiro.Controller.ViewAdapter.InstructorAdapter;
import com.example.c196_performanceassessment_thaisribeiro.Controller.CreateActivity.CUInstructorActivity;
import com.example.c196_performanceassessment_thaisribeiro.Controller.DetailsActivity.DInstructorActivity;
import com.example.c196_performanceassessment_thaisribeiro.database.Repository;
import com.example.c196_performanceassessment_thaisribeiro.entities.Course;
import com.example.c196_performanceassessment_thaisribeiro.entities.CourseInstructor;
import com.example.c196_performanceassessment_thaisribeiro.helper.DialogMessagesUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.InstructorUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.SelectedListItemUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.SwitchScreenUtility;

import java.util.ArrayList;
import java.util.List;
/**
 * LOfInstructorsActivity displays a list of instructors and provides options for managing them.
 * <p>
 * This activity allows users to view a list of all instructors, add new instructors, and delete existing ones. It uses a {@link RecyclerView}
 * to display the list of instructors and provides buttons to perform various actions related to instructors.
 * </p>
 * <p>
 * The activity provides the following functionalities:
 * <ul>
 *   <li>Displays a list of instructors using a {@link RecyclerView} and {@link InstructorAdapter}.</li>
 *   <li>Allows users to add a new instructor by navigating to {@link CUInstructorActivity}.</li>
 *   <li>Enables viewing of a selected instructor by navigating to {@link DInstructorActivity}.</li>
 *   <li>Provides functionality to delete a selected instructor with confirmation. If the instructor is assigned to any courses,
 *       the deletion will be prevented.</li>
 * </ul>
 * </p>
 * <p>
 * Components used by this activity:
 * <ul>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.database.Repository Repository} - For accessing instructor data.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.entities.CourseInstructor CourseInstructor} - Data model representing an instructor.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.Controller.CreateActivity.CUInstructorActivity CUInstructorActivity} - Activity for creating or updating instructors.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.Controller.DetailsActivity.DInstructorActivity DInstructorActivity} - Activity for viewing detailed information of an instructor.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.Controller.ViewAdapter.InstructorAdapter InstructorAdapter} - Adapter for displaying instructors in a {@link RecyclerView}.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.helper.DialogMessagesUtility DialogMessagesUtility} - Provides utility methods for dialog messages.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.helper.InstructorUtility InstructorUtility} - Provides methods for managing instructors and their assignments.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.helper.SelectedListItemUtility SelectedListItemUtility} - Utility for managing selected list items.</li>
 *   <li>{@link androidx.appcompat.app.AlertDialog AlertDialog} - For displaying confirmation dialogs.</li>
 * </ul>
 * </p>
 */
public class LOfInstructorsActivity extends AppCompatActivity {
    Repository repository_Va;
    RecyclerView instructors_List_Va;
    Button delete_CI_Va;
    Button view_CI_Va;
    Button add_CI_Va;
    AlertDialog.Builder builder_Va;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_instr_sc);

        repository_Va = new Repository(getApplication());
        builder_Va = new AlertDialog.Builder(this);

        instructors_List_Va = findViewById(R.id.list_of_instructors_ci_list);
        delete_CI_Va = findViewById(R.id.list_of_instructors_delete_ci_btn);
        view_CI_Va = findViewById(R.id.list_of_instructors_view_ci_btn);
        add_CI_Va = findViewById(R.id.list_of_instructors_add_ci_btn);

        setCourse_Recycler_View_m();

        add_CI_Va.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_Btn_Switch_Screen_M(CUInstructorActivity.class, SwitchScreenUtility.CAME_FROM_KEY_Va,
                        SwitchScreenUtility.LIST_OF_INSTRUCTORS_ACT_Va, SwitchScreenUtility.ADD_OR_UPDATE_SCREEN_KEY_Va, SwitchScreenUtility.ADD_INSTRUCTOR_VAL_Va);
            }
        });

        delete_CI_Va.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SelectedListItemUtility.getSelected_Instructor_Va() == null) {
                    Toast.makeText(LOfInstructorsActivity.this, DialogMessagesUtility.NEED_TO_SELECT_AN_INST_Va + " delete", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {

                    if (InstructorUtility.doesInstructorHaveC_M(SelectedListItemUtility.getSelected_Instructor_Va().getInstructorID(),
                            (ArrayList<Course>) repository_Va.getM_All_Courses_Va())) {
                        Toast.makeText(LOfInstructorsActivity.this, "You cannot delete " + SelectedListItemUtility.getSelected_Instructor_Va() + " because they are assigned to courses.", Toast.LENGTH_LONG).show();
                        return;
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                builder_Va.setTitle(DialogMessagesUtility.CONFIRMATION_Va)
                        .setMessage(DialogMessagesUtility.DELETE_CONFIRMATION_MESSAGE_Va + SelectedListItemUtility.getSelected_Instructor_Va().getName() + "?")
                        .setCancelable(true)
                        .setPositiveButton(DialogMessagesUtility.YES_Va, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try {
                                    Toast.makeText(LOfInstructorsActivity.this, "Deleted " + SelectedListItemUtility.getSelected_Instructor_Va(), Toast.LENGTH_LONG).show();
                                    repository_Va.delete(SelectedListItemUtility.getSelected_Instructor_Va());
                                    SelectedListItemUtility.setSelected_Instructor_Va(null);
                                    setCourse_Recycler_View_m();
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
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

        view_CI_Va.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SelectedListItemUtility.getSelected_Instructor_Va() == null) {
                    Toast.makeText(LOfInstructorsActivity.this, DialogMessagesUtility.NEED_TO_SELECT_AN_INST_Va + " view", Toast.LENGTH_SHORT).show();
                    return;
                }

                int instructor_Id_Va = SelectedListItemUtility.getSelected_Instructor_Va().getInstructorID();
                SelectedListItemUtility.setSelected_Instructor_Va(null);

                switch_Screen_M(DInstructorActivity.class, SwitchScreenUtility.CAME_FROM_KEY_Va, SwitchScreenUtility.LIST_OF_INSTRUCTORS_ACT_Va,
                        SwitchScreenUtility.INSTRUCTOR_ID_KEY_Va, String.valueOf(instructor_Id_Va));
            }
        });
    }

    void add_Btn_Switch_Screen_M(Class screenName_Va, String key_Name_Va, String value_Va, String add_Update_ScreenKey_Va, String add_UpdateScreenValue_Va) {

        Intent intent_Va = new Intent(this, screenName_Va);

        intent_Va.putExtra(key_Name_Va, value_Va);
        intent_Va.putExtra(add_Update_ScreenKey_Va, add_UpdateScreenValue_Va);

        startActivity(intent_Va);
    }

    void switch_Screen_M(Class screen_Name_Va, String screen_NameKey_Va , String screen_Name_Value_Va, String id_Key_Va, String idValue) {

        Intent intent_va = new Intent(this, screen_Name_Va);

        intent_va.putExtra(screen_NameKey_Va, screen_Name_Value_Va);
        intent_va.putExtra(id_Key_Va, idValue);

        startActivity(intent_va);
    }

    void setCourse_Recycler_View_m() {
        List<CourseInstructor> all_Instructors_Va;

        try {
            all_Instructors_Va = repository_Va.getM_All_Course_Instructors_Va();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        final InstructorAdapter instructor_Adapter_Va = new InstructorAdapter(this);

        instructors_List_Va.setAdapter(instructor_Adapter_Va);
        instructors_List_Va.setLayoutManager(new LinearLayoutManager(this));
        instructor_Adapter_Va.setInstructors(all_Instructors_Va);
    }
}