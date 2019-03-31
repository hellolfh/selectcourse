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
    <div style="margin-top: 50px;">
        <form method="POST"  enctype="multipart/form-data" id="form1" action="uploadExcel/form">
            <label>上传文件: </label>
            <input id="upfile" type="file" name="upfile"><br> <br>
            <input type="button" value="ajax提交" id="btn" name="btn" >
        </form>
    </div>
</rapid:override>
<%@ include file="base.jsp" %>
<script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/jquery.form/3.51/jquery.form.min.js"></script>
<script>
    $("#btn").click(function () {
        $('#form1').ajaxSubmit({
            url: '<%=basePath%>user/uploadCourseExcel',
            dataType: 'text',
            success: resutlMsg,
            error: errorMsg
        });

        function resutlMsg(msg) {
            alert(msg);
            $("#upfile").val("");
        }

        function errorMsg() {
            alert("导入excel出错！");
        }
    });
</script>

