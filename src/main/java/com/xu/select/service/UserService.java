package com.xu.select.service;

import com.xu.select.model.ChooseStartTime;
import com.xu.select.model.Course;
import com.xu.select.model.CourseChoose;
import com.xu.select.model.Institution;
import com.xu.select.model.Teacher;

import java.util.List;

public interface UserService {
    // 根据ID获取老师信息
    public Teacher getByTeacherNumber(String teacherNumber);
    // 修改老师基本信息如用户名密码
    public void updatePassword(Teacher teacher);
    // 查询出所有的老师
    public List<Teacher> getAllTeacher();

    // 某个系下的所有的课程
    public List<Course> getCourseByInstitutionNumber(String institutionNumber);
    // 增加一门课
    public void addCourse(Course course);
    // 删除一门课
    public void deleteCourse(String courseNumber);
    // 修改一门课
    public void updateCourse(Course course);

    public ChooseStartTime getChooseStartTime();
    // 某老师选某课
    public void selectCourse(String teacherNumber, String courseNumber);
    // 某老师取消选某课
    public void cancelSelectCourse(String teacherNumber, String courseNumber);

    // 某老师选某课的记录
    public CourseChoose getCourseChooseBy(String teacherNumber, String courseNumber);

    // 某老师选了哪些课
    public List<Course> getChoosedCourseByTeacherNumber(String teacherNumber);


    List<Course> getAllCourse();

    List<Institution> getAllInstitution();

    Course getCourseByCourseNumber(String courseNumber);

    Institution getInstitutionByNumber(String institutionNumber);

    void saveOrUpdateAll(List<Course> courses);
    void saveOrUpdate(Course course);
 }
