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
        <shiro:hasPermission name="ucenter:sign:write">
            <button type="button" class="btn btn-success signButton">Sign In</button>
        </shiro:hasPermission>
    </div>
    <table id="table"></table>
</div>
<div>
</div>
<jsp:include page="/resources/inc/footer.jsp" flush="true"/>
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
                {field: 'signId', title: 'ID', align: 'center'},
                {field: 'uName', title: 'Name', align: 'center'},
                {field: 'signTime', title: 'Sign Time', formatter: 'timeStampToDateTime', align: 'center'},
                {field: 'signType', title: 'Sign Type', formatter: 'signTypeFormatter', align: 'center'}
            ]
        });

        var flag = true;
        $(".signButton").click(function () {
            if (flag) {
                signIn();
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

    });

    //staff sign in
    function signIn() {
        $.ajax({
            type: 'post',
            url: '${basePath}/manage/sign/signIn',
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