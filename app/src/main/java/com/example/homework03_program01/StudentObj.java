//this is the student object
package com.example.homework03_program01;

public class StudentObj
{
    //student variables
    private String username;
    private String fname;
    private String lname;
    private String email;
    private int age;
    private float gpa;
    private int majorid;

    //constructor
    public StudentObj()
    {

    }


    //I know it's unused, but it feels wrong to delete, it might be used in the future
    public StudentObj(String u, String fn, String ln, String e, int a, float g, int mi)
    {
        username = u;
        fname = fn;
        lname = ln;
        email = e;
        age = a;
        gpa = g;
        majorid = mi;

    }

    //getters and setters

    public int getMajorid() {
        return majorid;
    }

    public void setMajorid(int majorid) {
        this.majorid = majorid;
    }

    public String getUsername() {
        return username;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public float getGpa() {
        return gpa;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGpa(float gpa) {
        this.gpa = gpa;
    }

}
