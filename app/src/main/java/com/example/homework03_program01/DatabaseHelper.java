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

    //constructor
    public DatabaseHelper(Context c)
    {
        super(c, database_name, null, 2);
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
        db.execSQL("INSERT INTO " + studentTableName + "(username, fname, lname, email, age, gpa) VALUES ('" + student.getUsername() + "', '" + student.getFname() + "', '" + student.getLname() + "', '" + student.getEmail() + "', '" + student.getAge() + "', '" + student.getGpa() + "');");
        db.close();
    }

    public void addMajor(MajorObj major)
    {
        //add major to database
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO " + majorTableName + "(majorName, majorPrefix) VALUES ('" + major.getMajorName() + "', '" + major.getMajorPrefix() + "');");
        db.close();
    }

    private Cursor getAllStudents()
    {
        //part 1 of getting all the entries from the student table
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + studentTableName, null);
    }
    private Cursor getAllMajors()
    {
        //part 1 of getting all the entries from the major table
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + majorTableName, null);
    }

    public ArrayList<StudentObj> getStudents()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<StudentObj> studentList = new ArrayList<>();
        //part 2 of getting all the students form the students table
        Cursor cursor = getAllStudents();
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
                //student.setMajorId(Integer.parseInt(cursor.getString((int) cursor.getColumnIndex("major"))));
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

    public ArrayList<MajorObj> getMajors()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MajorObj> majorList = new ArrayList<>();
        Cursor cursor = getAllMajors();
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
        String checkCommand = "SELECT count(id) FROM " + majorTableName + " WHERE majorId = '" + id + "';";
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
}
