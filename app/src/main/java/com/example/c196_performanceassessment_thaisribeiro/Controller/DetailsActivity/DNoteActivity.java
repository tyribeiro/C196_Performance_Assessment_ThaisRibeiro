package com.example.c196_performanceassessment_thaisribeiro.Controller.DetailsActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.c196_performanceassessment_thaisribeiro.R;
import com.example.c196_performanceassessment_thaisribeiro.Controller.CreateActivity.CUNoteActivity;
import com.example.c196_performanceassessment_thaisribeiro.database.Repository;
import com.example.c196_performanceassessment_thaisribeiro.entities.Course;
import com.example.c196_performanceassessment_thaisribeiro.entities.CourseNote;
import com.example.c196_performanceassessment_thaisribeiro.helper.CourseUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.CourseNoteUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.SwitchScreenUtility;
import com.example.c196_performanceassessment_thaisribeiro.Controller.DetailsActivity.DCourseActivity;

import java.util.ArrayList;
/**
 * DNoteActivity displays detailed information about a specific course note.
 * <p>
 * This activity shows the details of a course note, including the note's content. It provides functionality to edit the note,
 * navigate back to the course details, and share the note via other applications.
 * </p>
 * <p>
 * The activity includes the following functionalities:
 * <ul>
 *   <li>Displays the content of the selected course note.</li>
 *   <li>Allows navigation back to the detailed course view.</li>
 *   <li>Provides an option to edit the note by navigating to {@link CUNoteActivity}.</li>
 *   <li>Allows sharing of the note's content through other applications.</li>
 * </ul>
 * </p>
 * <p>
 * Components used by this activity:
 * <ul>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.database.Repository Repository} - For accessing course notes and courses data.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.entities.CourseNote CourseNote} - Data model representing a course note.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.helper.CourseUtility CourseUtility} - Provides methods for retrieving course details.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.helper.CourseNoteUtility CourseNoteUtility} - Provides methods for retrieving note details.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.Controller.CreateActivity.CUNoteActivity CUNoteActivity} - Activity for editing or creating course notes.</li>
 * </ul>
 * </p>
 */
public class DNoteActivity extends AppCompatActivity {
    Repository repository_Va;
    Button edit_Btn_Va;
    Button share_Btn_Va;
    Button back_Btn_Va;
    TextView noted_Details_Va;
    int note_Id_Va;
    ArrayList<CourseNote> db_Note_List_Va;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_note_sc);

        repository_Va = new Repository(getApplication());
        Intent intent_Va = getIntent();
        note_Id_Va = Integer.valueOf(intent_Va.getStringExtra(SwitchScreenUtility.COURSE_NOTE_ID_KEY_Va));

        try {
            db_Note_List_Va = (ArrayList<CourseNote>) repository_Va.getM_All_Course_Notes_Va();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        CourseNote note_va = CourseNoteUtility.retrieveN_FromDatabaseByNoteID_m(db_Note_List_Va, note_Id_Va);

        edit_Btn_Va = findViewById(R.id.detailed_note_edit_note_btn);
        noted_Details_Va = findViewById(R.id.detailed_note_note);
        share_Btn_Va = findViewById(R.id.detailed_note_share_note_btn);
        back_Btn_Va = findViewById(R.id.detailed_note_back_btn);

        noted_Details_Va.setText(note_va.getNote());

        back_Btn_Va.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch_Screens_M(DCourseActivity.class, SwitchScreenUtility.COURSE_ID_KEY_Va, String.valueOf(note_va.getCourseID()));
            }
        });

        edit_Btn_Va.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch_Screens_M(CUNoteActivity.class, SwitchScreenUtility.CAME_FROM_KEY_Va, SwitchScreenUtility.DETAILED_NOTE_ACT_Va,
                        SwitchScreenUtility.ADD_OR_UPDATE_SCREEN_KEY_Va, SwitchScreenUtility.UPDATE_NOTE_VAL_Va, SwitchScreenUtility.COURSE_NOTE_ID_KEY_Va, String.valueOf(note_Id_Va));
            }
        });

        share_Btn_Va.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String course_Title_Va;

                try {
                    course_Title_Va = CourseUtility.retrieveCourseFromD_ByCourseID_M((ArrayList<Course>) repository_Va.getM_All_Courses_Va(), note_va.getCourseID()).getTitle();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                share_Data_m(course_Title_Va, note_va.getNote());
            }
        });
    }

    void share_Data_m(String course_Name_Va, String note_Body_Va) {
        Intent intent_Va = new Intent(Intent.ACTION_SEND);

        intent_Va.setType("text/plain");

        intent_Va.putExtra(Intent.EXTRA_SUBJECT, "Note for course: " + course_Name_Va);

        intent_Va.putExtra(Intent.EXTRA_TEXT, note_Body_Va);

        startActivity(Intent.createChooser(intent_Va, "Choose a Platform"));
    }

    void switch_Screens_M(Class screnns_Va, String id_Key_Va, String id_Value_Va) {

        Intent intent_Va = new Intent(this, screnns_Va);

        intent_Va.putExtra(id_Key_Va, id_Value_Va);

        startActivity(intent_Va);
    }

    void switch_Screens_M(Class screen_Va, String cameFrom_Scr_Key_Va, String cameFrom_Scr_Value_Va, String addUpdate_Scr_Key_Va,
                          String add_UpdateScr_Value_Va, String id_Key_Va, String id_Value_Va) {

        Intent intent_Va = new Intent(this, screen_Va);

        intent_Va.putExtra(cameFrom_Scr_Key_Va, cameFrom_Scr_Value_Va);
        intent_Va.putExtra(addUpdate_Scr_Key_Va, add_UpdateScr_Value_Va);
        intent_Va.putExtra(id_Key_Va, id_Value_Va);

        startActivity(intent_Va);
    }
}