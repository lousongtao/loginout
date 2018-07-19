<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>班次设定</title>
    <link href="${basePath}/resources/zheng-admin/plugins/bootstrap-3.3.0/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${basePath}/resources/zheng-admin/plugins/bootstrap-datetimepicker/bootstrap-datetimepicker.css"
          rel="stylesheet"/>
    <link href="${basePath}/resources/zheng-admin/plugins/material-design-iconic-font-2.2.0/css/material-design-iconic-font.min.css"
          rel="stylesheet"/>
    <link href="${basePath}/resources/zheng-admin/plugins/waves-0.7.5/waves.min.css" rel="stylesheet"/>
    <link href="${basePath}/resources/zheng-admin/plugins/jquery-confirm/jquery-confirm.min.css" rel="stylesheet"/>
    <link href="${basePath}/resources/zheng-admin/plugins/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css"
          rel="stylesheet"/>
    <link href="${basePath}/resources/zheng-admin/css/common.css" rel="stylesheet"/>
    <link href="${basePath}/resources/zheng-admin/plugins/fullcalendar-3.9.0/fullcalendar.min.css" rel="stylesheet"/>
    <link href="${basePath}/resources/zheng-admin/plugins/fullcalendar-3.9.0/fullcalendar.print.min.css"
          rel='stylesheet' media='print'/>
    <style>


    </style>
</head>
<body>
<div id="main">
    <div id="toolbar">
        <shiro:hasPermission name="ucenter:scheduling:write">
            <a class="waves-effect waves-button" href="javascript:;"
               onclick="editAction()"><i class="zmdi zmdi-plus"></i>
                编辑班次</a>
            <a class="waves-effect waves-button" href="javascript:;"
               onclick="saveAction()"><i class="zmdi zmdi-close"></i>
                保存班次</a>
        <%--</shiro:hasPermission>--%>
    </div>
</div>
<div class="calendarWrapper">
    <div id='pbgl_nav' style='display:none;width: 1002px;margin-left: 10px;'>
        <p id="pbgl_nav_p" style="font-weight:bold;margin-bottom: 5px; margin-top: 5px;"></p>
        <div id="pbgl_nav_div"></div>
    </div>
    <div id="pbgl_calendar" class="dib" style="overflow-y:auto"></div>
    <div class="paiban_xia_toolbar paiban_toolbar_container" id="paiban_toolbar_container" style="display:none;"></div>
</div>
<script src="${basePath}/resources/zheng-admin/plugins/jquery.1.12.4.min.js"></script>
<script src="${basePath}/resources/zheng-admin/plugins/bootstrap-datetimepicker/bootstrap-datetimepicker.min.js"></script>
<script src="${basePath}/resources/zheng-admin/plugins/bootstrap-datetimepicker/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="${basePath}/resources/zheng-admin/plugins/bootstrap-3.3.0/js/bootstrap.min.js"></script>
<script src="${basePath}/resources/zheng-admin/plugins/waves-0.7.5/waves.min.js"></script>
<script src="${basePath}/resources/zheng-admin/plugins/jquery-confirm/jquery-confirm.min.js"></script>
<%--<script src="${basePath}/resources/zheng-admin/js/common.js"></script>--%>
<script src="${basePath}/resources/zheng-admin/plugins/fullcalendar-3.9.0/moment.min.js"></script>
<script src="${basePath}/resources/zheng-admin/plugins/fullcalendar-3.9.0/zh-cn.js"></script>
<script src="${basePath}/resources/zheng-admin/plugins/fullcalendar-3.9.0/fullcalendar.min.js"></script>
<script>
    $(function () {
        $("#paiban_toolbar_container").hide();
        initCalender();

//        var currentView = $('#pbgl_calendar').fullCalendar('getView');
//        var monday = $.fullCalendar.formatDate(currentView.visStart, "yyyy年MM月dd日");
//        var sunday = $.fullCalendar.formatDate(currentView.visEnd, "yyyy年MM月dd日");
//        console.log("当前视图的第一天:" + monday)
//        console.log(sunday)
//        getShift();  //获取班次并创建弹出层
//        getUserGroup();     //获取员工分组信息


    });

    var calendar;

    function editAction() {
        alert("start edit")
    }

    function saveAction() {
        alert("save data")
    }

    /**
     * 初始化Calender
     */
    function initCalender() {
        calendar = $('#pbgl_calendar').fullCalendar({
            header: {
                left: '',
                center: '',
                right: 'prev,next today'
            },
            locale:'zh-cn',
            buttonText: {
                today: '本周',
            },
            defaultView: "basicWeek",
            firstday: 1
        });
        return calendar;
    }

    /**
     * 初始化日历标题
     */
    function initTitle() {
        //设定标题
        var weekEndTime = new Date(Giscafer.calenderVisEnd) - (24 * 60 * 60 * 1000);
        var monday = $.fullCalendar.formatDate(Giscafer.calenderVisStart,
            "yyyy年MM月dd日");
        var sunday = $.fullCalendar.formatDate(new Date(weekEndTime),
            "yyyy年MM月dd日");
        var titleHtml = "<span style='font-weight:bold;'>本周 (" + monday + " - " + sunday + ") 排班表</span>"
        $('#calendar .fc-header-left').html('');
        $('#calendar .fc-header-left').append(titleHtml);
    }


    /**
     * 获取班次信息
     */
    function getShift() {
        $.ajax({
            url: '${basePath}/manage/shift/getAllShift',
            type: 'POST',
            dataType: 'json'
        })
            .done(function (result) {
                if (result.code == 1) {
                    console.log(result.data);
                    alert("处理班次数据");

                }

            })
            .fail(function () {
                console.log("error");
            });
    }

    /**
     * 获取用户组信息
     */
    function getUserGroup() {
        $.ajax({
            url: '${basePath}/manage/group/getGroupUser',
            data: {limit: 100},
            type: 'POST',
            dataType: 'json'
        })
            .done(function (result) {
                if (result.code == 1) {
                    alert("处理用户组数据")
                    console.log(result.data);
                }
            })
            .fail(function () {
                console.log("error");
            });
    }

</script>
</body>
</html>