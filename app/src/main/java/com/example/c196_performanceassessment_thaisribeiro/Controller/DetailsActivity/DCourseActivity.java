package com.example.c196_performanceassessment_thaisribeiro.Controller.DetailsActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c196_performanceassessment_thaisribeiro.Controller.MainActivity;
import com.example.c196_performanceassessment_thaisribeiro.R;
import com.example.c196_performanceassessment_thaisribeiro.Controller.MainActivity;
import com.example.c196_performanceassessment_thaisribeiro.Controller.MyReceiver;
import com.example.c196_performanceassessment_thaisribeiro.Controller.ViewAdapter.AssessmentAdapter;
import com.example.c196_performanceassessment_thaisribeiro.Controller.ViewAdapter.NoteAdapter;
import com.example.c196_performanceassessment_thaisribeiro.Controller.CreateActivity.CUAssessmentActivity;
import com.example.c196_performanceassessment_thaisribeiro.Controller.CreateActivity.CUCourseActivity;
import com.example.c196_performanceassessment_thaisribeiro.Controller.CreateActivity.CUNoteActivity;
import com.example.c196_performanceassessment_thaisribeiro.database.Repository;
import com.example.c196_performanceassessment_thaisribeiro.entities.Assessment;
import com.example.c196_performanceassessment_thaisribeiro.entities.Course;
import com.example.c196_performanceassessment_thaisribeiro.entities.CourseInstructor;
import com.example.c196_performanceassessment_thaisribeiro.entities.CourseNote;
import com.example.c196_performanceassessment_thaisribeiro.helper.AssessmentUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.CourseUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.DateFormatUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.DialogMessagesUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.InstructorUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.SelectedListItemUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.SwitchScreenUtility;
import com.example.c196_performanceassessment_thaisribeiro.Controller.DetailsActivity.DAssessmentActivity;
import com.example.c196_performanceassessment_thaisribeiro.Controller.DetailsActivity.DNoteActivity;
import com.example.c196_performanceassessment_thaisribeiro.Controller.DetailsActivity.DTermActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
/**
 * DCourseActivity displays detailed information about a specific course.
 * <p>
 * This activity presents the details of a course, including its start and end dates, instructor name, and associated assessments and notes.
 * Users can perform actions such as viewing or deleting assessments and notes, navigating to related screens, and setting notifications for the course's start or end dates.
 * </p>
 * <p>
 * The activity includes the following functionalities:
 * <ul>
 *   <li>Displays course details such as start date, end date, and instructor name.</li>
 *   <li>Provides options to view or delete assessments and notes associated with the course.</li>
 *   <li>Allows navigation to other activities for managing assessments and notes.</li>
 *   <li>Sets notifications for the course's start and end dates using alarms.</li>
 * </ul>
 * </p>
 * <p>
 * Components used by this activity:
 * <ul>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.database.Repository Repository} - For accessing course data, assessments, and notes.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.entities.Course Course} - Data model representing a course.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.entities.CourseInstructor CourseInstructor} - Data model representing a course instructor.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.entities.CourseNote CourseNote} - Data model representing a course note.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.entities.Assessment Assessment} - Data model representing an assessment.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.helper.CourseUtility CourseUtility} - Provides methods for retrieving course and note details.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.helper.InstructorUtility InstructorUtility} - Provides methods for retrieving instructor details.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.helper.SelectedListItemUtility SelectedListItemUtility} - Manages the currently selected assessment or note.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.helper.SwitchScreenUtility SwitchScreenUtility} - Manages screen transitions and key constants.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.Controller.MyReceiver MyReceiver} - Broadcast receiver for handling alarms.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.Controller.ViewAdapter.AssessmentAdapter AssessmentAdapter} - Adapter for displaying assessments in a RecyclerView.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.Controller.ViewAdapter.NoteAdapter NoteAdapter} - Adapter for displaying notes in a RecyclerView.</li>
 * </ul>
 * </p>
 */
public class DCourseActivity extends AppCompatActivity {
    Repository repository_Va;
    int course_Id_Va;
    Button view_Assignment_Btn_Va;
    Button delete_Assessment_Btn_Va;
    Button view_Note_Btn_Va;
    Button back_Btn_Va;
    Button delete_Note_Btn_Va;
    TextView start_Date_Va;
    TextView end_Date_Va;
    TextView instructor_Name_Va;
    RecyclerView assessments_List_Va;
    RecyclerView notes_List_Va;
    ArrayList<Course> db_Course_List_Va;
    AlertDialog.Builder builder_Va;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_cou_sc);

        repository_Va = new Repository(getApplication());
        builder_Va = new AlertDialog.Builder(this);

        try {
            db_Course_List_Va = (ArrayList<Course>) repository_Va.getM_All_Courses_Va();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Intent intent_Va = getIntent();
        course_Id_Va = Integer.valueOf(intent_Va.getStringExtra(SwitchScreenUtility.COURSE_ID_KEY_Va));

        view_Assignment_Btn_Va = findViewById(R.id.detailed_class_view_assignment_btn);
        start_Date_Va = findViewById(R.id.detailed_class_start_date);
        end_Date_Va = findViewById(R.id.detailed_class_end_date);
        instructor_Name_Va = findViewById(R.id.detailed_ci_name);
        assessments_List_Va = findViewById(R.id.detailed_class_assessments_list);
        delete_Assessment_Btn_Va = findViewById(R.id.detailed_class_delete_assignment_btn);
        view_Note_Btn_Va = findViewById(R.id.detailed_class_view_note_btn);
        delete_Note_Btn_Va = findViewById(R.id.detailed_class_delete_note_btn);
        notes_List_Va = findViewById(R.id.detailed_class_notes_list);
        back_Btn_Va = findViewById(R.id.detailed_course_back_btn);

        setTitle(CourseUtility.retrieveCourseFromD_ByCourseID_M(db_Course_List_Va, course_Id_Va).getTitle());

        try {
            setScreenDetails();
            setAssessment_Recycler_View_M();
            setCourse_Note_Recycler_View_M();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        view_Assignment_Btn_Va.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SelectedListItemUtility.getSelected_Assessment_Va() == null) {
                    Toast.makeText(DCourseActivity.this, DialogMessagesUtility.NEED_TO_SELECT_AN_AS_Va + " view", Toast.LENGTH_SHORT).show();
                    return;
                }

                Assessment assessment_Va = SelectedListItemUtility.getSelected_Assessment_Va();

                SelectedListItemUtility.setSelected_Assessment_Va(null);

                switch_Screens_Va(DAssessmentActivity.class, SwitchScreenUtility.CAME_FROM_KEY_Va, SwitchScreenUtility.DETAILED_COURSE_ACT_Va,
                        SwitchScreenUtility.ASSESSMENT_ID_KEY_Va, String.valueOf(assessment_Va.getAssessmentID()));
            }
        });

        view_Note_Btn_Va.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SelectedListItemUtility.getSelected_Note_Va() == null) {
                    Toast.makeText(DCourseActivity.this, DialogMessagesUtility.NEED_TO_SELECT_A_N_Va + " view", Toast.LENGTH_SHORT).show();
                    return;
                }

                CourseNote note_Va = SelectedListItemUtility.getSelected_Note_Va();

                SelectedListItemUtility.setSelected_Note_Va(null);

                switch_Screens_Va(DNoteActivity.class, SwitchScreenUtility.CAME_FROM_KEY_Va, SwitchScreenUtility.DETAILED_COURSE_ACT_Va,
                        SwitchScreenUtility.COURSE_NOTE_ID_KEY_Va, String.valueOf(note_Va.getCourseNoteID()));
            }
        });

        back_Btn_Va.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int term_Id_Va = CourseUtility.retrieveCourseFromD_ByCourseID_M(db_Course_List_Va, course_Id_Va).getTermID();

                SelectedListItemUtility.setSelected_Note_Va(null);
                SelectedListItemUtility.setSelected_Assessment_Va(null);

                switch_Screens_Va(DTermActivity.class, SwitchScreenUtility.TERM_ID_KEY_Va, String.valueOf(term_Id_Va));
            }
        });

        delete_Note_Btn_Va.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SelectedListItemUtility.getSelected_Note_Va() == null) {
                    Toast.makeText(DCourseActivity.this, DialogMessagesUtility.NEED_TO_SELECT_A_N_Va + " delete", Toast.LENGTH_SHORT).show();
                    return;
                }

                builder_Va.setTitle(DialogMessagesUtility.CONFIRMATION_Va)
                        .setMessage(DialogMessagesUtility.DELETE_CONFIRMATION_MESSAGE_Va + "the note?")
                        .setCancelable(true)
                        .setPositiveButton(DialogMessagesUtility.YES_Va, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try {
                                    Toast.makeText(DCourseActivity.this, "Deleted note", Toast.LENGTH_SHORT).show();
                                    repository_Va.delete(SelectedListItemUtility.getSelected_Note_Va());
                                    SelectedListItemUtility.setSelected_Note_Va(null);
                                    setCourse_Note_Recycler_View_M();
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

        delete_Assessment_Btn_Va.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SelectedListItemUtility.getSelected_Assessment_Va() == null) {
                    Toast.makeText(DCourseActivity.this, DialogMessagesUtility.NEED_TO_SELECT_AN_AS_Va + " delete", Toast.LENGTH_SHORT).show();
                    return;
                }

                builder_Va.setTitle(DialogMessagesUtility.CONFIRMATION_Va)
                        .setMessage(DialogMessagesUtility.DELETE_CONFIRMATION_MESSAGE_Va + SelectedListItemUtility.getSelected_Assessment_Va().getTitle() + "?")
                        .setCancelable(true)
                        .setPositiveButton(DialogMessagesUtility.YES_Va, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try {
                                    Toast.makeText(DCourseActivity.this, "Deleted " + SelectedListItemUtility.getSelected_Assessment_Va().getTitle(), Toast.LENGTH_SHORT).show();
                                    repository_Va.delete(SelectedListItemUtility.getSelected_Assessment_Va());
                                    SelectedListItemUtility.setSelected_Assessment_Va(null);
                                    setAssessment_Recycler_View_M();
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detailed_course, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getTitle() == null) {
            return false;
        }

        if (item.getTitle().equals(getString(R.string.menu_update_course))) {
            switch_Screens_Va(CUCourseActivity.class, SwitchScreenUtility.CAME_FROM_KEY_Va, SwitchScreenUtility.DETAILED_COURSE_ACT_Va,
                    SwitchScreenUtility.ADD_OR_UPDATE_SCREEN_KEY_Va, SwitchScreenUtility.UPDATE_COURSE_VAL_Va, SwitchScreenUtility.COURSE_ID_KEY_Va, String.valueOf(course_Id_Va));
            return true;
        } else if (item.getTitle().equals(getString(R.string.menu_add_assessment))) {
            switch_Screens_Va(CUAssessmentActivity.class, SwitchScreenUtility.CAME_FROM_KEY_Va, SwitchScreenUtility.DETAILED_COURSE_ACT_Va,
                    SwitchScreenUtility.ADD_OR_UPDATE_SCREEN_KEY_Va, SwitchScreenUtility.ADD_ASSESSMENT_VAL_Va, SwitchScreenUtility.COURSE_ID_KEY_Va, String.valueOf(course_Id_Va));
            return true;
        } else if (item.getTitle().equals(getString(R.string.menu_add_note))) {
            switch_Screens_Va(CUNoteActivity.class, SwitchScreenUtility.CAME_FROM_KEY_Va, SwitchScreenUtility.DETAILED_COURSE_ACT_Va,
                    SwitchScreenUtility.ADD_OR_UPDATE_SCREEN_KEY_Va, SwitchScreenUtility.ADD_NOTE_VAL_Va, SwitchScreenUtility.COURSE_ID_KEY_Va, String.valueOf(course_Id_Va));
            return true;
        } else if (item.getTitle().equals(getString(R.string.menu_notify_for_start_date))) {
            Course course = CourseUtility.retrieveCourseFromD_ByCourseID_M(db_Course_List_Va, course_Id_Va);
            SimpleDateFormat originalFormatter = new SimpleDateFormat(DateFormatUtility.longD_Format_Va, Locale.US);
            SimpleDateFormat targetFormatter = new SimpleDateFormat(DateFormatUtility.shortD_Format_Va, Locale.US);

            Date formattedS_Date_Va = null;

            try {
                String formatted_Start_DateS_Va = targetFormatter.format(originalFormatter.parse(course.getStartDate()));
                formattedS_Date_Va = targetFormatter.parse(formatted_Start_DateS_Va);

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            Long trigger_Va = formattedS_Date_Va.getTime();
            Intent intent_Va = new Intent(DCourseActivity.this, MyReceiver.class);
            intent_Va.putExtra("key", "Class, " + course.getTitle() + ", starts today!");

            PendingIntent sender_Va = PendingIntent.getBroadcast(DCourseActivity.this, +MainActivity.num_Alert_Va, intent_Va, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarm_Manager_Va = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            Toast.makeText(DCourseActivity.this, "Alert was set!", Toast.LENGTH_SHORT).show();

            alarm_Manager_Va.setExact(AlarmManager.RTC_WAKEUP, trigger_Va, sender_Va);
            return true;
        } else if (item.getTitle().equals(getString(R.string.menu_notify_for_end_date))) {
            Course course_Va = CourseUtility.retrieveCourseFromD_ByCourseID_M(db_Course_List_Va, course_Id_Va);
            SimpleDateFormat original_Formatter_Va = new SimpleDateFormat(DateFormatUtility.longD_Format_Va, Locale.US);
            SimpleDateFormat target_Formatter_Va = new SimpleDateFormat(DateFormatUtility.shortD_Format_Va, Locale.US);

            Date formatted_End_Date_Va = null;

            try {
                String formattedE_Date_S_Va = target_Formatter_Va.format(original_Formatter_Va.parse(course_Va.getEndDate()));
                formatted_End_Date_Va = target_Formatter_Va.parse(formattedE_Date_S_Va);

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            Long trigger_Va = formatted_End_Date_Va.getTime();

            Intent intent_Va = new Intent(DCourseActivity.this, MyReceiver.class);
            intent_Va.putExtra("key", "Class, " + course_Va.getTitle() + ", ends today!");

            PendingIntent sender_Va = PendingIntent.getBroadcast(DCourseActivity.this, ++MainActivity.num_Alert_Va, intent_Va, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarm_Manager_Va = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            Toast.makeText(DCourseActivity.this, "Alert was set!", Toast.LENGTH_SHORT).show();

            alarm_Manager_Va.setExact(AlarmManager.RTC_WAKEUP, trigger_Va, sender_Va);
            return true;
        }
        return false;
    }

    void switch_Screens_Va(Class screen_Va, String key_Name_Va, String value_Va) {

        Intent intent_Va = new Intent(this, screen_Va);

        intent_Va.putExtra(key_Name_Va, value_Va);

        startActivity(intent_Va);
    }

    void switch_Screens_Va(Class screen_Va, String key_Name_Va, String value_Va, String id_Key_Va, String id_Value_Va) {

        Intent intent_Va = new Intent(this, screen_Va);

        intent_Va.putExtra(key_Name_Va, value_Va);
        intent_Va.putExtra(id_Key_Va, id_Value_Va);

        startActivity(intent_Va);
    }

    void switch_Screens_Va(Class go_To_Screen_Va, String cFrom_Screen_Key_Va, String c_F_Screen_Value_Va,
                           String add_Update_Screen_Key_Va, String a_Update_ScreenValue_Va, String id_Key_Va, String id_Value_Va) {

        Intent intent_Va = new Intent(this, go_To_Screen_Va);

        intent_Va.putExtra(cFrom_Screen_Key_Va, c_F_Screen_Value_Va);
        intent_Va.putExtra(add_Update_Screen_Key_Va, a_Update_ScreenValue_Va);
        intent_Va.putExtra(id_Key_Va, id_Value_Va);

        startActivity(intent_Va);
    }

    void setScreenDetails() throws InterruptedException {
        Course course_Va = CourseUtility.retrieveCourseFromD_ByCourseID_M(db_Course_List_Va, course_Id_Va);

        start_Date_Va.setText(course_Va.getStartDate());
        end_Date_Va.setText(course_Va.getEndDate());
        instructor_Name_Va.setText(InstructorUtility.retrieveC_FromDatabaseByInstructorID_M((ArrayList<CourseInstructor>) repository_Va.getM_All_Course_Instructors_Va(), course_Va.getInstructorID()).getName());
    }

    void setAssessment_Recycler_View_M() throws InterruptedException {
        final AssessmentAdapter term_Adapter_Va = new AssessmentAdapter(this);

        List<Assessment> assessments_For_Course_Va = AssessmentUtility.getAllAssessments_ForCourse_M((ArrayList<Assessment>) repository_Va.getM_All_Assessments_Va(), course_Id_Va);

        assessments_List_Va.setAdapter(term_Adapter_Va);
        assessments_List_Va.setLayoutManager(new LinearLayoutManager(this));
        term_Adapter_Va.setAssessments(assessments_For_Course_Va);
    }

    void setCourse_Note_Recycler_View_M() throws InterruptedException {
        final NoteAdapter term_Adapter_Va = new NoteAdapter(this);

        List<CourseNote> notes_For_Course_Va = CourseUtility.getAllN_For_Course_M( (ArrayList<CourseNote>) repository_Va.getM_All_Course_Notes_Va(), course_Id_Va);

        notes_List_Va.setAdapter(term_Adapter_Va);
        notes_List_Va.setLayoutManager(new LinearLayoutManager(this));
        term_Adapter_Va.setNotes(notes_For_Course_Va);
    }
}