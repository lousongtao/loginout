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
    <title>Schedule</title>
    <jsp:include page="/resources/inc/head.jsp" flush="true"/>
</head>
<body>
<div id="main">

    <h3 id="headTitle" align="center">scheduling Table</h3>

    <div class="btn-group" role="group" aria-label="...">
        <div class="btn-group" role="group">
            <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Add Row
                <span class="caret"></span>
            </button>
            <ul class="dropdown-menu">
                <c:forEach var="mcGroup" items="${mcGroups}">
                    <li><a href="javascript:void(0)" id="groupId${mcGroup.id}"
                           onclick="addRowAction(${mcGroup.id})">${mcGroup.name}</a></li>
                </c:forEach>
            </ul>
        </div>
        <button id="deleteRow" type="button" class="btn btn-danger">Delete Choose Row</button>
    </div>
    <div class="btn-group" role="group" aria-label="...">
        <button id="lastWeek" type="button" class="btn btn-default">Last Week</button>
        <button id="thisWeek" type="button" class="btn btn-primary">This Week</button>
        <button id="nextWeek" type="button" class="btn btn-default">Next Week</button>
    </div>
    <div class="btn-group" role="group" aria-label="...">
        <a href="javascript:void(0)" onclick="alert('待实现')" class="btn btn-info" style="float: right">Save Template</a>
        <a href="javascript:void(0)" onclick="alert('待实现')" class="btn btn-success" style="float: right">Choose My Template</a>
    </div>
    <table id="table"></table>
</div>
<jsp:include page="/resources/inc/footer.jsp" flush="true"/>
<script>
    var $table = $('#table');
    //定义页面当天,以及页面周一和周天
    var pageToday;
    var pageMonday;
    var pageSunday;
    var myColumns = new Array();
    $(function () {
        //初始化本周日期
        initWeekStartAndEnd();
        //初始化列
        initColumns();
        //编辑上方标题
        editHeadTitle();
        //加载数据
        initData();
        //点击事件
        $("#lastWeek").click(function () {
            pageMonday = addDay(pageMonday, -7);
            pageSunday = addDay(pageSunday, -7);
            editHeadTitle();
//            重新加载数据
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
        $("#deleteRow").dblclick(function () {
            //该行数据先确定是否可以删除
            if (pageToday > pageSunday) {
                showTips("Be overdue");
                return;
            }
            var rows = $table.bootstrapTable('getSelections');
            if (rows.length == 0) {
                $.confirm({
                    title: false,
                    content: 'Please choose at least one record！',
                    autoClose: 'cancel|3000',
                    backgroundDismiss: true,
                    buttons: {
                        cancel: {
                            text: '取消',
                            btnClass: 'waves-effect waves-button'
                        }
                    }
                });
            } else {
                var ids = new Array();
                for (var i in rows) {
                    if (rows[i].data1) {
                        ids.push(rows[i].data1.id);
                    }
                    if (rows[i].data2) {
                        ids.push(rows[i].data2.id);
                    }
                    if (rows[i].data3) {
                        ids.push(rows[i].data3.id);
                    }
                    if (rows[i].data4) {
                        ids.push(rows[i].data4.id);
                    }
                    if (rows[i].data5) {
                        ids.push(rows[i].data5.id);
                    }
                    if (rows[i].data6) {
                        ids.push(rows[i].data6.id);
                    }
                    if (rows[i].data7) {
                        ids.push(rows[i].data7.id);
                    }
                }
                deleteMethod(ids);
            }
        });

    });

    //批量删除单元格数据数据
    function deleteMethod(ids) {
        deleteDialog = $.confirm({
            type: 'red',
            animationSpeed: 300,
            title: false,
            content: 'Confirm delete that you choose？',
            buttons: {
                confirm: {
                    text: '确认',
                    btnClass: 'waves-effect waves-button',
                    action: function () {
                        $.ajax({
                            type: 'post',
                            data: {
                                "ids": ids.join("-")
                            },
                            url: '${basePath}/manage/scheduling/batchDeleteCell',
                            success: function (result) {
                                if (result.code != 1) {
                                    if (result.data instanceof Array) {
                                        $.each(result.data, function (index, value) {
                                            $.confirm({
                                                theme: 'dark',
                                                animation: 'rotateX',
                                                closeAnimation: 'rotateX',
                                                title: false,
                                                content: value.errorMsg,
                                                buttons: {
                                                    confirm: {
                                                        text: '确认',
                                                        btnClass: 'waves-effect waves-button waves-light'
                                                    }
                                                }
                                            });
                                        });
                                    } else {
                                        $.confirm({
                                            theme: 'dark',
                                            animation: 'rotateX',
                                            closeAnimation: 'rotateX',
                                            title: false,
                                            content: result.data.errorMsg,
                                            buttons: {
                                                confirm: {
                                                    text: '确认',
                                                    btnClass: 'waves-effect waves-button waves-light'
                                                }
                                            }
                                        });
                                    }
                                } else {
                                    deleteDialog.close();
                                    $table.bootstrapTable('refresh');
                                }
                            },
                            error: function (XMLHttpRequest, textStatus, errorThrown) {
                                $.confirm({
                                    theme: 'dark',
                                    animation: 'rotateX',
                                    closeAnimation: 'rotateX',
                                    title: false,
                                    content: textStatus,
                                    buttons: {
                                        confirm: {
                                            text: '确认',
                                            btnClass: 'waves-effect waves-button waves-light'
                                        }
                                    }
                                });
                            }
                        });
                    }
                },
                cancel: {
                    text: '取消',
                    btnClass: 'waves-effect waves-button'
                }
            }
        });
    }

    //获取后台数据数据
    var updateCellDialog;

    function initData() {
        var height = $(window).height() - 125;//TODO:第一次取值, window.height只有25, 不知道原因, 导致下面的footer显示在了窗口外. 但是在tab上点击刷新, 就能把footer刷到窗口内
        $table.bootstrapTable({
            url: '${basePath}/manage/scheduling/getData',
            method: 'post',
            height: height,
            striped: true,
            sidePagination: 'server',
            escape: true,
            // toolbar: '#toolbar',
            showFooter: true ,
            queryParams: queryParams,
            columns: myColumns,
            rowStyle: myRowStyle,
            onDblClickCell: function (field, value, row, $element) {
                var cellIndex = $element[0].cellIndex;
                var editDate = addDay(pageMonday, cellIndex - 2);
                var paramEditDate = "?schedulingDate=" + transferDate(editDate);
                var branchId = "&branchId=${branchId}";
                var paramCellId = "";
                console.log(value);
                if (value && value.id) {
                    paramCellId = "&cellId=" + value.id;
                }
                //过滤掉不该有双击事件的格子
                if (field == "settlement" || field == "mcGroup" || field == "ck") {
                    return false;
                }
                //今天之前的不能改
                if (editDate < pageToday) {
                    showTips("Be overdue");
                    return false;
                }
                updateCellDialog = $.dialog({
                    animationSpeed: 300,
                    title: 'Edit Cell Data',
                    content: 'url:${basePath}/manage/scheduling/updateCell/' + row.mcGroup.id + paramEditDate + paramCellId + branchId,
                    onContentReady: function () {
                        initMaterialInput();
                    }
                });
            }

        });


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

    //设置查询参数
    function queryParams(params) {
        var param = {
            pageToday: transferDate(pageToday),
            pageMonday: transferDate(pageMonday),
            pageSunday: transferDate(pageSunday),
            branchId: ${branchId}
        }
        return param;
    }

    function myRowStyle(row, index) {
        var style = {css: {'color': row.mcGroup.color}};
        return style;
    }

    //更改头标题
    function editHeadTitle() {
        var pageMondayStr = formatEsDate(pageMonday);
        var pageSundayStr = formatEsDate(pageSunday);
        $("#headTitle").html(pageMondayStr + " To " + pageSundayStr + " Scheduling");
    }

    function addRowAction(groupId) {
        if (pageToday > pageSunday) {
            showTips("Be overdue");
            return;
        }
        var groupName = $("#groupId" + groupId).html();

        $table.bootstrapTable('insertRow', {
            index: 0, row: {
                mcGroup: {
                    id: groupId,
                    name: groupName,
                }
            }
        });
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
        pageMonday = getMonday(pageToday);
        pageSunday = getSunday(pageToday);
//         var $date = pageToday;
//         //今天是这周的第几天
//         var today = $date.getDay();
//         //上周日距离今天的天数（负数表示）
//         var stepSunDay = -today + 1;
//
//         // 如果今天是周日
//         if (today == 0) {
//             stepSunDay = -7;
//         }
//         // 周一距离今天的天数（负数表示）
//         var stepMonday = 7 - today;
//         var time = $date.getTime();
//         var monday = new Date(time + stepSunDay * 24 * 3600 * 1000);
//         pageMonday = monday;
// //        console.log("初始化本周一" + transferDate(pageMonday));
//         var sunday = new Date(time + stepMonday * 24 * 3600 * 1000);
//         pageSunday = sunday;
// //        console.log("初始化本周天" + transferDate(pageSunday));
        //标题也要跟着初始化
        var weekStartDateAndEndDate = new Array();
        weekStartDateAndEndDate.push(pageMonday, pageSunday);
        return weekStartDateAndEndDate;
    }

    function getMonday(d1){
        var day = d1.getDay();
        var oneday = 24 * 60 * 60 *1000;
        switch (day) {
            case 0: return new Date(d1.getTime() - 6 * oneday) ; //按照周日为本周第一天算, 周一则向前推6天
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


    /**
     * 获取当前的列
     */
    function initColumns() {
        myColumns = new Array();
        var checkBox = {
            field: 'ck',
            align: 'center',
            valign: 'middle',
            checkbox: true
        }
        var column0 = {
            field: 'mcGroup',
            title: 'Group',
            align: 'center',
            valign: 'middle',
            formatter: groupFormat,
            footerFormatter: 'Settlement'
        }
        var column1 = {
            field: 'data1',
            title: 'Monday' + '<br>' + formatEsDate(pageMonday),
            align: 'center',
            valign: 'middle',
            formatter: weekDateFormat,
            footerFormatter: daySettlement1
        }
        var column2 = {
            field: 'data2',
            title: 'Tuesday' + '<br>' + formatEsDate(addDay(pageMonday, 1)),
            align: 'center',
            valign: 'middle',
            formatter: weekDateFormat,
            footerFormatter: daySettlement2
        }
        var column3 = {
            field: 'data3',
            title: 'Wednesday' + '<br>' + formatEsDate(addDay(pageMonday, 2)),
            align: 'center',
            valign: 'middle',
            formatter: weekDateFormat,
            footerFormatter: daySettlement3
        }
        var column4 = {
            field: 'data4',
            title: 'Thursday' + '<br>' + formatEsDate(addDay(pageMonday, 3)),
            align: 'center',
            valign: 'middle',
            formatter: weekDateFormat,
            footerFormatter: daySettlement4
        }
        var column5 = {
            field: 'data5',
            title: 'Friday' + '<br>' + formatEsDate(addDay(pageMonday, 4)),
            align: 'center',
            valign: 'middle',
            formatter: weekDateFormat,
            footerFormatter: daySettlement5
        }
        var column6 = {
            field: 'data6',
            title: 'Saturday' + '<br>' + formatEsDate(addDay(pageMonday, 5)),
            align: 'center',
            valign: 'middle',
            formatter: weekDateFormat,
            footerFormatter: daySettlement6
        }
        var column7 = {
            field: 'data7',
            title: 'Sunday' + '<br>' + formatEsDate(addDay(pageMonday, 6)),
            align: 'center',
            valign: 'middle',
            formatter: weekDateFormat,
            footerFormatter: daySettlement7
        }
        var column8 = {
            field: 'settlement',
            title: 'Settlement',
            align: 'center',
            valign: 'middle',
            formatter: settlementFormat,
            footerFormatter: daySettlement8
        }
        myColumns.push(checkBox, column0, column1, column2, column3, column4, column5, column6, column7, column8);

    }

    //组格式化
    function groupFormat(value, row, index) {
        if (value) {
            return value.name;
        } else {
            return "-";
        }
    }

    //行总计格式化
    function settlementFormat(value, row, index) {
        if (row) {
            var totalTime = countTotalTime(row);
            var totalPay = countTotalPay(row);
            return totalTime + " hours" + "<br>$" + totalPay;
        } else {
            return "-";
        }
    }

    function countTotalTime(row) {
        if (row) {
            var totalTime = 0;
            if (row.data1 && row.data1.totalTime) {
                totalTime += row.data1.totalTime;
            }
            if (row.data2 && row.data2.totalTime) {
                totalTime += row.data2.totalTime;
            }
            if (row.data3 && row.data3.totalTime) {
                totalTime += row.data3.totalTime;
            }
            if (row.data4 && row.data4.totalTime) {
                totalTime += row.data4.totalTime;
            }
            if (row.data5 && row.data5.totalTime) {
                totalTime += row.data5.totalTime;
            }
            if (row.data6 && row.data6.totalTime) {
                totalTime += row.data6.totalTime;
            }
            if (row.data7 && row.data7.totalTime) {
                totalTime += row.data7.totalTime;
            }
            return totalTime;
        } else {
            return 0;
        }
    }

    function countTotalPay(row) {
        if (row) {
            var totalPay = 0;
            if (row.data1 && row.data1.estimatePay) {
                totalPay += row.data1.estimatePay;
            }
            if (row.data2 && row.data2.estimatePay) {
                totalPay += row.data2.estimatePay;
            }
            if (row.data3 && row.data3.estimatePay) {
                totalPay += row.data3.estimatePay;
            }
            if (row.data4 && row.data4.estimatePay) {
                totalPay += row.data4.estimatePay;
            }
            if (row.data5 && row.data5.estimatePay) {
                totalPay += row.data5.estimatePay;
            }
            if (row.data6 && row.data6.estimatePay) {
                totalPay += row.data6.estimatePay;
            }
            if (row.data7 && row.data7.estimatePay) {
                totalPay += row.data7.estimatePay;
            }
            return totalPay / 100;
        } else {
            return 0;
        }
    }

    //周数据格式化
    function weekDateFormat(value, row, index) {
        if (value && value.periodTime) {
            return value.uName + "<br>" + value.periodTime;
        } else {
            return "-";
        }
    }

    //页脚格式化
    function daySettlement1(rows) {
        if (rows) {
            var person = 0;
            var countHour = 0;
            var countPay = 0;
            for (var i = 0; i < rows.length; i++) {
                if (rows[i].data1 && rows[i].data1.totalTime) {
                    person += 1;
                    countHour += rows[i].data1.totalTime;
                    countPay += rows[i].data1.estimatePay;
                }
            }

            return person + " people" + "<br>" + hourDeal(countHour) + " hours" + "<br>$" + countPay / 100;
        } else {
            return '-';
        }
    }

    function daySettlement2(rows) {
        if (rows) {
            var person = 0;
            var countHour = 0;
            var countPay = 0;
            for (var i = 0; i < rows.length; i++) {
                if (rows[i].data2 && rows[i].data2.totalTime) {
                    person += 1;
                    countHour += rows[i].data2.totalTime;
                    countPay += rows[i].data2.estimatePay;
                }
            }
            return person + " people" + "<br>" + hourDeal(countHour) + " hours" + "<br>$" + countPay / 100;
        } else {
            return '-';
        }
    }

    function daySettlement3(rows) {
        if (rows) {
            var person = 0;
            var countHour = 0;
            var countPay = 0;
            for (var i = 0; i < rows.length; i++) {
                if (rows[i].data3 && rows[i].data3.totalTime) {
                    person += 1;
                    countHour += rows[i].data3.totalTime;
                    countPay += rows[i].data3.estimatePay;
                }
            }
            return person + " people" + "<br>" + hourDeal(countHour) + " hours" + "<br>$" + countPay / 100;
        } else {
            return '-';
        }
    }

    function daySettlement4(rows) {
        if (rows) {
            var person = 0;
            var countHour = 0;
            var countPay = 0;
            for (var i = 0; i < rows.length; i++) {
                if (rows[i].data4 && rows[i].data4.totalTime) {
                    person += 1;
                    countHour += rows[i].data4.totalTime;
                    countPay += rows[i].data4.estimatePay;
                }
            }
            return person + " people" + "<br>" + hourDeal(countHour) + " hours" + "<br>$" + countPay / 100;
        } else {
            return '-';
        }
    }

    function daySettlement5(rows) {
        if (rows) {
            var person = 0;
            var countHour = 0;
            var countPay = 0;
            for (var i = 0; i < rows.length; i++) {
                if (rows[i].data5 && rows[i].data5.totalTime) {
                    person += 1;
                    countHour += rows[i].data5.totalTime;
                    countPay += rows[i].data5.estimatePay;
                }
            }
            return person + " people" + "<br>" + hourDeal(countHour) + " hours" + "<br>$" + countPay / 100;
        } else {
            return '-';
        }
    }

    function daySettlement6(rows) {
        if (rows) {
            var person = 0;
            var countHour = 0;
            var countPay = 0;
            for (var i = 0; i < rows.length; i++) {
                if (rows[i].data6 && rows[i].data6.totalTime) {
                    person += 1;
                    countHour += rows[i].data6.totalTime;
                    countPay += rows[i].data6.estimatePay;
                }
            }
            return person + " people" + "<br>" + hourDeal(countHour) + " hours" + "<br>$" + countPay / 100;
        } else {
            return '-';
        }
    }

    function daySettlement7(rows) {
        if (rows) {
            var person = 0;
            var countHour = 0;
            var countPay = 0;
            for (var i = 0; i < rows.length; i++) {
                if (rows[i].data7 && rows[i].data7.totalTime) {
                    person += 1;
                    countHour += rows[i].data7.totalTime;
                    countPay += rows[i].data7.estimatePay;
                }
            }
            return person + " people" + "<br>" + hourDeal(countHour) + " hours" + "<br>$" + countPay / 100;
        } else {
            return '-';
        }
    }

    function daySettlement8(rows) {
        if (rows) {
            var countPay = 0;
            var countHour = 0;

            for (var i = 0; i < rows.length; i++) {
                countHour += countTotalTime(rows[i]);
                countPay += countTotalPay(rows[i]);
            }
            //小时保留一位小数,countPay保留2位

            return hourDeal(countHour) + " hours<br>$" + moneyDeal(countPay);
        } else {
            return '-';
        }
    }

    // 颜色
    function colorFormatter(value, row, index) {
//        var b='<button type="button" class="btn btn-default" style="background:'+value+'"></button>';
        var a = '<span style="color:' + value + '">' + value + '</span>';
        return a;
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

    //时间戳格式化  HH:mm:ss
    function timestampToTime(timestamp) {
        if (timestamp) {
            var date = new Date(timestamp);//
            var h = date.getHours() + ':';
            var m = date.getMinutes();
            return h + m;
        }
        return "??:??"
    }

    function moneyDeal(money) {
        money = Math.round(parseFloat(money) * 100) / 100;
        return money;
    }

    function hourDeal(hour) {
        return Math.round(parseFloat(hour) * 10) / 10;
    }
</script>
</body>
</html>