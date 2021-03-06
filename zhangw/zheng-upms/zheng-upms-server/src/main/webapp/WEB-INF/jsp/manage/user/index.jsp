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
    <title>用户管理</title>
    <jsp:include page="/resources/inc/head.jsp" flush="true"/>
</head>
<body>
<div id="main">
    <div id="toolbar">
        <shiro:hasPermission name="upms:user:create"><a class="waves-effect waves-button" href="javascript:;"
                                                        onclick="createAction()"><i class="zmdi zmdi-plus"></i>
            New</a></shiro:hasPermission>
        <%--<shiro:hasPermission name="upms:user:update"><a class="waves-effect waves-button" href="javascript:;"--%>
        <%--onclick="updateAction()"><i class="zmdi zmdi-edit"></i>--%>
        <%--编辑用户</a></shiro:hasPermission>--%>
        <shiro:hasPermission name="upms:user:delete"><a class="waves-effect waves-button" href="javascript:;"
                                                        onclick="deleteAction()"><i class="zmdi zmdi-close"></i>
            Delete</a></shiro:hasPermission>
        <!--<shiro:hasPermission name="upms:user:organization"><a class="waves-effect waves-button" href="javascript:;"
                                                              onclick="organizationAction()"><i class="zmdi zmdi-accounts-list"></i> 用户组织</a></shiro:hasPermission>
                -->
        <shiro:hasPermission name="upms:user:role"><a class="waves-effect waves-button" href="javascript:;"
                                                      onclick="roleAction()"><i class="zmdi zmdi-accounts"></i>
            Role</a></shiro:hasPermission>
        <shiro:hasPermission name="upms:user:permission"><a class="waves-effect waves-button" href="javascript:;"
                                                            onclick="permissionAction()"><i class="zmdi zmdi-key"></i>
            Permission</a></shiro:hasPermission>
    </div>
    <%--<div class="row">--%>
    <%--<div class="fixed-table-toolbar">--%>
    <%--<div class="columns pull-left col-md-2 nopadding">--%>
    <%--<input id="searchName" type="text" class="form-control"--%>
    <%--placeholder="账号">--%>
    <%--</div>--%>
    <%--<div class="columns pull-left">--%>
    <%--<button class="btn btn-success" onclick="reLoad()">查询</button>--%>
    <%--</div>--%>
    <%--</div>--%>
    <%--</div>--%>
    <table id="table"></table>
</div>
<div>
    <script type="text/javascript">
        var s_edit_h = 'hidden';
        var s_delete_h = 'hidden';
    </script>
</div>
<shiro:hasPermission name="upms:user:update">
    <script type="text/javascript">
        s_edit_h = '';
    </script>
</shiro:hasPermission>
<shiro:hasPermission name="upms:user:delete">
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
            url: '${basePath}/manage/user/list',
            height: getHeight(),
            striped: true,
            search: true,
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
            columns: [
                {field: 'ck', checkbox: true},
                {field: 'userId', title: 'ID', align: 'center'},
                {field: 'loginname', title: 'Login Name *'},
                {field: 'realname', title: 'Name *'},
//                {field: 'avatar', title: '头像', align: 'center', formatter: 'avatarFormatter'},
                {field: 'phone', title: 'Telephone'},
                {field: 'email', title: 'Email'},
                {field: 'sex', title: 'Sex', formatter: 'sexFormatter'},
                {field: 'locked', title: 'Status', sortable: true, align: 'center', formatter: 'lockedFormatter'},
//                {
//                    field: 'action',
//                    title: '操作',
//                    align: 'center',
//                    formatter: 'actionFormatter',
//                    events: 'actionEvents',
//                    clickToSelect: false
//                },
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
    });

    // 格式化操作按钮
    //    function actionFormatter(value, row, index) {
    //        return [
    //            '<a class="update" href="javascript:;" onclick="updateAction()" data-toggle="tooltip" title="Edit"><i class="glyphicon glyphicon-edit"></i></a>　',
    //            '<a class="delete" href="javascript:;" onclick="deleteAction()" data-toggle="tooltip" title="Remove"><i class="glyphicon glyphicon-remove"></i></a>'
    //        ].join('');
    //    }

    //    function reLoad() {
    //        $('#table').bootstrapTable('refreshOptions', {pageNumber: 1});
    ////        $('#table').bootstrapTable('refresh');
    //    }

    // 格式化图标
    function avatarFormatter(value, row, index) {
        return '<img src="${basePath}' + value + '" style="width:20px;height:20px;"/>';
    }

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

    // 格式化状态
    function lockedFormatter(value, row, index) {
        if (value == 1) {
            return '<span class="label label-default">Locked</span>';
        } else {
            return '<span class="label label-success">Normal</span>';
        }
    }

    // 新增
    var createDialog;

    function createAction() {
        createDialog = $.dialog({
            animationSpeed: 300,
            title: 'New Member',
            content: 'url:${basePath}/manage/user/create',
            onContentReady: function () {
                initMaterialInput();
                initUploader();
            }
        });
    }

    // 编辑
    var updateDialog;

    <%--function updateAction(userId) {--%>
    <%--var rows = $table.bootstrapTable('getSelections');--%>
    <%--if (rows.length != 1) {--%>
    <%--$.confirm({--%>
    <%--title: false,--%>
    <%--content: '请选择一条记录！',--%>
    <%--autoClose: 'cancel|3000',--%>
    <%--backgroundDismiss: true,--%>
    <%--buttons: {--%>
    <%--cancel: {--%>
    <%--text: '取消',--%>
    <%--btnClass: 'waves-effect waves-button'--%>
    <%--}--%>
    <%--}--%>
    <%--});--%>
    <%--} else {--%>
    <%--updateDialog = $.dialog({--%>
    <%--animationSpeed: 300,--%>
    <%--title: '编辑用户',--%>
    <%--content: 'url:${basePath}/manage/user/update/' + rows[0].userId,--%>
    <%--onContentReady: function () {--%>
    <%--initMaterialInput();--%>
    <%--initUploader();--%>
    <%--}--%>
    <%--});--%>
    <%--}--%>
    <%--}--%>

    function updateAction(userId) {
        if (userId) {
            updateDialog = $.dialog({
                animationSpeed: 300,
                title: 'Update Member',
                content: 'url:${basePath}/manage/user/update/' + userId,
                onContentReady: function () {
                    initMaterialInput();
                    initUploader();
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
                    content: 'Please choose one record!',
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
            content: 'Confirm to delete this record?',
            buttons: {
                confirm: {
                    text: 'Yes',
                    btnClass: 'waves-effect waves-button',
                    action: function () {
                        $.ajax({
                            type: 'get',
                            url: '${basePath}/manage/user/delete/' + ids.join("-"),
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

    // 用户组织
    var organizationDialog;
    var organizationUserId;

    function organizationAction() {
        var rows = $table.bootstrapTable('getSelections');
        if (rows.length != 1) {
            $.confirm({
                title: false,
                content: 'Please choose one record!',
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
            organizationUserId = rows[0].userId;
            organizationDialog = $.dialog({
                animationSpeed: 300,
                title: 'Organizer',
                content: 'url:${basePath}/manage/user/organization/' + organizationUserId,
                onContentReady: function () {
                    initMaterialInput();
                    $('select').select2({
                        placeholder: 'Please choose the organizer!',
                        allowClear: true
                    });
                }
            });
        }
    }

    // 用户角色
    var roleDialog;
    var roleUserId;

    function roleAction() {
        var rows = $table.bootstrapTable('getSelections');
        if (rows.length != 1) {
            $.confirm({
                title: false,
                content: 'Please choose one record',
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
            roleUserId = rows[0].userId;
            roleDialog = $.dialog({
                animationSpeed: 300,
                title: 'Role',
                content: 'url:${basePath}/manage/user/role/' + roleUserId,
                onContentReady: function () {
                    initMaterialInput();
                    $('select').select2({
                        placeholder: 'Please choose role!',
                        allowClear: true
                    });
                }
            });
        }
    }

    // 用户权限
    var permissionDialog;
    var permissionUserId;

    function permissionAction() {
        var rows = $table.bootstrapTable('getSelections');
        if (rows.length != 1) {
            $.confirm({
                title: false,
                content: 'Pleaes choose one record!',
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
            permissionUserId = rows[0].userId;
            permissionDialog = $.dialog({
                animationSpeed: 300,
                title: 'Grant',
                columnClass: 'large',
                content: 'url:${basePath}/manage/user/permission/' + permissionUserId,
                onContentReady: function () {
                    initMaterialInput();
                    initTree();
                }
            });
        }
    }
</script>
</body>
</html>