<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
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

    <form class="layui-form" style="margin:10px 15px 10px;">
        <div class="layui-form-item">
            <div class="layui-input-block">
                <div style="display: inline-block">
                    <div style="width: 180px; float: left; margin-left:-100px;">
                        <select class="layui-select" id="teasearch">
                            <option value="0">教师姓名</option>
                            <c:forEach items="${teaList}" var="teacher">
                                <option value="${teacher.teaId}">${teacher.teaName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <button type="button" id="tea" class="layui-btn" style="margin-left:10px;">筛选</button>
                </div>
                <div style="display: inline-block">
                    <div style="width: 180px; float: left; margin-left:300px;">
                        <select class="layui-select" id="inssearch">
                            <option value="0">学院名称</option>
                            <c:forEach items="${insList}" var="institution">
                                <option value="${institution.insId}">${institution.insName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <button type="button" id="ins" class="layui-btn" style="margin-left:10px;">筛选</button>
                </div>
                <button type="button" class="layui-btn" style="float:right;" onclick="search()">搜索</button>
                <input type="text" id="search" class="layui-input" style="float:right; width:200px;"
                       placeholder="请输入课程编号">
            </div>
        </div>
    </form>

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
        <c:forEach items="${paging.dataList}" var="course">
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
                <td>
                    <button class="layui-btn" onclick="select_course(${course.courseNumber})">选课</button>
                    <button class="layui-btn" onclick="unselect_course(${course.courseNumber})">退课</button>
                    <button class="layui-btn" onclick="edit_course(${course.courseNumber})">修改</button>
                    <button class="layui-btn" onclick="delete_course(${course.courseNumber})">删除</button>
                    <button class="layui-btn" onclick="detail_course(${course.courseNumber})">管理</button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div style="text-align:center; margin-top:10px; margin-left:-100px;">
        <c:if test="${paging.totalPage >=0}">
            <p style=" color: black; font-size:16px; margin-bottom:10px;">当前第 ${paging.currentPage }
                页/共 ${paging.totalPage} 页</p>
            <c:choose>
                <c:when test="${paging.totalPage==0}">
                    <button class="layui-btn layui-btn-disabled" onclick="goPage(1)">首页</button>
                    <button class="layui-btn layui-btn-disabled" onclick="goPage(${paging.currentPage-1})">上一页</button>
                    <button class="layui-btn layui-btn-disabled" onclick="goPage(${paging.currentPage+1})">下一页</button>
                    <button class="layui-btn layui-btn-disabled" onclick="goPage(${paging.totalPage})">末页</button>
                </c:when>
                <c:when test="${paging.currentPage==1 && paging.totalPage==1}">
                    <button class="layui-btn" onclick="goPage(1)">首页</button>
                    <button class="layui-btn layui-btn-disabled" onclick="goPage(${paging.currentPage-1})">上一页</button>
                    <button class="layui-btn layui-btn-disabled" onclick="goPage(${paging.currentPage+1})">下一页</button>
                    <button class="layui-btn" onclick="goPage(${paging.totalPage})">末页</button>
                </c:when>
                <c:when test="${paging.currentPage==1 && paging.totalPage>1}">
                    <button class="layui-btn" onclick="goPage(1)">首页</button>
                    <button class="layui-btn layui-btn-disabled" onclick="goPage(${paging.currentPage-1})">上一页</button>
                    <button class="layui-btn" onclick="goPage(${paging.currentPage+1})">下一页</button>
                    <button class="layui-btn" onclick="goPage(${paging.totalPage})">末页</button>
                </c:when>
                <c:when test="${paging.currentPage>1 && paging.currentPage<paging.totalPage}">
                    <button class="layui-btn" onclick="goPage(1)">首页</button>
                    <button class="layui-btn" onclick="goPage(${paging.currentPage-1})">上一页</button>
                    <button class="layui-btn" onclick="goPage(${paging.currentPage+1})">下一页</button>
                    <button class="layui-btn" onclick="goPage(${paging.totalPage})">末页</button>
                </c:when>
                <c:when test="${paging.currentPage>1 && paging.currentPage==paging.totalPage}">
                    <button class="layui-btn" onclick="goPage(1)">首页</button>
                    <button class="layui-btn" onclick="goPage(${paging.currentPage-1})">上一页</button>
                    <button class="layui-btn layui-btn-disabled" onclick="goPage(${paging.currentPage+1})">下一页</button>
                    <button class="layui-btn" onclick="goPage(${paging.totalPage})">末页</button>
                </c:when>
            </c:choose>
        </c:if>
    </div>
    <script>
        function goPage(page) {
            window.location.href = "<%=basePath%>user/courseList?page=" + page;
        }

        function select_course(courseNumber) {
            $.ajax({
                url:"<%=basePath%>user/courseList",
                type:post,
                data: {"courseNumber":courseNumber}
            });
            window.location.href = "<%=basePath%>user/editCourse?courseid=" + classId;
        }

        function unselect_course(courseNumber) {
            window.location.href = "<%=basePath%>user/editCourse?courseid=" + classId;
        }
        function edit_course(courseNumber) {
            window.location.href = "<%=basePath%>user/editCourse?courseid=" + courseNumber;
        }

        function delete_course(courseNumber) {
            var r = confirm("确认删除吗？");
            if (r == true) {
                window.location.href = "<%=basePath%>user/deleteCourse?courseid=" + courseNumber;
            } else {
                return;
            }
        }
        function detail_course(classId) {
            window.location.href = "<%=basePath%>user/detailCourse?courseid=" + classId + "&page=" + 1;
        }
    </script>
</rapid:override>
<%@ include file="base.jsp" %>
