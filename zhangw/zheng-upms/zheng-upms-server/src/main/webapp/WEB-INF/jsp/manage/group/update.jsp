﻿<%@ page contentType="text/html; charset=utf-8" %>
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
            <label for="name">Name</label>
            <input id="name" type="text" class="form-control" name="name" maxlength="20" value="${mcGroup.name}">
        </div>
        <div class="form-group">
            <label for="level">Level</label>
            <input id="level" type="number" class="form-control" name="level" maxlength="20" value="${mcGroup.level}">
        </div>
        <div class="form-group">
            <label for="color">颜色</label>
            <input id="color" type="color" class="form-control" name="color" maxlength="20"
                   value="${mcGroup.color}">
        </div>
        <div class="form-group">
            <label for="description">描述</label>
            <input id="description" type="area" class="form-control" name="description" value="${mcGroup.description}">
        </div>
        <div class="form-group text-right dialog-buttons">
            <a class="waves-effect waves-button" href="javascript:;" onclick="createSubmit();">Save</a>
            <a class="waves-effect waves-button" href="javascript:;" onclick="updateDialog.close();">Cancel</a>
        </div>
    </form>
</div>
<script>

    function createSubmit() {
        $.ajax({
            type: 'post',
            url: '${basePath}/manage/group/update/${mcGroup.id}',
            data: $('#updateForm').serialize(),
            beforeSend: function () {
                if ($('#name').val() == '') {
                    $('#name').focus();
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
                            text: 'Yes',
                            btnClass: 'waves-effect waves-button waves-light'
                        }
                    }
                });
            }
        });
    }
</script>