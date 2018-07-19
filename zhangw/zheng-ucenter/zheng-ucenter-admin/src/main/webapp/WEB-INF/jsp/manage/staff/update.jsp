<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<div id="updateDialog" class="crudDialog">
    <form id="updateForm" method="post">
        <div class="form-group">
            <label for="username">帐号</label>
            <input id="username" type="text" class="form-control" name="username" maxlength="20"
                   value="${user.username}" readonly>
        </div>
        <div class="form-group">
            <label for="realname">姓名</label>
            <input id="realname" type="text" class="form-control" name="realname" maxlength="20"
                   value="${user.realname}">
        </div>
        <div class="form-group">
            <label for="phone">电话</label>
            <input id="phone" type="text" class="form-control" name="phone" maxlength="20" value="${user.phone}">
        </div>
        <div class="form-group">
            <label for="baseSalaryMoney">底薪(单位:元,最多保留2位小数)</label>
            <input id="baseSalaryMoney" type="text" class="form-control" name="baseSalaryMoney" value="${(user.baseSalary)/100}">
        </div>
        <div class="form-group">
            <label for="perSalaryMoney">每小时薪酬(单位:元,同上)</label>
            <input id="perSalaryMoney" type="text" class="form-control" name="perSalaryMoney" value="${(user.perSalary)/100}">
        </div>
        <div class="form-group">
            <label for="email">邮箱</label>
            <input id="email" type="text" class="form-control" name="email" maxlength="50" value="${user.email}">
        </div>
        <div class="radio">
            <div class="radio radio-inline radio-info">
                <input id="sex_1" type="radio" name="sex" value="1" <c:if test="${user.sex==1}">checked</c:if>>
                <label for="sex_1">男 </label>
            </div>
            <div class="radio radio-inline radio-danger">
                <input id="sex_0" type="radio" name="sex" value="0" <c:if test="${user.sex==0}">checked</c:if>>
                <label for="sex_0">女 </label>
            </div>
        </div>
        <div class="radio">
            <div class="radio radio-inline radio-success">
                <input id="schedulestatus_0" type="radio" name="schedulestatus" value="1" <c:if test="${user.schedulestatus==1}">checked</c:if>>
                <label for="schedulestatus_0">参与排班 </label>
            </div>
            <div class="radio radio-inline">
                <input id="schedulestatus_1" type="radio" name="schedulestatus" value="0" <c:if test="${user.schedulestatus==0}">checked</c:if>>
                <label for="schedulestatus_1">否 </label>
            </div>
        </div>
        <div class="radio">
            <div class="radio radio-inline radio-success">
                <input id="locked_0" type="radio" name="locked" value="0" <c:if test="${user.locked==0}">checked</c:if>>
                <label for="locked_0">正常 </label>
            </div>
            <div class="radio radio-inline">
                <input id="locked_1" type="radio" name="locked" value="1" <c:if test="${user.locked==1}">checked</c:if>>
                <label for="locked_1">锁定 </label>
            </div>
        </div>
        <div class="form-group text-right dialog-buttons">
            <a class="waves-effect waves-button" href="javascript:;" onclick="createSubmit();">保存</a>
            <a class="waves-effect waves-button" href="javascript:;" onclick="updateDialog.close();">取消</a>
        </div>
    </form>
</div>
<script>


    function createSubmit() {
        //先做重要参数校验
        var isMoney = /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
        var baseSalary = $("#baseSalaryMoney").val();
        var perSalary = $("#perSalaryMoney").val();
        if (baseSalary != "0" && !isMoney.test(baseSalary)) {
            showTips("底薪输入不合规");
            return;
        }
        if (perSalary != "0" && !isMoney.test(perSalary)) {
            showTips("时薪输入不合规");
            return;
        }
        $.ajax({
            type: 'post',
            url: '${basePath}/manage/staff/update/${user.userId}',
            data: $('#updateForm').serialize(),
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
                                    text: '确认',
                                    btnClass: 'waves-effect waves-button waves-light'
                                }
                            }
                        });
                    }
                } else {
                    updateDialog.close();
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
                            text: '确认',
                            btnClass: 'waves-effect waves-button waves-light'
                        }
                    }
                });
            }
        });
    }
</script>