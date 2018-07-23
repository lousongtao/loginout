﻿<%@ page contentType="text/html; charset=utf-8" %>
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
    <title>排班</title>
    <jsp:include page="/resources/inc/head.jsp" flush="true"/>
</head>
<body>
<div id="main">
    <div id="toolbar">
        <shiro:hasPermission name="ucenter:shift:create">
            <a class="waves-effect waves-button" href="javascript:;"
               onclick="createAction()"><i class="zmdi zmdi-plus"></i>
                添加班次</a>
            <a class="waves-effect waves-button" href="javascript:;"
               onclick="deleteAction()"><i class="zmdi zmdi-close"></i>
                删除班次</a>
        </shiro:hasPermission>
    </div>
    <h3>本周(2018年07月23日-2018年07月23日)排班表</h3>
    <div class="btn-group" role="group" aria-label="...">
        <button type="button" class="btn btn-default">Left</button>
        <button type="button" class="btn btn-default">Middle</button>
        <button type="button" class="btn btn-default">Right</button>
    </div>
    <table id="table"></table>
</div>
<jsp:include page="/resources/inc/footer.jsp" flush="true"/>
<script>
    var $table = $('#table');
    $(function () {
        // bootstrap table初始化
        $table.bootstrapTable({
            url: '${basePath}/manage/shift/list',
            height: getHeight(),
            striped: true,
//            search: true,
            showRefresh: true,
            showColumns: true,
            minimumCountColumns: 2,
            clickToSelect: true,
            detailView: true,
            detailFormatter: 'detailFormatter',
            pagination: true,
            paginationLoop: false,
            sidePagination: 'server',
            silentSort: false,
            smartDisplay: false,
            escape: true,
            searchOnEnterKey: true,
            idField: 'id',
            maintainSelected: true,
            toolbar: '#toolbar',
            columns: [
                {field: 'ck', checkbox: true},
                {field: 'id', title: '编号', align: 'center'},
                {field: 'planName', title: '名称'},
                {field: 'color', title: '颜色', formatter: 'colorFormatter'},
                {field: 'periodTime', title: '起止时间'},
                {field: 'totalTime', title: '总时长(单位:时)'},
                {field: 'mark', title: '备注', visible: false},
//                {
//                    title: '操作', field: 'idd', align: 'center', clickToSelect: false,
//                    formatter: function (value, row, index) {
//                        var u = '<a  class="update ' + s_edit_h + '" href="#" mce_href="#" title="Edit" onclick="updateAction(\'' + row.id + '\')"><i class="glyphicon glyphicon-edit "></i></a>&nbsp&nbsp&nbsp ';
//                        var d = '<a  class="delete ' + s_delete_h + '" href="#" mce_href="#" title="Remove" onclick="deleteAction(\'' + row.id + '\')"><i class="glyphicon glyphicon-remove "></i></a> ';
//                        return u + d;
//                    }
//                }
            ]
        });

        function getNowFormatDate() {
            var date = new Date();
            var seperator1 = "-";
//            var year = date.getFullYear();
            var month = date.getMonth() + 1;
            var strDate = date.getDate();
            if (month >= 1 && month <= 9) {
                month = "0" + month;
            }
            if (strDate >= 0 && strDate <= 9) {
                strDate = "0" + strDate;
            }
            var currentdate = seperator1 + month + seperator1 + strDate;
            return currentdate;
        }
    });

    // 颜色性别
    function colorFormatter(value, row, index) {
        var a = '<span style="color:' + value + '">' + value + '</span>';
        return a;
    }


</script>
</body>
</html>