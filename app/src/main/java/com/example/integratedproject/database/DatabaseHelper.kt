package com.example.integratedproject.database

import android.content.ContentValues
import android.content.Context
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

    fun placeholder() {
        val db = this.writableDatabase
        db.execSQL(DELETE_TABLE_STUDENTSEXAMS)
        db.execSQL(CREATE_TABLE_STUDENTSEXAMS)

    }


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

    fun getStudent(studentId: String): ArrayList<String> {
        val studentsArrayList = ArrayList<String>()
        var snummer: String
        val db = this.readableDatabase


        val projection = arrayOf(STUDENT_ID, SNUMMER)
        val selection = "$STUDENT_ID = ?"
        val selectionArgs = arrayOf(studentId)
        val sortOrder = "$STUDENT_ID DESC"

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

    fun allExams(): Array<Array<String>> {
        var examsArrayList = arrayOf<Array<String>>()
        var name: String
        var examId: Int
        val db = this.readableDatabase

        val projection = arrayOf(EXAM_ID, NAME)
        val selection = null
        val selectionArgs = null
        val sortOrder = "${NAME} DESC"

        val cursor = db.query(
            TABLE_EXAMS,
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
                name = cursor.getString(cursor.getColumnIndexOrThrow(NAME))
                examId = cursor.getInt(cursor.getColumnIndexOrThrow(EXAM_ID))
                array += examId.toString()
                array += name
                examsArrayList += array
            } while (cursor.moveToNext())
        }
        cursor.close()

        return examsArrayList
    }

    fun allAdmins(): Array<Array<String>> {
        val db = this.readableDatabase
        var admins = arrayOf<Array<String>>()
        var email: String
        var password: String
        var adminId: Int

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
                adminId = cursor.getInt(cursor.getColumnIndexOrThrow(ADMIN_ID))
                array += email
                array += password
                array += adminId.toString()
                admins += array
            } while (cursor.moveToNext())
        }
        cursor.close()


        return admins
    }

    fun getOneAdmin(adminIdd: String): ArrayList<String> {
        val adminsArrayList = ArrayList<String>()
        var email: String
        var password: String
        var adminId: Int
        val db = this.readableDatabase


        val projection = arrayOf(ADMIN_ID, EMAIL, PASSWORD)
        val selection = "$ADMIN_ID = ?"
        val selectionArgs = arrayOf(adminIdd)
        val sortOrder = "$ADMIN_ID DESC"

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
                email = cursor.getString(cursor.getColumnIndexOrThrow(EMAIL))
                password = cursor.getString(cursor.getColumnIndexOrThrow(PASSWORD))
                adminId = cursor.getInt(cursor.getColumnIndexOrThrow(ADMIN_ID))
                adminsArrayList.add(email)
                adminsArrayList.add(password)
                adminsArrayList.add(adminId.toString())
            } while (cursor.moveToNext())
        }
        cursor.close()

        return adminsArrayList
    }

    fun allStudentExams(examIdToFind: Int): Array<Array<String>> {
        val db = this.readableDatabase
        var studentExams = arrayOf<Array<String>>()
        var snummer: String
        var examId: Int
        var latitude: Double
        var longitude: Double
        var counter: String
        var answers: String

        val projection = arrayOf(SNUMMER, EXAM_ID, LATITUDE, LONGITUDE, COUNTER, ANSWERS)
        val selection = "$EXAM_ID = ?"
        val selectionArgs = arrayOf(examIdToFind.toString())
        val sortOrder = "$SNUMMER DESC"

        val cursor = db.query(
            TABLE_STUDENTSEXAMS,
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
                snummer = cursor.getString(cursor.getColumnIndexOrThrow(SNUMMER))
                examId = cursor.getInt(cursor.getColumnIndexOrThrow(EXAM_ID))
                latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(LATITUDE))
                longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(LONGITUDE))
                counter = cursor.getString(cursor.getColumnIndexOrThrow(COUNTER))
                answers = cursor.getString(cursor.getColumnIndexOrThrow(ANSWERS))
                array += snummer.toString()
                array += examId.toString()
                array += latitude.toString()
                array += longitude.toString()
                array += counter.toString()
                array += answers
                studentExams += array
            } while (cursor.moveToNext())
        }
        cursor.close()


        return studentExams
    }

    fun getAllStudentExams(): Array<Array<String>> {
        val db = this.readableDatabase
        var studentExams = arrayOf<Array<String>>()
        var snummer: String
        var examId: Int
        var latitude: Double
        var longitude: Double
        var counter: String
        var answers: String

        val projection = arrayOf(SNUMMER, EXAM_ID, LATITUDE, LONGITUDE, COUNTER, ANSWERS)
        val selection = null
        val selectionArgs = null
        val sortOrder = null

        val cursor = db.query(
            TABLE_STUDENTSEXAMS,
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
                snummer = cursor.getString(cursor.getColumnIndexOrThrow(SNUMMER))
                examId = cursor.getInt(cursor.getColumnIndexOrThrow(EXAM_ID))
                latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(LATITUDE))
                longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(LONGITUDE))
                counter = cursor.getString(cursor.getColumnIndexOrThrow(COUNTER))
                answers = cursor.getString(cursor.getColumnIndexOrThrow(ANSWERS))
                array += snummer.toString()
                array += examId.toString()
                array += latitude.toString()
                array += longitude.toString()
                array += counter.toString()
                array += answers
                studentExams += array
            } while (cursor.moveToNext())
        }
        cursor.close()


        return studentExams
    }


    fun allQuestions(examIdToFind: String?): Array<Array<String>> {
        val db = this.readableDatabase
        var questions = arrayOf<Array<String>>()
        var questionId: Int
        var examId: Int
        var type: Int
        var solution: String

        val projection = arrayOf(QUESTION_ID, EXAM_ID, TYPE, SOLUTION)
        val selection = "$EXAM_ID = ?"
        val selectionArgs = arrayOf(examIdToFind.toString())
        val sortOrder = "$QUESTION_ID DESC"

        val cursor = db.query(
            TABLE_QUESTIONS,
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
                questionId = cursor.getInt(cursor.getColumnIndexOrThrow(QUESTION_ID))
                examId = cursor.getInt(cursor.getColumnIndexOrThrow(EXAM_ID))
                type = cursor.getInt(cursor.getColumnIndexOrThrow(TYPE))
                solution = cursor.getString(cursor.getColumnIndexOrThrow(SOLUTION))
                array += questionId.toString()
                array += examId.toString()
                array += type.toString()
                array += solution
                questions += array
            } while (cursor.moveToNext())
        }
        cursor.close()


        return questions
    }

    fun getAllQuestions(): Array<Array<String>> {
        val db = this.readableDatabase
        var questions = arrayOf<Array<String>>()
        var questionId: Int
        var examId: Int
        var type: Int
        var solution: String

        val projection = arrayOf(QUESTION_ID, EXAM_ID, TYPE, SOLUTION)
        val selection = null
        val selectionArgs = null
        val sortOrder = null

        val cursor = db.query(
            TABLE_QUESTIONS,
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
                questionId = cursor.getInt(cursor.getColumnIndexOrThrow(QUESTION_ID))
                examId = cursor.getInt(cursor.getColumnIndexOrThrow(EXAM_ID))
                type = cursor.getInt(cursor.getColumnIndexOrThrow(TYPE))
                solution = cursor.getString(cursor.getColumnIndexOrThrow(SOLUTION))
                array += questionId.toString()
                array += examId.toString()
                array += type.toString()
                array += solution
                questions += array
            } while (cursor.moveToNext())
        }
        cursor.close()


        return questions
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

    fun addStudentExam(snummer: String, examId: String, longitude: String, latitude: String, counter: Int, answers: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(SNUMMER, snummer)
        values.put(EXAM_ID, examId)
        values.put(LONGITUDE, longitude)
        values.put(LATITUDE, latitude)
        values.put(COUNTER, counter)
        values.put(ANSWERS, answers)

        return db.insert(TABLE_STUDENTSEXAMS, null, values)
    }

    fun addQuestions(examId: String, type: Int, solution: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(EXAM_ID, examId)
        values.put(TYPE, type)
        values.put(SOLUTION, solution)

        return db.insert(TABLE_QUESTIONS, null, values)
    }

    fun updateAdmin(oldPassword: String, newPassword: String): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(PASSWORD, newPassword)
        }

        val selection = "$PASSWORD LIKE ?"
        val selectionArgs = arrayOf(oldPassword)

        return db.update(
            TABLE_ADMINS,
            values,
            selection,
            selectionArgs
        )
    }

    fun updateQuestion(oldQuestionString: String, newQuestionString: String): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(SOLUTION, newQuestionString)
        }

        val selection = "$SOLUTION LIKE ?"
        val selectionArgs = arrayOf(oldQuestionString)

        return db.update(
            TABLE_QUESTIONS,
            values,
            selection,
            selectionArgs
        )
    }

    fun updateStudentExams(oldSolutionString: String, newSolutionString: String): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(ANSWERS, newSolutionString)
        }

        val selection = "$ANSWERS LIKE ?"
        val selectionArgs = arrayOf(oldSolutionString)

        return db.update(
            TABLE_STUDENTSEXAMS,
            values,
            selection,
            selectionArgs
        )
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


        private val EXAM_ID = "exam_id"
        private val NAME = "name"

        private val CREATE_TABLE_EXAMS = ("CREATE TABLE "
                + TABLE_EXAMS + "(" + EXAM_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME +  " TEXT);")

        private val DELETE_TABLE_EXAMS = "DROP TABLE IF EXISTS $TABLE_EXAMS"


        private val LONGITUDE = "long"
        private val LATITUDE = "lat"
        private val COUNTER = "counter"
        private val ANSWERS = "answers"

        private val CREATE_TABLE_STUDENTSEXAMS = ("CREATE TABLE " + TABLE_STUDENTSEXAMS + "(" + SNUMMER + " TEXT REFERENCES " +
                TABLE_STUDENTS + "(" + SNUMMER + ")," + EXAM_ID + " INTEGER NOT NULL REFERENCES " +
                TABLE_EXAMS + "(" + EXAM_ID + "), " + LATITUDE + " DECIMAL(8,6), " + LONGITUDE + " DECIMAL(9,6), " +
                COUNTER + " TEXT, " + ANSWERS + " TEXT, PRIMARY KEY(" + SNUMMER + ", " + EXAM_ID + "));")

        private val DELETE_TABLE_STUDENTSEXAMS = "DROP TABLE IF EXISTS $TABLE_STUDENTSEXAMS"

        private val QUESTION_ID = "question_id"
        private val TYPE = "type"
        private val SOLUTION = "solution"

        private val CREATE_TABLE_QUESTIONS = ("CREATE TABLE "
                + TABLE_QUESTIONS + "(" + QUESTION_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + EXAM_ID + " INTEGER NOT NULL REFERENCES " + TABLE_EXAMS + "(" + EXAM_ID + "),"
                + TYPE + " SMALLINT NOT NULL,"
                + SOLUTION + " TEXT NOT NULL);")

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