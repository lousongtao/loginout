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
    <title>Sign Management</title>
    <jsp:include page="/resources/inc/head.jsp" flush="true"/>
</head>
<body>
<div id="main">
    <div id="toolbar">
        <shiro:hasPermission name="ucenter:sign:signinout">
            <button type="button" class="btn btn-success signInButton">Sign In</button>
            <button type="button" class="btn btn-success signOutButton">Sign Out</button>
        </shiro:hasPermission>
        <shiro:hasPermission name="ucenter:sign:supplysign">
            <button type="button" class="btn btn-success supplySignButton">Supply Sign</button>
        </shiro:hasPermission>
    </div>
    <table id="table"></table>
</div>
<div>
</div>
<jsp:include page="/resources/inc/footer.jsp" flush="true"/>
<div>
    <script type="text/javascript">
        var s_edit_h = 'hidden';
        var s_delete_h = 'hidden';
    </script>
</div>
<shiro:hasPermission name="ucenter:signrecord:update">
    <script type="text/javascript">
        s_edit_h = '';
    </script>
</shiro:hasPermission>
<shiro:hasPermission name="ucenter:signrecord:delete">
    <script type="text/javascript">
        s_delete_h = '';
    </script>
</shiro:hasPermission>

<script>
    var $table = $('#table');
    $(function () {
        // bootstrap table初始化
        $table.bootstrapTable({
            url: '${basePath}/manage/sign/list',
            height: getHeight(),
            striped: true,
            showRefresh: true,
            showColumns: true,
            minimumCountColumns: 2,
            clickToSelect: true,
//            detailView: true,
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
//                {field: 'ck', radio: true},
                {field: 'signId', title: 'Sign ID', align: 'center'},
                {field: 'uName', title: 'Name', align: 'center'},
                {field: 'signTime', title: 'Sign Time', formatter: 'timeStampToDateTime', align: 'center'},
                {field: 'signType', title: 'Sign Type', formatter: 'signTypeFormatter', align: 'center'},
                {field: 'signVia', title: 'Sign Via', formatter: 'signViaFormatter', align: 'center'},
                {
                    title: 'Operation', field: 'idd', align: 'center', clickToSelect: false,
                    formatter: function (value, row, index) {
                        var u = '<a  class="update ' + s_edit_h + '" href="#" mce_href="#" title="Edit" onclick="updateAction(\'' + row.signId + '\')"><i class="glyphicon glyphicon-edit "></i></a>&nbsp&nbsp&nbsp ';
                        var d = '<a  class="delete ' + s_delete_h + '" href="#" mce_href="#" title="Remove" onclick="deleteAction(\'' + row.signId + '\')"><i class="glyphicon glyphicon-remove "></i></a> ';
                        return u + d;
                    }
                }
            ]
        });

        var flag = true;
        $(".signInButton").click(function () {
            if (flag) {
                signIn('${basePath}/manage/sign/signIn');
                flag = false;
                setTimeout(function () {
                    flag = true;
                }, 30000);
            } else {
                $.confirm({
                    title: false,
                    content: 'You\'ve Just Signed In ,Please Don\'t repeat clicks',
                    autoClose: 'cancel|2000',
                    backgroundDismiss: true,
                    buttons: {
                        cancel: {
                            text: '取消',
                            btnClass: 'waves-effect waves-button'
                        }
                    }
                });
            }
        });

        $(".signOutButton").click(function () {
            if (flag) {
                signIn('${basePath}/manage/sign/signOut');
                flag = false;
                setTimeout(function () {
                    flag = true;
                }, 30000);
            } else {
                $.confirm({
                    title: false,
                    content: 'You\'ve Just Signed In ,Please Don\'t repeat clicks',
                    autoClose: 'cancel|2000',
                    backgroundDismiss: true,
                    buttons: {
                        cancel: {
                            text: '取消',
                            btnClass: 'waves-effect waves-button'
                        }
                    }
                });
            }
        });

        $('.supplySignButton').click(createAction);
    });

    // 新增
    var createDialog;

    function createAction() {
        createDialog = $.dialog({
            animationSpeed: 300,
            title: 'Supply Sign',
            content: 'url:${basePath}/manage/sign/supplySign',
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
                title: 'Update Sign Record',
                content: 'url:${basePath}/manage/sign/update/' + id,
                onContentReady: function () {
                    initMaterialInput();
                }
            });
        }
    }

    function deleteAction(signId) {
        var ids = new Array();
        if (signId) {
            ids.push(signId);
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
                            url: '${basePath}/manage/sign/delete/' + ids.join("-"),
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
                                            content: result.data,
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

    //staff sign in
    function signIn(url) {
        $.ajax({
            type: 'post',
            url: url,
            beforeSend: function () {

            },
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
                            content: result.data.errorMsg || result.data,
                            buttons: {
                                confirm: {
                                    text: 'Yes',
                                    btnClass: 'waves-effect waves-button waves-light'
                                }
                            }
                        });
                    }

                } else {
                    $table.bootstrapTable('refresh');
                    $.confirm({
                        theme: 'white',
                        animation: 'rotateX',
                        closeAnimation: 'rotateX',
                        title: false,
                        content: "Signed In Success",
                        buttons: {
                            confirm: {
                                text: 'Okay',
                                btnClass: 'waves-effect waves-button waves-light'
                            }
                        }
                    });

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

    function signTypeFormatter(value, row, index) {
        if (value == 0 ) {
            return '<span class="label label-success">In</span>';
        } else {
            return '<span class="label label-danger">Out</span>';
        }
    }

    function signViaFormatter(value, row, index) {
        if (value == 0) {
            return "Browser Sign";
        } else if (value == 1) {
            return "Client Sign";
        } else {
            return "-";
        }
    }

</script>
</body>
</html>