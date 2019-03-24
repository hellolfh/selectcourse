package com.xu.select.dao;


import com.xu.select.model.ChooseStartTime;
import com.xu.select.model.Course;
import com.xu.select.model.CourseChoose;
import com.xu.select.model.Institution;
import com.xu.select.model.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    // 根据ID获取老师信息
    public Teacher getByTeacherNumber(@Param("teacherNumber") String teacherNumber);
    // 修改老师基本信息如用户名密码
    public void updatePassword(@Param("teacher") Teacher teacher);
    // 查询出所有的老师
    public List<Teacher> getAllTeacher();

    // 某个系下的所有的课程
    public List<Course> getCourseByInstitutionNumber(@Param("institutionNumber") String institutionNumber);
    // 增加一门课
    public void addCourse(@Param("course") Course course);
    // 删除一门课
    public void deleteCourse(@Param("courseNumber") String courseNumber);
    // 修改一门课
    public void updateCourse(@Param("course") Course course);

    ChooseStartTime getChooseStartTime();
    void saveChooseStartTime(@Param("chooseStartTime") ChooseStartTime chooseStartTime);
    void updateChooseStartTime(@Param("chooseStartTime") ChooseStartTime chooseStartTime);

    // 某老师选某课
    public void selectCourse(@Param("teacherNumber") String teacherNumber, @Param("courseNumber") String courseNumber);
    // 某老师取消选某课
    public void cancelSelectCourse(@Param("teacherNumber") String teacherNumber, @Param("courseNumber") String courseNumber);

    // 某老师选某课的记录
    public CourseChoose getCourseChooseBy(@Param("teacherNumber") String teacherNumber, @Param("courseNumber") String courseNumber);

    // 某老师选了哪些课
    public List<Course> getChoosedCourseByTeacherNumber(@Param("teacherNumber") String teacherNumber);

    List<Course> getAllCourse();

    List<Institution> getAllInstitution();

    Course getCourseByCourseNumber(@Param("courseNumber") String courseNumber);

    Institution getInstitutionByNumber(@Param("institutionNumber") String institutionNumber);
}
