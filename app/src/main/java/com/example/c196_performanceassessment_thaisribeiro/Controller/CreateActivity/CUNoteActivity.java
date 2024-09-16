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
import com.example.c196_performanceassessment_thaisribeiro.entities.CourseNote;
import com.example.c196_performanceassessment_thaisribeiro.helper.CourseNoteUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.DialogMessagesUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.InputValidationUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.SwitchScreenUtility;

import java.util.ArrayList;
/**
 * CUNoteActivity is an Android Activity class that manages the creation and updating of CourseNote entities.
 * <p>
 * This activity allows users to either add a new note or update an existing note for a specific course. It interfaces with the database through the Repository class
 * and utilizes various helper utilities for note operations and screen transitions.
 * </p>
 * <p>
 * The activity includes the following features:
 * <ul>
 *   <li>Retrieve and display existing note data if updating an existing note.</li>
 *   <li>Validate user input to ensure note details are provided.</li>
 *   <li>Add a new note to the database or update an existing note based on user actions.</li>
 *   <li>Handle screen transitions based on user actions and data, including confirmation dialogs for canceling operations.</li>
 * </ul>
 * </p>
 * <p>
 * The activity uses the following components:
 * <ul>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.database.Repository Repository} - For interacting with the database.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.entities.CourseNote CourseNote} - Data model for course notes.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.helper.CourseNoteUtility CourseNoteUtility} - Utilities for handling course notes.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.helper.DialogMessagesUtility DialogMessagesUtility} - Provides standardized dialog messages.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.helper.InputValidationUtility InputValidationUtility} - Validates user inputs.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.helper.SwitchScreenUtility SwitchScreenUtility} - Manages screen transitions.</li>
 * </ul>
 * </p>
 */
public class CUNoteActivity extends AppCompatActivity {
    Repository repository_Va;
    int course_Id_Va;
    int note_Id_Va;
    String add_Update_Va;
    EditText note_Details_Va;
    Button save_Btn_Va;
    Button cancel_Btn_Va;
    ArrayList<CourseNote> dbNote_List_Va;
    CourseNote note_Va;
    AlertDialog.Builder builder_Va;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_update_n_sc);

        repository_Va = new Repository(getApplication());
        builder_Va = new AlertDialog.Builder(this);

        try {
            dbNote_List_Va = (ArrayList<CourseNote>) repository_Va.getM_All_Course_Notes_Va();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Intent intent = getIntent();

        String activityCameFrom = intent.getStringExtra(SwitchScreenUtility.CAME_FROM_KEY_Va);
        add_Update_Va = intent.getStringExtra(SwitchScreenUtility.ADD_OR_UPDATE_SCREEN_KEY_Va);

        note_Details_Va = findViewById(R.id.create_note_details);
        save_Btn_Va = findViewById(R.id.create_note_btn);
        cancel_Btn_Va = findViewById(R.id.create_cancel_btn);

        setNote_Id_Va(intent);
        setNote();
        setCourse_Id_Va(intent);
        setTitle(add_Update_Va);

        setScreen_Details();

        save_Btn_Va.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(InputValidationUtility.isI_FieldEmpty_M(note_Details_Va)) {
                    Toast.makeText(CUNoteActivity.this, DialogMessagesUtility.EMPTY_INPUT_FIELDS_Va, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (add_Update_Va.equals(SwitchScreenUtility.ADD_NOTE_VAL_Va)) {
                    CourseNote addNote = new CourseNote(note_Details_Va.getText().toString().trim(), course_Id_Va);

                    try {

                        repository_Va.insert(addNote);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    Toast.makeText(CUNoteActivity.this, "Created note", Toast.LENGTH_SHORT).show();
                    switchs_Screens_M(SwitchScreenUtility.getActClass_M(activityCameFrom), SwitchScreenUtility.COURSE_ID_KEY_Va,
                            String.valueOf(course_Id_Va));
                } else {
                    CourseNote updateNote = CourseNoteUtility.retrieveN_FromDatabaseByNoteID_m(dbNote_List_Va, Integer.valueOf(note_Id_Va));

                    updateNote.setNote(note_Details_Va.getText().toString().trim());

                    try {
                        repository_Va.update(updateNote);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    Toast.makeText(CUNoteActivity.this, "Updated note", Toast.LENGTH_SHORT).show();
                    switchs_Screens_M(SwitchScreenUtility.getActClass_M(activityCameFrom), SwitchScreenUtility.COURSE_NOTE_ID_KEY_Va, String.valueOf(note_Id_Va));
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

                                if (add_Update_Va.equals(SwitchScreenUtility.ADD_NOTE_VAL_Va)) {
                                    switchs_Screens_M(SwitchScreenUtility.getActClass_M(activityCameFrom), SwitchScreenUtility.COURSE_ID_KEY_Va, String.valueOf(course_Id_Va));
                                } else {
                                    switchs_Screens_M(SwitchScreenUtility.getActClass_M(activityCameFrom), SwitchScreenUtility.COURSE_NOTE_ID_KEY_Va, String.valueOf(note_Id_Va));
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

    void switchs_Screens_M(Class screen_Nm_Va, String id_Key_Va, String id_Value_Va) {

        Intent intent_Va = new Intent(this, screen_Nm_Va);

        intent_Va.putExtra(id_Key_Va, id_Value_Va);

        startActivity(intent_Va);
    }

    void setNote_Id_Va(Intent intent_Va) {
        if (add_Update_Va.equals(SwitchScreenUtility.ADD_NOTE_VAL_Va)) {
            return;
        }
        note_Id_Va = Integer.valueOf(intent_Va.getStringExtra(SwitchScreenUtility.COURSE_NOTE_ID_KEY_Va));
    }

    void setNote() {
        note_Va = CourseNoteUtility.retrieveN_FromDatabaseByNoteID_m(dbNote_List_Va, note_Id_Va);
    }

    void setCourse_Id_Va(Intent intent_Va) {

        if (add_Update_Va.equals(SwitchScreenUtility.ADD_NOTE_VAL_Va)) {
            course_Id_Va = Integer.valueOf(intent_Va.getStringExtra(SwitchScreenUtility.COURSE_ID_KEY_Va));
        } else {
            course_Id_Va = CourseNoteUtility.retrieveN_FromDatabaseByNoteID_m(dbNote_List_Va, note_Id_Va).getCourseID();
        }
    }

    void setScreen_Details() {
        if (add_Update_Va.equals(SwitchScreenUtility.ADD_NOTE_VAL_Va)) {
            return;
        }

        CourseNote note_Va = CourseNoteUtility.retrieveN_FromDatabaseByNoteID_m(dbNote_List_Va, note_Id_Va);

        note_Details_Va.setText(note_Va.getNote());
    }
}