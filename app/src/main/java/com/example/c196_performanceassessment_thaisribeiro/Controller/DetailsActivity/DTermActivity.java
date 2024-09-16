package com.example.c196_performanceassessment_thaisribeiro.Controller.DetailsActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c196_performanceassessment_thaisribeiro.R;
import com.example.c196_performanceassessment_thaisribeiro.Controller.ViewAdapter.CourseAdapter;
import com.example.c196_performanceassessment_thaisribeiro.Controller.CreateActivity.CUCourseActivity;
import com.example.c196_performanceassessment_thaisribeiro.Controller.CreateActivity.CUTermActivity;
import com.example.c196_performanceassessment_thaisribeiro.database.Repository;
import com.example.c196_performanceassessment_thaisribeiro.entities.Assessment;
import com.example.c196_performanceassessment_thaisribeiro.entities.Course;
import com.example.c196_performanceassessment_thaisribeiro.entities.CourseNote;
import com.example.c196_performanceassessment_thaisribeiro.entities.Term;
import com.example.c196_performanceassessment_thaisribeiro.helper.CourseUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.DialogMessagesUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.SelectedListItemUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.SwitchScreenUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.TermUtility;
import com.example.c196_performanceassessment_thaisribeiro.Controller.DetailsActivity.DCourseActivity;

import java.util.ArrayList;
import java.util.List;
/**
 * DTermActivity displays detailed information about a specific term and provides options to manage associated courses.
 * <p>
 * This activity shows the details of a selected term, including its start and end dates. It allows users to add new courses to the term,
 * view or edit existing courses, and delete selected courses. The activity also includes a list of courses associated with the term.
 * </p>
 * <p>
 * The activity provides the following functionalities:
 * <ul>
 *   <li>Displays the term's start and end dates.</li>
 *   <li>Allows users to add a new course to the term by navigating to {@link CUCourseActivity}.</li>
 *   <li>Enables viewing and editing of selected courses by navigating to {@link DCourseActivity}.</li>
 *   <li>Provides functionality to delete a selected course, along with its associated notes and assessments.</li>
 *   <li>Displays a list of all courses associated with the term using a {@link RecyclerView} and {@link CourseAdapter}.</li>
 * </ul>
 * </p>
 * <p>
 * Components used by this activity:
 * <ul>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.database.Repository Repository} - For accessing term, course, assessment, and note data.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.entities.Term Term} - Data model representing a term.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.entities.Course Course} - Data model representing a course.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.entities.Assessment Assessment} - Data model representing an assessment.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.entities.CourseNote CourseNote} - Data model representing a course note.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.Controller.CreateActivity.CUCourseActivity CUCourseActivity} - Activity for creating or updating courses.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.Controller.CreateActivity.CUTermActivity CUTermActivity} - Activity for creating or updating terms.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.Controller.ViewAdapter.CourseAdapter CourseAdapter} - Adapter for displaying courses in a {@link RecyclerView}.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.helper.CourseUtility CourseUtility} - Provides methods for retrieving and managing courses.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.helper.TermUtility TermUtility} - Provides methods for retrieving and managing terms.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.helper.DialogMessagesUtility DialogMessagesUtility} - Provides utility methods for dialog messages.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.helper.SelectedListItemUtility SelectedListItemUtility} - Utility for managing selected list items.</li>
 *   <li>{@link androidx.appcompat.app.AlertDialog AlertDialog} - For displaying confirmation dialogs.</li>
 * </ul>
 * </p>
 */
public class DTermActivity extends AppCompatActivity {
    Repository repository_Va;
    int termId_Va;
    Button add_Course_Btn_Va;
    Button view_Course_Btn_Va;
    Button delete_Course_Btn_Va;
    RecyclerView class_List_Va;
    TextView start_Date_Va;
    TextView end_Date_Va;
    ArrayList<Term> dbTerm_List_Va;
    AlertDialog.Builder builder_Va;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_t_sc);

        repository_Va = new Repository(getApplication());
        builder_Va = new AlertDialog.Builder(this);

        try {
            dbTerm_List_Va = (ArrayList<Term>) repository_Va.getM_All_Terms_Va();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Intent intent_Va = getIntent();
        termId_Va = Integer.valueOf(intent_Va.getStringExtra(SwitchScreenUtility.TERM_ID_KEY_Va));

        add_Course_Btn_Va = findViewById(R.id.detailed_term_add_class_btn);
        view_Course_Btn_Va = findViewById(R.id.detailed_term_view_class_btn);
        delete_Course_Btn_Va = findViewById(R.id.detailed_term_delete_class_btn);
        class_List_Va = findViewById(R.id.detailed_term_classes_list);
        start_Date_Va = findViewById(R.id.detailed_term_start_date);
        end_Date_Va = findViewById(R.id.detailed_term_end_date);

        setScreenDetails(termId_Va);

        try {
            setCourse_Recycler_View_M(termId_Va);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        setTitle(TermUtility.retrieveTerm_From_Db_By_Term_ID_M(dbTerm_List_Va, termId_Va).getTitle());

        add_Course_Btn_Va.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchs_Screen_M(CUCourseActivity.class, SwitchScreenUtility.CAME_FROM_KEY_Va, SwitchScreenUtility.DETAILED_TERM_ACT_Va,
                        SwitchScreenUtility.ADD_OR_UPDATE_SCREEN_KEY_Va, SwitchScreenUtility.ADD_COURSE_VAL_Va, SwitchScreenUtility.TERM_ID_KEY_Va, String.valueOf(termId_Va));
            }
        });

        view_Course_Btn_Va.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SelectedListItemUtility.getSelected_Course_Va() == null) {
                    Toast.makeText(DTermActivity.this, DialogMessagesUtility.NEED_TO_SELECT_A_CO_Va + " view", Toast.LENGTH_SHORT).show();
                    return;
                }

                String course_Id_Va = String.valueOf(SelectedListItemUtility.getSelected_Course_Va().getCourseID());
                SelectedListItemUtility.setSelected_Course_Va(null);

                switchs_Screen_M(DCourseActivity.class, SwitchScreenUtility.CAME_FROM_KEY_Va, SwitchScreenUtility.DETAILED_TERM_ACT_Va,
                        SwitchScreenUtility.ADD_OR_UPDATE_SCREEN_KEY_Va, SwitchScreenUtility.UPDATE_COURSE_VAL_Va, SwitchScreenUtility.COURSE_ID_KEY_Va, course_Id_Va);
            }
        });

        delete_Course_Btn_Va.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SelectedListItemUtility.getSelected_Course_Va() == null) {
                    Toast.makeText(DTermActivity.this, DialogMessagesUtility.NEED_TO_SELECT_A_CO_Va + " delete", Toast.LENGTH_SHORT).show();
                    return;
                }

                builder_Va.setTitle(DialogMessagesUtility.CONFIRMATION_Va)
                        .setMessage(DialogMessagesUtility.DELETE_CONFIRMATION_MESSAGE_Va + SelectedListItemUtility.getSelected_Course_Va().getTitle() + "? " +
                                "Note: Deleting the course will delete all of its notes and assessments")
                        .setCancelable(true)
                        .setPositiveButton(DialogMessagesUtility.YES_Va, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Course course_Va = SelectedListItemUtility.getSelected_Course_Va();

                                SelectedListItemUtility.setSelected_Course_Va(null);

                                try {

                                    ArrayList<Assessment> db_Assessment_List_Va = (ArrayList<Assessment>) repository_Va.getM_All_Assessments_Va();
                                    ArrayList<CourseNote> db_Note_List_Va = (ArrayList<CourseNote>) repository_Va.getM_All_Course_Notes_Va();

                                    delete_Assessments_For_Course_M(db_Assessment_List_Va, course_Va.getCourseID());
                                    delete_Notes_For_Course_M(db_Note_List_Va, course_Va.getCourseID());

                                    Toast.makeText(DTermActivity.this, "Deleted " + course_Va.getTitle(), Toast.LENGTH_SHORT).show();
                                    repository_Va.delete(course_Va);
                                    setCourse_Recycler_View_M(termId_Va);
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
        getMenuInflater().inflate(R.menu.menu_detailed_term, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getTitle() == null) {
            return false;
        }

        if (item.getTitle().equals(getString(R.string.menu_update_term))) {
            switchs_Screen_M(CUTermActivity.class, SwitchScreenUtility.CAME_FROM_KEY_Va, SwitchScreenUtility.DETAILED_TERM_ACT_Va,
                    SwitchScreenUtility.ADD_OR_UPDATE_SCREEN_KEY_Va, SwitchScreenUtility.UPDATE_TERM_VAL_Va, SwitchScreenUtility.TERM_ID_KEY_Va, String.valueOf(termId_Va));
            return true;
        }
        return false;
    }

    void delete_Assessments_For_Course_M(ArrayList<Assessment> db_Assessment_List_Va, int course_Id_Va) throws InterruptedException {
        if (db_Assessment_List_Va.size() == 0) {
            return;
        }

        for (Assessment db_Assessment_Va : db_Assessment_List_Va) {
            if (db_Assessment_Va.getCourseID() == course_Id_Va) {
                repository_Va.delete(db_Assessment_Va);
            }
        }
    }

    void delete_Notes_For_Course_M(ArrayList<CourseNote> db_Note_List_Va, int course_Id_Va) throws InterruptedException {
        if (db_Note_List_Va.size() == 0) {
            return;
        }

        for (CourseNote dbNote : db_Note_List_Va) {
            if (dbNote.getCourseID() == course_Id_Va) {
                repository_Va.delete(dbNote);
            }
        }
    }

    void switchs_Screen_M(Class screen_Va, String came_F_Screen_Key_Va, String came_F_Screen_Value_Va, String add_Update_Screen_Key_Va,
                          String add_Update_Screen_Value_Va, String id_Key_Va, String id_Value_Va) {

        Intent intent_Va = new Intent(this, screen_Va);

        intent_Va.putExtra(came_F_Screen_Key_Va, came_F_Screen_Value_Va);
        intent_Va.putExtra(add_Update_Screen_Key_Va, add_Update_Screen_Value_Va);
        intent_Va.putExtra(id_Key_Va, id_Value_Va);

        startActivity(intent_Va);
    }

    void setScreenDetails(int term_Id_Va) {
        Term term_Va = TermUtility.retrieveTerm_From_Db_By_Term_ID_M(dbTerm_List_Va, term_Id_Va);

        start_Date_Va.setText(term_Va.getStartDate());
        end_Date_Va.setText(term_Va.getEndDate());
    }

    void setCourse_Recycler_View_M(int term_Id_Va) throws InterruptedException {
        List<Course> all_Courses_For_Term_Va = CourseUtility.getAllCoursesForT_M((ArrayList<Course>) repository_Va.getM_All_Courses_Va(), term_Id_Va);

        final CourseAdapter course_Adapter_Va = new CourseAdapter(this);

        class_List_Va.setAdapter(course_Adapter_Va);
        class_List_Va.setLayoutManager(new LinearLayoutManager(this));
        course_Adapter_Va.setCourses(all_Courses_For_Term_Va);
    }
}