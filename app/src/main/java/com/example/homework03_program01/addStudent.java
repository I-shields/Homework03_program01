package com.example.homework03_program01;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.Collection;
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
    ArrayList<StudentObj> studentList = new ArrayList<StudentObj>();
    DataHelper dh = new DataHelper();
    Button mainMenu;
    Intent mainView;
    int majorId;
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
        majorList              = dh.getMajorlist();
        studentList            = dh.getStudentList();
        dbh                    = new DatabaseHelper(this);

        majorNames.add("Majors");
        for(MajorObj major : majorList)
        {
            majorNames.add(major.getMajorName());

        }
        //add the majors to the drop down everytime this "screen" is opened
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,majorNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_majorSpinner.setAdapter(adapter);

        addMajorClick();
        saveBtnClick();
        mainMenuBtnClick();
        getMajorId();
        if(dh.getTempStudent() != null)
        {
            fillTempData(dh.getTempStudent());
        }

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
                saveTempData();
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
        if(!studentList.isEmpty())
        {
            for(StudentObj student : studentList)
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
        tv_opSM.setText(null);
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
        student.setMajorId(majorId);
        dbh.addStudent(student);

    }

    private void fillTempData(StudentObj student)
    {
        et_fName.setText(student.getFname());
        et_lName.setText(student.getLname());
        et_uName.setText(student.getUsername());
        et_email.setText(student.getEmail());
        if(student.getAge() == 0)
        {
            et_age.setText(null);
        }
        else
        {
            et_age.setText(String.valueOf(student.getAge()));
        }

        if(student.getGpa() == 0)
        {
            et_gpa.setText(null);
        }
        else
        {
            et_gpa.setText(String.valueOf(student.getGpa()));
        }
    }

    private void getMajorId()
    {

        spinner_majorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(i != 0)
                {
                    Log.d("ID of Selected ITEM", i + "");
                    String selectedMajorName = adapterView.getItemAtPosition(i).toString();
                    for(MajorObj mObj : majorList)
                    {
                        if(mObj.getMajorName().equalsIgnoreCase(selectedMajorName))
                        {
                            majorId = mObj.getMajorId();
                        }
                    }
                }
                Log.d("MAJOR NUMBER", majorId + "");

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void saveTempData()
    {
        StudentObj student = new StudentObj();
        if(!et_fName.getText().toString().isEmpty())
        {
            student.setFname(et_fName.getText().toString());
            Log.d("FNAME CHECK!!!", "In fname");
        }
        if(!et_lName.getText().toString().isEmpty())
        {
            student.setLname(et_lName.getText().toString());
        }
        if(!et_uName.getText().toString().isEmpty())
        {
            student.setUsername(et_uName.getText().toString());
        }
        if(!et_email.getText().toString().isEmpty())
        {
            student.setEmail(et_email.getText().toString());
        }
        if(!et_age.getText().toString().isEmpty())
        {
            student.setAge(Integer.parseInt(et_age.getText().toString()));
            Log.d("Age CHECK!!!", "In age");
        }
        if(!et_gpa.getText().toString().isEmpty())
        {
            student.setGpa(Float.parseFloat(et_gpa.getText().toString()));
        }

        dh.setTempStudent(student);
    }


}