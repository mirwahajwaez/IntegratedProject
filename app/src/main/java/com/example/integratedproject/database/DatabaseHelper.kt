package com.example.integratedproject.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.ArrayList

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_STUDENTS)
        db.execSQL(CREATE_TABLE_ADMIN)
        db.execSQL(CREATE_TABLE_EXAMS)
        db.execSQL(CREATE_TABLE_QUESTIONS)
        db.execSQL(CREATE_TABLE_STUDENTSEXAMS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DELETE_TABLE_STUDENTSEXAMS)
        db.execSQL(DELETE_TABLE_QUESTIONS)
        db.execSQL(DELETE_TABLE_EXAMS)
        db.execSQL(DELETE_TABLE_STUDENTS)
        db.execSQL(DELETE_TABLE_ADMIN)
        onCreate(db)
    }

    // loop through all rows and adding to Students list
    fun allStudents(): ArrayList<String> {
        val studentsArrayList = ArrayList<String>()
        var snummer: String
        val db = this.readableDatabase

        val projection = arrayOf(STUDENT_ID, SNUMMER)
        val selection = null
        val selectionArgs = null
        val sortOrder = "${SNUMMER} DESC"

        val cursor = db.query(
            TABLE_STUDENTS,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )

        if (cursor.moveToFirst()) {
            do {
                snummer = cursor.getString(cursor.getColumnIndexOrThrow(SNUMMER))
                studentsArrayList.add(snummer)
            } while (cursor.moveToNext())
        }
        cursor.close()

        return studentsArrayList
    }

    fun allExams(): Cursor {
        val db = this.readableDatabase
        return db.rawQuery(SELECT_EXAMS, null)
    }

    fun allAdmins(): Array<Array<String>> {
        val db = this.readableDatabase
        var admins = arrayOf<Array<String>>()
        //var matrix: Array<Array<String>>
        var email: String
        var password: String

        val projection = arrayOf(ADMIN_ID, EMAIL, PASSWORD)
        val selection = null
        val selectionArgs = null
        val sortOrder = null

        val cursor = db.query(
            TABLE_ADMINS,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )

        if (cursor.moveToFirst()) {
            do {
                var array = arrayOf<String>()
                email = cursor.getString(cursor.getColumnIndexOrThrow(EMAIL))
                password = cursor.getString(cursor.getColumnIndexOrThrow(PASSWORD))
                array += email
                array += password
                admins += array
            } while (cursor.moveToNext())
        }
        cursor.close()


        return admins
    }

    fun allStudentExams(examId: Int): Cursor {
        val db = this.readableDatabase

        val projection = arrayOf(STUDENT_ID, EXAM_ID, LATITUDE, LONGITUDE, POINTS, ANSWERS)
        val selection = "$EXAM_ID = ?"
        val selectionArgs = arrayOf(examId.toString())
        val sortOrder = "$STUDENT_ID DESC"

        return db.query(
            TABLE_STUDENTSEXAMS,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )
    }


    fun allQuestions(examId: Int): Cursor {
        val db = this.readableDatabase

        val projection = arrayOf(QUESTION_ID, EXAM_ID, QUESTION_NR, TYPE, SOLUTION)
        val selection = "$EXAM_ID = ?"
        val selectionArgs = arrayOf(examId.toString())
        val sortOrder = "$QUESTION_NR DESC"

        return db.query(
            TABLE_QUESTIONS,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )
    }

    fun addStudent(student: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(SNUMMER, student)

        return db.insert(TABLE_STUDENTS, null, values)
    }

    fun addExam(exam: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NAME, exam)

        return db.insert(TABLE_EXAMS, null, values)
    }

    fun addAdmin(email: String, password: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(EMAIL, email)
        values.put(PASSWORD, password)

        return db.insert(TABLE_ADMINS, null, values)
    }

    fun addStudentExam(studentId: Int, examId: Int, longitude: Double, latitude: Double, points: String, answers: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(STUDENT_ID, studentId)
        values.put(EXAM_ID, examId)
        values.put(LONGITUDE, longitude)
        values.put(LATITUDE, latitude)
        values.put(POINTS, points)
        values.put(ANSWERS, answers)

        return db.insert(TABLE_STUDENTSEXAMS, null, values)
    }

    fun addQuestions(questiontNr: Int, type: Int, solution: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(QUESTION_NR, questiontNr)
        values.put(TYPE, type)
        values.put(SOLUTION, solution)

        return db.insert(TABLE_QUESTIONS, null, values)
    }

    companion object {

        var DATABASE_NAME = "integrated_project"
        private val DATABASE_VERSION = 1
        private val TABLE_STUDENTS = "students"
        private val TABLE_STUDENTSEXAMS = "students_exams"
        private val TABLE_EXAMS = "exams"
        private val TABLE_ADMINS = "admins"
        private val TABLE_QUESTIONS = "questions"

        private val STUDENT_ID = "student_id"
        private val SNUMMER = "snummer"

        private val CREATE_TABLE_STUDENTS = ("CREATE TABLE "
                + TABLE_STUDENTS + "(" + STUDENT_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + SNUMMER + " TEXT );")

        private val DELETE_TABLE_STUDENTS = "DROP TABLE IF EXISTS $TABLE_STUDENTS"

        private val SELECT_STUDENTS = "SELECT * FROM $TABLE_STUDENTS"

        private val EXAM_ID = "exam_id"
        private val NAME = "name"

        private val CREATE_TABLE_EXAMS = ("CREATE TABLE "
                + TABLE_EXAMS + "(" + EXAM_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME +  " TEXT);")

        private val DELETE_TABLE_EXAMS = "DROP TABLE IF EXISTS $TABLE_EXAMS"

        private val SELECT_EXAMS = "SELECT * FROM $TABLE_EXAMS"


        private val LONGITUDE = "long"
        private val LATITUDE = "lat"
        private val POINTS = "points"
        private val ANSWERS = "answers"

        private val CREATE_TABLE_STUDENTSEXAMS = ("CREATE TABLE " + TABLE_STUDENTSEXAMS + "(" + STUDENT_ID + " INTEGER NOT NULL REFERENCES " +
                TABLE_STUDENTS + "(" + STUDENT_ID + ")," + EXAM_ID + " INTEGER NOT NULL REFERENCES " +
                TABLE_EXAMS + "(" + EXAM_ID + "), " + LATITUDE + " DECIMAL(8,6), " + LONGITUDE + " DECIMAL(9,6), " +
                POINTS + " TEXT, " + ANSWERS + " TEXT, PRIMARY KEY(" + STUDENT_ID + ", " + EXAM_ID + "));")

        private val DELETE_TABLE_STUDENTSEXAMS = "DROP TABLE IF EXISTS $TABLE_STUDENTSEXAMS"

        private val QUESTION_ID = "question_id"
        private val QUESTION_NR = "number"
        private val TYPE = "type"
        private val SOLUTION = "solution"

        private val CREATE_TABLE_QUESTIONS = ("CREATE TABLE "
                + TABLE_QUESTIONS + "(" + QUESTION_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + EXAM_ID + " INTEGER NOT NULL REFERENCES " + TABLE_EXAMS + "(" + EXAM_ID + "),"
                + QUESTION_NR + " INTEGER NOT NULL,"
                + TYPE + " SMALLINT NOT NULL,"
                + SOLUTION + "INTEGER NOT NULL);")

        private val DELETE_TABLE_QUESTIONS = "DROP TABLE IF EXISTS $TABLE_QUESTIONS"

        private val ADMIN_ID = "admin_id"
        private val EMAIL = "email"
        private val PASSWORD = "password"

        private val CREATE_TABLE_ADMIN = ("CREATE TABLE "
                + TABLE_ADMINS + "(" + ADMIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + EMAIL + " TEXT NOT NULL,"
                + PASSWORD + " TEXT NOT NULL);")

        private val DELETE_TABLE_ADMIN = "DROP TABLE IF EXISTS $TABLE_ADMINS"



    }
}