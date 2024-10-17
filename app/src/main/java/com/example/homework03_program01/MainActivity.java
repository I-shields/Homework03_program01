package com.example.homework03_program01;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.Console;

public class MainActivity extends AppCompatActivity {

    TextView ma_tv_title;
    Button ma_btn_addStudent;
    Button ma_btn_searchStudent;
    ListView ma_lv_studentList;
    Intent addStudentScreen;
    Intent searchStudentsScreen;
    mainViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ma_tv_title             = findViewById(R.id.am_tv_title);
        ma_btn_addStudent       = findViewById(R.id.am_btn_addStudent);
        ma_btn_searchStudent    = findViewById(R.id.am_btn_search);
        addStudentScreen        = new Intent(this, addStudent.class);
        searchStudentsScreen    = new Intent(this, searchStudents.class);
        ma_lv_studentList       = findViewById(R.id.am_lv_studentList);


        adapter = new mainViewAdapter(this);
        ma_lv_studentList.setAdapter(adapter);
        openAddStudent();
        //openSearchStudents();
        adapter.notifyDataSetChanged();
    }

    private void openAddStudent()
    {
        ma_btn_addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(addStudentScreen);

            }
        });
    }

    private void openSearchStudents()
    {
        ma_btn_searchStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(searchStudentsScreen);
            }
        });
    }

}