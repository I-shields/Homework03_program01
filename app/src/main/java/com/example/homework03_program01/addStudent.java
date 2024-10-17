package com.example.homework03_program01;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class addStudent extends AppCompatActivity {

    EditText et_fName;
    EditText et_lName;
    EditText et_uName;
    EditText et_email;
    EditText et_age;
    EditText et_gpa;
    TextView tv_addMajor;
    Intent addMajorIntent;
    Spinner spinner_majorSpinner;
    Button saveBtn;
    TextView tv_opSM;
    ArrayList<MajorObj> majorList = new ArrayList<MajorObj>();
    ArrayList<String> majorNames = new ArrayList<String>();
    DataHelper dh = new DataHelper();
    Button mainMenu;
    Intent mainView;
    DatabaseHelper dbh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_student);

        et_fName               = findViewById(R.id.as_et_fName);
        et_lName               = findViewById(R.id.as_et_lName);
        et_uName               = findViewById(R.id.as_et_uName);
        et_email               = findViewById(R.id.as_et_email);
        et_age                 = findViewById(R.id.as_et_age);
        et_gpa                 = findViewById(R.id.as_et_gpa);
        tv_addMajor            = findViewById(R.id.as_tv_addMajor);
        addMajorIntent         = new Intent(this, addMajor.class);
        spinner_majorSpinner   = findViewById(R.id.as_spin_majorSpinner);
        saveBtn                = findViewById(R.id.as_btn_save);
        tv_opSM                = findViewById(R.id.as_tv_opSM);
        mainMenu               = findViewById(R.id.as_btn_mainMenu);
        mainView               = new Intent(this, MainActivity.class);
        dbh                    = new DatabaseHelper(this);

        if(dh.tempStudent != null)
        {
            fillTempData();
        }

        majorList = dbh.getMajors();
        majorNames.add("Majors");
        for(MajorObj major : majorList)
        {
            majorNames.add(major.getMajorName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,majorNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_majorSpinner.setAdapter(adapter);


        addMajorClick();
        saveBtnClick();
        mainMenuBtnClick();


    }

    private void mainMenuBtnClick()
    {
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(mainView);
            }
        });
    }

    private void addMajorClick()
    {
        tv_addMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                saveData();
                startActivity(addMajorIntent);
            }
        });
    }

    private void saveBtnClick()
    {
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkData())
                {
                    if(checkUsername())
                    {
                        addStudentToDatabase();
                        tv_opSM.setText("Student added");
                        dh.tempStudent = null;
                        clearBoxes();
                    }
                    else
                    {
                        tv_opSM.setText("Username already exists");
                    }
                }
                else
                {
                    tv_opSM.setText("Unable to add student. Please fill all fields");
                }
            }
        });
    }

    private boolean checkData()
    {
        boolean valid = false;
        if(!et_fName.getText().toString().isEmpty() && !et_lName.getText().toString().isEmpty() && !et_uName.getText().toString().isEmpty() && !et_email.getText().toString().isEmpty() &&
                !et_age.getText().toString().isEmpty() && !et_gpa.getText().toString().isEmpty() && spinner_majorSpinner.getSelectedItemId() != 0)
        {
            valid = true;
        }

        return valid;
    }

    private boolean checkUsername()
    {
        boolean validUname = true;
        if(!dbh.getStudents().isEmpty())
        {
            for(StudentObj student : dbh.getStudents())
            {
                if(student.getUsername().equalsIgnoreCase(et_uName.getText().toString()))
                {
                    validUname = false;
                }
            }
        }
        return validUname;
    }

    private void clearBoxes()
    {
        et_fName.setText(null);
        et_lName.setText(null);
        et_uName.setText(null);
        et_email.setText(null);
        et_gpa.setText(null);
        et_age.setText(null);
        spinner_majorSpinner.setSelection(0);
    }

    private void addStudentToDatabase()
    {
        StudentObj student = new StudentObj();

        student.setFname(et_fName.getText().toString());
        student.setLname(et_lName.getText().toString());
        student.setUsername(et_uName.getText().toString());
        student.setEmail(et_email.getText().toString());
        student.setAge(Integer.parseInt(et_age.getText().toString()));
        student.setGpa(Float.parseFloat(et_gpa.getText().toString()));
        student.setMajorId(getMajorId());

        dbh.addStudent(student);

    }

    private void fillTempData()
    {
        et_fName.setText(dh.tempStudent.getFname());
        et_lName.setText(dh.tempStudent.getLname());
        et_uName.setText(dh.tempStudent.getUsername());
        et_email.setText(dh.tempStudent.getEmail());
        et_age.setText(dh.tempStudent.getAge());
        et_gpa.setText(String.valueOf(dh.tempStudent.getGpa()));
    }

    private int getMajorId()
    {
        int id = 0;
        for(MajorObj major : dbh.getMajors())
        {
            if(major.getMajorName().equalsIgnoreCase(spinner_majorSpinner.getItemAtPosition((int) spinner_majorSpinner.getSelectedItemId()).toString()))
            {
                id = major.getMajorId();
            }
        }

        return id;
    }

    private void saveData()
    {
        if(!et_fName.getText().toString().isEmpty())
        {
            dh.tempStudent.setFname(et_fName.getText().toString());
        }
        if(!et_lName.getText().toString().isEmpty())
        {
            dh.tempStudent.setLname(et_lName.getText().toString());
        }
        if(!et_uName.getText().toString().isEmpty())
        {
            dh.tempStudent.setUsername(et_uName.getText().toString());
        }
        if(!et_email.getText().toString().isEmpty())
        {
            dh.tempStudent.setEmail(et_email.getText().toString());
        }
        if(!et_age.getText().toString().isEmpty())
        {
            dh.tempStudent.setAge(Integer.parseInt(et_age.getText().toString()));
        }
        if(!et_gpa.getText().toString().isEmpty())
        {
            dh.tempStudent.setGpa(Float.parseFloat(et_gpa.getText().toString()));
        }
    }
}