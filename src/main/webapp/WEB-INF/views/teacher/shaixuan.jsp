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
    <div class="layui-form" id="shaixuan-form" style="margin-top:10px;">
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">课程编号</label>
                <div class="layui-input-inline">
                    <input type="text" name="courseNumber" id="courseNumber" placeholder="课程编号" autocomplete="off"
                           class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">课程名称</label>
                <div class="layui-input-inline">
                    <input type="text" name="courseName" id="courseName" placeholder="课程名称" autocomplete="off"
                           class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">课程属性</label>
                <div class="layui-input-inline">
                    <input type="text" name="property" id="property" placeholder="课程属性" autocomplete="off"
                           class="layui-input">
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">班级</label>
                <div class="layui-input-inline">
                    <input type="text" name="className" id="className" placeholder="课程班级" autocomplete="off"
                           class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">学生人数</label>
                <div class="layui-input-inline">
                    <input type="text" name="stuTotal" id="stuTotal" placeholder="学生人数" autocomplete="off"
                           class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">学时数</label>
                <div class="layui-input-inline">
                    <input type="text" name="classHour" id="classHour" placeholder="学时数" autocomplete="off"
                           class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">实验学时数</label>
                <div class="layui-input-inline">
                    <input type="text" name="shiyanHour" id="shiyanHour" placeholder="实验学时数" autocomplete="off"
                           class="layui-input">
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">备注</label>
                <div class="layui-input-inline">
                    <input type="text" name="note" id="note" placeholder="请输入备注" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">所属系</label>
                <div class="layui-input-inline">
                    <input type="text" name="institutionName" id="institutionName" placeholder="所属系" autocomplete="off" class="layui-input">
                </div>
            </div>

            <div class="layui-inline">
                <div class="layui-input-inline">
                    <button class="layui-btn-normal layui-btn" id="shaixuan">筛选</button>
                </div>
            </div>
            <div class="layui-inline">
                <div class="layui-input-inline">
                    <button class="layui-btn-normal layui-btn" id="xiazai">下载Excel</button>
                </div>
            </div>
        </div>
    </div>
    <div id="queryTablediv">
        <table id="queryTable" class="display" cellspacing="0" width="100%">
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
            </tr>
            </thead>
        </table>
    </div>
</rapid:override>
<%@ include file="base.jsp" %>
<link rel="stylesheet" href="http://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css">
<script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
<%--<script src="https://cdn.bootcss.com/jquery.form/3.51/jquery.form.min.js"></script>--%>
<script src="http://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
<script>
    $(document).ready(function() {
        $("#xiazai").click(function () {
            var param = parseToParams(getData());
            window.location.href = "<%=basePath%>user/downloadExcel?" + param;
        });

        function parseToParams(data) {
            try {
                var tempArr = [];
                for (var i in data) {
                    var key = encodeURIComponent(i);
                    var value = encodeURIComponent(data[i]);
                    tempArr.push(key + '=' + value);
                }
                var urlParamsStr = tempArr.join('&');
                return urlParamsStr;
            } catch (err) {
                return '';
            }
        }

        function getData() {
            var courseNumber = $("#courseNumber").val();
            var courseName = $("#courseName").val();
            var property = $("#property").val();
            var className = $("#className").val();
            var stuTotal = $("#stuTotal").val();
            var classHour = $("#classHour").val();
            var shiyanHour = $("#shiyanHour").val();
            var note = $("#note").val();
            var institutionName = $("#institutionName").val();
            var json = {"courseNumber": courseNumber, "courseName":courseName, "property":property,"className":className,
                "stuTotal":stuTotal,"classHour":classHour,"shiyanHour":shiyanHour,"note":note,"institutionName":institutionName};
            return json;
        }

        $('#queryTable').DataTable({
            "ajax": {
                "url": "<%=basePath%>user/fillQueryTable",
                "type": "POST",
                "data": getData()
            },
            "columns": [
                {"data": "courseNumber"},
                {"data": "courseName"},
                {"data": "property"},
                {"data": "className"},
                {"data": "stuTotal"},
                {"data": "classHour"},
                {"data": "shiyanHour"},
                {"data": "note"},
                {"data": "institutionName"}
            ]
        });

        $("#shaixuan").click(function () {
            $.ajax({
                url:"<%=basePath%>user/fillQueryTable",
                data: getData(),
                success: function (e) {
                    initDetailTableData(e.data);
                }
            });
        });

        function initDetailTableData(dataArr) { //dataArr是表格数据数组，和初始化配置需一样的结构
            var table = $('#queryTable').dataTable();
            var oSettings = table.fnSettings(); //这里获取表格的配置
            table.fnClearTable(this); //动态刷新关键部分语句，先清空数据
            for (var i = 0, l = dataArr.length; i < l; i++) {
                table.oApi._fnAddData(oSettings, dataArr[i]); //这里添加一行数据
            }
            oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
            table.fnDraw();//绘制表格
        }

    });
</script>