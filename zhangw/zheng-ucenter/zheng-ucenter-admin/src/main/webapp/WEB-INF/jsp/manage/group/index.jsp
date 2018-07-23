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
        <shiro:hasPermission name="ucenter:group:create">
            <a class="waves-effect waves-button" href="javascript:;"
               onclick="createAction()"><i class="zmdi zmdi-plus"></i>
                New</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="ucenter:group:delete">
            <a class="waves-effect waves-button" href="javascript:;"
               onclick="deleteAction()"><i class="zmdi zmdi-plus"></i>
                Delete</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="ucenter:group:update">
            <a class="waves-effect waves-button" href="javascript:;"
               onclick="moveInOutAction()"><i class="zmdi zmdi-plus"></i>
                Move In/Out Staff</a>
        </shiro:hasPermission>
    </div>
    <table id="table"></table>
</div>
<div>
    <script type="text/javascript">
        var s_edit_h = 'hidden';
        var s_delete_h = 'hidden';
    </script>
</div>
<shiro:hasPermission name="ucenter:group:update">
    <script type="text/javascript">
        s_edit_h = '';
    </script>
</shiro:hasPermission>
<shiro:hasPermission name="ucenter:group:delete">
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
            url: '${basePath}/manage/group/list',
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
            singleSelect: true,
            smartDisplay: false,
            escape: true,
            searchOnEnterKey: true,
            idField: 'id',
            maintainSelected: true,
            toolbar: '#toolbar',
            columns: [
                {field: 'ck', radio: true},
                {field: 'id', title: 'ID', align: 'center'},
                {field: 'name', title: 'Name'},
                {field: 'count', title: 'Population',formatter:'countUser'},
                {field: 'userList', title: 'Staffs', formatter: 'userListToName'},
                {field: 'description', title: 'Describe', visible: false},
                {field: 'createTime', title: 'Create Time', formatter: 'timeStampToDateTime', visible: false},
                {
                    title: 'Operation', field: 'idd', align: 'center', clickToSelect: false,
                    formatter: function (value, row, index) {
                        var u = '<a  class="update ' + s_edit_h + '" href="#" mce_href="#" title="Edit" onclick="updateAction(\'' + row.id + '\')"><i class="glyphicon glyphicon-edit "></i></a>&nbsp&nbsp&nbsp ';
                        var d = '<a  class="delete ' + s_delete_h + '" href="#" mce_href="#" title="Remove" onclick="deleteAction(\'' + row.id + '\')"><i class="glyphicon glyphicon-remove "></i></a> ';
                        return u + d;
                    }
                }
            ]
        });
    });

    function countUser(value, row, index) {
        if (row.userList) {
            return row.userList.length;
        } else {
            return "-";
        }
    }
    function userListToName(value, row, index) {
        if (value) {
            var userName =new Array();
            for (var i = 0; i < value.length; i++) {
                userName.push(value[i].realname);
            }
            return userName.join("|");
        } else {
            return "-";
        }
    }

    // 新增
    var createDialog;

    function createAction() {
        createDialog = $.dialog({
            animationSpeed: 300,
            title: '新增组',
            content: 'url:${basePath}/manage/group/create',
            onContentReady: function () {
                initMaterialInput();
            }
        });
    }

    // 编辑
    var updateDialog;

    function updateAction(id) {
        if (id) {
            updateDialog = $.dialog({
                animationSpeed: 300,
                title: 'Update Group',
                content: 'url:${basePath}/manage/group/update/' + id,
                onContentReady: function () {
                    initMaterialInput();
                }
            });
        }
    }

    // 删除
    var deleteDialog;

    function deleteAction(id) {
        var ids = new Array();
        if (id) {
            ids.push(id);
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
                    ids.push(rows[i].id);
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
                            url: '${basePath}/manage/group/delete/' + ids.join("-"),
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

</script>
</body>
</html>