//======================================
// This file handles the search
// functionality on the program
//======================================

package com.example.homework03_program01;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class searchStudents extends AppCompatActivity {

    //Variable list

    EditText et_searchInput;
    Spinner spin_typeSpinner;
    EditText et_gpaInput;
    Spinner spin_Operation;
    Button btn_mainMenu;
    TextView tv_Msg;
    ListView lv_results;

    Intent main;

    DataHelper dh = new DataHelper();

    searchStudentAdapter adapter;

    ArrayList<StudentObj> studentList = new ArrayList<StudentObj>();
    ArrayList<MajorObj> majorList = new ArrayList<MajorObj>();

    ArrayList<String> searchTypes = new ArrayList<String>();
    ArrayList<String> operations = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_students);

        et_searchInput      = findViewById(R.id.ss_et_searchInput);
        spin_typeSpinner    = findViewById(R.id.ss_spin_type);
        et_gpaInput         = findViewById(R.id.ss_et_gpaInput);
        spin_Operation      = findViewById(R.id.ss_spin_gpaType);
        tv_Msg              = findViewById(R.id.ss_tv_errorMsg);
        studentList         = dh.getStudentList();
        majorList           = dh.getMajorlist();
        lv_results          = findViewById(R.id.ss_lv_sr);
        btn_mainMenu        = findViewById(R.id.ss_btn_mainMenu);



        //add student search parameters to an arrayList
        //this arrayList is passed to a spinner
        searchTypes.add("First name");
        searchTypes.add("Last name");
        searchTypes.add("Major");
        searchTypes.add("Username");

        //add GPA search parameters to an arrayList
        //this arrayList is passed to a spinner
        operations.add("=");
        operations.add(">");
        operations.add("<");

        main = new Intent(this, MainActivity.class);

        adapter = new searchStudentAdapter(this);
        lv_results.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        addSpinnerItems();
        changeParameter();
        adaptiveSearch();
        goBackToMain();
        gpaAdaptiveSearch();
        changeGpaParameters();

    }

    private void goBackToMain()
    {
        btn_mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(main);
            }
        });
    }

    private void addSpinnerItems()
    {
        //add search types for students
        ArrayAdapter<String> studentSearchAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, searchTypes);
        studentSearchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_typeSpinner.setAdapter(studentSearchAdapter);

        //add search types for GPA
        ArrayAdapter<String> gpaSearchAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, operations);
        gpaSearchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_Operation.setAdapter(gpaSearchAdapter);

    }

    private void adaptiveSearch()
    {
        et_searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                //starts searching when a user starts typing
                if(charSequence.length() >= 1)
                {
                    et_gpaInput.setText(null);
                    String search = charSequence.toString();
                    search = search.toLowerCase();
                    searchFunction(search);
                }

                //clear search results when the text field is empty
                if(et_searchInput.getText().toString().isEmpty())
                {
                    dh.clearSearchResults();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void changeParameter()
    {
        spin_typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                //if the search parameters are changed, reset the search results
                //and perform a new search based on the new search parameters if
                //there is text in the search field
                dh.clearSearchResults();
                adapter.notifyDataSetChanged();
                if(!et_searchInput.getText().toString().isEmpty())
                {
                    searchFunction(et_searchInput.getText().toString().toLowerCase());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void searchFunction(String searchString)
    {
        //main search function. Performs search based on the search parameters and the search string
        dh.clearSearchResults();
        adapter.notifyDataSetChanged();

        if(spin_typeSpinner.getSelectedItemId() == 0)
        {
            for(StudentObj student : studentList)
            {
                if(student.getFname().toLowerCase().startsWith(searchString))
                {
                    dh.addSearchResult(student);
                    adapter.notifyDataSetChanged();
                }
            }
        }

        if(spin_typeSpinner.getSelectedItemId() == 1)
        {
            for(StudentObj student : studentList)
            {
                if(student.getLname().toLowerCase().startsWith(searchString))
                {
                    dh.addSearchResult(student);
                    adapter.notifyDataSetChanged();
                }
            }
        }

        if(spin_typeSpinner.getSelectedItemId() == 3)
        {
            for(StudentObj student : studentList)
            {
                if(student.getUsername().toLowerCase().startsWith(searchString))
                {
                    dh.addSearchResult(student);
                    adapter.notifyDataSetChanged();
                }
            }
        }

        if(spin_typeSpinner.getSelectedItemId() == 2)
        {
            for(MajorObj major : majorList)
            {
                if(major.getMajorName().toLowerCase().startsWith(searchString))
                {
                    for(StudentObj student : studentList)
                    {
                        if(student.getMajorid() == major.getMajorId())
                        {
                            dh.addSearchResult(student);
                        }
                    }
                }
            }

        }
    }

    private void gpaFullSearch(float gpaNum)
    {
        //gpa search function
        dh.clearSearchResults();
        if(spin_Operation.getSelectedItemId() == 0)
        {
            for(StudentObj student : studentList)
            {
                if(student.getGpa() == gpaNum)
                {
                    dh.addSearchResult(student);
                }
            }
        }
        if(spin_Operation.getSelectedItemId() == 1)
        {
            for(StudentObj student : studentList)
            {
                if(student.getGpa() > gpaNum)
                {
                    dh.addSearchResult(student);
                }
            }
        }
        if(spin_Operation.getSelectedItemId() == 2)
        {
            for(StudentObj student : studentList)
            {
                if(student.getGpa() < gpaNum)
                {
                    dh.addSearchResult(student);
                }
            }
        }

        adapter.notifyDataSetChanged();
    }


    private void gpaAdaptiveSearch()
    {
        et_gpaInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                //if theres text in the GPA search field, try to convert
                //to a float and start the search
                if(charSequence.length() >= 1)
                {
                    et_searchInput.setText(null);
                    spin_typeSpinner.setSelection(0);
                    try {
                        float gpaNum = Float.parseFloat(et_gpaInput.getText().toString());
                        gpaFullSearch(gpaNum);
                    }
                    catch (Exception e)
                    {
                        Log.d("Exception: ","Unable to convert float, " + e);
                    }
                }
                //clear search results if theres no text
                if(et_gpaInput.getText().toString().isEmpty())
                {
                    dh.clearSearchResults();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void changeGpaParameters()
    {
        //if there's text in the GPA search box, try to convert and perform a search
        spin_Operation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                if(!et_gpaInput.getText().toString().isEmpty())
                {
                    try {
                        float gpaNum = Float.parseFloat(et_gpaInput.getText().toString());
                        gpaFullSearch(gpaNum);
                    }
                    catch (Exception e)
                    {
                        Log.d("Exception: ","Unable to convert float, " + e);
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}