package com.example.homework03_program01;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper
{
    //database name and table names
    private static final String database_name = "database.db";
    private static final String studentTableName = "Students";
    private static final String majorTableName = "Majors";
    private DataHelper dh = new DataHelper();

    //constructor
    public DatabaseHelper(Context c)
    {
        super(c, database_name, null, 4);
    }


    @Override
    public void onConfigure(SQLiteDatabase db)
    {
        //enable foreign keys
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //create the major table
        db.execSQL("CREATE TABLE " + majorTableName + " (majorId INTEGER PRIMARY KEY AUTOINCREMENT, majorName varchar(18), majorPrefix varchar(9))");

        //crate the student table
        db.execSQL("CREATE TABLE " + studentTableName + " (id INTEGER PRIMARY KEY AUTOINCREMENT, username varchar(20) NOT NULL UNIQUE, fname varchar(20)," +
                   " lname varchar(20), email varchar(40), age INTEGER, gpa REAL, major INTEGER, FOREIGN KEY (major) REFERENCES " + majorTableName + " (majorId));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        //drop the tables
        db.execSQL("DROP TABLE IF EXISTS " + studentTableName + ";");
        db.execSQL("DROP TABLE IF EXISTS " + majorTableName + ";");

        onCreate(db);
    }

    public String getStudentTableName()
    {
        return studentTableName;
    }

    public String getMajorTableName()
    {
        return majorTableName;
    }

    public void addStudent(StudentObj student)
    {
        //add student to database
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO " + studentTableName + "(username, fname, lname, email, age, gpa, major) VALUES ('" + student.getUsername() + "', '" + student.getFname() + "', '" + student.getLname() + "', '" + student.getEmail() + "', '" + student.getAge() + "', '" + student.getGpa() + "', '" + student.getMajorId() + "'" + ");");
        db.close();
        dh.setStudentList(getAllStudents());
    }

    public void addMajor(MajorObj major)
    {
        //add major to database
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO " + majorTableName + "(majorName, majorPrefix) VALUES ('" + major.getMajorName() + "', '" + major.getMajorPrefix() + "');");
        db.close();
        dh.setMajorList(getAllMajors());
    }

    private Cursor getStudentCursor()
    {
        //part 1 of getting all the entries from the student table
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + studentTableName, null);
    }
    private Cursor getMajorCursor()
    {
        //part 1 of getting all the entries from the major table
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + majorTableName, null);
    }

    private ArrayList<StudentObj> getAllStudents()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<StudentObj> studentList = new ArrayList<>();
        //part 2 of getting all the students form the students table
        Cursor cursor = getStudentCursor();
        if(cursor != null && cursor.getCount() > 0)
        {
            while (cursor.moveToNext())
            {
                StudentObj student = new StudentObj();
                student.setFname(cursor.getString((int) cursor.getColumnIndex("fname")));
                student.setLname(cursor.getString((int) cursor.getColumnIndex("lname")));
                student.setEmail(cursor.getString((int) cursor.getColumnIndex("email")));
                student.setUsername(cursor.getString((int) cursor.getColumnIndex("username")));
                student.setAge(Integer.parseInt(cursor.getString((int) cursor.getColumnIndex("age"))));
                student.setGpa(Float.parseFloat(cursor.getString((int) cursor.getColumnIndex("gpa"))));
                student.setMajorId(Integer.parseInt(cursor.getString((int) cursor.getColumnIndex("major"))));
                student.setUserId(Integer.parseInt(cursor.getString((int) cursor.getColumnIndex("id"))));
                studentList.add(student);
            }
        }
        if(cursor != null)
        {
            cursor.close();
        }
        if(db.isOpen())
        {
            db.close();
        }

        return studentList;
    }

    private ArrayList<MajorObj> getAllMajors()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MajorObj> majorList = new ArrayList<>();
        Cursor cursor = getMajorCursor();
        if(cursor != null && cursor.getCount() > 0)
        {
            while (cursor.moveToNext())
            {
                MajorObj major = new MajorObj();
                major.setMajorName(cursor.getString((int) cursor.getColumnIndex("majorName")));
                major.setMajorPrefix(cursor.getString((int) cursor.getColumnIndex("majorPrefix")));
                majorList.add(major);
            }
        }

        if(cursor!= null)
        {
            cursor.close();
        }
        if(db.isOpen())
        {
            db.close();
        }

        return majorList;
    }

    //The getStudent is most likely redundant and unneeded
    public StudentObj getStudent(int id)
    {
        StudentObj student = new StudentObj();
        if(validStudentId(id))
        {
            String selectCommand = "SELECT FROM " + studentTableName + " WHERE id = '" + id + "';";

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectCommand, null);

            if(cursor != null && cursor.moveToFirst())
            {
                student.setFname(cursor.getString((int) cursor.getColumnIndex("fname")));
                student.setLname(cursor.getString((int) cursor.getColumnIndex("lname")));
                student.setEmail(cursor.getString((int) cursor.getColumnIndex("email")));
                student.setUsername(cursor.getString((int) cursor.getColumnIndex("username")));
                student.setAge(Integer.parseInt(cursor.getString((int) cursor.getColumnIndex("age"))));
                student.setGpa(Float.parseFloat(cursor.getString((int) cursor.getColumnIndex("gpa"))));
                student.setMajorId(Integer.parseInt(cursor.getString((int) cursor.getColumnIndex("major"))));
                student.setUserId(Integer.parseInt(cursor.getString((int) cursor.getColumnIndex("id"))));
            }
            if(cursor != null)
            {
                cursor.close();
            }

            db.close();
        }

        return student;
    }

    //getMajor is most likely redundant and unneeded
    public MajorObj getMajor(int id)
    {
        MajorObj major = new MajorObj();

        if(validMajorId(id))
        {
            String selectCommand = "SELECT FROM " + majorTableName + " WHERE majorId = '" + id + "';";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectCommand, null);

            if(cursor != null && cursor.moveToFirst())
            {
                major.setMajorPrefix(cursor.getString((int) cursor.getColumnIndex("majorPrefix")));
                major.setMajorName(cursor.getString((int) cursor.getColumnIndex("majorName")));
            }
            if(db.isOpen())
            {
                db.close();
            }
            if(cursor != null)
            {
                cursor.close();
            }
        }



        return major;
    }

    private boolean validMajorId(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String checkCommand = "SELECT count(majorId) FROM " + majorTableName + " WHERE majorId = '" + id + "';";
        Cursor cursor = db.rawQuery(checkCommand, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        db.close();
        if(count != 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean validStudentId(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String checkCommand = "SELECT count(id) FROM " + studentTableName + " WHERE id = '" + id + "';";
        Cursor cursor = db.rawQuery(checkCommand, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        db.close();
        if(count != 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void getEntries()
    {
        dh.setMajorList(getAllMajors());
        dh.setStudentList(getAllStudents());
    }
}
