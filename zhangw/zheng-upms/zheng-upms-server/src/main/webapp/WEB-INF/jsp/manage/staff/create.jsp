<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<div id="createDialog" class="crudDialog">
    <form id="createForm" method="post">
        <div class="form-group">
            <label for="username">Account</label>
            <input id="username" type="text" class="form-control" name="loginname" maxlength="20">
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input id="password" type="text" class="form-control" name="password" maxlength="32">
        </div>
        <div class="form-group">
            <label for="realname">Name</label>
            <input id="realname" type="text" class="form-control" name="realname" maxlength="20">
        </div>
        <div class="form-group">
            <label for="phone">Telephone</label>
            <input id="phone" type="text" class="form-control" name="phone" maxlength="20">
        </div>
        <div class="form-group">
            <label for="baseSalaryMoney">Base Salary($)</label>
            <input id="baseSalaryMoney" type="text" class="form-control" name="baseSalaryMoney" maxlength="20">
        </div>
        <div class="form-group">
            <label for="perSalaryMoney">Hour Salary($)</label>
            <input id="perSalaryMoney" type="text" class="form-control" name="perSalaryMoney" maxlength="20">
        </div>
        <div class="form-group">
            <label for="email">Mail</label>
            <input id="email" type="text" class="form-control" name="email" maxlength="50">
        </div>
        <div class="radio">
            <div class="radio radio-inline radio-info">
                <input id="sex_1" type="radio" name="sex" value="1" checked>
                <label for="sex_1">Male </label>
            </div>
            <div class="radio radio-inline radio-danger">
                <input id="sex_0" type="radio" name="sex" value="0">
                <label for="sex_0">Female</label>
            </div>
        </div>
        <div class="radio">
            <div class="radio radio-inline radio-success">
                <input id="schedulestatus_0" type="radio" name="schedulestatus" value="1" checked>
                <label for="schedulestatus_0">Do schedule </label>
            </div>
            <div class="radio radio-inline">
                <input id="schedulestatus_1" type="radio" name="schedulestatus" value="0">
                <label for="schedulestatus_1">No</label>
            </div>
        </div>
        <div class="radio">
            <div class="radio radio-inline radio-success">
                <input id="locked_0" type="radio" name="locked" value="0" checked>
                <label for="locked_0">Available</label>
            </div>
            <div class="radio radio-inline">
                <input id="locked_1" type="radio" name="locked" value="1">
                <label for="locked_1">Unavailable</label>
            </div>
        </div>
        <div class="form-group text-right dialog-buttons">
            <a class="waves-effect waves-button" href="javascript:;" onclick="createSubmit();">Save</a>
            <a class="waves-effect waves-button" href="javascript:;" onclick="createDialog.close();">Cancel</a>
        </div>
    </form>
</div>
<script>
    $(function () {
        //用户名异步检验
        $("#username").change(function () {
            var username = $(this).val();
            $.ajax({
                type: 'post',
                url: '${basePath}/manage/staff/ajaxUsername',
                data: {'username':username},
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
        });
    })

    function createSubmit() {
        //先做重要参数校验
        var isMoney = /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
        var baseSalary = $("#baseSalaryMoney").val();
        var perSalary = $("#perSalaryMoney").val();
        if (baseSalary != "0" && !isMoney.test(baseSalary)) {
            showTips("Base salary is not right format");
            return;
        }
        if (perSalary != "0" && !isMoney.test(perSalary)) {
            showTips("Hour salary is not right format");
            return;
        }

        $.ajax({
            type: 'post',
            url: '${basePath}/manage/staff/create',
            data: $('#createForm').serialize(),
            beforeSend: function () {
                if ($('#username').val() == '') {
                    $('#username').focus();
                    return false;
                }
                if ($('#password').val() == '' || $('#password').val().length < 5) {
                    $('#password').focus();
                    return false;
                }
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
                    createDialog.close();
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
</script>