//this file adds students to the database
package com.example.homework03_program01;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

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
    int majorId = -1;
    DatabaseHelper dbh;
    Button btn_delete;
    private static boolean updateMode = false;


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
        btn_delete             = findViewById(R.id.as_btn_delete);

        //creates a list of all the major names to be passed to the major dropdown
        majorNames.add("Majors");
        for(MajorObj major : majorList)
        {
            majorNames.add(major.getMajorName());
            Log.d("NEW MAJOR IDS: ", major.getMajorId() + "");

        }
        //add the majors to the drop down everytime this "screen" is opened
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, majorNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_majorSpinner.setAdapter(adapter);

        addMajorClick();
        saveBtnClick();
        mainMenuBtnClick();
        getMajorId();
        deleteBtn();
        if(dh.getTempStudent() != null)
        {
            fillTempData(dh.getTempStudent());
        }
        if(studentList.contains(dh.getTempStudent()))
        {
            saveBtn.setText("Update");
            et_uName.setEnabled(false);
            et_uName.setTextColor(Color.parseColor("#808080"));
            updateMode = true;
        }
        else
        {
            updateMode = false;
        }

    }

    //deletes a student
    private void deleteBtn()
    {
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!et_uName.getText().toString().isEmpty())
                {
                    dbh.deleteStudent(et_uName.getText().toString());
                    clearBoxes();
                    tv_opSM.setText("Student deleted");
                }
            }
        });
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

    //saves or updates a student
    private void saveBtnClick()
    {
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkData())
                {
                    if(updateMode)
                    {
                        addStudentToDatabase();
                        tv_opSM.setText("Student updated");
                        clearBoxes();
                    }

                    if(checkUsername() && !updateMode)
                    {
                        addStudentToDatabase();
                        tv_opSM.setText("Student added");
                        clearBoxes();
                    }
                    else
                    {
                        if(!updateMode)
                        {
                            tv_opSM.setText("Username already exists");
                        }
                        else
                        {
                            tv_opSM.setText("Student Updated");
                        }
                    }
                }
                else
                {
                    tv_opSM.setText("Unable to add student. Please fill all fields");
                }

                dh.setTempStudent(null);
            }
        });
    }


    //makes sure all the textboxes are filled
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

    //checks the username
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
    }

    //add student tp the database
    private void addStudentToDatabase()
    {
        if(majorId != -1)
        {
            StudentObj student = new StudentObj();
            student.setFname(et_fName.getText().toString());
            student.setLname(et_lName.getText().toString());
            student.setUsername(et_uName.getText().toString());
            student.setEmail(et_email.getText().toString());
            Log.d("Number", et_age.getText().toString());
            student.setAge(Integer.parseInt(et_age.getText().toString()));
            student.setGpa(Float.parseFloat(et_gpa.getText().toString()));
            student.setMajorid(majorId);

            if(updateMode)
            {
                dbh.updateStudent(student);
            }
            else
            {
                dbh.addStudent(student);
            }

        }
        else
        {
            Log.d("Invalid Major id", "no go!");
        }

    }

    //when the user comes back fill the data they entered, if any
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

        if(student.getMajorid() != 0)
        {
            spinner_majorSpinner.setSelection(student.getMajorid());
        }
    }

    //gets the major id
    private void getMajorId()
    {

        spinner_majorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                if(i != 0)
                {
                    String selectedMajorName = adapterView.getItemAtPosition(i).toString();
                    for(MajorObj mObj : majorList)
                    {
                        if(mObj.getMajorName().equalsIgnoreCase(selectedMajorName))
                        {
                            majorId = mObj.getMajorId();
                        }
                    }
                }

                Log.d("MajorID: ", majorId + "");

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    //saves the available info when the user goes to add a major
    private void saveTempData()
    {
        StudentObj student = new StudentObj();
        if(!et_fName.getText().toString().isEmpty())
        {
            student.setFname(et_fName.getText().toString());
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
        }
        if(!et_gpa.getText().toString().isEmpty())
        {
            student.setGpa(Float.parseFloat(et_gpa.getText().toString()));
        }
        if(spinner_majorSpinner.getSelectedItemId() != 0)
        {
            student.setMajorid((int) spinner_majorSpinner.getSelectedItemId());
        }

        dh.setTempStudent(student);
    }


}