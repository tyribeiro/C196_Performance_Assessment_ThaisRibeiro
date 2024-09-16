package com.example.c196_performanceassessment_thaisribeiro.database;

import android.app.Application;

import com.example.c196_performanceassessment_thaisribeiro.dao.AssessmentDAO;
import com.example.c196_performanceassessment_thaisribeiro.dao.CourseDAO;
import com.example.c196_performanceassessment_thaisribeiro.dao.CourseInstructorDAO;
import com.example.c196_performanceassessment_thaisribeiro.dao.CourseNoteDAO;
import com.example.c196_performanceassessment_thaisribeiro.dao.TermDAO;
import com.example.c196_performanceassessment_thaisribeiro.entities.Assessment;
import com.example.c196_performanceassessment_thaisribeiro.entities.Course;
import com.example.c196_performanceassessment_thaisribeiro.entities.CourseInstructor;
import com.example.c196_performanceassessment_thaisribeiro.entities.CourseNote;
import com.example.c196_performanceassessment_thaisribeiro.entities.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * Repository class provides an abstraction layer over the database operations.
 * It handles data operations and provides a clean API to the rest of the application.
 */
public class Repository {
    private AssessmentDAO m_Assessment_DAO_Va;
    private CourseDAO m_Course_DAO_Va;
    private CourseInstructorDAO m_Course_Instructor_DAO_Va;
    private CourseNoteDAO m_Course_Note_DAO_Va;
    private TermDAO m_Term_DAO_Va;

    private List<Assessment> m_All_Assessments_Va;
    private List<Course> m_All_Courses_Va;
    private List<CourseInstructor> m_All_Course_Instructors_Va;
    private List<CourseNote> m_All_Course_Notes_Va;
    private List<Term> m_All_Terms_Va;
    private final int TIME_SECONDS_Va = 500;
    private static int NUMBER_OF_TH_Va = 4;
    static final ExecutorService db_Executor_Va = Executors.newFixedThreadPool(NUMBER_OF_TH_Va);

    public Repository(Application application) {
        com.example.c196_performanceassessment_thaisribeiro.database.DatabaseBuilder db_Va = com.example.c196_performanceassessment_thaisribeiro.database.DatabaseBuilder.getDatabase(application);
        m_Assessment_DAO_Va = db_Va.assessmentDAO();
        m_Course_DAO_Va = db_Va.courseDAO();
        m_Course_Instructor_DAO_Va = db_Va.courseInstructorDAO();
        m_Course_Note_DAO_Va = db_Va.courseNoteDAO();
        m_Term_DAO_Va = db_Va.termDAO();
    }

    public List<Assessment> getM_All_Assessments_Va() throws InterruptedException {
        db_Executor_Va.execute(() -> {
            m_All_Assessments_Va = m_Assessment_DAO_Va.getAllAssessments();
        });

        Thread.sleep(TIME_SECONDS_Va);

        return m_All_Assessments_Va;
    }

    public List<Course> getM_All_Courses_Va() throws InterruptedException {

        db_Executor_Va.execute(() -> {
            m_All_Courses_Va = m_Course_DAO_Va.getAllCourses();
        });

        Thread.sleep(TIME_SECONDS_Va);

        return m_All_Courses_Va;
    }

    public List<CourseInstructor> getM_All_Course_Instructors_Va() throws InterruptedException {
        db_Executor_Va.execute(() -> {
            m_All_Course_Instructors_Va = m_Course_Instructor_DAO_Va.getAllCourseInstructors();
        });

        Thread.sleep(TIME_SECONDS_Va);

        return m_All_Course_Instructors_Va;
    }

    public List<CourseNote> getM_All_Course_Notes_Va() throws InterruptedException {
        db_Executor_Va.execute(() -> {
            m_All_Course_Notes_Va = m_Course_Note_DAO_Va.getAllCourseNotes();
        });

        Thread.sleep(TIME_SECONDS_Va);

        return m_All_Course_Notes_Va;
    }

    public List<Term> getM_All_Terms_Va() throws InterruptedException {
        db_Executor_Va.execute(() -> {
            m_All_Terms_Va = m_Term_DAO_Va.getAllTerms();
        });

        Thread.sleep(TIME_SECONDS_Va);

        return m_All_Terms_Va;
    }

    public void insert(Term term_Va) throws InterruptedException {
        db_Executor_Va.execute(() -> {
            m_Term_DAO_Va.insert(term_Va);
        });

        Thread.sleep(TIME_SECONDS_Va);
    }

    public void insert(Course course_Va) throws InterruptedException {
        db_Executor_Va.execute(() -> {
            m_Course_DAO_Va.insert(course_Va);
        });

        Thread.sleep(TIME_SECONDS_Va);
    }

    public void insert(Assessment assessment_Va) throws InterruptedException {
        db_Executor_Va.execute(() -> {
            m_Assessment_DAO_Va.insert(assessment_Va);
        });

        Thread.sleep(TIME_SECONDS_Va);
    }

    public void insert(CourseNote course_Note_Va) throws InterruptedException {
        db_Executor_Va.execute(() -> {
            m_Course_Note_DAO_Va.insert(course_Note_Va);
        });

        Thread.sleep(TIME_SECONDS_Va);
    }

    public void insert(CourseInstructor course_Instructor_Va) throws InterruptedException {
        db_Executor_Va.execute(() -> {
            m_Course_Instructor_DAO_Va.insert(course_Instructor_Va);
        });

        Thread.sleep(TIME_SECONDS_Va);
    }

    public void update(Term term_Va) throws InterruptedException {
        db_Executor_Va.execute(() -> {
            m_Term_DAO_Va.update(term_Va);
        });

        Thread.sleep(TIME_SECONDS_Va);
    }

    public void update(Course course_Va) throws InterruptedException {
        db_Executor_Va.execute(() -> {
            m_Course_DAO_Va.update(course_Va);
        });

        Thread.sleep(TIME_SECONDS_Va);
    }

    public void update(Assessment assessment_Va) throws InterruptedException {
        db_Executor_Va.execute(() -> {
            m_Assessment_DAO_Va.update(assessment_Va);
        });

        Thread.sleep(TIME_SECONDS_Va);
    }

    public void update(CourseNote course_Note_Va) throws InterruptedException {
        db_Executor_Va.execute(() -> {
            m_Course_Note_DAO_Va.update(course_Note_Va);
        });

        Thread.sleep(TIME_SECONDS_Va);
    }

    public void update(CourseInstructor course_Instructor_Va) throws InterruptedException {
        db_Executor_Va.execute(() -> {
            m_Course_Instructor_DAO_Va.update(course_Instructor_Va);
        });

        Thread.sleep(TIME_SECONDS_Va);
    }

    public void delete(Term term_Va) throws InterruptedException {
        db_Executor_Va.execute(() -> {
            m_Term_DAO_Va.delete(term_Va);
        });

        Thread.sleep(TIME_SECONDS_Va);
    }

    public void delete(Assessment assessment_Va) throws InterruptedException {
        db_Executor_Va.execute(() -> {
            m_Assessment_DAO_Va.delete(assessment_Va);
        });

        Thread.sleep(TIME_SECONDS_Va);
    }

    public void delete(Course course_Va) throws InterruptedException {
        db_Executor_Va.execute(() -> {
            m_Course_DAO_Va.delete(course_Va);
        });

        Thread.sleep(TIME_SECONDS_Va);
    }

    public void delete(CourseNote course_Note_Va) throws InterruptedException {
        db_Executor_Va.execute(() -> {
            m_Course_Note_DAO_Va.delete(course_Note_Va);
        });

        Thread.sleep(TIME_SECONDS_Va);
    }

    public void delete(CourseInstructor course_Instructor_Va) throws InterruptedException {
        db_Executor_Va.execute(() -> {
            m_Course_Instructor_DAO_Va.delete(course_Instructor_Va);
        });

        Thread.sleep(TIME_SECONDS_Va);
    }
}