package com.xu.select.controller;

import com.xu.select.bean.QueryBean;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // 用户点击首页时候的按钮进入的页面
    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
        Teacher teacher = getLoginUser(request);
        if (teacher == null) {
            return "redirect:login";
        }
        if (role_teacher.equals(teacher.getRole())) {
            return "teacher/teacherIndex";
        } else if (role_depHead.equals(teacher.getRole())) {
            return "teacher/teacherIndex";
        } else if (role_admin.equals(teacher.getRole())) {
            return "redirect:adminIndex";
        }
        return "teacher/teacherIndex";
    }

    // 点击excel导入进入的页面
    @RequestMapping("/excelImportIndex")
    public String excelImportIndex() {
        return "teacher/excelImport";
    }

    // 登录页面，会有用户名密码需要用户输入
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    // 会检查用户能名密码是否正确
    @RequestMapping(value = "check", method = RequestMethod.POST)
    public String checkAccount(@RequestParam("teacherNumber") String teacherNumber, @RequestParam("pass") String pass, HttpServletRequest request, Model model) {
        Teacher teacher = userService.getByTeacherNumber(teacherNumber);
        if (teacher == null || !teacher.getPassword().equals(pass)) {
            model.addAttribute("msg", "密码错误");
            //这里不加redirect，否则前端el取不到值
            return "login";
        }
        request.getSession().setAttribute("user", teacher);
        if (role_teacher.equals(teacher.getRole())) {
            return "teacher/teacherIndex";
        } else if (role_depHead.equals(teacher.getRole())) {
            return "teacher/teacherIndex";
        } else if (role_admin.equals(teacher.getRole())) {
            return "redirect:adminIndex";
        }
        return "login";
    }

    // 当前用户退出登录
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
    public String adminIndex(Model model) {
        model.addAttribute("chooseStartTime", userService.getChooseStartTime());
        return "teacher/adminIndex";
    }

    //当前登录者的信息页面
    @RequestMapping("/info")
    public String userInfo(@RequestParam("teacherNumber") String teacherNumber, Model model) {
        model.addAttribute("teacher", userService.getByTeacherNumber(teacherNumber));
        return "teacher/teacherInfo";
    }

    //修改密码进入的页面
    @RequestMapping("/editPass")
    public String editPass() {
        return "teacher/editTeaPass";
    }


    // 密码修改页面 点击确认修改然后进入的页面
    @RequestMapping("/changePass")
    public String changPass(@RequestParam("prepass") String prepass, @RequestParam("nowpass") String nowpass,
                            Model model, HttpServletRequest request) {
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
    public String courseList(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                             QueryBean query,
                             Model model, HttpServletRequest request) {
        Teacher teacher = getLoginUser(request);
        List<Course> courses = new ArrayList<>();
        if (role_admin.equals(teacher.getRole())) {
            if (!query.isSetValue()) {
                courses = userService.getAllCourse();
            } else {
                courses = userService.query(query);
            }
            model.addAttribute("paging", pageService.subList(page, courses));
            return "teacher/adminCourseList";
        } else if (role_depHead.equals(teacher.getRole())) {
            if (!query.isSetValue()) {
                String institutionNumber = teacher.getInstitutionNumber();
                courses = userService.getCourseByInstitutionNumber(institutionNumber);
            } else {
                courses = userService.query(query);
            }
            model.addAttribute("paging", pageService.subList(page, courses));
            return "teacher/depHeadCourseList";
        } else {
            if (!query.isSetValue()) {
                // 普通老师
                String institutionNumber = teacher.getInstitutionNumber();
                courses = userService.getCourseByInstitutionNumber(institutionNumber);
            } else {
                courses = userService.query(query);
            }
            model.addAttribute("paging", pageService.subList(page, courses));
            return "teacher/courseList";
        }

    }

    // 点击新增课程时候的页面
    @RequestMapping("/insertCourse")
    public String insertCourse(Model model) {
        List<Institution> institutions = userService.getAllInstitution();
        model.addAttribute("insList", institutions);
        return "teacher/insertCourse";
    }

    @RequestMapping("/editCourse")
    public String editCourse(@Param("courseid") String courseid, Model model) {
        List<Institution> institutions = userService.getAllInstitution();
        Course course = userService.getCourseByCourseNumber(courseid);
        model.addAttribute("course", course);
        model.addAttribute("insList", institutions);
        return "teacher/editCourse";
    }

    // 点击新增课程页面，然后在页面里输入课程信息，点进新增插入某一门课程
    @RequestMapping("/insertCourseSuccess")
    public String insertCourseSuccess(Course course, Model model, HttpServletRequest request) {
        Teacher teacher = getLoginUser(request);
        //获取插入后的课程编号
        String institutionNumber = course.getInstitutionNumber();
        Institution institution = userService.getInstitutionByNumber(institutionNumber);
        course.setInstitutionName(institution.getName());
        userService.addCourse(course);
        model.addAttribute("paging", pageService.subList(1, userService.getChoosedCourseByTeacherNumber(teacher.getTeacherNumber())));
        return "teacher/courseList";
    }

    // 修改某个课程的信息，比如课程名
    @RequestMapping("/updateCourseSuccess")
    public String updateCourseSuccess(@RequestBody Course course, @Param("page") int page, Model model, HttpServletRequest request) {
        userService.updateCourse(course);
        model.addAttribute("paging", pageService.subList(page, userService.getAllCourse()));
        return "teacher/courseList";
    }

    // 删除某一门课程
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

    // 设置选课开始时间
    @RequestMapping("/setChooseStartTime")
    public String setChooseStartTime(ChooseStartTime chooseStartTime) {
        ChooseStartTime dbChooseStartTime = userService.getChooseStartTime();
        if (dbChooseStartTime == null) {
            //增加一行记录
            userService.saveChooseStartTime(chooseStartTime);
        } else {
            dbChooseStartTime.setStartTime(chooseStartTime.getStartTime());
            // 修改一行记录
            userService.updateChooseStartTime(dbChooseStartTime);
        }
        return "";
    }

    // 将老师上传的excel课程文档插入到数据库里
    @RequestMapping("/uploadCourseExcel")
    @ResponseBody
    public String uploadCourseExcel(HttpServletRequest request) throws Exception {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        Teacher teacher = getLoginUser(multipartHttpServletRequest);
//        if (!role_admin.equals(teacher.getRole())) {
//            return "has no permission";
//        }
        MultipartFile file = multipartHttpServletRequest.getFile("upfile");
        if (file.isEmpty()) {
            throw new Exception("文件不存在！");
        }
        InputStream in = file.getInputStream();
        List<List<Object>> lines = ImportExcelUtil.getBankListByExcel(in, file.getOriginalFilename());
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
            course.setStuTotal(Integer.valueOf((String) line.get(4)));
            course.setShiyanHour(Integer.valueOf((String) line.get(5)));
            course.setNote(String.valueOf(line.get(6)));
            course.setInstitutionNumber(String.valueOf(line.get(7)));
            course.setInstitutionNumber(String.valueOf(line.get(8)));
            courses.add(course);
        }
        userService.saveOrUpdateAll(courses);
        return "success";
    }

    // 当前老师选某门课
    @RequestMapping(value = "/selectCourse")
    @ResponseBody
    public String selectCourse(String courseNumber, HttpServletRequest request) {
        Teacher teacher = getLoginUser(request);
        userService.selectCourse(teacher.getTeacherNumber(),courseNumber);
        return "success";
    }

    // 当前老师取消选某门课
    @RequestMapping(value = "/unselectCourse")
    @ResponseBody
    public String unselectCourse(String courseNumber, HttpServletRequest request) {
        Teacher teacher = getLoginUser(request);
        userService.cancelSelectCourse(teacher.getTeacherNumber(),courseNumber);
        return "success";
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public String query(QueryBean query, Model model) {
        List<Course> courses = userService.query(query);
        model.addAttribute("paging", pageService.subList(1, courses));
        return "redirect:courseList?page=1";
    }

    // 课程查询首页
    @RequestMapping("/queryIndex")
    public String queryIndex() {
        return "teacher/shaixuan";
    }

    // 点击查询按钮时的处理，返回查询到的课程的table列表
    @RequestMapping("/fillQueryTable")
    @ResponseBody
    public Map<String, List<Course>> fillQueryTable(Model model, QueryBean query) {
        List<Course> courses = new ArrayList<>();
        if (query.isSetValue()) {
            courses = userService.query(query);
        } else {
            courses = userService.getAllCourse();
        }
        Map<String, List<Course>> pp = new HashMap<>();
        pp.put("data", courses);
        return pp;
    }

    // 下载excel
    @RequestMapping("/downloadExcel")
    @ResponseBody
    public void downloadExcel(QueryBean query, HttpServletResponse response) {
        response.setContentType("application/binary;charset=UTF-8");
        try {
            ServletOutputStream out = response.getOutputStream();
            try {
                //设置文件头：最后一个参数是设置下载文件名(这里我们叫：张三.pdf)
                String fileName = "选课" + (new Date()).toString() + ".xls";
                response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
            List<String> titles = Arrays.asList("课程编号", "课程名", "课程性质", "班级", "学生人数", "总学时数", "实验学时数", "备注", "所属系编号", "所属系名称");
            List<Course> courses = new ArrayList<>();
            if (query.isSetValue()) {
                courses = userService.query(query);
            } else {
                userService.getAllCourse();
            }
            ImportExcelUtil.exportExcel(courses, titles, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Teacher getLoginUser(HttpServletRequest request) {
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");
        return teacher;
    }
}
