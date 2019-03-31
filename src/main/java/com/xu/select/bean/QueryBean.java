package com.xu.select.bean;


import org.springframework.util.StringUtils;

public class QueryBean {

    private String courseNumber;
    private String courseName;
    private String property;
    private String className;
    private String stuTotal;
    private String classHour;
    private String shiyanHour;
    private String note;
    private String institutionNumber;
    private String institutionName;



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

    public String getStuTotal() {
        return stuTotal;
    }

    public void setStuTotal(String stuTotal) {
        this.stuTotal = stuTotal;
    }

    public String getClassHour() {
        return classHour;
    }

    public void setClassHour(String classHour) {
        this.classHour = classHour;
    }

    public String getShiyanHour() {
        return shiyanHour;
    }

    public void setShiyanHour(String shiyanHour) {
        this.shiyanHour = shiyanHour;
    }

    public boolean isSetValue() {
        if (StringUtils.isEmpty(courseNumber) && StringUtils.isEmpty(courseName) && StringUtils.isEmpty(property)
                && StringUtils.isEmpty(className) && StringUtils.isEmpty(stuTotal) && StringUtils.isEmpty(classHour)
                && StringUtils.isEmpty(shiyanHour) && StringUtils.isEmpty(note) && StringUtils.isEmpty(institutionName)
                && StringUtils.isEmpty(institutionName)) {
            return false;
        }
        return true;
    }
}
