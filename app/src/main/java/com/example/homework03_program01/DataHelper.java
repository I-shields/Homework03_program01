package com.example.homework03_program01;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DataHelper
{
    private static ArrayList<StudentObj> studentList = new ArrayList<StudentObj>();
    private static ArrayList<MajorObj> majorList = new ArrayList<MajorObj>();
    private static ArrayList<StudentObj> searchList = new ArrayList<StudentObj>();
    private static StudentObj tempStudent;

    public ArrayList<StudentObj> getSearchList()
    {
        return searchList;
    }

    public void setSearchList(ArrayList<StudentObj> searchList)
    {
        DataHelper.searchList = searchList;
    }

    public void addSearchResult(StudentObj student)
    {
        searchList.add(student);
    }


    public void clearSearchResults()
    {
        searchList.clear();
    }

    public DataHelper()
    {

    }

    public void setTempStudent(StudentObj student)
    {
        Log.d("DATAHELPER", "ADDED STUDENT");
        tempStudent = student;
    }

    public StudentObj getTempStudent()
    {
        return tempStudent;
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

    public void setStudentList(ArrayList<StudentObj> stuList)
    {
        studentList = stuList;
    }

    public void setMajorList(ArrayList<MajorObj> mList)
    {
        majorList = mList;
    }
}
