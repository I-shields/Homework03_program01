package com.example.homework03_program01;

public class StudentObj
{
    private String username;
    private String fname;
    private String lname;
    private String email;
    private int age;
    private float gpa;
    private int majorId;
    private int userId;

    public StudentObj()
    {

    }

    public StudentObj(String u, String fn, String ln, String e, int a, float g)
    {
        username = u;
        fname = fn;
        lname = ln;
        email = e;
        age = a;
        gpa = g;

    }

    public StudentObj(String u, String fn, String ln, String e, int a, float g, int mi)
    {
        username = u;
        fname = fn;
        lname = ln;
        email = e;
        age = a;
        gpa = g;
        majorId = mi;

    }

    public int getMajorId() {
        return majorId;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
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

    public void setUserId(int id)
    {
        userId = id;
    }

    public int getUserId()
    {
        return userId;
    }
}
