//This file handles adding new majors
package com.example.homework03_program01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class addMajor extends AppCompatActivity {


    EditText et_majorName;
    EditText et_majorPrefix;
    Button btn_save;
    Button btn_cancel;
    Intent addStudentIntent;
    TextView tv_opSM;
    DatabaseHelper dbh;
    DataHelper dh;
    ArrayList<MajorObj> majorList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_major);

        et_majorName        = findViewById(R.id.amj_et_majorName);
        et_majorPrefix      = findViewById(R.id.amj_et_majorPrefix);
        btn_save            = findViewById(R.id.amj_btn_saveMajor);
        btn_cancel          = findViewById(R.id.amj_btn_cancel);
        tv_opSM             = findViewById(R.id.amj_tv_opSM);
        addStudentIntent    = new Intent(this, addStudent.class);
        dbh                 = new DatabaseHelper(this);
        dh                  = new DataHelper();
        majorList           = dh.getMajorlist();



        goBackBtnListener();
        saveBtnListener();

    }

    private void goBackBtnListener()
    {
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(addStudentIntent);
            }
        });


    }

    private void saveBtnListener()
    {
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //if the major doesn't exist, add it to the database
                if(checkData())
                {
                    updateDatabase();
                    tv_opSM.setText("Major Added");
                    clearBoxes();
                }
                else
                {
                    tv_opSM.setText("Error adding, please make sure boxes are filled and major does not already exist.");
                }
            }
        });
    }

    //send data to the database file to be added to the database
    private void updateDatabase()
    {
        String majorName = et_majorName.getText().toString();
        String majorPrefix = et_majorPrefix.getText().toString();
        MajorObj major = new MajorObj();
        major.setMajorName(majorName);
        major.setMajorPrefix(majorPrefix);
        dbh.addMajor(major);

    }

    //checks to see if the major exists
    private boolean checkData()
    {
        boolean validData = true;
        if(!et_majorName.getText().toString().isEmpty() && !et_majorPrefix.getText().toString().isEmpty())
        {
            if(!majorList.isEmpty())
            {
                for(MajorObj major : majorList)
                {
                    if(major.getMajorName().equalsIgnoreCase(et_majorName.getText().toString()))
                    {
                        validData = false;
                    }
                }
            }
        }
        else
        {
           validData = false;
        }
        return validData;
    }

    private void clearBoxes()
    {
        et_majorPrefix.setText(null);
        et_majorName.setText(null);
    }
}