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
    <div id="toolbar">
        <shiro:hasPermission name="ucenter:staff:write">
            <a class="waves-effect waves-button" href="javascript:;"
               onclick="createAction()"><i class="zmdi zmdi-plus"></i>
                New</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="ucenter:staff:delete">
            <a class="waves-effect waves-button" href="javascript:;"
               onclick="deleteAction()"><i class="zmdi zmdi-close"></i>
                Delete</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="ucenter:staff:group">
            <a class="waves-effect waves-button" href="javascript:;"
               onclick="groupAction()"><i class="zmdi zmdi-accounts-list"></i>
                Group</a>
        </shiro:hasPermission>
    </div>
    <form class="form-inline">
        <div class="form-group">
            <label for="accountSearch">Account</label>
            <input type="text" class="form-control" id="accountSearch">
        </div>
        <div class="form-group">
            <label for="nameSearch">Name</label>
            <input type="text" class="form-control" id="nameSearch">
        </div>
        <button id="searchButton" type="button" class="btn btn-default"><span class="glyphicon glyphicon-search"></span> Search</button>
    </form>
    <table id="table"></table>
</div>
<div>
    <script type="text/javascript">
        var s_edit_h = 'hidden';
        var s_delete_h = 'hidden';
    </script>
</div>
<shiro:hasPermission name="ucenter:staff:write">
    <script type="text/javascript">
        s_edit_h = '';
    </script>
</shiro:hasPermission>
<shiro:hasPermission name="ucenter:staff:delete">
    <script type="text/javascript">
        s_delete_h = '';
    </script>
</shiro:hasPermission>
<jsp:include page="/resources/inc/footer.jsp" flush="true"/>
<script>
    var $table = $('#table');
    $(function () {
        // bootstrap table初始化
        $table.bootstrapTable({
            url: '${basePath}/manage/staff/list',
            height: getHeight(),
            striped: true,
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
            idField: 'userId',
            maintainSelected: true,
            toolbar: '#toolbar',
            queryParams: function (params) {
                return {
                    // 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                    limit: params.limit,
                    offset: params.offset,
                    order: params.order,
                    accountSearch: $('#accountSearch').val(),
                    nameSearch: $('#nameSearch').val()
                };
            },
            columns: [
                {field: 'ck', radio: true},
                {field: 'userId', title: 'ID', align: 'center'},
                {field: 'loginname', title: 'Login Name *'},
                {field: 'realname', title: 'Name*'},
                {field: 'phone', title: 'Telephone'},
                {field: 'email', title: 'Mail', visible: false},
                {field: 'sex', title: 'Sex', formatter: 'sexFormatter'},
                {field: 'createTime', title: 'Create Time', sortable: true, formatter: 'timeStampToDateTime'},
                // {field: 'baseSalary', title: 'Base Salary', formatter: 'salaryFormatter'},
                {field: 'perSalary', title: 'Hour Salary($)'},
                {field: 'schedulestatus', title: 'In Schedule', align: 'center', formatter: 'schedulestatusFormatter'},
                {field: 'groupList', title: 'Group', align: 'center', formatter: 'groupFormatter'},
                {field: 'locked', title: 'Status', sortable: true, align: 'center', formatter: 'lockedFormatter'},
                {
                    title: 'Operation', field: 'idd', align: 'center', clickToSelect: false,
                    formatter: function (value, row, index) {
                        var u = '<a  class="update ' + s_edit_h + '" href="#" mce_href="#" title="Edit" onclick="updateAction(\'' + row.userId + '\')"><i class="glyphicon glyphicon-edit "></i></a>&nbsp&nbsp&nbsp ';
                        var d = '<a  class="delete ' + s_delete_h + '" href="#" mce_href="#" title="Remove" onclick="deleteAction(\'' + row.userId + '\')"><i class="glyphicon glyphicon-remove "></i></a> ';
                        return u + d;
                    }
                }
            ]
        });

        $("#searchButton").click(function () {
            $table.bootstrapTable('refresh');
        })
    });

    // 格式化性别
    function sexFormatter(value, row, index) {
        if (value == 1) {
            return 'Male';
        }
        if (value == 0) {
            return 'Female';
        }
        return '-';
    }

    //格式化是否排班
    function schedulestatusFormatter(value, row, index) {
        if (value == 1) {
            return '<span class="label label-success">Yes</span>';
        }
        if (value == 0) {
            return '<span class="label label-default">No</span>';
        }
        return '-';
    }

    //显示人员所在组情况
    function groupFormatter(value, row, index) {
        if (value) {
            var ab = '';
            for (var i = 0; i < value.length; i++) {
                ab += '<span class="label label-primary">' + value[i].name + '</span>';
            }
            return ab;
        } else {
            return '<span class="label label-danger">N/A</span>';
        }
    }

    // 格式化状态
    function lockedFormatter(value, row, index) {
        if (value == 1) {
            return '<span class="label label-default">Unavailable</span>';
        } else {
            return '<span class="label label-success">Available</span>';
        }
    }

    //格式化薪资显示
    function salaryFormatter(value, row, index) {
        if (!value) {
            return '-';
        } else {
            // var str = (value / 100).toFixed(2) + '';
            // var intSum = str.substring(0, str.indexOf(".")).replace(/\B(?=(?:\d{3})+$)/g, ',');//取到整数部分
            // var dot = str.substring(str.length, str.indexOf("."))//取到小数部分
            // var salary = intSum + dot;

            return salary;
        }
    }

    // 新增
    var createDialog;

    function createAction() {
        createDialog = $.dialog({
            animationSpeed: 300,
            title: 'New Staff',
            content: 'url:${basePath}/manage/staff/create',
            onContentReady: function () {
                initMaterialInput();
//                initUploader();
            }
        });
    }

    // 编辑
    var updateDialog;

    function updateAction(userId) {
        if (userId) {
            updateDialog = $.dialog({
                animationSpeed: 300,
                title: 'Edit User',
                content: 'url:${basePath}/manage/staff/update/' + userId,
                onContentReady: function () {
                    initMaterialInput();
                }
            });
        }
    }

    // 删除
    var deleteDialog;

    function deleteAction(userId) {
        var ids = new Array();
        if (userId) {
            ids.push(userId);
            deleteMethod(ids);
        } else {
            var rows = $table.bootstrapTable('getSelections');
            if (rows.length == 0) {
                $.confirm({
                    title: false,
                    content: 'Must choose one record！',
                    autoClose: 'cancel|3000',
                    backgroundDismiss: true,
                    buttons: {
                        cancel: {
                            text: 'Cancel',
                            btnClass: 'waves-effect waves-button'
                        }
                    }
                });
            } else {
                for (var i in rows) {
                    ids.push(rows[i].userId);
                }
                deleteMethod(ids);
            }

        }
    }

    function deleteMethod(ids) {
        deleteDialog = $.confirm({
            type: 'red',
            animationSpeed: 300,
            title: false,
            content: 'Do you confirm to delete it？',
            buttons: {
                confirm: {
                    text: 'Confirm',
                    btnClass: 'waves-effect waves-button',
                    action: function () {
                        $.ajax({
                            type: 'get',
                            url: '${basePath}/manage/staff/delete/' + ids.join("-"),
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
                                                        text: 'Yes',
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
                                                    text: 'Yes',
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
                                            text: 'Yes',
                                            btnClass: 'waves-effect waves-button waves-light'
                                        }
                                    }
                                });
                            }
                        });
                    }
                },
                cancel: {
                    text: 'Cancel',
                    btnClass: 'waves-effect waves-button'
                }
            }
        });
    }

    // 用户分组
    var groupDialog;
    var userId;

    function groupAction() {
        var rows = $table.bootstrapTable('getSelections');
        if (rows.length != 1) {
            $.confirm({
                title: false,
                content: '请选择一条记录！',
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
            userId = rows[0].userId;
            groupDialog = $.dialog({
                animationSpeed: 300,
                title: '用户分组',
                content: 'url:${basePath}/manage/staff/group/' + userId,
                onContentReady: function () {
                    initMaterialInput();
                    $('select').select2({
                        placeholder: '请选择组',
                        allowClear: true
                    });
                }
            });
        }
    }
</script>
</body>
</html>