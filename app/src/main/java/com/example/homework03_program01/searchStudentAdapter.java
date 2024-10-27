//This is the adapter for the search screen's list view
package com.example.homework03_program01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class searchStudentAdapter extends BaseAdapter
{
    private DataHelper dh = new DataHelper();
    private ArrayList<StudentObj> searchResults = new ArrayList<StudentObj>();
    private ArrayList<MajorObj> majorList = new ArrayList<MajorObj>();
    Context context;

    public searchStudentAdapter(Context c)
    {
        context = c;
        searchResults = dh.getSearchList();
        majorList = dh.getMajorlist();
    }

    @Override
    public int getCount()
    {
        return searchResults.size();
    }

    @Override
    public Object getItem(int i)
    {
        return searchResults.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        if(view == null)
        {
            LayoutInflater mInflator = (LayoutInflater) context.getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE);
            view = mInflator.inflate(R.layout.searchlayout, null);
        }

        TextView fname = view.findViewById(R.id.sl_tv_fname);
        TextView lname = view.findViewById(R.id.sl_tv_lname);
        TextView uname = view.findViewById(R.id.sl_tv_uname);
        TextView email = view.findViewById(R.id.sl_tv_email);
        TextView age = view.findViewById(R.id.sl_tv_age);
        TextView gpa = view.findViewById(R.id.sl_tv_gpa);
        TextView major = view.findViewById(R.id.sl_tv_major);

        StudentObj student = searchResults.get(i);

        fname.setText("First name: " + student.getFname());
        lname.setText("Last name: " + student.getLname());
        uname.setText("username: " + student.getUsername());
        email.setText("Email: " + student.getEmail());
        age.setText("Age: " + String.valueOf(student.getAge()));
        gpa.setText("Gpa: " + String.valueOf(student.getGpa()));
        for (MajorObj majorobj : majorList)
        {
            if(majorobj.getMajorId() == student.getMajorid())
            {
                major.setText("Major: " + majorobj.getMajorName());
            }
        }

        return view;
    }
}
