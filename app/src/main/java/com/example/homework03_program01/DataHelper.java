package com.example.homework03_program01;

import java.util.ArrayList;
import java.util.List;

public class DataHelper
{
    private static ArrayList<StudentObj> studentList = new ArrayList<StudentObj>();
    private static ArrayList<MajorObj> majorList = new ArrayList<MajorObj>();
    public StudentObj tempStudent;

    public DataHelper()
    {

    }

    public ArrayList<StudentObj> getStudentList()
    {
        return studentList;
    }

    public ArrayList<MajorObj> getMajorlist()
    {
        return majorList;
    }

    public StudentObj getStudentObj(int i)
    {
        if(i < studentList.size())
        {
            return studentList.get(i);
        }
        else
        {
            return null;
        }
    }

    public MajorObj getMajorObj(int i)
    {
        if(i < majorList.size())
        {
            return majorList.get(i);
        }
        else
        {
            return null;
        }
    }

    public void addStudent(StudentObj student)
    {
        studentList.add(student);
    }

    public void addMajor(MajorObj major)
    {
        majorList.add(major);
    }

}
