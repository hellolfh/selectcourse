<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<rapid:override name="head">
    <title>添加新课程</title>
</rapid:override>
<rapid:override name="content">
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    %>
    <c:if test="${sessionScope.user.role ne 'admin'}">
        您不是学院管理员, 没有权限
    </c:if>

    <c:if test="${sessionScope.user.role eq 'admin'}">
        <form class="layui-form" id="changeform" method="post" action="<%=basePath%>user/insertCourseSuccess" style="margin:80px 400px; width:450px;">
            <div class="layui-form-item">
                <label class="layui-form-label">课程编号</label>
                <div class="layui-input-block">
                    <input type="text" name="courseNumber" id="courseNumber" placeholder="请输入课程编号" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">课程名称</label>
                <div class="layui-input-block">
                    <input type="text" name="courseName" id="courseName" placeholder="请输入课程名称" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">课程属性</label>
                <div class="layui-input-block">
                    <input type="text" name="property" id="property" placeholder="请输入课程属性" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">班级</label>
                <div class="layui-input-block">
                    <input type="text" name="className" id="className" placeholder="请输入课程班级" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">学生人数</label>
                <div class="layui-input-block">
                    <input type="text" name="stuTotal" id="stuTotal" placeholder="请输入学生人数" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">学时数</label>
                <div class="layui-input-block">
                    <input type="text" name="classHour" id="classHour" placeholder="请输入学时数" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">实验学时数</label>
                <div class="layui-input-block">
                    <input type="text" name="shiyanHour" id="shiyanHour" placeholder="请输入实验学时数" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">备注</label>
                <div class="layui-input-block">
                    <input type="text" name="note" placeholder="请输入备注" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">所属系</label>
                <div class="layui-input-block">
                    <c:forEach items="${insList}" var="ins">
                        <input type="radio" name="institutionNumber" value="${ins.institutionNumber}" title="${ins.name}">
                    </c:forEach>
                </div>
            </div>
            <input type="submit" id="success" class="layui-btn layui-btn-danger layui-btn-lg" style="margin:0 550px;">
            </input>
        </form>

        <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
        <script>
            $(function () {
                $("#success1").click(function () {

                    var name = $("#name").val();
                    var num = $("#num").val();
                    var institutionNumber = "";
                    var count=0;
                    institutionNumber = $("input[name='institutionName']:checked").val();
                    var content=name+"|"+num+"|"+ins;
                    var myform=document.createElement("form");
                    myform.id = "form1";
                    myform.name = "form1";
                    document.body.appendChild(myform);
                    var input = document.createElement("input");
                    input.type = "text";
                    input.name = "content";
                    input.value = encodeURIComponent(encodeURIComponent(content));
                    myform.appendChild(input);
                    myform.method = "POST";
                    myform.action = "<%=basePath%>teacher/insertCourseSuccess?page="+1;
                    myform.submit();
                    document.body.removeChild(myform);
                })
            })
        </script>
    </c:if>

</rapid:override>
<%@ include file="base.jsp" %>
