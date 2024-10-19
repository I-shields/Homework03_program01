package com.example.homework03_program01;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class mainViewAdapter extends BaseAdapter
{

    private DataHelper dh = new DataHelper();
    private ArrayList<StudentObj> studentList;
    Context context;

    public mainViewAdapter(Context c)
    {
        context = c;
        studentList = dh.getStudentList();
    }

    @Override
    public int getCount() {
        return studentList.size();
    }

    @Override
    public Object getItem(int i) {
        return studentList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null)
        {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.mainviewlayout, null);
        }

        //find gui elements
        TextView fname = view.findViewById(R.id.mvl_tv_fname);
        TextView lname = view.findViewById(R.id.mvl_tv_lname);
        TextView uname = view.findViewById(R.id.mvl_tv_uname);

        StudentObj stobj = studentList.get(i);

        fname.setText(stobj.getFname());
        lname.setText(stobj.getLname());
        uname.setText(stobj.getUsername());

        return view;
    }
}
