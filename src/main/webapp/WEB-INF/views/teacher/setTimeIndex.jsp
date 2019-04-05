<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<rapid:override name="head">
    <title>课程信息</title>
</rapid:override>
<rapid:override name="content">
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    %>
    <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/jquery.form/3.51/jquery.form.min.js"></script>
    <h2 style="color: red">学院管理员可以设置选课开始时间</h2>
    <c:if test="${sessionScope.user.role ne 'admin'}">
        <h1 style="color: red">您不是学院管理员, 没有权限</h1>
    </c:if>
    <c:if test="${sessionScope.user.role eq 'admin'}">
        <div>
            <form class="layui-form" id="setChooseStartTime" method="post" action="<%=basePath%>user/setChooseStartTime" style="margin:80px;">
                <div class="layui-form-item">
                    <label class="layui-form-label">开始时间</label>
                    <div class="layui-input-block">
                        <input type="text" name="startTime" value="${startTime}" id="startTime" placeholder="选课开始时间,格式是:2019-01-01 11:11:11" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-input-block" style="margin-left:500px;">
                    <input type="submit" id="success" class="layui-btn layui-btn-danger layui-btn-lg">
                    </input>
                </div>



            </form>
        </div>
    </c:if>
    <script>
        function unselect_course(courseNumber) {
            var r = confirm("确认删除吗？");
            if (r == true) {

            }
            $.ajax({
                url:"<%=basePath%>user/unselectCourse?courseNumber=" + courseNumber,
                type:"post",
                success:function (e) {
                    window.location.href = "<%=basePath%>user/courseList?page=1";
                }
            });
        }
    </script>
</rapid:override>
<%@ include file="base.jsp" %>
