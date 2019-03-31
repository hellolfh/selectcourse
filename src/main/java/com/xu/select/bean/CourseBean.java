package com.xu.select.bean;


public class CourseBean {
    //    课程名，课程
//    性质，班级，学生人数，总学时数，实验学时数，备注，所属系
    private int id;
    private String courseNumber;
    private String courseName;
    private String property;
    private String className;
    private int stuTotal;
    private int classHour;
    private int shiyanHour;
    private String note;
    private String institutionNumber;
    private String institutionName;

    private String teacherNumber;
    private String teacherName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getStuTotal() {
        return stuTotal;
    }

    public void setStuTotal(int stuTotal) {
        this.stuTotal = stuTotal;
    }

    public int getClassHour() {
        return classHour;
    }

    public void setClassHour(int classHour) {
        this.classHour = classHour;
    }

    public int getShiyanHour() {
        return shiyanHour;
    }

    public void setShiyanHour(int shiyanHour) {
        this.shiyanHour = shiyanHour;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getInstitutionNumber() {
        return institutionNumber;
    }

    public void setInstitutionNumber(String institutionNumber) {
        this.institutionNumber = institutionNumber;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public String getTeacherNumber() {
        return teacherNumber;
    }

    public void setTeacherNumber(String teacherNumber) {
        this.teacherNumber = teacherNumber;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
