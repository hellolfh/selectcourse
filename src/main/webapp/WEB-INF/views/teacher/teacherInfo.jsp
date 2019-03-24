<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<rapid:override name="head">
    <title>个人资料</title>
</rapid:override>
<rapid:override name="content">
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    %>
    <table class="layui-table" style="margin:80px 400px; width:450px;">
        <colgroup>
            <col width="200">
            <col width="250">
            <col width="250">
        </colgroup>
        <tbody>
        <tr style="height:40px;">
            <td>教师号</td>
            <td>${teacher.teacherNumber}</td>
        </tr>
        <tr>
            <td>姓名</td>
            <td>${teacher.name}</td>
        </tr>
        <tr>
            <td>密码</td>
            <td>${teacher.password}</td>
        </tr>
        <tr>
            <td>学院</td>
            <td>${teacher.institutionName}</td>
        </tr>
        <tr>
            <td>角色</td>
            <td>${teacher.role}</td>
        </tr>
        </tbody>
    </table>
    <button class="layui-btn layui-btn-danger  layui-btn-lg" onclick="change()" style="margin:0 550px;">
        修改密码
    </button>
    <script>
        function change() {
            window.location.href="<%=basePath%>user/editPass";
        }
    </script>
</rapid:override>
<%@ include file="base.jsp" %>
