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
import com.example.c196_performanceassessment_thaisribeiro.entities.Term;
import com.example.c196_performanceassessment_thaisribeiro.helper.DateValidationUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.DialogMessagesUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.InputValidationUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.SwitchScreenUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.TermUtility;

import java.util.ArrayList;
/**
 * CUTermActivity is an Android Activity class responsible for creating and updating Term entities.
 * <p>
 * This activity facilitates the addition of new terms or modification of existing terms within the application. It interacts with the database through the Repository class
 * and uses various helper utilities to validate user input and manage term data.
 * </p>
 * <p>
 * The activity provides the following functionalities:
 * <ul>
 *   <li>Retrieve and display term details if updating an existing term.</li>
 *   <li>Validate input fields for term name and start/end dates, ensuring correctness and proper formatting.</li>
 *   <li>Add a new term to the database or update an existing term based on user actions.</li>
 *   <li>Handle screen transitions and confirmations for canceling operations.</li>
 * </ul>
 * </p>
 * <p>
 * The activity utilizes the following components:
 * <ul>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.database.Repository Repository} - For database interactions.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.entities.Term Term} - Data model representing a term.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.helper.DateValidationUtility DateValidationUtility} - Provides methods for validating date inputs.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.helper.DialogMessagesUtility DialogMessagesUtility} - Supplies standardized dialog messages for user interactions.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.helper.InputValidationUtility InputValidationUtility} - Validates general user inputs.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.helper.SwitchScreenUtility SwitchScreenUtility} - Manages screen transitions and key constants.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.helper.TermUtility TermUtility} - Utilities for handling term-specific operations.</li>
 * </ul>
 * </p>
 */
public class CUTermActivity extends AppCompatActivity {
    Repository repository_Va;
    Button save_Btn_Va;
    Button cancel_Btn_Va;
    EditText term_Name_Va;
    EditText start_Date_Va;
    EditText end_Date_Va;
    ArrayList<Term> dbTerm_List_Va;
    Term term_Va;
    String add_Update_Va;
    int term_Id_Va;
    AlertDialog.Builder builder_Va;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_update_t_sc);

        repository_Va = new Repository(getApplication());
        builder_Va = new AlertDialog.Builder(this);

        try {
            dbTerm_List_Va = (ArrayList<Term>) repository_Va.getM_All_Terms_Va();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Intent intent_Va = getIntent();

        String activity_Came_From_Va = intent_Va.getStringExtra(SwitchScreenUtility.CAME_FROM_KEY_Va);
        add_Update_Va = intent_Va.getStringExtra(SwitchScreenUtility.ADD_OR_UPDATE_SCREEN_KEY_Va);

        setTerm_Id_Va(intent_Va);
        setTerm();

        setTitle(add_Update_Va);

        save_Btn_Va = findViewById(R.id.create_term_btn);
        term_Name_Va = findViewById(R.id.create_term_name);
        start_Date_Va = findViewById(R.id.create_term_start_date);
        end_Date_Va = findViewById(R.id.create_term_end_date);
        cancel_Btn_Va = findViewById(R.id.create_term_cancel_btn);

        setScreen_Details();

        save_Btn_Va.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (InputValidationUtility.isI_FieldEmpty_M(term_Name_Va) || InputValidationUtility.isI_FieldEmpty_M(start_Date_Va) ||
                        InputValidationUtility.isI_FieldEmpty_M(end_Date_Va)) {

                    Toast.makeText(CUTermActivity.this, DialogMessagesUtility.EMPTY_INPUT_FIELDS_Va, Toast.LENGTH_SHORT).show();
                    return;
                } else if (!DateValidationUtility.isDCorrectLength_M(start_Date_Va.getText().toString()) || !DateValidationUtility.isDCorrectLength_M(end_Date_Va.getText().toString())) {
                    Toast.makeText(CUTermActivity.this, DialogMessagesUtility.DATE_IS_INCORRECT_LEN_Va, Toast.LENGTH_SHORT).show();
                    return;
                } else if (!DateValidationUtility.isD_ANumber_M(start_Date_Va.getText().toString()) || !DateValidationUtility.isD_ANumber_M(end_Date_Va.getText().toString())) {
                    Toast.makeText(CUTermActivity.this, DialogMessagesUtility.INVALID_INPUT_FOR_DATE_VA, Toast.LENGTH_SHORT).show();
                    return;
                } else if (!DateValidationUtility.isD_FormattedCorrect_M(start_Date_Va.getText().toString()) ||
                        !DateValidationUtility.isD_FormattedCorrect_M(end_Date_Va.getText().toString())) {

                    Toast.makeText(CUTermActivity.this, DialogMessagesUtility.DATE_IS_FORMATTED_INCORRECTLY_VA, Toast.LENGTH_SHORT).show();
                    return;
                } else if (!DateValidationUtility.isStartD_BeforeEndDate_M(start_Date_Va.getText().toString(), end_Date_Va.getText().toString())) {

                    Toast.makeText(CUTermActivity.this, DialogMessagesUtility.START_DATE_IS_AFTER_END_DATE_Va, Toast.LENGTH_SHORT).show();
                    return;
                }

                Term save_Term_Va = getTerm_For_Add_Update_M();

                if (TermUtility.doesTermNameExistInDb_M(dbTerm_List_Va, save_Term_Va)) {
                    Toast.makeText(CUTermActivity.this, DialogMessagesUtility.TERM_ALREADY_EX_Va, Toast.LENGTH_SHORT).show();
                    return;
                } else if (TermUtility.doesTermDateOverlapWithTermInDb_M(dbTerm_List_Va, save_Term_Va)) {

                    Toast.makeText(CUTermActivity.this, DialogMessagesUtility.TERM_DATES_OVERLAP_WITH_ANOTHER_TERMS_D_Va, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (add_Update_Va.equals(SwitchScreenUtility.ADD_TERM_VALUE_Va)) {
                    try {

                        repository_Va.insert(save_Term_Va);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    Toast.makeText(CUTermActivity.this, "Created " + save_Term_Va.getTitle(), Toast.LENGTH_SHORT).show();
                    switchs_Screens(SwitchScreenUtility.getActClass_M(activity_Came_From_Va));
                } else {
                    try {

                        repository_Va.update(save_Term_Va);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    Toast.makeText(CUTermActivity.this, "Updated " + save_Term_Va.getTitle(), Toast.LENGTH_SHORT).show();
                    switchs_Screens(SwitchScreenUtility.getActClass_M(activity_Came_From_Va), SwitchScreenUtility.TERM_ID_KEY_Va, String.valueOf(term_Id_Va));
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

                                if (add_Update_Va.equals(SwitchScreenUtility.ADD_TERM_VALUE_Va)) {
                                    switchs_Screens(SwitchScreenUtility.getActClass_M(activity_Came_From_Va));
                                } else {
                                    switchs_Screens(SwitchScreenUtility.getActClass_M(activity_Came_From_Va), SwitchScreenUtility.TERM_ID_KEY_Va, String.valueOf(term_Id_Va));
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

    void switchs_Screens(Class screenName_Va) {

        Intent intent_Va = new Intent(this, screenName_Va);

        startActivity(intent_Va);
    }

    void switchs_Screens(Class screen_Name_Va, String term_Id_Key_Va, String term_Id_Value_Va) {

        Intent intent_Va = new Intent(this, screen_Name_Va);

        intent_Va.putExtra(term_Id_Key_Va, term_Id_Value_Va);

        startActivity(intent_Va);
    }

    void setScreen_Details() {
        if (add_Update_Va.equals(SwitchScreenUtility.ADD_TERM_VALUE_Va)) {
            return;
        }
        term_Name_Va.setText(term_Va.getTitle());
        start_Date_Va.setText(term_Va.getStartDate());
        end_Date_Va.setText(term_Va.getEndDate());
    }

    void setTerm_Id_Va(Intent intent) {
        if (add_Update_Va.equals(SwitchScreenUtility.ADD_TERM_VALUE_Va)) {
            return;
        }
        term_Id_Va = Integer.valueOf(intent.getStringExtra(SwitchScreenUtility.TERM_ID_KEY_Va));
    }

    void setTerm() {
        term_Va = TermUtility.retrieveTerm_From_Db_By_Term_ID_M(dbTerm_List_Va, term_Id_Va);
    }

    Term getTerm_For_Add_Update_M() {
        if (add_Update_Va.equals(SwitchScreenUtility.ADD_TERM_VALUE_Va)) {
            return new Term(term_Name_Va.getText().toString().trim(), start_Date_Va.getText().toString(), end_Date_Va.getText().toString());
        }
        Term update_Term_Va = term_Va;

        update_Term_Va.updateFields_M(term_Name_Va, start_Date_Va, end_Date_Va);
        return update_Term_Va;
    }
}