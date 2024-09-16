package com.example.c196_performanceassessment_thaisribeiro.Controller.DetailsActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c196_performanceassessment_thaisribeiro.R;
import com.example.c196_performanceassessment_thaisribeiro.Controller.MainActivity;
import com.example.c196_performanceassessment_thaisribeiro.Controller.MyReceiver;
import com.example.c196_performanceassessment_thaisribeiro.Controller.CreateActivity.CUAssessmentActivity;
import com.example.c196_performanceassessment_thaisribeiro.database.Repository;
import com.example.c196_performanceassessment_thaisribeiro.entities.Assessment;
import com.example.c196_performanceassessment_thaisribeiro.helper.AssessmentUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.DateFormatUtility;
import com.example.c196_performanceassessment_thaisribeiro.helper.SwitchScreenUtility;
import com.example.c196_performanceassessment_thaisribeiro.Controller.DetailsActivity.DCourseActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
/**
 * DAssessmentActivity displays detailed information about a specific assessment.
 * <p>
 * This activity shows the details of an assessment, including its name, type, start date, end date, and additional information.
 * Users can edit the assessment or navigate back to the previous screen. It also provides options to set notifications for the start or end dates of the assessment.
 * </p>
 * <p>
 * The activity performs the following actions:
 * <ul>
 *   <li>Retrieves and displays detailed information about the assessment from the database.</li>
 *   <li>Provides options to navigate to the assessment editing screen or return to the previous screen.</li>
 *   <li>Sets alarms to notify users on the assessment's start or end date.</li>
 * </ul>
 * </p>
 * <p>
 * Components used by this activity:
 * <ul>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.database.Repository Repository} - For accessing assessment data.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.entities.Assessment Assessment} - Data model representing an assessment.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.helper.AssessmentUtility AssessmentUtility} - Provides methods for retrieving assessment details.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.helper.DateFormatUtility DateFormatUtility} - Contains date format utilities.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.helper.SwitchScreenUtility SwitchScreenUtility} - Manages screen transitions and key constants.</li>
 *   <li>{@link com.example.c196_performanceassessment_thaisribeiro.Controller.MyReceiver MyReceiver} - Broadcast receiver for handling alarms.</li>
 * </ul>
 * </p>
 */
public class DAssessmentActivity extends AppCompatActivity {
    Repository repository_Va;
    Button edit_Btn_Va;
    Button back_Btn_Va;
    TextView assessment_Name_Va;
    TextView assessment_Type_Va;
    TextView start_Date_Va;
    TextView end_Date_Va;
    TextView assessment_Info_Va;
    int assessment_Id_Va;
    ArrayList<Assessment> dbAssessment_List_Va;
    Assessment assessment_Va;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_as_sc);

        repository_Va = new Repository(getApplication());

        Intent intent = getIntent();

        try {
            dbAssessment_List_Va = (ArrayList<Assessment>) repository_Va.getM_All_Assessments_Va();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assessment_Id_Va = Integer.valueOf(intent.getStringExtra(SwitchScreenUtility.ASSESSMENT_ID_KEY_Va));
        assessment_Va = AssessmentUtility.re_AssessmentFromDatabaseByAssessmentID_M(dbAssessment_List_Va, assessment_Id_Va);

        edit_Btn_Va = findViewById(R.id.detailed_assessment_edit_btn);
        assessment_Name_Va = findViewById(R.id.detailed_assessment_name);
        assessment_Type_Va = findViewById(R.id.detailed_assessment_type);
        start_Date_Va = findViewById(R.id.detailed_assessment_start_date);
        end_Date_Va = findViewById(R.id.detailed_assessment_end_date);
        back_Btn_Va = findViewById(R.id.detailed_assessment_back_btn);
        assessment_Info_Va = findViewById(R.id.detailed_assessment_Info);

        set_Screen_Details();

        back_Btn_Va.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int courseId = assessment_Va.getCourseID();

                switchs_Screens_M(DCourseActivity.class, SwitchScreenUtility.COURSE_ID_KEY_Va, String.valueOf(courseId));
            }
        });

        edit_Btn_Va.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchs_Screens_M(CUAssessmentActivity.class, SwitchScreenUtility.CAME_FROM_KEY_Va,
                        SwitchScreenUtility.DETAILED_ASSESSMENT_ACT_Va, SwitchScreenUtility.ADD_OR_UPDATE_SCREEN_KEY_Va,
                        SwitchScreenUtility.UPDATE_ASSESSMENT_VAL_Va, SwitchScreenUtility.ASSESSMENT_ID_KEY_Va, String.valueOf(assessment_Id_Va));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detailed_assessment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getTitle() == null) {
            return false;
        }

        if (item.getTitle().equals(getString(R.string.menu_notify_for_start_date))) {
            SimpleDateFormat originalFormatter = new SimpleDateFormat(DateFormatUtility.longD_Format_Va, Locale.US);
            SimpleDateFormat targetFormatter = new SimpleDateFormat(DateFormatUtility.shortD_Format_Va, Locale.US);

            Date formatted_Start_Date_Va = null;

            try {
                String formatted_Star_Date_S_Va = targetFormatter.format(originalFormatter.parse(assessment_Va.getStartDate()));
                formatted_Start_Date_Va = targetFormatter.parse(formatted_Star_Date_S_Va);

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            Long trigger_Va = formatted_Start_Date_Va.getTime();
            Intent intent_Va = new Intent(DAssessmentActivity.this, MyReceiver.class);
            intent_Va.putExtra("key", "Assessment, " + assessment_Va.getTitle() + ", starts today!");

            PendingIntent sender_Va = PendingIntent.getBroadcast(DAssessmentActivity.this, ++MainActivity.num_Alert_Va, intent_Va, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarm_Manager_Va = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            Toast.makeText(DAssessmentActivity.this, "Alert was set!", Toast.LENGTH_SHORT).show();
            alarm_Manager_Va.setExact(AlarmManager.RTC_WAKEUP, trigger_Va, sender_Va);
            return true;
        } else if (item.getTitle().equals(getString(R.string.menu_notify_for_end_date))) {
            SimpleDateFormat original_Formatter_Va = new SimpleDateFormat(DateFormatUtility.longD_Format_Va, Locale.US);
            SimpleDateFormat target_Formatter_Va = new SimpleDateFormat(DateFormatUtility.shortD_Format_Va, Locale.US);

            Date formatted_End_Date_Va = null;

            try {
                String formatted_End_DateS_Va = target_Formatter_Va.format(original_Formatter_Va.parse(assessment_Va.getEndDate()));
                formatted_End_Date_Va = target_Formatter_Va.parse(formatted_End_DateS_Va);

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            Long trigger_Va = formatted_End_Date_Va.getTime();
            Intent intent_Va = new Intent(DAssessmentActivity.this, MyReceiver.class);
            intent_Va.putExtra("key", "Assessment, " + assessment_Va.getTitle() + ", ends today!");

            PendingIntent sender_Va = PendingIntent.getBroadcast(DAssessmentActivity.this, ++MainActivity.num_Alert_Va, intent_Va, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarm_Manager_Va = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            Toast.makeText(DAssessmentActivity.this, "Alert was set!", Toast.LENGTH_SHORT).show();
            alarm_Manager_Va.setExact(AlarmManager.RTC_WAKEUP, trigger_Va, sender_Va);
            return true;
        }
        return false;
    }

    void switchs_Screens_M(Class goTo_Screen_Va, String cameFrom_Screen_Key_Va, String cameFrom_Screen_Value_Va, String addOrUpdate_ScreenKey_Va,
                           String addOrUpdate_Screen_Value_Va, String id_Key_Va, String id_Value_Va) {

        Intent intent_Va = new Intent(this, goTo_Screen_Va);

        intent_Va.putExtra(cameFrom_Screen_Key_Va, cameFrom_Screen_Value_Va);
        intent_Va.putExtra(addOrUpdate_ScreenKey_Va, addOrUpdate_Screen_Value_Va);
        intent_Va.putExtra(id_Key_Va, id_Value_Va);

        startActivity(intent_Va);
    }

    void switchs_Screens_M(Class go_Screen_Va, String id_Key_Va, String id_Value_Va) {

        Intent intent_Va = new Intent(this, go_Screen_Va);

        intent_Va.putExtra(id_Key_Va, id_Value_Va);

        startActivity(intent_Va);
    }

    void set_Screen_Details() {
        assessment_Name_Va.setText(assessment_Va.getTitle());
        assessment_Type_Va.setText(assessment_Va.getType());
        assessment_Info_Va.setText(assessment_Va.getInformation());
        start_Date_Va.setText(assessment_Va.getStartDate());
        end_Date_Va.setText(assessment_Va.getEndDate());
    }
}