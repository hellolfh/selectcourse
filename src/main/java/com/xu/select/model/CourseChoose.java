package com.xu.select.model;


public class CourseChoose {
    private int id;
    // 哪个老师的学号
    private int teacherNumber;
    // 哪门课程的代号
    private String courseNumber;
    // 是否选中此课程
    private boolean selected;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTeacherNumber() {
        return teacherNumber;
    }
    public void setTeacherNumber(int teacherNumber) {
        this.teacherNumber = teacherNumber;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
