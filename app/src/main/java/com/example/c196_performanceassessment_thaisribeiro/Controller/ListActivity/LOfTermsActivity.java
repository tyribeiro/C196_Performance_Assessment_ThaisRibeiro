package com.example.c196_performanceassessment_thaisribeiro.Controller.ListActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.c196_performanceassessment_thaisribeiro.R;
import com.example.c196_performanceassessment_thaisribeiro.Controller.ViewAdapter.TermAdapter;
import com.example.c196_performanceassessment_thaisribeiro.Controller.CreateActivity.CUTermActivity;
import com.example.c196_performanceassessment_thaisribeiro.Controller.DetailsActivity.DTermActivity;
import com.example.c196_performanceassessment_thaisribeiro.database.Repository;
import com.example.c196_performanceassessment_thaisribeiro.entities.Course;
import com.example.c196_performanceassessment_thaisribeiro.entities.Term;
import com.example.c196_performanceassessment_thaisribeiro.helper.DialogMessagesUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.SelectedListItemUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.SwitchScreenUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity class for managing and displaying a list of terms.
 * <p>
 * This activity provides functionalities to add, view, and delete terms. It utilizes a RecyclerView
 * to display a list of terms and handles interactions such as adding new terms, viewing details of
 * selected terms, and deleting selected terms. It also shows appropriate messages and confirmations
 * to the user based on their actions.
 * </p>
 */
public class LOfTermsActivity extends AppCompatActivity {
    Repository repository_Va;
    Button addBtn_Va;
    Button view_Btn_Va;
    Button delete_Btn_Va;
    RecyclerView terms_List_Va;
    AlertDialog.Builder builder_Va;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_t_sc);

        repository_Va = new Repository(getApplication());
        builder_Va = new AlertDialog.Builder(this);

        addBtn_Va = findViewById(R.id.list_of_terms_add_term_btn);
        view_Btn_Va = findViewById(R.id.list_of_terms_view_term_btn);
        delete_Btn_Va = findViewById(R.id.list_of_terms_delete_term_btn);
        terms_List_Va = findViewById(R.id.list_of_terms_list);

        SelectedListItemUtility.setSelected_Course_Va(null);

        setTermRecyclerView();

        addBtn_Va.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBtn_Switch_Screen_M(CUTermActivity.class, SwitchScreenUtility.CAME_FROM_KEY_Va,
                        SwitchScreenUtility.LIST_OF_TERMS_ACT_Va, SwitchScreenUtility.ADD_OR_UPDATE_SCREEN_KEY_Va, SwitchScreenUtility.ADD_TERM_VALUE_Va);
            }
        });

        delete_Btn_Va.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SelectedListItemUtility.getSelected_Term_Va() == null) {
                    Toast.makeText(LOfTermsActivity.this, DialogMessagesUtility.NEED_TO_SELECT_A_TERM_Va + " delete", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {

                    if (d_Term_Have_Courses_M((ArrayList<Course>) repository_Va.getM_All_Courses_Va(), SelectedListItemUtility.getSelected_Term_Va().getTermID())) {
                        Toast.makeText(LOfTermsActivity.this, "You cannot delete the term because it has courses. Remove the courses first then delete the term.", Toast.LENGTH_LONG).show();
                        return;
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                builder_Va.setTitle(DialogMessagesUtility.CONFIRMATION_Va)
                        .setMessage(DialogMessagesUtility.DELETE_CONFIRMATION_MESSAGE_Va + SelectedListItemUtility.getSelected_Term_Va().getTitle() + "?")
                        .setCancelable(true)
                        .setPositiveButton(DialogMessagesUtility.YES_Va, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try {
                                    Toast.makeText(LOfTermsActivity.this, "Deleted " + SelectedListItemUtility.getSelected_Term_Va().getTitle(), Toast.LENGTH_SHORT).show();
                                    repository_Va.delete(SelectedListItemUtility.getSelected_Term_Va());
                                    SelectedListItemUtility.setSelected_Term_Va(null);
                                    setTermRecyclerView();
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

        view_Btn_Va.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SelectedListItemUtility.getSelected_Term_Va() == null) {
                    Toast.makeText(LOfTermsActivity.this, DialogMessagesUtility.NEED_TO_SELECT_A_TERM_Va + " view", Toast.LENGTH_SHORT).show();
                    return;
                }

                int term_Id_Va = SelectedListItemUtility.getSelected_Term_Va().getTermID();
                SelectedListItemUtility.setSelected_Term_Va(null);

                switchs_Screen_M(DTermActivity.class, SwitchScreenUtility.CAME_FROM_KEY_Va, SwitchScreenUtility.LIST_OF_TERMS_ACT_Va,
                        SwitchScreenUtility.TERM_ID_KEY_Va, String.valueOf(term_Id_Va));
            }
        });
    }

    /**
     * Checks if the term has associated courses that prevent its deletion.
     *
     * @param dbCourse_List_Va the list of courses
     * @param term_Id_Va the ID of the term to check
     * @return true if the term has associated courses, false otherwise
     */
    boolean d_Term_Have_Courses_M(ArrayList<Course> dbCourse_List_Va, int term_Id_Va) {
        if (dbCourse_List_Va.size() == 0) {
            return false;
        }

        for (Course dbCourse : dbCourse_List_Va) {
            if (dbCourse.getTermID() == term_Id_Va) {
                return true;
            }
        }
        return false;
    }
    /**
     * Switches to a different screen for adding or updating a term.
     *
     * @param screen_Name_Va the class of the activity to switch to
     * @param key_Name_Va the key for the intent extra
     * @param value_Va the value for the intent extra
     * @param add_Update_ScreenKey_Va the key for the add/update intent extra
     * @param ad_UpdateScreenValue_Va the value for the add/update intent extra
     */
    void addBtn_Switch_Screen_M(Class screen_Name_Va, String key_Name_Va, String value_Va, String add_Update_ScreenKey_Va, String ad_UpdateScreenValue_Va) {

        Intent intent_Va = new Intent(this, screen_Name_Va);

        intent_Va.putExtra(key_Name_Va, value_Va);
        intent_Va.putExtra(add_Update_ScreenKey_Va, ad_UpdateScreenValue_Va);

        startActivity(intent_Va);
    }

    /**
     * Switches to a different screen to view the details of a term.
     *
     * @param screenName_Va the class of the activity to switch to
     * @param cKey_Va the key for the intent extra
     * @param clNValue_Va the value for the intent extra
     * @param id_Key_Va the key for the term ID intent extra
     * @param id_Value_Va the value for the term ID intent extra
     */
    void switchs_Screen_M(Class screenName_Va, String cKey_Va , String clNValue_Va, String id_Key_Va, String id_Value_Va) {

        Intent intent_Va = new Intent(this, screenName_Va);

        intent_Va.putExtra(cKey_Va, clNValue_Va);
        intent_Va.putExtra(id_Key_Va, id_Value_Va);

        startActivity(intent_Va);
    }
    /**
     * Sets up the RecyclerView to display the list of terms.
     * <p>
     * This method initializes the RecyclerView with a TermAdapter and sets the layout manager.
     * It also fetches the list of terms from the repository and updates the adapter.
     * </p>
     */
    void setTermRecyclerView() {
        List<Term> all_Terms_Va;

        try {
            all_Terms_Va = repository_Va.getM_All_Terms_Va();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        final TermAdapter term_Adapter_Va = new TermAdapter(this);

        terms_List_Va.setAdapter(term_Adapter_Va);
        terms_List_Va.setLayoutManager(new LinearLayoutManager(this));
        term_Adapter_Va.setTerms(all_Terms_Va);
    }
}