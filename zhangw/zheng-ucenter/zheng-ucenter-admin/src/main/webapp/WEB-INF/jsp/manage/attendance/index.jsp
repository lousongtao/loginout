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
    <title>Attendance</title>
    <jsp:include page="/resources/inc/head.jsp" flush="true"/>
</head>
<body>
<div id="main">
    <div id="toolbar">
        <form class="form-inline">
            <div class="form-group">
                <label for="nameSearch">Name</label>
                <input type="text" class="form-control" id="nameSearch">
            </div>
            <div class="form-group">
                <select id="groupId" name="groupId">
                    <option value="">No Choose</option>
                    <c:forEach var="mcGroup" items="${mcGroups}">
                        <option value="${mcGroup.id}"
                        >${mcGroup.name}
                        </option>
                    </c:forEach>
                </select>
            </div>
            <button id="searchButton" type="button" class="btn btn-success"><span
                    class="glyphicon glyphicon-search"></span> Search
            </button>
        </form>
    </div>
    <h3 id="headTitle" align="center">Attendance Table</h3>

    <div class="btn-group" role="group" aria-label="...">
        <button id="lastMonth" type="button" class="btn btn-default">Last Month</button>
        <button id="thisMonth" type="button" class="btn btn-primary">This Month</button>
        <button id="nextMonth" type="button" class="btn btn-default">Next Month</button>
    </div>
    <table id="table"></table>
    <h5 align="center"><font color="red">Tips: √=Finished, ×=Abnormality, ??=Unknown</font></h5>
</div>
<jsp:include page="/resources/inc/footer.jsp" flush="true"/>
<script>
    var $table = $('#table');
    //定义页面当天,以及本月第一天和最后一天
    var pageMonth;
    var pageFirstDay;
    var pageLastDay;
    var myColumns;
    $(function () {
        //初始化本周日期
        initMonthStartAndEnd();
        //初始化列
        initColumns();
        //编辑上方标题
        editHeadTitle();
        //加载数据
        initData();
        //点击事件
        $("#searchButton").click(function () {
            $table.bootstrapTable('refresh');
        })
        $("#lastMonth").click(function () {
            var monthStartDateAndEndDate = getLastMonthStartAndEnd(pageLastDay);
            pageFirstDay = monthStartDateAndEndDate[0];
            pageLastDay = monthStartDateAndEndDate[1];
            editHeadTitle();
//            重新加载数据
            getData();
        });
        $("#thisMonth").click(function () {
            initMonthStartAndEnd();
            editHeadTitle();
            getData();
        });
        $("#nextMonth").click(function () {
            if (pageLastDay > pageMonth) {
                showTips("Unknown Future Attendance");
                return;
            }
            var monthStartDateAndEndDate = getNextMonthStartAndEnd(pageLastDay);
            pageFirstDay = monthStartDateAndEndDate[0];
            pageLastDay = monthStartDateAndEndDate[1];
            editHeadTitle();
            getData();
        });

    });


    //获取后台数据详情
    var cellDialog;

    function initData() {
        $table.bootstrapTable({
            url: '${basePath}/manage/attendance/getData',
            method: 'post',
            height: getHeight(),
            striped: true,
            sidePagination: 'server',
            escape: true,
            toolbar: '#toolbar',
//            showFooter: true,
            queryParams: queryParams,
            columns: myColumns,
            onDblClickCell: function (field, value, row, $element) {
                if (!value) {
                    return false;
                }
                //过滤掉不该有双击事件的格子
                if (field == "Name") {
                    return false;
                }
                console.log(value);
                cellDialog = $.dialog({
                    animationSpeed: 300,
                    title: 'Attendance Detail',
                    content: 'url:${basePath}/manage/attendance/detail/' + value.id,
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
            pageFirstDay: transferDate(pageFirstDay),
            pageLastDay: transferDate(pageLastDay),
            uName: $("#nameSearch").val(),
            groupId: $("#groupId").val()
        }
        return param;
    }

    //更改头标题
    function editHeadTitle() {
        var pageFirstDayStr = formatEsDate(pageFirstDay);
        var pageLastDayStr = formatEsDate(pageLastDay);
        $("#headTitle").html(pageFirstDayStr + " To " + pageLastDayStr + " Attendance");
    }


    //获取传入日期所在月的开始和结束
    function getThisMonthStartAndEnd(date) {
        var firstDate = new Date(date.setDate(1));
        var endDate = new Date(firstDate);
        endDate.setMonth(new Date(firstDate).getMonth() + 1);
        endDate.setDate(0);
        var monthStartDateAndEndDate = new Array();
        monthStartDateAndEndDate.push(firstDate, endDate);
        return monthStartDateAndEndDate;
    }

    //获取传入日期上个月的开始和结束
    function getLastMonthStartAndEnd(date) {
        var firstDate = new Date(date.getFullYear(), date.getMonth() - 1, 1);
        var endDate = getThisMonthStartAndEnd(firstDate)[1];
        var monthStartDateAndEndDate = new Array();
        monthStartDateAndEndDate.push(firstDate, endDate);
        return monthStartDateAndEndDate;

    }

    //获取传入日期下个月的开始和结束
    function getNextMonthStartAndEnd(date) {
        var firstDate = new Date(date.getFullYear(), date.getMonth() + 1, 1);
        var endDate = getThisMonthStartAndEnd(firstDate)[1];
        var monthStartDateAndEndDate = new Array();
        monthStartDateAndEndDate.push(firstDate, endDate);
        return monthStartDateAndEndDate;
    }

    //初始化本月起始日期和结束日期
    function initMonthStartAndEnd() {
        pageMonth = new Date();
        var monthStartDateAndEndDate = getThisMonthStartAndEnd(pageMonth);
        pageFirstDay = monthStartDateAndEndDate[0];
        pageLastDay = monthStartDateAndEndDate[1];
        console.log("初始化本月：" + transferDate(pageFirstDay) + " 最后一天：" + transferDate(pageLastDay));

    }

    //将时间转换成美式英语的时间格式,eg:3rd May 2018
    function formatEsDate($date) {
        var date = new Date($date);
        var monthArr = new Array("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Spt", "Oct", "Nov", "Dec");
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

    //获取本月 年
    function formateEsMonth($date) {
        var date = new Date($date);
        var monthArr = new Array("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Spt", "Oct", "Nov", "Dec");
        var year = date.getFullYear(); //年
        var month = monthArr[date.getMonth()]; //月
        return month + " " + year;
    }


    /**
     * 获取当前的列
     */
    function initColumns() {
        myColumns = new Array();
        var name = {
            field: 'uName',
            title: 'Name',
            align: 'center',
            valign: 'middle'
        }
        myColumns.push(name);
        for (var i = 1; i < pageLastDay.getDate() + 1; i++) {
            var column = {
                field: 'day' + i,
                title: i,
                align: 'center',
                valign: 'middle',
                formatter: resultFormatter
            }
            myColumns.push(column);
        }
    }

    function resultFormatter(value, row, index) {
        if (value) {
            if (value.result == 0) {
                //未知,当天有排班,但没打卡记录
                return '<a href="javascript:;">??</a>';
            } else if (value.result == 1) {
                //完成
                return '<a href="javascript:;" style="background-color: #00B83F"><i class="zmdi zmdi-check zmdi-hc-fw"></i></a>';
            } else if (value.result == 2) {
                //异常
                return '<a href="javascript:;" style="background-color: red"><i class="zmdi zmdi-close zmdi-hc-fw"></i></a>';
            } else {
                return "-";
            }
        } else {
            return "";
        }

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