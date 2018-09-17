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
    <title>Staff Management</title>
    <jsp:include page="/resources/inc/head.jsp" flush="true"/>
</head>
<body>
<div id="main">
    <h3 id="headTitle" align="center">sign statistics Table</h3>
    <div id="toolbar">
        <div class="btn-group" role="group" aria-label="...">
            <button id="lastWeek" type="button" class="btn btn-default">Last Week</button>
            <button id="thisWeek" type="button" class="btn btn-primary">This Week</button>
            <button id="nextWeek" type="button" class="btn btn-default">Next Week</button>
        </div>
    </div>

    <table id="table"></table>
</div>
<jsp:include page="/resources/inc/footer.jsp" flush="true"/>
<script>
    //定义页面当天,以及页面周一和周天
    var pageToday;
    var pageMonday;
    var pageSunday;
    var $table = $('#table');
    var myColumns = new Array();


    $(function () {
        //初始化本周日期
        initWeekStartAndEnd();
        //初始化列
        initColumns();
        editHeadTitle();
        // bootstrap table初始化
        $table.bootstrapTable({
            url: '${basePath}/manage/sign/queryrecord',
            method: 'post',
            height: getHeight(),
            striped: true,
            sidePagination: 'server',
            showFooter: true,
            escape: true,
            queryParams: queryParams,
            columns: myColumns,
            onLoadSuccess : function(data){
                if (data.code == 0){
                    alert(data.data);
                }
            },
            onLoadError: function(result){
                console.log('failed');
                console.log(result);
            }
        });
        //点击事件
        $("#lastWeek").click(function () {
            pageMonday = addDay(pageMonday, -7);
            pageSunday = addDay(pageSunday, -7);
            editHeadTitle();
            getData();
        });
        $("#thisWeek").click(function () {
            initWeekStartAndEnd();
            editHeadTitle();
            getData();
        });
        $("#nextWeek").click(function () {
            pageMonday = addDay(pageMonday, 7);
            pageSunday = addDay(pageSunday, 7);
            editHeadTitle();
            getData();
        });
    });

    function staffNameFormatter(value){
        return value.realname;
    }

    //更改头标题
    function editHeadTitle() {
        var pageMondayStr = formatEsDate(pageMonday);
        var pageSundayStr = formatEsDate(pageSunday);
        $("#headTitle").html(pageMondayStr + " To " + pageSundayStr + " Scheduling");
    }

    function queryParams(){
        var param = {
            pageToday: transferDate(pageToday),
            pageMonday: transferDate(pageMonday),
            pageSunday: transferDate(pageSunday)
        }
        return param;
    }

    //日期格式化 yyyy-MM-dd
    function transferDate(date) {
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
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

    //获取本周起始日期和结束日期
    function initWeekStartAndEnd() {
        pageToday = new Date();
        pageMonday = getMonday(pageToday);
        pageSunday = getSunday(pageToday);
        //标题也要跟着初始化
        var weekStartDateAndEndDate = new Array();
        weekStartDateAndEndDate.push(pageMonday, pageSunday);
        return weekStartDateAndEndDate;
    }

    function getMonday(d1){
        var day = d1.getDay();
        var oneday = 24 * 60 * 60 *1000;
        switch (day) {
            case 0: return new Date(d1.getTime() - 6 * oneday) ;
            case 1: return d1;
            case 2: return new Date(d1.getTime() - 1 * oneday) ;
            case 3: return new Date(d1.getTime() - 2 * oneday) ;
            case 4: return new Date(d1.getTime() - 3 * oneday) ;
            case 5: return new Date(d1.getTime() - 4 * oneday) ;
            case 6: return new Date(d1.getTime() - 5 * oneday) ;
        }
    }

    function getSunday(d1){
        var day = d1.getDay();
        var oneday = 24 * 60 * 60 * 1000;
        switch (day) {
            case 0: return d1;
            case 1: return new Date(d1.getTime() + 6 * oneday);
            case 2: return new Date(d1.getTime() + 5 * oneday);
            case 3: return new Date(d1.getTime() + 4 * oneday);
            case 4: return new Date(d1.getTime() + 3 * oneday);
            case 5: return new Date(d1.getTime() + 2 * oneday);
            case 6: return new Date(d1.getTime() + 1 * oneday);
        }
    }

    //获取传入日期做加减运算
    function addDay(pageDate, addDayCount) {
        var dt = new Date(pageDate);
        dt.setDate(pageDate.getDate() + addDayCount);
        return dt;
    }

    //更改头标题
    function editHeadTitle() {
        var pageMondayStr = formatEsDate(pageMonday);
        var pageSundayStr = formatEsDate(pageSunday);
        $("#headTitle").html(pageMondayStr + " To " + pageSundayStr + " Sign Report");
    }

    //将时间转换成美式英语的时间格式,eg:3rd May 2018
    function formatEsDate($date) {
        var date = new Date($date);
        var monthArr = new Array("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
        var suffix = new Array("st", "nd", "rd", "th");

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
        return ddate + " " + month + " " + year;
    }

    //重新渲染数据,并更改列名
    function getData() {
        initColumns();
        $table.bootstrapTable(
            "refreshOptions",
            {
                columns: myColumns,
            }
        );
    }

    //周数据格式化
    function weekDateFormat(value, row, index) {
        if (value && value.periodTime) {
            return value.uName + "<br>" + value.periodTime;
        } else {
            return "-";
        }
    }

    function moneyDeal(money) {
        money = Math.round(parseFloat(money) * 100) / 100;
        return money;
    }

    function hourDeal(hour) {
        return Math.round(parseFloat(hour) * 10) / 10;
    }

    function signRecordFormatter(value, row, col){
        if (value.hours == 0 || value.salary == 0)
            return '';
        return hourDeal(value.hours) + ' hours<br>salary $' + moneyDeal(value.salary) + '<br>Schedule ' + value.schedule + '<br>sign ' + value.realwork;
    }

    function totalColumnFormatter(value, row, col){
        if (value.hours == 0 || value.salary == 0)
            return '';
        return hourDeal(value.hours) + ' hours<br>salary $' + moneyDeal(value.salary);
    }

    function mondayFootFormatter(rows) {
        if (rows) {
            var countHour = 0;
            var countSalary = 0;
            for (var i = 0; i < rows.length; i++) {
                countHour += rows[i].monday.hours;
                countSalary += rows[i].monday.salary;
            }
            return hourDeal(countHour) + " hours" + "<br>$" + moneyDeal(countSalary);
        } else {
            return '-';
        }
    }

    function tuesdayFootFormatter(rows) {
        if (rows) {
            var countHour = 0;
            var countSalary = 0;
            for (var i = 0; i < rows.length; i++) {
                countHour += rows[i].tuesday.hours;
                countSalary += rows[i].tuesday.salary;
            }
            return hourDeal(countHour) + " hours" + "<br>$" + moneyDeal(countSalary);
        } else {
            return '-';
        }
    }

    function wednesdayFootFormatter(rows) {
        if (rows) {
            var countHour = 0;
            var countSalary = 0;
            for (var i = 0; i < rows.length; i++) {
                countHour += rows[i].wednesday.hours;
                countSalary += rows[i].wednesday.salary;
            }
            return hourDeal(countHour) + " hours" + "<br>$" + moneyDeal(countSalary);
        } else {
            return '-';
        }
    }

    function thursdayFootFormatter(rows) {
        if (rows) {
            var countHour = 0;
            var countSalary = 0;
            for (var i = 0; i < rows.length; i++) {
                countHour += rows[i].thursday.hours;
                countSalary += rows[i].thursday.salary;
            }
            return hourDeal(countHour) + " hours" + "<br>$" + moneyDeal(countSalary);
        } else {
            return '-';
        }
    }

    function fridayFootFormatter(rows) {
        if (rows) {
            var countHour = 0;
            var countSalary = 0;
            for (var i = 0; i < rows.length; i++) {
                countHour += rows[i].friday.hours;
                countSalary += rows[i].friday.salary;
            }
            return hourDeal(countHour) + " hours" + "<br>$" + moneyDeal(countSalary);
        } else {
            return '-';
        }
    }

    function saturdayFootFormatter(rows) {
        if (rows) {
            var countHour = 0;
            var countSalary = 0;
            for (var i = 0; i < rows.length; i++) {
                countHour += rows[i].saturday.hours;
                countSalary += rows[i].saturday.salary;
            }
            return hourDeal(countHour) + " hours" + "<br>$" + moneyDeal(countSalary);
        } else {
            return '-';
        }
    }

    function sundayFootFormatter(rows) {
        if (rows) {
            var countHour = 0;
            var countSalary = 0;
            for (var i = 0; i < rows.length; i++) {
                countHour += rows[i].total.hours;
                countSalary += rows[i].total.salary;
            }
            return hourDeal(countHour) + " hours" + "<br>$" + moneyDeal(countSalary);
        } else {
            return '-';
        }
    }

    function totalColumnFootFormatter(rows) {
        if (rows) {
            var countHour = 0;
            var countSalary = 0;
            for (var i = 0; i < rows.length; i++) {
                countHour += rows[i].total.hours;
                countSalary += rows[i].total.salary;
            }
            return hourDeal(countHour) + " hours" + "<br>$" + moneyDeal(countSalary);
        } else {
            return '-';
        }
    }
    /**
     * 获取当前的列
     */
    function initColumns() {
        myColumns = new Array();
        var column0 = {
            field: 'staff',
            title: 'Name',
            align: 'center',
            valign: 'middle',
            formatter: staffNameFormatter,
        }
        var column1 = {
            field: 'monday',
            title: 'Monday'  + '<br>' + formatEsDate(pageMonday),
            align: 'center',
            valign: 'middle',
            formatter: signRecordFormatter,
            footerFormatter: 'mondayFootFormatter'
        }
        var column2 = {
            field: 'tuesday',
            title: 'Tuesday' + '<br>' + formatEsDate(addDay(pageMonday, 1)),
            align: 'center',
            valign: 'middle',
            formatter: signRecordFormatter,
            footerFormatter: 'tuesdayFootFormatter'
        }
        var column3 = {
            field: 'wednesday',
            title: 'Wednesday' + '<br>' + formatEsDate(addDay(pageMonday, 2)),
            align: 'center',
            valign: 'middle',
            formatter: signRecordFormatter,
            footerFormatter: 'wednesdayFootFormatter'
        }
        var column4 = {
            field: 'thursday',
            title: 'Thursday' + '<br>' + formatEsDate(addDay(pageMonday, 3)),
            align: 'center',
            valign: 'middle',
            formatter: signRecordFormatter,
            footerFormatter: 'thursdayFootFormatter'
        }
        var column5 = {
            field: 'friday',
            title: 'Friday' + '<br>' + formatEsDate(addDay(pageMonday, 4)),
            align: 'center',
            valign: 'middle',
            formatter: signRecordFormatter,
            footerFormatter: 'fridayFootFormatter'
        }
        var column6 = {
            field: 'saturday',
            title: 'Saturday' + '<br>' + formatEsDate(addDay(pageMonday, 5)),
            align: 'center',
            valign: 'middle',
            formatter: signRecordFormatter,
            footerFormatter: 'saturdayFootFormatter'
        }
        var column7 = {
            field: 'sunday',
            title: 'Sunday' + '<br>' + formatEsDate(addDay(pageMonday, 6)),
            align: 'center',
            valign: 'middle',
            formatter: signRecordFormatter,
            footerFormatter: 'sundayFootFormatter'
        }
        var column8 = {
            field: 'total',
            title: 'Total',
            align: 'center',
            valign: 'middle',
            formatter: totalColumnFormatter,
            footerFormatter: 'totalColumnFootFormatter'
        }
        myColumns.push(column0, column1, column2, column3, column4, column5, column6, column7, column8);
    }
</script>
</body>
</html>