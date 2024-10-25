package com.example.homework03_program01;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        super(c, database_name, null, 9);
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
        db.execSQL("CREATE TABLE " + majorTableName + " (majorId  INTEGER PRIMARY KEY AUTOINCREMENT, majorName varchar(18), majorPrefix varchar(9))");

        //crate the student table
        db.execSQL("CREATE TABLE " + studentTableName + " (username varchar(20) NOT NULL UNIQUE PRIMARY KEY, fname varchar(20)," +
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
        db.execSQL("INSERT INTO " + studentTableName + "(username, fname, lname, email, age, gpa, major) VALUES ('" + student.getUsername() + "', '" + student.getFname() + "', '" + student.getLname() + "', '" + student.getEmail() + "', '" + student.getAge() + "', '" + student.getGpa() + "', '" + student.getMajorid() + "' " + ");");
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
                student.setMajorid(Integer.parseInt(cursor.getString((int) cursor.getColumnIndex("major"))));
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

    public ArrayList<MajorObj> getAllMajors()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MajorObj> majorList = new ArrayList<>();
        //part 2 of getting all the majors in the major table
        Cursor cursor = getMajorCursor();
        if(cursor != null && cursor.getCount() > 0)
        {
            while (cursor.moveToNext())
            {
                MajorObj major = new MajorObj();
                major.setMajorId(Integer.parseInt(cursor.getString((int) cursor.getColumnIndex("majorId"))));
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

    private boolean validStudentId(String uid)
    {
        //checks to see if the username is valid
        SQLiteDatabase db = this.getReadableDatabase();
        String checkCommand = "SELECT count(username) FROM " + studentTableName + " WHERE username = '" + uid + "';";
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
        //update the list in the "DataHelper" class
        dh.setMajorList(getAllMajors());
        dh.setStudentList(getAllStudents());
    }

    public void updateStudent(StudentObj student)
    {
        //update a student if the username is valid
        if(validStudentId(student.getUsername()))
        {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(" UPDATE " + studentTableName + " SET fname = '" + student.getFname() + "', lname = '" + student.getLname() + "', email = '" + student.getEmail() + "', username = '" + student.getUsername() + "', age = '" + student.getAge() + "', gpa = '" + student.getGpa() + "', major = '" + student.getMajorid() + "' WHERE username = '" + student.getUsername() + "';");
            db.close();
            dh.setStudentList(getAllStudents());
        }
    }

    public void deleteStudent(String uname)
    {
        //delete a student if the username is valid
        if(validStudentId(uname))
        {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("DELETE FROM " + studentTableName + " WHERE username = '" + uname + "';");
            db.close();
            dh.setStudentList(getAllStudents());
        }
    }
}
