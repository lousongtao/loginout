<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<div id="changePwdDialog" class="crudDialog">
    <form id="modifyForm" method="post">
        <div class="form-group">
            <label for="loginname">Your Account</label>
            <input id="loginname" type="text" class="form-control" readonly
                   value="${currentUser.loginname}">
        </div>
        <div class="form-group">
            <label for="oldPassword">Old Password</label>
            <input id="oldPassword" type="text" class="form-control" name="oldPassword" maxlength="32">
        </div>
        <div class="form-group">
            <label for="newPassword">New Password</label>
            <input id="newPassword" type="text" class="form-control" name="newPassword" maxlength="32">
        </div>

        <div class="form-group text-right dialog-buttons">
            <a class="waves-effect waves-button" href="javascript:;" onclick="createSubmit();"> Save </a>
            <a class="waves-effect waves-button" href="javascript:;" onclick="changePwdDialog.close();">Cancel</a>
        </div>
    </form>
</div>
<script>
    function createSubmit() {
        $.ajax({
            type: 'post',
            url: '${basePath}/manage/user/changePwd',
            data: $('#modifyForm').serialize(),
            beforeSend: function () {
                if ($('#oldPassword').val() == '') {
                    $('#oldPassword').focus();
                    return false;
                }
                if ($('#newPassword').val() == '' || $('#newPassword').val().length < 5) {
                    $('#newPassword').focus();
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
                            content: result.data.errorMsg || result.data,
                            buttons: {
                                confirm: {
                                    text: '确认',
                                    btnClass: 'waves-effect waves-button waves-light'
                                }
                            }
                        });
                    }
                } else {
                    $.alert({
                        title: 'Success!',
                        content: 'The password has been modified successfully, please login again'
                    });
                    setTimeout(function () {
                        window.location.href = '${basePath}/sso/logout';
                    }, 2000);

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
</script>