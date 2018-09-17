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
    <title>Group Management</title>
    <jsp:include page="/resources/inc/head.jsp" flush="true"/>
</head>
<body>
<div id="main">
    <div id="toolbar">
    </div>
    <h3 id="headTitle" align="center">scheduling Table</h3>
    <div class="btn-group" role="group" aria-label="...">
        <button id="lastWeek" type="button" class="btn btn-default">Last Week</button>
        <button id="thisWeek" type="button" class="btn btn-primary">This Week</button>
        <button id="nextWeek" type="button" class="btn btn-default">Next Week</button>
    </div>
    <table id="table"></table>
</div>
<div>
</div>
<jsp:include page="/resources/inc/footer.jsp" flush="true"/>
<script>
    var $table = $('#table');
    var pageToday;
    var pageMonday;
    var pageSunday;
    $(function () {
        initWeekStartAndEnd();
        editHeadTitle();
        // bootstrap table初始化
        $table.bootstrapTable({
            url: '${basePath}/manage/staff/schedule',
            height: getHeight(),
            method: 'post',
            striped: true,
            sidePagination: 'server',
            singleSelect: true,
            smartDisplay: false,
            escape: true,
            maintainSelected: true,
            queryParams: queryParams,
            toolbar: '#toolbar',
            columns: [
                {field: 'uName', title: 'Name', align: 'center'},
                {field: 'name', title: 'Group', align: 'center'},
                {field: 'periodTime', title: 'Period Time', align: 'center'},
                {field: 'schedulingDate', title: 'Scheduling Date', align: 'center',formatter: 'formatEsDate'},
                {field: 'branchName', title: 'Branch', align: 'center'},
                {field: 'totalTime', title: 'Total Time(hour)', align: 'center'},
                {field: 'result', title: 'Result', align: 'center',formatter: 'resultFormatter'}
            ]
        });

        $("#lastWeek").click(function () {
            pageMonday = addDay(pageMonday, -7);
            pageSunday = addDay(pageSunday, -7);
            editHeadTitle();
//            重新加载数据
        });
        $("#thisWeek").click(function () {
            initWeekStartAndEnd();
            editHeadTitle();
        });
        $("#nextWeek").click(function () {
            pageMonday = addDay(pageMonday, 7);
            pageSunday = addDay(pageSunday, 7);
            editHeadTitle();
        });

    });

    function resultFormatter(value, row, index){
        if(value==0){
            return '<span class="label label-default">unknown</span>';
        }else if(value==1){
            return '<span class="label label-success">finished</span>';
        }else if(value==2){
            return '<span class="label label-danger">unfinished</span>';
        }else {
            return "--"
        }
    }
    //设置查询参数
    function queryParams(params) {
        var param = {
            pageToday: transferDate(pageToday),
            pageMonday: transferDate(pageMonday),
            pageSunday: transferDate(pageSunday)
        }
        return param;
    }
    //获取传入日期做加减运算
    function addDay(pageDate, addDayCount) {
        var dt = new Date(pageDate);
        dt.setDate(pageDate.getDate() + addDayCount);
        return dt;
    }

    //获取本周起始日期和结束日期
    function initWeekStartAndEnd() {
        pageToday = new Date();
//        console.log("初始化今天" + transferDate(pageToday));
        var $date = pageToday;
        //今天是这周的第几天
        var today = $date.getDay();
        //上周日距离今天的天数（负数表示）
        var stepSunDay = -today + 1;

        // 如果今天是周日
        if (today == 0) {
            stepSunDay = -7;
        }
        // 周一距离今天的天数（负数表示）
        var stepMonday = 7 - today;
        var time = $date.getTime();
        var monday = new Date(time + stepSunDay * 24 * 3600 * 1000);
        pageMonday = monday;
//        console.log("初始化本周一" + transferDate(pageMonday));
        var sunday = new Date(time + stepMonday * 24 * 3600 * 1000);
        pageSunday = sunday;
//        console.log("初始化本周天" + transferDate(pageSunday));
        //标题也要跟着初始化
        var weekStartDateAndEndDate = new Array();
        weekStartDateAndEndDate.push(monday, sunday);
        return weekStartDateAndEndDate;
    }

    function editHeadTitle() {
        var pageMondayStr = formatEsDate(pageMonday);
        var pageSundayStr = formatEsDate(pageSunday);
        $("#headTitle").html(pageMondayStr + " To " + pageSundayStr);
        $table.bootstrapTable('refresh');
    }

    //日期格式化 yyyy-MM-dd
    function transferDate(date) {
        // 年
        var year = date.getFullYear();
        // 月
        var month = date.getMonth() + 1;
        // 日
        var day = date.getDate();

        if (month >= 1 && month <= 9) {

            month = "0" + month;
        }
        if (day >= 0 && day <= 9) {

            day = "0" + day;
        }

        var dateString = year + '-' + month + '-' + day;

        return dateString;
    }
    //将时间转换成美式英语的时间格式,eg:3rd May 2018
    function formatEsDate($date) {
        var date = new Date($date);
        var monthArr = new Array("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Spt", "Oct", "Nov", "Dec");
        var suffix = new Array("st", "nd", "rd", "th");
        var week = new Array("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun");

        var year = date.getFullYear(); //年
        var month = monthArr[date.getMonth()]; //月
        var ddate = date.getDate(); //日

        if (ddate % 10 < 1 || ddate % 10 > 3) {
            ddate = ddate + suffix[3];
        } else if (ddate % 10 == 1) {
            ddate = ddate + suffix[0];
        } else if (ddate % 10 == 2) {
            ddate = ddate + suffix[1];
        } else {
            ddate = ddate + suffix[2];
        }
        var sweek = "Sun";
        if (date.getDay() > 0)
            sweek = week[date.getDay() - 1];
        return sweek + " " + ddate + " " + month + " " + year;
    }
</script>
</body>
</html>