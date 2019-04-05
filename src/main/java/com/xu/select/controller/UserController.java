package com.xu.select.controller;

import com.xu.select.DateUtils;
import com.xu.select.bean.CourseBean;
import com.xu.select.bean.QueryBean;
import com.xu.select.excel.ImportExcelUtil;
import com.xu.select.model.ChooseStartTime;
import com.xu.select.model.Course;
import com.xu.select.model.Institution;
import com.xu.select.model.Teacher;
import com.xu.select.service.PageService;
import com.xu.select.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
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
@SessionAttributes(value ={"currentUser"},types={Teacher.class})
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
    public String index(HttpServletRequest request, Model model) {
        Teacher teacher = getLoginUser(request);
        if (teacher == null) {
            return "redirect:login";
        }
        model.addAttribute("currentUser", teacher);
        return "teacher/teacherIndex";
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
        model.addAttribute("currentUser", teacher);
        return "teacher/teacherIndex";
    }

    // 当前用户退出登录
    @RequestMapping("exit")
    public String exit(HttpServletRequest request) {
        request.getSession().invalidate();
        return "login";
    }

    //当前登录者的信息页面
    @RequestMapping("/info")
    public String userInfo(@RequestParam("teacherNumber") String teacherNumber, Model model, HttpServletRequest request) {
        Teacher teacher = getLoginUser(request);
        if (teacher == null) {
            return "redirect:login";
        }
        model.addAttribute("currentUser", teacher);
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
        if (teacher == null) {
            return "redirect:login";
        }
        List<Course> courses = new ArrayList<>();
        if (role_admin.equals(teacher.getRole())) {
            if (!query.isSetValue()) {
                courses = userService.getAllCourse();
            } else {
                courses = userService.query(query);
            }
        } else if (role_depHead.equals(teacher.getRole())) {
            // 系主任可以看到该系的所有的课程
            if (!query.isSetValue()) {
                String institutionNumber = teacher.getInstitutionNumber();
                courses = userService.getCourseByInstitutionNumber(institutionNumber);
            } else {
                courses = userService.query(query);
            }
        } else {
            if (!query.isSetValue()) {
                // 普通老师也可以看到所在系的所有的课程
                String institutionNumber = teacher.getInstitutionNumber();
                courses = userService.getCourseByInstitutionNumber(institutionNumber);
            } else {
                courses = userService.query(query);
            }
        }
        List<CourseBean> courseBeans = userService.convertToCourseBeanList(courses);
        model.addAttribute("paging", pageService.subList(page, courseBeans));
        model.addAttribute("ifStartSelectCourse", ifStartSelectCourse());
        ChooseStartTime chooseStartTime = userService.getChooseStartTime();
        model.addAttribute("startTime", DateUtils.getDate2String(DateUtils.YYYY_MM_DD_HH_MM_SS, chooseStartTime.getStartTime()));
        return "teacher/courseList";
    }

    // 点击新增课程时候的页面
    @RequestMapping("/insertCourse")
    public String insertCourse(Model model, HttpServletRequest request) {
        Teacher teacher = getLoginUser(request);
        if (teacher == null) {
            return "redirect:login";
        }
        List<Institution> institutions = userService.getAllInstitution();
        model.addAttribute("insList", institutions);
        return "teacher/insertCourse";
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
        return "redirect:courseList?page=1";
    }

    // 点击修改课程时候进入的页面
    @RequestMapping("/editCourse")
    public String editCourse(@Param("courseid") String courseid, Model model) {
        List<Institution> institutions = userService.getAllInstitution();
        Course course = userService.getCourseByCourseNumber(courseid);
        model.addAttribute("course", userService.convertToCourseBean(course));
        model.addAttribute("insList", institutions);
        return "teacher/editCourse";
    }


    // 在修改课程页面修改某个课程的信息，比如课程名，点击确定后的页面
    @RequestMapping("/updateCourseSuccess")
    public String updateCourseSuccess(CourseBean course, Model model, HttpServletRequest request) {
        userService.updateCourse(course);
        return "redirect:courseList?page=1";
    }

    // 删除某一门课程
    @RequestMapping("/deleteCourse")
    public String deleteCourse(@Param("courseid") String courseid, Model model, HttpServletRequest request) {
        userService.deleteCourse(courseid);
        model.addAttribute("paging", pageService.subList(1, userService.getAllCourse()));
        return "redirect:courseList?page=1";
    }

    //
    @RequestMapping("/detailCourse")
    public String detailCourse(@Param("courseid") String courseid, Model model, HttpServletRequest request) {
        Course course = userService.getCourseByCourseNumber(courseid);
        model.addAttribute("detail", course);
        return "teacher/courseDetail";
    }

    // 点击excel导入进入的页面
    @RequestMapping("/excelImportIndex")
    public String excelImportIndex(Model model, HttpServletRequest request) {
        Teacher teacher = getLoginUser(request);
        if (teacher == null) {
            return "redirect:login";
        }
        return "teacher/excelImport";
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


    // 点击下载excel按钮时候的处理逻辑
    @RequestMapping("/downloadExcel")
    @ResponseBody
    public void downloadExcel(QueryBean query, HttpServletResponse response) {
        response.setContentType("application/binary;charset=UTF-8");
        try {
            ServletOutputStream out = response.getOutputStream();
            try {
                //设置文件头：最后一个参数是设置下载文件名(这里我们叫：张三.pdf)
                String fileName = "选课" + DateUtils.getDate2String(DateUtils.YYYY_MM_DD_HH_MM_SS, new Date()) + ".xls";
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

    // 没用了
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public String query(QueryBean query, Model model) {
        List<Course> courses = userService.query(query);
        model.addAttribute("paging", pageService.subList(1, courses));
        return "redirect:courseList?page=1";
    }

    // 课程查询首页
    @RequestMapping("/queryIndex")
    public String queryIndex(HttpServletRequest request, Model model) {
        Teacher teacher = getLoginUser(request);
        if (teacher == null) {
            return "redirect:login";
        }
        model.addAttribute("ifStartSelectCourse", ifStartSelectCourse());
        ChooseStartTime chooseStartTime = userService.getChooseStartTime();
        model.addAttribute("startTime", DateUtils.getDate2String(DateUtils.YYYY_MM_DD_HH_MM_SS, chooseStartTime.getStartTime()));
        return "teacher/shaixuan";
    }

    // 在课程查询页面点击查询按钮时的处理，返回查询到的课程的table列表
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


    // 设置选课开始时间
    @RequestMapping("/setChooseStartTime")
    public String setChooseStartTime(String startTime) {
        ChooseStartTime chooseStartTime = new ChooseStartTime();
        Date date = DateUtils.getString2Date(DateUtils.YYYY_MM_DD_HH_MM_SS, startTime);
        chooseStartTime.setStartTime(date);
        ChooseStartTime dbChooseStartTime = userService.getChooseStartTime();
        if (dbChooseStartTime == null) {
            //增加一行记录
            userService.saveChooseStartTime(chooseStartTime);
        } else {
            dbChooseStartTime.setStartTime(chooseStartTime.getStartTime());
            // 修改一行记录
            userService.updateChooseStartTime(dbChooseStartTime);
        }
        return "redirect:setTimeIndex";
    }

    @RequestMapping("/setTimeIndex")
    public String setTimeIndex(Model model) {
        ChooseStartTime chooseStartTime = userService.getChooseStartTime();
        model.addAttribute("startTime", DateUtils.getDate2String(DateUtils.YYYY_MM_DD_HH_MM_SS, chooseStartTime.getStartTime()));
        return "/teacher/setTimeIndex";
    }

    public Teacher getLoginUser(HttpServletRequest request) {
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");
        return teacher;
    }

    /**
     * 如果设置了选课时间，选课时间比现在时间晚就没到选课时候，返回false。
     * @return
     */
    public boolean ifStartSelectCourse() {
        ChooseStartTime chooseStartTime = userService.getChooseStartTime();
        Date now = new Date();
        boolean ifStartSelectCourse = true;
        if (chooseStartTime.getStartTime() != null && chooseStartTime.getStartTime().after(now)) {
            ifStartSelectCourse = false;
        }
        return ifStartSelectCourse;
    }
}
