<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<rapid:override name="head">
    <title>首页</title>
</rapid:override>
<rapid:override name="content">
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    %>
    <div style="width:600px; height:450px; margin:100px 350px; border:3px solid gray;">
        <h2 style="text-align: center; margin-top: 25px; margin-bottom: 25px;">选课系统（教师版）</h2>
        <h3 style="margin-left:50px; margin-bottom: 10px;">1.登陆后请及时修改初始密码（个人资料）</h3>
        <h3 style="margin-left:50px; margin-bottom: 10px;">2.在（我的课程信息）中进行课程开设、已开设课程的查看、管理</h3>
        <div>
            <form class="setChooseStartTime-form" id="setChooseStartTime" method="post" action="<%=basePath%>user/setChooseStartTime" style="margin:80px 400px; width:450px;">
                <div class="layui-form-item">
                    <label class="layui-form-label">选课开始时间</label>
                    <div class="layui-input-block">
                        <input type="text" name="courseNumber" value="${chooseStartTime.startTime}" id="courseNumber" placeholder="请输入选课开始时间" autocomplete="off" class="layui-input">
                    </div>
                </div>
            </form>
        </div>

    </div>
    <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/jquery.form/3.51/jquery.form.min.js"></script>
</rapid:override>
<%@ include file="base.jsp"%>