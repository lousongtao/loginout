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
    <title>Role Management</title>
    <jsp:include page="/resources/inc/head.jsp" flush="true"/>
</head>
<body>
<div id="main">
    <div id="toolbar">
        <shiro:hasPermission name="upms:role:create"><a class="waves-effect waves-button" href="javascript:;"
                                                        onclick="createAction()"><i class="zmdi zmdi-plus"></i>
            New</a></shiro:hasPermission>
        <%--<shiro:hasPermission name="upms:role:update"><a class="waves-effect waves-button" href="javascript:;"--%>
                                                        <%--onclick="updateAction()"><i class="zmdi zmdi-edit"></i>--%>
            <%--编辑角色</a></shiro:hasPermission>--%>
        <shiro:hasPermission name="upms:role:delete"><a class="waves-effect waves-button" href="javascript:;"
                                                        onclick="deleteAction()"><i class="zmdi zmdi-close"></i>
            Delete</a></shiro:hasPermission>
        <shiro:hasPermission name="upms:role:permission"><a class="waves-effect waves-button" href="javascript:;"
                                                            onclick="permissionAction()"><i class="zmdi zmdi-key"></i>
            Permission</a></shiro:hasPermission>
    </div>
    <table id="table"></table>
</div>
<div>
    <script type="text/javascript">
        var s_edit_h = 'hidden';
        var s_delete_h = 'hidden';
    </script>
</div>
<shiro:hasPermission name="upms:role:update">
    <script type="text/javascript">
        s_edit_h = '';
    </script>
</shiro:hasPermission>
<shiro:hasPermission name="upms:role:delete">
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
            url: '${basePath}/manage/role/list',
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
            idField: 'roleId',
            maintainSelected: true,
            toolbar: '#toolbar',
            columns: [
                {field: 'ck', checkbox: true},
                {field: 'roleId', title: 'ID', sortable: true, align: 'center'},
                {field: 'name', title: 'Name'},
                {field: 'title', title: 'Title *'},
                {field: 'description', title: 'Description'},
                {
                    title: 'Operation', field: 'idd', align: 'center', clickToSelect: false,
                    formatter: function (value, row, index) {
                        var u = '<a  class="update ' + s_edit_h + '" href="#" mce_href="#" title="Edit" onclick="updateAction(\'' + row.roleId + '\')"><i class="glyphicon glyphicon-edit "></i></a>&nbsp&nbsp&nbsp ';
                        var d = '<a  class="delete ' + s_delete_h + '" href="#" mce_href="#" title="Remove" onclick="deleteAction(\'' + row.roleId + '\')"><i class="glyphicon glyphicon-remove "></i></a> ';
                        return u + d;
                    }
                }
            ]
        });
    });

    // 新增
    var createDialog;

    function createAction() {
        createDialog = $.dialog({
            animationSpeed: 300,
            title: 'New Role',
            content: 'url:${basePath}/manage/role/create',
            onContentReady: function () {
                initMaterialInput();
            }
        });
    }

    // 编辑
    var updateDialog;

    function updateAction(roleId) {
        if(roleId){
            updateDialog = $.dialog({
                animationSpeed: 300,
                title: 'Update Role',
                content: 'url:${basePath}/manage/role/update/' + roleId,
                onContentReady: function () {
                    initMaterialInput();
                }
            });
        }
    }

    // 删除
    var deleteDialog;

    function deleteAction(roleId) {
        var ids = new Array();
        if(roleId){
            ids.push(roleId);
            deleteMethod(ids);
        }else {
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
                    ids.push(rows[i].roleId);
                }
                deleteMethod(ids);
            }
        }
    }
    function deleteMethod(ids){
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
                        var ids = new Array();
                        for (var i in rows) {
                            ids.push(rows[i].roleId);
                        }
                        $.ajax({
                            type: 'get',
                            url: '${basePath}/manage/role/delete/' + ids.join("-"),
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


    // 角色权限
    var permissionDialog;
    var roleId;

    function permissionAction() {
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
            roleId = rows[0].roleId;
            permissionDialog = $.dialog({
                animationSpeed: 300,
                title: 'Permission',
                content: 'url:${basePath}/manage/role/permission/' + roleId,
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