package com.data;

/**
 * This is a Class object that will be used to transfer data from the database to the servlet and vice-versa.
 * Created by Matthew on 4/20/2017.
 */
public class ClassObj {
    private String className;       // the class name given by the teacher
    private String classCode;       // the unique class code auto-generated
    private String teacher;         // the full name of the teacher
    private String locked;          // explains if the class is locked (no students can join the class at the time)

    //constructors
    public ClassObj() {}
    public ClassObj(String name, String classCode, String teacher, String locked) {
        className = name;
        this.classCode = classCode;
        this.teacher = teacher;
        this.locked = locked;
    }

    //basic accessor/mutator methods
    public String getClassName() { return className; }
    public void setClassName(String name) { className = name; }
    public String getClassCode() { return classCode; }
    public void setClassCode(String code) { classCode = code; }
    public String getTeacher() { return teacher; }
    public void setTeacher(String teacher) { this.teacher = teacher; }
    public String getLocked() { return locked; }
    public void setLocked(String locked) { this.locked = locked; }
}
