<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<rapid:override name="head">
    <title>修改课程</title>
</rapid:override>
<rapid:override name="content">
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    %>
    <h2 style="color: red">系管理员可以修改课程的全部属性，系主任只能修改课程的选课老师</h2>
    <c:set var="role" scope="session" value="${sessionScope.currentUser.role}"/>
    <form class="layui-form" id="changeform" method="post" action="<%=basePath%>user/updateCourseSuccess" style="margin:80px 400px; width:450px;">
        <div class="layui-form-item">
            <label class="layui-form-label">课程编号</label>
            <div class="layui-input-block">
                <input type="text" name="courseNumber" value="${course.courseNumber}" id="courseNumber" placeholder="请输入课程编号" autocomplete="off" class="layui-input"
                    <c:if test="${role ne 'admin'}">readonly</c:if>>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">课程名称</label>
            <div class="layui-input-block">
                <input type="text" name="courseName" value="${course.courseName}" id="courseName" placeholder="请输入课程名称" autocomplete="off" class="layui-input"
                       <c:if test="${role ne 'admin'}">readonly</c:if>>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">课程属性</label>
            <div class="layui-input-block">
                <input type="text" name="property" value="${course.property}" id="property" placeholder="请输入课程属性" autocomplete="off" class="layui-input"
                       <c:if test="${role ne 'admin'}">readonly</c:if>>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">班级</label>
            <div class="layui-input-block">
                <input type="text" name="className" value="${course.className}" id="className" placeholder="请输入课程班级" autocomplete="off" class="layui-input"
                       <c:if test="${role ne 'admin'}">readonly</c:if>>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">学生人数</label>
            <div class="layui-input-block">
                <input type="text" name="stuTotal" value="${course.stuTotal}" id="stuTotal" placeholder="请输入学生人数" autocomplete="off" class="layui-input"
                       <c:if test="${role ne 'admin'}">readonly</c:if>>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">学时数</label>
            <div class="layui-input-block">
                <input type="text" name="classHour" value="${course.classHour}" id="classHour" placeholder="请输入学时数" autocomplete="off" class="layui-input"
                       <c:if test="${role ne 'admin'}">readonly</c:if>>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">实验学时数</label>
            <div class="layui-input-block">
                <input type="text" name="shiyanHour" value="${course.shiyanHour}" id="shiyanHour" placeholder="请输入实验学时数" autocomplete="off" class="layui-input"
                       <c:if test="${role ne 'admin'}">readonly</c:if>>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">备注</label>
            <div class="layui-input-block">
                <input type="text" name="note" value="${course.note}" placeholder="请输入备注" autocomplete="off" class="layui-input"
                       <c:if test="${role ne 'admin'}">readonly</c:if>>
            </div>
        </div>
        <div class="layui-form-item" >
            <label class="layui-form-label">所属系</label>
            <div class="layui-input-block">
                <c:forEach items="${insList}" var="ins">
                    <input type="radio" name="institutionNumber" value="${ins.institutionNumber}" title="${ins.name}"
                    <c:if test="${ins.institutionNumber eq course.institutionNumber}"> checked="checked" </c:if>
                           <c:if test="${role ne 'admin'}">readonly</c:if>/>
                </c:forEach>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">已选课老师</label>
            <div class="layui-input-block">
                <input type="text" name="teacherNumber" value="${course.teacherNumber}" placeholder="已选课老师编号" autocomplete="off" class="layui-input">
                <span>${course.teacherName}</span>
            </div>
        </div>

        <div class="layui-input-block" style="margin-left:500px;">
            <input type="submit" id="success" class="layui-btn layui-btn-danger layui-btn-lg">
            </input>
        </div>
    </form>

    <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
    <script>
    </script>
</rapid:override>
<%@ include file="base.jsp" %>
