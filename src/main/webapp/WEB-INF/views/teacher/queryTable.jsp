<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: xuwenqing
  Date: 2019-03-31
  Time: 10:59
  To change this template use File | Settings | File Templates.
--%>
<table class="layui-table" style="margin-top:15px;">
    <colgroup>
        <col width="50">
        <col width="120">
        <col width="100">
        <col width="180">
        <col width="50">
        <col width="50">
        <col width="50">
        <col width="180">
    </colgroup>
    <thead>
    <tr>
        <th>课程编号</th>
        <th>课程名称</th>
        <th>课程属性</th>
        <th>班级</th>
        <th>学生人数</th>
        <th>学时数</th>
        <th>实验学时数</th>
        <th>备注</th>
        <th>所属系</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${courses}" var="course">
        <tr>
            <td>${course.courseNumber}</td>
            <td>${course.courseName}</td>
            <td>${course.property}</td>
            <td>${course.className}</td>
            <td>${course.stuTotal}</td>
            <td>${course.classHour}</td>
            <td>${course.shiyanHour}</td>
            <td>${course.note}</td>
            <td>${course.institutionName}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
