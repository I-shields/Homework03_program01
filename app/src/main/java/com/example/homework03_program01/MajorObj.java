// This is the major object
package com.example.homework03_program01;

public class MajorObj
{
    private int majorId;
    private String majorName;
    private String majorPrefix;

    public MajorObj()
    {

    }

    public MajorObj(int mi, String mn, String mp)
    {
        majorId = mi;
        majorName = mn;
        majorPrefix = mp;
    }

    public int getMajorId() {
        return majorId;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getMajorPrefix() {
        return majorPrefix;
    }

    public void setMajorPrefix(String majorPrefix) {
        this.majorPrefix = majorPrefix;
    }
}
