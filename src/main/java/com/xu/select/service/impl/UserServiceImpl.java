package com.xu.select.service.impl;

import com.xu.select.dao.UserDao;
import com.xu.select.model.ChooseStartTime;
import com.xu.select.model.Course;
import com.xu.select.model.CourseChoose;
import com.xu.select.model.Institution;
import com.xu.select.model.Teacher;
import com.xu.select.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public Teacher getByTeacherNumber(String teacherNumber) {
        Teacher teacher = userDao.getByTeacherNumber(teacherNumber);
        return teacher;
    }

    @Override
    public void updatePassword(Teacher teacher) {
        userDao.updatePassword(teacher);
    }

    @Override
    public List<Teacher> getAllTeacher() {
        List<Teacher> teachers = userDao.getAllTeacher();
        return teachers;
    }

    @Override
    public List<Course> getCourseByInstitutionNumber(String institutionNumber) {
        List<Course> courses = userDao.getCourseByInstitutionNumber(institutionNumber);
        return courses;
    }

    @Override
    public void addCourse(Course course) {
        userDao.addCourse(course);
    }

    @Override
    public void deleteCourse(String courseNumber) {
        userDao.deleteCourse(courseNumber);
    }

    @Override
    public void updateCourse(Course course) {
        userDao.updateCourse(course);
    }

    @Override
    public ChooseStartTime getChooseStartTime() {
        ChooseStartTime chooseStartTime = userDao.getChooseStartTime();
        return chooseStartTime;
    }

    @Override
    public void selectCourse(String teacherNumber, String courseNumber) {
        userDao.selectCourse(teacherNumber, courseNumber);
    }

    @Override
    public void cancelSelectCourse(String teacherNumber, String courseNumber) {
        userDao.cancelSelectCourse(teacherNumber, courseNumber);
    }

    @Override
    public CourseChoose getCourseChooseBy(String teacherNumber, String courseNumber) {
        CourseChoose choose  = userDao.getCourseChooseBy(teacherNumber, courseNumber);
        return choose;
    }

    @Override
    public List<Course> getChoosedCourseByTeacherNumber(String teacherNumber) {
        List<Course> courses = userDao.getChoosedCourseByTeacherNumber(teacherNumber);
        return courses;
    }

    @Override
    public List<Course> getAllCourse() {
        List<Course> courses = userDao.getAllCourse();
        return courses;
    }

    @Override
    public List<Institution> getAllInstitution() {
        List<Institution> institutions = userDao.getAllInstitution();
        return institutions;
    }

    @Override
    public Course getCourseByCourseNumber(String courseNumber) {
        Course course = userDao.getCourseByCourseNumber(courseNumber);
        return course;
    }

    @Override
    public Institution getInstitutionByNumber(String institutionNumber) {
        Institution institution = userDao.getInstitutionByNumber(institutionNumber);
        return institution;
    }

    /**
     * 保存或者新增
     * @param courses
     */
    @Override
    public void saveOrUpdateAll(List<Course> courses) {
        if (CollectionUtils.isEmpty(courses)) {
            return;
        }
        for (Course course : courses) {
            saveOrUpdate(course);
        }
    }

    /**
     * 保存单个
     * @param course
     */
    public void saveOrUpdate(Course course) {
        if (course == null) {
            return;
        }
        Course dbCourse = userDao.getCourseByCourseNumber(course.getCourseNumber());
        if (dbCourse == null) {
            String institutionNumber = course.getInstitutionNumber();
            Institution institution = getInstitutionByNumber(institutionNumber);
            course.setInstitutionName(institution.getName());
            userDao.addCourse(course);
        } else {
            int id = dbCourse.getId();
            BeanUtils.copyProperties(course, dbCourse);
            dbCourse.setId(id);
            Institution institution = getInstitutionByNumber(dbCourse.getInstitutionNumber());
            course.setInstitutionName(institution.getName());
            userDao.updateCourse(course);
        }
    }
}
