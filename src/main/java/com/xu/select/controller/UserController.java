package com.xu.select.controller;

import com.xu.select.excel.ImportExcelUtil;
import com.xu.select.model.ChooseStartTime;
import com.xu.select.model.Course;
import com.xu.select.model.CourseChoose;
import com.xu.select.model.Institution;
import com.xu.select.model.Teacher;
import com.xu.select.service.PageService;
import com.xu.select.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    public static String role_teacher = "teacher";
    public static String role_depHead = "depHead";
    public static String role_admin = "admin";

    @Autowired
    private UserService userService;

    @Autowired
    private PageService pageService;

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        Course course = userService.getCourseByCourseNumber("1001");
        Teacher teacher = userService.getByTeacherNumber("2018100001");
        List<Teacher> teachers = userService.getAllTeacher();
        List<Course> courses = userService.getChoosedCourseByTeacherNumber("2018100004");
        ChooseStartTime chooseStartTime = userService.getChooseStartTime();
        List<Course>  courseList = userService.getCourseByInstitutionNumber("1001");
        List<Course>  allCourses  = userService.getAllCourse();
        CourseChoose courseChoose = userService.getCourseChooseBy("2018100004", "1001");
        List<Institution> institutions = userService.getAllInstitution();
        return "ss";

    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "check", method = RequestMethod.POST)
    public String checkAccount(@RequestParam("teacherNumber") String teacherNumber, @RequestParam("pass") String pass,HttpServletRequest request,  Model model) {
        Teacher teacher = userService.getByTeacherNumber(teacherNumber);
        if (teacher == null || !teacher.getPassword().equals(pass)) {
            model.addAttribute("msg", "密码错误");
            //这里不加redirect，否则前端el取不到值
            return "login";
        }
        model.addAttribute("username", teacher.getName());
        model.addAttribute("number", teacher.getTeacherNumber());
        request.getSession().setAttribute("user", teacher);
        if (role_teacher.equals(teacher.getRole())) {
            return "teacher/teacherIndex";
        } else if (role_depHead.equals(teacher.getRole())) {
            return "teacher/depHeadIndex";
        } else if (role_admin.equals(teacher.getRole())) {
            return "teacher/adminIndex";
        }
        return "login";
    }

    @RequestMapping("exit")
    public String exit(HttpServletRequest request) {
        request.getSession().invalidate();
        return "login";
    }

    @RequestMapping("/teacherIndex")
    public String teacherIndex() {
        return "teacher/teacherIndex";
    }

    @RequestMapping("/depHeadIndex")
    public String depHeadIndex() {
        return "teacher/depHeadIndex";
    }
    @RequestMapping("/adminIndex")
    public String adminIndex() {
        return "teacher/adminIndex";
    }

    @RequestMapping("/info")
    public String userInfo(@RequestParam("teacherNumber") String teacherNumber, Model model) {
        model.addAttribute("teacher", userService.getByTeacherNumber(teacherNumber));
        return "teacher/teacherInfo";
    }

    @RequestMapping("/editPass")
    public String editPass() {
        return "teacher/editTeaPass";
    }


    @RequestMapping("/changePass")
    public String changPass(@RequestParam("prepass") String prepass, @RequestParam("nowpass") String nowpass, Model model, HttpServletRequest request) {
        Teacher teacher = getLoginUser(request);
        if (!teacher.getPassword().equals(prepass)) {
            model.addAttribute("msg", "原始密码输入错误!");
            return "teacher/editTeaPass";
        } else {
            teacher.setPassword(nowpass);
            userService.updatePassword(teacher);
            model.addAttribute("teacher", teacher);
            return "teacher/teacherInfo";
        }
    }


    @RequestMapping("/courseList")
    public String courseList(@Param("page") int page, Model model, HttpServletRequest request) {
        Teacher teacher = getLoginUser(request);
        List<Course> courses;
        if (role_admin.equals(teacher.getRole())) {
            courses = userService.getAllCourse();
            model.addAttribute("paging", pageService.subList(page, courses));
            return "teacher/adminCourseList";
        } else if (role_depHead.equals(teacher.getRole())) {
            String institutionNumber = teacher.getInstitutionNumber();
            courses = userService.getCourseByInstitutionNumber(institutionNumber);
            model.addAttribute("paging", pageService.subList(page, courses));
            return "teacher/depHeadCourseList";
        } else {
            // 普通老师
            String institutionNumber = teacher.getInstitutionNumber();
            courses = userService.getCourseByInstitutionNumber(institutionNumber);
            model.addAttribute("paging", pageService.subList(page, courses));
            return "teacher/courseList";
        }


    }

    @RequestMapping("/insertCourse")
    public String insertCourse(Model model) {
        List<Institution> institutions = userService.getAllInstitution();
        model.addAttribute("insList", institutions);
        return "teacher/insertCourse";
    }

    @RequestMapping("/editCourse")
    public String editCourse(@Param("courseid") String courseid, Model model) {
        //
        List<Institution> institutions = userService.getAllInstitution();
        Course course = userService.getCourseByCourseNumber(courseid);
        model.addAttribute("courseInfo", course);
        model.addAttribute("checkIns", course.getStuTotal());
        model.addAttribute("insList", institutions);
        return "teacher/editCourse";
    }

    @RequestMapping("/insertCourseSuccess")
    public String insertCourseSuccess(Course course,  Model model, HttpServletRequest request) {
        Teacher teacher = getLoginUser(request);
        //获取插入后的课程编号
        String institutionNumber = course.getInstitutionNumber();
        Institution  institution = userService.getInstitutionByNumber(institutionNumber);
        course.setInstitutionName(institution.getName());
        userService.addCourse(course);
        model.addAttribute("paging", pageService.subList(1, userService.getChoosedCourseByTeacherNumber(teacher.getTeacherNumber())));
        return "teacher/courseList";
    }

    @RequestMapping("/updateCourseSuccess")
    public String updateCourseSuccess(@RequestBody Course course, @Param("page") int page, Model model, HttpServletRequest request) {
        userService.updateCourse(course);
        model.addAttribute("paging", pageService.subList(page, userService.getAllCourse()));
        return "teacher/courseList";
    }

    @RequestMapping("/deleteCourse")
    public String deleteCourse(@Param("courseid") String courseid, Model model, HttpServletRequest request) {
        userService.deleteCourse(courseid);
        model.addAttribute("paging", pageService.subList(1, userService.getAllCourse()));
        return "teacher/courseList";
    }

    @RequestMapping("/detailCourse")
    public String detailCourse(@Param("courseid") String courseid, Model model, HttpServletRequest request) {
        Course course = userService.getCourseByCourseNumber(courseid);
        model.addAttribute("detail", course);
        return "teacher/courseDetail";
    }

    @RequestMapping("/uploadCourseExcel")
    @ResponseBody
    public String uploadCourseExcel(HttpServletRequest request) throws Exception {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        Teacher teacher = getLoginUser(multipartHttpServletRequest);
//        if (!role_admin.equals(teacher.getRole())) {
//            return "has no permission";
//        }
        MultipartFile file = multipartHttpServletRequest.getFile("upfile");
        if(file.isEmpty()){
            throw new Exception("文件不存在！");
        }
        InputStream in = file.getInputStream();
        List<List<Object>> lines = ImportExcelUtil.getBankListByExcel(in,file.getOriginalFilename());
        in.close();

        //该处可调用service相应方法进行数据保存到数据库中，现只对数据输出
        List<Course> courses = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            List<Object> line = lines.get(i);
            Course course = new Course();
            course.setCourseNumber(String.valueOf(line.get(0)));
            course.setCourseName(String.valueOf(line.get(1)));
            course.setProperty(String.valueOf(line.get(2)));
            course.setClassName(String.valueOf(line.get(3)));
            course.setStuTotal(Integer.valueOf((String)line.get(4)));
            course.setShiyanHour(Integer.valueOf((String)line.get(5)));
            course.setNote(String.valueOf(line.get(6)));
            course.setInstitutionNumber(String.valueOf(line.get(7)));
            course.setInstitutionNumber(String.valueOf(line.get(8)));
            courses.add(course);
        }
        userService.saveOrUpdateAll(courses);
        return "success";
    }

    public Teacher getLoginUser(HttpServletRequest request) {
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");
        return teacher;
    }
}
