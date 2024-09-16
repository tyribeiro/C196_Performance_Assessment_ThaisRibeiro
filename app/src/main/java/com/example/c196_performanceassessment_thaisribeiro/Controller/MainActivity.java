package com.example.c196_performanceassessment_thaisribeiro.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.c196_performanceassessment_thaisribeiro.Controller.ListActivity.LOfInstructorsActivity;
import com.example.c196_performanceassessment_thaisribeiro.R;
import com.example.c196_performanceassessment_thaisribeiro.Controller.ListActivity.LOfTermsActivity;
import com.example.c196_performanceassessment_thaisribeiro.helper.SelectedListItemUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.SwitchScreenUtility;
/**
 * The main activity that provides navigation to other activities.
 * This activity includes buttons to view all terms and all instructors.
 */
public class MainActivity extends AppCompatActivity {
    Button view_All_Terms_Btn_Va;
    Button view_All_Instructors_Btn_Va;
    public static int num_Alert_Va;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view_All_Terms_Btn_Va = findViewById(R.id.home_view_all_terms_btn);
        view_All_Instructors_Btn_Va = findViewById(R.id.view_All_Instructors_Btn);

        SelectedListItemUtility.setSelected_Term_Va(null);
        SelectedListItemUtility.setSelected_Instructor_Va(null);

        view_All_Terms_Btn_Va.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchs_Screen_Va(LOfTermsActivity.class, SwitchScreenUtility.CAME_FROM_KEY_Va, SwitchScreenUtility.HOME_SCREEN_ACT_Va);
            }
        });
        view_All_Instructors_Btn_Va.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchs_Screen_Va(LOfInstructorsActivity.class, SwitchScreenUtility.CAME_FROM_KEY_Va, SwitchScreenUtility.HOME_SCREEN_ACT_Va);
            }
        });

    }

    /**
     * Starts a new activity and passes the specified key-value pair as an extra.

     *   The class of the activity to start.
     *    The key for the extra data.
     *    The value for the extra data.
     */
    void switchs_Screen_Va(Class screenN_Va, String key_Name_Va, String value_Va) {

        Intent intent_Va = new Intent(this, screenN_Va);

        intent_Va.putExtra(key_Name_Va, value_Va);

        startActivity(intent_Va);
    }
}